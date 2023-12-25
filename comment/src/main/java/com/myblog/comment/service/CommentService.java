package com.myblog.comment.service;

import com.myblog.comment.config.RestTemplateConfig;
import com.myblog.comment.entity.Comment;
import com.myblog.comment.payload.Post;
import com.myblog.comment.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;


@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private RestTemplateConfig restTemplate;

    public Comment saveComment(Comment comment) {
        Post post = restTemplate.getRestTemplate().getForObject("http://POST-SERVICE/api/posts/" + comment.getPostId(), Post.class);

        if (post != null) {
            String commentId = UUID.randomUUID().toString();
            comment.setCommentId(commentId);
            Comment savedComment = commentRepository.save(comment);
            return savedComment;


        } else {
            return null;
        }


    }


    public List<Comment> getAllCommentsByPostId(String postId) {
        List<Comment> comments = commentRepository.findByPostId(postId);
        return comments;
    }
}

