package com.tinqinacademy.bffservice.core.operations.hotel;

import com.tinqinacademy.bffservice.api.exceptions.Errors;
import com.tinqinacademy.bffservice.api.operations.hotelservice.hotel.getavailablerooms.AvailableRoomsIdsBffOutput;
import com.tinqinacademy.bffservice.api.operations.hotelservice.hotel.getavailablerooms.GetIdsOfAvailableRoomsBffInput;
import com.tinqinacademy.bffservice.api.operations.hotelservice.hotel.getavailablerooms.GetIdsOfAvailableRoomsOperation;
import com.tinqinacademy.bffservice.core.exceptions.ExceptionService;
import com.tinqinacademy.bffservice.core.operations.BaseOperationProcessor;
import com.tinqinacademy.hotel.api.operations.hotel.getavailablerooms.AvailableRoomsIdsOutput;
import com.tinqinacademy.hotel.restexport.HotelRestExport;
import io.vavr.control.Either;
import io.vavr.control.Try;
import jakarta.validation.Validator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class GetAvailableRoomsOperationProcessor extends BaseOperationProcessor implements GetIdsOfAvailableRoomsOperation {

    private final HotelRestExport hotelClient;

    public GetAvailableRoomsOperationProcessor(ConversionService conversionService, ExceptionService exceptionService, Validator validator, HotelRestExport hotelClient) {
        super(conversionService, exceptionService, validator);
        this.hotelClient = hotelClient;
    }

    @Override
    public Either<Errors, AvailableRoomsIdsBffOutput> process(GetIdsOfAvailableRoomsBffInput bffInput) {
        return Try.of(() -> {
                    validate(bffInput);

                    AvailableRoomsIdsOutput hotelOutput = hotelClient.getAvailableRooms(bffInput.getStartDate(),bffInput.getEndDate(), bffInput.getBedCount(), bffInput.getBedSize(), bffInput.getBathroomType());

                    AvailableRoomsIdsBffOutput bffOutput = conversionService.convert(hotelOutput, AvailableRoomsIdsBffOutput.class);
                    return bffOutput;
                })
                .toEither()
                .mapLeft(exceptionService::handle);
    }
}
