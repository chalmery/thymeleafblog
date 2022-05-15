package top.yangcc.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import top.yangcc.pojo.Type;

import java.util.List;

/**
 * @author yangcc
 */
public interface TypeService {
    /**
     * 保存类型
     * @param type 类型
     * @return type
     */
    Type saveType(Type type);

    /**
     * 获取类型
     * @param id id
     * @return type
     */
    Type getType(Long id);

    /**
     * 根据名称，查询
     * @param name name
     * @return type
     */
    Type getTypeByName(String name);

    /**
     * 分页查询
     * @param pageable jpa的对象
     * @return 返回page
     */
    Page<Type> listType(Pageable pageable);


    /**
     * 分页查询
     * @param size size
     * @return 返回page
     */
    List<Type> listTypeTop(Integer size);


    /**
     * 查询全部
     * @return list
     */
    List<Type> listType();

    /**
     * 修改
     * @param id id
     * @param type 新的类型
     * @return type
     */
    Type updateType(Long id,Type type);

    /**
     * 根据id删除
     * @param id id
     */
    void deleteType(Long id);


}
