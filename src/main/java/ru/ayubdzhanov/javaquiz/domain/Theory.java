package ru.ayubdzhanov.javaquiz.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import java.util.LinkedList;
import java.util.List;

@Getter
@Setter
@Entity
@JsonIgnoreProperties({"description", "parsedDescription", "category", "attachments"})
public class Theory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    private String description;

    @Transient
    private String parsedDescription;

    @ManyToOne
    private Category category;

    @OneToMany(mappedBy = "theory", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Attachment> attachments;

    public List<Attachment> getAttachments() {
        if (attachments == null) attachments = new LinkedList<>();
        return attachments;
    }
}
