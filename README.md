# artist-copilot
Host profiles and venues. They could be customers and stores.

There is one gateway service, and 3 microservices: profile-service, venue-service, image-service.

Each profile can have notes on multiple venues, and also multiple images.

Note that references between profiles and venues, and venues and profiles
are not of the same nature. Hence they are not stored and served together.

Instead the venues belonging to a profile are stored and served in the same
service as the profiles. The only reference from profiles to venues is the venue id, which should never change.
Same for venues.

WORK IN PROGRESS.
Next steps:
- Some end points of the gateway and services have not been implemented yet.
- Implement authentication and authorization
- Host on Google Cloud
- Dynamically scale services and gateway
