package com.spring.javaclassS.vo;

import lombok.Data;

@Data
public class ExchangeRateVO {
  private int result;							// 조회결과 성공여부(1:성공, 2:데이터코드오류, 3:인증코드오류, 4:일일제한횟수마감)
  private String cur_unit;				// 통화코드
  private String ttb;							// 전신환(송금) 받을때
  private String tts;							// 전신환(송금) 보낼때
  private String deal_bas_r;			// 매매 기준율
  private String bkpr;						// 장부가격
  private String yy_efee_r;				// 년환 가료율
  private String ten_dd_efee_r;		// 10일환 가료율
  private String kftc_bkpr;				// 서울외국환 중개 장부가격
  private String kftc_deal_bas_r;	// 서울 외국환 중개 매매기준율
  private String cur_nm;					// 국가/통화명
}
