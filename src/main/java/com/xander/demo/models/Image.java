package com.xander.demo.models;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

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
  // @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "image_id_generator")
  @SequenceGenerator(name = "image_id_generator", sequenceName = "images_sequence", allocationSize = 1)
  private Long id;

  @Column(name = "name")
  private String name;

  @Column(name = "original_file_name")
  private String originalFileName;

  @Column(name = "size")
  private Long size;

  @Column(name = "width")
  private double width;

  @Column(name = "height")
  private double height;

  @Column(name = "content_type")
  private String contentType;

  @Column(name = "ext")
  private String ext;

  @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
  @JsonBackReference
  private Post post;

  @OneToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
  @JsonBackReference
  private Story story;

  public Map<String, String> getSizes() {
    Map<String, String> _images = new HashMap<String, String>();

    _images.put("small", "media/" + this.id + "/small");
    _images.put("mid", "media/" + this.id + "/mid");
    _images.put("big", "media/" + this.id + "/big");

    return _images;
  }
}
