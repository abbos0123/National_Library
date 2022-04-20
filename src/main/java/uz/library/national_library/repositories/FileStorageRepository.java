package uz.library.national_library.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.library.national_library.models.FileStorage;
import uz.library.national_library.models.FileStorageStatus;

import java.util.List;

@Repository
public interface  FileStorageRepository  extends JpaRepository<FileStorage, Long> {

    FileStorage findFileStorageByHashId(String hashId);

    List<FileStorage> findFileStorageByFileStorageStatus(FileStorageStatus fileStorageStatus);

    boolean existsFileStorageByHashId(String hashId);
    FileStorage findByHashId(String hashId);

}
