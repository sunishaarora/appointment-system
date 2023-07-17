package com.perficient.apptsystem.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private Long userId;
    private String firstName;
    private String lastName;
    private String gender;
    private int age;
    private String emailAddresses;
    private String phoneNumbers;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User user)) return false;
        return age == user.age && Objects.equals(userId, user.userId) && Objects.equals(firstName, user.firstName) && Objects.equals(lastName, user.lastName) && Objects.equals(gender, user.gender) && Objects.equals(emailAddresses, user.emailAddresses) && Objects.equals(phoneNumbers, user.phoneNumbers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, firstName, lastName, gender, age, emailAddresses, phoneNumbers);
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", gender='" + gender + '\'' +
                ", age=" + age +
                ", emailAddresses='" + emailAddresses + '\'' +
                ", phoneNumbers='" + phoneNumbers + '\'' +
                '}';
    }
}
