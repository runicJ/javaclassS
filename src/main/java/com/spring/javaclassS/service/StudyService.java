package com.spring.javaclassS.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.spring.javaclassS.vo.ChartVO;
import com.spring.javaclassS.vo.CrimeVO;
import com.spring.javaclassS.vo.ExchangeRateVO;
import com.spring.javaclassS.vo.KakaoAddressVO;
import com.spring.javaclassS.vo.QrCodeVO;
import com.spring.javaclassS.vo.TransactionVO;
import com.spring.javaclassS.vo.UserVO;

public interface StudyService {

	public String[] getCityStringArray(String dodo);

	public ArrayList<String> getCityArrayList(String dodo);

	public UserVO getUserMidSearch(String mid);

	public ArrayList<UserVO> getUserMidList(String mid);

	public void setSaveCrimeDate(CrimeVO vo);

	public void setDeleteCrimeDate(int year);

	public ArrayList<CrimeVO> getListCrimeDate(int year);

	public ArrayList<CrimeVO> getYearPoliceCheck(int year, String police, String yearOrder);

	public CrimeVO getAnalyzeTotal(int year, String police);

	public int fileUpload(MultipartFile fName, String mid);

	public int multiFileUpload(MultipartHttpServletRequest mFile);

	public Map<String, Integer> analyzer(String content);

	public KakaoAddressVO getKakaoAddressSearch(String address);

	public void setKakaoAddressInput(KakaoAddressVO vo);

	public List<KakaoAddressVO> getKakaoAddressList();

	public int setKakaoAddressDelete(String address);

	public String fileCsvToMysql(MultipartFile fName);

	public String setCsvTableDelete(String csvTable);

	public String setQrCodeCreate(String realPath);

	public String setQrCodeCreate1(String realPath, QrCodeVO vo);

	public String setQrCodeCreate2(String realPath, QrCodeVO vo);

	public String setQrCodeCreate3(String realPath, QrCodeVO vo);

	public String setQrCodeCreate4(String realPath, QrCodeVO vo);

	public QrCodeVO getQrCodeSearch(String qrCode);

	public String setThumbnailCreate(MultipartFile file);

	public List<ChartVO> getRecentlyVisitCount(int i);

	public List<TransactionVO> getTransactionList();

	public int setTransactionUserInput(TransactionVO vo);

	public List<TransactionVO> getTransactionList2();

	public void setTransactionUser1Input(TransactionVO vo);

	public void setTransactionUser2Input(TransactionVO vo);

	public int setTransactionUserTotalInput(TransactionVO vo);

	public String getCurrencyRate(String receiveCountry, String searchdate);

	public List<ExchangeRateVO> getExchangeRateUnit(String searchdate);

	public String getCurrencyRateCompute(String receiveCountry, String sendAmount, String searchdate);

	//public HashMap<String, UserVO> getUserMidMap();

//	public int setDeleteCrimeData(int year);
//
//	public String getListCrimeData(int year);
//
//	public String getPoliceCrimeDate(String police, int year);
//
//	public String getPoliceCheck(String police);
//	
//	public String getYearPoliceCheck(String police, int year, String sort);
}
