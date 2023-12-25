package com.myblog.post.service;


import com.myblog.post.entity.Post;
import com.myblog.post.payload.PostDto;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Service;

@Service
public interface PostService {


    PostDto getPostById(String postId);

    PostDto getPostWithComments(String postId);

    PostDto createPost(PostDto postdto);

    PostDto updatePost(String postId, PostDto updatedPostDto);

    boolean deletePostById(String postId);

    Post findPostById(String postId);
}
