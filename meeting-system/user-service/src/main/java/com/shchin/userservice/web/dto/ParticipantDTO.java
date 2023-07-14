package com.shchin.userservice.web.dto;

import com.shchin.userservice.dao.UserDAO;
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
public class ParticipantDTO {

    @NotNull
    private MeetingDTO meetingDTO;

    @NotNull
    private List<UserDAO> userDAOList;

    @NotNull
    private UserDAO userOrganizer;

}
