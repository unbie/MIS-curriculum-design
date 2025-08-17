package com.market.controller.dto;

import lombok.Data;

@Data
public class CommentDTO {

    private String reviewerAccount;
    private String reviewedAccount;
    private String initialComments;
    private Integer stars;

}