# VIDEOHUB

VIDEOHUB is a Java Servlet + JSP web application for user account management in an online video browsing platform.

## Current Features

- User registration
- User login
- Update account details
- Delete account

## Technology Stack

- Backend: Java Servlets (Jakarta/Java EE style)
- Frontend: JSP, HTML, CSS, JavaScript
- Database: MySQL

## Production Hardening Added

- Removed hardcoded database credentials from source code
- Added environment-based DB configuration:
  - `VIDEOHUB_DB_URL`
  - `VIDEOHUB_DB_USER`
  - `VIDEOHUB_DB_PASSWORD`
- Replaced SQL string concatenation with prepared statements
- Added SHA-256 password hashing for new/updated passwords
- Kept login backward compatibility for existing plaintext passwords
- Stopped exposing user passwords in the UI

## Scale-Up Roadmap (YouTube Clone Direction)

### Phase 1 - Foundation (done in this repo)

- Secure authentication and database access
- Basic schema constraints (`UNIQUE` username/email)
- Thread-safe DB access patterns

### Phase 2 - Core Product Services (started in this repo)

- Split into separate services:
  - `auth-service` (users, sessions/JWT, roles)
  - `video-service` (upload metadata, categories, tags)
  - `interaction-service` (likes, comments, subscriptions)
- Introduce API gateway and versioned REST APIs
- Move from JSP pages to SPA frontend (React/Vue) consuming APIs

## New API Endpoints (Phase 2 foundation)

The following API endpoints are now available in this codebase:

- `POST /api/auth/register`
  - Form fields: `firstName`, `lastName`, `userName`, `password`, `email`, `phoneNo`
  - Creates a user, starts a server session, and returns a JWT token
- `POST /api/auth/login`
  - Form fields: `userName`, `password`
  - Authenticates user, starts a server session, and returns a JWT token
- `POST /api/auth/logout`
  - Ends current server session
- `GET /api/users/me`
  - Returns current authenticated user
- `GET /api/users/{id}`
  - Returns a user by numeric user ID
- `GET /api/users?offset=0&limit=10`
  - Returns paginated users (`limit` max is 100)

Notes:
- Responses are JSON.
- Authentication supports either:
  - Session cookie, or
  - `Authorization: Bearer <jwt-token>`
- Configure JWT secret for production:
  - `VIDEOHUB_JWT_SECRET=<strong-random-secret>`

## New API Endpoints (Phase 3.0 foundation)

The following channel/video metadata endpoints are now added:

- `POST /api/channels`
  - Auth required (session or Bearer token)
  - Form fields: `channelName`, `description`
- `GET /api/channels/{id}`
  - Public endpoint
- `GET /api/channels?offset=0&limit=10`
  - Public paginated channel list
- `POST /api/videos`
  - Auth required (session or Bearer token)
  - Form fields: `channelId`, `title`, `description`, `category`, `tags`, `visibility`, `durationSeconds` (optional)
  - Allowed `visibility`: `PUBLIC`, `UNLISTED`, `PRIVATE`
  - Only channel owner can create video metadata in that channel
- `GET /api/videos/{id}`
  - Public endpoint
- `GET /api/videos?offset=0&limit=10&channelId=1&query=intro`
  - Public paginated list with optional channel filter and text search

## New API Endpoints (Phase 3.1 - Video file in SQL DB)

- `POST /api/videos/upload`
  - Auth required (session or Bearer token)
  - `multipart/form-data`
  - Fields:
    - `videoId` (existing video metadata id)
    - `videoFile` (binary file)
  - Max upload size: 50 MB
  - File is stored in MySQL table `video_assets` (`LONG BLOB`)
- `GET /api/videos/stream/{videoId}`
  - Streams binary file from SQL database
  - `PRIVATE` videos require authentication
  - `PUBLIC` and `UNLISTED` can be streamed without login

Notes:
- SQL/BLOB storage is suitable for your current project stage and demos.
- For large-scale production, migrate file storage to object storage + CDN.

## New API Endpoints (Phase 3.2 - Thumbnails, duration, views)

- `POST /api/videos/thumbnail`
  - Auth required, owner only
  - `multipart/form-data`
  - Fields:
    - `videoId`
    - `thumbnailFile` (image)
  - Max upload size: 5 MB
- `GET /api/videos/thumbnail/{videoId}`
  - Returns thumbnail image binary from SQL DB
- `POST /api/videos/duration`
  - Auth required, owner only
  - Form fields:
    - `videoId`
    - `durationSeconds`
- `POST /api/videos/views/{videoId}`
  - Public endpoint to increment view count
  - Returns new `viewCount`

Video metadata now includes:
- `durationSeconds`
- `viewCount`

### Phase 3 - Media and Performance

- Add object storage for video files and thumbnails
- Add transcoding pipeline (multiple resolutions, HLS/DASH)
- Add CDN for global streaming
- Add Redis caching for hot data (home feed, trending videos)

### Phase 4 - Reliability and Growth

- Containerize services and deploy via Kubernetes
- Add observability (central logs, metrics, traces, alerting)
- Add rate limiting, abuse detection, and audit logging
- Add CI/CD with test gates and automated rollbacks

## Running Locally

1. Create MySQL database `video_browsing`.
2. Run `video_browsing_user_registration.sql` (now includes `user_registration`, `channels`, `videos`, `video_assets` tables).
3. Configure environment variables:
   - `VIDEOHUB_DB_URL=jdbc:mysql://localhost:3306/video_browsing`
   - `VIDEOHUB_DB_USER=<your-db-user>`
   - `VIDEOHUB_DB_PASSWORD=<your-db-password>`
   - `VIDEOHUB_JWT_SECRET=<strong-random-secret>`
4. Deploy the project to your servlet container (for example, Tomcat).
