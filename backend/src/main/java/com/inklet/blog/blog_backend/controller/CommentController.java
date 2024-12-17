package com.inklet.blog.blog_backend.controller;

import com.inklet.blog.blog_backend.configuration.JwtTokenProvider;
import com.inklet.blog.blog_backend.dto.CreateCommentDTO;
import com.inklet.blog.blog_backend.service.CommentService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    private final CommentService commentService;
    private final JwtTokenProvider jwtTokenProvider;

    public CommentController(CommentService commentService, JwtTokenProvider jwtTokenProvider) {
        this.commentService = commentService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PostMapping("/create")
    public ResponseEntity<CreateCommentDTO> createComment(@Valid @RequestBody CreateCommentDTO createCommentDTO,
                                                          HttpServletRequest request) {

        String token = request.getHeader("Authorization").substring(7);
        String username = jwtTokenProvider.getUsernameFromToken(token);

        CreateCommentDTO commentDTO = commentService.createComment(createCommentDTO, username);

        return new ResponseEntity<>(commentDTO, HttpStatus.CREATED);
    }
}
