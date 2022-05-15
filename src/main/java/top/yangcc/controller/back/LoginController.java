package top.yangcc.controller.back;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import top.yangcc.pojo.User;
import top.yangcc.service.UserService;
import top.yangcc.utils.Md5Utils;

import javax.servlet.http.HttpSession;

/**
 * @author yangcc
 */
@Controller
@RequestMapping("/back")
public class LoginController {
    @Autowired
    private UserService userService;
    /**访问就定向到登录页*/
    @GetMapping()
    public  String loginPage(){
        return "back/login";
    }

    /**登录*/
    @RequestMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        HttpSession httpSession,
                        RedirectAttributes redirectAttributes){
        User user = userService.checkUser(username, Md5Utils.code(password));
        //如果没问题，保存session
        if (user !=null){
            user.setPassword(null);
            httpSession.setAttribute("user",user);
            return "back/index";
        }else{
            redirectAttributes.addFlashAttribute("message","用户名和密码错误");
            return "redirect:/back";
        }
    }

    /**注销*/
    @GetMapping("/logout")
    public String loginOut(HttpSession httpSession){
        httpSession.removeAttribute("user");
        return "redirect:/back";
    }
}
