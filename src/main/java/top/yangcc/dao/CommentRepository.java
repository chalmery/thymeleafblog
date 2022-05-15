package top.yangcc.dao;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import top.yangcc.pojo.Comment;

import java.util.List;

/**
 * @author yangcc
 */
@Repository
public interface CommentRepository extends JpaRepository<Comment,Long> {

    /**
     * 优化评论
     * @param blogId id
     * @param sort sort
     * @return list
     */
    List<Comment> findByBlogIdAndParentCommentNull(Long blogId, Sort sort);
}