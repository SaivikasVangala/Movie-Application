package com.MovieFlix.MovieApi.Service;

import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public interface FileService {

    String uploadFile(String path, MultipartFile file) throws IOException;
    //In Spring we use multipartfile if we are dealing with file in restful manner.

    //IN Rest Protocol We cannot fetch the file directly and serve it to the user on frontend
    //We need to convert the file into streams. So we take 2 parameters 'Path' for getting the file from the required path and name for actually searching the name in the path.

    InputStream getResourceFile(String path,String filename) throws FileNotFoundException;


}
