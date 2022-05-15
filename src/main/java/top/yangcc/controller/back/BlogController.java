package top.yangcc.controller.back;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import top.yangcc.pojo.Blog;
import top.yangcc.pojo.User;
import top.yangcc.service.BlogService;
import top.yangcc.service.TagService;
import top.yangcc.service.TypeService;
import top.yangcc.vo.BlogQuery;

import javax.servlet.http.HttpSession;

/**
 * @author yangcc
 */
@Controller
@RequestMapping("/back")
public class BlogController {
    @Autowired
    private BlogService blogService;
    @Autowired
    private TypeService typeService;
    @Autowired
    private TagService tagService;
    /**
     * 新增跳转
     */
    @GetMapping("/blogs/input")
    public String input(Model model){
        model.addAttribute("blog",new Blog());
        // 拿到所有的标签，分类
        model.addAttribute("types",typeService.listType());
        model.addAttribute("tags",tagService.listTag());
        return "back/blogs-input";
    }
    /**
     * 新增数据
     */
    @PostMapping("/blogs")
    public String post(Blog blog, RedirectAttributes attributes, HttpSession session){
        if ("".equals(blog.getFlag())){
            blog.setFlag("原创");
        }
        blog.setUser((User) session.getAttribute("user"));
        //设置此blog对应的type，tag
        blog.setType(typeService.getType(blog.getType().getId()));
        blog.setTags(tagService.listTag(blog.getTagIds()));

        //保存
        Blog b ;
        if (blog.getId()==null){
            b=blogService.saveBlog(blog);
        }else {
            b=blogService.updateBlog(blog.getId(),blog);
        }
        if (b == null){
            attributes.addFlashAttribute("message","操作失败");
        }
        else{
            attributes.addFlashAttribute("message","操作成功");
        }
        return "redirect:/back/blogs";
    }


    /**
     * 修改跳转
     */
    @GetMapping("/blogs/{id}/input")
    public String editInput(Model model, @PathVariable Long id){
        // 拿到所有的标签，分类
        model.addAttribute("types",typeService.listType());
        model.addAttribute("tags",tagService.listTag());
        //设置标签
        Blog blog = blogService.getBlog(id);
        blog.init();
        model.addAttribute("blog",blog);
        return "back/blogs-input";
    }

    /**
     *分页查询
     */
    @GetMapping("/blogs")
    public String blogs(@PageableDefault(size = 8,sort = {"updateTime"},direction = Sort.Direction.DESC) Pageable pageable,
                        BlogQuery blog, Model model){
        //查询所有的分类
        model.addAttribute("types",typeService.listType());
        // 查询分页数据
        model.addAttribute("page",blogService.listBlog(pageable,blog));
        return "back/blogs";
    }

    /**
     * 分页搜索
     */
    @PostMapping("/blogs/search")
    public String search(@PageableDefault(size = 2,sort = {"updateTime"},direction = Sort.Direction.DESC) Pageable pageable,
                         BlogQuery blog, Model model){
        model.addAttribute("page",blogService.listBlog(pageable,blog));
        return "back/blogs :: blogList";
    }


    /**
     * 删除
     */
    @GetMapping("/blogs/{id}/delete")
    public String delete(@PathVariable Long id,RedirectAttributes attributes){
        blogService.deleteBlog(id);
        attributes.addFlashAttribute("message","删除成功");
        return "redirect:/back/blogs";
    }
}
