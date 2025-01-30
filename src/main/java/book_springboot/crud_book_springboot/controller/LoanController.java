package book_springboot.crud_book_springboot.controller;

import book_springboot.crud_book_springboot.entities.BookEntity;
import book_springboot.crud_book_springboot.entities.LoanEntity;
import book_springboot.crud_book_springboot.entities.LoanStatus;
import book_springboot.crud_book_springboot.repository.BookRepository;
import book_springboot.crud_book_springboot.repository.LoanRepository;
import book_springboot.crud_book_springboot.request.LoanRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<LoanEntity> createLoan(@RequestBody LoanEntity param) {
//        ambil objek bookEntiti berdasarkan bookId yang diberikan
        Optional<BookEntity> book = bookRepository.findById(param.getBook().getId());
        if (!book.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(null);
        }

        //buat objek LoanEntiti baru
        LoanEntity loan = new LoanEntity();
        loan.setBorrowerName(param.getBorrowerName());
        loan.setBook(book.get());
        loan.setStatus(param.getStatus());
        loan.setLoanDate(param.getLoanDate());
        loan.setReturnDate(param.getReturnDate());

        // Simpan dan kembalikan respons
        LoanEntity createdLoan = loanRepository.save(loan);
        return new ResponseEntity<>(createdLoan, HttpStatus.CREATED);
    }

//    get data
    @GetMapping(value = "getAllData")
    public ResponseEntity<Iterable<LoanEntity>> getAllData() {
        return ResponseEntity.ok(loanRepository.findAll());
    }
}
