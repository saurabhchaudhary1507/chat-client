Dockerizing chat-client

Quick steps to build and run locally (you need Docker and/or Maven installed):

1) Build with Maven (locally):

   Windows PowerShell:
   .\mvnw -B package

2) Build Docker image (if you prefer building the jar first):

   docker build -t chat-client:latest .

3) Run Docker container (pass the OpenAI API key via env):

   docker run --rm -p 8080:8080 -e OPENAI_API_KEY=your_api_key_here chat-client:latest

Or use docker-compose (reads OPENAI_API_KEY from your env):

   # Windows PowerShell
   $env:OPENAI_API_KEY = "your_api_key_here"
   docker-compose up --build

Security notes:
- Do NOT commit real secrets (API keys) to source control. Use environment variables, Docker secrets, or a secret manager.
- The repository's `application.properties` has been updated to read the API key from ${OPENAI_API_KEY}.

If you want, I can also add a CI workflow that builds the image and pushes to a registry.

