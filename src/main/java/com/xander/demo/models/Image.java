package com.xander.demo.models;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "images")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Image {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "id")
  private Long id;

  @Column(name = "name")
  private String name;

  @Column(name = "original_file_name")
  private String originalFileName;

  @Column(name = "size")
  private Long size;

  @Column(name = "content_type")
  private String contentType;

  @Column(name = "is_preview_image")
  private boolean isPreviewImage;

  @Lob
  private byte[] bytes;

  @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
  private Post post;
}
