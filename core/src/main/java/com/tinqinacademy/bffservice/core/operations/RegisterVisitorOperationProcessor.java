package com.tinqinacademy.bffservice.core.operations;

import com.tinqinacademy.bffservice.api.exceptions.Errors;
import com.tinqinacademy.bffservice.api.operations.hotelservice.system.registervisitor.RegisterVisitorBffInput;
import com.tinqinacademy.bffservice.api.operations.hotelservice.system.registervisitor.RegisterVisitorBffOutput;
import com.tinqinacademy.bffservice.api.operations.hotelservice.system.registervisitor.RegisterVisitorOperation;
import com.tinqinacademy.bffservice.core.exceptions.ExceptionService;
import com.tinqinacademy.bffservice.core.utils.LoggingUtils;
import com.tinqinacademy.hotel.api.operations.system.registervisitor.RegisterVisitorInput;
import com.tinqinacademy.hotel.restexport.HotelRestExport;
import io.vavr.control.Either;
import io.vavr.control.Try;
import jakarta.validation.Validator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class RegisterVisitorOperationProcessor extends BaseOperationProcessor implements RegisterVisitorOperation {

    private final HotelRestExport hotelClient;

    public RegisterVisitorOperationProcessor(ConversionService conversionService, ExceptionService exceptionService, Validator validator, HotelRestExport hotelClient) {
        super(conversionService, exceptionService, validator);
        this.hotelClient = hotelClient;
    }

    @Override
    public Either<Errors, RegisterVisitorBffOutput> process(RegisterVisitorBffInput bffInput) {
        return Try.of(() -> {
                    log.info(String.format("Start %s %s input: %s", this.getClass().getSimpleName(), LoggingUtils.getMethodName(),bffInput));
                    validate(bffInput);

                    RegisterVisitorInput hotelInput = conversionService.convert(bffInput,RegisterVisitorInput.class);
                    hotelClient.registerVisitors(hotelInput);

                    RegisterVisitorBffOutput bffOutput = RegisterVisitorBffOutput.builder().build(); //? "no response"
                    log.info(String.format("End %s %s output: %s", this.getClass().getSimpleName(), LoggingUtils.getMethodName(), bffOutput));
                    return bffOutput;})
                .toEither()
                .mapLeft(exceptionService::handle);
    }
}
