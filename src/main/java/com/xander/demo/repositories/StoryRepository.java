package com.xander.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.xander.demo.models.Story;

public interface StoryRepository extends JpaRepository<Story, Long> {

}
