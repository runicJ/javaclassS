show tables;

/* 대분류(main) */
create table categoryMain(
  categoryMainCode  char(1)  not null,		/* 대분류코드(A,B,C,...) => 영문 대문자 1자(중복불허) */
  categoryMainName  varchar(20) not null, /* 대분류명(회사명 => 삼성/현대/LG...(중복불허) */
  primary key(categoryMainCode),
  unique key(categoryMainName)
);

/* 중분류(middle) */
create table categoryMiddle(
	categoryMainCode  char(1)  not null,			/* 대분류코드를 외래키로 지정 */
  categoryMiddleCode  char(2)  not null,		/* 중분류코드(01,02,03,...) => 문자형 숫자 2자(중복불허) */
  categoryMiddleName  varchar(20) not null, /* 중분류명(제품분류명 => 전자제품/생활가전/차종/의류/신발류...(중복불허) */
  primary key(categoryMiddleCode),
  foreign key(categoryMainCode) references categoryMain(categoryMainCode)
);

/* 소분류(sub) */
create table categorySub(
	categoryMainCode  char(1)  not null,			/* 대분류코드를 외래키로 지정 */
  categoryMiddleCode  char(2)  not null,		/* 중분류코드를 외래키로 지정 */
  categorySubCode  char(3)  not null,				/* 소분류코드(001,002,003,...) => 문자형 숫자 2자(중복불허) */
  categorySubName  varchar(20) not null, 		/* 소분류명(상품구분 => 중분류가 전자제품이라면? 냉장고/에어컨/오디오/TV */
  primary key(categorySubCode),
  foreign key(categoryMainCode) references categoryMain(categoryMainCode),
  foreign key(categoryMiddleCode) references categoryMiddle(categoryMiddleCode)
);

select * from categoryMiddle;
select * from categoryMiddle 
    where categoryMiddleCode=#{vo.categoryMiddleCode} and categoryMiddleName=#{vo.categoryMiddleName};

/* 세분류(상품 테이블) */
create table dbProduct (
  idx  int not null, 				/* 상품 고유번호 */
	categoryMainCode  char(1)  not null,			/* 대분류코드를 외래키로 지정 */
  categoryMiddleCode  char(2)  not null,		/* 중분류코드를 외래키로 지정 */
  categorySubCode  char(3)  not null,				/* 소분류코드를 외래키로 지정 */
  productCode   varchar(20) not null,				/* 상품고유코드(대분류코드+중분류코드+소분류코드+상품고유번호) 예 : A 01 002 5 */
  productName   varchar(50) not null,				/* 상품명(상품모델명) */
  detail				varchar(100) not null,			/* 상품의 간단설명(초기화면 메인창에 출력할 간단한 설명) */
  mainPrice     int not null,								/* 상품의 기본가격 */
  fSName				varchar(200) not null,			/* 상품의 기본사진(1장이상 처리시는 '/'로 구분한다.) */
  content				text not null,							/* 상품의 상세설명 - ckeditor를 이용 */
  primary key(idx),
  unique key(productCode,productName),
  foreign key(categoryMainCode) references categoryMain(categoryMainCode),
  foreign key(categoryMiddleCode) references categoryMiddle(categoryMiddleCode),
  foreign key(categorySubCode) references categorySub(categorySubCode)
);

/* 상품 옵션 */
create table dbOption (
  idx    int not null auto_increment, /* 옵션 고유번호 */
  productIdx int not null,						/* product테이블(상품)의 고유번호 - 외래키로 지정 */
  optionName varchar(50) not null, 		/* 옵션 이름 */
  optionPrice int not null default 0, /* 옵션 가격 */
  primary key(idx),
  foreign key(productIdx) references dbProduct(idx)
);

desc dbOption;
select * from dbOption where productIdx = 1 order by optionName;

/* 상품 카트 */
create table dbCart (
	idx int not null auto_increment primary key,
	cartDate datetime default now(),
	mid varchar(20) not null,
	productIdx int not null,
	productName varchar(50) not null,
	mainPrice int not null,
	thumbImg varchar(200) not null,
	optionIdx varchar(50) not null,
	optionName varchar(50) not null,
	optionPrice varchar(50) not null default 0,
	optionNum varchar(50) not null,
	totalPrice int not null,
	foreign key(productIdx) references dbProduct(idx)
);

drop table dbCart;
