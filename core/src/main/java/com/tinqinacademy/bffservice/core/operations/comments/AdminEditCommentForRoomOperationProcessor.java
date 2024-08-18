package com.tinqinacademy.bffservice.core.operations.comments;

import com.tinqinacademy.bffservice.api.exceptions.Errors;
import com.tinqinacademy.bffservice.api.operations.commentsservice.system.editcommentforroom.AdminEditCommentForRoomBffInput;
import com.tinqinacademy.bffservice.api.operations.commentsservice.system.editcommentforroom.AdminEditCommentForRoomBffOutput;
import com.tinqinacademy.bffservice.api.operations.commentsservice.system.editcommentforroom.AdminEditCommentForRoomOperation;
import com.tinqinacademy.bffservice.core.exceptions.ExceptionService;
import com.tinqinacademy.bffservice.core.operations.BaseOperationProcessor;
import com.tinqinacademy.bffservice.core.utils.LoggingUtils;
import com.tinqinacademy.commentsservice.api.operations.system.editcommentforroom.AdminEditCommentForRoomInput;
import com.tinqinacademy.commentsservice.api.operations.system.editcommentforroom.AdminEditCommentForRoomOutput;
import com.tinqinacademy.commentsservice.restexport.CommentsRestExport;
import com.tinqinacademy.hotel.api.operations.internal.getroomid.GetRoomIdOutput;
import com.tinqinacademy.hotel.restexport.HotelRestExport;
import io.vavr.control.Either;
import io.vavr.control.Try;
import jakarta.validation.Validator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AdminEditCommentForRoomOperationProcessor extends BaseOperationProcessor implements AdminEditCommentForRoomOperation {

    private final HotelRestExport hoteClient;
    private final CommentsRestExport commentsClient;

    public AdminEditCommentForRoomOperationProcessor(ConversionService conversionService, ExceptionService exceptionService, Validator validator, HotelRestExport hoteClient, CommentsRestExport commentsClient) {
        super(conversionService, exceptionService, validator);
        this.hoteClient = hoteClient;
        this.commentsClient = commentsClient;
    }

    @Override
    public Either<Errors, AdminEditCommentForRoomBffOutput> process(AdminEditCommentForRoomBffInput bffInput) {
        return Try.of(() -> {
                    log.info(String.format("Start %s %s input: %s", this.getClass().getSimpleName(), LoggingUtils.getMethodName(), bffInput));
                    validate(bffInput);

                    GetRoomIdOutput hotelOutput = hoteClient.getRoomIdByNumber(bffInput.getRoomNo());
                    String roomId = hotelOutput.getRoomId();

                    AdminEditCommentForRoomInput commentsInput = conversionService.convert(bffInput, AdminEditCommentForRoomInput.class);
                    commentsInput.setRoomId(roomId);
                    AdminEditCommentForRoomOutput commentsOutput = commentsClient.adminEditCommentForRoom(bffInput.getCommentId(), commentsInput);

                    AdminEditCommentForRoomBffOutput bffOutput = AdminEditCommentForRoomBffOutput.builder()
                            .id(commentsOutput.getId())
                            .build();

                    log.info(String.format("End %s %s output: %s", this.getClass().getSimpleName(), LoggingUtils.getMethodName(), bffOutput));
                    return bffOutput;
                })
                .toEither()
                .mapLeft(exceptionService::handle);
    }
}
