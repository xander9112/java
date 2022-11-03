package com.xander.demo.models;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "posts")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Post {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "application_id_generator")
  @SequenceGenerator(name = "application_id_generator", sequenceName = "applications_sequence", allocationSize = 1)
  @Column(name = "id")
  private Long id;

  @Column(name = "name")
  private String name;

  @Column(name = "content", length = 2048)
  private String content;

  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "post")
  @JsonManagedReference
  private List<Image> images = new ArrayList<>();

  private LocalDateTime createdAt;

  @PrePersist
  private void init() {
    createdAt = LocalDateTime.now();
  }

  public void addImageToPost(Image image) {
    image.setPost(this);

    if (images == null) {
      images = new ArrayList<>();
    }

    images.add(image);

  }
}
