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
import top.yangcc.pojo.Type;
import top.yangcc.service.TypeService;

/**
 * 类型相关的controller
 * @author yangcc
 */
@Controller
@RequestMapping("/back")
public class TypeController {

    @Autowired
    private TypeService typeService;

    /**
     * 默认的分页设置
     */
    @GetMapping("/types")
    public String list(@PageableDefault(
            size = 8,sort = {"id"},direction = Sort.Direction.DESC)
                                   Pageable pageable, Model model){
        // 根据前端的分页数据进行查询
        Page<Type> types = typeService.listType(pageable);
        // 放入model
        model.addAttribute("page",types);

        return "back/types";
    }

    /**
     * 跳转到新增type
     */
    @GetMapping("/types/input")
    public String input(Model model){
        model.addAttribute("type",new Type());
        return "back/types-input";
    }

    /**
     * 增加type
     */
    @PostMapping("/types")
    public String post(Type type, BindingResult result, RedirectAttributes attributes){
        if (type == null){
            result.rejectValue("name","nameError","不能为空");
            return "back/types-input";
        }
        if(typeService.getTypeByName(type.getName())!=null){
            result.rejectValue("name","nameError","已有该分类");
            return "back/types-input";
        }
        Type type1 = typeService.saveType(type);
        if (type1 == null){
            attributes.addFlashAttribute("message","操作失败");
        }
        else{
            attributes.addFlashAttribute("message","操作成功");
        }
        return "redirect:/back/types";
    }

    /**
     * 修改页面跳转
     */
    @GetMapping("/types/{id}/input")
    public String editInput(@PathVariable Long id, Model model){
        model.addAttribute("type",typeService.getType(id));
        return "back/types-input";
    }


    /**
     * 修改标签
     */
    @PostMapping("/types/{id}")
    public String editPost(@PathVariable Long id, Type type, BindingResult result, RedirectAttributes attributes){
        if (type == null){
            result.rejectValue("name","nameError","不能为空");
            return "back/types-input";
        }
        if(typeService.getTypeByName(type.getName())!=null){
            result.rejectValue("name","nameError","已有该分类");
            return "back/types-input";
        }
        Type type1 = typeService.updateType(id,type);
        if (type1 == null){
            attributes.addFlashAttribute("message","更新失败");
        }
        else{
            attributes.addFlashAttribute("message","更新成功");
        }
        return "redirect:/back/types";
    }

    /**
     * 删除
     */
    @GetMapping("/types/{id}/delete")
    public String delete(@PathVariable Long id,RedirectAttributes attributes){
        typeService.deleteType(id);
        attributes.addFlashAttribute("message","删除成功");
        return "redirect:/back/types";
    }
}
