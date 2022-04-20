package uz.library.national_library.models;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "news_table")
public class News implements Comparable<News> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss dd/MM/yyyy")
    private Date date;
    private String data;
    private int viewCount;

    @OneToOne
    @JoinColumn(name = "fk_image_file")
    private FileStorage imageFileStorage;

    public News() {
    }

    public News(Long id, String title, Date date, String data, int viewCount, FileStorage imageFileStorage) {
        this.id = id;
        this.title = title;
        this.date = date;
        this.data = data;
        this.viewCount = viewCount;
        this.imageFileStorage = imageFileStorage;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public int getViewCount() {
        return viewCount;
    }

    public void setViewCount(int viewCount) {
        this.viewCount = viewCount;
    }

    public FileStorage getImageFileStorage() {
        return imageFileStorage;
    }

    public void setImageFileStorage(FileStorage imageFileStorage) {
        this.imageFileStorage = imageFileStorage;
    }

    @Override
    public int compareTo(News o) {
        if (date == null || o.getDate() == null)
            return 0;
        return date.compareTo(o.getDate());
    }
}
