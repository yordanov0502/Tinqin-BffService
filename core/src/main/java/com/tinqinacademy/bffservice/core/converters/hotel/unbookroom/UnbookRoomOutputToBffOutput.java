package com.tinqinacademy.bffservice.core.converters.hotel.unbookroom;

import com.tinqinacademy.bffservice.api.operations.hotelservice.hotel.unbookroom.UnbookRoomBffOutput;
import com.tinqinacademy.bffservice.core.converters.BaseConverter;
import com.tinqinacademy.hotel.api.operations.hotel.unbookroom.UnbookRoomOutput;
import org.springframework.stereotype.Component;

@Component
public class UnbookRoomOutputToBffOutput extends BaseConverter<UnbookRoomOutput, UnbookRoomBffOutput,UnbookRoomOutputToBffOutput> {
    public UnbookRoomOutputToBffOutput() {
        super(UnbookRoomOutputToBffOutput.class);
    }

    @Override
    protected UnbookRoomBffOutput convertObj(UnbookRoomOutput source) {
        UnbookRoomBffOutput bffOutput = UnbookRoomBffOutput.builder().build();
        return bffOutput;
    }
}
