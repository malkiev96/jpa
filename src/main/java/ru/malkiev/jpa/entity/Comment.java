package ru.malkiev.jpa.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "COMMENTS")
@Getter
@NoArgsConstructor
public class Comment {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "ID")
  private Integer id;

  @Column(name = "MESSAGE", nullable = false, length = 1000)
  @NotEmpty
  @Size(max = 1000)
  private String message;

  @ManyToOne(optional = false)
  @JoinColumn(name = "AUTHOR_ID", nullable = false)
  private User author;

}
