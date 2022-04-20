package uz.library.national_library.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.library.national_library.models.Book;
import uz.library.national_library.services.BookService;
import uz.library.national_library.services.FileService;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/book")
public class BookController {

    private final BookService bookService;
    private final FileService fileService;


    public BookController(BookService bookService, FileService fileService) {
        this.bookService = bookService;
        this.fileService = fileService;
    }

    @PostMapping("/add")
    public ResponseEntity<?> addNewBook(
            @RequestBody Book book,
            @RequestHeader String hashIdOfBookPdfFile,
            @RequestHeader String hashIdImage) {

        if (hashIdOfBookPdfFile == null || hashIdOfBookPdfFile.equals(""))
            return ResponseEntity.ok("Iltimos kitobni hashIdsini to'g'ri kiriting!");

        if (!fileService.existsByHashId(hashIdOfBookPdfFile))
            return ResponseEntity.ok("Bunday hashId ostidagi kitob mavjud emas!");

        if (hashIdImage == null || hashIdImage.equals(""))
            return ResponseEntity.ok("Iltimos kitobni rasmini hashIdsini to'g'ri kiriting!");

        if (!fileService.existsByHashId(hashIdImage))
            return ResponseEntity.ok("Bunday hashId ostidagi muqova rasmi  mavjud emas!");


        book.setFileStorage(fileService.findByHashId(hashIdOfBookPdfFile));
        book.setImageStorage(fileService.findByHashId(hashIdImage));

        var exe = book.getImageStorage().getExtension();

        if (!Objects.equals(exe, "png") && !Objects.equals(exe, "jpg"))
            return ResponseEntity.ok("Iltimos Kitob muqovasi uchun pnj yoki jpg formatdagi rasim tanlang !");

        var bookSaved = bookService.saveBook(book);
        return ResponseEntity.ok(bookSaved);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteBook(@PathVariable("id") Long bookId) {
        if (!bookService.doesBookExist(bookId))
            return ResponseEntity.ok("Bunday id ostida hech qanday kitob topilmadi!");

        try {
            var msg = bookService.deleteBook(bookId);
            return ResponseEntity.ok(msg);

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.ok("You could not deleted this file !");
        }

    }


    @PutMapping("/update")
    public ResponseEntity<?> updateBook(@RequestBody Book book) {
        if (book.getId() == null)
            return ResponseEntity.badRequest().build();

        if (!bookService.doesBookExist(book.getId()))
            return ResponseEntity.ok("Siz mavjud bo'lmagan kitobning id sini kiritingiz!");

        bookService.update(book);

        return ResponseEntity.ok(book);
    }

    @GetMapping("/find_with_id/{id}")
    public ResponseEntity<?> getBookById(@PathVariable("id") Long id) {
        if (!bookService.doesBookExist(id))
            return ResponseEntity.ok().body("Bu id ostida heck qanday kitob mavjud emas !");

        var book = bookService.findBookWithId(id);
        return ResponseEntity.ok(book);
    }

    @GetMapping("/find_with_name/{name}")
    public ResponseEntity<List<Book>> getBookByName(@PathVariable String name) {
        var list = bookService.findAllBooksByName(name);
        return ResponseEntity.ok(list);
    }

    @GetMapping("/authors_book/search")
    public ResponseEntity<?> getAllBooksOfAuthor(@RequestParam String authorName) {
        var list = bookService.findAllBookOfAuthor(authorName);
        return ResponseEntity.ok(list);
    }

    @GetMapping("/allBook")
    public ResponseEntity<?> getAllBooks() {
        var list = bookService.findAllBooks();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/category")
    public ResponseEntity<?> getAllBookByCategory(@RequestHeader String name) {
        return ResponseEntity.ok(bookService.findAllBookByCategory(name));
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchBooksByName(@RequestParam String keyword) {
        return ResponseEntity.ok(bookService.searchBook(keyword));
    }

    @DeleteMapping("/clear_garbage")
    public ResponseEntity<?> clearDraftFiles() {
        fileService.deleteAllDraft();
        return ResponseEntity.ok("Keraksiz ma'lumotlar o'chirildi !");
    }

}
