package com.perficient.apptsystem.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Appts {
    private Long id;
    private Long userId;
    private String apptName;
    private String apptType;
    private String description;
    private Date startTime;
    private Date endTime;
    private String metaData;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Appts appts)) return false;
        return Objects.equals(id, appts.id) && Objects.equals(userId, appts.userId) && Objects.equals(apptName, appts.apptName) && Objects.equals(apptType, appts.apptType) && Objects.equals(description, appts.description) && Objects.equals(startTime, appts.startTime) && Objects.equals(endTime, appts.endTime) && Objects.equals(metaData, appts.metaData);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, apptName, apptType, description, startTime, endTime, metaData);
    }

    @Override
    public String toString() {
        return "Appts{" +
                "id=" + id +
                ", userId=" + userId +
                ", apptName='" + apptName + '\'' +
                ", apptType='" + apptType + '\'' +
                ", description='" + description + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", metaData='" + metaData + '\'' +
                '}';
    }
}
