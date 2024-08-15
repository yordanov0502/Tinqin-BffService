package com.tinqinacademy.bffservice.core.converters.hotel.getavailablerooms;

import com.tinqinacademy.bffservice.api.operations.hotelservice.hotel.getavailablerooms.AvailableRoomsIdsBffOutput;
import com.tinqinacademy.bffservice.core.converters.BaseConverter;
import com.tinqinacademy.hotel.api.operations.hotel.getavailablerooms.AvailableRoomsIdsOutput;
import org.springframework.stereotype.Component;

@Component
public class AvailableRoomIdsOutputToBffOutput extends BaseConverter<AvailableRoomsIdsOutput, AvailableRoomsIdsBffOutput, AvailableRoomIdsOutputToBffOutput> {
    public AvailableRoomIdsOutputToBffOutput() {
        super(AvailableRoomIdsOutputToBffOutput.class);
    }


    @Override
    protected AvailableRoomsIdsBffOutput convertObj(AvailableRoomsIdsOutput hotelOutput) {

        AvailableRoomsIdsBffOutput bffOutput = AvailableRoomsIdsBffOutput.builder()
                .availableRoomsIds(hotelOutput.getAvailableRoomsIds())
                .build();

        return bffOutput;
    }
}