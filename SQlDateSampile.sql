USE library_db;

/*
SET FOREIGN_KEY_CHECKS = 0;

TRUNCATE TABLE book_authors;
TRUNCATE TABLE books;
TRUNCATE TABLE members;
TRUNCATE TABLE authors;
TRUNCATE TABLE categories;
TRUNCATE TABLE publishers;
TRUNCATE TABLE borrowing_transactions;
TRUNCATE TABLE system_users;
TRUNCATE TABLE user_activity_log;

SET FOREIGN_KEY_CHECKS = 1
*/
INSERT INTO authors (name) VALUES 
('George Orwell'),
('J.K. Rowling'),
('Stephen King'),
('Agatha Christie');

INSERT INTO publishers (name) VALUES 
('Penguin Books'),
('HarperCollins'),
('Bloomsbury'),
('Random House');


INSERT INTO categories (name) VALUES 
('Fiction'),
('Science Fiction'),
('Fantasy'),
('Mystery'),
('Non-Fiction');

INSERT INTO books (title, publication_year, isbn, edition, summary, language, publisher_id, category_id,available)
VALUES
('1984', 1949, '9780451524935', '1st', 'Dystopian novel by George Orwell.', 'English', 1, 2,True),
('Harry Potter and the Philosopher''s Stone', 1997, '9780747532699', '1st', 'First book of Harry Potter series.', 'English', 3, 3,True),
('The Shining', 1977, '9780307743657', '1st', 'Horror novel by Stephen King.', 'English', 2, 1,True),
('Murder on the Orient Express', 1934, '9780007119318', '1st', 'Detective novel by Agatha Christie.', 'English', 4, 4,True);



INSERT INTO book_authors (book_id, author_id) VALUES
(1, 1),
(2, 2),
(3, 3),
(4, 4);


INSERT INTO members (name, email, phone, membership_date) VALUES
('Alice Johnson', 'alice@example.com', '1234567890', '2022-01-15'),
('Bob Smith', 'bob@example.com', '0987654321', '2023-02-20');

-- password is admin123   lib123  staff123
INSERT INTO system_users (username, password, email, role, last_login) VALUES
('admin1',    '$2a$10$hP3EoQ6L8PpdywOCiN5Qv.xkqY7uF0Gc0rVvV4QYx3hEwVpQxYqM2', 'admin@example.com', 'ADMINISTRATOR', NOW()),
('librarian1','$2a$10$kQ2kY3WfsQb6tzdpTUNgPu1YfMGQUBsHQYZK/Z9sMZq/jSKTtwMKa', 'librarian@example.com', 'LIBRARIAN', NOW()),
('staff1',    '$2a$10$EqZlrg2vwQXGf7MskfXzBuWcztN58k6vBfOfHPHgXoKj0VsVdMCle', 'staff@example.com', 'STAFF', NOW());


INSERT INTO borrowing_transactions (book_id, member_id, processed_by_user_id, borrow_date, return_date, returned)
VALUES
(1, 1, 2, '2024-05-01', '2024-05-15', TRUE),
(2, 2, 3, '2024-06-10', NULL, FALSE);
