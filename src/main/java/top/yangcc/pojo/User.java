package top.yangcc.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author yangcc
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="t_user")
public class User {
    @Id
    @GeneratedValue
    private Long id;
    /**昵称*/
    private String nickname;
    /** 用户名*/
    private String username;
    /**密码*/
    private String password;
    /**邮箱*/
    private String email;
    /**头像*/
    private String avatar;
    /**类型*/
    private Integer type;
    /**创建时间*/
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;
    /**最后更新时间*/
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTime;

    @OneToMany(mappedBy = "user")
    private List<Blog> blogs=  new ArrayList<>();


}
