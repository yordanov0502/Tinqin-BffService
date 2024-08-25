package com.tinqinacademy.bffservice.rest.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tinqinacademy.authenticationservice.api.operations.auth.AuthOutput;
import com.tinqinacademy.authenticationservice.restexport.AuthenticationRestExport;
import com.tinqinacademy.bffservice.api.RestApiRoutes;
import com.tinqinacademy.bffservice.api.operations.commentsservice.system.editcommentforroom.AdminEditCommentForRoomBffInput;
import com.tinqinacademy.bffservice.rest.security.JwtDecoder;
import com.tinqinacademy.commentsservice.api.operations.hotel.addcommentforroom.AddCommentForRoomInput;
import com.tinqinacademy.commentsservice.api.operations.hotel.addcommentforroom.AddCommentForRoomOutput;
import com.tinqinacademy.commentsservice.api.operations.hotel.editcommentforroom.UserEditCommentForRoomInput;
import com.tinqinacademy.commentsservice.api.operations.hotel.editcommentforroom.UserEditCommentForRoomOutput;
import com.tinqinacademy.commentsservice.api.operations.hotel.getallcommentsofroom.GetAllCommentsOfRoomOutput;
import com.tinqinacademy.commentsservice.api.operations.system.editcommentforroom.AdminEditCommentForRoomInput;
import com.tinqinacademy.commentsservice.api.operations.system.editcommentforroom.AdminEditCommentForRoomOutput;
import com.tinqinacademy.commentsservice.restexport.CommentsRestExport;
import com.tinqinacademy.hotel.api.operations.internal.getroomid.GetRoomIdOutput;
import com.tinqinacademy.hotel.api.operations.internal.isroomexists.IsRoomExistsOutput;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class CommentsControllerTest {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper mapper;
    @MockBean
    private HotelRestExport hotelRestExport;
    @MockBean
    private CommentsRestExport commentsRestExport;
    @MockBean
    private AuthenticationRestExport authenticationRestExport;
    @MockBean
    private JwtDecoder jwtDecoder;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllCommentsOfRoomOk() throws Exception {
        String roomId = UUID.randomUUID().toString();

        when(commentsRestExport.getAllCommentsForRoom(roomId))
                .thenReturn(GetAllCommentsOfRoomOutput.builder()
                        .commentInfos(List.of())
                        .build());

        mvc.perform(get(RestApiRoutes.GET_ALL_ROOM_COMMENTS, roomId))
                .andExpect(status().isOk());
    }

    @Test
    void getAllCommentsOfRoomBadRequest() throws Exception {
        mvc.perform(get(RestApiRoutes.GET_ALL_ROOM_COMMENTS, "noId"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getAllCommentsOfRoomNotFound() throws Exception {
        mvc.perform(get(RestApiRoutes.GET_ALL_ROOM_COMMENTS + "/wrong", "noId"))
                .andExpect(status().isNotFound());
    }

    @Test
    void addCommentForRoomCreated() throws Exception {
        String userId = UUID.randomUUID().toString();
        String roomId = UUID.randomUUID().toString();
        String jwtHeader = "Bearer 1234";

        AddCommentForRoomInput input = AddCommentForRoomInput.builder()
                .roomId(roomId)
                .userId(userId)
                .content("The room was wonderful.")
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

        when(hotelRestExport.checkIfRoomExists(roomId))
                .thenReturn(IsRoomExistsOutput.builder()
                        .build());


        String commentId = UUID.randomUUID().toString();
        AddCommentForRoomOutput mockedCommentOutput = mock(AddCommentForRoomOutput.class);
        when(mockedCommentOutput.getId()).thenReturn(commentId);

        when(commentsRestExport.addCommentForRoom(eq(roomId), any(AddCommentForRoomInput.class)))
                .thenReturn(mockedCommentOutput);


        mvc.perform(post(RestApiRoutes.ADD_COMMENT_FOR_ROOM, roomId)
                        .header(HttpHeaders.AUTHORIZATION, jwtHeader)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(input)))
                .andExpect(status().isCreated());
    }

    @Test
    void addCommentForRoomBadRequest() throws Exception {
        String userId = UUID.randomUUID().toString();
        String roomId = UUID.randomUUID().toString();
        String jwtHeader = "Bearer 1234";

        AddCommentForRoomInput input = AddCommentForRoomInput.builder()
                .roomId(roomId)
                .userId(userId)
                .content("The room was wonderful.")
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

        when(hotelRestExport.checkIfRoomExists(roomId))
                .thenReturn(IsRoomExistsOutput.builder()
                        .build());


        String commentId = UUID.randomUUID().toString();
        AddCommentForRoomOutput mockedCommentOutput = mock(AddCommentForRoomOutput.class);
        when(mockedCommentOutput.getId()).thenReturn(commentId);

        when(commentsRestExport.addCommentForRoom(roomId, input))
                .thenReturn(mockedCommentOutput);


        mvc.perform(post(RestApiRoutes.ADD_COMMENT_FOR_ROOM, roomId)
                        .header(HttpHeaders.AUTHORIZATION, jwtHeader)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(input)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void addCommentForRoomNotFound() throws Exception {
        String userId = UUID.randomUUID().toString();
        String roomId = UUID.randomUUID().toString();
        String jwtHeader = "Bearer 1234";

        AddCommentForRoomInput input = AddCommentForRoomInput.builder()
                .roomId(roomId)
                .userId(userId)
                .content("The room was wonderful.")
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

        when(hotelRestExport.checkIfRoomExists(roomId))
                .thenReturn(IsRoomExistsOutput.builder()
                        .build());


        String commentId = UUID.randomUUID().toString();
        AddCommentForRoomOutput mockedCommentOutput = mock(AddCommentForRoomOutput.class);
        when(mockedCommentOutput.getId()).thenReturn(commentId);

        when(commentsRestExport.addCommentForRoom(roomId, input))
                .thenReturn(mockedCommentOutput);


        mvc.perform(post(RestApiRoutes.ADD_COMMENT_FOR_ROOM + "/wrong", roomId)
                        .header(HttpHeaders.AUTHORIZATION, jwtHeader)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(input)))
                .andExpect(status().isNotFound());
    }

    @Test
    void addCommentForRoomUnauthorized() throws Exception {
        String userId = UUID.randomUUID().toString();
        String roomId = UUID.randomUUID().toString();
        String jwtHeader = "Bearer 1234";

        AddCommentForRoomInput input = AddCommentForRoomInput.builder()
                .roomId(roomId)
                .userId(userId)
                .content("The room was wonderful.")
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

        when(hotelRestExport.checkIfRoomExists(roomId))
                .thenReturn(IsRoomExistsOutput.builder()
                        .build());


        String commentId = UUID.randomUUID().toString();
        AddCommentForRoomOutput mockedCommentOutput = mock(AddCommentForRoomOutput.class);
        when(mockedCommentOutput.getId()).thenReturn(commentId);

        when(commentsRestExport.addCommentForRoom(roomId, input))
                .thenReturn(mockedCommentOutput);


        mvc.perform(post(RestApiRoutes.ADD_COMMENT_FOR_ROOM, roomId)
                        .header(HttpHeaders.AUTHORIZATION, jwtHeader)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(input)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void userEditCommentForRoom() throws Exception {
        String userId = UUID.randomUUID().toString();
        String commentId = UUID.randomUUID().toString();
        String jwtHeader = "Bearer 1234";

        UserEditCommentForRoomInput input = UserEditCommentForRoomInput.builder()
                .content("example comment")
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

        UserEditCommentForRoomOutput mockedCommentOutput = mock(UserEditCommentForRoomOutput.class);
        when(mockedCommentOutput.getId()).thenReturn(commentId);

        when(commentsRestExport.userEditCommentForRoom(eq(commentId), any(UserEditCommentForRoomInput.class)))
                .thenReturn(mockedCommentOutput);

        mvc.perform(patch(RestApiRoutes.EDIT_COMMENT_FOR_ROOM, commentId)
                .contentType("application/json-patch+json")
                .content(mapper.writeValueAsString(input))
                .header(HttpHeaders.AUTHORIZATION, jwtHeader)
        ).andExpect(status().isOk());
    }

    @Test
    void userEditCommentForRoomBadRequest() throws Exception {
        String userId = UUID.randomUUID().toString();
        String commentId = UUID.randomUUID().toString();
        String jwtHeader = "Bearer 1234";

        UserEditCommentForRoomInput input = UserEditCommentForRoomInput.builder()
                .content("example comment")
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

        UserEditCommentForRoomOutput mockedCommentOutput = mock(UserEditCommentForRoomOutput.class);
        when(mockedCommentOutput.getId()).thenReturn(commentId);

        when(commentsRestExport.userEditCommentForRoom(commentId, input))
                .thenReturn(mockedCommentOutput);

        mvc.perform(patch(RestApiRoutes.EDIT_COMMENT_FOR_ROOM, commentId)
                .contentType("application/json-patch+json")
                .content(mapper.writeValueAsString(input))
                .header(HttpHeaders.AUTHORIZATION, jwtHeader)
        ).andExpect(status().isBadRequest());
    }

    @Test
    void userEditCommentForRoomNotFound() throws Exception {
        String userId = UUID.randomUUID().toString();
        String commentId = UUID.randomUUID().toString();
        String jwtHeader = "Bearer 1234";

        UserEditCommentForRoomInput input = UserEditCommentForRoomInput.builder()
                .content("example comment")
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

        UserEditCommentForRoomOutput mockedCommentOutput = mock(UserEditCommentForRoomOutput.class);
        when(mockedCommentOutput.getId()).thenReturn(commentId);

        when(commentsRestExport.userEditCommentForRoom(eq(commentId), any(UserEditCommentForRoomInput.class)))
                .thenReturn(mockedCommentOutput);

        mvc.perform(patch(RestApiRoutes.EDIT_COMMENT_FOR_ROOM + "/wrong", commentId)
                .contentType("application/json-patch+json")
                .content(mapper.writeValueAsString(input))
                .header(HttpHeaders.AUTHORIZATION, jwtHeader)
        ).andExpect(status().isNotFound());
    }

    @Test
    void userEditCommentForRoomUnauthorized() throws Exception {
        String userId = UUID.randomUUID().toString();
        String commentId = UUID.randomUUID().toString();
        String jwtHeader = "Bearer 1234";

        UserEditCommentForRoomInput input = UserEditCommentForRoomInput.builder()
                .content("example comment")
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

        UserEditCommentForRoomOutput mockedCommentOutput = mock(UserEditCommentForRoomOutput.class);
        when(mockedCommentOutput.getId()).thenReturn(commentId);

        when(commentsRestExport.userEditCommentForRoom(eq(commentId), any(UserEditCommentForRoomInput.class)))
                .thenReturn(mockedCommentOutput);

        mvc.perform(patch(RestApiRoutes.EDIT_COMMENT_FOR_ROOM, commentId)
                .contentType("application/json-patch+json")
                .content(mapper.writeValueAsString(input))
                .header(HttpHeaders.AUTHORIZATION, jwtHeader)
        ).andExpect(status().isUnauthorized());
    }

    @Test
    void adminEditCommentForRoomOk() throws Exception {
        String userId = UUID.randomUUID().toString();
        String commentId = UUID.randomUUID().toString();
        String roomId = UUID.randomUUID().toString();
        String roomNumber = "1A";
        String jwtHeader = "Bearer 1234";

        AdminEditCommentForRoomBffInput bffInput = AdminEditCommentForRoomBffInput.builder()
                .roomNo(roomNumber)
                .userId(userId)
                .content("The room was shiny.")
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

        when(hotelRestExport.getRoomIdByNumber(roomNumber))
                .thenReturn(GetRoomIdOutput.builder()
                        .roomId(roomId)
                        .build());

        AdminEditCommentForRoomOutput mockedCommentOutput = mock(AdminEditCommentForRoomOutput.class);
        when(mockedCommentOutput.getId()).thenReturn(commentId);

        when(commentsRestExport.adminEditCommentForRoom(eq(commentId), any(AdminEditCommentForRoomInput.class)))
                .thenReturn(mockedCommentOutput);

        mvc.perform(put(RestApiRoutes.ADMIN_EDIT_COMMENT_FOR_ROOM, commentId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(bffInput))
                        .header(HttpHeaders.AUTHORIZATION, jwtHeader))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(commentId));
    }

    @Test
    void adminEditCommentForRoomBadRequest() throws Exception {
        String userId = UUID.randomUUID().toString();
        String commentId = UUID.randomUUID().toString();
        String roomId = UUID.randomUUID().toString();
        String roomNumber = "1A";
        String jwtHeader = "Bearer 1234";

        when(authenticationRestExport.isJwtValid(jwtHeader))
                .thenReturn(AuthOutput.builder()
                        .isJwtValid(true)
                        .build());

        Map<String, Object> claims = new HashMap<>();
        claims.put("sub", userId);
        claims.put("role", "ADMIN");

        when(jwtDecoder.decodeJwt(jwtHeader))
                .thenReturn(claims);

        when(hotelRestExport.getRoomIdByNumber(roomNumber))
                .thenReturn(GetRoomIdOutput.builder()
                        .roomId(roomId)
                        .build());

        AdminEditCommentForRoomOutput mockedCommentOutput = mock(AdminEditCommentForRoomOutput.class);
        when(mockedCommentOutput.getId()).thenReturn(commentId);

        when(commentsRestExport.adminEditCommentForRoom(eq(commentId), any(AdminEditCommentForRoomInput.class)))
                .thenReturn(mockedCommentOutput);

        mvc.perform(put(RestApiRoutes.ADMIN_EDIT_COMMENT_FOR_ROOM, commentId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, jwtHeader))
                .andExpect(status().isBadRequest());
    }

    @Test
    void adminEditCommentForRoomNotFound() throws Exception {
        String userId = UUID.randomUUID().toString();
        String commentId = UUID.randomUUID().toString();
        String roomId = UUID.randomUUID().toString();
        String roomNumber = "1A";
        String jwtHeader = "Bearer 1234";

        when(authenticationRestExport.isJwtValid(jwtHeader))
                .thenReturn(AuthOutput.builder()
                        .isJwtValid(true)
                        .build());

        Map<String, Object> claims = new HashMap<>();
        claims.put("sub", userId);
        claims.put("role", "ADMIN");

        when(jwtDecoder.decodeJwt(jwtHeader))
                .thenReturn(claims);

        when(hotelRestExport.getRoomIdByNumber(roomNumber))
                .thenReturn(GetRoomIdOutput.builder()
                        .roomId(roomId)
                        .build());

        AdminEditCommentForRoomOutput mockedCommentOutput = mock(AdminEditCommentForRoomOutput.class);
        when(mockedCommentOutput.getId()).thenReturn(commentId);

        when(commentsRestExport.adminEditCommentForRoom(eq(commentId), any(AdminEditCommentForRoomInput.class)))
                .thenReturn(mockedCommentOutput);

        mvc.perform(put(RestApiRoutes.ADMIN_EDIT_COMMENT_FOR_ROOM+"/wrong", commentId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, jwtHeader))
                .andExpect(status().isNotFound());
    }

    @Test
    void adminEditCommentForRoomUnauthorized() throws Exception {
        String userId = UUID.randomUUID().toString();
        String commentId = UUID.randomUUID().toString();
        String roomId = UUID.randomUUID().toString();
        String roomNumber = "1A";
        String jwtHeader = "Bearer 1234";

        AdminEditCommentForRoomBffInput bffInput = AdminEditCommentForRoomBffInput.builder()
                .roomNo(roomNumber)
                .userId(userId)
                .content("The room was shiny.")
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

        when(hotelRestExport.getRoomIdByNumber(roomNumber))
                .thenReturn(GetRoomIdOutput.builder()
                        .roomId(roomId)
                        .build());

        AdminEditCommentForRoomOutput mockedCommentOutput = mock(AdminEditCommentForRoomOutput.class);
        when(mockedCommentOutput.getId()).thenReturn(commentId);

        when(commentsRestExport.adminEditCommentForRoom(eq(commentId), any(AdminEditCommentForRoomInput.class)))
                .thenReturn(mockedCommentOutput);

        mvc.perform(put(RestApiRoutes.ADMIN_EDIT_COMMENT_FOR_ROOM, commentId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(bffInput))
                        .header(HttpHeaders.AUTHORIZATION, jwtHeader))
                .andExpect(status().isUnauthorized());
    }

}