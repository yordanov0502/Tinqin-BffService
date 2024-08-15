package com.tinqinacademy.bffservice.api.operations.hotelservice.hotel.getavailablerooms;



import com.tinqinacademy.bffservice.api.base.OperationInput;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Builder(toBuilder = true)
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class GetIdsOfAvailableRoomsBffInput implements OperationInput {

    @NotNull
    private LocalDate startDate;
    @NotNull
    private LocalDate endDate;
    @Min(value = 1, message = "bedCount should be at least 1.")
    @Max(value = 10, message = "bedCount should be maximum 10.")
    private Integer bedCount;
    private String bedSize;
    private String bathroomType;
}
