package com.tinqinacademy.bffservice.api.operations.hotelservice.hotel.unbookroom;


import com.tinqinacademy.bffservice.api.base.OperationInput;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.validator.constraints.UUID;

@Builder
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UnbookRoomBffInput implements OperationInput {
    @NotNull
    @UUID
    private String bookingId;
}
