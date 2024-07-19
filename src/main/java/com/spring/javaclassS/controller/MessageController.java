package com.spring.javaclassS.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MessageController {

	@RequestMapping(value = "/message/{msgFlag}", method = RequestMethod.GET)
	public String getMessage(Model model,
			@PathVariable String msgFlag,
			@RequestParam(name="mid", defaultValue = "", required = false) String mid,
			@RequestParam(name="tempFlag", defaultValue = "", required = false) String tempFlag,
			@RequestParam(name="idx", defaultValue = "", required = false) String idx,
			@RequestParam(name="pag", defaultValue = "1", required = false) String pag,
			@RequestParam(name="pageSize", defaultValue = "5", required = false) String pageSize
		) {
		
		if(msgFlag.equals("userDeleteOk")) {
			model.addAttribute("msg", "회원 자료가 삭제 되었습니다.");
			model.addAttribute("url", "/user/userList");
		}
		else if(msgFlag.equals("userDeleteNo")) {
			model.addAttribute("msg", "회원 자료가 삭제 실패~~");
			model.addAttribute("url", "/user/userList");
		}
		else if(msgFlag.equals("userInputOk")) {
			model.addAttribute("msg", "회원 자료가 등록되었습니다.");
			model.addAttribute("url", "/user/userList");
		}
		else if(msgFlag.equals("userInputNo")) {
			model.addAttribute("msg", "회원 자료 등록 실패~~");
			model.addAttribute("url", "/user/userList");
		}
		else if(msgFlag.equals("userUpdateOk")) {
			model.addAttribute("msg", "회원 정보를 수정하였습니다.");
			model.addAttribute("url", "/user/userList");
		}
		else if(msgFlag.equals("userUpdateNo")) {
			model.addAttribute("msg", "회원 정보 실패~~");
			model.addAttribute("url", "/user/userList");
		}
		else if(msgFlag.equals("dbtestDeleteOk")) {
			model.addAttribute("msg", "회원 삭제 완료!");
			if(tempFlag.equals("validator")) model.addAttribute("url", "/study/validator/validatorForm");
			else if(tempFlag.equals("transaction")) model.addAttribute("url", "/study/transaction/transactionForm");
			else model.addAttribute("url", "/dbtest/dbtestList");
		}
		else if(msgFlag.equals("dbtestDeleteNo")) {
			model.addAttribute("msg", "회원 삭제 실패~~");
			model.addAttribute("url", "/dbtest/dbtestList");
		}
		else if(msgFlag.equals("dbtestInputOk")) {
			model.addAttribute("msg", "회원 가입 완료!");
			model.addAttribute("url", "/dbtest/dbtestList");
		}
		else if(msgFlag.equals("dbtestInputNo")) {
			model.addAttribute("msg", "회원 가입 실패~~");
			model.addAttribute("url", "/dbtest/dbtestList");
		}
		else if(msgFlag.equals("dbtestUpdateOk")) {
			model.addAttribute("msg", "회원정보 수정 완료!");
			model.addAttribute("url", "/dbtest/dbtestList");
		}
		else if(msgFlag.equals("dbtestUpdateNo")) {
			model.addAttribute("msg", "회원정보 수정 실패~~");
			model.addAttribute("url", "/dbtest/dbtestList");
		}
		else if(msgFlag.equals("mailSendOk")) {
			model.addAttribute("msg", "메일이 전송되었습니다.");
			model.addAttribute("url", "/study/mail/mailForm");
		}
		else if(msgFlag.equals("guestInputOk")) {
			model.addAttribute("msg", "방명록에 글이 등록되었습니다.");
			model.addAttribute("url", "/guest/guestList");
		}
		else if(msgFlag.equals("guestInputNo")) {
			model.addAttribute("msg", "방명록 글 등록 실패");
			model.addAttribute("url", "/guest/guestInput");
		}
		else if(msgFlag.equals("idCheckNo")) {
			model.addAttribute("msg", "이미 사용중인 아이디 입니다.");
			model.addAttribute("url", "/member/memberJoin");
		}
		else if(msgFlag.equals("nickCheckNo")) {
			model.addAttribute("msg", "이미 사용중인 닉네임 입니다.");
			model.addAttribute("url", "/member/memberJoin");
		}
		else if(msgFlag.equals("memberJoinOk")) {
			model.addAttribute("msg", "회원에 가입되셨습니다.");
			model.addAttribute("url", "/member/memberLogin");
		}
		else if(msgFlag.equals("memberJoinNo")) {
			model.addAttribute("msg", "회원 가입 실패~~");
			model.addAttribute("url", "/member/memberJoin");
		}
		else if(msgFlag.equals("memberLoginNewOk")) {
			model.addAttribute("msg", mid+"님 로그인 되셨습니다.\\n신규 비밀번호가 발급되었습니다. 점검 후 회원정보를 변경해주세요.");
			model.addAttribute("url", "/member/memberMain");
		}
		else if(msgFlag.equals("memberLoginNo")) {
			model.addAttribute("msg", "로그인 실패~~");
			model.addAttribute("url", "/member/memberLogin");
		}
		else if(msgFlag.equals("memberLoginOk")) {
			model.addAttribute("msg", mid+"님 로그인 되셨습니다.");
			model.addAttribute("url", "/member/memberMain");
		}
		else if(msgFlag.equals("memberLoginNo")) {
			model.addAttribute("msg", "로그인 실패~~");
			model.addAttribute("url", "/member/memberLogin");
		}
		else if(msgFlag.equals("memberLogout")) {
			model.addAttribute("msg", mid + "님 로그아웃 되셨습니다.");
			model.addAttribute("url", "/member/memberLogin");
		}
		else if(msgFlag.equals("kakaoLogout")) {
			model.addAttribute("msg", mid + "님 카카오 로그아웃 되셨습니다.");
			model.addAttribute("url", "/member/memberLogin");
		}
		else if(msgFlag.equals("fileUploadOk")) {
			model.addAttribute("msg", "파일이 업로드 되었습니다.");
			model.addAttribute("url", "/study/fileUpload/fileUpload");
		}
		else if(msgFlag.equals("fileUploadNo")) {
			model.addAttribute("msg", "파일 업로드 실패~~");
			model.addAttribute("url", "/study/fileUpload/fileUpload");
		}
		else if(msgFlag.equals("memberUpdateOk")) {
			model.addAttribute("msg", "회원 정보가 변경되었습니다.");
			model.addAttribute("url", "/member/memberUpdate");
		}
		else if(msgFlag.equals("memberUpdateNo")) {
			model.addAttribute("msg", "회원 정보 변경실패~~");
			model.addAttribute("url", "/member/memberPwdCheck/i");
		}
		else if(msgFlag.equals("adminNo")) {
			model.addAttribute("msg", "관리자만 접속하실수 있습니다.");
			model.addAttribute("url", "/");
		}
		else if(msgFlag.equals("memberNo")) {
			model.addAttribute("msg", "회원 로그인후 사용하세요");
			model.addAttribute("url", "/member/memberLogin");
		}
		else if(msgFlag.equals("memberLevelNo")) {
			model.addAttribute("msg", "회원 등급을 확인하세요.");
			model.addAttribute("url", "/member/memberMain");
		}
		else if(msgFlag.equals("boardInputOk")) {
			model.addAttribute("msg", "게시글이 등록되었습니다.");
			model.addAttribute("url", "/board/boardList");
		}
		else if(msgFlag.equals("boardInputNo")) {
			model.addAttribute("msg", "게시글 등록 실패~~");
			model.addAttribute("url", "/board/boardInput");
		}
		else if(msgFlag.equals("boardUpdateOk")) {
			model.addAttribute("msg", "게시글이 수정 되었습니다.");
			model.addAttribute("url", "/board/boardContent?idx="+idx+"&pag="+pag+"&pageSize="+pageSize);
		}
		else if(msgFlag.equals("boardUpdateNo")) {
			model.addAttribute("msg", "게시글 수정 실패~~");
			model.addAttribute("url", "/board/boardUpdate?idx="+idx+"&pag="+pag+"&pageSize="+pageSize);
		}
		else if(msgFlag.equals("boardDeleteOk")) {
			model.addAttribute("msg", "게시글이 삭제 되었습니다.");
			model.addAttribute("url", "/board/boardList");
		}
		else if(msgFlag.equals("boardDeleteNo")) {
			model.addAttribute("msg", "게시글 삭제 실패~~~");
			model.addAttribute("url", "/board/boardContent?idx="+idx+"&pag="+pag+"&pageSize="+pageSize);
		}
		else if(msgFlag.equals("multiFileUploadOk")) {
			model.addAttribute("msg", "파일이 업로드 되었습니다.");
			model.addAttribute("url", "/study/fileUpload/fileUpload");
		}
		else if(msgFlag.equals("multiFileUploadNo")) {
			model.addAttribute("msg", "파일 업로드 실패~~");
			model.addAttribute("url", "/study/fileUpload/multiFile");
		}
		else if(msgFlag.equals("pdsUploadOk")) {
			model.addAttribute("msg", "파일이 업로드 되었습니다.");
			model.addAttribute("url", "/pds/pdsList");
		}
		else if(msgFlag.equals("pdsUploadNo")) {
			model.addAttribute("msg", "파일 업로드 실패~~");
			model.addAttribute("url", "/pds/pdsInput");
		}
		else if(msgFlag.equals("midSameSearch")) {
			model.addAttribute("msg", "같은 아이디를 가진 회원이 존재합니다. \\n아이디 확인 후 다시 로그인해 주세요.");
			model.addAttribute("url", "/member/memberLogin");
		}
		else if(msgFlag.equals("transactionUserInputOk")) {
			model.addAttribute("msg", "user테이블에 회원이 등록되었습니다.");
			if(tempFlag.equals("validator")) model.addAttribute("url", "/study/validator/validatorForm");
			else if(tempFlag.equals("transaction")) model.addAttribute("url", "/study/transaction/transactionForm");
		}
		else if(msgFlag.equals("transactionUserInputNo")) {
			model.addAttribute("msg", "user테이블에 회원이 등록실패~~");
			model.addAttribute("url", "/study/validator/validatorForm");
		}
		else if(msgFlag.equals("backendCheckNo")) {
			model.addAttribute("msg", "저장실패~~ 입력된 자료를 확인해보세요.(BackEnd Check)");
			model.addAttribute("url", "/study/validator/validatorForm");
		}
		else if(msgFlag.equals("dbProductInputOk")) {
			model.addAttribute("msg", "상품이 등록되었습니다.");
			model.addAttribute("url", "dbShop/dbShopList");
		}
		else if(msgFlag.equals("dbProductInputNo")) {
			model.addAttribute("msg", "상품 등록 실패~~");
			model.addAttribute("url", "dbShop/dbProduct");
		}
		else if(msgFlag.equals("dbOptionInputOk")) {
			model.addAttribute("msg", "옵션항목이 등록되었습니다.");
			model.addAttribute("url", "dbShop/dbOption?productName="+tempFlag);
		}
		else if(msgFlag.equals("dbOptionInputNo")) {
			model.addAttribute("msg", "옵션항목 등록 실패~~");
			model.addAttribute("url", "dbShop/dbOption?productName="+tempFlag);
		}
		else if(msgFlag.equals("cartOrderOk")) {
			model.addAttribute("msg", "장바구니에 상품이 등록되었습니다.\\n주문창으로 이동합니다.");
			model.addAttribute("url", "/dbShop/dbCartList");
		}
		else if(msgFlag.equals("cartOrderNo")) {
			model.addAttribute("msg", "장바구니에 상품 등록실패");
			model.addAttribute("url", "/dbShop/dbCartList");
		}
		else if(msgFlag.equals("cartInputOk")) {
			model.addAttribute("msg", "장바구니에 상품이 등록되었습니다.\\n즐거운 쇼핑되세요.");
			model.addAttribute("url", "/dbShop/dbProductList");
		}
		else if(msgFlag.equals("cartEmpty")) {
			model.addAttribute("msg", "장바구니가 비어있습니다.");
			model.addAttribute("url", "/dbShop/dbProductList");
		}
		else if(msgFlag.equals("paymentResultOk")) {
			model.addAttribute("msg", "결재가 성공적으로 완료되었습니다.");
			model.addAttribute("url", "/dbShop/paymentResultOk");
		}
		
//		if(msgFlag.equals("userDeleteOk")) {
//			model.addAttribute("msg", "회원 자료가 삭제 되었습니다.");
//			model.addAttribute("url", "/user/userList");
//		}
//		else if(msgFlag.equals("userDeleteNo")) {
//			model.addAttribute("msg", "회원 자료가 삭제 실패~~");
//			model.addAttribute("url", "/user/userList");
//		}
//		else if(msgFlag.equals("dbtestDeleteOk")) {
//			model.addAttribute("msg", "회원 자료가 삭제 되었습니다.");
//			model.addAttribute("url", "/dbTest/dbtestList");
//		}
//		else if(msgFlag.equals("dbtestDeleteNo")) {
//			model.addAttribute("msg", "회원 자료가 삭제 실패~");
//			model.addAttribute("url", "/dbTest/dbtestList");
//		}
//		else if(msgFlag.equals("userInputOk")) {
//			model.addAttribute("msg", "회원 자료가 등록되었습니다.");
//			model.addAttribute("url", "/user/userList");
//		}
//		else if(msgFlag.equals("userInputNo")) {
//			model.addAttribute("msg", "회원 자료 등록 실패~~");
//			model.addAttribute("url", "/user/userList");
//		}
//		else if(msgFlag.equals("userSearchNo")) {
//			model.addAttribute("msg", "검색어와 관련된 회원이 존재하지 않습니다.");
//			model.addAttribute("url", "/user/userList");
//		}
//		else if(msgFlag.equals("userUpdateOk")) {
//			model.addAttribute("msg", "회원 정보를 수정하였습니다.");
//			model.addAttribute("url", "/user/userList");
//		}
//		else if(msgFlag.equals("userUpdateNo")) {
//			model.addAttribute("msg", "회원 정보 실패~~");
//			model.addAttribute("url", "/user/userList");
//		}
//		else if(msgFlag.equals("dbtestDeleteOk")) {
//			model.addAttribute("msg", "회원 삭제 완료!");
//			model.addAttribute("url", "/dbtest/dbtestList");
//		}
//		else if(msgFlag.equals("dbtestDeleteNo")) {
//			model.addAttribute("msg", "회원 삭제 실패~~");
//			model.addAttribute("url", "/dbtest/dbtestList");
//		}	
//		else if(msgFlag.equals("dbtestInputOk")) {
//			model.addAttribute("msg", "회원 가입 완료!");
//			model.addAttribute("url", "/dbtest/dbtestList");
//		}
//		else if(msgFlag.equals("dbtestInputNo")) {
//			model.addAttribute("msg", "회원 가입 실패~~");
//			model.addAttribute("url", "/dbtest/dbtestList");
//		}
//		else if(msgFlag.equals("dbtestUpdateOk")) {
//			model.addAttribute("msg", "회원정보 수정 완료!");
//			model.addAttribute("url", "/dbtest/dbtestList");
//		}
//		else if(msgFlag.equals("dbtestUpdateNo")) {
//			model.addAttribute("msg", "회원정보 수정 실패~~");
//			model.addAttribute("url", "/dbtest/dbtestList");
//		}
//		else if(msgFlag.equals("mailSendOk")) {
//			model.addAttribute("msg", "메일이 전송되었습니다.");
//			model.addAttribute("url", "/study/mail/mailForm");
//		}
//		else if(msgFlag.equals("guestInputOk")) {
//			model.addAttribute("msg", "방명록에 글이 등록되었습니다.");
//			model.addAttribute("url", "/guest/guestList");
//		}
//		else if(msgFlag.equals("guestInputNo")) {
//			model.addAttribute("msg", "방명록 글 등록 실패");
//			model.addAttribute("url", "/guest/guestInput");
//		}
//		else if(msgFlag.equals("idCheckNo")) {
//			model.addAttribute("msg", "이미 사용중인 아이디 입니다.");
//			model.addAttribute("url", "/member/memberJoin");
//		}
//		else if(msgFlag.equals("nickCheckNo")) {
//			model.addAttribute("msg", "이미 사용중인 닉네임 입니다.");
//			model.addAttribute("url", "/member/memberJoin");
//		}
//		else if(msgFlag.equals("memberJoinOk")) {
//			model.addAttribute("msg", "회원에 가입되셨습니다.");
//			model.addAttribute("url", "/member/memberLogin");
//		}
//		else if(msgFlag.equals("memberJoinNo")) {
//			model.addAttribute("msg", "회원 가입 실패~~");
//			model.addAttribute("url", "/member/memberJoin");
//		}
//		else if(msgFlag.equals("memberLoginOk")) {
//			model.addAttribute("msg", mid+"님 로그인 되셨습니다.");
//			model.addAttribute("url", "/member/memberMain");
//		}
//		else if(msgFlag.equals("memberLoginNo")) {
//			model.addAttribute("msg", "로그인 실패~~");
//			model.addAttribute("url", "/member/memberLogin");
//		}
//		else if(msgFlag.equals("memberLogout")) {
//			model.addAttribute("msg", mid + "님 로그아웃 되셨습니다.");
//			model.addAttribute("url", "/member/memberLogin");
//		}
//		else if(msgFlag.equals("fileUploadOk")) {
//			model.addAttribute("msg", "파일이 업로드 되었습니다.");
//			model.addAttribute("url", "/study/fileUpload/fileUpload");
//		}
//		else if(msgFlag.equals("fileUploadNo")) {
//			model.addAttribute("msg", "파일 업로드 실패~~");
//			model.addAttribute("url", "/study/fileUpload/fileUpload");
//		}
//		else if(msgFlag.equals("memberUpdateOk")) {
//			model.addAttribute("msg", "회원정보가 수정되었습니다.");
//			model.addAttribute("url", "/member/memberMain");
//		}
//		else if(msgFlag.equals("memberUpdateNo")) {
//			model.addAttribute("msg", "회원정보 수정 실패");
//			model.addAttribute("url", "/member/memberUpdate");
//		}
//		else if(msgFlag.equals("memberDeleteOk")) {
//			model.addAttribute("msg", "회원 탈퇴처리 되었습니다.");
//			model.addAttribute("url", "/member/memberLogin");
//		}
//		else if(msgFlag.equals("memberDeleteNo")) {
//			model.addAttribute("msg", "회원탈퇴 실패");
//			model.addAttribute("url", "/member/memberDelete");
//		}
//		else if(msgFlag.equals("memberPwdNo")) {
//			model.addAttribute("msg", "입력하신 비밀번호가 일치하지 않습니다.");
//			model.addAttribute("url", "/member/memberPwdCheck/i");
//		}
//		else if(msgFlag.equals("adminNo")) {
//			model.addAttribute("msg", "관리자만 접속하실 수 있습니다.");
//			model.addAttribute("url", "/");
//		}
//		else if(msgFlag.equals("memberNo")) {
//			model.addAttribute("msg", "회원 로그인 후 사용하세요");
//			model.addAttribute("url", "/member/memberLogin");
//		}
//		else if(msgFlag.equals("memberLevelNo")) {
//			model.addAttribute("msg", "회원 등급을 사용하세요");
//			model.addAttribute("url", "/member/memberMain");
//		}
		
		return "include/message";
	}
	
}
