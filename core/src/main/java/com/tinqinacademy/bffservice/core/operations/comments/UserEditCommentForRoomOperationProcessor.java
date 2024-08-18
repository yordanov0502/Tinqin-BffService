package com.tinqinacademy.bffservice.core.operations.comments;

import com.tinqinacademy.bffservice.api.exceptions.Errors;
import com.tinqinacademy.bffservice.api.operations.commentsservice.hotel.editcommentforroom.UserEditCommentForRoomBffInput;
import com.tinqinacademy.bffservice.api.operations.commentsservice.hotel.editcommentforroom.UserEditCommentForRoomBffOutput;
import com.tinqinacademy.bffservice.api.operations.commentsservice.hotel.editcommentforroom.UserEditCommentForRoomOperation;
import com.tinqinacademy.bffservice.core.exceptions.ExceptionService;
import com.tinqinacademy.bffservice.core.operations.BaseOperationProcessor;
import com.tinqinacademy.bffservice.core.utils.LoggingUtils;
import com.tinqinacademy.commentsservice.api.operations.hotel.editcommentforroom.UserEditCommentForRoomInput;
import com.tinqinacademy.commentsservice.api.operations.hotel.editcommentforroom.UserEditCommentForRoomOutput;
import com.tinqinacademy.commentsservice.restexport.CommentsRestExport;
import io.vavr.control.Either;
import io.vavr.control.Try;
import jakarta.validation.Validator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserEditCommentForRoomOperationProcessor extends BaseOperationProcessor implements UserEditCommentForRoomOperation {

    private final CommentsRestExport commentsClient;


    public UserEditCommentForRoomOperationProcessor(ConversionService conversionService, ExceptionService exceptionService, Validator validator, CommentsRestExport commentsClient) {
        super(conversionService, exceptionService, validator);
        this.commentsClient = commentsClient;
    }

    @Override
    public Either<Errors, UserEditCommentForRoomBffOutput> process(UserEditCommentForRoomBffInput bffInput) {
        return Try.of(() -> {
                    log.info(String.format("Start %s %s input: %s", this.getClass().getSimpleName(), LoggingUtils.getMethodName(), bffInput));
                    validate(bffInput);


                    UserEditCommentForRoomInput commentsInput = conversionService.convert(bffInput, UserEditCommentForRoomInput.class);
                    UserEditCommentForRoomOutput commentsOutput = commentsClient.userEditCommentForRoom(bffInput.getCommentId(), commentsInput);

                    UserEditCommentForRoomBffOutput bffOutput = UserEditCommentForRoomBffOutput.builder()
                            .id(commentsOutput.getId())
                            .build();

                    log.info(String.format("End %s %s output: %s", this.getClass().getSimpleName(), LoggingUtils.getMethodName(), bffOutput));
                    return bffOutput;
                })
                .toEither()
                .mapLeft(exceptionService::handle);
    }
}
