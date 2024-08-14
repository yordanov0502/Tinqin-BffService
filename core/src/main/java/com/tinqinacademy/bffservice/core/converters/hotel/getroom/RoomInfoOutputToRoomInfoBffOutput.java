package com.tinqinacademy.bffservice.core.converters.hotel.getroom;

import com.tinqinacademy.bffservice.api.model.enums.BathroomType;
import com.tinqinacademy.bffservice.api.model.enums.BedSize;
import com.tinqinacademy.bffservice.api.operations.hotelservice.hotel.getroom.RoomInfoBffOutput;
import com.tinqinacademy.bffservice.api.operations.hotelservice.hotel.getroom.content.BookedIntervalBff;
import com.tinqinacademy.bffservice.core.converters.BaseConverter;
import com.tinqinacademy.hotel.api.operations.hotel.getroom.RoomInfoOutput;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RoomInfoOutputToRoomInfoBffOutput extends BaseConverter<RoomInfoOutput, RoomInfoBffOutput,RoomInfoOutputToRoomInfoBffOutput> {

    public RoomInfoOutputToRoomInfoBffOutput() {
        super(RoomInfoOutputToRoomInfoBffOutput.class);
    }

    @Override
    protected RoomInfoBffOutput convertObj(RoomInfoOutput hotelOutput) {

        List<BookedIntervalBff> bookedIntervalsBff =
                hotelOutput.getDatesOccupied()
                .stream()
                .map(b -> new BookedIntervalBff(b.getStartDate(),b.getEndDate()))
                .toList();

        RoomInfoBffOutput bffOutput = RoomInfoBffOutput.builder()
                .roomId(hotelOutput.getRoomId())
                .price(hotelOutput.getPrice())
                .floor(hotelOutput.getFloor())
                .bedSize(BedSize.getByCode(hotelOutput.getBedSize().toString()))
                .bathroomType(BathroomType.getByCode(hotelOutput.getBathroomType().toString()))
                .bedCount(hotelOutput.getBedCount())
                .datesOccupied(bookedIntervalsBff)
                .build();

        return bffOutput;
    }
}
