<<<<<<< HEAD
"# ProductManagentRestAPI" 
=======
# Product Management REST API
==================================================

Spring Boot REST API with JWT Authentication and Role-Based Authorization.  
Uses PostgreSQL database and Docker for containerized setup.

--------------------------------------------------
🚀 TECH STACK
--------------------------------------------------

- Java 17
- Spring Boot
- Spring Security
- JWT (JSON Web Token)
- PostgreSQL
- Hibernate / JPA
- Docker & Docker Compose
- Swagger (OpenAPI)

--------------------------------------------------
🏗️ ARCHITECTURE
--------------------------------------------------

Layered Architecture:

- Controller Layer – Handles HTTP requests
- Service Layer – Business logic
- Repository Layer – Database access
- Security Layer – JWT filter & role-based access
- Database – PostgreSQL

--------------------------------------------------
🔐 AUTHENTICATION FLOW
--------------------------------------------------

1. Login using:
   POST /api/users/login

2. JWT token is generated.

3. Send token in request header:
   Authorization: Bearer <token>

4. Token is validated before accessing protected endpoints.

--------------------------------------------------
👥 ROLES
--------------------------------------------------

ROLE_USER
- View products

ROLE_ADMIN
- Create products
- Update products
- Delete products

--------------------------------------------------
🐳 SETUP INSTRUCTIONS
--------------------------------------------------

1️⃣ Clone Repository

git clone https://github.com/ShivrajWandhe0307/ProductManagentRestAPI.git
cd ProductManagentRestAPI

2️⃣ Build and Start Containers

docker-compose up --build

--------------------------------------------------
🌐 APPLICATION URLS
--------------------------------------------------

Application:
http://localhost:8080

Swagger UI:
http://localhost:8080/swagger-ui/index.html

--------------------------------------------------
🔑 HOW TO USE JWT TOKEN IN SWAGGER
--------------------------------------------------

1. Open Swagger UI:
   http://localhost:8080/swagger-ui/index.html

2. Call:
   POST /api/users/login

3. Copy the token from response.

4. Click "Authorize" button (top right).

5. Enter:
   Bearer your_token_here

6. Click Authorize → Close.

Now you can access protected endpoints.

--------------------------------------------------
🛠 IMPORTANT DOCKER COMMANDS
--------------------------------------------------

Start containers:
docker-compose up --build

Run in background:
docker-compose up -d

Stop containers:
docker-compose down

View running containers:
docker ps

View logs:
docker logs productrestapi

Access PostgreSQL container:
docker exec -it productdb psql -U postgres

--------------------------------------------------
🗄 DATABASE CONFIGURATION (DOCKER)
--------------------------------------------------

Database: productmanagement  
Username: postgres  
Password: postgres  
Port: 5432  

--------------------------------------------------
📌 MAIN ENDPOINTS
--------------------------------------------------

Authentication:

POST /api/users/register
POST /api/users/login

Products:

GET    /api/v1/products/{id}
GET    /api/v1/products/{id}/items
GET    /api/v1/products
POST   /api/v1/products
PUT    /api/v1/products/{id}
DELETE /api/v1/products/{id}

--------------------------------------------------
📖 API DOCUMENTATION
--------------------------------------------------

Swagger (OpenAPI) available at:
http://localhost:8080/swagger-ui/index.html

--------------------------------------------------
✨ FEATURES
--------------------------------------------------

- JWT Authentication
- Role-based Authorization
- Secure REST APIs
- PostgreSQL Integration
- Dockerized Setup
- Swagger Documentation

--------------------------------------------------
👨‍💻 AUTHOR
--------------------------------------------------

Shivraj Wandhe
>>>>>>> fe68f501a3862836a7dd38bed3179ac39dc0be70
