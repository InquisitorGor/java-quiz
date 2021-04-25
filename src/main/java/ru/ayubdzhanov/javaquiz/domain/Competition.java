package ru.ayubdzhanov.javaquiz.domain;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

@Entity
public class Competition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime startedAt;

    private LocalDateTime finishedAt;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToMany()
    @JoinTable(
        name = "competition_task",
        joinColumns = @JoinColumn(name = "competition_id"),
        inverseJoinColumns = @JoinColumn(name = "task_id")
    )
    private List<Task> tasks;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "competition")
    private List<ContestantInfo> contestants;

    @Transient
    private String imageLink;

    @Transient
    private UserData userData;

    @Transient
    private String style;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getStartedAt() {
        return startedAt;
    }

    public void setStartedAt(LocalDateTime startedAt) {
        this.startedAt = startedAt;
    }

    public LocalDateTime getFinishedAt() {
        return finishedAt;
    }

    public void setFinishedAt(LocalDateTime finishedAt) {
        this.finishedAt = finishedAt;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    public List<ContestantInfo> getContestants() {
        if (contestants == null) contestants = new LinkedList<>();
        return contestants;
    }

    public void setContestants(List<ContestantInfo> contestants) {
        this.contestants = contestants;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public UserData getUserData() {
        return userData;
    }

    public void setUserData(UserData userData) {
        this.userData = userData;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }
}
