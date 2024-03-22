package ru.malkiev.jpa.model;

import java.util.List;
import lombok.Data;

@Data
public class PostDetailModel extends PostModel {

  private List<TagModel> tags;
  private List<CommentModel> comments;

}
