package io.pivotal.chicago.post;

import java.util.Optional;

public interface PostService {
    PostResponse create(PostRequest postRequest);

    Optional<PostResponse> find(Long id);
}
