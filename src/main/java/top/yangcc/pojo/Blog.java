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
@Table(name="t_blog")
public class Blog {
    @Id
    @GeneratedValue
    private Long id;
    /**标题*/
    private String title;
    /**内容,当超过长度时候会转换*/
    @Basic(fetch = FetchType.LAZY)
    @Lob
    private String content;
    /**图片*/
    private String firstPicture;
    /**摘要*/
    private String description;
    /**是否原创*/
    private String flag;
    /**浏览量*/
    private Integer views ;
    /**转载声明*/
    private boolean shareStatement;
    /**可评论*/
    private boolean commentabled;
    /**公开*/
    private boolean published;
    /**推荐*/
    private boolean recommend;
    /**创建时间*/
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;
    /**最后更新时间*/
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTime;

    @ManyToOne
    private Type type;

    @ManyToMany(cascade = {CascadeType.PERSIST})
    private List<Tag> tags =  new ArrayList<>();

    @ManyToOne
    private User user;

    @OneToMany(mappedBy = "blog")
    private List<Comment> comments=  new ArrayList<>();

    /**保存tags*/
    @Transient
    private String tagIds;


    /**
     * 初始化所有的Tag，赋值给TagIDs
     */
    public void init(){
        tagIds = tagsToIds(tags);
    }

    /**
     * 拿到所有的Tag，变为String
     */
    private String tagsToIds(List<Tag> tags){
        if (!tags.isEmpty()){
            StringBuffer sb = new StringBuffer();
            //标记，每次让前面加上，让第一个不加
            boolean flag = false;
            for (Tag tag : tags) {
                if(flag){
                    sb.append(",");
                }else {
                    flag=true;
                }
                sb.append(tag.getId());
            }
            return sb.toString();
        }else{
            return tagIds;
        }
    }

}
