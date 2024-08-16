package com.tinqinacademy.bffservice.api.operations.hotelservice.system.getvisitors;

import com.tinqinacademy.bffservice.api.base.OperationOutput;
import com.tinqinacademy.bffservice.api.operations.hotelservice.system.getvisitors.content.VisitorBffOutput;
import lombok.*;

import java.util.List;

@Builder(toBuilder = true)
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class GetVisitorsBffOutput implements OperationOutput {
    private List<VisitorBffOutput> visitorOutputList;
}
