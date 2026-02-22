-- =============================================
-- Agriculture Farm Management System - Schema
-- =============================================

-- Users Table
CREATE TABLE IF NOT EXISTS users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    full_name VARCHAR(100),
    role ENUM('ADMIN', 'MANAGER', 'WORKER') NOT NULL DEFAULT 'WORKER',
    is_active BOOLEAN DEFAULT TRUE,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP
);

-- Farms Table
CREATE TABLE IF NOT EXISTS farms (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    location VARCHAR(255),
    total_area DOUBLE,
    description VARCHAR(500),
    owner_id BIGINT NOT NULL,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (owner_id) REFERENCES users (id) ON DELETE CASCADE
);

-- Crops Table
CREATE TABLE IF NOT EXISTS crops (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    type VARCHAR(100),
    sowing_date DATE,
    harvest_date DATE,
    field_location VARCHAR(200),
    status ENUM(
        'SOWING',
        'GROWING',
        'FLOWERING',
        'HARVESTING',
        'HARVESTED',
        'FAILED'
    ) NOT NULL DEFAULT 'SOWING',
    growth_stage VARCHAR(100),
    expected_yield DOUBLE,
    actual_yield DOUBLE,
    notes VARCHAR(500),
    farm_id BIGINT NOT NULL,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (farm_id) REFERENCES farms (id) ON DELETE CASCADE
);

-- Fertilizer Logs Table
CREATE TABLE IF NOT EXISTS fertilizer_logs (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    crop_id BIGINT NOT NULL,
    type VARCHAR(100) NOT NULL,
    product_name VARCHAR(100),
    amount DOUBLE,
    unit VARCHAR(20),
    applied_date DATE,
    notes VARCHAR(500),
    FOREIGN KEY (crop_id) REFERENCES crops (id) ON DELETE CASCADE
);

-- Livestock Table
CREATE TABLE IF NOT EXISTS livestock (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    type VARCHAR(50) NOT NULL,
    breed VARCHAR(100),
    gender VARCHAR(20),
    age INT,
    health_status VARCHAR(50) DEFAULT 'Healthy',
    tag_number VARCHAR(50) UNIQUE,
    weight DOUBLE,
    notes VARCHAR(500),
    farm_id BIGINT NOT NULL,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (farm_id) REFERENCES farms (id) ON DELETE CASCADE
);

-- Medical Records Table
CREATE TABLE IF NOT EXISTS medical_records (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    livestock_id BIGINT NOT NULL,
    description VARCHAR(200) NOT NULL,
    diagnosis VARCHAR(200),
    treatment VARCHAR(200),
    vet_name VARCHAR(100),
    treatment_date DATE,
    cost DOUBLE,
    notes VARCHAR(500),
    FOREIGN KEY (livestock_id) REFERENCES livestock (id) ON DELETE CASCADE
);

-- Vaccination Records Table
CREATE TABLE IF NOT EXISTS vaccination_records (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    livestock_id BIGINT NOT NULL,
    vaccine_name VARCHAR(150) NOT NULL,
    vaccination_date DATE,
    next_due_date DATE,
    administered_by VARCHAR(100),
    notes VARCHAR(500),
    FOREIGN KEY (livestock_id) REFERENCES livestock (id) ON DELETE CASCADE
);

-- Inventory Items Table
CREATE TABLE IF NOT EXISTS inventory_items (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(150) NOT NULL,
    category VARCHAR(100),
    quantity DOUBLE,
    unit VARCHAR(20),
    min_stock_level DOUBLE,
    unit_price DOUBLE,
    supplier VARCHAR(100),
    description VARCHAR(500),
    farm_id BIGINT NOT NULL,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (farm_id) REFERENCES farms (id) ON DELETE CASCADE
);

-- Workers Table
CREATE TABLE IF NOT EXISTS workers (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    role VARCHAR(100),
    phone VARCHAR(15),
    email VARCHAR(100),
    address VARCHAR(255),
    salary DOUBLE,
    join_date DATETIME DEFAULT CURRENT_TIMESTAMP,
    is_active BOOLEAN DEFAULT TRUE,
    farm_id BIGINT NOT NULL,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (farm_id) REFERENCES farms (id) ON DELETE CASCADE
);

-- Attendance Table
CREATE TABLE IF NOT EXISTS attendance (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    worker_id BIGINT NOT NULL,
    date DATE NOT NULL,
    status VARCHAR(20) NOT NULL DEFAULT 'PRESENT',
    notes VARCHAR(200),
    FOREIGN KEY (worker_id) REFERENCES workers (id) ON DELETE CASCADE
);

-- Tasks Table
CREATE TABLE IF NOT EXISTS tasks (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(200) NOT NULL,
    description VARCHAR(1000),
    assigned_to BIGINT,
    status ENUM(
        'PENDING',
        'IN_PROGRESS',
        'COMPLETED',
        'CANCELLED'
    ) NOT NULL DEFAULT 'PENDING',
    due_date DATE,
    priority VARCHAR(20) DEFAULT 'MEDIUM',
    farm_id BIGINT NOT NULL,
    created_by BIGINT,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    completed_at DATETIME,
    FOREIGN KEY (assigned_to) REFERENCES workers (id) ON DELETE SET NULL,
    FOREIGN KEY (farm_id) REFERENCES farms (id) ON DELETE CASCADE,
    FOREIGN KEY (created_by) REFERENCES users (id) ON DELETE SET NULL
);

-- Expenses Table
CREATE TABLE IF NOT EXISTS expenses (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    category VARCHAR(100) NOT NULL,
    amount DOUBLE,
    expense_date DATE DEFAULT(CURRENT_DATE),
    description VARCHAR(500),
    payment_method VARCHAR(100),
    farm_id BIGINT NOT NULL,
    recorded_by BIGINT,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (farm_id) REFERENCES farms (id) ON DELETE CASCADE,
    FOREIGN KEY (recorded_by) REFERENCES users (id) ON DELETE SET NULL
);

-- Sales Table
CREATE TABLE IF NOT EXISTS sales (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    product VARCHAR(150) NOT NULL,
    product_type VARCHAR(50),
    quantity DOUBLE,
    unit VARCHAR(20),
    unit_price DOUBLE,
    total_amount DOUBLE,
    buyer_name VARCHAR(100),
    sale_date DATE DEFAULT(CURRENT_DATE),
    notes VARCHAR(500),
    farm_id BIGINT NOT NULL,
    recorded_by BIGINT,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (farm_id) REFERENCES farms (id) ON DELETE CASCADE,
    FOREIGN KEY (recorded_by) REFERENCES users (id) ON DELETE SET NULL
);

-- =============================================
-- SEED DATA
-- =============================================
-- Default admin user (password: admin123 - BCrypt encoded)
INSERT IGNORE INTO
    users (
        username,
        password,
        email,
        full_name,
        role,
        is_active
    )
VALUES (
        'admin',
        '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBpwTpyL5BqEoS',
        'admin@farm.com',
        'Farm Administrator',
        'ADMIN',
        TRUE
    );

-- Default farm
INSERT IGNORE INTO
    farms (
        name,
        location,
        total_area,
        description,
        owner_id
    )
VALUES (
        'Green Valley Farm',
        'Karnataka, India',
        50.5,
        'Main farm operations center',
        1
    );

-- Sample inventory items
INSERT IGNORE INTO
    inventory_items (
        name,
        category,
        quantity,
        unit,
        min_stock_level,
        unit_price,
        farm_id
    )
VALUES (
        'Wheat Seeds',
        'Seeds',
        500,
        'kg',
        100,
        45.00,
        1
    ),
    (
        'Urea Fertilizer',
        'Fertilizers',
        200,
        'kg',
        50,
        25.00,
        1
    ),
    (
        'Sprayer 20L',
        'Tools',
        5,
        'pieces',
        2,
        1500.00,
        1
    ),
    (
        'Pesticide Alpha',
        'Pesticides',
        30,
        'liters',
        10,
        350.00,
        1
    );

-- Sample crop
INSERT IGNORE INTO
    crops (
        name,
        type,
        sowing_date,
        harvest_date,
        field_location,
        status,
        growth_stage,
        farm_id
    )
VALUES (
        'Wheat Season 2024',
        'Grain',
        '2024-11-01',
        '2025-03-15',
        'Field A - North',
        'GROWING',
        'Tillering',
        1
    );

-- Sample livestock
INSERT IGNORE INTO
    livestock (
        type,
        breed,
        gender,
        age,
        health_status,
        tag_number,
        weight,
        farm_id
    )
VALUES (
        'Cow',
        'Holstein',
        'Female',
        36,
        'Healthy',
        'COW-001',
        450.0,
        1
    ),
    (
        'Goat',
        'Boer',
        'Male',
        24,
        'Healthy',
        'GOAT-001',
        65.0,
        1
    );