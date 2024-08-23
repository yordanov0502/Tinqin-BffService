package com.tinqinacademy.bffservice.rest.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tinqinacademy.authenticationservice.api.operations.auth.AuthOutput;
import com.tinqinacademy.authenticationservice.restexport.AuthenticationRestExport;
import com.tinqinacademy.bffservice.api.RestApiRoutes;
import com.tinqinacademy.bffservice.api.operations.hotelservice.system.createroom.CreateRoomBffInput;
import com.tinqinacademy.bffservice.api.operations.hotelservice.system.registervisitor.RegisterVisitorBffInput;
import com.tinqinacademy.bffservice.api.operations.hotelservice.system.updateroom.UpdateRoomBffInput;
import com.tinqinacademy.bffservice.api.operations.hotelservice.system.updateroompartially.UpdateRoomPartiallyBffInput;
import com.tinqinacademy.bffservice.rest.security.JwtDecoder;
import com.tinqinacademy.hotel.api.model.enums.BathroomType;
import com.tinqinacademy.hotel.api.model.enums.BedSize;
import com.tinqinacademy.hotel.api.operations.hotel.bookroom.BookRoomInput;
import com.tinqinacademy.hotel.api.operations.hotel.bookroom.BookRoomOutput;
import com.tinqinacademy.hotel.api.operations.hotel.getavailablerooms.AvailableRoomsIdsOutput;
import com.tinqinacademy.hotel.api.operations.hotel.getroom.RoomInfoOutput;
import com.tinqinacademy.hotel.api.operations.hotel.unbookroom.UnbookRoomInput;
import com.tinqinacademy.hotel.api.operations.hotel.unbookroom.UnbookRoomOutput;
import com.tinqinacademy.hotel.api.operations.system.createroom.CreateRoomInput;
import com.tinqinacademy.hotel.api.operations.system.createroom.CreateRoomOutput;
import com.tinqinacademy.hotel.api.operations.system.deleteroom.DeleteRoomOutput;
import com.tinqinacademy.hotel.api.operations.system.getvisitors.GetVisitorsOutput;
import com.tinqinacademy.hotel.api.operations.system.registervisitor.RegisterVisitorInput;
import com.tinqinacademy.hotel.api.operations.system.registervisitor.RegisterVisitorOutput;
import com.tinqinacademy.hotel.api.operations.system.updateroom.UpdateRoomInput;
import com.tinqinacademy.hotel.api.operations.system.updateroom.UpdateRoomOutput;
import com.tinqinacademy.hotel.api.operations.system.updateroompartially.UpdateRoomPartiallyInput;
import com.tinqinacademy.hotel.api.operations.system.updateroompartially.UpdateRoomPartiallyOutput;
import com.tinqinacademy.hotel.restexport.HotelRestExport;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class HotelControllerTest {
    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private HotelRestExport hotelRestExport;
    @MockBean
    private AuthenticationRestExport authenticationRestExport;
    @MockBean
    private JwtDecoder jwtDecoder;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getIdsOfAvailableRoomsOk() throws Exception {
        LocalDate startDate = LocalDate.of(2024, 10, 11);
        LocalDate endDate = LocalDate.of(2024, 10, 15);
        int bedCount = 1;
        com.tinqinacademy.bffservice.api.model.enums.BedSize bedSize = com.tinqinacademy.bffservice.api.model.enums.BedSize.SINGLE;
        com.tinqinacademy.bffservice.api.model.enums.BathroomType bathroomType = com.tinqinacademy.bffservice.api.model.enums.BathroomType.PRIVATE;
        when(hotelRestExport.getAvailableRooms(
                startDate, endDate, bedCount, bedSize.toString(), bathroomType.toString()))
                .thenReturn(new AvailableRoomsIdsOutput());

        mvc.perform(get(RestApiRoutes.GET_IDS_OF_AVAILABLE_ROOMS)
                        .param("startDate", "2024-10-11")
                        .param("endDate", "2024-10-15")
                        .param("bedCount", "1")
                        .param("bedSize", bedSize.toString())
                        .param("bathroomType", bathroomType.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void getIdsOfAvailableRoomsBadRequest() throws Exception {
        com.tinqinacademy.bffservice.api.model.enums.BedSize bedSize = com.tinqinacademy.bffservice.api.model.enums.BedSize.SINGLE;
        com.tinqinacademy.bffservice.api.model.enums.BathroomType bathroomType = com.tinqinacademy.bffservice.api.model.enums.BathroomType.PRIVATE;
        when(hotelRestExport.getAvailableRooms(
                null, null, null, bedSize.toString(), bathroomType.toString()))
                .thenReturn(new AvailableRoomsIdsOutput());

        mvc.perform(get(RestApiRoutes.GET_IDS_OF_AVAILABLE_ROOMS)
                        .param("bedCount", "1")
                        .param("bedSize", bedSize.toString())
                        .param("bathroomType", bathroomType.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getIdsOfAvailableRoomsNotFound() throws Exception {
        com.tinqinacademy.bffservice.api.model.enums.BedSize bedSize = com.tinqinacademy.bffservice.api.model.enums.BedSize.SINGLE;
        com.tinqinacademy.bffservice.api.model.enums.BathroomType bathroomType = com.tinqinacademy.bffservice.api.model.enums.BathroomType.PRIVATE;
        when(hotelRestExport.getAvailableRooms(
                null, null, null, bedSize.toString(), bathroomType.toString()))
                .thenReturn(new AvailableRoomsIdsOutput());

        mvc.perform(get(RestApiRoutes.GET_IDS_OF_AVAILABLE_ROOMS + "/wrong")
                        .param("bedCount", "1")
                        .param("bedSize", bedSize.toString())
                        .param("bathroomType", bathroomType.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void getRoomInfoOk() throws Exception {
        String roomId = UUID.randomUUID().toString();

        when(hotelRestExport.getRoomById(roomId))
                .thenReturn(new RoomInfoOutput(
                        roomId,
                        BigDecimal.valueOf(100),
                        1,
                        BedSize.KING_SIZE,
                        BathroomType.PRIVATE,
                        2,
                        new ArrayList<>()
                ));

        mvc.perform(get(RestApiRoutes.GET_INFO_FOR_ROOM, roomId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void getRoomInfoBadRequest() throws Exception {
        String roomId = UUID.randomUUID().toString();

        when(hotelRestExport.getRoomById(roomId))
                .thenReturn(new RoomInfoOutput());

        mvc.perform(get(RestApiRoutes.GET_INFO_FOR_ROOM, roomId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getRoomInfoNotFound() throws Exception {
        String roomId = UUID.randomUUID().toString();

        when(hotelRestExport.getRoomById(roomId))
                .thenReturn(new RoomInfoOutput());

        mvc.perform(get(RestApiRoutes.GET_INFO_FOR_ROOM + "/wrong", roomId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void bookRoomCreated() throws Exception {
        String jwtHeader = "Bearer 1234";
        String roomId = UUID.randomUUID().toString();
        String userId = UUID.randomUUID().toString();
        BookRoomInput bookRoomInput = BookRoomInput.builder()
                .startDate(LocalDate.of(2024, 1, 1))
                .endDate(LocalDate.of(2025, 1, 1))
                .build();

        when(authenticationRestExport.isJwtValid(jwtHeader))
                .thenReturn(AuthOutput.builder()
                        .isJwtValid(true)
                        .build());

        Map<String, Object> claims = new HashMap<>();
        claims.put("sub", userId);
        claims.put("role", "USER");

        when(jwtDecoder.decodeJwt(jwtHeader))
                .thenReturn(claims);

        when(hotelRestExport.bookRoom(roomId, bookRoomInput))
                .thenReturn(BookRoomOutput.builder().build());

        mvc.perform(post(RestApiRoutes.BOOK_ROOM, roomId)
                .header(HttpHeaders.AUTHORIZATION, jwtHeader)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(bookRoomInput))
        ).andExpect(status().isCreated());
    }

    @Test
    void bookRoomBadRequest() throws Exception {
        String jwtHeader = "Bearer 1234";
        String roomId = UUID.randomUUID().toString();
        String userId = UUID.randomUUID().toString();
        BookRoomInput bookRoomInput = BookRoomInput.builder()
                .startDate(LocalDate.of(2024, 1, 1))
                .endDate(LocalDate.of(2025, 1, 1))
                .userId(userId)
                .build();

        when(authenticationRestExport.isJwtValid(jwtHeader))
                .thenReturn(AuthOutput.builder()
                        .isJwtValid(true)
                        .build());

        Map<String, Object> claims = new HashMap<>();
        claims.put("sub", userId);
        claims.put("role", "USER");

        when(jwtDecoder.decodeJwt(jwtHeader))
                .thenReturn(claims);

        when(hotelRestExport.bookRoom(roomId, bookRoomInput))
                .thenReturn(BookRoomOutput.builder().build());

        mvc.perform(post(RestApiRoutes.BOOK_ROOM, roomId)
                .header(HttpHeaders.AUTHORIZATION, jwtHeader)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(bookRoomInput))
        ).andExpect(status().isBadRequest());
    }

    @Test
    void bookRoomNotFound() throws Exception {
        String jwtHeader = "Bearer 1234";
        String roomId = UUID.randomUUID().toString();
        String userId = UUID.randomUUID().toString();
        BookRoomInput bookRoomInput = BookRoomInput.builder()
                .startDate(LocalDate.of(2024, 1, 1))
                .endDate(LocalDate.of(2025, 1, 1))
                .build();

        when(authenticationRestExport.isJwtValid(jwtHeader))
                .thenReturn(AuthOutput.builder()
                        .isJwtValid(true)
                        .build());

        Map<String, Object> claims = new HashMap<>();
        claims.put("sub", userId);
        claims.put("role", "USER");

        when(jwtDecoder.decodeJwt(jwtHeader))
                .thenReturn(claims);

        when(hotelRestExport.bookRoom(roomId, bookRoomInput))
                .thenReturn(BookRoomOutput.builder().build());

        mvc.perform(post(RestApiRoutes.BOOK_ROOM + "/wrong", roomId)
                .header(HttpHeaders.AUTHORIZATION, jwtHeader)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(bookRoomInput))
        ).andExpect(status().isNotFound());
    }

    @Test
    void bookRoomUnauthorized() throws Exception {
        String jwtHeader = "Bearer 1234";
        String roomId = UUID.randomUUID().toString();
        String userId = UUID.randomUUID().toString();
        BookRoomInput bookRoomInput = BookRoomInput.builder()
                .startDate(LocalDate.of(2024, 1, 1))
                .endDate(LocalDate.of(2025, 1, 1))
                .build();

        when(authenticationRestExport.isJwtValid(jwtHeader))
                .thenReturn(AuthOutput.builder()
                        .isJwtValid(false)
                        .build());

        Map<String, Object> claims = new HashMap<>();
        claims.put("sub", userId);
        claims.put("role", "USER");

        when(jwtDecoder.decodeJwt(jwtHeader))
                .thenReturn(claims);

        when(hotelRestExport.bookRoom(roomId, bookRoomInput))
                .thenReturn(BookRoomOutput.builder().build());

        mvc.perform(post(RestApiRoutes.BOOK_ROOM, roomId)
                .header(HttpHeaders.AUTHORIZATION, jwtHeader)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(bookRoomInput))
        ).andExpect(status().isUnauthorized());
    }

    @Test
    void unbookRoomOk() throws Exception {
        String roomId = UUID.randomUUID().toString();
        String jwtHeader = "Bearer 1234";

        String bookingId = UUID.randomUUID().toString();
        String userId = UUID.randomUUID().toString();

        UnbookRoomInput input = UnbookRoomInput.builder()
                .userId(userId)
                .build();

        when(authenticationRestExport.isJwtValid(jwtHeader))
                .thenReturn(AuthOutput.builder()
                        .isJwtValid(true)
                        .build());

        Map<String, Object> claims = new HashMap<>();
        claims.put("sub", userId);
        claims.put("role", "USER");

        when(jwtDecoder.decodeJwt(jwtHeader))
                .thenReturn(claims);

        when(hotelRestExport.unbookRoom(bookingId, input))
                .thenReturn(UnbookRoomOutput.builder().build());

        mvc.perform(delete(RestApiRoutes.UNBOOK_ROOM, roomId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(input))
                .header(HttpHeaders.AUTHORIZATION, jwtHeader)
        ).andExpect(status().isOk());
    }

    @Test
    void unbookRoomBadRequest() throws Exception {
        String roomId = UUID.randomUUID().toString();
        String jwtHeader = "Bearer 1234";

        String bookingId = UUID.randomUUID().toString();
        String userId = UUID.randomUUID().toString();

        UnbookRoomInput input = UnbookRoomInput.builder()
                .userId(userId)
                .build();

        when(authenticationRestExport.isJwtValid(jwtHeader))
                .thenReturn(AuthOutput.builder()
                        .isJwtValid(true)
                        .build());

        Map<String, Object> claims = new HashMap<>();
        claims.put("sub", userId);
        claims.put("role", "USER");

        when(jwtDecoder.decodeJwt(jwtHeader))
                .thenReturn(claims);

        when(hotelRestExport.unbookRoom(bookingId, input))
                .thenReturn(UnbookRoomOutput.builder().build());

        mvc.perform(delete(RestApiRoutes.UNBOOK_ROOM, "noId")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(input))
                .header(HttpHeaders.AUTHORIZATION, jwtHeader)
        ).andExpect(status().isBadRequest());
    }

    @Test
    void unbookRoomNotFound() throws Exception {
        String roomId = UUID.randomUUID().toString();
        String jwtHeader = "Bearer 1234";

        String bookingId = UUID.randomUUID().toString();
        String userId = UUID.randomUUID().toString();

        UnbookRoomInput input = UnbookRoomInput.builder()
                .userId(userId)
                .build();

        when(authenticationRestExport.isJwtValid(jwtHeader))
                .thenReturn(AuthOutput.builder()
                        .isJwtValid(true)
                        .build());

        Map<String, Object> claims = new HashMap<>();
        claims.put("sub", userId);
        claims.put("role", "USER");

        when(jwtDecoder.decodeJwt(jwtHeader))
                .thenReturn(claims);

        when(hotelRestExport.unbookRoom(bookingId, input))
                .thenReturn(UnbookRoomOutput.builder().build());

        mvc.perform(delete(RestApiRoutes.UNBOOK_ROOM + "/wrong", roomId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(input))
                .header(HttpHeaders.AUTHORIZATION, jwtHeader)
        ).andExpect(status().isNotFound());
    }

    @Test
    void unbookRoomUnauthorized() throws Exception {
        String roomId = UUID.randomUUID().toString();
        String jwtHeader = "Bearer 1234";

        String bookingId = UUID.randomUUID().toString();
        String userId = UUID.randomUUID().toString();

        UnbookRoomInput input = UnbookRoomInput.builder()
                .userId(userId)
                .build();

        when(authenticationRestExport.isJwtValid(jwtHeader))
                .thenReturn(AuthOutput.builder()
                        .isJwtValid(false)
                        .build());

        Map<String, Object> claims = new HashMap<>();
        claims.put("sub", userId);
        claims.put("role", "USER");

        when(jwtDecoder.decodeJwt(jwtHeader))
                .thenReturn(claims);

        when(hotelRestExport.unbookRoom(bookingId, input))
                .thenReturn(UnbookRoomOutput.builder().build());

        mvc.perform(delete(RestApiRoutes.UNBOOK_ROOM, roomId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(input))
                .header(HttpHeaders.AUTHORIZATION, jwtHeader)
        ).andExpect(status().isUnauthorized());
    }

    @Test
    void registerVisitor() throws Exception {
        String userId = UUID.randomUUID().toString();
        String jwtHeader = "Bearer 123";
        RegisterVisitorBffInput bffRegisterVisitorInput = RegisterVisitorBffInput.builder()
                .visitorInputList(List.of())
                .build();

        when(authenticationRestExport.isJwtValid(jwtHeader))
                .thenReturn(AuthOutput.builder()
                        .isJwtValid(true)
                        .build());

        Map<String, Object> claims = new HashMap<>();
        claims.put("sub", userId);
        claims.put("role", "ADMIN");

        when(jwtDecoder.decodeJwt(jwtHeader))
                .thenReturn(claims);

        RegisterVisitorInput registerVisitorInput = RegisterVisitorInput.builder()
                .build();

        when(hotelRestExport.registerVisitors(registerVisitorInput))
                .thenReturn(RegisterVisitorOutput.builder().build());

        mvc.perform(post(RestApiRoutes.REGISTER_VISITOR)
                        .header(HttpHeaders.AUTHORIZATION, jwtHeader)
                        .content(mapper.writeValueAsString(bffRegisterVisitorInput))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    void registerVisitorBadRequest() throws Exception {
        String userId = UUID.randomUUID().toString();
        String jwtHeader = "Bearer 123";
        RegisterVisitorBffInput bffRegisterVisitorInput = null;

        when(authenticationRestExport.isJwtValid(jwtHeader))
                .thenReturn(AuthOutput.builder()
                        .isJwtValid(true)
                        .build());

        Map<String, Object> claims = new HashMap<>();
        claims.put("sub", userId);
        claims.put("role", "ADMIN");

        when(jwtDecoder.decodeJwt(jwtHeader))
                .thenReturn(claims);

        RegisterVisitorInput registerVisitorInput = RegisterVisitorInput.builder()
                .build();

        when(hotelRestExport.registerVisitors(registerVisitorInput))
                .thenReturn(RegisterVisitorOutput.builder().build());

        mvc.perform(post(RestApiRoutes.REGISTER_VISITOR)
                        .header(HttpHeaders.AUTHORIZATION, jwtHeader)
                        .content(mapper.writeValueAsString(bffRegisterVisitorInput))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void registerVisitorNotFound() throws Exception {
        String userId = UUID.randomUUID().toString();
        String jwtHeader = "Bearer 123";
        RegisterVisitorBffInput bffRegisterVisitorInput = RegisterVisitorBffInput.builder()
                .visitorInputList(List.of())
                .build();

        when(authenticationRestExport.isJwtValid(jwtHeader))
                .thenReturn(AuthOutput.builder()
                        .isJwtValid(true)
                        .build());

        Map<String, Object> claims = new HashMap<>();
        claims.put("sub", userId);
        claims.put("role", "ADMIN");

        when(jwtDecoder.decodeJwt(jwtHeader))
                .thenReturn(claims);

        RegisterVisitorInput registerVisitorInput = RegisterVisitorInput.builder()
                .build();

        when(hotelRestExport.registerVisitors(registerVisitorInput))
                .thenReturn(RegisterVisitorOutput.builder().build());

        mvc.perform(post(RestApiRoutes.REGISTER_VISITOR + "/wrong")
                        .header(HttpHeaders.AUTHORIZATION, jwtHeader)
                        .content(mapper.writeValueAsString(bffRegisterVisitorInput))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void registerVisitorUnauthorized() throws Exception {
        String userId = UUID.randomUUID().toString();
        String jwtHeader = "Bearer 123";
        RegisterVisitorBffInput bffRegisterVisitorInput = RegisterVisitorBffInput.builder()
                .visitorInputList(List.of())
                .build();

        when(authenticationRestExport.isJwtValid(jwtHeader))
                .thenReturn(AuthOutput.builder()
                        .isJwtValid(false)
                        .build());

        Map<String, Object> claims = new HashMap<>();
        claims.put("sub", userId);
        claims.put("role", "ADMIN");

        when(jwtDecoder.decodeJwt(jwtHeader))
                .thenReturn(claims);

        RegisterVisitorInput registerVisitorInput = RegisterVisitorInput.builder()
                .build();

        when(hotelRestExport.registerVisitors(registerVisitorInput))
                .thenReturn(RegisterVisitorOutput.builder().build());

        mvc.perform(post(RestApiRoutes.REGISTER_VISITOR)
                        .header(HttpHeaders.AUTHORIZATION, jwtHeader)
                        .content(mapper.writeValueAsString(bffRegisterVisitorInput))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void getVisitorsOk() throws Exception {
        String userId = UUID.randomUUID().toString();
        String jwtHeader = "Bearer 123";
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = LocalDate.now().plusDays(1);

        when(authenticationRestExport.isJwtValid(jwtHeader))
                .thenReturn(AuthOutput.builder()
                        .isJwtValid(true)
                        .build());

        Map<String, Object> claims = new HashMap<>();
        claims.put("sub", userId);
        claims.put("role", "ADMIN");

        when(jwtDecoder.decodeJwt(jwtHeader))
                .thenReturn(claims);

        when(hotelRestExport.getVisitors(startDate, endDate, null, null, null,
                null, null, null, null, null))
                .thenReturn(GetVisitorsOutput.builder().
                        visitorOutputList(List.of())
                        .build());


        mvc.perform(get(RestApiRoutes.GET_VISITORS)
                        .param("startDate", String.valueOf(startDate))
                        .param("endDate", String.valueOf(endDate))
                        .header(HttpHeaders.AUTHORIZATION, jwtHeader))
                .andExpect(status().isOk());
    }

    @Test
    void getVisitorsBadRequest() throws Exception {
        String userId = UUID.randomUUID().toString();
        String jwtHeader = "Bearer 123";
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = LocalDate.now().plusDays(1);

        when(authenticationRestExport.isJwtValid(jwtHeader))
                .thenReturn(AuthOutput.builder()
                        .isJwtValid(true)
                        .build());

        Map<String, Object> claims = new HashMap<>();
        claims.put("sub", userId);
        claims.put("role", "ADMIN");

        when(jwtDecoder.decodeJwt(jwtHeader))
                .thenReturn(claims);

        when(hotelRestExport.getVisitors(startDate, endDate, null, null, null,
                null, null, null, null, null))
                .thenReturn(GetVisitorsOutput.builder().
                        visitorOutputList(List.of())
                        .build());


        mvc.perform(get(RestApiRoutes.GET_VISITORS)
                        .param("endDate", String.valueOf(endDate))
                        .header(HttpHeaders.AUTHORIZATION, jwtHeader))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getVisitorsNotFound() throws Exception {
        String userId = UUID.randomUUID().toString();
        String jwtHeader = "Bearer 123";
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = LocalDate.now().plusDays(1);

        when(authenticationRestExport.isJwtValid(jwtHeader))
                .thenReturn(AuthOutput.builder()
                        .isJwtValid(true)
                        .build());

        Map<String, Object> claims = new HashMap<>();
        claims.put("sub", userId);
        claims.put("role", "ADMIN");

        when(jwtDecoder.decodeJwt(jwtHeader))
                .thenReturn(claims);

        when(hotelRestExport.getVisitors(startDate, endDate, null, null, null,
                null, null, null, null, null))
                .thenReturn(GetVisitorsOutput.builder().
                        visitorOutputList(List.of())
                        .build());


        mvc.perform(get(RestApiRoutes.GET_VISITORS + "/wrong")
                        .param("startDate", String.valueOf(startDate))
                        .param("endDate", String.valueOf(endDate))
                        .header(HttpHeaders.AUTHORIZATION, jwtHeader))
                .andExpect(status().isNotFound());
    }

    @Test
    void getVisitorsUnauthorized() throws Exception {
        String userId = UUID.randomUUID().toString();
        String jwtHeader = "Bearer 123";
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = LocalDate.now().plusDays(1);

        when(authenticationRestExport.isJwtValid(jwtHeader))
                .thenReturn(AuthOutput.builder()
                        .isJwtValid(false)
                        .build());

        Map<String, Object> claims = new HashMap<>();
        claims.put("sub", userId);
        claims.put("role", "ADMIN");

        when(jwtDecoder.decodeJwt(jwtHeader))
                .thenReturn(claims);

        when(hotelRestExport.getVisitors(startDate, endDate, null, null, null,
                null, null, null, null, null))
                .thenReturn(GetVisitorsOutput.builder().
                        visitorOutputList(List.of())
                        .build());


        mvc.perform(get(RestApiRoutes.GET_VISITORS)
                        .param("startDate", String.valueOf(startDate))
                        .param("endDate", String.valueOf(endDate))
                        .header(HttpHeaders.AUTHORIZATION, jwtHeader))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void createRoomCreated() throws Exception {
        String userId = UUID.randomUUID().toString();
        String jwtHeader = "Bearer 123";

        when(authenticationRestExport.isJwtValid(jwtHeader))
                .thenReturn(AuthOutput.builder()
                        .isJwtValid(true)
                        .build());

        Map<String, Object> claims = new HashMap<>();
        claims.put("sub", userId);
        claims.put("role", "ADMIN");

        when(jwtDecoder.decodeJwt(jwtHeader))
                .thenReturn(claims);

        CreateRoomInput createRoomInput = CreateRoomInput.builder()
                .bedCount(2)
                .bedSize(BedSize.SINGLE)
                .bathroomType(BathroomType.PRIVATE)
                .floor(1)
                .roomNo("1A")
                .price(BigDecimal.valueOf(100))
                .build();

        when(hotelRestExport.createRoom(createRoomInput))
                .thenReturn(CreateRoomOutput.builder()
                        .build());

        CreateRoomBffInput input = CreateRoomBffInput
                .builder()
                .bedCount(2)
                .bedSize(com.tinqinacademy.bffservice.api.model.enums.BedSize.SINGLE)
                .bathroomType(com.tinqinacademy.bffservice.api.model.enums.BathroomType.PRIVATE)
                .floor(1)
                .roomNo("1A")
                .price(BigDecimal.valueOf(100))
                .build();

        mvc.perform(post(RestApiRoutes.CREATE_ROOM)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(input))
                        .header(HttpHeaders.AUTHORIZATION, jwtHeader))
                .andExpect(status().isCreated());
    }

    @Test
    void createRoomBadRequest() throws Exception {
        String userId = UUID.randomUUID().toString();
        String jwtHeader = "Bearer 123";

        when(authenticationRestExport.isJwtValid(jwtHeader))
                .thenReturn(AuthOutput.builder()
                        .isJwtValid(true)
                        .build());

        Map<String, Object> claims = new HashMap<>();
        claims.put("sub", userId);
        claims.put("role", "ADMIN");

        when(jwtDecoder.decodeJwt(jwtHeader))
                .thenReturn(claims);

        CreateRoomInput createRoomInput = CreateRoomInput.builder()
                .bedCount(2)
                .bedSize(BedSize.SINGLE)
                .bathroomType(BathroomType.PRIVATE)
                .floor(1)
                .roomNo("1A")
                .price(BigDecimal.valueOf(100))
                .build();

        when(hotelRestExport.createRoom(createRoomInput))
                .thenReturn(CreateRoomOutput.builder()
                        .build());

        CreateRoomBffInput input = CreateRoomBffInput
                .builder()
                .bedCount(-2)
                .bedSize(com.tinqinacademy.bffservice.api.model.enums.BedSize.SINGLE)
                .bathroomType(com.tinqinacademy.bffservice.api.model.enums.BathroomType.PRIVATE)
                .floor(1)
                .roomNo("1A")
                .price(BigDecimal.valueOf(100))
                .build();

        mvc.perform(post(RestApiRoutes.CREATE_ROOM)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(input))
                        .header(HttpHeaders.AUTHORIZATION, jwtHeader))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createRoomUnauthorized() throws Exception {
        String userId = UUID.randomUUID().toString();
        String jwtHeader = "Bearer 123";

        when(authenticationRestExport.isJwtValid(jwtHeader))
                .thenReturn(AuthOutput.builder()
                        .isJwtValid(false)
                        .build());

        Map<String, Object> claims = new HashMap<>();
        claims.put("sub", userId);
        claims.put("role", "ADMIN");

        when(jwtDecoder.decodeJwt(jwtHeader))
                .thenReturn(claims);

        CreateRoomInput createRoomInput = CreateRoomInput.builder()
                .bedCount(2)
                .bedSize(BedSize.SINGLE)
                .bathroomType(BathroomType.PRIVATE)
                .floor(1)
                .roomNo("1A")
                .price(BigDecimal.valueOf(100))
                .build();

        when(hotelRestExport.createRoom(createRoomInput))
                .thenReturn(CreateRoomOutput.builder()
                        .build());

        CreateRoomBffInput input = CreateRoomBffInput
                .builder()
                .bedCount(2)
                .bedSize(com.tinqinacademy.bffservice.api.model.enums.BedSize.SINGLE)
                .bathroomType(com.tinqinacademy.bffservice.api.model.enums.BathroomType.PRIVATE)
                .floor(1)
                .roomNo("1A")
                .price(BigDecimal.valueOf(100))
                .build();

        mvc.perform(post(RestApiRoutes.CREATE_ROOM)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(input))
                        .header(HttpHeaders.AUTHORIZATION, jwtHeader))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void updateRoomOk() throws Exception {
        String userId = UUID.randomUUID().toString();
        String jwtHeader = "Bearer 123";
        String roomId = UUID.randomUUID().toString();

        when(authenticationRestExport.isJwtValid(jwtHeader))
                .thenReturn(AuthOutput.builder()
                        .isJwtValid(true)
                        .build());

        Map<String, Object> claims = new HashMap<>();
        claims.put("sub", userId);
        claims.put("role", "ADMIN");

        when(jwtDecoder.decodeJwt(jwtHeader))
                .thenReturn(claims);

//        UpdateRoomInput updateRoomInput = UpdateRoomInput
//                .builder()
//                .roomId(roomId)
//                .bedCount(2)
//                .bedSize(BedSize.DOUBLE)
//                .bathroomType(BathroomType.SHARED)
//                .floor(2)
//                .roomNo("2A")
//                .price(BigDecimal.valueOf(200))
//                .build();

        UpdateRoomBffInput input = UpdateRoomBffInput
                .builder()
                .bedCount(2)
                .bedSize(com.tinqinacademy.bffservice.api.model.enums.BedSize.DOUBLE)
                .bathroomType(com.tinqinacademy.bffservice.api.model.enums.BathroomType.SHARED)
                .floor(2)
                .roomNo("2A")
                .price(BigDecimal.valueOf(200))
                .build();

        UpdateRoomOutput mockHotelOutput = mock(UpdateRoomOutput.class);
        when(mockHotelOutput.getRoomId()).thenReturn(roomId);

        when(hotelRestExport.updateRoom(eq(roomId), any(UpdateRoomInput.class)))
                .thenReturn(mockHotelOutput);

        mvc.perform(put(RestApiRoutes.UPDATE_ROOM, roomId)
                        .header(HttpHeaders.AUTHORIZATION, jwtHeader)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(input)))
                .andExpect(status().isOk());
    }

    @Test
    void updateRoomBadRequest() throws Exception {
        String userId = UUID.randomUUID().toString();
        String jwtHeader = "Bearer 123";
        String roomId = UUID.randomUUID().toString();

        when(authenticationRestExport.isJwtValid(jwtHeader))
                .thenReturn(AuthOutput.builder()
                        .isJwtValid(true)
                        .build());

        Map<String, Object> claims = new HashMap<>();
        claims.put("sub", userId);
        claims.put("role", "ADMIN");

        when(jwtDecoder.decodeJwt(jwtHeader))
                .thenReturn(claims);

        UpdateRoomInput updateRoomInput = UpdateRoomInput
                .builder()
                .roomId(roomId)
                .bedCount(2)
                .bedSize(BedSize.DOUBLE)
                .bathroomType(BathroomType.SHARED)
                .floor(2)
                .roomNo("2A")
                .price(BigDecimal.valueOf(200))
                .build();

        UpdateRoomBffInput input = UpdateRoomBffInput
                .builder()
                .bedCount(2)
                .bedSize(com.tinqinacademy.bffservice.api.model.enums.BedSize.DOUBLE)
                .bathroomType(com.tinqinacademy.bffservice.api.model.enums.BathroomType.SHARED)
                .floor(2)
                .roomNo("2A")
                .price(BigDecimal.valueOf(200))
                .build();

        UpdateRoomOutput mockHotelOutput = mock(UpdateRoomOutput.class);
        when(mockHotelOutput.getRoomId()).thenReturn(roomId);

        when(hotelRestExport.updateRoom(roomId, updateRoomInput))
                .thenReturn(mockHotelOutput);

        mvc.perform(put(RestApiRoutes.UPDATE_ROOM, roomId)
                        .header(HttpHeaders.AUTHORIZATION, jwtHeader)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(input)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateRoomNotFound() throws Exception {
        String userId = UUID.randomUUID().toString();
        String jwtHeader = "Bearer 123";
        String roomId = UUID.randomUUID().toString();

        when(authenticationRestExport.isJwtValid(jwtHeader))
                .thenReturn(AuthOutput.builder()
                        .isJwtValid(true)
                        .build());

        Map<String, Object> claims = new HashMap<>();
        claims.put("sub", userId);
        claims.put("role", "ADMIN");

        when(jwtDecoder.decodeJwt(jwtHeader))
                .thenReturn(claims);

        UpdateRoomInput updateRoomInput = UpdateRoomInput
                .builder()
                .roomId(roomId)
                .bedCount(2)
                .bedSize(BedSize.DOUBLE)
                .bathroomType(BathroomType.SHARED)
                .floor(2)
                .roomNo("2A")
                .price(BigDecimal.valueOf(200))
                .build();

        UpdateRoomBffInput input = UpdateRoomBffInput
                .builder()
                .bedCount(2)
                .bedSize(com.tinqinacademy.bffservice.api.model.enums.BedSize.DOUBLE)
                .bathroomType(com.tinqinacademy.bffservice.api.model.enums.BathroomType.SHARED)
                .floor(2)
                .roomNo("2A")
                .price(BigDecimal.valueOf(200))
                .build();

        UpdateRoomOutput mockHotelOutput = mock(UpdateRoomOutput.class);
        when(mockHotelOutput.getRoomId()).thenReturn(roomId);

        when(hotelRestExport.updateRoom(roomId, updateRoomInput))
                .thenReturn(mockHotelOutput);

        mvc.perform(put(RestApiRoutes.UPDATE_ROOM + "/wrong", roomId)
                        .header(HttpHeaders.AUTHORIZATION, jwtHeader)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(input)))
                .andExpect(status().isNotFound());
    }

    @Test
    void updateRoomUnauthorized() throws Exception {
        String userId = UUID.randomUUID().toString();
        String jwtHeader = "Bearer 123";
        String roomId = UUID.randomUUID().toString();

        when(authenticationRestExport.isJwtValid(jwtHeader))
                .thenReturn(AuthOutput.builder()
                        .isJwtValid(false)
                        .build());

        Map<String, Object> claims = new HashMap<>();
        claims.put("sub", userId);
        claims.put("role", "ADMIN");

        when(jwtDecoder.decodeJwt(jwtHeader))
                .thenReturn(claims);

        UpdateRoomInput updateRoomInput = UpdateRoomInput
                .builder()
                .roomId(roomId)
                .bedCount(2)
                .bedSize(BedSize.DOUBLE)
                .bathroomType(BathroomType.SHARED)
                .floor(2)
                .roomNo("2A")
                .price(BigDecimal.valueOf(200))
                .build();

        UpdateRoomBffInput input = UpdateRoomBffInput
                .builder()
                .bedCount(2)
                .bedSize(com.tinqinacademy.bffservice.api.model.enums.BedSize.DOUBLE)
                .bathroomType(com.tinqinacademy.bffservice.api.model.enums.BathroomType.SHARED)
                .floor(2)
                .roomNo("2A")
                .price(BigDecimal.valueOf(200))
                .build();

        UpdateRoomOutput mockHotelOutput = mock(UpdateRoomOutput.class);
        when(mockHotelOutput.getRoomId()).thenReturn(roomId);

        when(hotelRestExport.updateRoom(roomId, updateRoomInput))
                .thenReturn(mockHotelOutput);

        mvc.perform(put(RestApiRoutes.UPDATE_ROOM, roomId)
                        .header(HttpHeaders.AUTHORIZATION, jwtHeader)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(input)))
                .andExpect(status().isUnauthorized());
    }


    @Test
    void updateRoomPartiallyOk() throws Exception {
        String userId = UUID.randomUUID().toString();
        String jwtHeader = "Bearer 123";
        String roomId = UUID.randomUUID().toString();

        when(authenticationRestExport.isJwtValid(jwtHeader))
                .thenReturn(AuthOutput.builder()
                        .isJwtValid(true)
                        .build());

        Map<String, Object> claims = new HashMap<>();
        claims.put("sub", userId);
        claims.put("role", "ADMIN");

        when(jwtDecoder.decodeJwt(jwtHeader))
                .thenReturn(claims);

//        UpdateRoomPartiallyInput partialUpdateRoomInput = UpdateRoomPartiallyInput.builder()
//                .roomNo("3A")
//                .floor(3)
//                .build();

        when(hotelRestExport.updateRoomPartially(eq(roomId), any(UpdateRoomPartiallyInput.class)))
                .thenReturn(UpdateRoomPartiallyOutput.builder().build());

        UpdateRoomPartiallyBffInput input = UpdateRoomPartiallyBffInput
                .builder()
                .roomId(roomId)
                .roomNo("3A")
                .floor(3)
                .build();

        mvc.perform(patch(RestApiRoutes.UPDATE_ROOM_PARTIALLY, roomId)
                        .contentType("application/json-patch+json")
                        .content(mapper.writeValueAsString(input))
                        .header(HttpHeaders.AUTHORIZATION, jwtHeader))
                .andExpect(status().isOk());

    }

    @Test
    void updateRoomPartiallyBadRequest() throws Exception {
        String userId = UUID.randomUUID().toString();
        String jwtHeader = "Bearer 123";
        String roomId = UUID.randomUUID().toString();

        when(authenticationRestExport.isJwtValid(jwtHeader))
                .thenReturn(AuthOutput.builder()
                        .isJwtValid(true)
                        .build());

        Map<String, Object> claims = new HashMap<>();
        claims.put("sub", userId);
        claims.put("role", "ADMIN");

        when(jwtDecoder.decodeJwt(jwtHeader))
                .thenReturn(claims);

        UpdateRoomPartiallyInput partialUpdateRoomInput = UpdateRoomPartiallyInput.builder()
                .roomNo("3A")
                .floor(3)
                .build();

        when(hotelRestExport.updateRoomPartially(roomId, partialUpdateRoomInput))
                .thenReturn(UpdateRoomPartiallyOutput.builder().build());

        UpdateRoomPartiallyBffInput input = UpdateRoomPartiallyBffInput
                .builder()
                .roomId(roomId)
                .roomNo("3A")
                .floor(3)
                .build();

        mvc.perform(patch(RestApiRoutes.UPDATE_ROOM_PARTIALLY, roomId)
                        .contentType("application/json-patch+json")
                        .content(mapper.writeValueAsString(input))
                        .header(HttpHeaders.AUTHORIZATION, jwtHeader))
                .andExpect(status().isBadRequest());

    }

    @Test
    void updateRoomPartiallyNotFound() throws Exception {
        String userId = UUID.randomUUID().toString();
        String jwtHeader = "Bearer 123";
        String roomId = UUID.randomUUID().toString();

        when(authenticationRestExport.isJwtValid(jwtHeader))
                .thenReturn(AuthOutput.builder()
                        .isJwtValid(true)
                        .build());

        Map<String, Object> claims = new HashMap<>();
        claims.put("sub", userId);
        claims.put("role", "ADMIN");

        when(jwtDecoder.decodeJwt(jwtHeader))
                .thenReturn(claims);

        UpdateRoomPartiallyInput partialUpdateRoomInput = UpdateRoomPartiallyInput.builder()
                .roomNo("3A")
                .floor(3)
                .build();

        when(hotelRestExport.updateRoomPartially(roomId, partialUpdateRoomInput))
                .thenReturn(UpdateRoomPartiallyOutput.builder().build());

        UpdateRoomPartiallyBffInput input = UpdateRoomPartiallyBffInput
                .builder()
                .roomId(roomId)
                .roomNo("3A")
                .floor(3)
                .build();

        mvc.perform(patch(RestApiRoutes.UPDATE_ROOM_PARTIALLY+"/wrong", roomId)
                        .contentType("application/json-patch+json")
                        .content(mapper.writeValueAsString(input))
                        .header(HttpHeaders.AUTHORIZATION, jwtHeader))
                .andExpect(status().isNotFound());
    }

    @Test
    void updateRoomPartiallyUnauthorized() throws Exception {
        String userId = UUID.randomUUID().toString();
        String jwtHeader = "Bearer 123";
        String roomId = UUID.randomUUID().toString();

        when(authenticationRestExport.isJwtValid(jwtHeader))
                .thenReturn(AuthOutput.builder()
                        .isJwtValid(false)
                        .build());

        Map<String, Object> claims = new HashMap<>();
        claims.put("sub", userId);
        claims.put("role", "ADMIN");

        when(jwtDecoder.decodeJwt(jwtHeader))
                .thenReturn(claims);

        UpdateRoomPartiallyInput partialUpdateRoomInput = UpdateRoomPartiallyInput.builder()
                .roomNo("3A")
                .floor(3)
                .build();

        when(hotelRestExport.updateRoomPartially(roomId, partialUpdateRoomInput))
                .thenReturn(UpdateRoomPartiallyOutput.builder().build());

        UpdateRoomPartiallyBffInput input = UpdateRoomPartiallyBffInput
                .builder()
                .roomId(roomId)
                .roomNo("3A")
                .floor(3)
                .build();

        mvc.perform(patch(RestApiRoutes.UPDATE_ROOM_PARTIALLY, roomId)
                        .contentType("application/json-patch+json")
                        .content(mapper.writeValueAsString(input))
                        .header(HttpHeaders.AUTHORIZATION, jwtHeader))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void deleteRoomOk() throws Exception {
        String userId = UUID.randomUUID().toString();
        String jwtHeader = "Bearer 123";
        String roomId = UUID.randomUUID().toString();

        when(authenticationRestExport.isJwtValid(jwtHeader))
                .thenReturn(AuthOutput.builder()
                        .isJwtValid(true)
                        .build());

        Map<String, Object> claims = new HashMap<>();
        claims.put("sub", userId);
        claims.put("role", "ADMIN");

        when(jwtDecoder.decodeJwt(jwtHeader))
                .thenReturn(claims);

        when(hotelRestExport.deleteRoom(roomId))
                .thenReturn(DeleteRoomOutput.builder().build());

        mvc.perform(delete(RestApiRoutes.DELETE_ROOM, roomId)
                        .header(HttpHeaders.AUTHORIZATION, jwtHeader))
                .andExpect(status().isOk());
    }

    @Test
    void deleteRoomBadRequest() throws Exception {
        String userId = UUID.randomUUID().toString();
        String jwtHeader = "Bearer 123";
        String roomId = UUID.randomUUID().toString();

        when(authenticationRestExport.isJwtValid(jwtHeader))
                .thenReturn(AuthOutput.builder()
                        .isJwtValid(true)
                        .build());

        Map<String, Object> claims = new HashMap<>();
        claims.put("sub", userId);
        claims.put("role", "ADMIN");

        when(jwtDecoder.decodeJwt(jwtHeader))
                .thenReturn(claims);

        when(hotelRestExport.deleteRoom(roomId))
                .thenReturn(DeleteRoomOutput.builder().build());

        mvc.perform(delete(RestApiRoutes.DELETE_ROOM, "noId")
                        .header(HttpHeaders.AUTHORIZATION, jwtHeader))
                .andExpect(status().isBadRequest());
    }

    @Test
    void deleteRoomNotFound() throws Exception {
        String userId = UUID.randomUUID().toString();
        String jwtHeader = "Bearer 123";
        String roomId = UUID.randomUUID().toString();

        when(authenticationRestExport.isJwtValid(jwtHeader))
                .thenReturn(AuthOutput.builder()
                        .isJwtValid(true)
                        .build());

        Map<String, Object> claims = new HashMap<>();
        claims.put("sub", userId);
        claims.put("role", "ADMIN");

        when(jwtDecoder.decodeJwt(jwtHeader))
                .thenReturn(claims);

        when(hotelRestExport.deleteRoom(roomId))
                .thenReturn(DeleteRoomOutput.builder().build());

        mvc.perform(delete(RestApiRoutes.DELETE_ROOM+"/wrong", roomId)
                        .header(HttpHeaders.AUTHORIZATION, jwtHeader))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteRoomUnauthorized() throws Exception {
        String userId = UUID.randomUUID().toString();
        String jwtHeader = "Bearer 123";
        String roomId = UUID.randomUUID().toString();

        when(authenticationRestExport.isJwtValid(jwtHeader))
                .thenReturn(AuthOutput.builder()
                        .isJwtValid(false)
                        .build());

        Map<String, Object> claims = new HashMap<>();
        claims.put("sub", userId);
        claims.put("role", "ADMIN");

        when(jwtDecoder.decodeJwt(jwtHeader))
                .thenReturn(claims);

        when(hotelRestExport.deleteRoom(roomId))
                .thenReturn(DeleteRoomOutput.builder().build());

        mvc.perform(delete(RestApiRoutes.DELETE_ROOM, roomId)
                        .header(HttpHeaders.AUTHORIZATION, jwtHeader))
                .andExpect(status().isUnauthorized());
    }
}