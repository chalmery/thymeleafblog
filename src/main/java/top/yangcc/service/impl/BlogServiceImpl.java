package top.yangcc.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.yangcc.dao.BlogRepository;
import top.yangcc.pojo.Blog;
import top.yangcc.pojo.Type;
import top.yangcc.service.BlogService;
import top.yangcc.utils.MarkDownUtils;
import top.yangcc.utils.MyBeanUtils;
import top.yangcc.vo.BlogQuery;

import javax.persistence.criteria.*;
import java.util.*;

/**
 * @author yangcc
 */
@Service
@Transactional
public class BlogServiceImpl implements BlogService {
    @Autowired
    private BlogRepository blogRepository;

    @Override
    public Blog getBlog(Long id) {
        return blogRepository.findById(id).orElse(null);
    }


    /**
     * 分页条件查询
     */
    @Override
    public Page<Blog> listBlog(Pageable pageable, BlogQuery blog) {
        Specification<Blog> specification = new Specification<Blog>() {
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
                // 条件集合
                List<Predicate> predicateList = new ArrayList<>();
                //判断查询条件是否有标题
                if (blog.getTitle() !=null && !"".equals(blog.getTitle())){
                    // 如果有标题，条件查询
                    predicateList.add(cb.like(root.get("title"),"%"+blog.getTitle()+"%"));
                }
                //如果有对应id的type，则查询
                if (blog.getTypeId()!=null){
                    predicateList.add(cb.equal(root.<Type>get("type").get("id"),blog.getTypeId()));
                }
                // 如果是被推荐的
                if (blog.isRecommend()){
                     predicateList.add(cb.equal(root.<Boolean>get("recommend"),blog.isRecommend()));
                }
                cq.where(predicateList.toArray(new Predicate[predicateList.size()]));
                return null;
            }
        };
        return blogRepository.findAll(specification,pageable);
    }

    /**
     * 分页查询，只查询
     */
    @Override
    public Page<Blog> listBlog(Pageable pageable) {
        return blogRepository.findAll(pageable);
    }

    @Override
    public Page<Blog> listBlog(Pageable pageable, String query) {
        return blogRepository.findByQuery(pageable,query);
    }

    @Override
    public Page<Blog> listBlog(Long tagId, Pageable pageable) {
        return blogRepository.findAll(new Specification<Blog>() {
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
                Join join = root.join("tags");
                return cb.equal(join.get("id"),tagId);
            }
        },pageable);
    }

    @Override
    public Blog saveBlog(Blog blog) {
        //if id==null 表示是 新增
        if (blog.getId()==null){
            // 新建的时候初始化
            blog.setCreateTime(new Date());
            blog.setUpdateTime(new Date());
            blog.setViews(0);
        }
        else{
            blog.setUpdateTime(new Date());
        }
        return blogRepository.save(blog);
    }

    /**
     * 更新
     * @param id id
     */
    @Override
    public Blog updateBlog(Long id, Blog blog) {
        Blog blog1 = blogRepository.findById(id).orElse(null);
        if (blog1 ==null){
            throw  new RuntimeException("没有对应id的值");
        }
        BeanUtils.copyProperties(blog,blog1, MyBeanUtils.getNullPropertyNames(blog));
        blog1.setUpdateTime(new Date());
        return blogRepository.save(blog1);
    }

    /**
     * 删除
     * @param id id
     */
    @Override
    public void deleteBlog(Long id) {
        blogRepository.deleteById(id);
    }

    /**
     * 展示推荐文章
     * @param size 数量
     * @return
     */
    @Override
    public List<Blog> listRecommendBlogTop(Integer size) {
        //排序对象
        Sort sort = Sort.by(Sort.Direction.DESC, "updateTime");
        //分页对象，当前第几页，每页大小，排序条件对象
        PageRequest of = PageRequest.of(0, size, sort);
        return blogRepository.findTop(of);
    }

    /**
     * 转换
     * @param id id
     * @return blog
     */
    @Override
    public Blog getBlogAndConvert(Long id) {
        Blog blog = blogRepository.findById(id).orElse(null);
        if (blog==null){
            throw new RuntimeException("不存在此文章");
        }
        //这里必须新建一个Blog对象，因为jpa的原因，如果修改原对象，就修改数据库
        Blog b = new Blog();
        BeanUtils.copyProperties(blog,b);
        String content = b.getContent();
        //转换
        String s = MarkDownUtils.markdownToHtmlExtensions(content);
        b.setContent(s);
        /*浏览次数*/
        blogRepository.updateViews(id);
        return b;
    }


    @Override
    public Map<String, List<Blog>> archiveBlog() {
        List<String> years = blogRepository.findGroupYear();
        Map<String, List<Blog>> map = new HashMap<>(16);
        for (String year : years){
            map.put(year,blogRepository.findByYear(year));
        }
        return map;
    }

    @Override
    public Long countBlog() {
        return blogRepository.count();
    }
}
