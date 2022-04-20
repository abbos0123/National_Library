package uz.library.national_library;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.text.SimpleDateFormat;
import java.util.Date;

@SpringBootApplication
public class NationalLibraryApplication {

    public static void main(String[] args) {
        SpringApplication.run(NationalLibraryApplication.class, args);
//
//        SimpleDateFormat fom = new SimpleDateFormat("ss-mm-HH");
//        var date = new Date();
//
//        for (int i = 0; i < 10; i++)
//            System.out.println(fom.format(date));

    }

}
