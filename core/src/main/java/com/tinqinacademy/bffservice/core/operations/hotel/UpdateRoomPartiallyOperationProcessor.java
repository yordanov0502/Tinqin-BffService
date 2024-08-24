package com.tinqinacademy.bffservice.core.operations.hotel;

import com.tinqinacademy.bffservice.api.exceptions.Errors;
import com.tinqinacademy.bffservice.api.operations.hotelservice.system.updateroompartially.UpdateRoomPartiallyBffInput;
import com.tinqinacademy.bffservice.api.operations.hotelservice.system.updateroompartially.UpdateRoomPartiallyBffOutput;
import com.tinqinacademy.bffservice.api.operations.hotelservice.system.updateroompartially.UpdateRoomPartiallyOperation;
import com.tinqinacademy.bffservice.core.exceptions.ExceptionService;
import com.tinqinacademy.bffservice.core.operations.BaseOperationProcessor;
import com.tinqinacademy.bffservice.core.utils.LoggingUtils;
import com.tinqinacademy.hotel.api.operations.system.updateroompartially.UpdateRoomPartiallyInput;
import com.tinqinacademy.hotel.api.operations.system.updateroompartially.UpdateRoomPartiallyOutput;
import com.tinqinacademy.hotel.restexport.HotelRestExport;
import io.vavr.control.Either;
import io.vavr.control.Try;
import jakarta.validation.Validator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UpdateRoomPartiallyOperationProcessor extends BaseOperationProcessor implements UpdateRoomPartiallyOperation {

    private final HotelRestExport hotelClient;

    public UpdateRoomPartiallyOperationProcessor(ConversionService conversionService, ExceptionService exceptionService, Validator validator, HotelRestExport hotelClient) {
        super(conversionService, exceptionService, validator);
        this.hotelClient = hotelClient;
    }

    @Override
    public Either<Errors, UpdateRoomPartiallyBffOutput> process(UpdateRoomPartiallyBffInput bffInput) {
        return Try.of(() -> {
                    validate(bffInput);

                    UpdateRoomPartiallyInput hotelInput = conversionService.convert(bffInput, UpdateRoomPartiallyInput.class);
                    UpdateRoomPartiallyOutput hotelOutput = hotelClient.updateRoomPartially(hotelInput.getRoomId(), hotelInput);

                    UpdateRoomPartiallyBffOutput bffOutput = UpdateRoomPartiallyBffOutput.builder()
                            .roomId(hotelOutput.getRoomId())
                            .build();
                    return bffOutput;
                })
                .toEither()
                .mapLeft(exceptionService::handle);
    }
}
