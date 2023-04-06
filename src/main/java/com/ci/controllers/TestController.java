package com.ci.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ci.payload.response.MessageResponse;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/test")
public class TestController {

	@GetMapping("/url1")
//	@PreAuthorize("hasRole('PRIV_1') or hasRole('PRIV_2') or hasRole('PRIV_3')")
	public ResponseEntity<?> url1() {
		return ResponseEntity.ok(new MessageResponse("URL accessible without auth..."));
	}

	@GetMapping("/url2")
//	@PreAuthorize("hasRole('PRIV_1') and hasRole('PRIV_2')")
	@PreAuthorize("hasAuthority('PRIV_1') and hasAuthority('PRIV_2')")
	public ResponseEntity<?> url2() {
		return ResponseEntity.ok(new MessageResponse("URL access with priv 1 and priv 2"));
	}

	@GetMapping("/url3")
	@PreAuthorize("hasAuthority('PRIV_1') and hasAuthority('PRIV_3')")
	public ResponseEntity<?> url3() {
		return ResponseEntity.ok(new MessageResponse("URL access with priv 1 and priv 3"));
	}

	@GetMapping("/url4")
	@PreAuthorize("hasAuthority('PRIV_1') and hasAuthority('PRIV_2') and hasAuthority('PRIV_3')")
	public ResponseEntity<?> url4() {
		return ResponseEntity.ok(new MessageResponse("URL access with priv 1, priv 2 and priv 3"));
	}
}
