package top.yangcc.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author yangcc
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="t_tag")
public class Tag {
    @Id
    @GeneratedValue
    private Long id;
    /**标签的名字*/
    private String name;

    /**多对多的tag和Blog*/
    @ManyToMany(mappedBy = "tags")
    private List<Blog> blogs=  new ArrayList<>();
}
