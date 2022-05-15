package top.yangcc.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.yangcc.dao.TypeRepository;
import top.yangcc.pojo.Type;
import top.yangcc.service.TypeService;

import java.util.List;

/**
 * @author yangcc
 */
@Service
@Transactional
public class TypeServiceImpl implements TypeService {

    @Autowired
    public TypeRepository typeRepository;

    @Override
    public Type saveType(Type type) {
        return typeRepository.save(type);
    }

    /**
     * 获取类型
     * @param id id
     * @return type
     */
    @Override
    public Type getType(Long id) {
        return typeRepository.findById(id).orElse(null);
    }

    /**
     * 根据名称，查询
     * @param name name
     * @return type
     */
    @Override
    public Type getTypeByName(String name) {
        return typeRepository.findByName(name);
    }

    /**
     * 分页查询
     * @param pageable jpa的对象
     * @return 返回page
     */
    @Override
    public Page<Type> listType(Pageable pageable) {
        return typeRepository.findAll(pageable);
    }

    /**
     * 分页查询
     * @param size size
     * @return 返回page
     */
    @Override
    public List<Type> listTypeTop(Integer size) {
        // 排序对象，倒叙
        Sort sort =Sort.by(Sort.Direction.DESC,"blogs.size");
        //分页对象，
        Pageable pageable =PageRequest.of(0,size,sort);
        return typeRepository.findTop(pageable);
    }

    /**
     * 查询全部
     */
    @Override
    public List<Type> listType() {
         return typeRepository.findAll();
    }


    /**
     * 修改
     * @param id id
     * @param type 新的类型
     * @return type
     */
    @Override
    public Type updateType(Long id, Type type) {
        // 先查询
        Type one = typeRepository.findById(id).orElse(null);
        //
        if (one==null){
            throw  new RuntimeException("没有对应id的值");
        }
        //将前台的值copy到查询的对象
        BeanUtils.copyProperties(type,one);
        return typeRepository.save(one);
    }

    /**
     * 根据id删除
     * @param id id
     */
    @Override
    public void deleteType(Long id) {
        typeRepository.deleteById(id);
    }
}
