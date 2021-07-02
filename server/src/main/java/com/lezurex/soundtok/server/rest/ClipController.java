package com.lezurex.soundtok.server.rest;

import com.lezurex.soundtok.server.database.entities.Clip;
import com.lezurex.soundtok.server.database.entities.Comment;
import com.lezurex.soundtok.server.database.entities.User;
import com.lezurex.soundtok.server.database.repositories.ClipRepo;
import com.lezurex.soundtok.server.database.repositories.CommentRepo;
import com.lezurex.soundtok.server.exceptions.NotFoundException;
import com.lezurex.soundtok.server.rest.output.SimpleClip;
import com.lezurex.soundtok.server.security.CustomUserDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.HtmlUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@RestController
@Slf4j
public class ClipController {

    @Autowired
    private ClipRepo clipRepo;
    @Autowired
    private CommentRepo commentRepo;

    @GetMapping(value = "/api/clip/{clipId}")
    public SimpleClip getClip(@PathVariable int clipId, Authentication authentication) throws NotFoundException {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        Clip clip = clipRepo.findById(clipId).orElseThrow(NotFoundException::new);
        return new SimpleClip(clip, userDetails.getUser());
    }

    @GetMapping(value = "/api/clip/{clipId}/comments")
    public List<Comment> getClipComments(@PathVariable int clipId) throws NotFoundException {
        Clip clip = clipRepo.findById(clipId).orElseThrow(NotFoundException::new);
        return clip.getComments();
    }

    @PostMapping(value = "/api/clip/{clipId}/comments")
    public ResponseEntity<String> postComment(@PathVariable int clipId, @RequestParam String content, Authentication authentication) throws NotFoundException {
        User user = ((CustomUserDetails) authentication.getPrincipal()).getUser();
        Clip clip = clipRepo.findById(clipId).orElseThrow(NotFoundException::new);
        Comment comment = new Comment(HtmlUtils.htmlEscape(content), new Date(), user);
        clip.getComments().add(comment);
        commentRepo.save(comment);
        clipRepo.save(clip);
        return new ResponseEntity<>("", HttpStatus.OK);
    }

    @GetMapping(value = "/api/clip/{clipId}/likes")
    public Collection<User> getClipLikes(@PathVariable int clipId) throws NotFoundException {
        Clip clip = clipRepo.findById(clipId).orElseThrow(NotFoundException::new);
        return clip.getLikes();
    }

    @GetMapping(value = "/api/clip/{clipId}/audio")
    public ResponseEntity<InputStreamResource> getClipAudio(HttpServletRequest request, HttpServletResponse response, @PathVariable int clipId) throws IOException {
        Clip clip = clipRepo.findById(clipId).orElseThrow(NotFoundException::new);
        InputStreamResource inputStreamResource = new InputStreamResource(new ByteArrayInputStream(clip.getAudio()));
        HttpHeaders headers = new HttpHeaders();
        headers.setContentLength(clip.getAudio().length);
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        return new ResponseEntity<>(inputStreamResource, headers, HttpStatus.OK);
    }

    @PostMapping(value = "/api/clip")
    public String postClip(@RequestParam String caption, @RequestParam MultipartFile audio, Authentication authentication) throws IOException {
        User user = ((CustomUserDetails) authentication.getPrincipal()).getUser();
        byte[] bytes = audio.getBytes();
        Clip clip = new Clip(caption, new Date(), user, bytes);
        this.clipRepo.save(clip);
        return "success";
    }

    @PatchMapping(value = "/api/clip/{clipId}/like")
    public String likeClip(@PathVariable int clipId, Authentication authentication) throws NotFoundException {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        Clip clip = clipRepo.findById(clipId).orElseThrow(NotFoundException::new);
        User liker = clip.getLikes().stream().findAny().filter(user -> userDetails.getUser().getId() == user.getId()).orElse(null);
        if (liker != null) {
            clip.getLikes().remove(liker);
        } else
            clip.getLikes().add(userDetails.getUser());
        clipRepo.save(clip);
        return "success";
    }

}
