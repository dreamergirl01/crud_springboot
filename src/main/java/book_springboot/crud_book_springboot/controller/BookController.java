package book_springboot.crud_book_springboot.controller;

import book_springboot.crud_book_springboot.entities.BookEntity;
import book_springboot.crud_book_springboot.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/book")
public class BookController {

    @Autowired
    BookRepository bookRepository;

    @GetMapping(value = "/checkAPI")
    public String checkAPI() {
        return "API is running";
    }

    @PostMapping(value = "addNewBook")
    public BookEntity addNewBook(@RequestBody BookEntity param) {
        bookRepository.save(param);
        return param;
    }
}
