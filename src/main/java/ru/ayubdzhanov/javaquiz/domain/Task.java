package ru.ayubdzhanov.javaquiz.domain;

import lombok.Getter;
import lombok.Setter;
import ru.ayubdzhanov.javaquiz.util.ViewUtils;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@Entity
public class Task implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String question;

    private Integer prestige;

    @ManyToOne
    private Category category;

    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL)
    private List<TaskOption> taskOption;

    @Transient
    private ViewUtils viewUtil;

}
