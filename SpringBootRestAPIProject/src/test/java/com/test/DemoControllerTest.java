package com.test;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;

import static org.assertj.core.api.Assertions.assertThat;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DemoControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private String url(String param) {
        return "http://localhost:" + port + "/remove?original=" + param;
    }

    @Test
    void testBasicFunctionality() {
        ResponseEntity<String> response = restTemplate.getForEntity(url("eloquent"), String.class);
        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.getBody()).isEqualTo("loquen");
    }

    @Test
    void testTwoCharactersReturnsEmpty() {
        ResponseEntity<String> response = restTemplate.getForEntity(url("ab"), String.class);
        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.getBody()).isEqualTo("");
    }

    @Test
    void testOneCharacterReturnsBadRequest() {
        ResponseEntity<String> response = restTemplate.getForEntity(url("a"), String.class);
        assertThat(response.getStatusCodeValue()).isEqualTo(400);
    }

    @Test
    void testSpecialCharacters() {
        ResponseEntity<String> response = restTemplate.getForEntity(url("_123_%qwerty+"), String.class);
        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.getBody()).isEqualTo("123_%qwerty");
    }
}
