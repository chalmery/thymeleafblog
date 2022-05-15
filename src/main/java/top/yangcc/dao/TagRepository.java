package top.yangcc.dao;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import top.yangcc.pojo.Tag;
import top.yangcc.pojo.Type;

import java.util.List;

/**
 *
 * @author yangcc
 */
@Repository
public interface TagRepository extends JpaRepository<Tag,Long> {

    /**
     * 根据名称，查询
     * @param name name
     * @return tag
     */
    Tag findByName(String name);


    /**
     * 指定条件查询
     * @param pageable 封装的pageable对象，
     * @return list
     */
    @Query("select t from Tag  t")
    List<Tag> findTop(Pageable pageable);
}
