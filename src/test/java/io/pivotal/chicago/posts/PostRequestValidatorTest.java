package io.pivotal.chicago.posts;

import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class PostRequestValidatorTest {
    @InjectMocks
    PostRequestValidator postRequestValidator;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Test
    public void supportsValidatingPostRequests() {
        assertThat(postRequestValidator.supports(PostRequest.class), is(true));
    }

    @Test
    public void failsGivenAPostRequestWithoutATitle() {
        Post target = new Post();
        target.setTitle(null);

        Errors errors = new BeanPropertyBindingResult(target, "post");

        postRequestValidator.validate(target, errors);

        assertThat(errors.hasErrors(), is(true));
        assertThat(errors.getFieldError("title").getCode(), is("blank"));
    }

    @Test
    public void passesGivenAPostRequestWithATitle() {
        Post target = new Post();
        target.setTitle("The title");

        Errors errors = new BeanPropertyBindingResult(target, "post");

        postRequestValidator.validate(target, errors);

        assertThat(errors.hasFieldErrors("title"), is(false));
    }
}