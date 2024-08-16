package com.tinqinacademy.bffservice.core.operations;

import com.tinqinacademy.bffservice.api.exceptions.Errors;
import com.tinqinacademy.bffservice.api.operations.hotelservice.system.getvisitors.GetVisitorOperation;
import com.tinqinacademy.bffservice.api.operations.hotelservice.system.getvisitors.GetVisitorsBffInput;
import com.tinqinacademy.bffservice.api.operations.hotelservice.system.getvisitors.GetVisitorsBffOutput;
import com.tinqinacademy.bffservice.core.exceptions.ExceptionService;
import com.tinqinacademy.bffservice.core.utils.LoggingUtils;
import com.tinqinacademy.hotel.api.operations.system.getvisitors.GetVisitorsOutput;
import com.tinqinacademy.hotel.restexport.HotelRestExport;
import io.vavr.control.Either;
import io.vavr.control.Try;
import jakarta.validation.Validator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class GetVisitorsOperationProcessor extends BaseOperationProcessor implements GetVisitorOperation {

    private final HotelRestExport hotelClient;

    public GetVisitorsOperationProcessor(ConversionService conversionService, ExceptionService exceptionService, Validator validator, HotelRestExport hotelClient) {
        super(conversionService, exceptionService, validator);
        this.hotelClient = hotelClient;
    }

    @Override
    public Either<Errors, GetVisitorsBffOutput> process(GetVisitorsBffInput bffInput) {
        return Try.of(() -> {
                    log.info(String.format("Start %s %s input: %s", this.getClass().getSimpleName(), LoggingUtils.getMethodName(),bffInput));
                    validate(bffInput);

                    GetVisitorsOutput hotelOutput = hotelClient.getVisitors(
                            bffInput.getStartDate(),
                            bffInput.getEndDate(),
                            bffInput.getFirstName(),
                            bffInput.getLastName(),
                            bffInput.getPhoneNumber(),
                            bffInput.getIdCardNumber(),
                            bffInput.getIdCardValidity(),
                            bffInput.getIdCardIssueAuthority(),
                            bffInput.getIdCardIssueDate(),
                            bffInput.getRoomNumber());

                    GetVisitorsBffOutput bffOutput = conversionService.convert(hotelOutput,GetVisitorsBffOutput.class);
                    log.info(String.format("End %s %s output: %s", this.getClass().getSimpleName(), LoggingUtils.getMethodName(), bffOutput));
                    return bffOutput;})
                .toEither()
                .mapLeft(exceptionService::handle);
    }
}
