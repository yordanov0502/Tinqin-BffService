package com.tinqinacademy.bffservice.core.converters.comments.usereditcommentforroom;

import com.tinqinacademy.bffservice.api.operations.commentsservice.hotel.editcommentforroom.UserEditCommentForRoomBffInput;
import com.tinqinacademy.bffservice.core.converters.BaseConverter;
import com.tinqinacademy.commentsservice.api.operations.hotel.editcommentforroom.UserEditCommentForRoomInput;
import org.springframework.stereotype.Component;

@Component
public class UserEditCommentForRoomBffInputToCommentsInput extends BaseConverter<UserEditCommentForRoomBffInput, UserEditCommentForRoomInput,UserEditCommentForRoomBffInputToCommentsInput> {
    public UserEditCommentForRoomBffInputToCommentsInput() {
        super(UserEditCommentForRoomBffInputToCommentsInput.class);
    }

    @Override
    protected UserEditCommentForRoomInput convertObj(UserEditCommentForRoomBffInput bffInput) {
        UserEditCommentForRoomInput commentsInput = UserEditCommentForRoomInput.builder()
                .commentId(bffInput.getCommentId())
                .content(bffInput.getContent())
                .userId(bffInput.getUserId())
                .build();

        return commentsInput;
    }
}
