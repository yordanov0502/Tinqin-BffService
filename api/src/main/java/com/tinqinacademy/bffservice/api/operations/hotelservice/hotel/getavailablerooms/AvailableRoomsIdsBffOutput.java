package com.tinqinacademy.bffservice.api.operations.hotelservice.hotel.getavailablerooms;

import com.tinqinacademy.bffservice.api.base.OperationOutput;
import lombok.*;

import java.util.List;

@Builder(toBuilder = true)
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class AvailableRoomsIdsBffOutput implements OperationOutput {

    private List<String> availableRoomsIds;
}
