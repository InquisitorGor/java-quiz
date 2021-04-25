package ru.ayubdzhanov.javaquiz.domain;

import javax.persistence.Entity;
import javax.persistence.FetchType;
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

    private Integer prestige;

    @ManyToOne
    private Category category;

    @OneToMany(mappedBy = "task")
    private List<TaskOption> taskOption;

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

    public Integer getPrestige() {
        return prestige;
    }

    public void setPrestige(Integer prestige) {
        this.prestige = prestige;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public List<TaskOption> getTaskOption() {
        return taskOption;
    }

    public void setTaskOption(List<TaskOption> taskOption) {
        this.taskOption = taskOption;
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

    @Override
    public String toString() {
        return "Task{" +
            "id=" + id +
            ", question='" + question + '\'' +
            ", prestige=" + prestige +
            ", category=" + category +
            ", taskOption=" + taskOption +
            ", menu='" + menu + '\'' +
            ", menuCounter=" + menuCounter +
            '}';
    }
}
