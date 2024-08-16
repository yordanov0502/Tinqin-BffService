package com.tinqinacademy.bffservice.core.converters.hotel.getvisitors;

import com.tinqinacademy.bffservice.api.operations.hotelservice.system.getvisitors.GetVisitorsBffOutput;
import com.tinqinacademy.bffservice.api.operations.hotelservice.system.getvisitors.content.VisitorBffOutput;
import com.tinqinacademy.bffservice.core.converters.BaseConverter;
import com.tinqinacademy.hotel.api.operations.system.getvisitors.GetVisitorsOutput;
import org.springframework.stereotype.Component;

@Component
public class GetVisitorsOutputToGetVisitorsBffOutput extends BaseConverter<GetVisitorsOutput, GetVisitorsBffOutput,GetVisitorsOutputToGetVisitorsBffOutput> {
    public GetVisitorsOutputToGetVisitorsBffOutput() {
        super(GetVisitorsOutputToGetVisitorsBffOutput.class);
    }

    @Override
    protected GetVisitorsBffOutput convertObj(GetVisitorsOutput hotelOutput) {

        GetVisitorsBffOutput bffOutput = GetVisitorsBffOutput.builder()
                .visitorOutputList(hotelOutput.getVisitorOutputList()
                        .stream()
                        .map(visitor -> new VisitorBffOutput(
                                visitor.getStartDate(),
                                visitor.getEndDate(),
                                visitor.getFirstName(),
                                visitor.getLastName(),
                                visitor.getPhoneNumber(),
                                visitor.getIdCardNumber(),
                                visitor.getIdCardValidity(),
                                visitor.getIdCardIssueAuthority(),
                                visitor.getIdCardIssueDate(),
                                visitor.getRoomNumber()
                        ))
                        .toList())
                .build();

        return bffOutput;
    }
}