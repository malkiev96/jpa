package ru.malkiev.jpa.model;

import lombok.Data;

@Data
public class PostModel {

  private Integer id;
  private String title;
  private String statusName;
  private String categoryName;
  private UserModel author;


  @Data
  public static class TagModel {

    private Integer id;
    private String name;

  }

  @Data
  public static class UserModel {

    private String firstName;
    private String lastName;
    private String roleName;

  }

  @Data
  public static class CommentModel {

    private String message;
    private UserModel author;

  }
}
