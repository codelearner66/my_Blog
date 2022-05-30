package com.blog.controller.user.restController;

import com.blog.commonResult.ResponseResult;
import com.blog.service.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.FileNotFoundException;
import java.util.List;

@RestController
public class UploadController {
    @Autowired
    private UploadService uploadService;

    @PostMapping("/upload")
    public ResponseResult uploadImg(@RequestParam("file")  MultipartFile img, HttpServletRequest request) throws FileNotFoundException {
        return  uploadService.uploadImg(img,request);
    }
    @PostMapping("/uploadArticle")
    public ResponseResult uploadArticleImg(@RequestParam("articleImg") List<MultipartFile> img, HttpServletRequest request){
        return uploadService.uploadArticleImg(img, request);
    }
}
