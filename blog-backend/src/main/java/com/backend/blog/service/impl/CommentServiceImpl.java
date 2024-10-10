package com.backend.blog.service.impl;

import com.backend.blog.entity.Comment;
import com.backend.blog.entity.Post;
import com.backend.blog.exception.ResourceNotFoundException;
import com.backend.blog.payload.CommentDto;
import com.backend.blog.payload.PostDto;
import com.backend.blog.repository.CommentRepository;
import com.backend.blog.repository.PostRepository;
import com.backend.blog.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CommentDto createDto(CommentDto commentDto, Integer postId) {
        Post post = this.postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));
        Comment comment = this.modelMapper.map(commentDto, Comment.class);
        comment.setPost(post);
        Comment savedComment = this.commentRepository.save(comment);
        return this.modelMapper.map(savedComment, CommentDto.class);
    }

    @Override
    public void deleteComment(Integer commentId) {
        Comment comment = this.commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));
        this.commentRepository.delete(comment);
    }
}
