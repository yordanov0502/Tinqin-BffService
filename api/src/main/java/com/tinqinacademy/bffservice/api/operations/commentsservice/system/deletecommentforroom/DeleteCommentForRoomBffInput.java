package com.tinqinacademy.bffservice.api.operations.commentsservice.system.deletecommentforroom;

import com.tinqinacademy.bffservice.api.base.OperationInput;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.validator.constraints.UUID;

@Builder(toBuilder = true)
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class DeleteCommentForRoomBffInput implements OperationInput {
    @NotBlank
    @UUID
    private String commentId;

}
