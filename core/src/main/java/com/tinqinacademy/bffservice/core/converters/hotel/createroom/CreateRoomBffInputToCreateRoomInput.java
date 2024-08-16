package com.tinqinacademy.bffservice.core.converters.hotel.createroom;

import com.tinqinacademy.bffservice.api.operations.hotelservice.system.createroom.CreateRoomBffInput;
import com.tinqinacademy.bffservice.core.converters.BaseConverter;
import com.tinqinacademy.hotel.api.model.enums.BathroomType;
import com.tinqinacademy.hotel.api.model.enums.BedSize;
import com.tinqinacademy.hotel.api.operations.system.createroom.CreateRoomInput;
import org.springframework.stereotype.Component;

@Component
public class CreateRoomBffInputToCreateRoomInput extends BaseConverter<CreateRoomBffInput, CreateRoomInput,CreateRoomBffInputToCreateRoomInput> {
    public CreateRoomBffInputToCreateRoomInput() {
        super(CreateRoomBffInputToCreateRoomInput.class);
    }

    @Override
    protected CreateRoomInput convertObj(CreateRoomBffInput bffInput) {
        CreateRoomInput hotelInput = CreateRoomInput.builder()
                .bedCount(bffInput.getBedCount())
                .bedSize(BedSize.getByCode(bffInput.getBedSize().toString()))
                .bathroomType(BathroomType.getByCode(bffInput.getBathroomType().toString()))
                .floor(bffInput.getFloor())
                .roomNo(bffInput.getRoomNo())
                .price(bffInput.getPrice())
                .build();

        return hotelInput;
    }
}
