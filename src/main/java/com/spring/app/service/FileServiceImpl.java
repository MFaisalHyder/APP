package com.spring.app.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
public class FileServiceImpl implements FileService {

    @Override
    public boolean saveFiles(final List<MultipartFile> files) {
        initializeDirectory();
        files.forEach(
                file -> {
                    byte[] bytes = new byte[0];
                    try {
                        bytes = file.getBytes();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Path path = Paths.get(FILE_LOCATION + file.getOriginalFilename());
                    try {
                        Files.write(path, bytes);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
        );
        return true;
    }

}