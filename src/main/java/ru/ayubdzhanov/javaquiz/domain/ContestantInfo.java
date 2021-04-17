package ru.ayubdzhanov.javaquiz.domain;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import java.util.LinkedList;
import java.util.List;

@Entity
public class ContestantInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Competition competition;

    @ManyToOne(cascade = CascadeType.ALL)
    private UserData userData;

    @ManyToMany
    @JoinTable(
        name = "contestant_result",
        joinColumns = @JoinColumn(name = "contestant_info_id"),
        inverseJoinColumns = @JoinColumn(name = "task_option_id")
    )
    private List<TaskOption> contestantResults;

    private Integer score;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Competition getCompetition() {
        return competition;
    }

    public void setCompetition(Competition competition) {
        this.competition = competition;
    }

    public UserData getUserData() {
        return userData;
    }

    public void setUserData(UserData userData) {
        this.userData = userData;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public List<TaskOption> getContestantResults() {
        if (contestantResults == null) contestantResults = new LinkedList<>();
        return contestantResults;
    }

    public void setContestantResults(List<TaskOption> contestantResults) {
        this.contestantResults = contestantResults;
    }
}
