package uz.library.national_library.models;


import javax.persistence.*;

@Entity
@Table(name = "book")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String author;

    @OneToOne
    @JoinColumn(name = "fk_image_storage_id")
    private FileStorage imageStorage;

    @OneToOne
    @JoinColumn(name = "fk_file_storage_id")
    private FileStorage fileStorage;

    @Enumerated(EnumType.STRING)
    private BookCategory bookCategory;

    private String description;

    private int downloadCount;

    public Book() {
    }

    public BookCategory getBookCategory() {
        return bookCategory;
    }

    public void setBookCategory(BookCategory bookCategory) {
        this.bookCategory = bookCategory;
    }

    public FileStorage getFileStorage(){
        return fileStorage;
    }

    public void setFileStorage(FileStorage fileStorage) {
        this.fileStorage = fileStorage;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public FileStorage getImageStorage() {
        return imageStorage;
    }

    public void setImageStorage(FileStorage imageStorage) {
        this.imageStorage = imageStorage;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getDownloadCount() {
        return downloadCount;
    }

    public void setDownloadCount(int downloadCount) {
        this.downloadCount = downloadCount;
    }
}
