package com.eventify.ticket.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import com.eventify.ticket.dto.BookingDTO;
import com.eventify.ticket.dto.TicketInfoDTO;
import com.eventify.ticket.model.BookedTicket;

@Mapper( componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BookingMapper {

    @Mapping(source = "id", target = "bookingId")
    @Mapping(source = "ticket.purchaseDate", target = "bookedAt")
    BookingDTO toDto(BookedTicket bookedTicket);

    @Mapping(source = "ticket.event.title", target = "eventName")
    @Mapping(source = "ticket.ticketCode", target = "ticketCode")
    @Mapping(source = "ticket.quantity", target = "ticketQuantity")
    @Mapping(source = "ticket.status", target = "ticketStatus")
    @Mapping(source = "ticket.purchaseDate", target = "purchaseDate")
    @Mapping(source = "ticket.user.username", target = "attendeeName")
    @Mapping(source = "checkedInTime", target = "checkInTime")
    TicketInfoDTO toTicketInfo(BookedTicket bookedTicket);

}