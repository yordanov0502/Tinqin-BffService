package com.tinqinacademy.bffservice.api.operations.hotelservice.hotel.unbookroom.getuseridofbooking;

import com.tinqinacademy.bffservice.api.base.OperationInput;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.validator.constraints.UUID;

@Builder(toBuilder = true)
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class GetUserIdOfBookingBffInput implements OperationInput {
    @NotNull
    @UUID
    private String bookingId;
}
