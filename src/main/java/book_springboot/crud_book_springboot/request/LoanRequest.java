package book_springboot.crud_book_springboot.request;

public class LoanRequest {

    private String borrowerName;
    private Integer bookId;

    public String getBorrowerName() {
        return borrowerName;
    }
    public void setBorrowerName(String borrowerName) {
        this.borrowerName = borrowerName;
    }
    public Integer getBookId() {
        return bookId;
    }
    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }
}
