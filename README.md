# sample-app

A minimal Java 21 HTTP service (no dependencies), used to test the AIO CI/CD platform.

- `GET /` returns a JSON greeting with the version
- `GET /health` returns `{"status":"UP"}`

The self-test runs during `docker build`, so leave the platform's test command empty.
