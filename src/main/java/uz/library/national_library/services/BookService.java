package uz.library.national_library.services;

import org.springframework.stereotype.Service;
import uz.library.national_library.models.Book;
import uz.library.national_library.models.BookCategory;
import uz.library.national_library.repositories.BookRepository;
import uz.library.national_library.repositories.FileStorageRepository;

import java.util.List;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final FileService fileService;
    private final FileStorageRepository fileStorageRepository;

    public BookService(BookRepository bookRepository, FileService fileService, FileStorageRepository fileStorageRepository) {
        this.bookRepository = bookRepository;
        this.fileService = fileService;
        this.fileStorageRepository = fileStorageRepository;
    }

    public Book saveBook(Book book) {
        return bookRepository.save(book);
    }

    public String deleteBook(Long bookId) {
        try {
            if (!bookRepository.existsById(bookId))
                return "Bunday kitob mavjud emas !";

            var book = bookRepository.findBookById(bookId);
            var fileStorage = book.getFileStorage();
            var imageStorage = book.getImageStorage();

            bookRepository.deleteById(bookId);
            fileService.clearMemory(fileStorage);
            fileService.clearMemory(imageStorage);

            return book.getName() + "  muvofaqqiyatli o'chirildi.";
        } catch (Exception e) {
            return "Kitobni o'chirishda xatolik yuz berdi !";
        }
    }

    public List<Book> findAllBooksByName(String name) {
        if (name == null || name.equals(""))
            return bookRepository.findAllBook();

        return bookRepository.findAllByNameContainingIgnoreCaseOrderByDownloadCountDesc(name);
    }

    public List<Book> findAllBookOfAuthor(String authorName) {
        return bookRepository.findBookByAuthor(authorName);
    }

    public Book findBookWithId(Long id) {
        return bookRepository.findBookById(id);
    }

    public Book update(Book book) {
        return bookRepository.save(book);
    }

    public List<Book> findAllBooks() {
        return bookRepository.findAllBook();

    }

    public List<Book> findAllBookByCategory(String bookCategoryName) {
        var newName = bookCategoryName.toUpperCase();
        var bookCategory = BookCategory.MATEMATIKA;

        switch (newName) {

            case "MATEMATIKA":
                bookCategory = BookCategory.MATEMATIKA;
                break;

            case "DASTURLASH":
                bookCategory = BookCategory.DASTURLASH;
                break;
            case "FIZIKA":
                bookCategory = BookCategory.FIZIKA;
                break;
            case "KIMYO":
                bookCategory = BookCategory.KIMYO;
                break;
            case "BIOLOGIYA":
                bookCategory = BookCategory.BIOLOGIYA;
                break;
            case "IJTIMOIY":
                bookCategory = BookCategory.IJTIMOIY;
                break;
            case "TARIX":
                bookCategory = BookCategory.TARIX;
                break;
            case "GEOGRAFIYA":
                bookCategory = BookCategory.GEOGRAFIYA;
                break;
            case "XORIJIY_TILLAR":
                bookCategory = BookCategory.XORIJIY_TILLAR;
                break;
            case "EKALOGIYA":
                bookCategory = BookCategory.EKALOGIYA;
                break;
            case "GIDROMETROLOGIYA":
                bookCategory = BookCategory.GIDROMETROLOGIYA;
                break;
            case "SPORT":
                bookCategory = BookCategory.SPORT;
                break;
            case "IQTISODIYOT":
                bookCategory = BookCategory.IQTISODIYOT;
                break;
            case "GEOLOGIYA":
                bookCategory = BookCategory.GEOLOGIYA;
                break;
            default:
                bookCategory = null;
        }
        return bookRepository.findBooksByBookCategory(bookCategory);
    }

    public List<Book> searchBook(String keyword) {
        if (keyword == null || keyword.equals("")) {
            bookRepository.findAllBook();
        }

        var list = bookRepository.findAllByNameContainingIgnoreCaseOrderByDownloadCountDesc(keyword);
        return list;
    }

    public Book increaseCount(Long bookId, String FileHashId) {
        var book = findBookWithId(bookId);
        if (book == null)
            return null;
        book.setDownloadCount(book.getDownloadCount() + 1);
        return saveBook(book);
    }

    public boolean doesBookExist(Long id){
        return bookRepository.existsById(id);
    }
}
