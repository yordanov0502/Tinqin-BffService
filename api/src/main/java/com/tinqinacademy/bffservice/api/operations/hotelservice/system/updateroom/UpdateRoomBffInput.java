package com.tinqinacademy.bffservice.api.operations.hotelservice.system.updateroom;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tinqinacademy.bffservice.api.base.OperationInput;
import com.tinqinacademy.bffservice.api.model.enums.BathroomType;
import com.tinqinacademy.bffservice.api.model.enums.BedSize;
import com.tinqinacademy.bffservice.api.validation.bathroomtype.BathroomTypeCode;
import com.tinqinacademy.bffservice.api.validation.bedsize.BedSizeCode;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;
import org.hibernate.validator.constraints.UUID;

import java.math.BigDecimal;

@Builder(toBuilder = true)
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UpdateRoomBffInput implements OperationInput {

    @JsonIgnore
    @UUID
    private String roomId;
    @Positive
    @NotNull
    private Integer bedCount;
    @NotNull
    @BedSizeCode
    private BedSize bedSize;
    @NotNull
    @BathroomTypeCode
    private BathroomType bathroomType;
    @Positive
    @NotNull
    private Integer floor;
    @NotBlank
    private String roomNo;
    @Positive
    @NotNull
    private BigDecimal price;
}
