package com.tinqinacademy.bffservice.core.converters.hotel.updateroompartially;

import com.tinqinacademy.bffservice.api.operations.hotelservice.system.updateroompartially.UpdateRoomPartiallyBffInput;
import com.tinqinacademy.bffservice.core.converters.BaseConverter;
import com.tinqinacademy.hotel.api.model.enums.BathroomType;
import com.tinqinacademy.hotel.api.model.enums.BedSize;
import com.tinqinacademy.hotel.api.operations.system.updateroompartially.UpdateRoomPartiallyInput;
import org.springframework.stereotype.Component;

@Component
public class UpdateRoomPartiallyBffInputToHotelInput extends BaseConverter<UpdateRoomPartiallyBffInput, UpdateRoomPartiallyInput, UpdateRoomPartiallyBffInputToHotelInput> {

    public UpdateRoomPartiallyBffInputToHotelInput() {
        super(UpdateRoomPartiallyBffInputToHotelInput.class);
    }

    @Override
    protected UpdateRoomPartiallyInput convertObj(UpdateRoomPartiallyBffInput bffInput) {
        UpdateRoomPartiallyInput hotelInput = UpdateRoomPartiallyInput.builder()
                .roomId(bffInput.getRoomId())
                .bedCount(bffInput.getBedCount())
                .bedSize(bffInput.getBedSize() != null ? BedSize.getByCode(bffInput.getBedSize().toString()) : null)
                .bathroomType(bffInput.getBathroomType() != null ? BathroomType.getByCode(bffInput.getBathroomType().toString()) : null)
                .floor(bffInput.getFloor())
                .roomNo(bffInput.getRoomNo())
                .price(bffInput.getPrice())
                .build();

        return hotelInput;
    }
}