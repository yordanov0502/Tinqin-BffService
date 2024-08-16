package com.tinqinacademy.bffservice.core.operations.hotel;

import com.tinqinacademy.bffservice.api.exceptions.Errors;
import com.tinqinacademy.bffservice.api.operations.hotelservice.system.createroom.CreateRoomBffInput;
import com.tinqinacademy.bffservice.api.operations.hotelservice.system.createroom.CreateRoomBffOutput;
import com.tinqinacademy.bffservice.api.operations.hotelservice.system.createroom.CreateRoomOperation;
import com.tinqinacademy.bffservice.core.exceptions.ExceptionService;
import com.tinqinacademy.bffservice.core.operations.BaseOperationProcessor;
import com.tinqinacademy.bffservice.core.utils.LoggingUtils;
import com.tinqinacademy.hotel.api.operations.system.createroom.CreateRoomInput;
import com.tinqinacademy.hotel.api.operations.system.createroom.CreateRoomOutput;
import com.tinqinacademy.hotel.restexport.HotelRestExport;
import io.vavr.control.Either;
import io.vavr.control.Try;
import jakarta.validation.Validator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CreateRoomOperationProcessor extends BaseOperationProcessor implements CreateRoomOperation {

    private final HotelRestExport hotelClient;

    public CreateRoomOperationProcessor(ConversionService conversionService, ExceptionService exceptionService, Validator validator, HotelRestExport hotelClient) {
        super(conversionService, exceptionService, validator);
        this.hotelClient = hotelClient;
    }

    @Override
    public Either<Errors, CreateRoomBffOutput> process(CreateRoomBffInput bffInput) {
        return Try.of(() -> {
                    log.info(String.format("Start %s %s input: %s", this.getClass().getSimpleName(), LoggingUtils.getMethodName(), bffInput));
                    validate(bffInput);

                    CreateRoomInput hotelInput =  conversionService.convert(bffInput,CreateRoomInput.class);
                    CreateRoomOutput hotelOutput = hotelClient.createRoom(hotelInput);

                    CreateRoomBffOutput bffOutput = conversionService.convert(hotelOutput, CreateRoomBffOutput.class);
                    log.info(String.format("End %s %s output: %s", this.getClass().getSimpleName(), LoggingUtils.getMethodName(), bffOutput));
                    return bffOutput;
                })
                .toEither()
                .mapLeft(exceptionService::handle);
    }
}
