package com.tinqinacademy.bffservice.api.operations.commentsservice.hotel.getallcommentsofroom;

import com.tinqinacademy.bffservice.api.base.OperationOutput;
import com.tinqinacademy.bffservice.api.operations.commentsservice.hotel.getallcommentsofroom.content.CommentBffInfo;
import lombok.*;

import java.util.List;

@Builder(toBuilder = true)
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class GetAllCommentsOfRoomBffOutput implements OperationOutput {
    List<CommentBffInfo> commentInfos;
}
