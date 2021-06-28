package com.spring.app.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

public interface FileService {
    String FILE_LOCATION = "storage/";

    default void initializeDirectory() {

        File directory = new File(FILE_LOCATION);
        if (directory.mkdir()) System.out.println("directory created");
    }

    boolean saveFiles(List<MultipartFile> files);
}