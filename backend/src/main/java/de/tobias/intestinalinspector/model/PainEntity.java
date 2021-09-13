package de.tobias.intestinalinspector.model;

import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name="pain_table")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PainEntity {

    @Id
    @GeneratedValue
    @Column(name="id")
    private long id;

    @Column(name="date")
    private Date date;

    @Column(name="pain_level")
    private int painLevel;

    @Column(name="user_name")
    private String userName;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PainEntity that = (PainEntity) o;
        return id == that.id && painLevel == that.painLevel && Objects.equals(date, that.date) && Objects.equals(userName, that.userName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, date, painLevel, userName);
    }
}
