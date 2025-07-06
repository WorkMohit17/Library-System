# 📚 Library Management System (Spring Boot)

This is a simple Library Management System built using **Spring Boot**, designed to manage **Authors** and their **Books**. It demonstrates the use of RESTful APIs, DTO mapping, Entity Relationships (One-to-Many), Exception Handling, and basic Git project structure.

---

## 📌 Features

- 🔍 Get all authors / books
- 🧑 Add, update, delete authors
- 📘 Add, update, delete books
- 🔗 Map books to authors
- 🔄 Change the author of a book
- 🔍 Fetch all books by an author
- 🔍 Get author info from a book
- ✅ Exception handling with meaningful messages
- 🛠 DTOs and Entity Mappers for clean separation

---

## 🏗️ Tech Stack

- Java 17+
- Spring Boot
- Spring Web
- Spring Data JPA (Hibernate)
- H2 / MySQL (configurable)
- Maven
- Lombok

---

## 🔗 API Endpoints

### 🔹 Author APIs

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/author/` | Get all authors |
| GET | `/author/{id}` | Get author by ID |
| POST | `/author/` | Create a new author |
| PUT | `/author/{id}` | Update author |
| DELETE | `/author/{id}` | Delete author |
| GET | `/author/{authorId}/books` | Get books by author |
| POST | `/author/{id}/add-book` | Add book to author |
| DELETE | `/author/{authorId}/book/{bookId}` | Remove book from author |

### 🔹 Book APIs

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/book/` | Get all books |
| GET | `/book/{id}` | Get book by ID |
| POST | `/book/` | Create a new book |
| PUT | `/book/{id}` | Update book |
| DELETE | `/book/{id}` | Delete book |
| PATCH | `/book/{bookId}/change-author?newAuthorId={id}` | Change book's author |
| GET | `/book/{bookId}/author` | Get author by book ID |

---

## 🔧 Setup Instructions

### 1. Clone the repository
```bash
git clone https://github.com/WorkMohit17/Library-System.git
cd Library-System```

```
### 2. Import Project into IDE
```bash
Open IntelliJ, VS Code, or Eclipse
Import as a Maven project
```

### 3. Build and Run
```
mvn clean install
mvn spring-boot:run
```

### 4. Test the APIs
```
Use Postman or curl to test the endpoints (see list above).
```


### 🗃️ Sample Data (Optional)
Add this in src/main/resources/data.sql:
```
INSERT INTO author_table (id, name, bio) VALUES (1, 'Peter Thiel', 'Founder of PayPal, author of Zero to One');
INSERT INTO book_table (id, title, publish_date, author_id) VALUES (1, 'Zero to One', '2014-09-16', 1);
```

### ❗ Error Response Format
```
{
  "timestamp": "2025-07-06T18:00:00",
  "status": 404,
  "error": "Not Found",
  "message": "Author with id: 100 does not exist"
}
```

### ✅ Future Enhancements
- API documentation with Swagger
- Unit & integration testing
- Pagination for /books and /authors
- Search & filtering support
- JWT-based authentication



## 🧑‍💻 Author
**Mohit Bansal**  
🔗 [GitHub](https://github.com/WorkMohit17) | 💼 [LinkedIn](https://www.linkedin.com/in/workmohit17/)



### 📜 License
 This project is open source and available under the MIT License.
