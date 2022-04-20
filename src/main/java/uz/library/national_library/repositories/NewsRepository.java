package uz.library.national_library.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uz.library.national_library.models.News;

import java.util.List;

@Repository
public interface NewsRepository extends JpaRepository<News, Long> {

    @Query(value = "select * from news_table n order by date desc", nativeQuery = true)
    List<News> findAllNews();

    boolean existsById(Long id);

    News findNewsById(Long id);

    List<News> findAllByTitleContainingIgnoreCaseOrderByDateDesc(@Param("title") String title);

}
