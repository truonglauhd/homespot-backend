package com.homespot.model;

import jakarta.persistence.*;

@Entity
@Table(name = "favorites")
public class Favorite {

    @EmbeddedId
    private FavoriteId id;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User user;

    @ManyToOne
    @MapsId("postId")
    @JoinColumn(name = "post_id", insertable = false, updatable = false)
    private Post post;

    public FavoriteId getId() { return id; }
    public void setId(FavoriteId id) { this.id = id; }
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
    public Post getPost() { return post; }
    public void setPost(Post post) { this.post = post; }
}
