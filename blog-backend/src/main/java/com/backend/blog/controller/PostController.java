package com.backend.blog.controller;

import com.backend.blog.payload.PostDto;
import com.backend.blog.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    @Autowired
    private PostService postService;

    @PostMapping("/user/{userId}/category/{categoryId}")
    public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto, @PathVariable Integer userId, @PathVariable Integer categoryId){
        PostDto newPost = this.postService.createPost(postDto, userId, categoryId);
        return new ResponseEntity<>(newPost, HttpStatus.CREATED);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<PostDto>> getPostsByUser(@PathVariable Integer userId){
        List<PostDto> listOfPosts = this.postService.getPostsByUser(userId);
        return new ResponseEntity<>(listOfPosts, HttpStatus.OK);
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<PostDto>> getPostsByCategory(@PathVariable Integer categoryId){
        List<PostDto> listOfPosts = this.postService.getPostsByCategory(categoryId);
        return new ResponseEntity<>(listOfPosts, HttpStatus.OK);
    }

}
