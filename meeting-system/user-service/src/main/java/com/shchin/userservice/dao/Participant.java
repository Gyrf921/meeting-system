package com.shchin.userservice.dao;


import lombok.*;

import javax.persistence.*;

@Builder
@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "participants", schema="public")
@IdClass(ParticipantId.class)
public class Participant {

    @Id
    private Long meetingId;

    @Id
    private Long userId;


    private boolean isOrganizer;


}
