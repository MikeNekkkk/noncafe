CREATE DATABASE cafe;
USE cafe;

SET FOREIGN_KEY_CHECKS = 0;

CREATE TABLE category (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    status VARCHAR(20) NOT NULL DEFAULT 'ACTIVE'
);

CREATE TABLE product (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    category_id BIGINT NOT NULL,
    name VARCHAR(255) NOT NULL,
    price DECIMAL(10,2) NOT NULL,
    pricein DECIMAL(10,2) NOT NULL,
    detail_description TEXT,
    image_url VARCHAR(255) NOT NULL,
    stock_quantity INT NOT NULL,
    reserved INT DEFAULT 0,
    weight_gram INT NOT NULL DEFAULT 250,
    status VARCHAR(20) NOT NULL DEFAULT 'ACTIVE',

    FOREIGN KEY (category_id) REFERENCES category(id)
);

CREATE TABLE product_profile (
    product_id BIGINT PRIMARY KEY,
    flavor_intensity INT,
    bitterness_level INT,
    acidity_level INT,
    sweetness_level INT,
    roast_level VARCHAR(20),
    caffeine_level VARCHAR(20),
    taste_notes VARCHAR(255),
    suitable_for VARCHAR(100),
    recommended_time VARCHAR(100),
    grind_size VARCHAR(20),
    processing_method VARCHAR(20),
    brew_methods VARCHAR(255),

    FOREIGN KEY (product_id) REFERENCES product(id) ON DELETE CASCADE
);

CREATE TABLE users (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(150) UNIQUE NOT NULL,
    full_name VARCHAR(200) NOT NULL,
    phone VARCHAR(20),
    address VARCHAR(500),
    role VARCHAR(20) DEFAULT 'USER',
    status VARCHAR(20) DEFAULT 'ACTIVE',
    reset_token VARCHAR(64),

    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE orders (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT,

    first_name VARCHAR(100),
    last_name VARCHAR(100),
    address VARCHAR(500),
    city VARCHAR(100),
    state VARCHAR(50),
    ward VARCHAR(100),
    district VARCHAR(50),

    email VARCHAR(150),
    phone VARCHAR(20),

    total_amount DECIMAL(15,2),
    ship_fee DECIMAL(15,2) DEFAULT 0,
    ghn_order_code VARCHAR(100),
    ghn_district_id INT,
    ghn_ward_code VARCHAR(20),

    status VARCHAR(20) DEFAULT 'PENDING',
    payment_method VARCHAR(50),

    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE SET NULL
);

CREATE TABLE order_items (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    order_id BIGINT,
    product_id BIGINT,

    product_name VARCHAR(200),
    price DECIMAL(15,2),
    pricein DECIMAL(15,2),
    quantity INT,
    subtotal DECIMAL(15,2),

    FOREIGN KEY (order_id) REFERENCES orders(id) ON DELETE CASCADE
);

CREATE TABLE payment_transactions (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    order_id BIGINT,
    payment_method VARCHAR(50),
    amount DECIMAL(15,2),
    status VARCHAR(20),
    transaction_id VARCHAR(200),

    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    FOREIGN KEY (order_id) REFERENCES orders(id) ON DELETE CASCADE
);

CREATE TABLE blog (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(500),
    slug VARCHAR(500) UNIQUE,
    thumbnail_url VARCHAR(500),
    content LONGTEXT,
    excerpt TEXT,
    author_id BIGINT,
    author_name VARCHAR(200),
    category VARCHAR(100),
    view_count INT DEFAULT 0,
    statusblog VARCHAR(20) DEFAULT 'PUBLISHED',

    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    FOREIGN KEY (author_id) REFERENCES users(id) ON DELETE SET NULL
);

CREATE TABLE contacts (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT,
    name VARCHAR(200),
    email VARCHAR(150),
    subject VARCHAR(500),
    message TEXT,
    status VARCHAR(20) DEFAULT 'NEW',

    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE SET NULL
);

-- ==================== CHATBOT ====================
CREATE TABLE chatbot_history (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    session_id VARCHAR(255),
    user_id BIGINT,
    user_message TEXT,
    bot_response TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE stock_movement_log (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    product_id BIGINT,
    order_id BIGINT,
    movement_type ENUM('IN','OUT'),
    quantity INT,
    note TEXT,
    created_by VARCHAR(100),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    FOREIGN KEY (product_id) REFERENCES product(id),
    FOREIGN KEY (order_id) REFERENCES orders(id)
);

CREATE TABLE order_tracking_log (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    order_id BIGINT,
    status_code VARCHAR(50),
    status_name VARCHAR(200),
    location VARCHAR(300),
    note TEXT,
    updated_by VARCHAR(50) DEFAULT 'SYSTEM',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    FOREIGN KEY (order_id) REFERENCES orders(id)
);

SET FOREIGN_KEY_CHECKS = 1;

-- CATEGORY
INSERT INTO category (name) VALUES 
('Cà phê Hòa tan'),
('Cà phê Rang xay'),
('Cà phê Hạt'),
('Sản phẩm mới');

-- USER
INSERT INTO users (username, password, email, full_name, role) VALUES
('admin', '0192023a7bbd73250516f069df18b500', 'admin@cafe.com', 'Administrator', 'ADMIN'),
('user1', '482c811da5d5b4bc6d497ffa98491e38', 'user1@gmail.com', 'Nguyễn Văn A', 'USER');


INSERT INTO product (category_id, name, price, pricein, stock_quantity)
VALUES
(1, 'Cà phê hòa tan G7', 78000, 50000, 100),
(2, 'Cà phê rang xay truyền thống', 120000, 90000, 50);

-- NCC
INSERT INTO NCC (nameKho, nameQuanKho, phone, address) VALUES
('Kho Sơn Trà', 'Phạm Đức', '0905556669', 'Đà Nẵng');