package com.spring.app.controller;

import com.spring.app.service.FileService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class FileController {
    private final FileService fileService;

    @ApiOperation(value = "Perform Upload of Files")
    @PostMapping(value = "/upload", produces = "application/json")
    public ResponseEntity<String> singleFileUpload(@RequestParam("file") List<MultipartFile> files,
                                                   @RequestParam("id") final String id) {

        if (files.isEmpty())
            return new ResponseEntity<>("Please select a file to upload", HttpStatus.BAD_REQUEST);


        return new ResponseEntity<>("Files are Uploaded [" + fileService.saveFiles(files) + "] for ".concat(id), HttpStatus.OK);
    }

}