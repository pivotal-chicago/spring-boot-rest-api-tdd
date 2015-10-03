package io.pivotal.chicago.post;


import org.junit.Rule;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.Optional;

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

        Post savedPost = when(mock(Post.class).getId())
                .thenReturn(1L)
                .getMock();
        when(savedPost.getTitle())
                .thenReturn(title);
        when(postRepository.save(postArgumentCaptor.capture()))
                .thenReturn(savedPost);

        PostResponse postResponse = defaultPostService.create(postRequest);

        assertThat(postArgumentCaptor.getValue().getTitle(), is(postRequest.getTitle()));
        assertThat(postResponse.getId(), is(savedPost.getId()));
        assertThat(postResponse.getTitle(), is(savedPost.getTitle()));
    }

    @Test
    public void findingAnExistingPostByID() {
        Post existingPost = when(mock(Post.class).getId())
                .thenReturn(1L)
                .getMock();
        when(existingPost.getTitle())
                .thenReturn("The title");
        when(postRepository.findOne(1L))
                .thenReturn(existingPost);

        Optional<PostResponse> optionalPostResponse = defaultPostService.find(1L);

        PostResponse postResponse = optionalPostResponse.get();
        assertThat(postResponse.getId(), is(existingPost.getId()));
        assertThat(postResponse.getTitle(), is(existingPost.getTitle()));
    }

    @Test
    public void returnsNothingWhenUnableToFindAnExistingPostByID() {
        when(postRepository.findOne(1L))
                .thenReturn(null);

        Optional<PostResponse> optionalPostResponse = defaultPostService.find(1L);

        assertThat(optionalPostResponse.isPresent(), is(false));
    }
}