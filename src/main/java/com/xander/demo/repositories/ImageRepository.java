package com.xander.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.xander.demo.models.Image;

public interface ImageRepository extends JpaRepository<Image, Long> {

}
