1. Start Docker desktop (which will start Docker daemon)
2. cd to D:\artist-copilot
3. If any MySQL data source (not docker container) is running, drop it.
4. Create a new, empty My/SQ?L data source. Set user to root and pw to rootpass.
5. run: docker compose -f docker-compose.local.yaml down
6. run: docker compose -f docker-compose.local.yaml up -d
   (this will grab a MySQL image from Docker Hub if needed, and initialize it as per init/01-database.sql)
8. credentials root / rootpass should work.
9. Create 3 schemas: profile, venue, image

