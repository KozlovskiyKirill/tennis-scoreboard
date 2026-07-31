package com.tennis.scoreboard.Model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import jakarta.persistence.Id;

import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Table(name = "Players", indexes = {@Index(name="fn_name_index", columnList = "name", unique = true)})
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="name", nullable = false, unique = true)
    private String name;

    public Player (String name){this.name = name;
    }

}
