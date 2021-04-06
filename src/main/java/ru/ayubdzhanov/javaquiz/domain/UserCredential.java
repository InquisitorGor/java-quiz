package ru.ayubdzhanov.javaquiz.domain;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "user_credential")
public class UserCredential {

    @Id
    private Long id;

    private String name;

    private String password;

    private String role;

    @OneToOne
    @MapsId
    @JoinColumn(name = "user_data")
    private UserData userData;

    @OneToMany(mappedBy = "userCredential")
    private List<ContestantInfo> contestantInfos;

    public UserCredential() {
    }

    public UserCredential(String name, String password, String role) {
        this.name = name;
        this.password = password;
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public UserData getUserData() {
        return userData;
    }

    public void setUserData(UserData userData) {
        this.userData = userData;
    }

    public List<ContestantInfo> getContestantInfos() {
        return contestantInfos;
    }

    public void setContestantInfos(List<ContestantInfo> contestantInfos) {
        this.contestantInfos = contestantInfos;
    }
}
