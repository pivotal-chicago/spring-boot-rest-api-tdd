package io.pivotal.chicago.posts;

public interface PostService {
    PostResponse create(PostRequest postRequest);

    PostResponse find(Long id);
}
