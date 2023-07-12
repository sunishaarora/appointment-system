package com.perficient.apptsystem.controller;

import com.perficient.apptsystem.model.Appts;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class ApptSystemControllerTest {

    @MockBean
    private RestTemplate restTemplate;

    @InjectMocks
    private ApptSystemController controller;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        controller = new ApptSystemController(restTemplate);
    }

    @Test
    public void testCreateAppt() {
        Appts appt = new Appts();
        when(restTemplate.postForObject("http://localhost:8200/api/v1/appts/add", appt, Appts.class)).thenReturn(appt);

        Appts createdAppt = controller.createAppt(appt);

        assertNotNull(createdAppt);
        assertSame(appt, createdAppt);
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
    public void testGetAppt(){
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
    public void testGetApptList(){
        List<Appts> apptList = Arrays.asList(new Appts(), new Appts());

        when(restTemplate.exchange(eq("http://localhost:8200/api/v1/appts"), eq(HttpMethod.GET), isNull(),
                eq(new ParameterizedTypeReference<List<Appts>>() {})))
                .thenReturn(ResponseEntity.ok(apptList));

        ResponseEntity<List<Appts>> responseEntity = controller.getAllAppts();

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertSame(apptList, responseEntity.getBody());
        verify(restTemplate, times(1))
                .exchange(eq("http://localhost:8200/api/v1/appts"), eq(HttpMethod.GET), isNull(),
                        eq(new ParameterizedTypeReference<List<Appts>>() {}));
    }

}