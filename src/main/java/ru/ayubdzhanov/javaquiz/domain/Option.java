package ru.ayubdzhanov.javaquiz.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
public class Option {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String option;

    @OneToMany(mappedBy = "option")
    private List<TaskOption> taskOptions;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }

    public List<TaskOption> getTaskOptions() {
        return taskOptions;
    }

    public void setTaskOptions(List<TaskOption> taskOptions) {
        this.taskOptions = taskOptions;
    }
}
