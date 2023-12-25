package com.myblog.post.payload;

import com.myblog.post.entity.Post;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostDto {
    private String postId;

    private String title;

    private String description;

    private String content;

    private List<Comment> comments;

    public static PostDto fromEntity(Post updatedPost) {
        PostDto postDto = new PostDto();
        postDto.setPostId(updatedPost.getId());
        postDto.setTitle(updatedPost.getTitle());
        postDto.setContent(updatedPost.getContent());
        postDto.setDescription((updatedPost.getDescription()));

        return postDto;
    }
}

