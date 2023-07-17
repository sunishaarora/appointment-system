package com.perficient.apptsystem.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.Date;

class ApptsTest {

    @Test
    public void testConstructorAndGetters() {

        Long id = 1L;
        Long userId = 2L;
        String apptName = "Test Appointment";
        String apptType = "Type 1";
        String description = "Test description";
        Date startTime = new Date();
        Date endTime = new Date();
        String metaData = "Test metadata";

        Appts appt = new Appts(id, userId, apptName, apptType, description, startTime, endTime, metaData);

        Assertions.assertEquals(id, appt.getId());
        Assertions.assertEquals(userId, appt.getUserId());
        Assertions.assertEquals(apptName, appt.getApptName());
        Assertions.assertEquals(apptType, appt.getApptType());
        Assertions.assertEquals(description, appt.getDescription());
        Assertions.assertEquals(startTime, appt.getStartTime());
        Assertions.assertEquals(endTime, appt.getEndTime());
        Assertions.assertEquals(metaData, appt.getMetaData());
    }

    @Test
    public void testEqualsAndHashCode() {
        Appts appt1 = new Appts(1L, 2L, "Test1", "Type1", "Description1", new Date(), new Date(), "Metadata1");
        Appts appt2 = new Appts(1L, 2L, "Test1", "Type1", "Description1", new Date(), new Date(), "Metadata1");
        Appts appt3 = new Appts(2L, 3L, "Test2", "Type2", "Description2", new Date(), new Date(), "Metadata2");

        Assertions.assertEquals(appt1, appt2);
        Assertions.assertNotEquals(appt1, appt3);
        Assertions.assertEquals(appt1.hashCode(), appt2.hashCode());
        Assertions.assertNotEquals(appt1.hashCode(), appt3.hashCode());
    }

    @Test
    public void testToString() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2023, Calendar.JULY, 13, 13, 56, 10);
        Date testDate = calendar.getTime();
        Appts appt = new Appts(1L, 2L, "Test Appointment", "Type 1", "Test description", testDate, testDate, "Test metadata");
        String expectedString = "Appts{id=1, userId=2, apptName='Test Appointment', apptType='Type 1', description='Test description', startTime=Thu Jul 13 13:56:10 CDT 2023, endTime=Thu Jul 13 13:56:10 CDT 2023, metaData='Test metadata'}";

        String actualString = appt.toString();

        Assertions.assertTrue(expectedString.equals(actualString));
    }

}