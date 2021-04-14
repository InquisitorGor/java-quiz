package ru.ayubdzhanov.javaquiz.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import java.util.List;

@Entity
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String question;

    private Long prestige;

    @ManyToOne
    private Category category;

    @OneToMany(mappedBy = "task")
    private List<Option> options;

    @Transient
    private String menu;

    @Transient
    private Integer menuCounter;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public Long getPrestige() {
        return prestige;
    }

    public void setPrestige(Long prestige) {
        this.prestige = prestige;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public List<Option> getOptions() {
        return options;
    }

    public void setOptions(List<Option> options) {
        this.options = options;
    }

    public String getMenu() {
        return menu;
    }

    public void setMenu(String menu) {
        this.menu = menu;
    }

    public Integer getMenuCounter() {
        if (menuCounter == null) menuCounter = 0;
        return menuCounter;
    }

    public void setMenuCounter(Integer menuCounter) {
        this.menuCounter = menuCounter;
    }
}
