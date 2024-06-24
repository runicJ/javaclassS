show tables;

create table guest2 (
	idx  int not null auto_increment primary key,  /* 방명록 고유번호 */
	name varchar(20) not null,  /* 방명록 작성자 성명 */
	content text not null,   /* 방명록 글 내용 */
	email varchar(60),  /* 메일 주소 */
	homePage varchar(60),  /* 홈페이지 주소(블로그 주소) */
	visitDate datetime default now(),  /* 방문일자 */
	hostIp  varchar(30) not null  /* 방문자의 접속 IP getremoteaddress */
);

desc guest2;

insert into guest2 value (default, '관리자', '방명록 서비스를 시작합니다.', 'jhs2468@naver.com', 'www.naver.com', default, '192.168.50.57');

select * from guest2;

select count(df) as cnt from (select date_format(visitDate,  '%Y%m%d') as df from guest2 where name='atom1234' or name='아톰맨' or name='아톰' group by df) as groupBy;

select count(date_format(visitDate,  '%Y%m%d')) as cnt from guest2 where name='atom1234' or name='아톰맨' or name='아톰';

/* 업무설계 -> DB -> VO -> DAO */