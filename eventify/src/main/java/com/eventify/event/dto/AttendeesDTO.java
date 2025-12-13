package com.eventify.event.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AttendeesDTO {
    private int attendeesCount;
    private List<AttendeeDetailDTO> attendeesDetail;
}
