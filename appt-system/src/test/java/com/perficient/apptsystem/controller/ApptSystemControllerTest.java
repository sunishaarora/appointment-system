package com.perficient.apptsystem.controller;

import com.perficient.apptsystem.model.Appts;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

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
        when(restTemplate.postForObject(anyString(), any(Appts.class), eq(Appts.class))).thenReturn(appt);

        Appts createdAppt = controller.createAppt(appt);

        assertNotNull(createdAppt);
        assertSame(appt, createdAppt);
        verify(restTemplate, times(1)).postForObject(anyString(), any(Appts.class), eq(Appts.class));
    }

    @Test
    void testDeleteAppt() {
        Long id = 1L;
        doNothing().when(restTemplate).delete(anyString(), eq(id));

        ResponseEntity<?> responseEntity = controller.deleteAppt(id);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        verify(restTemplate, times(1)).delete(anyString(), eq(id));
    }

    @Test
    public void testUpdateAppt() {
        Long id = 1L;
        Appts appt = new Appts();
        ResponseEntity<Appts> response = ResponseEntity.ok(appt);
        when(restTemplate.exchange(anyString(), eq(HttpMethod.PUT), any(HttpEntity.class), eq(Appts.class), eq(id)))
                .thenReturn(response);

        ResponseEntity<?> responseEntity = controller.updateAppt(id, appt);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertSame(appt, responseEntity.getBody());
        verify(restTemplate, times(1))
                .exchange(anyString(), eq(HttpMethod.PUT), any(HttpEntity.class), eq(Appts.class), eq(id));
    }
}