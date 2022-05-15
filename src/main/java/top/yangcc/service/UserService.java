package top.yangcc.service;

import org.springframework.stereotype.Service;
import top.yangcc.pojo.User;

/**
 * @author yangcc
 */
public interface UserService {
    /**
     * 检验用户
     * @param username 用户名
     * @param password 密码
     * @return user
     */
    User checkUser(String username, String password);
}
