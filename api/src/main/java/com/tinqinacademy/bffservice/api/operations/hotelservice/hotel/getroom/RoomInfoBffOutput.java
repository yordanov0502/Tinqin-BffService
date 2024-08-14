package com.tinqinacademy.bffservice.api.operations.hotelservice.hotel.getroom;



import com.tinqinacademy.bffservice.api.base.OperationOutput;
import com.tinqinacademy.bffservice.api.model.enums.BathroomType;
import com.tinqinacademy.bffservice.api.model.enums.BedSize;
import com.tinqinacademy.bffservice.api.operations.hotelservice.hotel.getroom.content.BookedIntervalBff;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Builder(toBuilder = true)
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class RoomInfoBffOutput implements OperationOutput {

    private String roomId;
    private BigDecimal price;
    private Integer floor;
    private BedSize bedSize;
    private BathroomType bathroomType;
    private Integer bedCount;
    private List<BookedIntervalBff> datesOccupied;
}
