package io.pivotal.chicago.post;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DefaultPostService implements PostService {
    @Autowired
    private PostRepository postRepository;

    @Override
    public PostResponse create(PostRequest postRequest) {
        Post post = new Post();
        post.setTitle(postRequest.getTitle());

        post = postRepository.save(post);

        PostResponse postResponse = new PostResponse();
        postResponse.setId(post.getId());
        postResponse.setTitle(post.getTitle());
        return postResponse;
    }

    @Override
    public PostResponse find(Long id) {
        Post post = postRepository.findOne(id);

        PostResponse postResponse = new PostResponse();
        postResponse.setId(post.getId());
        postResponse.setTitle(post.getTitle());
        return postResponse;
    }
}
