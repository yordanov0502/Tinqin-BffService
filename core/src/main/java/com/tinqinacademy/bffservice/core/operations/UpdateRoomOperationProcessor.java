package com.tinqinacademy.bffservice.core.operations;


import com.tinqinacademy.bffservice.api.exceptions.Errors;
import com.tinqinacademy.bffservice.api.operations.hotelservice.system.updateroom.UpdateRoomBffInput;
import com.tinqinacademy.bffservice.api.operations.hotelservice.system.updateroom.UpdateRoomBffOutput;
import com.tinqinacademy.bffservice.api.operations.hotelservice.system.updateroom.UpdateRoomOperation;
import com.tinqinacademy.bffservice.core.exceptions.ExceptionService;
import com.tinqinacademy.bffservice.core.utils.LoggingUtils;
import com.tinqinacademy.hotel.api.operations.system.updateroom.UpdateRoomInput;
import com.tinqinacademy.hotel.api.operations.system.updateroom.UpdateRoomOutput;
import com.tinqinacademy.hotel.restexport.HotelRestExport;
import io.vavr.control.Either;
import io.vavr.control.Try;
import jakarta.validation.Validator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UpdateRoomOperationProcessor extends BaseOperationProcessor implements UpdateRoomOperation {

    private final HotelRestExport hotelClient;

    public UpdateRoomOperationProcessor(ConversionService conversionService, ExceptionService exceptionService, Validator validator, HotelRestExport hotelClient) {
        super(conversionService, exceptionService, validator);
        this.hotelClient = hotelClient;
    }

    @Override
    public Either<Errors, UpdateRoomBffOutput> process(UpdateRoomBffInput bffInput) {
        return Try.of(() -> {
                    log.info(String.format("Start %s %s input: %s", this.getClass().getSimpleName(), LoggingUtils.getMethodName(), bffInput));
                    validate(bffInput);

                    UpdateRoomInput hotelInput = conversionService.convert(bffInput, UpdateRoomInput.class);
                    UpdateRoomOutput hotelOutput = hotelClient.updateRoom(hotelInput.getRoomId(), hotelInput);

                    UpdateRoomBffOutput bffOutput = UpdateRoomBffOutput.builder()
                            .roomId(hotelOutput.getRoomId())
                            .build();
                    log.info(String.format("End %s %s output: %s", this.getClass().getSimpleName(), LoggingUtils.getMethodName(), bffOutput));
                    return bffOutput;
                })
                .toEither()
                .mapLeft(exceptionService::handle);
    }
}
