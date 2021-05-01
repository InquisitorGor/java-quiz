package ru.ayubdzhanov.javaquiz.domain;

import lombok.Getter;
import lombok.Setter;

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

@Getter
@Setter
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

    private Integer prestige;

    public List<TaskOption> getContestantResults() {
        if (contestantResults == null) contestantResults = new LinkedList<>();
        return contestantResults;
    }

}
