package com.tennis.scoreboard.Model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Data;
import org.hibernate.annotations.Audited;
import org.springframework.data.annotation.Id;

import java.util.List;

@Entity
@Data
@Audited.Table(name = "Players")
public class Player {
    @Id
    private int ID;

    @Column(name="Name")
    private String Name;

    @OneToMany(mappedBy = "Players")
    private List<Match> matches;
}
