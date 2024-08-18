package com.tinqinacademy.bffservice.core.converters.comments.addcommentforroom;

import com.tinqinacademy.bffservice.api.operations.commentsservice.hotel.addcommentforroom.AddCommentForRoomBffInput;
import com.tinqinacademy.bffservice.core.converters.BaseConverter;
import com.tinqinacademy.commentsservice.api.operations.hotel.addcommentforroom.AddCommentForRoomInput;
import org.springframework.stereotype.Component;

@Component
public class AddCommentForRoomBffInputToCommentInput extends BaseConverter<AddCommentForRoomBffInput, AddCommentForRoomInput,AddCommentForRoomBffInputToCommentInput> {
    public AddCommentForRoomBffInputToCommentInput() {
        super(AddCommentForRoomBffInputToCommentInput.class);
    }

    @Override
    protected AddCommentForRoomInput convertObj(AddCommentForRoomBffInput bffInput) {
        AddCommentForRoomInput commentInput = AddCommentForRoomInput.builder()
                .roomId(bffInput.getRoomId())
                .userId(bffInput.getUserId())
                .content(bffInput.getContent())
                .build();

        return commentInput;
    }
}
