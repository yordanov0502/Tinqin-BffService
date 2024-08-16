package com.tinqinacademy.bffservice.api.operations.hotelservice.system.createroom;

import com.tinqinacademy.bffservice.api.base.OperationOutput;
import lombok.*;

@Builder(toBuilder = true)
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CreateRoomBffOutput implements OperationOutput {
    private String roomId;
}
