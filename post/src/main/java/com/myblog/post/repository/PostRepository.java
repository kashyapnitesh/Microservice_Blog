package com.myblog.post.repository;
import com.myblog.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostRepository extends JpaRepository<Post,String> {

   // Optional<Post> findById(String id);
}
