package io.pivotal.chicago.post;

public interface PostService {
    PostResponse create(PostRequest postRequest);

    PostResponse find(Long id);
}
