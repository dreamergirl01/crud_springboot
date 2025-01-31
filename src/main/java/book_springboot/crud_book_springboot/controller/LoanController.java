package book_springboot.crud_book_springboot.controller;

import book_springboot.crud_book_springboot.entities.BookEntity;
import book_springboot.crud_book_springboot.entities.LoanEntity;
import book_springboot.crud_book_springboot.repository.BookRepository;
import book_springboot.crud_book_springboot.repository.LoanRepository;
import book_springboot.crud_book_springboot.request.LoanRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Book;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping(value = "/loan")
public class LoanController {

    @Autowired
    LoanRepository loanRepository;
    @Autowired
    private BookRepository bookRepository;

    //    add data
    @PostMapping(value = "addData")
    public ResponseEntity<BookEntity> addLoan(@RequestBody LoanRequest request) {

        Integer bookId = request.getBookId();
        String borrowerName = request.getBorrowerName();

        BookEntity book = bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Book Not Found"));

        LoanEntity loan = new LoanEntity();
        loan.setBorrowerName(borrowerName);
        loan.setBook(book);
        loan.setStatus("BORROWED");
        loan.setLoanDate(LocalDateTime.now());

        loanRepository.save(loan);
        return ResponseEntity.status(HttpStatus.CREATED).body(book);
    }
//    get data
    @GetMapping(value = "getAllData")
    public ResponseEntity<Iterable<LoanEntity>> getAllData() {
        return ResponseEntity.ok(loanRepository.findAll());
    }
}
