# VIDEOHUB

VIDEOTUBE is a Java Servlet + JSP web application that implements user account management and a simple video metadata + streaming prototype suitable for local testing and demos.

## Highlights

- User registration, login, update and delete account flows
- JSON-based REST API endpoints for auth, users, channels, and videos
- Video thumbnail and video file storage in MySQL (suitable for demos)
- Environment-driven configuration and improved password handling

## What's included

- Source: `src/main/java` (servlets, services, DB utils)
- Web: `src/main/webapp` (JSP pages, CSS, images)
- Build output: `build/classes`
- Database schema / seed: `video_browsing_user_registration.sql`

## Project structure (important files)

- `src/main/java/api` — public API servlets (`AuthApiRegisterServlet`, `AuthApiLoginServlet`, `VideosApiServlet`, etc.)
- `src/main/java/RegisterUser` — legacy/user-facing servlets and utilities (`UserRegisterServlet`, `UserLoginServlet`, `UserDBUtil`, etc.)
- `src/main/java/service` — service layer (`AuthService`, `UserService`, `VideoService`)
- `src/main/java/video` — domain models and DB utilities for videos and channels
- `src/main/webapp` — JSP pages and static assets (login.jsp, home.jsp, useraccount.jsp)
- `video_browsing_user_registration.sql` — creates tables and sample data

## Environment variables

Set these before running (example using Windows PowerShell):

```powershell
$env:VIDEOHUB_DB_URL = 'jdbc:mysql://localhost:3306/video_browsing'
$env:VIDEOHUB_DB_USER = 'dbuser'
$env:VIDEOHUB_DB_PASSWORD = 'secret'
$env:VIDEOHUB_JWT_SECRET = 'replace-with-a-strong-secret'
```

The application reads DB credentials from environment variables; there are no hardcoded credentials in source.

## Running locally (quick steps)

1. Create a MySQL database named `video_browsing` and run `video_browsing_user_registration.sql`.
2. Set the environment variables shown above.
3. Build and deploy the WAR to your servlet container (Tomcat example):

```powershell
# build with your IDE or keep classes under build/classes
# package into a WAR (example with ant/maven not included here)
# copy the built WAR to Tomcat's webapps/ and start Tomcat
```

4. Open your browser to the app (e.g., `http://localhost:8080/VIDEOHUB` or to the API endpoints under `/api`).

## API endpoints (summary)

- `POST /api/auth/register` — register and receive a JWT/session
- `POST /api/auth/login` — login
- `POST /api/auth/logout` — logout
- `GET /api/users/me` — get current user
- `GET /api/videos/{id}` and `GET /api/videos` — video metadata endpoints
- `POST /api/videos/upload` — upload binary video (multipart)
- `GET /api/videos/stream/{videoId}` — stream video binary
- `POST /api/videos/thumbnail` and `GET /api/videos/thumbnail/{videoId}` — thumbnail endpoints

See the servlet implementations in `src/main/java/api` for exact request/response details and required form fields.

## Notes & next steps

- This repo stores small demo videos and thumbnails in MySQL BLOBs; for production, migrate to object storage and a CDN.
- You can switch from JSP pages to an SPA frontend that consumes the `/api` endpoints.

If you'd like, I can:

- Add a quick `build`/`deploy` script for Tomcat
- Generate a small Postman collection for the API
- Add a CONTRIBUTING section or license

Enjoy working with VIDEOHUB!
