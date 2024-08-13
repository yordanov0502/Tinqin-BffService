package com.tinqinacademy.bffservice.core.converters.hotel.unbookroom.getuseridofbooking;

import com.tinqinacademy.bffservice.api.operations.hotelservice.hotel.unbookroom.getuseridofbooking.GetUserIdOfBookingBffOutput;
import com.tinqinacademy.bffservice.core.converters.BaseConverter;
import com.tinqinacademy.hotel.api.operations.hotel.unbookroom.getuseridofbooking.GetUserIdOfBookingOutput;
import org.springframework.stereotype.Component;

@Component
public class GetUserIdOfBookingOutputToBffOutput extends BaseConverter<GetUserIdOfBookingOutput, GetUserIdOfBookingBffOutput,GetUserIdOfBookingOutputToBffOutput> {
    public GetUserIdOfBookingOutputToBffOutput() {
        super(GetUserIdOfBookingOutputToBffOutput.class);
    }

    @Override
    protected GetUserIdOfBookingBffOutput convertObj(GetUserIdOfBookingOutput output) {
        GetUserIdOfBookingBffOutput bffOutput = GetUserIdOfBookingBffOutput.builder()
                .userId(output.getUserId())
                .build();

        return bffOutput;
    }
}
