# artist-copilot
Host artist profiles with the intention of matching them with opportunities to show their work, and more

### For a new deployment
1. create a google cloud bucket. Here is a sample configuration:
Overview
   Created August 10, 2026 at 12:37:52 PM UTC-4
   Updated August 10, 2026 at 12:37:52 PM UTC-4
   Hierarchical namespace Not enabled
   Location type Region
   Location us-east1 (South Carolina)
   Replication —
   Cross-bucket replication Not enabled
   Default storage class Standard
   Requester Pays - OFF
   Tags None
   Labels None
   Cloud Console URL https://console.cloud.google.com/storage/browser/artist-venue-images
   Cloud Storage URI gs://artist-venue-images
   Cross-origin resource sharing Not enabled
Permissions 
   Access control: Uniform 
   Public access prevention Not enabled by org policy or bucket setting
   Public access status Not public
   IP filtering Not configured
Protection
   Soft delete policy 7 days
   Object versioning Off
   Bucket retention policy None
   Object retention Disabled
   Encryption
      Default encryption key type Google-managed
      Encryption enforcement rules
         Google-managed keys Allowed
         Cloud KMS keys Allowed
         Customer-supplied keys Restricted as of August 10, 2026, 12:39:51 PM GMT-4
Object lifecycle
   Lifecycle rules None
Rapid Cache
   Cache Not configured
   Compute zones —

###
2. the whole CLI recipe.

1. Create the bucket (skip if it exists) and make objects public

bash
gcloud storage buckets create gs://my-images-bucket --location=us-east1

# Allow public reads on objects
gcloud storage buckets add-iam-policy-binding gs://my-images-bucket \
--member=allUsers --role=roles/storage.objectViewer

2. Create a backend bucket with CDN enabled

This is the key step — the --enable-cdn flag is what turns on Cloud CDN:

bash
gcloud compute backend-buckets create images-backend \
--gcs-bucket-name=my-images-bucket \
--enable-cdn \
--cache-mode=CACHE_ALL_STATIC \
--default-ttl=86400

CACHE_ALL_STATIC caches static content even if objects lack Cache-Control headers. Alternatively USE_ORIGIN_HEADERS gives you full control via object metadata (see step 6).

3. Create a URL map pointing at the backend bucket

bash
gcloud compute url-maps create images-lb \
--default-backend-bucket=images-backend

4. Set up HTTPS: certificate, proxy, and forwarding rule

bash
# Reserve a static IP
gcloud compute addresses create images-ip --global

# Google-managed cert for your domain
gcloud compute ssl-certificates create images-cert \
--domains=images.yourdomain.com --global

# Target proxy ties cert + URL map together
gcloud compute target-https-proxies create images-proxy \
--url-map=images-lb \
--ssl-certificates=images-cert

# Forwarding rule: the actual front door
gcloud compute forwarding-rules create images-https \
--address=images-ip \
--global \
--target-https-proxy=images-proxy \
--ports=443

5. Point DNS at the load balancer

Get the IP and create an A record for images.yourdomain.com:

bash
gcloud compute addresses describe images-ip --global --format="get(address)"

The managed cert will provision automatically once DNS resolves (can take 15–60 minutes). After that, https://images.yourdomain.com/path/in/bucket.jpg serves your objects through the CDN.

6. Set cache headers on upload

Since uploaded images are immutable in your model (new upload = new key), set aggressive caching when your upload service writes to the bucket:

bash
gcloud storage cp photo.webp gs://my-images-bucket/uploads/abc123.webp \
--cache-control="public, max-age=31536000, immutable"

Or in Java, since your upload service is presumably Spring Boot:

java
BlobInfo blobInfo = BlobInfo.newBuilder(bucketName, objectKey)
.setContentType("image/webp")
.setCacheControl("public, max-age=31536000, immutable")
.build();
storage.create(blobInfo, imageBytes);

Verifying it works

bash
curl -sI https://images.yourdomain.com/uploads/abc123.webp | grep -iE "age|cache|via"


top-level architecture (drawn by Claude according to my architecture design):

              ┌─────────────┐
     client ─▶│ API Gateway │
              └──────┬──────┘
        ┌───────────┼───────────┐
        ▼           ▼           ▼
┌──────────┐ ┌──────────┐ ┌──────────┐
│ profile  │ │  venue   │ │  image   │
│ service  │ │ service  │ │ service  │
└────┬─────┘ └────┬─────┘ └────┬─────┘
profile +     venue +       images
profile_venue venue_profile  (DB)
(DB)         (DB)
└─── Pub/Sub events (deletes) ───┘


Note that references between profiles and venues, and venues and profiles
are not of the same nature. Hence they are not stored and served together.

Instead the venues belonging to a profile are stored and served in the same
service as the profiles. Hence the only reference from profiles to venues is the venue id, which should never change.
Same for venues.

