package com.tinqinacademy.bffservice.api.operations.commentsservice.system.addcommentforroom;

import com.tinqinacademy.bffservice.api.base.OperationOutput;
import lombok.*;

@Builder(toBuilder = true)
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class AddCommentForRoomBffOutput implements OperationOutput {
    private String id;
}
