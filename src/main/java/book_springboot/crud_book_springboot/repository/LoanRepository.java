package book_springboot.crud_book_springboot.repository;

import book_springboot.crud_book_springboot.entities.LoanEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoanRepository extends JpaRepository<LoanEntity, Integer> {
}
