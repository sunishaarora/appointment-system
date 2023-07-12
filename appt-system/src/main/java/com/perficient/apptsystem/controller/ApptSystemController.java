package com.perficient.apptsystem.controller;

import com.perficient.apptsystem.model.User;
import com.perficient.apptsystem.model.Appts;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@RestController
@RequestMapping("/management-system/")
public class ApptSystemController {
    private static RestTemplate restTemplate = new RestTemplate();

    public ApptSystemController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @PostMapping("/users")
    public User createUser(@RequestBody User user) {
        return restTemplate.postForObject("http://localhost:8100/api/v1/users", user, User.class);
    }

    @DeleteMapping("/deleteUser/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable Long userId) {
        restTemplate.delete("http://localhost:8100/api/v1/deleteUser/{userId}", userId);
        return null;
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        String url = "http://localhost:8100/api/v1/users";
        ResponseEntity<List<User>> responseEntity = restTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<User>>() {
        });
        return responseEntity;
    }

    @PutMapping("/updateUser/{userId}")
    public ResponseEntity<User> updateUser(@PathVariable Long userId, @RequestBody User user) {
        HttpEntity<User> request = new HttpEntity<>(user);
        ResponseEntity<User> response = restTemplate.exchange("http://localhost:8100/api/v1/updateUser/" + userId, HttpMethod.PUT, request, User.class);
        return ResponseEntity.ok(response.getBody());
    }

    @GetMapping("/user/search/{firstName}/{lastName}")
    public ResponseEntity<List<User>> getUserByName(@PathVariable String firstName, @PathVariable String lastName) {
        String userMicroserviceUrl = "http://localhost:8100/api/v1/";
        String searchUserByNameEndpoint = "/user/search/{firstName}/{lastName}";
        String requestUrl = userMicroserviceUrl + searchUserByNameEndpoint;

        Map<String, String> uriVariables = new HashMap<>();
        uriVariables.put("firstName", firstName);
        uriVariables.put("lastName", lastName);

        ResponseEntity<User[]> responseEntity = restTemplate.getForEntity(
                requestUrl,
                User[].class,
                uriVariables
        );

        User[] usersArray = responseEntity.getBody();
        List<User> userList = (usersArray != null) ? Arrays.asList(usersArray) : Collections.emptyList();

        if (userList.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(userList);
    }

    @PostMapping("/appts")
    public Appts createAppt(@RequestBody Appts appt) {
        return restTemplate.postForObject("http://localhost:8200/api/v1/appts/add", appt, Appts.class);
    }

    @DeleteMapping("/deleteAppt/{id}")
    public ResponseEntity<?> deleteAppt(@PathVariable Long id) {
        try {
            restTemplate.delete("http://localhost:8200/api/v1/appts/delete/{id}", id);
            return ResponseEntity.ok().build();
        } catch (HttpClientErrorException ex) {
            HttpStatus statusCode = (HttpStatus) ex.getStatusCode();
            String errorMessage = ex.getResponseBodyAsString();
            return ResponseEntity.status(statusCode).body(errorMessage);
        }
    }

    @GetMapping("/appts/{id}")
    public ResponseEntity<?> getAppt(@PathVariable Long id) {
        try {
            ResponseEntity<?> responseEntity = restTemplate.getForEntity("http://localhost:8200/api/v1/appts/{id}", Appts.class, id);
            return ResponseEntity.ok(responseEntity.getBody());
        } catch (HttpClientErrorException ex) {
            HttpStatus statusCode = (HttpStatus) ex.getStatusCode();
            String errorMessage = ex.getResponseBodyAsString();
            return ResponseEntity.status(statusCode).body(errorMessage);
        }
    }

    @PutMapping("/appts/updateAppt/{id}")
    public ResponseEntity<?> updateAppt(@PathVariable Long id, @RequestBody Appts appt) {
        try {
            HttpEntity<Appts> request = new HttpEntity<>(appt);
            ResponseEntity<Appts> response = restTemplate.exchange("http://localhost:8200/api/v1/appts/update/{id}", HttpMethod.PUT, request, Appts.class, id);
            return ResponseEntity.ok(response.getBody());
        } catch (HttpServerErrorException ex) {
            HttpStatus statusCode = (HttpStatus) ex.getStatusCode();
            String errorMessage = ex.getResponseBodyAsString();
            return ResponseEntity.status(statusCode).body(errorMessage);
        }
    }

    @GetMapping("/appts")
    public ResponseEntity<List<Appts>> getAllAppts() {
        String url = "http://localhost:8200/api/v1/appts";
        ResponseEntity<List<Appts>> responseEntity = restTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<Appts>>() {
        });
        return responseEntity;
    }

}
