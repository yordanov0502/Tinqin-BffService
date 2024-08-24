package com.tinqinacademy.bffservice.core.operations.comments;

import com.tinqinacademy.bffservice.api.exceptions.Errors;
import com.tinqinacademy.bffservice.api.operations.commentsservice.hotel.getallcommentsofroom.GetAllCommentsOfRoomBffInput;
import com.tinqinacademy.bffservice.api.operations.commentsservice.hotel.getallcommentsofroom.GetAllCommentsOfRoomBffOutput;
import com.tinqinacademy.bffservice.api.operations.commentsservice.hotel.getallcommentsofroom.GetAllCommentsOfRoomOperation;
import com.tinqinacademy.bffservice.core.exceptions.ExceptionService;
import com.tinqinacademy.bffservice.core.operations.BaseOperationProcessor;
import com.tinqinacademy.bffservice.core.utils.LoggingUtils;
import com.tinqinacademy.commentsservice.api.operations.hotel.getallcommentsofroom.GetAllCommentsOfRoomOutput;
import com.tinqinacademy.commentsservice.restexport.CommentsRestExport;
import com.tinqinacademy.hotel.restexport.HotelRestExport;
import io.vavr.control.Either;
import io.vavr.control.Try;
import jakarta.validation.Validator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class GetAllCommentsOfRoomOperationProcessor extends BaseOperationProcessor implements GetAllCommentsOfRoomOperation {

    private final HotelRestExport hotelClient;
    private final CommentsRestExport commentsClient;

    public GetAllCommentsOfRoomOperationProcessor(ConversionService conversionService, ExceptionService exceptionService, Validator validator, HotelRestExport hotelClient, CommentsRestExport commentsClient) {
        super(conversionService, exceptionService, validator);
        this.hotelClient = hotelClient;
        this.commentsClient = commentsClient;
    }

    @Override
    public Either<Errors, GetAllCommentsOfRoomBffOutput> process(GetAllCommentsOfRoomBffInput bffInput) {
        return Try.of(() -> {
                    validate(bffInput);

                    hotelClient.checkIfRoomExists(bffInput.getRoomId());

                    GetAllCommentsOfRoomOutput commentsOutput = commentsClient.getAllCommentsForRoom(bffInput.getRoomId());
                    GetAllCommentsOfRoomBffOutput bffOutput = conversionService.convert(commentsOutput, GetAllCommentsOfRoomBffOutput.class);

                    return bffOutput;
                })
                .toEither()
                .mapLeft(exceptionService::handle);
    }
}
