package ru.malkiev.jpa;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import javax.persistence.criteria.JoinType;
import org.hibernate.LazyInitializationException;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;
import ru.malkiev.jpa.entity.Post;
import ru.malkiev.jpa.entity.Role.Code;
import ru.malkiev.jpa.model.PostDetailModel;
import ru.malkiev.jpa.model.PostModel;
import ru.malkiev.jpa.model.mapper.PostMapper;
import ru.malkiev.jpa.repository.PostRepository;

@SpringBootTest
class PostsIntegrationTest {

  @Autowired
  private PostRepository repository;

  private final PostMapper mapper = PostMapper.INSTANCE;

  @Test
  @Transactional
  void testGetAll() {
    List<Post> posts = repository.findAll();
    List<PostModel> models = mapper.toCollectionModel(posts);
    assertEquals(20, models.size());
  }

  @Test
  @Transactional
  void testGetOne() {
    Post post = repository.findById(1).orElseThrow();
    PostDetailModel model = PostMapper.INSTANCE.toDetailModel(post);
    assertNotNull(model);
  }

  @Test
  @DisplayName("could not initialize proxy - no Session")
  void testGetAllThrowsLazyInitEx() {
    List<Post> posts = repository.findAll();
    assertThrows(LazyInitializationException.class, () -> mapper.toCollectionModel(posts));
  }

//  region join fetch

  @Test
  @DisplayName("join fetch для одной записи")
  @Transactional
  void testGetOneWithJoinFetch() {
    Post post = repository.findByIdWithHeader(1).orElseThrow();
    PostDetailModel model = PostMapper.INSTANCE.toDetailModel(post);
    assertNotNull(model);
  }

  @Test
  @DisplayName("join fetch для списка")
  void testGetAllWithJoinFetch() {
    List<Post> posts = repository.findAllWithHeader();
    List<PostModel> models = mapper.toCollectionModel(posts);
    assertEquals(20, models.size());
  }

//  endregion

//  region entityGraphs

  @Test
  @DisplayName("NamedEntityGraph для списка")
  void testGetAllWithNamedEntityGraph() {
    List<Post> posts = repository.findAllWithNamedGraph();
    List<PostModel> models = mapper.toCollectionModel(posts);
    assertEquals(20, models.size());
  }

  @Test
  @DisplayName("EntityGraph для одной записи с декартовым произведением")
  void testGetOneWithEntityGraph() {
    Post post = repository.findByIdWithAllFields(1).orElseThrow();
    PostDetailModel model = PostMapper.INSTANCE.toDetailModel(post);
    assertNotNull(model);
  }

//  endregion

//  region specifications

  @Test
  void testGetOneWithSpec() {
    Specification<Post> spec = (root, query, cb) -> cb.equal(root.get("id"), 2);
    Post post = repository.findOne(spec).orElseThrow();
    assertThrows(LazyInitializationException.class, () -> mapper.toDetailModel(post));
  }

  @Test
  @DisplayName("Можно комбинировать Specification и entityGraphs")
  @Disabled("похоже на ошибку в библиотеке")
    // т.к. author содержит подграф, то почему-то падает ошибка при сортировке по свойсту из author
  void testFindAllWithSpecAndGraphs() {
    Specification<Post> spec = (root, query, cb) -> root.join("author")
        .join("role")
        .get("code")
        .in(Code.ROLE_USER);
    List<Post> posts = repository.findAll(spec, Sort.by("author.firstName"));
    List<PostModel> models = mapper.toCollectionModel(posts);
    assertEquals(12, models.size());
  }

  @Test
  @DisplayName("Решение проблемы выше, join заменен на get")
  void testFindAllWithSpecAndGraphs1() {
    Specification<Post> spec = (root, query, cb) -> root.get("author")
        .get("role")
        .get("code")
        .in(Code.ROLE_USER);
    List<Post> posts = repository.findAll(spec, Sort.by("author.firstName"));
    List<PostModel> models = mapper.toCollectionModel(posts);
    assertEquals(12, models.size());
  }

  @Test
  @DisplayName("Решение проблемы выше, inner join заменен на left join")
  void testFindAllWithSpecAndGraphs2() {
    Specification<Post> spec = (root, query, cb) -> root.join("author", JoinType.LEFT)
        .join("role", JoinType.LEFT)
        .get("code")
        .in(Code.ROLE_USER);
    List<Post> posts = repository.findAll(spec, Sort.by("author.firstName"));
    List<PostModel> models = mapper.toCollectionModel(posts);
    assertEquals(12, models.size());
  }

  @Test
  @DisplayName("Join fetch с помощью спецификации")
  void testFindAllWithFetchSpec() {
    Specification<Post> spec = (root, query, cb) -> {
      root.fetch("status", JoinType.LEFT);
      root.fetch("category", JoinType.LEFT);
      root.fetch("author", JoinType.LEFT)
          .fetch("role", JoinType.LEFT);
      query.orderBy(cb.asc(root.get("author").get("firstName")));
      return cb.and();
    };
    List<Post> posts = repository.findAll(spec);
    List<PostModel> models = mapper.toCollectionModel(posts);
    assertEquals(20, models.size());
  }

//  endregion

}