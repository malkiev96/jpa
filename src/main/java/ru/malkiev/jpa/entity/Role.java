package ru.malkiev.jpa.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ROLES")
@Getter
@NoArgsConstructor
public class Role {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "ID")
  private Integer id;

  @Enumerated(EnumType.STRING)
  @Column(name = "CODE", nullable = false)
  private Code code;

  @Column(name = "NAME", nullable = false)
  private String name;

  public enum Code {
    ROLE_USER,
    ROLE_ADMIN
  }

}
