package com.tinqinacademy.bffservice.api.operations.hotelservice.hotel.unbookroom.getuseridofbooking;

import com.tinqinacademy.bffservice.api.base.OperationOutput;
import lombok.*;

@Builder(toBuilder = true)
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class GetUserIdOfBookingBffOutput implements OperationOutput {
    private String userId;
}
