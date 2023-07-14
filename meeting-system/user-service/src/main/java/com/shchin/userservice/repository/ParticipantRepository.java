package com.shchin.userservice.repository;

import com.shchin.userservice.dao.ParticipantDAO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParticipantRepository  extends JpaRepository<ParticipantDAO, Long> {

    List<ParticipantDAO> findAllByMeetingId(Long meetingId);

}
