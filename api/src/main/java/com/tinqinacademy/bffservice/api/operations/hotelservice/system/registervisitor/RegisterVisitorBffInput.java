package com.tinqinacademy.bffservice.api.operations.hotelservice.system.registervisitor;


import com.tinqinacademy.bffservice.api.base.OperationInput;
import com.tinqinacademy.bffservice.api.operations.hotelservice.system.registervisitor.content.VisitorBffInput;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Builder(toBuilder = true)
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class RegisterVisitorBffInput implements OperationInput {
   @NotNull(message = "Visitors list cannot be null.")
   private List<@Valid VisitorBffInput> visitorInputList;
}
