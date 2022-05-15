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
@Table(name="t_comment")
public class Comment {
    @Id
    @GeneratedValue
    private Long id;
    /**昵称*/
    private String nickname;
    /**邮箱*/
    private String email;
    /**内容*/
    private String content;
    /**头像*/
    private String avatar;
    /**创建时间*/
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;

    /**对个评论对应一个博客*/
    @ManyToOne
    private Blog blog;

    /**一个评论根节点对应多个子节点*/
    @OneToMany(mappedBy = "parentComment")
    private List<Comment> commentList=  new ArrayList<>();

    /** 多个评论对应一根节点 */
    @ManyToOne
    private Comment parentComment;

    private boolean adminComment;
}
