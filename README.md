# artist-copilot
Host artist profiles with the intention of matching them with opportunities to show their work, and more


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

