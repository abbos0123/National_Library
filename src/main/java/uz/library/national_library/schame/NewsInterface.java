package uz.library.national_library.schame;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import uz.library.national_library.models.News;

public interface NewsInterface {

    ResponseEntity<?> saveNews(News news,  String hashId);

    ResponseEntity<?> updateNews( News news);

    ResponseEntity<?> deleteNews( News news);

    ResponseEntity<?> getAllNews();

    ResponseEntity<?> searchNews(String name);

    ResponseEntity<?> increaseNewsViewCount(Long id);
}
