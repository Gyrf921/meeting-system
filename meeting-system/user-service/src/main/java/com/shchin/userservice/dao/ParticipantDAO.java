package com.shchin.userservice.dao;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Builder
@Getter
@Setter
@Entity
@AllArgsConstructor
@Table(name = "participants", schema="public")
@IdClass(ParticipantId.class)
public class ParticipantDAO {

    @Id
    private Long meetingId;

    @Id
    private Long userId;

    private boolean isOrganizer;

    public ParticipantDAO(){}

}
