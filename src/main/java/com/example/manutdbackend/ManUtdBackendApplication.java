package com.example.manutdbackend;

import com.example.manutdbackend.service.FilesStorageService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.Resource;

@SpringBootApplication
public class ManUtdBackendApplication {
    @Resource
    FilesStorageService storageService;

    public static void main(String[] args) {
        SpringApplication.run(ManUtdBackendApplication.class, args);
    }

//    @Override
//    public void run(String... arg) throws Exception {
//        storageService.deleteAll();
//        storageService.init();
//    }
}
