<div align="center">

# ☕ Nõn Café — E-Commerce Platform with AI Consultant Chatbot

**Full-stack e-commerce website for a coffee brand, integrated with an AI-powered chatbot for personalized product consultation.**

[![Java](https://img.shields.io/badge/Java-17+-ED8B00?logo=openjdk&logoColor=white)]()
[![Tomcat](https://img.shields.io/badge/Tomcat-9.0-F8DC75?logo=apachetomcat&logoColor=black)]()
[![MySQL](https://img.shields.io/badge/MySQL-8.0+-4479A1?logo=mysql&logoColor=white)]()
[![Gemini API](https://img.shields.io/badge/Gemini-2.5_Flash-8E75FF?logo=googlegemini&logoColor=white)]()
[![Deploy](https://img.shields.io/badge/deployed-Render-46E3B7?logo=render&logoColor=white)]()
[![Status](https://img.shields.io/badge/status-completed-success)]()

[Live Demo](https://noncafetttn-dn.onrender.com) · [Report Bug](#) · [Request Feature](#)

</div>

---

## Overview

Nõn Café is a full-stack B2C e-commerce platform built from scratch with **Java Servlet/JSP (MVC)**, designed for a small coffee brand that previously depended entirely on third-party marketplaces (Shopee, Lazada). The project replaces that dependency with an owned sales channel, real shipping integration, real payment processing, and an AI chatbot that recommends coffee based on customer taste preferences.

**Key engineering focus areas:**
- End-to-end order pipeline: cart → checkout → payment (VNPay) → shipment (GHN) → real-time tracking
- A **context-aware AI chatbot** with intent detection, built on Gemini API, that queries a structured coffee-taste profile schema to generate grounded recommendations rather than generic LLM output
- A centralized admin console for inventory (FIFO), orders, suppliers, and multi-dimensional revenue reporting
- Five external service integrations wired into a single MVC codebase (GHN, VNPay, Gmail SMTP, Google OAuth2, Gemini AI)

---

## Features

### Customer-facing
- Email or Google OAuth2 registration/login
- Product browsing, search, and category filtering
- Cart management with GHN address lookup (province → district → ward)
- Checkout via COD or VNPay (sandbox)
- Real-time order tracking synced with GHN shipment status
- **AI chatbot** for taste-based coffee recommendations, brewing advice, and general Q&A
- Automated order-confirmation emails via Gmail SMTP
- Password recovery via email link
- Blog/news reading and contact form

### Admin console
- Dashboard: total orders, revenue, new customers, low-stock alerts
- Product management, including detailed taste-profile configuration (bitterness, roast level, brew method, etc.)
- Order management with automatic GHN shipment creation on status change
- Inventory management: stock in/out, movement history, low-stock alerts
- Supplier and category management
- Blog/content management and customer contact handling
- Revenue reports (daily/monthly/yearly) with charts

---

## Architecture

```
┌──────────────────────────────────────────────────────────────┐
│                          Client                              │
│              Customers · Guests · Admin                      │
└──────────────────────────────────────────────────────────────┘
                              │ HTTPS
                              ▼
┌──────────────────────────────────────────────────────────────┐
│                          VIEW LAYER                          │
│         JSP · HTML5 · CSS3 · Bootstrap 5 · JavaScript        │
└──────────────────────────────────────────────────────────────┘
                              │
                              ▼
┌──────────────────────────────────────────────────────────────┐
│                       CONTROLLER LAYER                       │
│                Java Servlet (MVC Pattern)                    │
│  ┌───────────────┬──────────────┬────────────────────────┐   │
│  │  GHNService   │GeminiService │    EmailService        │   │
│  │  VNPayConfig  │GoogleOAuth2  │    IntentDetector      │   │
│  │  GHNApiServlet│ChatbotServlet│    ContextExtractor    │   │
│  └───────────────┴──────────────┴────────────────────────┘   │
└──────────────────────────────────────────────────────────────┘
                              │ JDBC / DAO Pattern
                              ▼
┌──────────────────────────────────────────────────────────────┐
│                          MODEL LAYER                         │
│                     MySQL 8.0+ Database                      │
│                 Dao.java · Entity Classes                    │
└──────────────────────────────────────────────────────────────┘

External services:  GHN API · VNPay · Gmail SMTP · Gemini AI · Google OAuth2
Deployment:          Render Cloud (PaaS) · MySQL (FreeSQLDatabase)
```

---

## Tech Stack

| Layer | Technology | Notes |
|:---|:---|:---|
| Backend | Java Servlet (JDK 17+) | Classic MVC, no framework dependency |
| View | JSP + JSTL | Server-side rendering |
| Frontend | HTML5 / CSS3 / JavaScript / Bootstrap 5 | Responsive UI |
| Server | Apache Tomcat 9.0 | Servlet container |
| Database | MySQL 8.0+ | 13 core tables, DAO pattern |
| AI | Gemini 2.5 Flash API | Chatbot reasoning + generation |
| Payments | VNPay Sandbox | HMAC-SHA512 signed transactions |
| Shipping | GHN API v2 | Address lookup, fee calc, shipment creation, tracking |
| Email | Gmail SMTP + JavaMail | Order confirmations, password reset |
| Auth | Google OAuth2 | Social login |
| Deployment | Render Cloud | CI/CD from GitHub |

---

## AI Chatbot Pipeline

The chatbot is not a thin wrapper around the Gemini API — it runs a small pipeline that grounds responses in structured product data:

```
User message
     │
     ▼
IntentDetector          → classifies: product question / general chat / order status
     │
     ▼
ContextExtractor +
ConversationContext      → maintains session-based conversation state
     │
     ├─ [Product question] → query product_profile table   ──┐
     │                                                       │
     └─ [General chat] ───────────────────────────────────►  ┤
                                                             ▼
                                          ConsultantPromptBuilder
                                          → builds a grounded, context-aware prompt
                                                             │
                                                             ▼
                                              GeminiService → Gemini API call
                                                             │
                                                             ▼
                                          Persist to chatbot_history → respond
```

**Supported intents:** `PRODUCT_RECOMMEND`, `PRODUCT_INFO`, `BREW_METHOD`, `GENERAL_CHAT`, `ORDER_STATUS`

**Taste-profile schema** used to ground recommendations (`product_profile` table): flavor intensity, bitterness, acidity, sweetness, roast level, caffeine level, brew method, target audience, recommended time of day. A query like *"a mild coffee to drink in the evening"* is resolved against these fields before being handed to Gemini, rather than relying on the model's generic knowledge.

---

## External Integrations

| Service | Purpose | Docs |
|:---|:---|:---|
| GHN (Giao Hàng Nhanh) | Address lookup, shipping fee calc, shipment creation, real-time tracking | [api.ghn.vn](https://api.ghn.vn/home/docs) |
| VNPay Sandbox | Payment gateway, HMAC-SHA512 signed transactions | [sandbox.vnpayment.vn](https://sandbox.vnpayment.vn/apis/docs/gioi-thieu/) |
| Gmail SMTP | Transactional email (order confirmation, password reset) | [support.google.com/mail](https://support.google.com/mail/answer/185833) |
| Gemini API | Chatbot reasoning and response generation | [ai.google.dev](https://ai.google.dev/gemini-api/docs) |
| Google OAuth2 | Social login | [developers.google.com](https://developers.google.com/identity/protocols/oauth2) |
| Render Cloud | Hosting / CI/CD | [render.com/docs](https://render.com/docs) |

---

## Getting Started

### Prerequisites

| Requirement | Minimum | Recommended |
|:---|:---:|:---:|
| JDK | 11 | 17 LTS |
| Apache Tomcat | 9.0 | 9.0.95 |
| MySQL | 5.7 | 8.0+ |
| IDE | Eclipse 2022-06 | Eclipse 2024-06 (JEE) |

### 1. Clone the repository

```bash
git clone https://github.com/MikeNekkkk/noncafe.git
cd noncafe-ecommerce
```

### 2. Set up the database

```sql
source DB.sql;
```

### 3. Import into your IDE

```
Eclipse: File → Import → Existing Projects into Workspace
       → Add Tomcat 9 Server → Right-click Project → Run on Server
```

### 4. Configure the database connection

`src/main/java/Dao/DBConnection.java`:

```java
private static final String URL  = "jdbc:mysql://localhost:3306/noncafe?useSSL=false&serverTimezone=UTC";
private static final String USER = "root";
private static final String PASS = "your_password";
```

### 5. Configure API keys

```java
// GHNService.java
private static final String TOKEN   = "your_ghn_token";
private static final int    SHOP_ID = your_shop_id;

// GeminiService.java
private static final String API_KEY = "your_gemini_api_key";

// EmailService.java
private static final String USERNAME = "your_gmail@gmail.com";
private static final String PASSWORD = "xxxx xxxx xxxx xxxx"; // 16-char Gmail App Password

// VNPayConfig.java
public static final String vnp_TmnCode    = "your_tmn_code";
public static final String vnp_HashSecret = "your_hash_secret";

// GoogleLoginServlet.java / GoogleCallbackServlet.java
private static final String CLIENT_ID     = "your_google_client_id";
private static final String CLIENT_SECRET = "your_google_client_secret";
```

> Gmail App Password: enable 2-Step Verification on the account, then generate one at `myaccount.google.com` → Security → App Passwords.

### 6. Run

```
Right-click project → Run As → Run on Server → Apache Tomcat 9.0
→ http://localhost:8080/CafeTN/home
```

### Demo credentials

| Role | Email | Password |
|:---|:---|:---|
| Admin | `admin@cafe.com` | `admin123` |
| Customer | Register, or sign in with Google | — |

---

## Project Structure

```
CafeTN/
├── src/main/java/
│   ├── AdminControl/          # Admin CRUD servlets (products, orders, ...)
│   ├── Controller/            # Customer-facing servlets (checkout, GHN, OAuth, VNPay)
│   ├── Dao/                   # DAO pattern — Dao.java, DBConnection.java
│   ├── connection/            # DBConnection.java
│   ├── Entity/                # Order, Product, User, TrackingLog, CartItem, ...
│   └── Service/               # External integrations + chatbot logic
│       ├── GHNService.java
│       ├── GeminiService.java
│       ├── EmailService.java
│       ├── VNPayConfig.java
│       ├── IntentDetector.java
│       ├── ContextExtractor.java
│       ├── ConversationContext.java
│       └── ConsultantPromptBuilder.java
├── WebContent/
│   ├── doc/                   
│   ├── chatbot.jsp
│   ├── checkout.jsp
│   ├── payment-method.jsp
│   ├── ....
│   └── WEB-INF/web.xml
├── DB.sql
└── README.md
```

---

## Database

MySQL schema with 13 core tables, including `users`, `product`, `product_profile`, `orders`, `order_items`, `payment_transactions`, `order_tracking_log`, `stock_movement_log`, `ncc` (suppliers), `blog`, `contacts`, and `chatbot_history`.

Full schema: [`DB.sql`](DB.sql) · ERD: [`docs/erd.png`](docs/erd.png)

---

## Related Documents

| Document | Path |
|:---|:---|
| Architecture diagram | [`docs/architecture.png`](docs/architecture.png) |
| ERD | [`docs/erd.png`](docs/erd.png) |
| Use case diagram | [`docs/usecase.png`](docs/usecase.png) |
| Sequence diagrams | [`docs/sequence-diagrams/`](docs/sequence-diagrams/) |
| Database script | [`DB.sql`](DB.sql) |

---

## Author

**Mai Thị Thanh Huyền**
[GitHub](https://github.com/MikeNekkkk) · [Email](mailto:your.email@example.com) · [LinkedIn](#)

</div>
