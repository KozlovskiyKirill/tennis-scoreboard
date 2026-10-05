package com.tennis.scoreboard.Entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.regex.Pattern;

@Entity
@Getter
@NoArgsConstructor (access = AccessLevel.PROTECTED)
@Table(name = "Players", indexes = {@Index(name = "fn_name_index",columnList = "Name", unique = true)})
public class PlayerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer ID;

    @Column(name = "Name", unique = true, length = 10, nullable = false)
    private String name;

    private static final Pattern PATTERN_NAME = Pattern.compile("^[a-zA-Z]{2,10}$");

    public PlayerEntity (String name){
        if(name==null || !PATTERN_NAME.matcher(name).matches())
            throw new IllegalArgumentException("Incorrect name! It should contain only letters.Max length - 10");
        this.name = name;
    }
}
