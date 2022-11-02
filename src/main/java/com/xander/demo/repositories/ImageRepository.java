package com.xander.demo.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.xander.demo.models.Image;

public interface ImageRepository extends JpaRepository<Image, UUID> {

}
