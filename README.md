

Write complete professional README.md following the reference template standard
markdown

<div align="center">

# NGHIÊN CỨU THIẾT KẾ VÀ XÂY DỰNG WEBSITE THƯƠNG MẠI ĐIỆN TỬ CHO THƯƠNG HIỆU CÀ PHÊ NÕN TÍCH HỢP CHATBOT AI TƯ VẤN KHÁCH HÀNG
## *Nõn Café E-Commerce System*

[![Status](https://img.shields.io/badge/status-completed-success)]()
[![Version](https://img.shields.io/badge/version-1.0.0-blue)]()
[![Java](https://img.shields.io/badge/Java-17+-ED8B00)]()
[![Tomcat](https://img.shields.io/badge/Tomcat-9.0-F8DC75)]()
[![MySQL](https://img.shields.io/badge/MySQL-8.0+-4479A1)]()
[![License](https://img.shields.io/badge/license-Academic--Use-orange)]()

**Khóa luận Tốt nghiệp — Năm học 2025–2026**

Khoa Toán — Tin · Trường Đại học Sư phạm · Đại học Đà Nẵng

**Demo:** [https://noncafetttn-dn.onrender.com]

</div>

---

## Mục lục

1. [Thông tin chung](#1-thông-tin-chung)
2. [Giới thiệu đề tài](#2-giới-thiệu-đề-tài)
3. [Các chức năng chính](#3-các-chức-năng-chính)
4. [Kiến trúc hệ thống](#4-kiến-trúc-hệ-thống)
5. [Công nghệ sử dụng](#5-công-nghệ-sử-dụng)
6. [Yêu cầu môi trường](#6-yêu-cầu-môi-trường)
7. [Hướng dẫn cài đặt và chạy](#7-hướng-dẫn-cài-đặt-và-chạy)
8. [Cấu trúc thư mục](#8-cấu-trúc-thư-mục)
9. [Cơ sở dữ liệu](#9-cơ-sở-dữ-liệu)
10. [Mô hình Chatbot AI tư vấn](#10-mô-hình-chatbot-ai-tư-vấn)
11. [Các dịch vụ tích hợp](#11-các-dịch-vụ-tích-hợp)
12. [Giao diện minh hoạ](#12-giao-diện-minh-hoạ)
13. [Tài liệu liên quan](#13-tài-liệu-liên-quan)
14. [Lời cảm ơn](#14-lời-cảm-ơn)
15. [Trích dẫn](#15-trích-dẫn)
16. [Liên hệ](#18-liên-hệ)

---

## 1. Thông tin chung

| Mục | Thông tin |
|:---|:---|
| **Tên đề tài (Tiếng Việt)** | Nghiên cứu thiết kế và xây dựng Website Thương mại Điện tử Cho Thương hiệu Cà Phê Nõn tích hợp Chatbot AI tư vấn khách|
| **Tên đề tài (Tiếng Anh)** | Research, Design and Development of an E-Commerce Website for Nõn Café Brand with AI-Powered Customer Chatbot AI |
| **Sinh viên thực hiện** | Mai Thị Thanh Huyền |
| **Lớp** | 22CNTTC |
| **Giảng viên hướng dẫn** | TS. Nguyễn Thị Ngọc Anh |
| **Đơn vị công tác GVHD** | Khoa Toán — Tin, Trường Đại học Sư phạm — Đại học Đà Nẵng |
| **Niên khoá** | 2022 — 2026 |
| **Năm bảo vệ** | 2026 |
| **Ngành đào tạo** | Công nghệ Thông tin |
| **URL demo** | https://noncafetttn-dn.onrender.com |

---

## 2. Giới thiệu đề tài

### 2.1. Bối cảnh

Thị trường thương mại điện tử Việt Nam tăng trưởng 18–20% mỗi năm, dự báo đạt 40 tỷ USD vào năm 2027 với hơn 60 triệu người tiêu dùng số. Tuy nhiên, các thương hiệu cà phê nhỏ và vừa vẫn chủ yếu phụ thuộc vào các sàn TMĐT như Shopee, Lazada — phải tuân theo chính sách của sàn và không làm chủ được dữ liệu khách hàng.

### 2.2. Vấn đề

- Thương hiệu cà phê Nõn chưa có kênh bán hàng trực tuyến riêng.
- Quy trình đăng ký, đăng nhập và khôi phục mật khẩu còn phức tạp, tốn thời gian.
- Thiếu cơ chế xác nhận đơn hàng kịp thời, gây cảm giác không chắc chắn cho khách hàng.
- Chưa có công cụ tư vấn sản phẩm tự động — khách hàng khó chọn cà phê phù hợp khẩu vị.
- Hệ thống quản lý vận hành (kho hàng, đơn hàng, báo cáo) còn thủ công, thiếu tập trung.

### 2.3. Mục tiêu

Đề tài xây dựng một website thương mại điện tử B2C **tự phát triển hoàn toàn** với ba đóng góp chính:

1. Xây dựng hệ thống bán hàng trực tuyến đầy đủ: giỏ hàng, thanh toán VNPay sanbox, theo dõi đơn hàng GHN.
2. Tích hợp **Chatbot tư vấn AI** dựa trên Gemini API — cá nhân hóa gợi ý sản phẩm theo khẩu vị người dùng 24/7.
3. Xây dựng hệ thống **Admin tập trung** quản lý toàn bộ vận hành: kho hàng, đơn hàng, khách hàng, báo cáo doanh thu.

### 2.4. Phạm vi

| Trong phạm vi | Ngoài phạm vi |
|:---|:---|
| Thị trường Việt Nam, thương hiệu Nõn | Bán hàng đa quốc gia |
| Sản phẩm cà phê (hòa tan, rang xay, drip) | Các ngành hàng khác |
| Thanh toán COD + VNPay Sandbox 
| Vận chuyển GHN Sandbox |
| Chatbot tư vấn cà phê bằng Gemini AI |

### 2.5. Đóng góp của đề tài

- Website thương mại điện tử hoàn chỉnh theo kiến trúc MVC, triển khai thực tế trên Render Cloud.
- Mô hình Chatbot AI tư vấn cà phê tích hợp phân tích ý định (Intent Detection) và duy trì ngữ cảnh hội thoại (Context-Aware Conversation).
- Hệ thống Admin toàn diện với dashboard thống kê, quản lý kho theo FIFO, báo cáo doanh thu đa chiều.
- Tích hợp đầy đủ 5 dịch vụ ngoài: GHN, VNPay, Gmail SMTP, Google OAuth2, Gemini AI.

---

## 3. Các chức năng chính

### Phân hệ Khách hàng

- Đăng ký / đăng nhập bằng email hoặc tài khoản Google (OAuth2).
- Xem, tìm kiếm và lọc sản phẩm theo danh mục.
- Quản lý giỏ hàng, chọn địa chỉ giao hàng tích hợp API GHN(sanbox),
- Thanh toán COD hoặc VNPay (Sandbox).
- Theo dõi trạng thái đơn hàng theo hành trình vận chuyển thực tế.
- **Trò chuyện với Chatbot AI Gemini** — tư vấn loại cà phê phù hợp khẩu vị, cách pha chế, gợi ý sản phẩm.
- Nhận email hóa đơn của đơn hàng tự động qua Gmail SMTP.
- Khôi phục mật khẩu qua liên kết gửi đến Gmail.
- Đọc bài viết tin tức, gửi liên hệ đến thương hiệu.
- Quản lý thông tin cá nhân và lịch sử mua hàng.

### Phân hệ Quản trị (Admin)

- Dashboard tổng quan: tổng đơn hàng, doanh thu, khách hàng mới, cảnh báo sắp hết hàng.
- Quản lý sản phẩm: thêm/sửa/xóa, ẩn/hiện, cấu hình profile cà phê (độ đắng, rang, cách pha...).
- Quản lý đơn hàng: duyệt, cập nhật trạng thái, tạo vận đơn GHN tự động khi chuyển sang "Đang vận chuyển".
- Quản lý kho hàng: nhập/xuất kho, xem lịch sử tồn kho, cảnh báo sắp hết.
- Quản lý khách hàng: xem danh sách, chỉnh sửa, khóa/mở tài khoản.
- Quản lý Nhà cung cấp (NCC).
- Quản lý danh mục sản phẩm.
- Quản lý bài viết / Blog và xử lý liên hệ từ khách hàng.
- Báo cáo doanh thu theo ngày, tháng, năm với biểu đồ trực quan.

---

## 4. Kiến trúc hệ thống

```
┌──────────────────────────────────────────────────────────────┐
│                      Người dùng                              │
│        (Khách hàng · Khách vãng lai · Admin)                 │
└──────────────────────────────────────────────────────────────┘
                              │ HTTPS
                              ▼
┌──────────────────────────────────────────────────────────────┐
│                           TẦNG VIEW                          │
│         JSP · HTML5 · CSS3 · Bootstrap 5 · JavaScript        │
└──────────────────────────────────────────────────────────────┘
                              │ HTTP Request / Response
                              ▼
┌──────────────────────────────────────────────────────────────┐
│                    TẦNG CONTROLLER                           │
│               Java Servlet (MVC Pattern)                     │
│  ┌───────────────┬──────────────┬────────────────────────┐   │
│  │  GHNService   │GeminiService │    EmailService        │   │
│  │  VNPayConfig  │GoogleOAuth2  │    IntentDetector      │   │
│  │  GHNApiServlet│ChatbotServlet│    ContextExtractor    │   │
│  └───────────────┴──────────────┴────────────────────────┘   │
└──────────────────────────────────────────────────────────────┘
                              │ JDBC / DAO Pattern
                              ▼
┌──────────────────────────────────────────────────────────────┐
│                         TẦNG MODEL                           │
│                     MySQL 8.0+ Database                      │
│              Dao.java  ·  Entity Classes                     │
└──────────────────────────────────────────────────────────────┘

Dịch vụ ngoài:  GHN API · VNPay · Gmail SMTP · Gemini AI · Google OAuth2
Triển khai:     Render Cloud (PaaS)  ·  MySQL (FreeSQLDatabase)
```

---

## 5. Công nghệ sử dụng

| Thành phần | Công nghệ | Phiên bản | Lý do lựa chọn |
|:---|:---|:---:|:---|
| Backend | Java Servlet | JDK 17+ | Nền tảng Java Enterprise vững chắc, học thuật |
| View | JSP + JSTL | 3.0 | Tách biệt logic và giao diện, dễ tích hợp dữ liệu động |
| Frontend | HTML5 / CSS3 / JavaScript | — | Chuẩn web, không phụ thuộc framework |
| UI Framework | Bootstrap 5 | 5.3 | Responsive, hỗ trợ tốt đa thiết bị |
| Server | Apache Tomcat | 9.0.95 | Servlet Container chuẩn, nhẹ, ổn định |
| Cơ sở dữ liệu | MySQL | 8.0+ | Quan hệ chặt, hỗ trợ tốt transaction |
| Chatbot AI | Gemini API (Google) | 2.5 Flash | LLM mạnh, free tier đủ cho demo |
| Thanh toán | VNPay Sandbox | — | Cổng thanh toán hàng đầu Việt Nam |
| Vận chuyển | GHN API | v2 | Phủ sóng toàn quốc, API rõ ràng |
| Email | Gmail SMTP + JavaMail | — | Miễn phí, đáng tin cậy |
| Xác thực | Google OAuth2 | — | Đơn giản, quen thuộc với người dùng |
| Triển khai | Render Cloud | — | Free tier, hỗ trợ Java WAR, dễ CI/CD |
| IDE | Eclipse / IntelliJ IDEA | — | Hỗ trợ tốt Dynamic Web Project |

---

## 6. Yêu cầu môi trường

| Yêu cầu | Phiên bản tối thiểu | Khuyến nghị |
|:---|:---:|:---:|
| Hệ điều hành | Windows 10 / macOS 12 / Ubuntu 20.04 | Windows 11 / Ubuntu 22.04 |
| JDK (Java) | 11 | 17 LTS |
| Apache Tomcat | 9.0 | 9.0.95 |
| MySQL | 5.7 | 8.0+ |
| Eclipse IDE | 2022-06 | 2024-06 (JEE) |
| MySQL Workbench | 8.0 | 8.0.36 |
| RAM | 4 GB | 8 GB |

---

## 7. Hướng dẫn cài đặt và chạy

### 7.1. Clone mã nguồn

```bash
git clone https://github.com/MikeNekkkk/noncafe.git
cd noncafe-ecommerce
```

### 7.2. Cài đặt cơ sở dữ liệu

```sql
-- Mở MySQL Workbench, chạy file script
source DB.sql;
```

### 7.3. Import dự án vào IDE

```
Eclipse:
  1. File → Import → Existing Projects into Workspace
  2. Chọn thư mục vừa clone
  3. Add Tomcat 9 Server → Right-click Project → Run on Server
```

### 7.4. Cấu hình kết nối cơ sở dữ liệu

Sửa file `src/main/java/Dao/DBConnection.java`:

```java
private static final String URL  = "jdbc:mysql://localhost:3306/noncafe?useSSL=false&serverTimezone=UTC";
private static final String USER = "root";
private static final String PASS = "your_password";
```

### 7.5. Cấu hình API Keys

Cập nhật các hằng số trong từng file Service:

```java
// GHNService.java
private static final String TOKEN   = "your_ghn_token";
private static final int    SHOP_ID = your_shop_id;
private static final int    SENDER_DISTRICT_ID = 1527; // Đà Nẵng

// GeminiService.java
private static final String API_KEY = "your_gemini_api_key";

// EmailService.java
private static final String USERNAME = "your_gmail@gmail.com";
private static final String PASSWORD = "xxxx xxxx xxxx xxxx"; // App Password 16 ký tự

// VNPayConfig.java
public static final String vnp_TmnCode   = "your_tmn_code";
public static final String vnp_HashSecret = "your_hash_secret";

// GoogleCallbackServlet.java / GoogleLoginServlet.java
private static final String CLIENT_ID     = "your_google_client_id";
private static final String CLIENT_SECRET = "your_google_client_secret";
```

### 7.6. Lấy App Password Gmail

```
1. Vào myaccount.google.com → Bảo mật
2. Bật Xác minh 2 bước (bắt buộc)
3. Tìm mục "Mật khẩu ứng dụng" → Tạo mới
4. Chọn: Thư + Máy tính → Google tạo mật khẩu 16 ký tự
5. Dán vào PASSWORD trong EmailService.java
```

### 7.7. Khởi chạy ứng dụng

```
1. Nhấp chuột phải vào Project → Run As → Run on Server
2. Chọn Apache Tomcat 9.0 → Finish
3. Truy cập: http://localhost:8080/CafeTN/home
```

### 7.8. Tài khoản dùng thử

| Vai trò | Email | Mật khẩu |
|:---|:---|:---|
| Admin | `admin@cafe.com` | `admin123` |
| Khách hàng | Đăng ký hoặc dùng Google OAuth2 | — |

---

## 8. Cấu trúc thư mục

```
CafeTN/
├── src/
│   └── main/
│       └── java/
│           ├── AdminControl/              
│           │   ├── HienThiSanpham.java    
│           │   ├── AddProductServlet.java 
│           │   ├── UpdateProductServlet.java
│           │   ├── UpdateOrderServlet.java 
│           │   └── ...
│           ├── Controller/               
│           │   ├── CheckoutServlet.java  
│           │   ├── GHNApiServlet.java   
│           │   ├── GoogleLoginServlet.java
│           │   ├── GoogleCallbackServlet.java
│           │   ├── VNPayServlet.java
│           │   ├── VNPayReturnServlet.java
│           │   ├── DetailOrderOfUserServlet.java
│           │   └── ...
│           ├── Dao/
│           │   ├── Dao.java               # Data Access Object (toàn bộ DB queries)
│           │   └── DBConnection.java     
│           ├── Entity/                    
│           │   ├── Order.java
│           │   ├── OrderItem.java
│           │   ├── Product.java
│           │   ├── User.java
│           │   ├── TrackingLog.java
│           │   ├── CartItem.java
│           │   └── ...
│           └── Service/                   # Business Logic & External APIs
│               ├── GHNService.java     
│               ├── GeminiService.java   
│               ├── EmailService.java     
│               ├── VNPayConfig.java      
│               ├── IntentDetector.java   
│               ├── IntentType.java       
│               ├── ContextExtractor.java  
│               ├── ConversationContext.java
│               └── ConsultantPromptBuilder.java 
├── WebContent/
│   ├── doc/                               # JSP trang Admin
│   │   ├── table-data-product.jsp
│   │   ├── table-data-oder.jsp
│   │   ├── form-add-san-pham.jsp
│   │   └── ...
│   ├── ChiTiet_DonHang.jsp                
│   ├── checkout.jsp                       
│   ├── payment-method.jsp
│   ├── chatbot.jsp                        
│   ├── admin-assets/                     
│   ├── img/                               
│   └── WEB-INF/
│       └── web.xml
├── DB.sql                                 
└── README.md
```

---

## 9. Cơ sở dữ liệu

Hệ thống sử dụng MySQL với **13 bảng nghiệp vụ** chính.

| Bảng | Mô tả |
|:---|:---|
| `users` | Tài khoản người dùng: thường + Google OAuth2, phân quyền ADMIN/USER |
| `product` | Sản phẩm cà phê: tên, giá, tồn kho, trọng lượng, ảnh, danh mục |
| `product_profile` | Profile chi tiết cà phê: độ đắng, chua, ngọt, rang, cách pha, phù hợp cho... |
| `category` | Danh mục sản phẩm |
| `orders` | Đơn hàng: thông tin người nhận, địa chỉ, GHN district/ward, phí ship |
| `order_items` | Chi tiết sản phẩm trong từng đơn hàng |
| `payment_transactions` | Giao dịch VNPay: mã giao dịch, ngân hàng, trạng thái |
| `order_tracking_log` | Lịch sử trạng thái vận chuyển theo từng bước hành trình |
| `stock_movement_log` | Nhật ký nhập/xuất kho: số lượng, lý do, thời gian |
| `ncc` | Thông tin Nhà cung cấp |
| `blog` | Bài viết tin tức thương hiệu |
| `contacts` | Liên hệ từ khách hàng |
| `chatbot_history` | Lịch sử hội thoại chatbot: session_id, userId, nội dung |

> Sơ đồ ERD chi tiết: [`docs/erd.png`](docs/erd.png)  
> Script SQL: [`DB.sql`](DB.sql)

---

## 10. Mô hình Chatbot AI tư vấn

### 10.1. Kiến trúc Chatbot

Chatbot hoạt động theo pipeline nhiều lớp xử lý chuyên biệt:

```
Tin nhắn khách hàng
        │
        ▼
┌─────────────────────┐
│   IntentDetector    │  Phân loại: hỏi sản phẩm / hội thoại chung / đơn hàng
└─────────────────────┘
        │
        ▼
┌─────────────────────┐
│  ContextExtractor   │  Duy trì ngữ cảnh hội thoại (session-based)
│  ConversationContext│
└─────────────────────┘
        │
        ├─── [Hỏi sản phẩm] ──► Truy vấn DB (product_profile) ──►┐
        │                                                          │
        └─── [Hội thoại chung] ─────────────────────────────────►┤
                                                                   ▼
                                                   ┌──────────────────────────┐
                                                   │  ConsultantPromptBuilder │
                                                   │  Tạo prompt có ngữ cảnh  │
                                                   └──────────────────────────┘
                                                                   │
                                                                   ▼
                                                   ┌──────────────────────────┐
                                                   │     GeminiService        │
                                                   │  Gọi Gemini API → sinh   │
                                                   │  câu trả lời tự nhiên    │
                                                   └──────────────────────────┘
                                                                   │
                                                                   ▼
                                                   Lưu chatbot_history → DB
                                                                   │
                                                                   ▼
                                                        Phản hồi đến khách
```

### 10.2. Các loại Intent được hỗ trợ

| Intent | Mô tả | Xử lý |
|:---|:---|:---|
| `PRODUCT_RECOMMEND` | Hỏi gợi ý cà phê theo khẩu vị | Truy vấn DB + Gemini |
| `PRODUCT_INFO` | Hỏi thông tin sản phẩm cụ thể | Truy vấn DB + Gemini |
| `BREW_METHOD` | Hỏi cách pha chế | Gemini (context sản phẩm) |
| `GENERAL_CHAT` | Hội thoại thông thường | Gemini trực tiếp |
| `ORDER_STATUS` | Hỏi trạng thái đơn hàng | Truy vấn DB orders |

### 10.3. Cá nhân hóa theo profile cà phê

Mỗi sản phẩm được gắn profile chi tiết trong bảng `product_profile` với các chiều:

| Thuộc tính | Mô tả | Ví dụ |
|:---|:---|:---|
| `flavor_intensity` | Độ đậm đà (1–5) | 4 |
| `bitterness_level` | Độ đắng (1–5) | 3 |
| `acidity_level` | Độ chua (1–5) | 2 |
| `sweetness_level` | Độ ngọt (1–5) | 3 |
| `roast_level` | Mức độ rang | DARK / MEDIUM / LIGHT |
| `caffeine_level` | Mức caffeine | HIGH / MEDIUM / LOW |
| `brew_methods` | Phương pháp pha phù hợp | Phin, Espresso, Cold Brew |
| `suitable_for` | Đối tượng phù hợp | Người mới uống, người thích đắng... |
| `recommended_time` | Thời điểm uống | Morning, Afternoon, Evening |

Khi khách hỏi "cà phê nhẹ uống buổi tối", Chatbot truy vấn DB theo các chiều này và cung cấp context cho Gemini để sinh câu trả lời chính xác.

---

## 11. Các dịch vụ tích hợp

###  Render Cloud

Nền tảng PaaS dùng để triển khai ứng dụng Java Servlet/JSP. Hỗ trợ triển khai từ GitHub, tự động build và deploy khi push code, không cần quản lý máy chủ vật lý.

> Tài liệu: [https://render.com/docs](https://render.com/docs)

###  Gmail SMTP

Gửi email thật đến khách hàng (xác nhận đơn hàng, khôi phục mật khẩu) qua máy chủ SMTP chính thức của Google.

```
Host:     smtp.gmail.com
Port:     587
Security: STARTTLS
Auth:     App Password (16 ký tự — tạo tại myaccount.google.com)
Library:  JavaMail API
```

> Tài liệu: [https://support.google.com/mail/answer/185833](https://support.google.com/mail/answer/185833)

###  VNPay Sandbox

Cổng thanh toán điện tử hàng đầu Việt Nam. Xác thực giao dịch bằng chữ ký số HMAC-SHA512. Hỗ trợ thẻ ngân hàng, ví điện tử, QR Pay.

> Tài liệu: [https://sandbox.vnpayment.vn/apis/docs/gioi-thieu/](https://sandbox.vnpayment.vn/apis/docs/gioi-thieu/)

###  Gemini AI

Mô hình ngôn ngữ lớn (LLM) của Google được tích hợp qua API chính thức. Đóng vai trò chatbot tư vấn cà phê 24/7 — phân tích ý định, duy trì ngữ cảnh và sinh câu trả lời cá nhân hóa.

> Tài liệu: [https://ai.google.dev/gemini-api/docs](https://ai.google.dev/gemini-api/docs)

###  GHN – Giao Hàng Nhanh

Tích hợp đầy đủ API GHN v2:
- Lấy danh sách Tỉnh/Thành – Quận/Huyện – Phường/Xã cho form địa chỉ.
- Tính phí vận chuyển theo trọng lượng thực tế và kích thước hộp carton.
- Tạo vận đơn tự động khi admin chuyển đơn sang trạng thái "Đang vận chuyển".
- Theo dõi hành trình vận chuyển và hiển thị cho khách hàng.

> Tài liệu: [https://api.ghn.vn/home/docs](https://api.ghn.vn/home/docs)

###  Google OAuth2

Đăng nhập nhanh bằng tài khoản Gmail thông qua Google Identity Services. `GoogleLoginServlet` khởi tạo luồng OAuth2, `GoogleCallbackServlet` xử lý mã xác thực, tạo/cập nhật tài khoản và thiết lập session.

> Tài liệu: [https://developers.google.com/identity/protocols/oauth2](https://developers.google.com/identity/protocols/oauth2)

---

## 12. Giao diện minh hoạ

| Trang chủ | Chi tiết sản phẩm |
|:---:|:---:|
| ![Trang chủ](screenshots/01-home.png) | ![Chi tiết SP](screenshots/02-product-detail.png) |

| Chatbot AI tư vấn | Theo dõi đơn hàng |
|:---:|:---:|
| ![Chatbot](screenshots/03-chatbot.png) | ![Tracking](screenshots/04-order-tracking.png) |

| Dashboard Admin | Quản lý đơn hàng |
|:---:|:---:|
| ![Admin Dashboard](screenshots/05-admin-dashboard.png) | ![Đơn hàng](screenshots/06-order-management.png) |

---

## 13. Tài liệu liên quan

| Tài liệu | Đường dẫn |
|:---|:---|
| Báo cáo khóa luận (full) | [`docs/bao-cao-khoa-luan.pdf`](docs/bao-cao-khoa-luan.pdf) |
| Slide bảo vệ | [`docs/slide-bao-ve.pptx`](docs/slide-bao-ve.pptx) |
| Sơ đồ ERD | [`docs/erd.png`](docs/erd.png) |
| Sơ đồ Use Case | [`docs/usecase.png`](docs/usecase.png) |
| Sơ đồ kiến trúc | [`docs/architecture.png`](docs/architecture.png) |
| Biểu đồ tuần tự | [`docs/sequence-diagrams/`](docs/sequence-diagrams/) |
| Script CSDL | [`DB.sql`](DB.sql) |

---

## 14. Lời cảm ơn

Em xin chân thành cảm ơn:

- **TS. Nguyễn Thị Ngọc Anh** — giảng viên hướng dẫn — đã tận tình định hướng, góp ý về cả mặt kỹ thuật lẫn học thuật trong suốt quá trình thực hiện đề tài.
- **Quý thầy cô Khoa Toán — Tin, Trường Đại học Sư phạm — Đại học Đà Nẵng** đã truyền đạt kiến thức nền tảng vững chắc về Công nghệ Thông tin, giúp em có đủ hành trang để hoàn thành đề tài này.
- **Gia đình, bạn bè và tập thể lớp 22CNTTC** đã luôn ủng hộ, đóng góp ý kiến trong quá trình phát triển và kiểm thử hệ thống.

Trong quá trình thực hiện không tránh khỏi những thiếu sót, em rất mong nhận được sự góp ý từ quý thầy cô và bạn đọc.

---

## 15. Trích dẫn

Nếu tham khảo mã nguồn hoặc kết quả nghiên cứu của đề tài này, vui lòng trích dẫn:

```bibtex
@thesis{maithanhhuyenb2026noncafe,
  author       = {Mai Thị Thanh Huyền},
  title        = {Nghiên cứu thiết kế và xây dựng Website Thương mại Điện tử Cho  Thương hiệu Cà Phê Nõn tích hợp Chatbot AI tư vấn khách hàng },
  school       = {Trường Đại học Sư phạm — Đại học Đà Nẵng},
  year         = {2026},
  type         = {Khóa luận tốt nghiệp đại học},
  address      = {Đà Nẵng, Việt Nam},
  note         = {GVHD: TS. Nguyễn Thị Ngọc Anh}
}
```

---

## 16. Liên hệ

| | |
|:---|:---|
| **Sinh viên** | Mai Thị Thanh Huyền |
| **Lớp** | 22CNTTC |
| **Khoa** | Toán — Tin |
| **Trường** | Đại học Sư phạm — Đại học Đà Nẵng |
| **GVHD** | TS. Nguyễn Thị Ngọc Anh |
| **Địa chỉ** | 459 Tôn Đức Thắng, Liên Chiểu, Đà Nẵng |

---

<div align="center">

*© 2026 — Khóa luận tốt nghiệp ngành Công nghệ Thông tin*  
*Khoa Toán — Tin · Trường Đại học Sư phạm · Đại học Đà Nẵng*  
*Mã nguồn được phát hành cho mục đích học thuật (Academic Use Only).*

</div>