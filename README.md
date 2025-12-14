# 🚀 Spring Boot Auto-Scaling MYAPP-Demo Project
**Author: Ashish Singh**

A production-style Spring Boot application deployed on **multiple Tomcat
instances (8080, 8081, 8082, 8083, 8084)** with full CI/CD, security, logging and auto-scaling.

---

## ✅ Tech Stack (Cleaned & Updated)

- ✅ Java 21
- ✅ Spring Boot 3.5.x
- ✅ Spring Security + JWT
- ✅ MySQL
- ✅ JPA / Hibernate
- ✅ Redis (Cache + Rate Limiting)
- ✅ Resilience4j
- ✅ Logback + MDC (Centralized Logging)
- ✅ Swagger (OpenAPI)
- ✅ Jenkins CI/CD + GitHub
- ✅ Docker + Docker Compose
- ✅ Nginx Load Balancer
- ✅ Multi-Tomcat Deployment
- ✅ CPU Based Auto-Scaling (Cron)
- ✅ Custom Annotations (Auditing, Rate Limiting)
- ✅ Async Background Processing (Email Service)

---

## ✅ 1. Project Overview

This is a **real-world enterprise-grade Spring Boot application** with:

- JWT based Authentication & Authorization
- Role Based Access Control (ADMIN, USER)
- User & Product Management APIs
- Redis Caching & API Rate Limiting
- Global Exception Handling
- Centralized Logging (Logback + MDC)
- Full API lifecycle logging (Filter + Controller + Service AOP)
- Swagger UI for API testing
- Jenkins CI/CD with GitHub
- Dockerized Deployment
- Nginx based Load Balancing
- CPU Load based Auto Scaling using Cron Jobs

---

## ✅ 2. Major Features

### 🔐 Security & Authentication

- JWT Token Generation
- Refresh Token API
- Custom JWT Authentication Filter
- Role Based Authorization
- Custom Access Denied Handler
- Custom Authentication Entry Point

### ⚙️ Backend Core Features

- User Management (Register, Login, Delete, Roles)
- Product CRUD APIs
- Audit Log using AOP (@Auditable)
- Background Async Task Service (Email Notifications)
- Exception Logging Aspect

### ⚡ Redis & Rate Limiting

- Redis Cache Integration
- Custom @RateLimit Annotation
- Aspect Based Rate Limiting
- Resilience4j Circuit Breaker & Retry

### 📄 Swagger & API Docs

- Swagger YAML Configuration
- Swagger UI enabled

### 📜 Logging (Updated Today ✅)

- Logback with Rolling File Appender
- MDC Logging Filter (requestId, username)
- API Request/Response Logging Filter
- Controller Level AOP Logging
- Service Level AOP Logging
- Exception AOP Logging
- Daily Rolling Logs

---

## 🗂 Project Structure
(Structure unchanged – verified and correct)

---

## ✅ 3. Environment Configuration

Environment | Port | Profile
----------- | ---- | -------
WSL DEV | 8080 | wsl-dev
WSL QA | 8081 | wsl-qa
WSL PROD-1 | 8082 | wsl-prod
WSL PROD-2 | 8083 | wsl-prod
WSL PROD-3 | 8084 | wsl-prod

---

## ✅ 4. Build & Run Commands

### ▶️ Local Run

mvn clean install

java -jar target/myapp-demo.war

### ▶️ WSL Run

java -jar -Dspring.profiles.active=wsl-prod target/myapp-demo.war

---

## ✅ 5. Docker Deployment

### Build Image

docker build -t myapp .

### Start using Docker Compose

docker-compose up -d

### Services

- Spring Boot App
- MySQL
- Nginx Load Balancer

---

## ✅ 6. Security Summary

- JWT Authentication
- Stateless APIs
- Role Based API Authorization
- Protected Endpoints via Spring Security

---

## ✅ 7. Logging

- Logback Centralized Logging
- MDC Based Correlation IDs
- Auto-scale activity stored at:

/home/aashudev/autoscale/autoscale.log

---

## ✅ 8. CI/CD with Jenkins

- Manual GitHub Trigger Supported
- Jenkins Pipeline:
    - Pulls code from GitHub
    - Builds WAR using Maven
    - Deploys to Multiple Tomcat Instances
    - Runs Health Checks
    - Rolling Deployment Supported

---

## ✅ 9. Swagger API Documentation

Access Swagger UI:

http://localhost:8082/swagger-ui.html

(Repeat for 8083, 8084)

---

## ✅ 10. Auto-Scaling Ports

Instance | Port
-------- | -----
PROD-1 | 8082
PROD-2 | 8083
PROD-3 | 8084

Triggers:
- System CPU Load
- Cron Job Execution

---

## ✅ 11. Auto-Scaling Testing

### Step 1: Install Stress Tool

sudo apt install stress -y

### Step 2: Monitor Logs

tail -f /home/aashudev/autoscale/autoscale.log

### Step 3: Generate Load

stress --cpu 4 --timeout 120

### Step 4: Verify New Servers

sudo lsof -i :8083
sudo lsof -i :8084

### Step 5: Stop Load

pkill stress

### Step 6: Verify Scale Down

sudo lsof -i :8083
sudo lsof -i :8084

---

## ✅ 12. Health Check Endpoint

http://localhost:8082/actuator/health

(Repeat for 8083 and 8084)

---

## ✅ 13. API Testing

- Authorization Header with JWT Token
- Postman / Swagger UI Supported

---

## ✅ 14. Nginx Load Balancing

- Traffic Distribution across 8082, 8083, 8084
- Health Check Based Routing
- Zero Downtime Access

---

## ✅ 15. CPU Based Auto Scaling

Scripts Location:
/home/aashudev/autoscale/

Cron Jobs:
* * * * * /home/aashudev/autoscale/start-on-load.sh
* * * * * /home/aashudev/autoscale/stop-on-low-load.sh

Scaling Rules:

CPU Load | Action
---------|--------
Load > 4 | Start PROD-2 (8083)
Load > 6 | Start PROD-3 (8084)
Load < 3 | Stop PROD-3
Load < 2 | Stop PROD-2

---

## ✅ 16. WAR Deployment

target/myapp-demo.war

Deploy to:
- Tomcat 8082
- Tomcat 8083
- Tomcat 8084

---

## ✅ 17. Features Summary

- Spring Boot 3.5.x
- JWT + Spring Security
- Swagger OpenAPI
- Centralized Logback Logging
- Controller + Service + Exception AOP Logging
- Multi-Tomcat Deployment
- Redis Cache & Rate Limiting
- Jenkins CI/CD
- Docker + Nginx
- CPU Based Auto-Scaling

---

## ✅ 18. Swagger Access

http://localhost:8080/swagger-ui.html
http://localhost:8082/swagger-ui.html

---
✅ 19. Distributed Transaction Management (Saga Pattern)

This project implements a Saga Orchestrator Pattern for handling distributed transactions across multiple services (Wallet, Payment, Invoice).

🔹 Why Saga?

Traditional DB transactions do not work across:

Multiple services

Kafka-based async processing

Payment & wallet systems

Saga ensures:

Data consistency

Fault tolerance

Crash recovery

🧠 Saga Architecture Used

✔ Saga Pattern: Orchestrator-based
✔ Saga State Persistence: MySQL (order_saga table)
✔ Saga Resume after crash
✔ Compensation logic
✔ Idempotency protection
✔ Distributed Locking

🔄 Saga Flow (Order → Payment → Invoice)
STARTED
  ↓
WALLET_DEBITED
  ↓
PAYMENT_DONE
  ↓
INVOICE_GENERATED
  ↓
COMPLETED

❌ Failure Path
ANY STEP FAILED
   ↓
COMPENSATION (Wallet Refund)
   ↓
SAGA FAILED

📦 Saga Components
1️⃣ Saga Orchestrator

Central OrderSagaService

Controls execution order

Updates saga state in DB

2️⃣ Saga State Table
order_saga
- saga_id
- order_id
- status
- created_at
- updated_at

🔐 Idempotency Support (Payment Safety)

Idempotency-Key header used for payment APIs

Prevents:

Duplicate payment

Kafka re-processing

Retry double execution

🔒 Distributed Locking (Redis)

Redis-based distributed lock

Prevents:

Concurrent saga execution

Double wallet debit

Duplicate invoice generation avoid

🔁 Saga Retry & Resume Mechanism
⏰ Cron-Based Retry Job

Automatically retries incomplete sagas

Handles:

Service crashes

Kafka consumer failures

Temporary DB/network issues

🔄 Resume Logic

Saga resumes execution from last successful step, not from beginning.

📩 Kafka Event-Driven Communication

Payment service publishes events:

PAYMENT_SUCCESS

PAYMENT_FAILED

Invoice service consumes payment events

Ensures loose coupling & async processing

🧾 Compensation Logic (Rollback)

If any step fails:

Wallet amount is refunded

Order marked as FAILED

Saga status updated as FAILED

✔ Compensation is idempotent
✔ Safe to retry

⚡ Resilience & Fault Tolerance

Resilience4j Retry

Kafka consumer retry with backoff

Saga retry via scheduler

Dead-letter handling ready (extensible)

🧪 Saga Testing Scenarios

✔ Kill application mid-transaction → Saga resumes
✔ Payment failure → Wallet refunded
✔ Invoice failure → Saga retried
✔ Kafka re-delivery → No duplicate execution



## 👨‍💻 Author

**Ashish Singh**  
Java Backend Developer (6+ Years Experience)  
Spring Boot | Microservices | DevOps | Redis | Jenkins | Docker

---

✅ *This project simulates real-world cloud-style auto-scaling deployment on WSL/Linux.*

