package uz.library.national_library.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uz.library.national_library.models.Book;
import uz.library.national_library.models.BookCategory;


import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findBookByAuthor(String author);

    @Query(value = "select * from book b where b.name = :name", nativeQuery = true)
    List<Book> findBookByNameNativeQuery(@Param("name") String name);

    Book findBookById(Long id);

    @Query(value = "select * from book b order by download_count desc", nativeQuery = true)
    List<Book> findAllBook();

    List<Book> findBooksByBookCategory(BookCategory bookCategory);

    List<Book> findAllByNameContainingIgnoreCaseOrderByDownloadCountDesc(@Param("keyoword") String keyword);

    boolean existsById(Long id);
}
