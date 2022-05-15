package top.yangcc.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.yangcc.dao.TagRepository;
import top.yangcc.pojo.Tag;
import top.yangcc.service.TagService;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yangcc
 */
@Service
@Transactional
public class TagServiceImpl implements TagService {

    @Autowired
    public TagRepository tagRepository;

    @Override
    public Tag saveTag(Tag tag) {
        return tagRepository.save(tag);
    }

    /**
     * 获取类型
     * @param id id
     * @return tag
     */
    @Override
    public Tag getTag(Long id) {
        return tagRepository.findById(id).orElse(null);
    }

    /**
     * 根据名称，查询
     * @param name name
     * @return tag
     */
    @Override
    public Tag getTagByName(String name) {
        return tagRepository.findByName(name);
    }

    /**
     * 分页查询
     * @param pageable jpa的对象
     * @return 返回page
     */
    @Override
    public Page<Tag> listTag(Pageable pageable) {
        return tagRepository.findAll(pageable);
    }

    /**
     *拿到全部的tag
     * @return list
     */
    @Override
    public List<Tag> listTag() {
        return tagRepository.findAll();
    }

    /**
     *拿到全部的tag
     * @param size size
     * @return list
     */
    @Override
    public List<Tag> listTagTop(Integer size) {
        // 排序对象，倒叙
        Sort sort =Sort.by(Sort.Direction.DESC,"blogs.size");
        //分页对象，
        Pageable pageable = PageRequest.of(0,size,sort);
        return tagRepository.findTop(pageable);
    }

    /**
     * 根据ids拿到全部的tag
     * @param ids id
     * @return
     */
    @Override
    public List<Tag> listTag(String ids) {
        return tagRepository.findAllById(convertList(ids));
    }

    /**
     *根据字符串1,2,3切分为集合
     */
    private List<Long> convertList(String ids){
        List<Long> list = new ArrayList<>();
        if (ids !=null && ids.length() !=0){
            String[] split = ids.split(",");
            for (String s : split) {
                list.add(new Long(s));
            }
        }
        return list;
    }


    /**
     * 修改
     * @param id id
     * @param tag 新的类型
     * @return tag
     */
    @Override
    public Tag updateTag(Long id, Tag tag) {
        // 先查询
        Tag one = tagRepository.findById(id).orElse(null);
        //
        if (one==null){
            throw  new RuntimeException("没有对应id的值");
        }
        //将前台的值copy到查询的对象
        BeanUtils.copyProperties(tag,one);
        return tagRepository.save(one);
    }

    /**
     * 根据id删除
     * @param id id
     */
    @Override
    public void deleteTag(Long id) {
        tagRepository.deleteById(id);
    }
}
