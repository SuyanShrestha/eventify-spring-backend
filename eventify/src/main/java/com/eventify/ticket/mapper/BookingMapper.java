package com.eventify.ticket.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import com.eventify.event.dto.BookingDTO;
import com.eventify.ticket.model.BookedTicket;

@Mapper( componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BookingMapper {

    @Mapping(source = "id", target = "bookingId")
    @Mapping(source = "ticket.purchaseDate", target = "bookedAt")
    BookingDTO toDto(BookedTicket bookedTicket);

}