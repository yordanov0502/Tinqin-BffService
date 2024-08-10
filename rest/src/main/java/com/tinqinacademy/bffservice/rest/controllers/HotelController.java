package com.tinqinacademy.bffservice.rest.controllers;

import com.tinqinacademy.bffservice.api.RestApiRoutes;
import com.tinqinacademy.commentsservice.restexport.CommentsRestExport;
import com.tinqinacademy.hotel.api.exceptions.Errors;
import com.tinqinacademy.hotel.api.operations.hotel.bookroom.BookRoomInput;
import com.tinqinacademy.hotel.api.operations.hotel.bookroom.BookRoomOutput;
import com.tinqinacademy.hotel.restexport.HotelRestExport;
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
public class HotelController extends BaseController{

    private final HotelRestExport hotelClient;
    private final CommentsRestExport commentsClient;

    @Operation(summary = "Book a room.",
            description = "Books the room specified.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully booked room."),
            @ApiResponse(responseCode = "400", description = "Bad request."),
            @ApiResponse(responseCode = "404", description = "Not found.")
    })
    @PostMapping(RestApiRoutes.BOOK_ROOM)
    public ResponseEntity<?> bookRoom(@PathVariable String roomId, @RequestBody BookRoomInput inputArg) {

        BookRoomInput input = inputArg.toBuilder()
                .roomId(roomId)
                .build();

        //Either<Errors, BookRoomOutput> either = bookRoomOperation.process(input);
        return new ResponseEntity<>(hotelClient.bookRoom(roomId,input), HttpStatus.CREATED);
        //return mapToResponseEntity(either,HttpStatus.CREATED);
    }

    @Operation(summary = "Get info for room by its id.",
            description = "Returns basic info for a room with the specific id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully returned info for a room."),
            @ApiResponse(responseCode = "400", description = "Bad request."),
            @ApiResponse(responseCode = "404", description = "Not found.")
    })
    @GetMapping(RestApiRoutes.GET_INFO_FOR_ROOM)
    public ResponseEntity<?> getRoomById(@PathVariable String roomId) {
         return new ResponseEntity<>(hotelClient.getRoomById(roomId), HttpStatus.OK);
    }


    @Operation(summary = "Get list of all comments for room.",
            description = "Gets list of all commentInfos left for a certain room.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully returned all commentInfos for a room."),
            @ApiResponse(responseCode = "400", description = "Bad request."),
            @ApiResponse(responseCode = "404", description = "Not found.")
    })
    @GetMapping(RestApiRoutes.GET_ALL_ROOM_COMMENTS)
    public ResponseEntity<?> getAllCommentsOfRoom(@PathVariable String roomId) {
        return new ResponseEntity<>(commentsClient.getAllCommentsForRoom(roomId),HttpStatus.OK);
    }
}
