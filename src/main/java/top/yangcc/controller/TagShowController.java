package top.yangcc.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import top.yangcc.pojo.Tag;
import top.yangcc.service.BlogService;
import top.yangcc.service.TagService;

import java.util.List;


/**
 * tag
 * @author yangcc
 */
@Controller
public class TagShowController {

    @Autowired
    private TagService tagService;

    @Autowired
    private BlogService blogService;


    @GetMapping("/tags/{id}")
    public String tags(@PageableDefault(size = 6, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageabl,
                       @PathVariable Long id,
                       Model model) {

        List<Tag> tags = tagService.listTagTop(10000);
        if (id == -1) {
            id = tags.get(0).getId();
        }
        model.addAttribute("tags", tags);
        model.addAttribute("page", blogService.listBlog(id, pageabl));
        model.addAttribute("activeTagId", id);
        return "tags";
    }
}
