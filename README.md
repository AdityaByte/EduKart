# 🛒 Edukart
> Microservices-based ecommerce platform for purchasing notes, projects, and digital study materials.

## Architecture Diagram

<p>
  <img src="https://drive.google.com/thumbnail?id=1Cig1mOgJ4jb_8Ri4ORwH8C8dJKMo2_xk&sz=w2000" alt="Architecture" width="100%" />
</p>

## Tech Stack
**Frontend:** Next.js + Firebase Auth <br>
**Gateway:** Spring Cloud Gateway (OAuth2 Resource Server) <br>
**Service Discovery:** Eureka <br>
**Backend:** Spring Boot Microservices <br>
**Storage:** Cloudinary <br>
**Payment:** Stripe <br>
**Notifications:** Brevo <br>

**Databases:**
- Product Service → MongoDB
- Cart Service → NeonDB (Postgres)
- Order Service → Supabase (Postgres)

## 🛠 Run Instructions
```bash
# Run frontend.
cd frontend-nextjs
npm install
npm run dev

# Backend
# Configure all the environment variables.
# Make sure all the dependencies has been successfully configured.
# Run it in the intellij idea.

# In Future: Implementing docker.
```

Developer: @adityabyte