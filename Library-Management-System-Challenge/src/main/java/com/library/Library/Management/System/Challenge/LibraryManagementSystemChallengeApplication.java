package com.library.Library.Management.System.Challenge;

import com.library.Library.Management.System.Challenge.entity.*;
import com.library.Library.Management.System.Challenge.service.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;

@SpringBootApplication
public class LibraryManagementSystemChallengeApplication {

	public static void main(String[] args) {
		SpringApplication.run(LibraryManagementSystemChallengeApplication.class, args);
	}


	@Bean
	CommandLineRunner demoData(
			SystemUserService userService,
			AuthorService authorService,
			PublisherService publisherService,
			CategoryService categoryService,
			BookService bookService,
			MemberService memberService,
			BorrowingTransactionService transactionService,
			PasswordEncoder encoder
	) {
		return args -> {
/*
			// ---------------- Users ----------------
			SystemUser admin = new SystemUser();
			admin.setUsername("admin");
			admin.setPassword("admin123");
			admin.setEmail("admin@example.com");
			admin.setRole(UserRole.ADMINISTRATOR);
			admin.setLastLogin(LocalDateTime.now());
			userService.saveUser(admin, admin);

			SystemUser librarian = new SystemUser();
			librarian.setUsername("librarian");
			librarian.setPassword("lib123");
			librarian.setEmail("librarian@example.com");
			librarian.setRole(UserRole.LIBRARIAN);
			librarian.setLastLogin(LocalDateTime.now());
			userService.saveUser(librarian, admin);

			// ---------------- Authors ----------------
			Author author1 = new Author();
			author1.setName("George Orwell");
			authorService.saveAuthor(author1);

			Author author2 = new Author();
			author2.setName("J.K. Rowling");
			authorService.saveAuthor(author2);

			// ---------------- Publishers ----------------
			Publisher publisher1 = new Publisher();
			publisher1.setName("Penguin Books");
			publisherService.savePublisher(publisher1);

			Publisher publisher2 = new Publisher();
			publisher2.setName("Bloomsbury");
			publisherService.savePublisher(publisher2);

			// ---------------- Categories ----------------
			Category category1 = new Category();
			category1.setName("Fiction");
			categoryService.saveCategory(category1);

			Category category2 = new Category();
			category2.setName("Fantasy");
			categoryService.saveCategory(category2);

			// ---------------- Books ----------------
			Book book1 = new Book();
			book1.setTitle("1984");
			book1.setIsbn("9780451524935");
			book1.setEdition("1st");
			book1.setPublicationYear(1949);
			book1.setAvailable(true);
			book1.setAuthors(new HashSet<>(Arrays.asList(author1)));
			book1.setCategory(category1);
			bookService.saveBook(book1);

			Book book2 = new Book();
			book2.setTitle("Harry Potter and the Philosopher's Stone");
			book2.setIsbn("9780747532699");
			book2.setEdition("1st");
			book2.setPublicationYear(1997);
			book2.setAvailable(true);
			book2.setAuthors(new HashSet<>(Arrays.asList(author2)));
			book2.setPublisher(publisher2);
			book2.setCategory(category2);
			bookService.saveBook(book2);

			// ---------------- Members ----------------
			Member member1 = new Member();
			member1.setName("Alice Johnson");
			member1.setEmail("alice@example.com");
			member1.setPhone("1234567890");
			member1.setMembershipDate(LocalDate.now().minusYears(1));
			memberService.saveMember(member1);

			Member member2 = new Member();
			member2.setName("Bob Smith");
			member2.setEmail("bob@example.com");
			member2.setPhone("0987654321");
			member2.setMembershipDate(LocalDate.now().minusMonths(6));
			memberService.saveMember(member2);

			// ---------------- Borrowing Transactions ----------------
			BorrowingTransaction transaction1 = new BorrowingTransaction();
			transaction1.setBook(book1);
			transaction1.setBorrowDate(LocalDate.now().minusDays(2));
			transactionService.borrowBook(transaction1, member1.getId());

			BorrowingTransaction transaction2 = new BorrowingTransaction();
			transaction2.setBook(book2);
			transaction2.setBorrowDate(LocalDate.now().minusDays(1));
			transactionService.borrowBook(transaction2, member2.getId());
*/
			System.out.println("✅ Demo data initialized successfully!");
		};
	}
}
