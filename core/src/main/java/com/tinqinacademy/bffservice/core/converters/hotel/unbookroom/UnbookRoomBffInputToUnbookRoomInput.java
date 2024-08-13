package com.tinqinacademy.bffservice.core.converters.hotel.unbookroom;

import com.tinqinacademy.bffservice.api.operations.hotelservice.hotel.unbookroom.UnbookRoomBffInput;
import com.tinqinacademy.bffservice.core.converters.BaseConverter;
import com.tinqinacademy.hotel.api.operations.hotel.unbookroom.UnbookRoomInput;
import org.springframework.stereotype.Component;

@Component
public class UnbookRoomBffInputToUnbookRoomInput extends BaseConverter<UnbookRoomBffInput, UnbookRoomInput,UnbookRoomBffInputToUnbookRoomInput> {
    public UnbookRoomBffInputToUnbookRoomInput() {
        super(UnbookRoomBffInputToUnbookRoomInput.class);
    }

    @Override
    protected UnbookRoomInput convertObj(UnbookRoomBffInput bffInput) {
        UnbookRoomInput input = UnbookRoomInput.builder()
                .bookingId(bffInput.getBookingId())
                .build();

        return input;
    }
}
