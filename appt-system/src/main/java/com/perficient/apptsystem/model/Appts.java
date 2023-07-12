package com.perficient.apptsystem.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Appts {
    private Long id;
    private Long userId;
    private String apptName;
    private String apptType;
    private String description;
    private Date startTime;
    private Date endTime;
    private String metaData;
}
