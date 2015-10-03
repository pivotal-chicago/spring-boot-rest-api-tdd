package io.pivotal.chicago.posts;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

import static java.util.stream.Collectors.toMap;
import static org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder.fromMethodCall;
import static org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder.on;

@RestController
public class PostsController {
    @Autowired
    private PostService postService;

    @Autowired
    private PostRequestValidator postRequestValidator;

    @Autowired
    private MessageSource messageSource;

    @InitBinder
    protected void initBinder(WebDataBinder webDataBinder) {
        webDataBinder.setValidator(postRequestValidator);
    }

    @RequestMapping(value = "/posts", method = RequestMethod.POST)
    public ResponseEntity<Void> create(@Validated @RequestBody PostRequest postRequest,
                                       Errors errors) {
        if (errors.hasErrors()) {
            throw new InvalidResourceException(errors);
        }
        PostResponse postResponse = postService.createPost(postRequest);

        URI uri = fromMethodCall(on(PostsController.class).show(postResponse.getId()))
                .build()
                .toUri();

        return ResponseEntity.created(uri).build();
    }

    @RequestMapping(value = "/posts/{id}", method = RequestMethod.GET)
    public PostResponse show(@PathVariable Long id) {
        return postService.find(id);
    }

    @ExceptionHandler(InvalidResourceException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ErrorResponse invalidResourceException(InvalidResourceException e) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setErrors(e.getErrors()
                        .getFieldErrors()
                        .stream()
                        .collect(
                                toMap(FieldError::getField,
                                        t -> messageSource.getMessage(t.getCode(), null, null))
                        )
        );
        return errorResponse;
    }
}
