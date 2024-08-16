package com.tinqinacademy.bffservice.core.converters.hotel.createroom;

import com.tinqinacademy.bffservice.api.operations.hotelservice.system.createroom.CreateRoomBffOutput;
import com.tinqinacademy.bffservice.core.converters.BaseConverter;
import com.tinqinacademy.hotel.api.operations.system.createroom.CreateRoomOutput;
import org.springframework.stereotype.Component;

@Component
public class CreateRoomOuputToCreateRoomBffOutput extends BaseConverter<CreateRoomOutput, CreateRoomBffOutput,CreateRoomOuputToCreateRoomBffOutput> {
    public CreateRoomOuputToCreateRoomBffOutput() {
        super(CreateRoomOuputToCreateRoomBffOutput.class);
    }

    @Override
    protected CreateRoomBffOutput convertObj(CreateRoomOutput hotelOutput) {
        CreateRoomBffOutput bffOutput = CreateRoomBffOutput.builder()
                .roomId(hotelOutput.getRoomId())
                .build();

        return bffOutput;
    }
}
