package com.tinqinacademy.bffservice.api.operations.commentsservice.hotel.getallcommentsofroom.content;

import lombok.*;

import java.time.LocalDate;

@Builder(toBuilder = true)
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CommentBffInfo {
    private String id;
    private String userId;
    private String content;
    private LocalDate publishDate;
    private LocalDate lastEditedDate;
    private String lastEditedBy;
}
