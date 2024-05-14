package com.sparta.schedule.entity;

import com.sparta.schedule.dto.ScheduleRequestDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "schedule")
public class Schedule  extends Timestamped{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String contents;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private String password;

    public Schedule(ScheduleRequestDTO requestDTO) {
        this.title = requestDTO.getTitle();
        this.contents = requestDTO.getContents();
        this.nickname = requestDTO.getNickname();
        this.password = requestDTO.getPassword();
    }

    public void update(ScheduleRequestDTO requestDTO) {
        this.title = requestDTO.getTitle();
        this.contents = requestDTO.getContents();
        this.nickname = requestDTO.getNickname();
    }
}
