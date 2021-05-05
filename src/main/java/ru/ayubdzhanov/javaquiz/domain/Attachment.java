package ru.ayubdzhanov.javaquiz.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

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

    public enum Type {
        VIDEO, PICTURE
    }
}
