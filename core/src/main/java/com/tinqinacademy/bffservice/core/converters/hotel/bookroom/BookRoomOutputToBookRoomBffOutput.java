package com.tinqinacademy.bffservice.core.converters.hotel.bookroom;

import com.tinqinacademy.bffservice.api.operations.hotelservice.hotel.bookroom.BookRoomBffOutput;
import com.tinqinacademy.bffservice.core.converters.BaseConverter;
import com.tinqinacademy.hotel.api.operations.hotel.bookroom.BookRoomOutput;
import org.springframework.stereotype.Component;

@Component
public class BookRoomOutputToBookRoomBffOutput extends BaseConverter<BookRoomOutput, BookRoomBffOutput,BookRoomOutputToBookRoomBffOutput> {
    public BookRoomOutputToBookRoomBffOutput() {
        super(BookRoomOutputToBookRoomBffOutput.class);
    }

    @Override
    protected BookRoomBffOutput convertObj(BookRoomOutput output) {
        return BookRoomBffOutput.builder().build();
    }
}