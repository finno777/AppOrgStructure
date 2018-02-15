package com.digdes.rst.orgstructure.persistance.model;

import com.fasterxml.jackson.annotation.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.hibernate.annotations.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;
import java.util.List;

import static org.hibernate.annotations.OnDeleteAction.NO_ACTION;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@Table(name = "organ")
//@Cacheable
//@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
//@EqualsAndHashCode(exclude = {"attachments", "people", "id"})
public class Organ {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "organ_id")
    @ApiModelProperty("id Органа")
    private Long id;
    @Column(length = 128)
    @ApiModelProperty("Наименование органа управления")
    private String name;
    @Column(length = 8192)
    @ApiModelProperty("Описание")
    private String description;
    @Column
    @ApiModelProperty("Документы")
    private String documents;
    @Column(length = 16384)
    @ApiModelProperty("Принятые решения по рассмотрению проектов и иных документов")
    private String decision;
    @Column(length = 4096)
    @ApiModelProperty("График заседаний")
    private String schedule;
    @Column(length = 4096)
    @ApiModelProperty("Условия присутствия на заседании")
    private String conditions;
    @Column(length = 32768)
    @ApiModelProperty("Заседания")
    private String meeting;
    @Column(length = 4096)
    @ApiModelProperty("План работы")
    private String plan;
    @Column(length = 1024)
    @ApiModelProperty("Контактнеы данные")
    private String details;
    @Column(length = 65536)
    @ApiModelProperty("Положение об общественном совете при органе власти")
    private String situation;
    @Column
    @ApiModelProperty("Позиция на сайте")
    private Integer sitePosition;
    @Column
    private Date creationDate;
    @Column
    @ApiModelProperty("Дата последнего изменения")
    private Date lastUpdate;


    @ManyToOne
    @OnDelete(action = NO_ACTION)
    @ApiModelProperty("Связь коллегии и портлета")
    private Application application;

    //@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @ManyToMany(mappedBy = "organs", cascade = CascadeType.ALL)
    @JsonBackReference
    List<Person> people;

    @ApiModelProperty(value = "Файлы", required = false)
    @Transient
    private List<Attachment> attachments;

    @Column(name = "search_index")
    String searchId;

    @Override
    public String toString() {
        return "Organ{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", documents='" + documents + '\'' +
                ", decision='" + decision + '\'' +
                ", schedule='" + schedule + '\'' +
                ", conditions='" + conditions + '\'' +
                ", meeting='" + meeting + '\'' +
                ", plan='" + plan + '\'' +
                ", details='" + details + '\'' +
                ", situation='" + situation + '\'' +
                ", sitePosition=" + sitePosition +
                ", application=" + application +
                ", attachments=" + attachments +
                ", people=" + people +
                '}';
    }

    public Organ(String name) {
        this.name = name;
    }

    public void mapOrgan(Organ organ) {
        this.name = organ.getName();
        this.description = organ.getDescription();
        this.documents = organ.getDocuments();
        this.decision = organ.getDecision();
        this.schedule = organ.getSchedule();
        this.conditions = organ.getConditions();
        this.meeting = organ.getMeeting();
        this.details = organ.getDetails();
        this.situation = organ.getSituation();
        this.sitePosition = organ.getSitePosition();
        this.application = organ.getApplication();
        this.people = organ.getPeople();
        this.attachments = organ.getAttachments();
        this.plan = organ.getPlan();
    }
}
