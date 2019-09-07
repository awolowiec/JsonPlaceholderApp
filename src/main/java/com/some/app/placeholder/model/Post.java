package com.some.app.placeholder.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Post implements Serializable {
    private int id;
    private int userId;
    private String title;
    private String body;
    private List<Comment> comments;
}
