package com.backend.blog.service;

import com.backend.blog.payload.CommentDto;

public interface CommentService {

    CommentDto createDto(CommentDto commentDto, Integer postId);
    void deleteComment(Integer commentId);

}
