package io.pivotal.chicago.posts;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

import static org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder.fromMethodCall;
import static org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder.on;

@RestController
public class PostsController {
    @Autowired
    private PostService postService;

    @RequestMapping(value = "/posts", method = RequestMethod.POST)
    public ResponseEntity<Void> create(@RequestBody PostRequest postRequest) {
        PostResponse postResponse = postService.createPost(postRequest);

        URI uri = fromMethodCall(on(PostsController.class).show(postResponse.getId().toString()))
                .build()
                .toUri();

        return ResponseEntity.created(uri).build();
    }

    @RequestMapping(value = "/posts/{id}", method = RequestMethod.GET)
    public PostResponse show(@PathVariable String id) {
        return null;
    }
}
