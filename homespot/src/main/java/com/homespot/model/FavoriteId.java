package com.homespot.model;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class FavoriteId implements Serializable {
    private Integer userId;
    private Integer postId;

    public FavoriteId() {}
    public FavoriteId(Integer userId, Integer postId) {
        this.userId = userId;
        this.postId = postId;
    }

    public Integer getUserId() { return userId; }
    public void setUserId(Integer userId) { this.userId = userId; }
    public Integer getPostId() { return postId; }
    public void setPostId(Integer postId) { this.postId = postId; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FavoriteId)) return false;
        FavoriteId that = (FavoriteId) o;
        return Objects.equals(userId, that.userId) &&
                Objects.equals(postId, that.postId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, postId);
    }
}
