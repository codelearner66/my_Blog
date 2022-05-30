package com.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.blog.commonResult.ResponseResult;
import com.blog.enums.AppHttpCodeEnum;
import com.blog.mapper.CommentMapper;
import com.blog.pojo.Comment;
import com.blog.service.CommentService;
import com.blog.service.UserService;
import com.blog.utils.BeanCopyUtils;
import com.blog.utils.SecurityUtils;
import com.blog.vo.CommentVo;
import com.blog.vo.PageVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service("commentService")
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

    @Autowired
    private UserService userService;

    @Override
    public ResponseResult commentList(Long articleId, Integer pageNum, Integer pageSize) {
        //查询对应文章的根评论
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        //对articleId进行判断
        queryWrapper.eq(Comment::getArticleId,articleId);
        //根评论 rootId为-1
        queryWrapper.eq(Comment::getRootId,-1);

        //分页查询
        Page<Comment> page = new Page(pageNum,pageSize);
        page(page,queryWrapper);

        List<CommentVo> commentVoList = toCommentVoList(page.getRecords());

        return ResponseResult.okResult(new PageVo(commentVoList,page.getTotal()));
    }

    /**
     * 查询对应根评论的子评论
     * @param articleId  文章id
     * @param rootId     根评论id
     * @param pageNum    评论页数
     * @param pageSize   评论大小
     * @return
     */
    @Override
    public ResponseResult ChildCommentList(Long articleId,Long rootId, Integer pageNum, Integer pageSize) {
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        //对articleId进行判断
        queryWrapper.eq(Comment::getArticleId,articleId);
        queryWrapper.eq(Comment ::getRootId,rootId);
        //分页查询
        Page<Comment> page = new Page<>(pageNum,pageSize);
        page(page,queryWrapper);

        List<CommentVo> commentVoList = toCommentVoList(page.getRecords());

        return ResponseResult.okResult(new PageVo(commentVoList,page.getTotal()));
    }

    @Override
    /**
     *   返回评论和子评论
     * @param articleId 文章id
     * @param rootId    根评论id -1 为一级根
     * @param pageNum   根评论分页
     * @param pageSize  分页大小
     * @param commentNum 子评论论分页
     * @param commentSize  子评论分页大小
     * @return
     */
    public ResponseResult commentList(Long articleId, Long rootId, Integer pageNum, Integer pageSize, Long commentNum, Long commentSize) {
        //查询对应文章的根评论
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        //对articleId进行判断
        queryWrapper.eq(Comment::getArticleId,articleId);
        //根评论 rootId为-1
        queryWrapper.eq(Comment::getRootId,-1);
        //分页查询
        if(pageNum==null) {
            pageNum=1;
        }
        Page<Comment> page = new Page(pageNum,pageSize);
        page(page,queryWrapper);
        List<CommentVo> commentVoList = toCommentVoList(page.getRecords());
        //遍历根评论查找子评论 一般子评论只查询第一页 指定rootId 的子评论按照分页规则查询
        for(CommentVo cvo:commentVoList){
            List<CommentVo> commentVos;
            if (cvo.getRootId().equals(rootId)) {
                 commentVos = toCommentVoList(articleId, cvo.getId(), commentNum, commentSize);
            }
            else {
                commentVos = toCommentVoList(articleId, cvo.getId(), 1L, 10L);
            }
            cvo.setChildCommentVo(commentVos);
        }
        return ResponseResult.okResult(new PageVo(commentVoList,page.getTotal()));
    }

    @Override
    public ResponseResult addComments(Comment comment) {
        comment.setCreateBy(SecurityUtils.getUserId());
        comment.setCreateTime(new Date());
        if (save(comment)) {
            return ResponseResult.okResult();
        }
        else {
            return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
        }
    }

    private List<CommentVo> toCommentVoList(Long articleId,Long rootId, Long pageNum, Long pageSize){
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        //对articleId进行判断
        queryWrapper.eq(Comment::getArticleId,articleId);
        queryWrapper.eq(Comment ::getToCommentId,rootId);
        //分页查询
        Page<Comment> page = new Page<>(pageNum,pageSize);
        page(page,queryWrapper);
        return toCommentVoList(page.getRecords());
    }

    private List<CommentVo> toCommentVoList(List<Comment> list){
        List<CommentVo> commentVos = BeanCopyUtils.copyBeanList(list, CommentVo.class);
        //遍历vo集合
        for (CommentVo commentVo : commentVos) {
            //通过creatyBy查询用户的昵称并赋值
            String nickName =userService.getById(commentVo.getCreateBy()).getNickName()==null? "该用户已注销" : userService.getById(commentVo.getCreateBy()).getNickName() ;
            commentVo.setUsername(nickName);
            //通过toCommentUserId查询用户的昵称并赋值
            //如果toCommentUserId不为-1才进行查询
            if(commentVo.getToCommentUserId()!=-1){
                String toCommentUserName =commentVo.getToCommentUserId()!=null? userService.getById(commentVo.getToCommentUserId()).getNickName():"该用户已注销";
                commentVo.setToCommentUserName(toCommentUserName);
            }
        }
        return commentVos;
    }

}
