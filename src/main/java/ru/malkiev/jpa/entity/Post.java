package ru.malkiev.jpa.entity;

import java.util.LinkedHashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.NamedEntityGraphs;
import javax.persistence.NamedSubgraph;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NamedEntityGraphs(value = {
    @NamedEntityGraph(name = Post.WITH_REQUIRED_FIELDS, attributeNodes = {
        @NamedAttributeNode("status"),
        @NamedAttributeNode("category"),
        @NamedAttributeNode(value = "author", subgraph = Post.AUTHOR_WITH_ROLE),
    }, subgraphs = {
        @NamedSubgraph(name = Post.AUTHOR_WITH_ROLE, attributeNodes = {
            @NamedAttributeNode("role")
        })
    })
})
@Entity
@Table(name = "POSTS")
@Getter
@NoArgsConstructor
public class Post {

  public static final String WITH_REQUIRED_FIELDS = "WITH_REQUIRED_FIELDS";
  public static final String AUTHOR_WITH_ROLE = "AUTHOR_WITH_ROLE";

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "ID")
  private Integer id;

  @Column(name = "TITLE", nullable = false)
  private String title;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "STATUS_ID", nullable = false)
  private Status status;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "CATEGORY_ID", nullable = false)
  private Category category;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "AUTHOR_ID")
  private User author;

  @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinColumn(name = "POST_ID")
  @OrderBy("id desc")
  private Set<Comment> comments = new LinkedHashSet<>();

  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(
      name = "POST_TAGS",
      joinColumns = @JoinColumn(name = "POST_ID"),
      inverseJoinColumns = @JoinColumn(name = "TAG_ID")
  )
  @OrderBy("id desc")
  private Set<Tag> tags = new LinkedHashSet<>();

}
