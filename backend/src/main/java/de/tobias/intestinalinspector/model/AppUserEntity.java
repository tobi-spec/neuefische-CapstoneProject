package de.tobias.intestinalinspector.model;


import lombok.*;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name="app_user")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppUserEntity {

    @Id
    @GeneratedValue
    @Column(name="id", nullable = false, unique = true)
    private long id;

    @Column(name="user_name", nullable = false, unique = true)
    private String userName;

    @Column(name="user_password", nullable = false)
    private String userPassword;

    @Column(name="user_role", nullable = false)
    private String userRole;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AppUserEntity that = (AppUserEntity) o;
        return id == that.id && Objects.equals(userName, that.userName) && Objects.equals(userPassword, that.userPassword);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userName, userPassword);
    }

    @Override
    public String toString() {
        return "AppUserEntity{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", userPassword='" + userPassword + '\'' +
                '}';
    }
}
