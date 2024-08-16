package com.tinqinacademy.bffservice.core.operations.hotel;

import com.tinqinacademy.bffservice.api.exceptions.Errors;
import com.tinqinacademy.bffservice.api.operations.hotelservice.hotel.bookroom.BookRoomBffInput;
import com.tinqinacademy.bffservice.api.operations.hotelservice.hotel.bookroom.BookRoomOperation;
import com.tinqinacademy.bffservice.api.operations.hotelservice.hotel.bookroom.BookRoomBffOutput;
import com.tinqinacademy.bffservice.core.exceptions.ExceptionService;
import com.tinqinacademy.bffservice.core.operations.BaseOperationProcessor;
import com.tinqinacademy.bffservice.core.utils.LoggingUtils;
import com.tinqinacademy.hotel.api.operations.hotel.bookroom.BookRoomInput;
import com.tinqinacademy.hotel.api.operations.hotel.bookroom.BookRoomOutput;
import com.tinqinacademy.hotel.restexport.HotelRestExport;
import io.vavr.control.Either;
import io.vavr.control.Try;
import jakarta.validation.Validator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class BookOperationProcessor extends BaseOperationProcessor implements BookRoomOperation {

    private final HotelRestExport hotelClient;

    public BookOperationProcessor(ConversionService conversionService, ExceptionService exceptionService, Validator validator, HotelRestExport hotelClient) {
        super(conversionService, exceptionService, validator);
        this.hotelClient = hotelClient;
    }

    @Override
    public Either<Errors, BookRoomBffOutput> process(BookRoomBffInput bffInput) {
        return Try.of(() -> {
                    log.info(String.format("Start %s %s input: %s", this.getClass().getSimpleName(), LoggingUtils.getMethodName(),bffInput));
                    validate(bffInput);

                    BookRoomInput hotelInput = conversionService.convert(bffInput,BookRoomInput.class);
                    BookRoomOutput hotelOutput = hotelClient.bookRoom(bffInput.getRoomId(),hotelInput);

                    BookRoomBffOutput bffOutput = conversionService.convert(hotelOutput,BookRoomBffOutput.class);
                    log.info(String.format("End %s %s output: %s", this.getClass().getSimpleName(), LoggingUtils.getMethodName(), bffOutput));
                    return bffOutput;})
                .toEither()
                .mapLeft(exceptionService::handle);
    }
}