# 📚 Library Management System (LMS)

> • This README explains the design choices and implementation details of the LMS project, including architecture, database design, CRUD operations, roles, security, and setup instructions.

---

### 🧑‍💻 Project Overview

* The LMS efficiently manages **books, authors, categories, members, system users, borrowing transactions, and activity logs**.
* Provides a secure **RESTful API** with **CRUD functionality** and **role-based access control**.
* Designed to help administrators and staff manage resources while ensuring members have a smooth borrowing experience.

---

### 🏗️ System Architecture

* **Controller Layer**: Handles HTTP requests and responses.
* **Service Layer**: Contains business logic, validation, and transactions.
* **Repository Layer**: Interacts with the database using **Spring Data JPA**.
* **Entity Layer**: Represents database tables via JPA annotations.

**Technologies & Tools**

---

### 🗄️ Database Design

**Entities & Relationships**

| Entity               | Fields                                                                                 | Relationships                                                                                                          |
| -------------------- | -------------------------------------------------------------------------------------- | ---------------------------------------------------------------------------------------------------------------------- |
| SystemUser           | id, username, email, password, role, lastLogin                                         | One-to-Many with UserActivityLog, One-to-Many with BorrowingTransaction (processed)                                    |
| Member               | id, name, email, phone, membershipDate                                                 | One-to-Many with BorrowingTransaction                                                                                  |
| Book                 | id, title, publicationYear, isbn, edition, summary, language, coverImageUrl, available | Many-to-Many with Author, Many-to-One with Publisher, Many-to-One with Category, One-to-Many with BorrowingTransaction |
| Author               | id, name                                                                               | Many-to-Many with Book                                                                                                 |
| Category             | id, name, parentCategory                                                               | One-to-Many with subCategories, One-to-Many with Book                                                                  |
| Publisher            | id, name                                                                               | One-to-Many with Book                                                                                                  |
| BorrowingTransaction | id, borrowDate, returnDate, returned                                                   | Many-to-One with Book, Many-to-One with Member, Many-to-One with SystemUser                                            |
| UserActivityLog      | id, action, details, timestamp                                                         | Many-to-One with SystemUser                                                                                            |

---

### 👥 Roles & Permissions

| Role          | Permissions                                           |
| ------------- | ----------------------------------------------------- |
| ADMINISTRATOR | Full access to all entities and operations            |
| LIBRARIAN     | Manage books, categories, borrow/return transactions  |
| STAFF         | Process borrow/return transactions and manage records |
| MEMBER        | Browse, borrow, and return books                      |

---

### 🔄 CRUD Operations

**Example: BookController**

```java
@RestController
@RequestMapping("/api/books")
public class BookController {
    private final BookService bookService;
    public BookController(BookService bookService) { this.bookService = bookService; }

    @GetMapping
    public List<Book> getAllBooks() { return bookService.findAllBooks(); }

    @PostMapping
    public Book addBook(@RequestBody Book book) { return bookService.saveBook(book); }

    @PutMapping("/{id}")
    public Book updateBook(@PathVariable Long id, @RequestBody Book book) { return bookService.updateBook(id, book); }

    @DeleteMapping("/{id}")
    public void deleteBook(@PathVariable Long id) { bookService.deleteBook(id); }
}
```

---

### 🔒 Security

* Role-based access control.
* Passwords are securely encrypted.
* Endpoints restricted by user roles.

---

### ⚙️ Setup & Execution

**Prerequisites:** Java 17+, Maven 3.6+, MySQL 8+

**Steps:**

1. Clone repository:

```bash
git clone https://github.com/Abd-Elrahman-hany-Rawash/Library-Management-System-Challenge.git
```

2. Import database schema:

```sql
source ProjectDatabaseSchema.sql;
```

3. Configure `application.properties`.
4. Run application:

```bash
mvn spring-boot:run
```

**Testing:**

* Use the provided Postman collection.
* Run unit tests with JUnit 5 and Spring Boot Test.

---

### 🚀 Future Enhancements

* JWT authentication
* Pagination and filtering
* Advanced search by author/category
* Enhanced ERD diagrams and documentation
