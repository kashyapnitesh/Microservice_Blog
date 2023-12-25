package com.myblog.post.service.impl;

import com.myblog.post.config.RestTemplateConfig;
import com.myblog.post.entity.Post;
import com.myblog.post.exception.ResourceNotFound;
import com.myblog.post.payload.PostDto;
import com.myblog.post.repository.PostRepository;
import com.myblog.post.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.ArrayList;
import java.util.UUID;

@Service
public class PostServiceImpl implements PostService {
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private RestTemplateConfig restTemplate;

    @Override
    public PostDto createPost(PostDto pdto) {

        // Generate a random postId using UUID
        String randomPostId = UUID.randomUUID().toString();
        pdto.setPostId(randomPostId);

        Post posts = new Post();
        posts.setId(randomPostId);
        posts.setTitle(pdto.getTitle());
        posts.setContent(pdto.getContent());
        posts.setDescription(pdto.getDescription());
//        posts.setPublishedDate(new Date());

        // Save the post to the repository
        Post savedPost = postRepository.save(posts);

        PostDto postdto = new PostDto();

        postdto.setTitle(savedPost.getTitle());
        postdto.setContent(savedPost.getContent());
        postdto.setDescription(savedPost.getDescription());
//      postdto.setPublishedDate(savedPost.getPublishedDate());

        return postdto;
    }


    @Override
    public PostDto updatePost(String postId, PostDto updatedPostDto) {

            Post posts = postRepository.findById(postId)
                    .orElseThrow(() -> new ResourceNotFound("Post not found with id: " + postId));
        return mapToDto(posts);
        }

    @Override
    public boolean deletePostById(String postId) {
Post dpost = postRepository.findById(postId).orElseThrow(()->new ResourceNotFound(
                         "Post not found with this Id : " + postId));
postRepository.deleteById(postId);
        return true;
    }

    @Override
    public Post findPostById(String postId) {
        Post post = postRepository.findById(postId).get();
                return post;
    }

    @Override
    public PostDto getPostById(String postId) {
        Post posts = postRepository.findById(postId).get();
        return mapToDto(posts);
    }

    //purpose of mapToDto() method is to simply convert the entity object into Dto...
    //means taking content of entity and put them into dto and retutn back a Dto..
    PostDto mapToDto(Post mapPost) {
        PostDto pdto = new PostDto();
        pdto.setPostId(mapPost.getId());
        pdto.setTitle(mapPost.getTitle());
        pdto.setContent(mapPost.getContent());
        pdto.setDescription(mapPost.getDescription());
//      pdto.setPublishedDate(mapPost.getPublishedDate());
        return pdto;
    }


    @Override
    public PostDto getPostWithComments(String postId) {
        // Find the post by its postId
        Post post = postRepository.findById(postId).get();
        // Get comments for the post from a RESTful API
        ArrayList comments = restTemplate.getRestTemplate().getForObject(
                "http://COMMENT-SERVICE/api/comments/"  //whatever this url is returning back
                        + postId, ArrayList.class);    //you have to give that here, now it is returning
                                                     // an arraylist so you have to give ArrayList.class

// Create a PostDto object to hold post and comments information
        PostDto postdto = new PostDto();
        postdto.setPostId(post.getId());
        postdto.setTitle(post.getTitle());
        postdto.setDescription(post.getDescription());
        postdto.setContent(post.getContent());
        postdto.setComments(comments);
        return postdto;
    }


}

