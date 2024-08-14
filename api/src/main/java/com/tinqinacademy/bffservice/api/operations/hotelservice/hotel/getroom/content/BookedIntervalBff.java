package com.tinqinacademy.bffservice.api.operations.hotelservice.hotel.getroom.content;

import lombok.*;

import java.time.LocalDate;

@Builder(toBuilder = true)
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class BookedIntervalBff {
    private LocalDate startDate;
    private LocalDate endDate;
}
