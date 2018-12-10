package com.moriarty.couchdb_ca.restful_endpoint;

import com.moriarty.couchdb_ca.db_repository.PostRepository;
import com.moriarty.couchdb_ca.entity.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("post")
public class PostController {

    @Autowired
    private PostRepository postRepository;

    @GetMapping("")
    public List<Post> getAllPosts() {
        return postRepository.getAll();
    }

    @GetMapping("/{id}")
    public Post getPost(@PathVariable String id) {
        return postRepository.get(id);
    }

    @PostMapping("")
    public Post addPost(@RequestBody Post Post) {
        postRepository.add(Post);
        return Post;
    }

    @PutMapping("/{id}")
    public Post updatePost(@PathVariable String id, @RequestBody String decription) {
        Post post = postRepository.get(id);
        post.setDescription(decription);
        postRepository.update(post);
        return post;
    }

    @GetMapping("by_subredditId/{subredditId}")
    public List<Post> findBySubredditId(@PathVariable String subredditId) {
        return postRepository.findBySubredditId(subredditId);
    }

    @GetMapping("num_of_comments_on_post/{postId}")
    public int getNumOfCommentsOnPost(@PathVariable String postId) {
        return postRepository.getNumOfCommentsOnPost(postId);
    }

    @GetMapping("/by_title/{title}")
    public List<Post> updatePost(@PathVariable String title) {
        return postRepository.findByTitle(title);
    }

    @DeleteMapping("/{id}")
    public void deletePost(@PathVariable String id) {
        Post Post = postRepository.get(id);
        postRepository.remove(Post);
    }
}
