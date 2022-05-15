package top.yangcc.controller.back;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import top.yangcc.pojo.Tag;
import top.yangcc.service.TagService;

/**
 * 类型相关的controller
 * @author yangcc
 */
@Controller
@RequestMapping("/back")
public class TagController {

    @Autowired
    private TagService tagService;

    /**
     * 默认的分页设置
     */
    @GetMapping("/tags")
    public String list(@PageableDefault(
            size = 8,sort = {"id"},direction = Sort.Direction.DESC)
                                   Pageable pageable, Model model){
        // 根据前端的分页数据进行查询
        Page<Tag> tags = tagService.listTag(pageable);
        // 放入model
        model.addAttribute("page",tags);

        return "back/tags";
    }

    /**
     * 跳转到新增tag
     */
    @GetMapping("/tags/input")
    public String input(Model model){
        model.addAttribute("tag",new Tag());
        return "back/tags-input";
    }

    /**
     * tag
     */
    @PostMapping("/tags")
    public String post(Tag tag, BindingResult result, RedirectAttributes attributes){
        if (tag == null){
            result.rejectValue("name","nameError","不能为空");
            return "back/tags-input";
        }
        if(tagService.getTagByName(tag.getName())!=null){
            result.rejectValue("name","nameError","已有该标签");
            return "back/tags-input";
        }
        Tag tag1 = tagService.saveTag(tag);
        if (tag1 == null){
            attributes.addFlashAttribute("message","操作失败");
        }
        else{
            attributes.addFlashAttribute("message","操作成功");
        }
        return "redirect:/back/tags";
    }

    /**
     * 修改页面跳转
     */
    @GetMapping("/tags/{id}/input")
    public String editInput(@PathVariable Long id, Model model){
        model.addAttribute("tag",tagService.getTag(id));
        return "back/tags-input";
    }


    /**
     * 修改标签
     */
    @PostMapping("/tags/{id}")
    public String editPost(@PathVariable Long id, Tag tag, BindingResult result, RedirectAttributes attributes){
        if (tag == null){
            result.rejectValue("name","nameError","不能为空");
            return "back/tags-input";
        }
        if(tagService.getTagByName(tag.getName())!=null){
            result.rejectValue("name","nameError","已有该标签");
            return "back/tags-input";
        }
        Tag tag1 = tagService.updateTag(id,tag);
        if (tag1 == null){
            attributes.addFlashAttribute("message","更新失败");
        }
        else{
            attributes.addFlashAttribute("message","更新成功");
        }
        return "redirect:/back/tags";
    }

    /**
     * 删除
     */
    @GetMapping("/tags/{id}/delete")
    public String delete(@PathVariable Long id,RedirectAttributes attributes){
        tagService.deleteTag(id);
        attributes.addFlashAttribute("message","删除成功");
        return "redirect:/back/tags";
    }
}
