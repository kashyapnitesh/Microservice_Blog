package com.myblog.post.controller;


import com.myblog.post.entity.Post;
import com.myblog.post.payload.PostDto;
import com.myblog.post.repository.PostRepository;
import com.myblog.post.service.PostService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/post")
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private PostRepository postRepository;


    @PostMapping
    public ResponseEntity<PostDto> savePost(@RequestBody PostDto postDto) {
        PostDto newPost = postService.createPost(postDto);
        return new ResponseEntity<>(newPost, HttpStatus.CREATED);
    }


//    @GetMapping("/{postId}")
//    public Post getPostByPostId(@PathVariable String postId) {
//        Post post = postService.findPostById(postId);
//        return null;
//    }



    //  http://localhost:8081/api/post/{postId}
    @GetMapping("/{postId}")
    public PostDto getPostById(@PathVariable String postId) {
        PostDto postdto = postService.getPostById(postId);
        return postdto;
    }


    //  Get your comments along with the post
    //http://localhost:8081/api/post/{postId}/comments
    @GetMapping("/{postId}/comments")
    @CircuitBreaker(name = "CommentBreaker", fallbackMethod = "commentFallback")
    public ResponseEntity<PostDto> getPostWithComments(@PathVariable String postId) {
        PostDto postDto = postService.getPostWithComments(postId);
        return new ResponseEntity<>(postDto, HttpStatus.OK);
    }

    public ResponseEntity<PostDto> commentFallback(String postId, Exception ex) {
        System.out.println("Fallback is executed because service is down" + ex.getMessage());
        ex.printStackTrace();

        PostDto dto = new PostDto();
        dto.setPostId("1234");
        dto.setTitle("Service Down");
        dto.setContent("Service Down");
        dto.setDescription("Service Down");

        return new ResponseEntity<>(dto, HttpStatus.BAD_REQUEST);
    }


    // Update an existing post by ID
    // http://localhost:8081/api/post/update/{postId}
    @PutMapping("/update/{postId}")
    public ResponseEntity<PostDto> updatePost(@PathVariable String postId, @RequestBody PostDto updatedPostDto) {
        PostDto updatedPost = postService.updatePost(postId, updatedPostDto);
        return new ResponseEntity<>(updatedPost, HttpStatus.OK);

    }

    // Delete a post by ID
    // http://localhost:8081/api/post/delete/{postId}
    @DeleteMapping("/delete/{postId}")
    public ResponseEntity<String> deletePostById(@PathVariable String postId) {
        boolean deleted = postService.deletePostById(postId);
        return new ResponseEntity<>("Post is Deleted :" + postId, HttpStatus.OK);
    }
}



