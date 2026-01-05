DROP DATABASE IF EXISTS pizzeria;

CREATE DATABASE pizzeria CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE pizzeria;



CREATE TABLE users(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    full_name VARCHAR(100) NOT NULL,
    phone VARCHAR(20),
    address VARCHAR(255),
    profile_image_url VARCHAR(255),
    enabled BOOLEAN DEFAULT TRUE,
    created_at DATETIME NOT NULL,
    updated_at DATETIME NOT NULL
);



CREATE TABLE roles(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE,
    description VARCHAR(255)
);



CREATE TABLE user_roles(
    user_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,
    PRIMARY KEY(user_id,role_id),
    CONSTRAINT fk_user_roles_user FOREIGN KEY(user_id) REFERENCES users(id),
    CONSTRAINT fk_user_roles_role FOREIGN KEY(role_id) REFERENCES roles(id)
);



CREATE TABLE pizzas(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    slug VARCHAR(120) NOT NULL UNIQUE,
    description TEXT,
    image_url VARCHAR(255),
    active BOOLEAN DEFAULT TRUE,
    created_at DATETIME NOT NULL,
    updated_at DATETIME NOT NULL
);



CREATE TABLE pizza_sizes(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    pizza_id BIGINT NOT NULL,
    size_label VARCHAR(10) NOT NULL,
    diameter_cm INT NOT NULL,
    price DECIMAL(8,2) NOT NULL,
    active BOOLEAN DEFAULT TRUE,
    CONSTRAINT fk_sizes_pizza FOREIGN KEY(pizza_id) REFERENCES pizzas(id)
);



CREATE TABLE ingredients(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    slug VARCHAR(120) UNIQUE,
    description TEXT,
    extra_price DECIMAL(6,2) DEFAULT 0,
    active BOOLEAN DEFAULT TRUE
);



CREATE TABLE pizza_ingredients(
    pizza_id BIGINT NOT NULL,
    ingredient_id BIGINT NOT NULL,
    PRIMARY KEY(pizza_id,ingredient_id),
    CONSTRAINT fk_pi_pizza FOREIGN KEY(pizza_id) REFERENCES pizzas(id),
    CONSTRAINT fk_pi_ingredient FOREIGN KEY(ingredient_id) REFERENCES ingredients(id)
);



CREATE TABLE tags(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    slug VARCHAR(60) NOT NULL UNIQUE,
    description VARCHAR(255)
);



CREATE TABLE pizza_tags(
    pizza_id BIGINT NOT NULL,
    tag_id BIGINT NOT NULL,
    PRIMARY KEY(pizza_id,tag_id),
    CONSTRAINT fk_pt_pizza FOREIGN KEY(pizza_id) REFERENCES pizzas(id),
    CONSTRAINT fk_pt_tag FOREIGN KEY(tag_id) REFERENCES tags(id)
);



CREATE TABLE orders(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    code VARCHAR(50) NOT NULL UNIQUE,
    customer_id BIGINT NOT NULL,
    status VARCHAR(20) NOT NULL,
    total_price DECIMAL(10,2) NOT NULL,
    delivery_address VARCHAR(255) NOT NULL,
    note VARCHAR(255),
    assigned_cook_id BIGINT,
    assigned_courier_id BIGINT,
    created_at DATETIME NOT NULL,
    updated_at DATETIME NOT NULL,
    CONSTRAINT fk_orders_customer FOREIGN KEY(customer_id) REFERENCES users(id),
    CONSTRAINT fk_orders_cook FOREIGN KEY(assigned_cook_id) REFERENCES users(id),
    CONSTRAINT fk_orders_courier FOREIGN KEY(assigned_courier_id) REFERENCES users(id)
);



CREATE TABLE order_items(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    order_id BIGINT NOT NULL,
    pizza_size_id BIGINT,
    quantity INT NOT NULL,
    unit_price DECIMAL(8,2) NOT NULL,
    pizza_name_snapshot VARCHAR(100) NOT NULL,
    size_label_snapshot VARCHAR(10) NOT NULL,
    ingredients_snapshot TEXT,
    image_url_snapshot VARCHAR(255),
    CONSTRAINT fk_order_items_order FOREIGN KEY(order_id) REFERENCES orders(id),
    CONSTRAINT fk_order_items_size FOREIGN KEY(pizza_size_id) REFERENCES pizza_sizes(id)
);

