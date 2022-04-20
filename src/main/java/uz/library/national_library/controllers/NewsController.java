package uz.library.national_library.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.library.national_library.models.InputNews;
import uz.library.national_library.models.News;
import uz.library.national_library.services.FileService;
import uz.library.national_library.services.NewsService;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/news")
public class NewsController {
    private final NewsService newsService;
    private final FileService fileService;

    public NewsController(NewsService newsService, FileService fileService) {
        this.newsService = newsService;
        this.fileService = fileService;
    }


    @PostMapping("/add")
    public ResponseEntity<?> saveNews(
            @RequestBody InputNews inputNews,
            @RequestHeader String hashId
    ) {
        if (inputNews == null || inputNews.getData() == null || inputNews.getTitle() == null || inputNews.getData().equals("") || inputNews.getTitle().equals(""))
            return ResponseEntity.badRequest().body("Iltimos Ma'lumotlarni kiriting ");

        var news = new News();
        news.setTitle(inputNews.getTitle());
        news.setData(inputNews.getData());


        var returnValue = newsService.saveNews(news, hashId);
        if (returnValue == null)
            return ResponseEntity.badRequest().body("Siz Hamma ma'lumotlarni kiritmadingiz !");

        return ResponseEntity.accepted().header("Yangilik muvaffaqiyatli yuklandi.").body(returnValue);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateNews(@RequestBody News news) {
        var returnValue = newsService.update(news);

        if (returnValue == null)
            return ResponseEntity.badRequest().body("Yanglashda xatolik yuz berdi! Ilimos hamma ma'lumotlarni kirinting!");

        return ResponseEntity.ok(returnValue);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteNews(@RequestHeader Long newsId) {
        if (!newsService.doesNewsExist(newsId))
            return ResponseEntity.badRequest().body("Bunday Ma'lumot mavjud emas!");

        var news = newsService.getNewsById(newsId);

        if (newsService.deleteNews(news)) {
            return ResponseEntity.ok("Ma'lumot o'chirildi.");
        } else {
            return ResponseEntity.ok("Ma'lumot o'chirishda xatolik yuz berdi!");
        }

    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getNewsById(@PathVariable Long id) {
        var news = newsService.getNewsById(id);

        if (news == null)
            return ResponseEntity.ok("Bunday yangilik mavjud emas");

        return ResponseEntity.ok(news);
    }

    @GetMapping("/allNews")
    public ResponseEntity<?> getAllNews() {
        var returnValue = newsService.getAllNews();
        return ResponseEntity.ok(returnValue);
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchNewsWithTitle(@RequestParam String keyword) {
        if (keyword == null || keyword.equals(""))
            return ResponseEntity.ok(newsService.getAllNews());

        var returnValue = newsService.searchNewsByName(keyword);

        if (returnValue == null)
            returnValue = new ArrayList<>();

        return ResponseEntity.ok(returnValue);
    }

    @PostMapping("/add_view_count")
    public ResponseEntity<?> increaseNewsViewCount(@RequestParam Long id) {
        var news = newsService.getNewsById(id);

        if (news == null)
            return ResponseEntity.ok("Bunday yangilik mavjud emas");

        news.setViewCount(news.getViewCount() + 1);
        return ResponseEntity.ok(newsService.update(news));
    }
}
