package io.pivotal.chicago.posts;


import org.junit.Rule;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class DefaultPostServiceTest {
    @Mock
    PostRepository postRepository;

    @InjectMocks
    DefaultPostService defaultPostService;

    @Captor
    ArgumentCaptor<Post> postArgumentCaptor;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Test
    public void creatingAPostTellsThePostRepositoryToSaveAPost() {
        String title = "The title";

        PostRequest postRequest = new PostRequest();
        postRequest.setTitle(title);

        Post savedPost = mock(Post.class);
        when(savedPost.getId())
                .thenReturn(1L);
        when(savedPost.getTitle())
                .thenReturn(title);
        when(postRepository.save(postArgumentCaptor.capture()))
                .thenReturn(savedPost);

        PostResponse postResponse = defaultPostService.createPost(postRequest);

        assertThat(postArgumentCaptor.getValue().getTitle(), is(postRequest.getTitle()));
        assertThat(postResponse.getId(), is(savedPost.getId()));
        assertThat(postResponse.getTitle(), is(savedPost.getTitle()));
    }
}