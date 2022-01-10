package com.cos.photogramstart.web.dto.comment;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Data;

//@NotNull = Null 값 체크 
//@NotEmpty  = 빈값이거나 null 체크
//@NotBlank = 빈값이거나 null 체크 그리고 빈 공백 ( " " ) 까지 

@Data
public class CommentDto {
	@NotBlank // 빈값이거나 null  그리고 빈 공백 체크 
	private String content;
	@NotNull // 빈값 과 null 체크 
	private Integer imageId;
}
 