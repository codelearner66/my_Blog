package com.blog.service;

import com.blog.commonResult.ResponseResult;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.FileNotFoundException;
import java.util.List;

public interface UploadService {
    ResponseResult uploadImg(MultipartFile img);
    ResponseResult uploadImg(MultipartFile img, HttpServletRequest request) throws FileNotFoundException;

    ResponseResult uploadArticleImg(List<MultipartFile> img, HttpServletRequest request);
}
