package com.tinqinacademy.bffservice.core.operations;

import com.tinqinacademy.bffservice.api.exceptions.Errors;
import com.tinqinacademy.bffservice.api.operations.hotelservice.hotel.unbookroom.UnbookOperation;
import com.tinqinacademy.bffservice.api.operations.hotelservice.hotel.unbookroom.UnbookRoomBffInput;
import com.tinqinacademy.bffservice.api.operations.hotelservice.hotel.unbookroom.UnbookRoomBffOutput;
import com.tinqinacademy.bffservice.core.exceptions.ExceptionService;
import com.tinqinacademy.bffservice.core.utils.LoggingUtils;
import com.tinqinacademy.bffservice.persistence.model.context.UserContext;
import com.tinqinacademy.hotel.api.operations.hotel.unbookroom.UnbookRoomInput;
import com.tinqinacademy.hotel.api.operations.hotel.unbookroom.UnbookRoomOutput;
import com.tinqinacademy.hotel.restexport.HotelRestExport;
import io.vavr.control.Either;
import io.vavr.control.Try;
import jakarta.validation.Validator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UnbookOperationProcessor extends BaseOperationProcessor implements UnbookOperation {

    private final HotelRestExport hotelClient;


    public UnbookOperationProcessor(ConversionService conversionService, ExceptionService exceptionService, Validator validator, HotelRestExport hotelClient) {
        super(conversionService, exceptionService, validator);
        this.hotelClient = hotelClient;
    }

    @Override
    public Either<Errors, UnbookRoomBffOutput> process(UnbookRoomBffInput bffInput) {
        return Try.of(() -> {
                    log.info(String.format("Start %s %s input: %s", this.getClass().getSimpleName(), LoggingUtils.getMethodName(),bffInput));
                    validate(bffInput);

                    UnbookRoomInput hotelInput = conversionService.convert(bffInput,UnbookRoomInput.class);
                    UnbookRoomOutput hotelOutput = hotelClient.unbookRoom(hotelInput.getBookingId(),hotelInput);

                    UnbookRoomBffOutput bffOutput = conversionService.convert(hotelOutput,UnbookRoomBffOutput.class);
                    log.info(String.format("End %s %s output: %s", this.getClass().getSimpleName(), LoggingUtils.getMethodName(), bffOutput));
                    return bffOutput;})
                .toEither()
                .mapLeft(exceptionService::handle);
    }

}