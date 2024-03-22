package ru.malkiev.jpa.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import ru.malkiev.jpa.entity.Post;

public interface PostRepository extends JpaRepository<Post, Integer>,
    JpaSpecificationExecutor<Post> {

  @Query("select p from Post p "
      + "left join fetch p.status "
      + "left join fetch p.category "
      + "left join fetch p.author a left join fetch a.role "
      + "where p.id = ?1")
  Optional<Post> findByIdWithHeader(Integer id);

  @Query("select p from Post p "
      + "left join fetch p.status "
      + "left join fetch p.category "
      + "left join fetch p.author a left join fetch a.role")
  List<Post> findAllWithHeader();

  @EntityGraph(value = Post.WITH_REQUIRED_FIELDS)
  @Query("select p from Post p")
  List<Post> findAllWithNamedGraph();

  @EntityGraph(attributePaths = {
      "category", "status", "author", "author.role", "tags",
      "comments", "comments.author", "comments.author.role"
  })
  @Query("select p from Post p where p.id = ?1")
  Optional<Post> findByIdWithAllFields(Integer id);

  @Override
  @EntityGraph(value = Post.WITH_REQUIRED_FIELDS)
  List<Post> findAll(Specification<Post> spec, Sort sort);
}