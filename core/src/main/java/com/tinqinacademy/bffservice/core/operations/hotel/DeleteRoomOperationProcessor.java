package com.tinqinacademy.bffservice.core.operations.hotel;

import com.tinqinacademy.bffservice.api.exceptions.Errors;
import com.tinqinacademy.bffservice.api.operations.hotelservice.system.deleteroom.DeleteRoomBffInput;
import com.tinqinacademy.bffservice.api.operations.hotelservice.system.deleteroom.DeleteRoomBffOutput;
import com.tinqinacademy.bffservice.api.operations.hotelservice.system.deleteroom.DeleteRoomOperation;
import com.tinqinacademy.bffservice.core.exceptions.ExceptionService;
import com.tinqinacademy.bffservice.core.operations.BaseOperationProcessor;
import com.tinqinacademy.hotel.restexport.HotelRestExport;
import io.vavr.control.Either;
import io.vavr.control.Try;
import jakarta.validation.Validator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class DeleteRoomOperationProcessor extends BaseOperationProcessor implements DeleteRoomOperation {

    private final HotelRestExport hotelClient;

    public DeleteRoomOperationProcessor(ConversionService conversionService, ExceptionService exceptionService, Validator validator, HotelRestExport hotelClient) {
        super(conversionService, exceptionService, validator);
        this.hotelClient = hotelClient;
    }

    @Override
    public Either<Errors, DeleteRoomBffOutput> process(DeleteRoomBffInput bffInput) {
        return Try.of(() -> {
                    validate(bffInput);

                    hotelClient.deleteRoom(bffInput.getRoomId());

                    DeleteRoomBffOutput bffOutput = DeleteRoomBffOutput.builder().build();
                    return bffOutput;
                })
                .toEither()
                .mapLeft(exceptionService::handle);
    }
}
