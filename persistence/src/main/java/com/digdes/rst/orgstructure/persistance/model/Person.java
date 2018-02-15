package com.digdes.rst.orgstructure.persistance.model;

import com.fasterxml.jackson.annotation.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.OnDelete;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.hibernate.annotations.OnDeleteAction.NO_ACTION;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
//@Cacheable
//@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "person_id")
    @ApiModelProperty("id")
    private Long id;

    @Column(length = 50)
    @ApiModelProperty("Имя")
    private String name;

    @Column(length = 30)
    @ApiModelProperty("Телефон")
    private String phone;

    @Column
    @ApiModelProperty("Дата создания")
    private Date creationDate;


    @Column
    @ApiModelProperty("Дата последнего изменения")
    private Date lastUpdate;

    @Column
    @ApiModelProperty("Имэйл")
    private String email;

    @Column(length = 1000)
    @ApiModelProperty("Должность")
    private String position;

    @Column
    @Enumerated(EnumType.STRING)
    @ApiModelProperty("Роль")
    private Role role;

    @Column
    @ApiModelProperty("Позиция на сайте")
    private Integer sitePosition;

    @Transient
    @ApiModelProperty("Атрибуты человека")
    //@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonBackReference
    private List<Attribute> attributes;

    @ManyToOne
    @ApiModelProperty("Связь человека и портлета")
    private Application application;

    @Column(name = "search_index")
    String searchId;

    @ApiModelProperty(value = "Файлы")
    @Transient
    private List<Attachment> attachments;

    @ManyToMany(cascade = CascadeType.ALL)
    @OnDelete(action = NO_ACTION)
    @ApiModelProperty("Связь человека и органа управления")
    @JoinTable(name = "Person_Organ", joinColumns = @JoinColumn(name = "person_id", referencedColumnName = "person_id"), inverseJoinColumns = @JoinColumn(name = "organ_id", referencedColumnName = "organ_id"))
    //  @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonManagedReference
    private List<Organ> organs = new ArrayList<>();

    @Transient
    @ApiModelProperty("Курируемые упраления")
    private List<CuratorPerson> curatorGovernments;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Person person = (Person) o;

        if (id != null ? !id.equals(person.id) : person.id != null) return false;
        if (name != null ? !name.equals(person.name) : person.name != null) return false;
        if (phone != null ? !phone.equals(person.phone) : person.phone != null) return false;
        if (position != null ? !position.equals(person.position) : person.position != null) return false;
        if (role != person.role) return false;
        if (attributes != null ? !attributes.equals(person.attributes) : person.attributes != null) return false;
        return application != null ? application.equals(person.application) : person.application == null;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", position='" + position + '\'' +
                ", role=" + role +
                ", sitePosition=" + sitePosition +
                ", attributes=" + attributes +
                ", application=" + application +
                ", curatorGovernments=" + curatorGovernments +
                ", attachments=" + attachments +
                '}';
    }


    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (id != null ? id.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (phone != null ? phone.hashCode() : 0);
        result = 31 * result + (position != null ? position.hashCode() : 0);
        result = 31 * result + (role != null ? role.hashCode() : 0);
        result = 31 * result + (attributes != null ? attributes.hashCode() : 0);
        result = 31 * result + (application != null ? application.hashCode() : 0);
        return result;
    }

    public void addOrgan(Organ organ) {
        if (organs == null)
            organs = new ArrayList<>();
        organs.add(organ);
    }

    public Person(String name) {
        this.name = name;
    }

    public void mapPerson(Person person) {
        this.name = person.getName();
        this.phone = person.getPhone();
        this.email = person.getEmail();
        this.position = person.getPosition();
        this.role = person.getRole();
        this.sitePosition = person.getSitePosition();
        this.application = person.getApplication();
        this.organs = person.getOrgans();

        this.attachments = person.getAttachments();
        this.attributes = person.getAttributes();
        this.curatorGovernments = person.getCuratorGovernments();
    }


    public enum Role {
        MANAGER,
        DEPUTY,
        ADVISER,
        MEMBER
    }
}
