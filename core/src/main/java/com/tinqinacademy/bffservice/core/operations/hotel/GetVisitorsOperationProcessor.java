package com.tinqinacademy.bffservice.core.operations.hotel;

import com.tinqinacademy.bffservice.api.exceptions.Errors;
import com.tinqinacademy.bffservice.api.operations.hotelservice.system.getvisitors.GetVisitorOperation;
import com.tinqinacademy.bffservice.api.operations.hotelservice.system.getvisitors.GetVisitorsBffInput;
import com.tinqinacademy.bffservice.api.operations.hotelservice.system.getvisitors.GetVisitorsBffOutput;
import com.tinqinacademy.bffservice.core.exceptions.ExceptionService;
import com.tinqinacademy.bffservice.core.operations.BaseOperationProcessor;
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
                    return bffOutput;})
                .toEither()
                .mapLeft(exceptionService::handle);
    }
}
