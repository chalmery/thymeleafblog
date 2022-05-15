package top.yangcc.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.NotFound;

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
@Table(name="t_type")
public class Type {
    @Id
    @GeneratedValue
    private Long id;
    /**分类的名字*/
    private String name;

    /**一对多的type和Blog*/
    @OneToMany(mappedBy = "type")
    private List<Blog> blogs=  new ArrayList<>();
}
