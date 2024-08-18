package com.tinqinacademy.bffservice.api.operations.commentsservice.hotel.editcommentforroom;

import com.tinqinacademy.bffservice.api.base.OperationOutput;
import lombok.*;

@Builder(toBuilder = true)
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UserEditCommentForRoomBffOutput implements OperationOutput {
    private String id;
}
