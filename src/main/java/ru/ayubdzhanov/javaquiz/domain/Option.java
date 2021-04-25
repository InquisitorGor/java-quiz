package ru.ayubdzhanov.javaquiz.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Option option1 = (Option) o;
        return id.equals(option1.id) && option.equals(option1.option) && taskOptions.equals(option1.taskOptions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, option, taskOptions);
    }

    @Override
    public String toString() {
        return "Option{" +
            "id=" + id +
            ", option='" + option + '\'' +
            '}';
    }
}
