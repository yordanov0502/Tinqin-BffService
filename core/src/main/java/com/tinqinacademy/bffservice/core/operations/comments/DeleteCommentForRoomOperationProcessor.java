package com.tinqinacademy.bffservice.core.operations.comments;

import com.tinqinacademy.bffservice.api.exceptions.Errors;
import com.tinqinacademy.bffservice.api.operations.commentsservice.system.deletecommentforroom.DeleteCommentForRoomBffInput;
import com.tinqinacademy.bffservice.api.operations.commentsservice.system.deletecommentforroom.DeleteCommentForRoomBffOutput;
import com.tinqinacademy.bffservice.api.operations.commentsservice.system.deletecommentforroom.DeleteCommentForRoomOperation;
import com.tinqinacademy.bffservice.core.exceptions.ExceptionService;
import com.tinqinacademy.bffservice.core.operations.BaseOperationProcessor;
import com.tinqinacademy.bffservice.core.utils.LoggingUtils;
import com.tinqinacademy.commentsservice.restexport.CommentsRestExport;
import io.vavr.control.Either;
import io.vavr.control.Try;
import jakarta.validation.Validator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class DeleteCommentForRoomOperationProcessor extends BaseOperationProcessor implements DeleteCommentForRoomOperation {

    private final CommentsRestExport commentsClient;

    public DeleteCommentForRoomOperationProcessor(ConversionService conversionService, ExceptionService exceptionService, Validator validator, CommentsRestExport commentsClient) {
        super(conversionService, exceptionService, validator);
        this.commentsClient = commentsClient;
    }

    @Override
    public Either<Errors, DeleteCommentForRoomBffOutput> process(DeleteCommentForRoomBffInput bffInput) {
        return Try.of(() -> {
                    log.info(String.format("Start %s %s input: %s", this.getClass().getSimpleName(), LoggingUtils.getMethodName(), bffInput));
                    validate(bffInput);

                    commentsClient.deleteCommentForRoom(bffInput.getCommentId());

                    DeleteCommentForRoomBffOutput bffOutput = DeleteCommentForRoomBffOutput.builder().build();
                    log.info(String.format("End %s %s output: %s", this.getClass().getSimpleName(), LoggingUtils.getMethodName(), bffOutput));
                    return bffOutput;
                })
                .toEither()
                .mapLeft(exceptionService::handle);
    }
}
