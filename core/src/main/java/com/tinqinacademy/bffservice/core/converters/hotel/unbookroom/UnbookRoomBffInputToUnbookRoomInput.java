package com.tinqinacademy.bffservice.core.converters.hotel.unbookroom;

import com.tinqinacademy.bffservice.api.operations.hotelservice.hotel.unbookroom.UnbookRoomBffInput;
import com.tinqinacademy.bffservice.core.converters.BaseConverter;
import com.tinqinacademy.bffservice.persistence.model.context.UserContext;
import com.tinqinacademy.hotel.api.operations.hotel.unbookroom.UnbookRoomInput;
import org.springframework.stereotype.Component;

@Component
public class UnbookRoomBffInputToUnbookRoomInput extends BaseConverter<UnbookRoomBffInput, UnbookRoomInput,UnbookRoomBffInputToUnbookRoomInput> {

    private final UserContext userContext;

    public UnbookRoomBffInputToUnbookRoomInput(UserContext userContext) {
        super(UnbookRoomBffInputToUnbookRoomInput.class);
        this.userContext = userContext;
    }

    @Override
    protected UnbookRoomInput convertObj(UnbookRoomBffInput bffInput) {
        UnbookRoomInput input = UnbookRoomInput.builder()
                .bookingId(bffInput.getBookingId())
                .userId(userContext.getUserId())
                .build();

        return input;
    }
}
