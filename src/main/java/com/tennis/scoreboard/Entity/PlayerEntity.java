package com.tennis.scoreboard.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "Players", indexes = {@Index(name = "fn_name_index",columnList = "Name", unique = true)})
public class PlayerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ID;

    @Column(name = "Name", unique = true, length = 10, nullable = false)
    private String Name;

    public PlayerEntity (String name){
        if(name == null || name.isBlank())
            throw new IllegalArgumentException("Player name must not be blank");
        if(name.length() > 10)
            throw new IllegalArgumentException("Player name too long");
        Name = name;
    }
}
