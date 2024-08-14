package com.tinqinacademy.bffservice.api.operations.hotelservice.hotel.getroom;


import com.tinqinacademy.bffservice.api.base.OperationInput;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.validator.constraints.UUID;

@Builder(toBuilder = true)
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class RoomInfoBffInput implements OperationInput {
    @NotBlank
    @UUID
    private String roomId;
}
