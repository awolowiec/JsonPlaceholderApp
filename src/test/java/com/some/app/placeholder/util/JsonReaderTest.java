package com.some.app.placeholder.util;

import com.google.common.collect.ImmutableList;
import com.some.app.placeholder.model.Comment;
import com.some.app.placeholder.model.Post;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class JsonReaderTest {
    private static final String POSTS_URL = "https://jsonplaceholder.typicode.com/posts";
    private static final String POST_COMMENTS_URL_TEMPLATE = "https://jsonplaceholder.typicode.com/comments?postId=%s";

    @Mock
    private ResponseEntity<Post[]> responseEntityPostMock;

    @Mock
    private RestTemplate restTemplateMock;

    @InjectMocks
    private JsonReader jsonReader;


    @ParameterizedTest
    @MethodSource("parametersForTestGetAllPosts")
    void testGetAllPosts(List<Post> expectedResults) {
        // given
        when(responseEntityPostMock.getBody()).thenReturn(expectedResults.toArray(new Post[0]));
        when(restTemplateMock.getForEntity(POSTS_URL, Post[].class)).thenReturn(responseEntityPostMock);

        // when
        List<Post> results = jsonReader.getAllPosts();

        // then
        assertEquals(expectedResults, results);
        verify(restTemplateMock).getForEntity(POSTS_URL, Post[].class);
    }

    static Stream parametersForTestGetAllPosts() {
        return Stream.of(
                ImmutableList.of(new Post(1, 1, "example title", "example body", Collections.emptyList()),
                        new Post(2, 2, "example title", "example body", Collections.emptyList())),
                Collections.singletonList(new Post(1, 1, "example title", "example body", Collections.emptyList())),
                Collections.emptyList()
        );
    }

    @Test
    void testGetAllPostsWithComments() {
        // given
        List<Post> expectedResults = ImmutableList.of(
                new Post(1, 1, "example title", "example body", ImmutableList.of(
                        new Comment(1, 1, "name", "email", "body"))),
                new Post(2, 2, "example title", "example body", ImmutableList.of(
                        new Comment(2, 2, "name", "email", "body"), new Comment(3, 2, "name", "email", "body"))));

        List<Post> posts = ImmutableList.of(new Post(1, 1, "example title", "example body", Collections.emptyList()),
                new Post(2, 2, "example title", "example body", Collections.emptyList()));

        ResponseEntity<Comment[]> responseEntityForFirstPostMock = mock(ResponseEntity.class);
        ResponseEntity<Comment[]> responseEntityForSecondPostMock = mock(ResponseEntity.class);

        when(responseEntityPostMock.getBody()).thenReturn(posts.toArray(new Post[0]));
        when(responseEntityForFirstPostMock.getBody()).thenReturn(expectedResults.get(0).getComments().toArray(new Comment[0]));
        when(responseEntityForSecondPostMock.getBody()).thenReturn(expectedResults.get(1).getComments().toArray(new Comment[0]));

        when(restTemplateMock.getForEntity(POSTS_URL, Post[].class)).thenReturn(responseEntityPostMock);
        when(restTemplateMock.getForEntity(String.format(POST_COMMENTS_URL_TEMPLATE, 1), Comment[].class))
                .thenReturn(responseEntityForFirstPostMock);
        when(restTemplateMock.getForEntity(String.format(POST_COMMENTS_URL_TEMPLATE, 2), Comment[].class))
                .thenReturn(responseEntityForSecondPostMock);

        // when
        List<Post> results = jsonReader.getAllPostsWithComments();

        // then
        assertEquals(expectedResults, results);
        verify(restTemplateMock).getForEntity(POSTS_URL, Post[].class);
        verify(restTemplateMock).getForEntity(String.format(POST_COMMENTS_URL_TEMPLATE, 1), Comment[].class);
        verify(restTemplateMock).getForEntity(String.format(POST_COMMENTS_URL_TEMPLATE, 2), Comment[].class);
    }
}