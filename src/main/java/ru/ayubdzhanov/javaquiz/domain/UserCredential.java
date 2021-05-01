package ru.ayubdzhanov.javaquiz.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "user_credential")
public class UserCredential {

    @Id
    private Long id;

    private String login;

    private String password;

    private String role;

    @OneToOne
    @MapsId
    @JoinColumn(name = "id")
    private UserData userData;

    public UserCredential() {
    }

    public UserCredential(String login, String password, String role, UserData userData) {
        this.login = login;
        this.password = password;
        this.role = role;
        this.userData = userData;
    }
}
