package io.pivotal.chicago.posts;

public interface PostService {
    PostResponse createPost(PostRequest postRequest);

    PostResponse find(Long id);
}
