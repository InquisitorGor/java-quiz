package ru.ayubdzhanov.javaquiz.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Entity
public class UserData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(mappedBy = "userData", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private UserCredential userCredential;

    private String name;

    private Integer prestige;

    private Integer victories;

    private Integer defeats;

    private Integer draws;

    private LocalDate registrationDate;

    private Integer amountOfBattles;

    @OneToMany(mappedBy = "userData")
    private List<ContestantInfo> contestantInfos;

    public void setVictories(Integer victories) {
        this.amountOfBattles++;
        this.victories = victories;
    }

    public void setDefeats(Integer defeats) {
        this.amountOfBattles++;
        this.defeats = defeats;
    }

    public void setDraws(Integer draws) {
        this.amountOfBattles++;
        this.draws = draws;
    }
}
