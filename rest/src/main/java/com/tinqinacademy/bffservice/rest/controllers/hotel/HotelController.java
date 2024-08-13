package com.tinqinacademy.bffservice.rest.controllers.hotel;

import com.tinqinacademy.bffservice.api.RestApiRoutes;
import com.tinqinacademy.bffservice.api.exceptions.Errors;
import com.tinqinacademy.bffservice.api.operations.hotelservice.hotel.bookroom.BookRoomBffInput;
import com.tinqinacademy.bffservice.api.operations.hotelservice.hotel.bookroom.BookRoomOperation;
import com.tinqinacademy.bffservice.api.operations.hotelservice.hotel.bookroom.BookRoomBffOutput;
import com.tinqinacademy.bffservice.persistence.model.context.UserContext;
import com.tinqinacademy.bffservice.rest.controllers.BaseController;
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
public class HotelController extends BaseController {

    private final BookRoomOperation bookRoomOperation;
    private final UserContext userContext;

    @Operation(summary = "Book a room.",
            description = "Books the room specified.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully booked room."),
            @ApiResponse(responseCode = "400", description = "Bad request."),
            @ApiResponse(responseCode = "401", description = "Unauthorized."),
            @ApiResponse(responseCode = "404", description = "Not found.")
    })
    @PostMapping(RestApiRoutes.BOOK_ROOM)
    public ResponseEntity<?> bookRoom(@PathVariable String roomId, @RequestBody BookRoomBffInput inputArg) {
        BookRoomBffInput input = inputArg.toBuilder()
                .roomId(roomId)
                .userId(userContext.getUserId())
                .build();
        Either<Errors, BookRoomBffOutput> either = bookRoomOperation.process(input);
        return mapToResponseEntity(either,HttpStatus.CREATED);
    }

}