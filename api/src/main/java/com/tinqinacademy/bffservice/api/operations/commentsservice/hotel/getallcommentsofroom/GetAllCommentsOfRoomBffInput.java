package com.tinqinacademy.bffservice.api.operations.commentsservice.hotel.getallcommentsofroom;

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
public class GetAllCommentsOfRoomBffInput implements OperationInput {
    @NotBlank
    @UUID
    private String roomId;

}
