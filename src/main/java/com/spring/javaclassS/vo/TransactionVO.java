package com.spring.javaclassS.vo;


import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

import lombok.Data;

@Data
public class TransactionVO {  // validator에서도 사용(프로젝트에서는 트렌젝션 안에서 validator도 사용하니까 하나만 만듦)
	private int idx;
	
	//@NotEmpty(message="작성자 아이디를 입력하세요.")
	@NotBlank(message = "공백은 입력할 수 없습니다. 입력 값 : ${validatedValue}")
	@Size(min=4, max=10, message = "아이디 길이가 잘못되었습니다.")
	private String mid;
	
	@Size(min=2, max=20, message = "성명의 길이가 잘못되었습니다.")
	private String name;
	
	@Range(min=19, message = "미성년자는 가입할수 없습니다.")
	private int age;
	
	private String address;
	
	// user2테이블에서의 필드 추가
	private String job;
}
