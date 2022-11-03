package com.xander.demo.models;

import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "stories")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Story {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "story_id_generator")
  @SequenceGenerator(name = "story_id_generator", sequenceName = "stories_sequence", allocationSize = 1)
  @Column(name = "id")
  private Long id;

  @Column(name = "name")
  private String name;

  @Column(name = "content", length = 2048)
  private String content;

  @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "story")
  @JsonManagedReference
  private Image image;

  private LocalDateTime createdAt;

  @PrePersist
  private void init() {
    createdAt = LocalDateTime.now();
  }

  public void addImageToStory(Image image) {
    image.setStory(this);

    setImage(image);
    // images.add(image);

  }
}
