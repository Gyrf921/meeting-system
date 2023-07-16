package com.shchin.userservice.repository;

import com.shchin.userservice.dao.Participant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParticipantRepository  extends JpaRepository<Participant, Long> {

    List<Participant> findAllByMeetingId(Long meetingId);

}
