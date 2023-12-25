package com.myblog.post.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name= "posts")
public class Post {
    @Id
    private String id;
    private String title;
    private String description;
    private String content;

}