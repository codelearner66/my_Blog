package com.blog.constants;

public class SystemConstants
{

    /**
     *  文章是正常分布状态
     */
    public static final int ARTICLE_STATUS_NORMAL = 0;

    /**
     *  文章是草稿
     */
    public static final int ARTICLE_STATUS_DRAFT = 1;
    /*
    * 文章已提交审核
     */
    public static final int ARTICLE_STATUS_WAITTOCHECK = 2;
    /**
     * 文章被驳回 未审核过
     */
    public static final int ARTICLE_STATUS_REJECT = 3;


    public static final String  STATUS_NORMAL = "0";
    /**
     * 友链状态为审核通过
     */
    public static final String  LINK_STATUS_NORMAL = "0";
    /**
     * 评论类型为：文章评论
     */
    public static final String ARTICLE_COMMENT = "0";
    /**
     * 评论类型为：友联评论
     */
    public static final String LINK_COMMENT = "1";
}