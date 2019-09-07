package com.some.app.placeholder.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Comment implements Serializable {
    int id;
    int postId;
    String name;
    String email;
    String body;
}
