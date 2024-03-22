package ru.malkiev.jpa.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.malkiev.jpa.entity.Post;
import ru.malkiev.jpa.model.PostDetailModel;
import ru.malkiev.jpa.model.PostModel;
import ru.malkiev.jpa.model.mapper.PostMapper;
import ru.malkiev.jpa.repository.PostRepository;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {

  private final PostRepository repository;

  @GetMapping
  public ResponseEntity<Page<PostModel>> getPage(@PageableDefault Pageable pageable) {
    Page<Post> page = repository.findAll(pageable);
    Page<PostModel> pagedModel = PostMapper.INSTANCE.toPagedModel(page);
    return ResponseEntity.ok(pagedModel);
  }

  @GetMapping("/{id}")
  public ResponseEntity<PostDetailModel> getPostDetail(@PathVariable Integer id) {
    return repository.findById(id)
        .map(PostMapper.INSTANCE::toDetailModel)
        .map(ResponseEntity::ok)
        .orElseThrow();
  }

}
