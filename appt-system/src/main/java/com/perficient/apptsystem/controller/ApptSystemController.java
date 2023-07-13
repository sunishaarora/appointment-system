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
    public ResponseEntity<?> createUser(@RequestBody User user) {
        try{
            User help = restTemplate.postForObject("http://localhost:8100/api/v1/users", user, User.class);
            return ResponseEntity.ok(help);
        } catch (HttpServerErrorException ex){
            HttpStatus statusCode = (HttpStatus) ex.getStatusCode();
            String errorMessage = ex.getResponseBodyAsString();
            return ResponseEntity.status(statusCode).body(errorMessage);
        }
    }

    @DeleteMapping("/deleteUser/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable Long userId) {
        String url = "http://localhost:8100/api/v1/deleteUser/{userId}";
        HttpEntity<?> requestEntity = new HttpEntity<>(null);
        restTemplate.exchange(url, HttpMethod.DELETE, requestEntity, Void.class, userId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        String url = "http://localhost:8100/api/v1/users";
        ResponseEntity<List<User>> responseEntity = restTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<User>>() {});
        return responseEntity;
    }
    @GetMapping("/user/search/{firstName}/{lastName}")
    public ResponseEntity<?> getUserByName(@PathVariable String firstName, @PathVariable String lastName) {
        String url = "http://localhost:8100/api/v1/user/search/{firstName}/{lastName}";
        Map<String, String> uriVariables = new HashMap<>();
            uriVariables.put("firstName", firstName);
            uriVariables.put("lastName", lastName);
        try{
            ResponseEntity<List<User>> responseEntity = restTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<User>>() {}, uriVariables);
            return responseEntity;
        } catch (HttpClientErrorException ex) {
            HttpStatus statusCode = (HttpStatus) ex.getStatusCode();
            String errorMessage = ex.getResponseBodyAsString();
            return ResponseEntity.status(statusCode).body(errorMessage);
        }

    }
    @PutMapping("/updateUser/{userId}")
    public ResponseEntity<?> updateUser(@PathVariable Long userId, @RequestBody User user) {
        try {
            HttpEntity<User> request = new HttpEntity<>(user);
            ResponseEntity<User> response = restTemplate.exchange("http://localhost:8100/api/v1/updateUser/{userId}", HttpMethod.PUT, request, User.class, userId);
            return ResponseEntity.ok(response.getBody());
        } catch (HttpClientErrorException ex) {
            HttpStatus statusCode = (HttpStatus) ex.getStatusCode();
            String errorMessage = ex.getResponseBodyAsString();
            return ResponseEntity.status(statusCode).body(errorMessage);
        }
    }

    @PostMapping("/appts")
    public ResponseEntity<?> createAppt(@RequestBody Appts appt) {
        try{
            Appts appts = restTemplate.postForObject("http://localhost:8200/api/v1/appts/add", appt, Appts.class);
            return ResponseEntity.ok(appts);
        } catch (HttpClientErrorException ex){
            HttpStatus statusCode = (HttpStatus) ex.getStatusCode();
            String errorMessage = ex.getResponseBodyAsString();
            return ResponseEntity.status(statusCode).body(errorMessage);
        }

    }

    @DeleteMapping("/deleteAppt/{id}")
    public ResponseEntity<?> deleteAppt(@PathVariable Long id) {
        try {
            restTemplate.delete("http://localhost:8200/api/v1/appts/delete/{id}", id);
            return ResponseEntity.ok().body("Appointment deleted");
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
        } catch (HttpClientErrorException ex) {
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
