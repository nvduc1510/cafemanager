-- role --
CREATE TABLE IF NOT EXISTS `user_role` (
    `user_role_id` BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `user_role_name` VARCHAR(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

# -- User --
CREATE TABLE IF NOT EXISTS `user` (
    `user_id` INT AUTO_INCREMENT NOT NULL PRIMARY KEY ,
    `user_name` VARCHAR(255) NOT NULL,
    `password` VARCHAR(255) NULL,
    `email` VARCHAR(255) NOT NULL,
    `user_full_name` VARCHAR(255) NOT NULL,
    `user_sex` VARCHAR(10) NULL,
    `user_birthdate` DATE NULL,
    `user_address` VARCHAR(125),
    `user_image` VARCHAR(255),
    `user_role_id` INT NOT NULL,
    FOREIGN KEY (`user_role_id`) REFERENCES `user_role`(`user_role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


-- shift (ca làm việc) --
CREATE TABLE IF NOT EXISTS `shift` (
    shift_id INT AUTO_INCREMENT NOT NULL PRIMARY KEY,
    user_id INT NOT NULL,
    time_start DATE NOT NULL,
    time_end DATE NOT NULL,
    text_note VARCHAR(255),
    FOREIGN KEY (user_id) REFERENCES `user`(user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Pay statement (Bảng lương) --
CREATE TABLE IF NOT EXISTS `pay_statement` (
    pay_statement_id INT AUTO_INCREMENT NOT NULL PRIMARY KEY,
    user_id INT NOT NULL,
    shift_id INT NOT NULL,
    total_salary INT NOT NULL,
    date_created DATE NOT NULL,
    FOREIGN KEY (user_id) REFERENCES `user`(user_id),
    FOREIGN KEY (shift_id) REFERENCES `shift`(shift_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Product (Sản phẩm) --
CREATE TABLE IF NOT EXISTS `product` (
    product_id INT AUTO_INCREMENT PRIMARY KEY,
    product_name VARCHAR(100) NOT NULL,
    product_price INT NOT NULL,
    product_image VARCHAR(255)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Collections (Loại hàng) --
CREATE TABLE IF NOT EXISTS `collections` (
    collection_id INT AUTO_INCREMENT PRIMARY KEY,
    collection_name VARCHAR(255) NOT NULL,
    collection_description VARCHAR(255) NOT NULL,
    product_id INT NOT NULL,
    FOREIGN KEY (product_id) REFERENCES product(product_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Detail selling (Chi tiết bán hàng) --
CREATE TABLE IF NOT EXISTS `detail_selling` (
    selling_id INT AUTO_INCREMENT PRIMARY KEY,
    bill_code INT NOT NULL,
    product_id INT NOT NULL,
    quantity INT NOT NULL,
    total_amount INT,
    FOREIGN KEY (bill_code) REFERENCES `bill`(bill_code),
    FOREIGN KEY (product_id) REFERENCES `product`(product_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- bill --
CREATE TABLE IF NOT EXISTS `bill` (
    bill_code INT AUTO_INCREMENT PRIMARY KEY,
    date_start DATE,
    total_amount INT,
    user_id INT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES `user`(user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


--  Data user_role
INSERT INTO user_role VALUES (1, 'Admin');
INSERT INTO user_role VALUES (2, 'Member');

-- Data user
INSERT INTO user VALUES (1, 'admin', '$2a$10$r.XIN4K9vTioiuYQwaTop.UVQ5r5FvrKk2V5Orm9Hc6n4i9Tvjthy', 'admin@gmail.com', 'admin', 'Name', '2000-10-10','a','a',1);
INSERT INTO user VALUES (2, 'a', '$2a$10$r.XIN4K9vTioiuYQwaTop.UVQ5r5FvrKk2V5Orm9Hc6n4i9Tvjthy', 'a@gmail.com', 'a', 'Name', '2000-10-10','a','a',2);
