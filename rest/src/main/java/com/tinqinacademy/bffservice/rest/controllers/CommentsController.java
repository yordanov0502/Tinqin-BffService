package com.tinqinacademy.bffservice.rest.controllers;

import com.tinqinacademy.bffservice.api.RestApiRoutes;
import com.tinqinacademy.bffservice.api.exceptions.Errors;
import com.tinqinacademy.bffservice.api.operations.commentsservice.system.addcommentforroom.AddCommentForRoomBffInput;
import com.tinqinacademy.bffservice.api.operations.commentsservice.system.addcommentforroom.AddCommentForRoomBffOutput;
import com.tinqinacademy.bffservice.api.operations.commentsservice.system.addcommentforroom.AddCommentForRoomOperation;
import com.tinqinacademy.bffservice.rest.security.UserContext;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.vavr.control.Either;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CommentsController extends BaseController{

    private final UserContext userContext;
    private final AddCommentForRoomOperation addCommentForRoomOperation;

    @Operation(summary = "Add a comment for room.",
            description = "Leaves a comment regarding a certain room.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully added comment for a room."),
            @ApiResponse(responseCode = "400", description = "Bad request."),
            @ApiResponse(responseCode = "401", description = "Unauthorized."),
            @ApiResponse(responseCode = "404", description = "Not found.")
    })
    @PostMapping(RestApiRoutes.ADD_COMMENT_FOR_ROOM)
    public ResponseEntity<?> addCommentForRoom(@PathVariable String roomId, @RequestBody AddCommentForRoomBffInput inputArg) {
        AddCommentForRoomBffInput input = inputArg.toBuilder()
                .roomId(roomId)
                .userId(userContext.getUserId())
                .build();
        Either<Errors, AddCommentForRoomBffOutput> either = addCommentForRoomOperation.process(input);
        return mapToResponseEntity(either, HttpStatus.CREATED);
    }
}
