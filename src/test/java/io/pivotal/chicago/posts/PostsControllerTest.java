package io.pivotal.chicago.posts;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.springframework.context.MessageSource;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.validation.Errors;

import static org.hamcrest.Matchers.hasEntry;
import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

public class PostsControllerTest {
    @Mock
    PostService postService;

    @Mock
    PostRequestValidator postRequestValidator;

    @Mock
    MessageSource messageSource;

    @InjectMocks
    PostsController postsController;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    MockMvc mockMvc;

    @Before
    public void setUpMockMvc() {
        mockMvc = standaloneSetup(postsController).build();
    }

    @Before
    public void setUpPostRequestValidator() {
        when(postRequestValidator.supports(PostRequest.class))
                .thenReturn(true);
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

    @Test
    public void returnsErrorsWhenTryingToCreateAnInvalidPost() throws Exception {
        doAnswer(invocation -> {
            Errors errors = invocation.getArgumentAt(1, Errors.class);
            errors.rejectValue("title", "blank");
            return null;
        }).when(postRequestValidator).validate(isA(PostRequest.class), isA(Errors.class));

        when(messageSource.getMessage("blank", null, null))
                .thenReturn("can't be blank");

        mockMvc.perform(post("/posts")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors", hasEntry("title", "can't be blank")));
    }
}
