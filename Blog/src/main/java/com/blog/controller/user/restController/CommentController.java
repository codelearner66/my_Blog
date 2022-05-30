package com.blog.controller.user.restController;

import com.blog.commonResult.ResponseResult;
import com.blog.pojo.Comment;
import com.blog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @GetMapping("/commentList")
    public ResponseResult commentList(Long articleId, Integer pageNum, Integer pageSize){
        return commentService.commentList(articleId,pageNum,pageSize);
    }
    @GetMapping("/commentList1")
    public ResponseResult commentList(Long articleId, Long rootId, Integer pageNum, Integer pageSize, Long commentNum, Long commentSize){
        return commentService.commentList(articleId,rootId,pageNum,pageSize,commentNum,commentSize);
    }
    @PostMapping("/addComments")
    public ResponseResult addComments(Comment comment){

        return commentService.addComments(comment);
    }
}
