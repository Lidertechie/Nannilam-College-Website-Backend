package com.Lider.college_website.repository;

import com.Lider.college_website.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {

    List<Event> findAllByActiveTrueOrderByStartDateAsc();

    List<Event> findAllByOrderByStartDateAsc(); // admin, includes inactive

}