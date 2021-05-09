package ru.ayubdzhanov.javaquiz.domain;

import lombok.Getter;
import lombok.Setter;
import ru.ayubdzhanov.javaquiz.util.ViewUtils;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

@Getter
@Setter
@Entity
public class Attachment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String path;

    @Enumerated(EnumType.STRING)
    private Type type;

    @ManyToOne
    @JoinColumn(name = "theory_id")
    private Theory theory;

    @Enumerated(EnumType.STRING)
    private Size size;

    private String caption;

    @Transient
    private ViewUtils viewUtils;

    public enum Type {
        VIDEO, IMAGE
    }

    public enum Size {
        SMALL, AVERAGE, BIG
    }
}
