package top.yangcc.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import top.yangcc.pojo.Tag;

import java.util.List;

/**
 * @author yangcc
 */
public interface TagService {
    /**
     * 保存类型
     * @param tag 类型
     * @return tag
     */
    Tag saveTag(Tag tag);

    /**
     * 获取类型
     * @param id id
     * @return tag
     */
    Tag getTag(Long id);

    /**
     * 根据名称，查询
     * @param name name
     * @return tag
     */
    Tag getTagByName(String name);

    /**
     * 分页查询
     * @param pageable jpa的对象
     * @return 返回page
     */
    Page<Tag> listTag(Pageable pageable);

    /**
     *拿到全部的tag
     * @return list
     */
    List<Tag> listTag();

    /**
     *拿到全部的tag
     * @param size size
     * @return list
     */
    List<Tag> listTagTop(Integer size);

    /**
     *拿到全部的tag
     * @param ids id
     * @return list
     */
    List<Tag> listTag(String ids);

    /**
     * 修改
     * @param id id
     * @param tag 新的类型
     * @return tag
     */
    Tag updateTag(Long id,Tag tag);

    /**
     * 根据id删除
     * @param id id
     */
    void deleteTag(Long id);
}
