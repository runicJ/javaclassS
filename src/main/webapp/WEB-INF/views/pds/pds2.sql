show tables;
/* "/" 구분자를 넣어서 만듦(하나하나 따로 필드를 만들지 않음) / varchar 마지막은 무조건 null이 들어가고 나머지는 반환함(char와 다름) - 10Byte 사용하면 사용안한 189Byte 반환, 1은 마지막 null */
create table pds2 (
	idx  int not null auto_increment,  /* 자료실 고유번호 */
	mid  varchar(20) not null,  /* 작성자 아이디 */
	nickName  varchar(20) not null,  /* 작성자 닉네임(실명제) */
	fName  varchar(200) not null,  /* 업로드 시의 파일명 */
	fSName  varchar(200) not null,  /* 실제 서버에 저장되는 파일명 */
	fSize  int not null,  /* 업로드되는 파일의 총 사이즈 */
	title  varchar(100) not null,  /* 업로드 파일의 간단 제목 - 자료실을 게시판처럼(타이틀 클릭하면 들어가도록) */
	part  varchar(20) not null,  /* 파일분류(학습/여행/음식/__/기타) */
	fDate  datetime default now(),  /* 업로드한 날짜 */  
	downNum  int  default 0,  /* 다운 받은 횟수 */
	openSw  char(3) default '공개',  /* 파일 공개여부(공개/비공개) */
	/*pwd  varchar(100),  파일 비공개 시 암호설정 sha256 암호화 */
	hostIp  varchar(30) not null,  /* 업로드한 클라이언트 IP */
	content  text,  /* 업로드 파일의 상세 설명 */
	primary key(idx),
	foreign key(mid) references member3(mid)
);

desc pds2;

drop table pds2;