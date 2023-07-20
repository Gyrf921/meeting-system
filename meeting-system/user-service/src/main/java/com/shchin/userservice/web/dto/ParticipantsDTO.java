package com.shchin.userservice.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;


@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ParticipantsDTO {

    @NotNull
    private MeetingDTO meetingDTO;

    @NotNull
    private List<Long> usersIdList;

    @NotNull
    private Long userOrganizerId;

}
