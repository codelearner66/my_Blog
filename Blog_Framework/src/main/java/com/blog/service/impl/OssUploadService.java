package com.blog.service.impl;

import com.blog.commonResult.ResponseResult;
import com.blog.enums.AppHttpCodeEnum;
import com.blog.exception.SystemException;
import com.blog.pojo.LoginUser;
import com.blog.pojo.User;
import com.blog.service.UploadService;
import com.blog.service.UserService;
import com.blog.utils.PathUtils;
import com.blog.utils.SecurityUtils;
import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Data
@ConfigurationProperties(prefix = "oss")
public class OssUploadService implements UploadService {
   @Autowired
   UserService userService;
   private String accessKey;
    private String secretKey;
    private String bucket;
    @Override
    public ResponseResult uploadImg(MultipartFile img) {

        //判断文件类型
        //获取原始文件名
        String originalFilename = img.getOriginalFilename();
        //对原始文件名进行判断
        if (img.isEmpty() || (!originalFilename.endsWith(".png") && !originalFilename.endsWith(".jpg"))) {
            throw new SystemException(AppHttpCodeEnum.FILE_TYPE_ERROR);
        }
        //如果判断通过上传文件到OSS
        String filePath = PathUtils.generateFilePath(originalFilename);
        String url = uploadOss(img, filePath);//  2099/2/3/wqeqeqe.png
        //Todo 保存路径数据到数据库中
        LoginUser loginUser = SecurityUtils.getLoginUser();
        User user= loginUser.getUser();
        user.setAvatar(url);
        Boolean aBoolean = userService.updateUserHeader(user);
        return ResponseResult.okResult(url);
    }

    private String uploadOss(MultipartFile imgFile, String filePath) {
        //构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration(Region.autoRegion());
        //...其他参数参考类注释
        UploadManager uploadManager = new UploadManager(cfg);
        //默认不指定key的情况下，以文件内容的hash值作为文件名
        String key = filePath;
        try {
            InputStream inputStream = imgFile.getInputStream();
            Auth auth = Auth.create(accessKey, secretKey);
            String upToken = auth.uploadToken(bucket);
            try {
                Response response = uploadManager.put(inputStream, key, upToken, null, null);
                //解析上传成功的结果
                DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
                // System.out.println(putRet.key);
                //  System.out.println(putRet.hash);
                return "http:r8o5f106d.hn-bkt.clouddn.com/" + key;
            } catch (QiniuException ex) {
                Response r = ex.response;
                System.err.println(r.toString());
                try {
                    System.err.println(r.bodyString());
                } catch (QiniuException ex2) {
                    //ignore
                }
            }
        } catch (Exception ex) {
            //ignore
        }
        return "www";
    }
    @Override
    public ResponseResult uploadImg(MultipartFile img, HttpServletRequest request) throws FileNotFoundException {
        //校验图片信息
        String originalFilename = img.getOriginalFilename();
        String lastName = null;
        if (!originalFilename.endsWith(".jpg") && !originalFilename.endsWith(".png")) {
            throw new SystemException(AppHttpCodeEnum.FILE_TYPE_ERROR);
        }
        if (img.getSize() > 1024 * 1024 * 5) {
            throw new SystemException(AppHttpCodeEnum.FILE_SIZE_ERROR);
        }
        //获取文件后缀
        lastName = originalFilename.substring(originalFilename.length() - 4);
        //保存文件到本地路径
        String path = "E:\\jee-2020-092\\spring boot\\my_Blog\\Blog\\src\\main\\resources\\static\\upload\\" + SecurityUtils.getUserId() + "\\";
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        String fileName = SecurityUtils.getUserId() + UUID.randomUUID().toString().replaceAll("-", "");

        try {
            img.transferTo(new File(path, fileName + lastName));
        } catch (IOException e) {
            throw new SystemException(AppHttpCodeEnum.FILE_TYPE_ERROR);
        }
        //Todo 修改数据库文件地址
        LoginUser loginUser = SecurityUtils.getLoginUser();
        User user= loginUser.getUser();
        user.setAvatar("/upload/" + SecurityUtils.getUserId()+"/"+fileName+lastName);
        Boolean aBoolean = userService.updateUserHeader(user);
        return ResponseResult.okResult(200, "/upload/" + SecurityUtils.getUserId()+"/"+fileName+lastName);
    }

    @Override
    public ResponseResult uploadArticleImg(List<MultipartFile> multipartFile, HttpServletRequest request) {
        if (multipartFile.isEmpty()) {
            return ResponseResult.errorResult(AppHttpCodeEnum.FILE_SIZE_ERROR);
        }
        List<String> fileList = new ArrayList<>();
        for (MultipartFile img : multipartFile) {
            //校验图片信息
            String originalFilename = img.getOriginalFilename();
            String lastName;
            if (!originalFilename.endsWith(".jpg") && !originalFilename.endsWith(".png")) {
                throw new SystemException(AppHttpCodeEnum.FILE_TYPE_ERROR);
            }
            if (img.getSize() > 1024 * 1024 * 5) {
                throw new SystemException(AppHttpCodeEnum.FILE_SIZE_ERROR);
            }
            //获取文件后缀
            lastName = originalFilename.substring(originalFilename.length() - 4);
            //保存文件到本地路径
            String path = "E:\\jee-2020-092\\spring boot\\my_Blog\\Blog\\src\\main\\resources\\static\\upload\\" + SecurityUtils.getUserId() + "\\";
            File file = new File(path);
            if (!file.exists()) {
                file.mkdirs();
            }
            String fileName = SecurityUtils.getUserId() + UUID.randomUUID().toString().replaceAll("-", "");
            try {
                fileList.add("/upload/" + SecurityUtils.getUserId()+"/"+fileName+lastName);
                img.transferTo(new File(path, fileName + lastName));

            } catch (IOException e) {
                throw new SystemException(AppHttpCodeEnum.FILE_TYPE_ERROR);
            }
        }
        return ResponseResult.okResult(fileList);
    }


}
