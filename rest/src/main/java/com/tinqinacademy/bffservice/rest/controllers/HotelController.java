package com.tinqinacademy.bffservice.rest.controllers;

import com.tinqinacademy.bffservice.api.RestApiRoutes;
import com.tinqinacademy.commentsservice.restexport.CommentsRestExport;
import com.tinqinacademy.hotel.restexport.HotelRestExport;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
public class HotelController extends BaseController{

    private final HotelRestExport hotelClient;
    private final CommentsRestExport commentsClient;

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
