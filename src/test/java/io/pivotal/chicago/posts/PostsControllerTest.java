package io.pivotal.chicago.posts;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

public class PostsControllerTest {
    @Mock
    PostService postService;

    @InjectMocks
    PostsController postsController;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    MockMvc mockMvc;

    @Before
    public void setUpMockMvc() {
        mockMvc = standaloneSetup(postsController).build();
    }

    @Test
    public void creatingAPostTellsThePostServiceToCreateAPost() throws Exception {
        PostResponse postResponse = when(mock(PostResponse.class).getId())
                .thenReturn(1L)
                .getMock();

        when(postService.createPost(isA(PostRequest.class)))
                .thenReturn(postResponse);

        mockMvc.perform(post("/posts")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", is("http://localhost/posts/1")));
    }
}
