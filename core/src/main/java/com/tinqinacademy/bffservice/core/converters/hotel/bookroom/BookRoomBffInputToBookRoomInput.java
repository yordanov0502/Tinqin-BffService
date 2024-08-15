package com.tinqinacademy.bffservice.core.converters.hotel.bookroom;

import com.tinqinacademy.bffservice.api.operations.hotelservice.hotel.bookroom.BookRoomBffInput;
import com.tinqinacademy.bffservice.core.converters.BaseConverter;
import com.tinqinacademy.hotel.api.operations.hotel.bookroom.BookRoomInput;
import org.springframework.stereotype.Component;

@Component
public class BookRoomBffInputToBookRoomInput extends BaseConverter<BookRoomBffInput, BookRoomInput, BookRoomBffInputToBookRoomInput> {


    public BookRoomBffInputToBookRoomInput() {
        super(BookRoomBffInputToBookRoomInput.class);
    }

    @Override
    protected BookRoomInput convertObj(BookRoomBffInput bffInput) {

        BookRoomInput input = BookRoomInput.builder()
                .startDate(bffInput.getStartDate())
                .endDate(bffInput.getEndDate())
                .userId(bffInput.getUserContextId())
                .build();

        return input;
    }
}
