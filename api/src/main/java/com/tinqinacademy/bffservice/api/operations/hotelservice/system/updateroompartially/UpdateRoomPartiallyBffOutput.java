package com.tinqinacademy.bffservice.api.operations.hotelservice.system.updateroompartially;

import com.tinqinacademy.bffservice.api.base.OperationOutput;
import lombok.*;

@Builder(toBuilder = true)
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UpdateRoomPartiallyBffOutput implements OperationOutput {
    private String roomId;
}
