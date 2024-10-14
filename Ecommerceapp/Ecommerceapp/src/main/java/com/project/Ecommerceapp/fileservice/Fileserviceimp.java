package com.project.Ecommerceapp.fileservice;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class Fileserviceimp implements Filemethods{

    @Override
    public String uploadfile(String path, MultipartFile file) throws IOException {
        String orignalfilename=file.getOriginalFilename();
        String randomId= UUID.randomUUID().toString();
        String filename=randomId.concat(orignalfilename.substring(orignalfilename.lastIndexOf(".")));
        String filepath=path+File.pathSeparator+filename;

        File folder=new File(path);
        if(!folder.exists()){
            folder.mkdir();
        }
        Files.copy(file.getInputStream(), Paths.get(filepath));
        return filename;
    }
}
