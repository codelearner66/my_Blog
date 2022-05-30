package com.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.blog.commonResult.ResponseResult;
import com.blog.constants.SystemConstants;
import com.blog.enums.AppHttpCodeEnum;
import com.blog.mapper.ArticleMapper;
import com.blog.mapper.UserMapper;
import com.blog.pojo.Article;
import com.blog.pojo.Category;
import com.blog.service.ArticleService;
import com.blog.service.CategoryService;
import com.blog.utils.BeanCopyUtils;
import com.blog.utils.SecurityUtils;
import com.blog.vo.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

    @Autowired
    CategoryService categoryService;
    @Autowired
    UserMapper userMapper;

    @Override
    public ResponseResult hotArticleList() {
        //查询热门文章 封装成ResponseResult返回
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        //必须是正式文章
        queryWrapper.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);
        //按照浏览量进行排序
        queryWrapper.orderByDesc(Article::getViewCount);
        //最多只查询10条
        Page<Article> page = new Page(1, 10);
        page(page, queryWrapper);
        List<Article> articles = page.getRecords();
        //bean拷贝
        List<HotArticleVo> articleVos = new ArrayList<>();
        for (Article article : articles) {
            HotArticleVo vo = new HotArticleVo();
            BeanUtils.copyProperties(article, vo);
            articleVos.add(vo);
        }

        return ResponseResult.okResult(articleVos);
    }

    @Override
    public ResponseResult hotArticleListDetils() {
        //查询热门文章 封装成ResponseResult返回
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        //必须是正式文章
        queryWrapper.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);
        //按照浏览量进行排序
        queryWrapper.orderByDesc(Article::getViewCount);
        //最多只查询10条
        Page<Article> page = new Page(1, 10);
        page(page, queryWrapper);
        List<Article> articles = page.getRecords();
        //查询categoryName
        List<Article> collect = articles.stream()
                .map(article -> article.setCategoryName(categoryService.getById(article.getCategoryId()).getName()))
                .collect(Collectors.toList());
        //封装查询结果
        List<ArticleListVo> articleListVos = BeanCopyUtils.copyBeanList(collect, ArticleListVo.class);

        PageVo pageVo = new PageVo(articleListVos, page.getTotal());
        return ResponseResult.okResult(pageVo);
    }

    @Override
    public ResponseResult articleList(Integer pageNum, Integer pageSize, Long categoryId) {
        //查询条件
        LambdaQueryWrapper<Article> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        // 如果 有categoryId 就要 查询时要和传入的相同
        lambdaQueryWrapper.eq(Objects.nonNull(categoryId) && categoryId > 0, Article::getCategoryId, categoryId);
        // 状态是正式发布的
        lambdaQueryWrapper.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);
        // 对isTop进行降序
        lambdaQueryWrapper.orderByDesc(Article::getIsTop);

        //分页查询
        Page<Article> page = new Page<>(pageNum, pageSize);
        page(page, lambdaQueryWrapper);

        List<Article> articles = page.getRecords();
        //查询categoryName
        articles.stream()
                .map(article -> article.setCategoryName(categoryService.getById(article.getCategoryId()).getName()))
                .collect(Collectors.toList());
        //封装查询结果
        List<ArticleListVo> articleListVos = BeanCopyUtils.copyBeanList(page.getRecords(), ArticleListVo.class);

        PageVo pageVo = new PageVo(articleListVos, page.getTotal());
        return ResponseResult.okResult(pageVo);
    }

    @Override
    public ResponseResult articleListByUserId(Integer pageNum, Integer pageSize, Long categoryId) {
        //查询条件
        LambdaQueryWrapper<Article> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        // 如果 有categoryId 就要 查询时要和传入的相同
        lambdaQueryWrapper.eq(Objects.nonNull(categoryId) && categoryId > 0, Article::getCategoryId, categoryId);
        //查询该用户名下的所有文章
        lambdaQueryWrapper.eq(Article::getCreateBy, SecurityUtils.getUserId());
        // 对isTop进行降序
        lambdaQueryWrapper.orderByDesc(Article::getIsTop);

        //分页查询
        Page<Article> page = new Page<>(pageNum, pageSize);
        page(page, lambdaQueryWrapper);

        List<Article> articles = page.getRecords();
        //查询categoryName
        articles.stream()
                .map(article -> article.setCategoryName(categoryService.getById(article.getCategoryId()).getName()))
                .collect(Collectors.toList());
        //封装查询结果
        List<ArticleListVo> articleListVos = BeanCopyUtils.copyBeanList(page.getRecords(), ArticleListVo.class);

        PageVo pageVo = new PageVo(articleListVos, page.getTotal());
        return ResponseResult.okResult(pageVo);
    }

    @Override
    public ResponseResult getArticleDetail(Long id) {
        //根据id查询文章
        Article article = getById(id);
        //转换成VO
        ArticleDetailVo articleDetailVo = BeanCopyUtils.copyBean(article, ArticleDetailVo.class);
        //根据分类id查询分类名
        Long categoryId = articleDetailVo.getCategoryId();
        Category category = categoryService.getById(categoryId);
        if (category != null) {
            articleDetailVo.setCategoryName(category.getName());
        }
        //封装响应返回
        return ResponseResult.okResult(articleDetailVo);
    }

    @Override
    public ResponseResult addViewCount(Long id) {
        UpdateWrapper<Article> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", id);
        Article article = getById(id);
        updateWrapper.set("view_count", article.getViewCount() + 1);
        boolean update = update(null, updateWrapper);
        if (update) {
            return ResponseResult.okResult();
        }
        return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
    }

    @Override
    public ResponseResult passArticle(Long id) {
        Article byId = getById(id);
        byId.setStatus(String.valueOf(SystemConstants.ARTICLE_STATUS_NORMAL));
       if(updateById(byId)) {
           return ResponseResult.okResult();
       }
        return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
    }

    @Override
    public ResponseResult deleteArticle(Long id) {
        Article byId = getById(id);
        byId.setStatus(String.valueOf(SystemConstants.ARTICLE_STATUS_REJECT));
        if(updateById(byId)) {
            return ResponseResult.okResult();
        }
        return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
    }

    @Override
    public ResponseResult getDraft() {
        LambdaQueryWrapper<Article> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        //设置 查询提交检测的文章
        lambdaQueryWrapper.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_WAITTOCHECK);
        // 第一步查询数据 转化为流
        List<Article> collect = list(lambdaQueryWrapper).stream()
                //设置分类名
                .map(article -> article.setCategoryName(categoryService.getById(article.getCategoryId()).getName()))
                //收集成列表
                .collect(Collectors.toList());
        //转化为 最终目标对象
        List<ArticleListVo> articleListVos = BeanCopyUtils.copyBeanList(collect, ArticleListVo.class);
        return ResponseResult.okResult(articleListVos);
    }

    @Override
    public ResponseResult addArticle(Article article,String status) {
        article.setCreateBy(SecurityUtils.getUserId());
        article.setCreateTime(new Date());
        /*更新文章状态*/
        article.setStatus(String.valueOf(SystemConstants.ARTICLE_STATUS_WAITTOCHECK).equals(status)?status : String.valueOf(SystemConstants.ARTICLE_STATUS_DRAFT));
        //如果id==-1 说明是新文章
        if (article.getId() > 0) {
            LambdaUpdateWrapper<Article> lambdaUpdate = new LambdaUpdateWrapper<>();
            lambdaUpdate.eq(Article::getId, article.getId());
            if (update(article, lambdaUpdate)) {
                return ResponseResult.okResult();
            }
        }
        article.setId(null);
        if (save(article)) {
            return ResponseResult.okResult();
        }
        return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
    }

    @Override
    /**
     * 模糊查询
     */
    public ResponseResult getArticlesDim(String str) {
        LambdaQueryWrapper<Article> wrapper = new LambdaQueryWrapper<>();
        //查询当前用户的文章
        wrapper.eq(Article::getCreateBy, SecurityUtils.getUserId());
        //   添加模糊 查询
        wrapper.like(Article::getTitle, str);
        Page<Article> pPage = new Page<>();
        page(pPage, wrapper);
        List<Article> records = pPage.getRecords();
        // 写入 分类名查询categoryName
        records.stream()
                .map(article -> article.setCategoryName(categoryService.getById(article.getCategoryId()).getName()))
                .collect(Collectors.toList());
        //封装查询结果
        List<ArticleListVo> articleListVos = BeanCopyUtils.copyBeanList(pPage.getRecords(), ArticleListVo.class);
        PageVo pageVo = new PageVo(articleListVos, pPage.getTotal());
        return ResponseResult.okResult(pageVo);
    }

    @Override
    public ResponseResult getArticleTotle() {
        return ResponseResult.okResult(count());
    }

    @Override
    public ResponseResult getArticleBroTotle() {

        List<Article> list = list();
        Optional<Long> reduce = list.stream().map(Article::getViewCount).reduce(Long::sum);

        return ResponseResult.okResult(reduce.get());
    }

    @Override
    public ResponseResult getNewArticlesTotle() {
        LambdaQueryWrapper<Article> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_WAITTOCHECK);
        List<Article> list = list(lambdaQueryWrapper);
        long count = list.stream().map((Function<Article, Object>) article -> 1).count();
        return ResponseResult.okResult(count);
    }

    @Override
    public ResponseResult getNewDailyArticles() {
        List<UserCount> my_article = userMapper.getSamthingCount("my_article");
      //过滤掉日期为空的天
        List<UserCount> collect = my_article.stream()
                .filter(userCount -> Objects.nonNull(userCount.getTime()))
                .distinct()
                .collect(Collectors.toList());
        return ResponseResult.okResult(collect);
    }

}