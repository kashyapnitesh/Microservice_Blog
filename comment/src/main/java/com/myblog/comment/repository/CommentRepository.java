package com.myblog.comment.repository;

import com.myblog.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, String> {
   List<Comment> findByPostId(String postId);

}
