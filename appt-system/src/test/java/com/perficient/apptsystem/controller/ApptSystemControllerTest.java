package com.perficient.apptsystem.controller;

import com.perficient.apptsystem.model.Appts;
import com.perficient.apptsystem.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ApptSystemControllerTest {

    @MockBean
    private RestTemplate restTemplate;

    @Autowired
    private MockMvc mockMvc;
    private ApptSystemController controller;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        controller = new ApptSystemController(restTemplate, "http://mock-users", "http://mock-appts");
    }
    @Test
    void getAllUsers_Success() throws Exception {
        List<User> users = Arrays.asList(
                new User(1L, "John", "Doe", "Male", 25, "john.doe@gmail.com", "3144411316"),
                new User(2L, "Jane", "Wakes", "Female", 26, "jane.wakes@gmail.com", "3144411317")
        );
        ResponseEntity<List<User>> responseEntity = ResponseEntity.ok(users);
        when(restTemplate.exchange(any(), HttpMethod.GET, null, new ParameterizedTypeReference<List<User>>() {
        })).thenReturn(responseEntity);
    }

    @Test
    void getUserByName_Success() throws Exception {
        String firstName = "John";
        String lastName = "Doe";
        Map<String, String> uriVariables = new HashMap<>();
        uriVariables.put("firstName", firstName);
        uriVariables.put("lastName", lastName);

        List<User> userList = List.of(new User());

        when(restTemplate.exchange(
                eq("http://localhost:8100/api/v1/user/search/{firstName}/{lastName}"),
                eq(HttpMethod.GET),
                isNull(),
                eq(new ParameterizedTypeReference<List<User>>() {
                }),
                eq(uriVariables)
        )).thenReturn(ResponseEntity.ok(userList));

        ResponseEntity<?> responseEntity = controller.getUserByName(firstName, lastName);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        List<User> actualUserList = (List<User>) responseEntity.getBody();
        assertEquals(userList, actualUserList);

        verify(restTemplate, times(1)).exchange(
                eq("http://localhost:8100/api/v1/user/search/{firstName}/{lastName}"),
                eq(HttpMethod.GET),
                isNull(),
                eq(new ParameterizedTypeReference<List<User>>() {
                }),
                eq(uriVariables)
        );
    }

    @Test
    void updateUser_Success() throws Exception {
        String url = "http://localhost:8100/api/v1/updateUser/{userId}";
        User user = new User();
        ResponseEntity<User> response = ResponseEntity.ok(user);
        when(restTemplate.exchange(eq(url), eq(HttpMethod.PUT), any(HttpEntity.class), eq(User.class), eq(1L)))
                .thenReturn(response);
        ResponseEntity<?> responseEntity = controller.updateUser(1L, new User(1L, "John", "Doe", "Male", 25, "john.doe@gmail.com", "3144411316"));
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(user, responseEntity.getBody());
    }

    @Test
    public void createUser_Success() {
        User user = new User();
        when(restTemplate.postForObject("http://localhost:8100/api/v1/users", user, User.class)).thenReturn(user);

        ResponseEntity<?> createdUser = controller.createUser(user);

        assertNotNull(createdUser);
        assertEquals(HttpStatus.OK, createdUser.getStatusCode());
        assertSame(user, createdUser.getBody());
        verify(restTemplate, times(1)).postForObject("http://localhost:8100/api/v1/users", user, User.class);

    }

    @Test
    public void testCreateAppt() {
        Appts appt = new Appts();
        when(restTemplate.postForObject("http://localhost:8200/api/v1/appts/add", appt, Appts.class)).thenReturn(appt);

        ResponseEntity<?> createdAppt = controller.createAppt(appt);

        assertNotNull(createdAppt);
        assertEquals(HttpStatus.OK, createdAppt.getStatusCode());
        assertSame(appt, createdAppt.getBody());
        verify(restTemplate, times(1)).postForObject("http://localhost:8200/api/v1/appts/add", appt, Appts.class);
    }

    @Test
    public void testUpdateAppt() {
        Long id = 1L;
        Appts appt = new Appts();
        ResponseEntity<Appts> response = ResponseEntity.ok(appt);
        HttpEntity<Appts> request = new HttpEntity<>(appt);
        when(restTemplate.exchange("http://localhost:8200/api/v1/appts/update/{id}", HttpMethod.PUT, request, Appts.class, id))
                .thenReturn(response);

        ResponseEntity<?> responseEntity = controller.updateAppt(id, appt);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertSame(appt, responseEntity.getBody());
        verify(restTemplate, times(1))
                .exchange("http://localhost:8200/api/v1/appts/update/{id}", HttpMethod.PUT, request, Appts.class, id);
    }

    @Test
    void testDeleteAppt() {
        Long id = 1L;
        doNothing().when(restTemplate).delete("http://localhost:8200/api/v1/appts/delete/{id}", id);

        ResponseEntity<?> responseEntity = controller.deleteAppt(id);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        verify(restTemplate, times(1)).delete("http://localhost:8200/api/v1/appts/delete/{id}", id);
    }

    @Test
    public void testGetAppt() {
        Long id = 1L;
        Appts appt = new Appts();
        when(restTemplate.getForEntity(eq("http://localhost:8200/api/v1/appts/{id}"), eq(Appts.class), eq(id)))
                .thenReturn(ResponseEntity.ok(appt));

        ResponseEntity<?> responseEntity = controller.getAppt(id);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertSame(appt, responseEntity.getBody());
        verify(restTemplate, times(1))
                .getForEntity(eq("http://localhost:8200/api/v1/appts/{id}"), eq(Appts.class), eq(id));
    }

    @Test
    public void testGetApptList() {
        List<Appts> apptList = Arrays.asList(new Appts(), new Appts());

        when(restTemplate.exchange(eq("http://localhost:8200/api/v1/appts"), eq(HttpMethod.GET), isNull(),
                eq(new ParameterizedTypeReference<List<Appts>>() {
                })))
                .thenReturn(ResponseEntity.ok(apptList));

        ResponseEntity<List<Appts>> responseEntity = controller.getAllAppts();

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertSame(apptList, responseEntity.getBody());
        verify(restTemplate, times(1))
                .exchange(eq("http://localhost:8200/api/v1/appts"), eq(HttpMethod.GET), isNull(),
                        eq(new ParameterizedTypeReference<List<Appts>>() {
                        }));
    }

}


//    @Test
//    void deleteUser_Success() throws Exception {
//        Long userId = 1L;
//        ResponseEntity<Void> responseEntity = ResponseEntity.ok().build();
//        when(restTemplate.exchange(anyString(), eq(HttpMethod.DELETE), any(), eq(Void.class), eq(userId))).thenReturn(responseEntity);
////        ResponseEntity<?> response = controller.deleteUser(userId);
////        assertEquals(HttpStatus.OK, response.getStatusCode());
//        mockMvc.perform(delete("/management-system/deleteUser/{userId}", userId)).andExpect(status().isOk());
//        verify(restTemplate, times(1)).exchange(anyString(), eq(HttpMethod.DELETE), any(), eq(Void.class), eq(userId));
//    }