package com.shchin.userservice.dao;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Embeddable
@AllArgsConstructor
public class ParticipantId implements Serializable {

    private Long meetingId;

    private Long userId;

    public ParticipantId(){}

}
