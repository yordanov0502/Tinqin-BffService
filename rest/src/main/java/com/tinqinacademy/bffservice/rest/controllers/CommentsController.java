package com.tinqinacademy.bffservice.rest.controllers;

import com.tinqinacademy.bffservice.api.RestApiRoutes;
import com.tinqinacademy.bffservice.api.exceptions.Errors;
import com.tinqinacademy.bffservice.api.operations.commentsservice.hotel.addcommentforroom.AddCommentForRoomBffInput;
import com.tinqinacademy.bffservice.api.operations.commentsservice.hotel.addcommentforroom.AddCommentForRoomBffOutput;
import com.tinqinacademy.bffservice.api.operations.commentsservice.hotel.addcommentforroom.AddCommentForRoomOperation;
import com.tinqinacademy.bffservice.api.operations.commentsservice.hotel.editcommentforroom.UserEditCommentForRoomBffInput;
import com.tinqinacademy.bffservice.api.operations.commentsservice.hotel.editcommentforroom.UserEditCommentForRoomBffOutput;
import com.tinqinacademy.bffservice.api.operations.commentsservice.hotel.editcommentforroom.UserEditCommentForRoomOperation;
import com.tinqinacademy.bffservice.api.operations.commentsservice.hotel.getallcommentsofroom.GetAllCommentsOfRoomBffInput;
import com.tinqinacademy.bffservice.api.operations.commentsservice.hotel.getallcommentsofroom.GetAllCommentsOfRoomBffOutput;
import com.tinqinacademy.bffservice.api.operations.commentsservice.hotel.getallcommentsofroom.GetAllCommentsOfRoomOperation;
import com.tinqinacademy.bffservice.api.operations.commentsservice.system.deletecommentforroom.DeleteCommentForRoomBffInput;
import com.tinqinacademy.bffservice.api.operations.commentsservice.system.deletecommentforroom.DeleteCommentForRoomBffOutput;
import com.tinqinacademy.bffservice.api.operations.commentsservice.system.deletecommentforroom.DeleteCommentForRoomOperation;
import com.tinqinacademy.bffservice.rest.security.UserContext;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.vavr.control.Either;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class CommentsController extends BaseController{

    private final UserContext userContext;
    private final GetAllCommentsOfRoomOperation getAllCommentsOfRoomOperation;
    private final AddCommentForRoomOperation addCommentForRoomOperation;
    private final UserEditCommentForRoomOperation userEditCommentForRoomOperation;
    private final DeleteCommentForRoomOperation deleteCommentForRoomOperation;

    @Operation(summary = "Get list of all comments for room.",
            description = "Gets list of all commentInfos left for a certain room.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully returned all commentInfos for a room."),
            @ApiResponse(responseCode = "400", description = "Bad request."),
            @ApiResponse(responseCode = "404", description = "Not found.")
    })
    @GetMapping(RestApiRoutes.GET_ALL_ROOM_COMMENTS)
    public ResponseEntity<?> getAllCommentsOfRoom(@PathVariable String roomId) {
        GetAllCommentsOfRoomBffInput input = GetAllCommentsOfRoomBffInput.builder()
                .roomId(roomId)
                .build();
        Either<Errors, GetAllCommentsOfRoomBffOutput> either = getAllCommentsOfRoomOperation.process(input);
        return mapToResponseEntity(either,HttpStatus.OK);
    }

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

    @Operation(summary = "Edit a comment for room. (user)",
            description = "User can edit own comment left for a certain room. Last edited date is updated. Info regarding user edited is updated.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully edited comment for a room."),
            @ApiResponse(responseCode = "400", description = "Bad request."),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "404", description = "Not found.")
    })
    @PatchMapping(value = RestApiRoutes.EDIT_COMMENT_FOR_ROOM, consumes = "application/json-patch+json")
    public ResponseEntity<?> editCommentForRoom(@PathVariable String commentId, @RequestBody UserEditCommentForRoomBffInput inputArg) {
        UserEditCommentForRoomBffInput input = inputArg.toBuilder()
                .commentId(commentId)
                .userId(userContext.getUserId())
                .build();
        Either<Errors, UserEditCommentForRoomBffOutput> either = userEditCommentForRoomOperation.process(input);
        return mapToResponseEntity(either,HttpStatus.OK);
    }

    @Operation(summary = "Delete a comment for room. (admin)",
            description = "Admin can delete any comment left for a certain room.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully deleted comment for a room."),
            @ApiResponse(responseCode = "400", description = "Bad request."),
            @ApiResponse(responseCode = "401", description = "Unauthorized."),
            @ApiResponse(responseCode = "403", description = "Forbidden."),
            @ApiResponse(responseCode = "404", description = "Not found.")
    })
    @DeleteMapping(RestApiRoutes.ADMIN_DELETE_COMMENT_FOR_ROOM)
    public ResponseEntity<?> deleteCommentForRoom(@PathVariable String commentId) {
        DeleteCommentForRoomBffInput input = DeleteCommentForRoomBffInput.builder()
                .commentId(commentId)
                .build();
        Either<Errors, DeleteCommentForRoomBffOutput> either = deleteCommentForRoomOperation.process(input);
        return mapToResponseEntity(either,HttpStatus.OK);
    }
}
