package com.tinqinacademy.bffservice.core.converters.hotel.updateroom;

import com.tinqinacademy.bffservice.api.operations.hotelservice.system.updateroom.UpdateRoomBffInput;
import com.tinqinacademy.bffservice.core.converters.BaseConverter;
import com.tinqinacademy.hotel.api.model.enums.BathroomType;
import com.tinqinacademy.hotel.api.model.enums.BedSize;
import com.tinqinacademy.hotel.api.operations.system.updateroom.UpdateRoomInput;
import org.springframework.stereotype.Component;

@Component
public class UpdateRoomBffInputToUpdateRoomInput extends BaseConverter<UpdateRoomBffInput, UpdateRoomInput,UpdateRoomBffInputToUpdateRoomInput> {
    public UpdateRoomBffInputToUpdateRoomInput() {
        super(UpdateRoomBffInputToUpdateRoomInput.class);
    }

    @Override
    protected UpdateRoomInput convertObj(UpdateRoomBffInput bffInput) {
       UpdateRoomInput input = UpdateRoomInput.builder()
               .roomId(bffInput.getRoomId())
               .bedCount(bffInput.getBedCount())
               .bedSize(BedSize.getByCode(bffInput.getBedSize().toString()))
               .bathroomType(BathroomType.getByCode(bffInput.getBathroomType().toString()))
               .floor(bffInput.getFloor())
               .roomNo(bffInput.getRoomNo())
               .price(bffInput.getPrice())
               .build();

       return input;
    }
}
