package com.tinqinacademy.bffservice.core.converters.comments.admineditcommentforroom;

import com.tinqinacademy.bffservice.api.operations.commentsservice.system.editcommentforroom.AdminEditCommentForRoomBffInput;
import com.tinqinacademy.bffservice.core.converters.BaseConverter;
import com.tinqinacademy.commentsservice.api.operations.system.editcommentforroom.AdminEditCommentForRoomInput;
import org.springframework.stereotype.Component;

@Component
public class AdminEditCommentForRoomBffInputToCommentsInput extends BaseConverter<AdminEditCommentForRoomBffInput, AdminEditCommentForRoomInput, AdminEditCommentForRoomBffInputToCommentsInput> {
    public AdminEditCommentForRoomBffInputToCommentsInput() {
        super(AdminEditCommentForRoomBffInputToCommentsInput.class);
    }

    @Override
    protected AdminEditCommentForRoomInput convertObj(AdminEditCommentForRoomBffInput bffInput) {
        AdminEditCommentForRoomInput commentsInput = AdminEditCommentForRoomInput.builder()
                .commentId(bffInput.getCommentId())
                .content(bffInput.getContent())
                .userId(bffInput.getUserId())
                .build();

        return commentsInput;
    }
}
