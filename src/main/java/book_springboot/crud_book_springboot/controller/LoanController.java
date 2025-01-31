package book_springboot.crud_book_springboot.controller;

import book_springboot.crud_book_springboot.entities.BookEntity;
import book_springboot.crud_book_springboot.entities.LoanEntity;
import book_springboot.crud_book_springboot.repository.BookRepository;
import book_springboot.crud_book_springboot.repository.LoanRepository;
import book_springboot.crud_book_springboot.request.LoanRequest;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.time.LocalDateTime;
import java.util.List;
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
//    get data, search and pagination
    @GetMapping(value = "getAllData")
    public ResponseEntity<Page<LoanEntity>> getAllData(
            @RequestParam(value = "keyword", required = false, defaultValue = "") String keyword,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {

        // Validasi input untuk halaman dan ukuran
        page = Math.max(0, page);
        size = Math.max(1, Math.min(size, 100));

        // Membuat Pageable dengan sorting berdasarkan loanDate (urutan menurun)
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "loanDate"));

        // Eksekusi pencarian dengan kata kunci
        Page<LoanEntity> loanEntityPage = loanRepository.findByBorrowerNameContainingIgnoreCase(
                keyword.trim(),
                pageable
        );

        return ResponseEntity.ok(loanEntityPage);
    }

//    update data
    @PostMapping(value = "updateData/{id}")
    public ResponseEntity<LoanEntity> updateData(@PathVariable("id") Integer id, @RequestBody LoanEntity loanRequest) {

        Optional<LoanEntity> loanOptional = loanRepository.findById(id);
        if (!loanOptional.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        LoanEntity loan = loanOptional.get();
        loan.setStatus("RETURNED");
        loan.setReturnDate(LocalDateTime.now());
        loanRepository.save(loan);
        return ResponseEntity.ok(loan);
    }
}
