package com.tinqinacademy.bffservice.api.operations.commentsservice.system.editcommentforroom;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class AdminEditCommentForRoomBffInput implements OperationInput {
    @JsonIgnore
    @UUID
    private String commentId;
    @NotBlank
    private String roomNo;
    @JsonIgnore
    @NotBlank
    @UUID
    private String userId;
    @NotBlank
    private String content;
}
