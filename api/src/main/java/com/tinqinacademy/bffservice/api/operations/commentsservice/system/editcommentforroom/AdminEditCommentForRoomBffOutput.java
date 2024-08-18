package com.tinqinacademy.bffservice.api.operations.commentsservice.system.editcommentforroom;

import com.tinqinacademy.bffservice.api.base.OperationOutput;
import lombok.*;

@Builder(toBuilder = true)
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class AdminEditCommentForRoomBffOutput implements OperationOutput {
    private String id;
}
