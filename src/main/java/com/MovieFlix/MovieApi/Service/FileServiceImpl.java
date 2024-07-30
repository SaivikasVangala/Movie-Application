package com.MovieFlix.MovieApi.Service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service("FileImpl")
public class FileServiceImpl implements FileService {
    @Override
    public String uploadFile(String path, MultipartFile file) throws IOException {
        //To get name of the file.
        String fileName = file.getOriginalFilename();

        //To get a file path
        String filepath = path+ File.separator + fileName;
        //File Separator says that both path and filename should be appended.

        //Create File Object.

        File f = new File(path);
        if(!f.exists()){
            f.mkdirs();
        }

        // Copy the file or upload the path to the file.

        Files.copy(file.getInputStream(), Paths.get(filepath), StandardCopyOption.REPLACE_EXISTING);
        return fileName;
    }

    @Override
    public InputStream getResourceFile(String path, String filename) throws FileNotFoundException {
        String filepath = path+ File.separator + filename;

        return new FileInputStream(filepath);
    }
}
