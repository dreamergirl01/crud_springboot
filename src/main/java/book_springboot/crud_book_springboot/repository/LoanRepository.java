package book_springboot.crud_book_springboot.repository;

import book_springboot.crud_book_springboot.entities.LoanEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoanRepository extends JpaRepository<LoanEntity, Integer> {
    Page<LoanEntity>findByBorrowerNameContainingIgnoreCase(String borrowerName, Pageable pageable);
}
