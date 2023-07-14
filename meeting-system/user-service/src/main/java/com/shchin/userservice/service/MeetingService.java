package com.shchin.userservice.service;

import com.shchin.userservice.dao.MeetingDAO;
import com.shchin.userservice.web.dto.MeetingDTO;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface MeetingService {

    Optional<MeetingDAO> findById(Long id);

    @Transactional
    MeetingDAO create(MeetingDTO meetingDTO, Long userOrganizerId);

    @Transactional
    MeetingDAO update(Long id, MeetingDTO meetingDTO);

    @Transactional
    void delete(long id);


}
