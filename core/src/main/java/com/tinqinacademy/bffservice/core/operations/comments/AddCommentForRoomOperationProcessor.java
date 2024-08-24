package com.tinqinacademy.bffservice.core.operations.comments;

import com.tinqinacademy.bffservice.api.exceptions.Errors;
import com.tinqinacademy.bffservice.api.operations.commentsservice.hotel.addcommentforroom.AddCommentForRoomBffInput;
import com.tinqinacademy.bffservice.api.operations.commentsservice.hotel.addcommentforroom.AddCommentForRoomBffOutput;
import com.tinqinacademy.bffservice.api.operations.commentsservice.hotel.addcommentforroom.AddCommentForRoomOperation;
import com.tinqinacademy.bffservice.core.exceptions.ExceptionService;
import com.tinqinacademy.bffservice.core.operations.BaseOperationProcessor;
import com.tinqinacademy.commentsservice.api.operations.hotel.addcommentforroom.AddCommentForRoomInput;
import com.tinqinacademy.commentsservice.api.operations.hotel.addcommentforroom.AddCommentForRoomOutput;
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
public class AddCommentForRoomOperationProcessor extends BaseOperationProcessor implements AddCommentForRoomOperation {

    private final HotelRestExport hotelClient;
    private final CommentsRestExport commentsClient;

    public AddCommentForRoomOperationProcessor(ConversionService conversionService, ExceptionService exceptionService, Validator validator, HotelRestExport hotelClient, CommentsRestExport commentsClient) {
        super(conversionService, exceptionService, validator);
        this.hotelClient = hotelClient;
        this.commentsClient = commentsClient;
    }

    @Override
    public Either<Errors, AddCommentForRoomBffOutput> process(AddCommentForRoomBffInput bffInput) {
        return Try.of(() -> {
                    validate(bffInput);

                    hotelClient.checkIfRoomExists(bffInput.getRoomId());

                    AddCommentForRoomInput commentInput = conversionService.convert(bffInput, AddCommentForRoomInput.class);
                    AddCommentForRoomOutput commentOutput = commentsClient.addCommentForRoom(bffInput.getRoomId(), commentInput);

                    AddCommentForRoomBffOutput bffOutput = AddCommentForRoomBffOutput.builder()
                            .id(commentOutput.getId())
                            .build();

                    return bffOutput;
                })
                .toEither()
                .mapLeft(exceptionService::handle);
    }
}