package ru.malkiev.jpa.model.mapper;

import java.util.List;
import java.util.stream.Collectors;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;
import ru.malkiev.jpa.entity.Comment;
import ru.malkiev.jpa.entity.Post;
import ru.malkiev.jpa.entity.Tag;
import ru.malkiev.jpa.entity.User;
import ru.malkiev.jpa.model.PostDetailModel;
import ru.malkiev.jpa.model.PostModel;
import ru.malkiev.jpa.model.PostModel.CommentModel;
import ru.malkiev.jpa.model.PostModel.TagModel;
import ru.malkiev.jpa.model.PostModel.UserModel;

@Mapper
public interface PostMapper {

  PostMapper INSTANCE = Mappers.getMapper(PostMapper.class);

  TagModel toModel(Tag tag);

  CommentModel toModel(Comment comment);

  @Mapping(source = "role.name", target = "roleName")
  UserModel toModel(User user);

  @Mapping(source = "status.name", target = "statusName")
  @Mapping(source = "category.name", target = "categoryName")
  PostModel toModel(Post post);

  @Mapping(source = "status.name", target = "statusName")
  @Mapping(source = "category.name", target = "categoryName")
  PostDetailModel toDetailModel(Post post);

  default Page<PostModel> toPagedModel(Page<Post> posts) {
    return posts.map(this::toModel);
  }

  default List<PostModel> toCollectionModel(List<Post> posts) {
    return posts.stream().map(this::toModel).collect(Collectors.toList());
  }

}
