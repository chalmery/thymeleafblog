package top.yangcc.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import top.yangcc.pojo.Blog;
import top.yangcc.vo.BlogQuery;

import java.util.List;
import java.util.Map;

/**
 * @author yangcc
 */
public interface BlogService {
    /**
     * 获取blog
     * @param id id
     * @return blog
     */
    Blog getBlog(Long id);

    /**
     * 分页条件查询
     * @param pageable pageable
     * @param blog blog
     * @return Page
     */
    Page<Blog> listBlog(Pageable pageable, BlogQuery blog);

    /**
     * 条件查询
     * @param pageable pageable
     * @return Page
     */
    Page<Blog> listBlog(Pageable pageable);

    /**
     * 条件查询
     * @param pageable pageable
     * @param query query
     * @return Page
     */
    Page<Blog> listBlog(Pageable pageable,String query);


    Page<Blog> listBlog(Long tagId,Pageable pageable);

    /**
     * 保存
     * @param blog blog
     * @return blog
     */
    Blog saveBlog(Blog blog);

    /**
     * 更新
     * @param blog blog
     * @param id id
     * @return blog
     */
    Blog updateBlog(Long id,Blog blog);

    /**
     * 删除
     * @param id id
     */
    void deleteBlog(Long id);


    /**
     * 首页展示推荐的文章
     * @param size 数量
     * @return list
     */
    List<Blog> listRecommendBlogTop(Integer size);


    /**
     *转换
     * @param id id
     */
    Blog getBlogAndConvert(Long id);

    /**
     * 归档查询
     * @return map
     */
    Map<String,List<Blog>> archiveBlog();

    /**
     * 博客总条数
     * @return long
     */
    Long countBlog();
}
