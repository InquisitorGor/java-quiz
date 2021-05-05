package ru.ayubdzhanov.javaquiz.domain;

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
import java.util.LinkedList;
import java.util.List;

@Getter
@Setter
@Entity
public class Theory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    private String description;

    @ManyToOne
    private Category category;

    @OneToMany(mappedBy = "theory", cascade = CascadeType.ALL)
    private List<Attachment> attachment;

    public List<Attachment> getAttachment() {
        if (attachment == null) attachment = new LinkedList<>();
        return attachment;
    }
}
