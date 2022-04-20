package uz.library.national_library.services;

import org.springframework.stereotype.Service;
import uz.library.national_library.models.News;
import uz.library.national_library.repositories.FileStorageRepository;
import uz.library.national_library.repositories.NewsRepository;

import java.util.*;

@Service
public class NewsService {
    private final NewsRepository newsRepository;
    private final FileStorageRepository fileStorageRepository;
    private final FileService fileService;

    public NewsService(NewsRepository newsRepository, FileStorageRepository fileStorageRepository, FileService fileService) {
        this.newsRepository = newsRepository;
        this.fileStorageRepository = fileStorageRepository;
        this.fileService = fileService;
    }

    public News saveNews(News news, String hashId) {
        if (news == null)
            return null;

        if (Objects.equals(news.getData(), "") || Objects.equals(news.getTitle(), ""))
            return null;

        var date = new Date();
        date.setHours(date.getHours() + 5);
        news.setDate(date);

        if (hashId == null || hashId.equals("") || !fileStorageRepository.existsFileStorageByHashId(hashId))
            news.setImageFileStorage(null);

        news.setImageFileStorage(fileStorageRepository.findByHashId(hashId));

        return newsRepository.save(news);
    }

    public News update(News news) {
        if (news == null ||
                news.getId() == null ||
                !doesNewsExist(news.getId()) ||
                news.getData() == null ||
                news.getData().equals("") ||
                news.getTitle() == null ||
                news.getTitle().equals(""))
            return null;

        return newsRepository.save(news);
    }

    public boolean deleteNews(News news) {
        if (news == null || news.getId() == 0)
            return false;

        if (!newsRepository.existsById(news.getId()))
            return false;

        try {
            var newsImage = news.getImageFileStorage();
            newsRepository.delete(news);

            if (newsImage != null)
                fileService.clearMemory(newsImage);

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public News getNewsById(Long newsId) {
        if (!newsRepository.existsById(newsId))
            return null;

        return newsRepository.findNewsById(newsId);
    }

    public boolean doesNewsExist(Long id) {
        return newsRepository.existsById(id);
    }

    public List<News> getAllNews() {
        return newsRepository.findAllNews();
    }

    public List<News> searchNewsByName(String title) {

        if (title == null)
            return newsRepository.findAllNews();

        List<News> list1;

        list1 = newsRepository.findAllByTitleContainingIgnoreCaseOrderByDateDesc(title);

        return list1;
    }

}
