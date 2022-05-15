package top.yangcc.dao;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import top.yangcc.pojo.Type;

import java.util.List;

/**
 *
 * @author yangcc
 */
@Repository
public interface TypeRepository extends JpaRepository<Type,Long> {

    /**
     * 根据名称，查询
     * @param name name
     * @return type
     */
    Type findByName(String name);


    /**
     * 查询数量最多的type
     * @param pageable pageable
     * @return list
     */
    @Query("select t from Type t")
    List<Type> findTop(Pageable pageable);
}
