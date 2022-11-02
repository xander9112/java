package com.xander.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.xander.demo.models.Post;

public interface PostRepository extends JpaRepository<Post, Long> {

}
