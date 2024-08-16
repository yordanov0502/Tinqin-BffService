package com.tinqinacademy.bffservice.core.converters.hotel.registervisitor;

import com.tinqinacademy.bffservice.api.operations.hotelservice.system.registervisitor.RegisterVisitorBffInput;
import com.tinqinacademy.bffservice.core.converters.BaseConverter;
import com.tinqinacademy.hotel.api.operations.system.registervisitor.RegisterVisitorInput;
import com.tinqinacademy.hotel.api.operations.system.registervisitor.content.VisitorInput;
import org.springframework.stereotype.Component;

@Component
public class RegisterVisitorBffInputToRegisterVisitorInput extends BaseConverter<RegisterVisitorBffInput, RegisterVisitorInput, RegisterVisitorBffInputToRegisterVisitorInput> {
    public RegisterVisitorBffInputToRegisterVisitorInput() {
        super(RegisterVisitorBffInputToRegisterVisitorInput.class);
    }

    @Override
    protected RegisterVisitorInput convertObj(RegisterVisitorBffInput bffInput) {
        RegisterVisitorInput hotelInput = RegisterVisitorInput.builder()
                .visitorInputList(bffInput.getVisitorInputList()
                        .stream()
                        .map(visitor -> new VisitorInput(
                                visitor.getRoomId(),
                                visitor.getStartDate(),
                                visitor.getEndDate(),
                                visitor.getFirstName(),
                                visitor.getLastName(),
                                visitor.getDateOfBirth(),
                                visitor.getPhoneNumber(),
                                visitor.getIdCardNumber(),
                                visitor.getIdCardValidity(),
                                visitor.getIdCardIssueAuthority(),
                                visitor.getIdCardIssueDate()
                        ))
                        .toList())
                .build();

        return hotelInput;
    }


}
