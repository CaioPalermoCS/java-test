package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Base64;

@SpringBootApplication
@RestController
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@GetMapping("/search")
	public String searchUser(@RequestParam String user) throws Exception {
		Connection connection = DriverManager.getConnection("jdbc:h2:mem:test", "sa", "");
		Statement statement = connection.createStatement();

		String sql = "SELECT * FROM users WHERE name = '" + user + "'";
		ResultSet rs = statement.executeQuery(sql);

		StringBuilder result = new StringBuilder();
		while (rs.next()) {
			result.append(rs.getString(1)).append("\n");
		}

		return result.toString();
	}

	@GetMapping("/redirect")
	public String redirectTo(@RequestParam String next) {
		return "redirect:" + next;
	}

	@GetMapping("/file")
	public String readFile(@RequestParam String filename) throws Exception {
		File file = new File("/tmp/uploads/" + filename);
		return file.getCanonicalPath();
	}

	@PostMapping("/session")
	public String storeInSession(HttpServletRequest request, HttpSession session) {
		String role = request.getParameter("role");
		session.setAttribute("role", role);
		return "saved";
	}

	@GetMapping("/hash")
	public String insecureHash(@RequestParam String value) throws Exception {
		MessageDigest md = MessageDigest.getInstance("MD5");
		byte[] digest = md.digest(value.getBytes(StandardCharsets.UTF_8));
		return Base64.getEncoder().encodeToString(digest);
	}

	@GetMapping("/proxy")
	public String buildRemoteUrl(@RequestParam String host) {
		String url = "http://" + host + "/api/data";
		return url;
	}
}
