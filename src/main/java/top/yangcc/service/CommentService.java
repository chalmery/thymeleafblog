package top.yangcc.service;

import top.yangcc.pojo.Comment;

import java.util.List;

/**
 * @author yangcc
 */
public interface CommentService {
    /**
     * 获取评论列表
     * @param blogId id
     * @return list
     */
    List<Comment> listCommentByBlogId(Long blogId);

    /**
     * 保存Comment
     * @param comment comment
     * @return comment
     */
    Comment savaComment(Comment comment);
}
