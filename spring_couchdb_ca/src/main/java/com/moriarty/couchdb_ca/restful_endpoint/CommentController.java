package com.moriarty.couchdb_ca.restful_endpoint;

import com.moriarty.couchdb_ca.db_repository.CommentRepository;
import com.moriarty.couchdb_ca.db_repository.PostRepository;
import com.moriarty.couchdb_ca.entity.Comment;
import com.moriarty.couchdb_ca.entity.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("comment")
public class CommentController {

    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private PostRepository postRepository;

    @GetMapping("/{postId}")
    public List<Comment> getCommentsOnPost(@PathVariable String postId) {
        return commentRepository.findByPostId(postId);
    }

    @PutMapping("/{id}")
    public Post updatePost(@PathVariable String id, @RequestBody String commentContent) {
        Comment comment = new Comment();
        comment.setContent(commentContent);
        Post post = postRepository.get(id);
        post.addComment(comment);
        postRepository.update(post);
        return post;
    }

    @PutMapping("/{id}/comment")
    public Post addPostComment(@PathVariable String id, @RequestBody String content) {
        Post post = postRepository.get(id);
        Comment comment = new Comment();
        comment.setContent(content);
        post.addComment(comment);
        postRepository.update(post);
        return post;
    }
}
