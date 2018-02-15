package com.digdes.rst.orgstructure.persistance.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.OnDelete;

import javax.persistence.*;

import static org.hibernate.annotations.OnDeleteAction.CASCADE;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data
//@Cacheable
//@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Attribute {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    @ApiModelProperty("id")
    private Long id;

    @Column(length = 128)
    @ApiModelProperty("Ключевое название")
    private String key;

    @Column(length = 16384)
    @ApiModelProperty("Значение")
    private String value;

    @ManyToOne
    @OnDelete(action = CASCADE)
    @ApiModelProperty("Связь человека и его атрибутов")
  //  @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonManagedReference
    private Person person;

    @Override
    public String toString() {
        return "Attribute{" +
                "id=" + id +
                ", key='" + key + '\'' +
                ", value='" + value + '\'' +
                ", person={id=" + person.getId() + "}" +
                '}';
    }
}
