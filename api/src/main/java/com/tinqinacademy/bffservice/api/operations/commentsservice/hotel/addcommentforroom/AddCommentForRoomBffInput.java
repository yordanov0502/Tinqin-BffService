package com.tinqinacademy.bffservice.api.operations.commentsservice.hotel.addcommentforroom;

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
public class AddCommentForRoomBffInput implements OperationInput {
    @JsonIgnore
    @UUID
    private String roomId;
    @JsonIgnore
    @NotBlank
    @UUID
    private String userId;
    @NotBlank
    private String content;
}
