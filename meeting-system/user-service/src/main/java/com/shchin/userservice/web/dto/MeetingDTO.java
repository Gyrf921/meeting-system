package com.shchin.userservice.web.dto;

import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Date;

@Builder
@Data
public class MeetingDTO {

    @NotNull(message = "Name is mandatory")
    private String name;

    private String description;

    private Date datetimeStart;

    private Date datetimeEnd;
}
