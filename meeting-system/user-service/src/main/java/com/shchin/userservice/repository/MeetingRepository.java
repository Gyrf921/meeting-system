package com.shchin.userservice.repository;

import com.shchin.userservice.dao.MeetingDAO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface MeetingRepository extends JpaRepository<MeetingDAO, Long> {

    Optional<MeetingDAO> findById(Long id);

}
