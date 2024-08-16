package com.tinqinacademy.bffservice.rest.controllers;

import com.tinqinacademy.bffservice.api.RestApiRoutes;
import com.tinqinacademy.bffservice.api.exceptions.Errors;
import com.tinqinacademy.bffservice.api.operations.hotelservice.hotel.bookroom.BookRoomBffInput;
import com.tinqinacademy.bffservice.api.operations.hotelservice.hotel.bookroom.BookRoomOperation;
import com.tinqinacademy.bffservice.api.operations.hotelservice.hotel.bookroom.BookRoomBffOutput;
import com.tinqinacademy.bffservice.api.operations.hotelservice.hotel.getavailablerooms.AvailableRoomsIdsBffOutput;
import com.tinqinacademy.bffservice.api.operations.hotelservice.hotel.getavailablerooms.GetIdsOfAvailableRoomsBffInput;
import com.tinqinacademy.bffservice.api.operations.hotelservice.hotel.getavailablerooms.GetIdsOfAvailableRoomsOperation;
import com.tinqinacademy.bffservice.api.operations.hotelservice.hotel.getroom.GetRoomOperation;
import com.tinqinacademy.bffservice.api.operations.hotelservice.hotel.getroom.RoomInfoBffInput;
import com.tinqinacademy.bffservice.api.operations.hotelservice.hotel.getroom.RoomInfoBffOutput;
import com.tinqinacademy.bffservice.api.operations.hotelservice.hotel.unbookroom.UnbookOperation;
import com.tinqinacademy.bffservice.api.operations.hotelservice.hotel.unbookroom.UnbookRoomBffInput;
import com.tinqinacademy.bffservice.api.operations.hotelservice.hotel.unbookroom.UnbookRoomBffOutput;
import com.tinqinacademy.bffservice.api.operations.hotelservice.system.createroom.CreateRoomBffInput;
import com.tinqinacademy.bffservice.api.operations.hotelservice.system.createroom.CreateRoomBffOutput;
import com.tinqinacademy.bffservice.api.operations.hotelservice.system.createroom.CreateRoomOperation;
import com.tinqinacademy.bffservice.api.operations.hotelservice.system.getvisitors.GetVisitorOperation;
import com.tinqinacademy.bffservice.api.operations.hotelservice.system.getvisitors.GetVisitorsBffInput;
import com.tinqinacademy.bffservice.api.operations.hotelservice.system.getvisitors.GetVisitorsBffOutput;
import com.tinqinacademy.bffservice.api.operations.hotelservice.system.registervisitor.RegisterVisitorBffInput;
import com.tinqinacademy.bffservice.api.operations.hotelservice.system.registervisitor.RegisterVisitorBffOutput;
import com.tinqinacademy.bffservice.api.operations.hotelservice.system.registervisitor.RegisterVisitorOperation;
import com.tinqinacademy.bffservice.api.operations.hotelservice.system.updateroom.UpdateRoomBffInput;
import com.tinqinacademy.bffservice.api.operations.hotelservice.system.updateroom.UpdateRoomBffOutput;
import com.tinqinacademy.bffservice.api.operations.hotelservice.system.updateroom.UpdateRoomOperation;
import com.tinqinacademy.bffservice.rest.security.UserContext;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.vavr.control.Either;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
public class HotelController extends BaseController {

    private final UserContext userContext;
    private final GetIdsOfAvailableRoomsOperation getAvailableRoomsOperation;
    private final GetRoomOperation getRoomOperation;
    private final BookRoomOperation bookRoomOperation;
    private final UnbookOperation unbookOperation;
    private final RegisterVisitorOperation registerVisitorOperation;
    private final GetVisitorOperation getVisitorOperation;
    private final CreateRoomOperation createRoomOperation;
    private final UpdateRoomOperation updateRoomOperation;

    @Operation(summary = "Get ids of available rooms.",
            description = "Checks whether a room is available for a certain period. Bed requirements should come as query parameters in URL.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully returned list with ids of available rooms."),
            @ApiResponse(responseCode = "400", description = "Bad request."),
            @ApiResponse(responseCode = "404", description = "Not found.")
    })
    @GetMapping(RestApiRoutes.GET_IDS_OF_AVAILABLE_ROOMS)
    public ResponseEntity<?> getIdsOfAvailableRooms(
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate,
            @RequestParam(required = false) Integer bedCount,
            @RequestParam(required = false) String bedSize,
            @RequestParam(required = false) String bathroomType) {

        GetIdsOfAvailableRoomsBffInput input = GetIdsOfAvailableRoomsBffInput.builder()
                .startDate(startDate)
                .endDate(endDate)
                .bedCount(bedCount)
                .bedSize(bedSize)
                .bathroomType(bathroomType)
                .build();

        Either<Errors, AvailableRoomsIdsBffOutput> either = getAvailableRoomsOperation.process(input);

        return mapToResponseEntity(either,HttpStatus.OK);
    }

    @Operation(summary = "Get room information.",
            description = "Returns basic info for a room with the specific id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully returned info for a room."),
            @ApiResponse(responseCode = "400", description = "Bad request."),
            @ApiResponse(responseCode = "404", description = "Not found.")
    })
    @GetMapping(RestApiRoutes.GET_INFO_FOR_ROOM)
    public ResponseEntity<?> getRoomInfo(@PathVariable String roomId) {
        RoomInfoBffInput input = RoomInfoBffInput.builder()
                .roomId(roomId)
                .build();
        Either<Errors, RoomInfoBffOutput> either = getRoomOperation.process(input);
        return mapToResponseEntity(either,HttpStatus.OK);
    }

    @Operation(summary = "Book room.",
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
                .userContextId(userContext.getUserId())
                .build();
        Either<Errors, BookRoomBffOutput> either = bookRoomOperation.process(input);
        return mapToResponseEntity(either,HttpStatus.CREATED);
    }

    @Operation(summary = "Unbook room.",
            description = "Unbooks a room that the user has already booked.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully unbooked room."),
            @ApiResponse(responseCode = "400", description = "Bad request."),
            @ApiResponse(responseCode = "401", description = "Unauthorized."),
            @ApiResponse(responseCode = "404", description = "Not found.")
    })
    @DeleteMapping(RestApiRoutes.UNBOOK_ROOM)
    public ResponseEntity<?> unbookRoom(@PathVariable String bookingId) {
        UnbookRoomBffInput input = UnbookRoomBffInput.builder()
                .bookingId(bookingId)
                .userContextId(userContext.getUserId())
                .build();
        Either<Errors, UnbookRoomBffOutput> either = unbookOperation.process(input);
        return mapToResponseEntity(either,HttpStatus.OK);
    }

    @Operation(summary = "Register visitors.",
            description = "Register visitors as room renters.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully registered visitor."),
            @ApiResponse(responseCode = "400", description = "Bad request."),
            @ApiResponse(responseCode = "401", description = "Unauthorized."),
            @ApiResponse(responseCode = "403", description = "Forbidden."),
            @ApiResponse(responseCode = "404", description = "Not found.")
    })
    @PostMapping(RestApiRoutes.REGISTER_VISITOR)
    public ResponseEntity<?> registerVisitor(@RequestBody RegisterVisitorBffInput input) {
        Either<Errors, RegisterVisitorBffOutput> either = registerVisitorOperation.process(input);
        return mapToResponseEntity(either, HttpStatus.CREATED);
    }

    @Operation(summary = "Get visitors.",
            description = "Admin only. Provides a report based on various criteria. Provides info when room was occupied and by whom. Can report when when a user has occupied rooms.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully got info for visitors."),
            @ApiResponse(responseCode = "400", description = "Bad request."),
            @ApiResponse(responseCode = "401", description = "Unauthorized."),
            @ApiResponse(responseCode = "403", description = "Forbidden."),
            @ApiResponse(responseCode = "404", description = "Not found.")
    })
    @GetMapping(RestApiRoutes.GET_VISITORS)
    public ResponseEntity<?> getVisitors(
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate,
            @RequestParam(required = false) String firstName,
            @RequestParam(required = false) String lastName,
            @RequestParam(required = false) String phoneNumber,
            @RequestParam(required = false) String idCardNumber,
            @RequestParam(required = false) LocalDate idCardValidity,
            @RequestParam(required = false) String idCardIssueAuthority,
            @RequestParam(required = false) LocalDate idCardIssueDate,
            @RequestParam(required = false) String roomNumber
    ) {

        GetVisitorsBffInput input = GetVisitorsBffInput.builder()
                .startDate(startDate)
                .endDate(endDate)
                .firstName(firstName)
                .lastName(lastName)
                .phoneNumber(phoneNumber)
                .idCardNumber(idCardNumber)
                .idCardValidity(idCardValidity)
                .idCardIssueAuthority(idCardIssueAuthority)
                .idCardIssueDate(idCardIssueDate)
                .roomNumber(roomNumber)
                .build();

        Either<Errors, GetVisitorsBffOutput> either = getVisitorOperation.process(input);

        return mapToResponseEntity(either, HttpStatus.OK);
    }

    @Operation(summary = "Create room.",
            description = "Admin creates a new room with the specified parameters.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created room."),
            @ApiResponse(responseCode = "400", description = "Bad request."),
            @ApiResponse(responseCode = "401", description = "Unauthorized."),
            @ApiResponse(responseCode = "403", description = "Forbidden."),
            @ApiResponse(responseCode = "404", description = "Not found."),
    })
    @PostMapping(RestApiRoutes.CREATE_ROOM)
    public ResponseEntity<?> createRoom(@RequestBody CreateRoomBffInput input) {
        Either<Errors, CreateRoomBffOutput> either = createRoomOperation.process(input);
        return mapToResponseEntity(either, HttpStatus.CREATED);
    }

    @Operation(summary = "Update room.",
            description = "Admin updates the info regarding a certain room.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated info for room."),
            @ApiResponse(responseCode = "400", description = "Bad request."),
            @ApiResponse(responseCode = "401", description = "Unauthorized."),
            @ApiResponse(responseCode = "403", description = "Forbidden."),
            @ApiResponse(responseCode = "404", description = "Not found.")
    })
    @PutMapping(RestApiRoutes.UPDATE_ROOM)
    public ResponseEntity<?> updateRoom(@PathVariable String roomId, @RequestBody UpdateRoomBffInput inputArg) {
        UpdateRoomBffInput input = inputArg.toBuilder()
                .roomId(roomId)
                .build();
        Either<Errors, UpdateRoomBffOutput> either = updateRoomOperation.process(input);
        return mapToResponseEntity(either,HttpStatus.OK);
    }
}