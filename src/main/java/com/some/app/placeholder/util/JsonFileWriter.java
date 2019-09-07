package com.some.app.placeholder.util;

import com.google.gson.Gson;
import com.some.app.placeholder.model.Post;
import lombok.RequiredArgsConstructor;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

@RequiredArgsConstructor
class JsonFileWriter {
    private final Gson gson;

    void saveToFile(Post post, String filename) {
        try (Writer writer = new FileWriter(filename)) {
            gson.toJson(post, writer);
        } catch (IOException e) {
            throw new RuntimeException("Error occurred while saving post to file", e);
        }
    }
}
