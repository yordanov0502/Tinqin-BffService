package com.tinqinacademy.bffservice.core.converters.comments.getallcommentsofroom;

import com.tinqinacademy.bffservice.api.operations.commentsservice.hotel.getallcommentsofroom.GetAllCommentsOfRoomBffOutput;
import com.tinqinacademy.bffservice.api.operations.commentsservice.hotel.getallcommentsofroom.content.CommentBffInfo;
import com.tinqinacademy.bffservice.core.converters.BaseConverter;
import com.tinqinacademy.commentsservice.api.operations.hotel.getallcommentsofroom.GetAllCommentsOfRoomOutput;
import org.springframework.stereotype.Component;

@Component
public class GetAllCommentsOfRoomToBffOutput extends BaseConverter<GetAllCommentsOfRoomOutput, GetAllCommentsOfRoomBffOutput, GetAllCommentsOfRoomToBffOutput> {
    public GetAllCommentsOfRoomToBffOutput() {
        super(GetAllCommentsOfRoomToBffOutput.class);
    }

    @Override
    protected GetAllCommentsOfRoomBffOutput convertObj(GetAllCommentsOfRoomOutput commentsOutput) {
        GetAllCommentsOfRoomBffOutput bffOutput = GetAllCommentsOfRoomBffOutput.builder()
                .commentInfos(commentsOutput.getCommentInfos()
                        .stream()
                        .map(comment -> new CommentBffInfo(
                                comment.getId(),
                                comment.getUserId(),
                                comment.getContent(),
                                comment.getPublishDate(),
                                comment.getLastEditedDate(),
                                comment.getLastEditedBy()
                        )).toList())
                .build();

        return bffOutput;
    }
}
