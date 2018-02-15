package com.digdes.rst.orgstructure.persistance.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.OnDelete;

import javax.persistence.*;

import java.util.List;

import static org.hibernate.annotations.OnDeleteAction.NO_ACTION;

@ApiModel(value="Organization", description="Модель организации")
@Data
@NoArgsConstructor
@Entity
public class Organization {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty(value = "Полное наименование", required = true)
    @Column(length = 248, nullable = false)
    private String title;

    @ApiModelProperty(value = "Краткое наименование", required = true)
    @Column(length = 64, nullable = false)
    private String titleShort;

    @ApiModelProperty(value = "Описание", required = true)
    @Column(length = 8192, nullable = false)
    private String description;

    @ApiModelProperty(value = "Адрес", required = false)
    @Column(length = 128, nullable = true)
    private String address;

    @ApiModelProperty(value = "Фактический адрес", required = false)
    @Column(length = 128, nullable = true)
    private String addressActual;

    @ApiModelProperty(value = "Телефоны", required = false)
    @OneToMany(mappedBy = "organization",cascade =  {CascadeType.ALL}, orphanRemoval=true )
    @JsonIgnoreProperties(value = { "organization" })
    private List<OrganizationPhone> phones;

    @ApiModelProperty(value = "Факс", required = false)
    @Column(length = 64, nullable = true)
    private String fax;

    @ApiModelProperty(value = "E-mail", required = false)
    @Column(length = 64, nullable = true)
    private String mail;

    @ApiModelProperty(value = "Положение", required = false)
    @Column(length = 49152, nullable = true)
    private String provision;

    @ApiModelProperty(value = "Сайт", required = false)
    @Column(length = 64, nullable = true)
    private String site;

    @ApiModelProperty(value = "ссылка на иконку", required = true)
    @Column(nullable = false, length = 255)
    private String uuid;

    @ApiModelProperty(value = "Родительский Тип организаций", required = false)
    @ManyToOne(fetch = FetchType.EAGER)
    private TypeOrganization typeOrganization;

    @ApiModelProperty(value = "Родительская подгруппа", required = false)
    @ManyToOne
    private SubGroupOrganizations subGroupOrganizations;

    @ApiModelProperty(value = "Файлы", required = false)
    @Transient
    private List<Attachment> attachments;

    @ApiModelProperty(value = "Сотрудники", required = false)
    @ManyToMany(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "organization" })
    private List<OrganizationPerson> people;

    @ApiModelProperty("Удалена")
    @Column(nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean deleted;

    @Transient
    @ApiModelProperty("Ссылка на элемент")
    private String link;

    @Override
    public String toString() {
        return "Organization{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", titleShort='" + titleShort + '\'' +
                ", description='" + description + '\'' +
                ", address='" + address + '\'' +
                ", addressActual='" + addressActual + '\'' +
                ", phones=" + phones +
                ", fax='" + fax + '\'' +
                ", mail='" + mail + '\'' +
                ", provision='" + provision + '\'' +
                ", site='" + site + '\'' +
                ", uuid=" + uuid +
                (typeOrganization!=null?", typeOrganization={id=" + typeOrganization.getId() +"}":"") +
                (subGroupOrganizations!=null?", subGroupOrganizations={id=" + subGroupOrganizations.getId() +"}":"") +
                ", attachments=" + attachments +
                ", people=" + people +
                ", deleted=" + deleted +
                ", link='" + link + '\'' +
                '}';
    }
}
