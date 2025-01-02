//package com.rubypaper.comment;
//
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.annotation.AuthenticationPrincipal;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.PutMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.rubypaper.service.SecurityUser;
//
//@RestController
//@RequestMapping("/comments")
//public class CommentController {
//	
//	@Autowired
//	private CommentService commentServ;
//	
//	@GetMapping
//	public List<Comment> getComments(){
//		return commentServ.getComments();
//	}
//	
//	@PostMapping
//	public Comment insertComment(@RequestBody Comment comment, @AuthenticationPrincipal SecurityUser principal) {
//		comment.setNickname(principal.getMember().getNickname());
//		return commentServ.insertComment(comment);
//	}
//	
//	@PutMapping
//	public Comment updateComment(@RequestParam Long id, @AuthenticationPrincipal SecurityUser principal, )
//
//}
