package com.tinqinacademy.bffservice.api.operations.hotelservice.system.updateroom;

import com.tinqinacademy.bffservice.api.base.OperationOutput;
import lombok.*;

@Builder(toBuilder = true)
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UpdateRoomBffOutput implements OperationOutput {
    private String roomId;
}
