package com.homespot.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "reports")
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "post_id")
    private Integer postId;

    @Column(name = "user_id")
    private Integer userId;

    private String reason;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "post_id", insertable = false, updatable = false)
    private Post post;

    @ManyToOne
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User user;

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public Integer getPostId() { return postId; }
    public void setPostId(Integer postId) { this.postId = postId; }
    public Integer getUserId() { return userId; }
    public void setUserId(Integer userId) { this.userId = userId; }
    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public Post getPost() { return post; }
    public void setPost(Post post) { this.post = post; }
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
}
