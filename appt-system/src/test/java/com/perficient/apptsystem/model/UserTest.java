package com.perficient.apptsystem.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class UserTest {

    @Test
    void testConstructorAndGetters() {
        Long userId = 1L;
        String firstName = "John";
        String lastName = "Doe";
        String gender = "Male";
        int age = 30;
        String emailAddresses = "john.doe@example.com";
        String phoneNumbers = "1234567890";

        User user = new User(userId, firstName, lastName, gender, age, emailAddresses, phoneNumbers);

        assertEquals(userId, user.getUserId());
        assertEquals(firstName, user.getFirstName());
        assertEquals(lastName, user.getLastName());
        assertEquals(gender, user.getGender());
        assertEquals(age, user.getAge());
        assertEquals(emailAddresses, user.getEmailAddresses());
        assertEquals(phoneNumbers, user.getPhoneNumbers());
    }

    @Test
    void testEqualsAndHashCode() {
        User user1 = new User(1L, "John", "Doe", "Male", 30, "john.doe@example.com", "1234567890");
        User user2 = new User(1L, "John", "Doe", "Male", 30, "john.doe@example.com", "1234567890");
        User user3 = new User(2L, "Jane", "Smith", "Female", 25, "jane.smith@example.com", "9876543210");

        assertTrue(user1.equals(user2));
        assertTrue(user2.equals(user1));
        assertFalse(user1.equals(user3));
        assertFalse(user3.equals(user1));

        assertEquals(user1.hashCode(), user2.hashCode());
        assertNotEquals(user1.hashCode(), user3.hashCode());
    }

    @Test
    void testToString() {
        User user = new User(1L, "John", "Doe", "Male", 30, "john.doe@example.com", "1234567890");
        String expectedToString = "User{userId=1, firstName='John', lastName='Doe', gender='Male', age=30, emailAddresses='john.doe@example.com', phoneNumbers='1234567890'}";

        assertEquals(expectedToString, user.toString());
    }
}
