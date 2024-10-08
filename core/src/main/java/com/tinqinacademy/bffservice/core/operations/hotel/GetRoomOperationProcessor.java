package com.tinqinacademy.bffservice.core.operations.hotel;

import com.tinqinacademy.bffservice.api.exceptions.Errors;
import com.tinqinacademy.bffservice.api.operations.hotelservice.hotel.getroom.GetRoomOperation;
import com.tinqinacademy.bffservice.api.operations.hotelservice.hotel.getroom.RoomInfoBffInput;
import com.tinqinacademy.bffservice.api.operations.hotelservice.hotel.getroom.RoomInfoBffOutput;
import com.tinqinacademy.bffservice.core.exceptions.ExceptionService;
import com.tinqinacademy.bffservice.core.operations.BaseOperationProcessor;
import com.tinqinacademy.hotel.api.operations.hotel.getroom.RoomInfoOutput;
import com.tinqinacademy.hotel.restexport.HotelRestExport;
import io.vavr.control.Either;
import io.vavr.control.Try;
import jakarta.validation.Validator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class GetRoomOperationProcessor extends BaseOperationProcessor implements GetRoomOperation {

    private final HotelRestExport hotelClient;

    public GetRoomOperationProcessor(ConversionService conversionService, ExceptionService exceptionService, Validator validator, HotelRestExport hotelClient) {
        super(conversionService, exceptionService, validator);
        this.hotelClient = hotelClient;
    }

    @Override
    public Either<Errors, RoomInfoBffOutput> process(RoomInfoBffInput bffInput) {
        return Try.of(() -> {
                    validate(bffInput);

                    RoomInfoOutput hotelOutput = hotelClient.getRoomById(bffInput.getRoomId());

                    RoomInfoBffOutput bffOutput = conversionService.convert(hotelOutput, RoomInfoBffOutput.class);
                    return bffOutput;
                })
                .toEither()
                .mapLeft(exceptionService::handle);
    }
}
