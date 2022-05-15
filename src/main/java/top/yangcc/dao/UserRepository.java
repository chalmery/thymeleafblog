package top.yangcc.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import top.yangcc.pojo.User;

/**
 *
 * @author yangcc
 */
public interface UserRepository extends JpaRepository<User,Long> {

    /**
     * 查询用户信息
     * @param username 账号
     * @param password 密码
     * @return user
     */
    User findByUsernameAndPassword(String username, String password);

}
