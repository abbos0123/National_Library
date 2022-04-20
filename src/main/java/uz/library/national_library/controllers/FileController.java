package uz.library.national_library.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileUrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.library.national_library.models.FileStorage;
import uz.library.national_library.services.BookService;
import uz.library.national_library.services.FileService;

import java.net.MalformedURLException;
import java.net.URLEncoder;

@RestController
@RequestMapping("/api/file")
public class FileController {

    private final FileService fileService;
    private final BookService bookService;

    @Value("${upload.folder}")
    private String uploadFolder;

    public FileController(FileService fileService, BookService bookService) {
        this.fileService = fileService;
        this.bookService = bookService;
    }

    @PostMapping("/upload")
    public ResponseEntity<?> uploadAnyFile(@RequestParam("file") MultipartFile multipartFile) {

        var file = fileService.save(multipartFile);
        return ResponseEntity.ok(file);
    }

    @PostMapping("/upload/image")
    public ResponseEntity<?> uploadImage(@RequestParam("file") MultipartFile multipartFile) {

        if(!"image/png".equals(multipartFile.getContentType()) && !"image/jpeg".equals(multipartFile.getContentType())) {
            return ResponseEntity.ok("Iltimos png yoki jpeg file kiritng.");
        }

        var file = fileService.save(multipartFile);
        return ResponseEntity.ok(file);
    }

    @PostMapping("/upload/pdf_book")
    public ResponseEntity<?> uploadPdfBook(@RequestParam("file") MultipartFile multipartFile) {

        if(!"application/pdf".equals(multipartFile.getContentType()) && !"application/vnd.openxmlformats-officedocument.wordprocessingml.document".equals(multipartFile.getContentType())) {
           return ResponseEntity.ok("Iltimos pdf yoki doc file kiritng.");
        }

        var file = fileService.save(multipartFile);
        return ResponseEntity.ok(file);
    }


    @GetMapping("/preview/{hashId}")
    public ResponseEntity<?> previewFile(@PathVariable String hashId) throws MalformedURLException {
        FileStorage fileStorage = fileService.findByHashId(hashId);

        if (fileStorage == null)
            return ResponseEntity.ok("Bunday fayl mavjud emas");

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; fileName=\"" + URLEncoder.encode(fileStorage.getName()))
                .contentType(MediaType.parseMediaType(fileStorage.getContentType()))
                .body(new FileUrlResource(String.format("%s/%s", uploadFolder, fileStorage.getUploadPath())));
    }


    @GetMapping("/download/{bookId}")
    public ResponseEntity<?> downloadFile( @PathVariable Long bookId, @RequestHeader String hashId) throws MalformedURLException {

        try {
            FileStorage fileStorage = fileService.findByHashId(hashId);
            bookService.increaseCount(bookId, "");

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; fileName=\"" + URLEncoder.encode(fileStorage.getName()))
                    .contentType(MediaType.parseMediaType(fileStorage.getContentType()))
                    .body(new FileUrlResource(String.format("%s/%s", uploadFolder, fileStorage.getUploadPath())));

        }catch (Exception e){
            return ResponseEntity.ok("File not found !");
        }
    }

    @DeleteMapping("/delete/{hashId}")
    public ResponseEntity<?> deleteFile(@PathVariable String hashId) {
        try {
            fileService.deleteFile(hashId);
            return ResponseEntity.ok("File is deleted.");
        } catch (Exception e) {
            return ResponseEntity.ok(e.getMessage());
        }
    }
}
