package com.project.Ecommerceapp.fileservice;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface Filemethods {
    String uploadfile(String path, MultipartFile file) throws IOException;
}
