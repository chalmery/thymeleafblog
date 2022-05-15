package top.yangcc.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import top.yangcc.pojo.Blog;
import top.yangcc.pojo.Tag;

import java.util.List;

/**
 *
 * @author yangcc
 */
@Repository
public interface BlogRepository extends JpaRepository<Blog,Long>,
        JpaSpecificationExecutor<Blog> {


    /**
     * 查询推荐的文章
     * @param pageable pageable
     * @return list
     */
    @Query("select b from Blog b where b.commentabled=true")
    List<Blog> findTop(Pageable pageable);



    /**
     * 查询推荐的文章
     * @param pageable pageable
     * @param query query
     * @return list
     */
    @Query("select b from Blog b where b.title like ?1 or b.content like ?1")
    Page<Blog> findByQuery(Pageable pageable, String query);


    /**
     * 浏览次数
     * @param id id
     * @return int
     */
    @Modifying
    @Query("update Blog b set b.views = b.views+1 where b.id = ?1")
    int updateViews(Long id);

    /**
     * 归档查询
     * @return list
     */
    @Query("select function('date_format',b.updateTime,'%Y') as year from Blog b group by function('date_format',b.updateTime,'%Y') order by function('date_format',b.updateTime,'%Y') DESC ")
    List<String> findGroupYear();

    /**
     * 归档 根据年份查询
     * @param year year
     * @return list
     */
    @Query("select b from Blog b where function('date_format',b.updateTime,'%Y') = ?1")
    List<Blog> findByYear(String year);
}
