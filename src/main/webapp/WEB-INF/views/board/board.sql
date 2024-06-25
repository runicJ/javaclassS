show tables;

create table board2 (
	idx  int not null auto_increment primary key,  /* 게시글의 고유번호 */
	mid  varchar(20) not null,  /* 게시글 작성자 아이디(게시글에는 닉네임이 올라가지만, 닉네임을 바꿀 경우 식별을 아이디로 함) */
	nickName  varchar(20) not null,  /* 게시글 작성자 닉네임 */
	title  varchar(100) not null,  /* 게시글 제목 */
	content  text not null,  /* 글 내용 */
	readNum  int default 0,  /* 글 조회수 */
	hostIp  varchar(40) not null,  /* 글 작성자 IP */
	openSw  char(2) default 'OK',  /* 게시글 공개 여부(OK:공개, NO:비공개) */
	wDate  datetime default now(),  /* 게시글 작성일 */
	good  int default 0,  /* '종아요' 클릭 횟수 누적 */
	complaint char(2) default 'NO',  /* 신고글 유무(신고중:OK, 정상글:NO) alter table add column */
	foreign key(mid) references member3(mid)  /* 변경(restrict) */
);

desc board2;
drop table board2;

insert into board2 values (default,'admin','관리맨','게시판 서비스를 시작합니다.','즐거운 게시판 생활이 되세요.',default,'192.168.50.57',default,default,default,default);

/* 댓글 달기 */
create table boardReply2 (
	idx  int not null auto_increment,  /* 댓글 고유번호 */
	boardIdx  int not null,  /* 원본글(부모글)의 고유번호 - 외래키로 지정(함부로 부모글에서 삭제 못하도록) */
	mid  varchar(20) not null,  /* 댓글 올린이의 아이디 */
	nickName varchar(20) not null, /* 댓글 올린이의 닉네임(로그인한 사용자의 session 닉네임) */
	wDate datetime default now(),  /* 댓글 올린 날짜/시간 */
	hostIp varchar(50) not null,  /* 댓글 올린 PC의 고유 IP */
	content text not null,  /* 댓글 내용 */
	primary key(idx),
	foreign key(boardIdx) references board2(idx)  /* 부모테이블은 board */
	on update cascade  /* 부모가 수정되면 같이 수정되도록 */
	on delete restrict  /* 무조건 자식글을 먼저 지우고 부모글을 지워야 함 */
);
desc boardReply2;

insert into boardReply2 values (default,21,'aris1234','앨리스',default,'192.168.50.57','환영합니다');
insert into boardReply2 values (default,19,'aris1234','앨리스',default,'192.168.50.57','반갑습니다');
insert into boardReply2 values (default,1,'aris1234','앨리스',default,'192.168.50.57','안녕하세요');

select * from boardReply2;

select * from board2;
select * from board2 where idx = 9;  /* 현재글 - idx,title content에서 이 변수가 이미 사용중 같으면 사용할 수 없음 => 이전글 다음글 같은 페이지 */
select idx,title from board where idx > 9 order by idx limit 1;  /* 다음글 // 다음글이 꼭 10번일거란 보장이 없으므로 9번보다 크고 정렬해서 limit 함 */
select idx,title from board where idx < 9 order by idx desc limit 1;  /* 이전글 - idx랑 title 가져감 */

ALTER TABLE board AUTO_INCREMENT = 1;

-- 시간으로 비교해서 필드에 값 저장하기
select *, timestampdiff(hour, wDate, now()) as hour_diff from board;  /* 옵션, 비교1, 비교데이터2 / 2-1 => 사용하려면 DAO 어디서 나오는지 이건 게시글리스트 */

-- 날짜로 비교해서 필드에 값 저장하기 date_format(visitDate,  '%Y%m%d')
select *, datediff(wDate, now()) as hour_diff, date_format(wDate, '%Y-%m-%d %H:%i:%s') from board;  /* 날짜만 비교 datediff(날짜를 비교하겠다고 선언했기 때문에 옵션이 필요없음) => - 나오면 지났다는 것 */

--<marquee behavior="alternate" scrollamount="1000">깜박깜박</marquee>

-- 관리자는 모든글 보여주고, 일반사용자는 비공개글(openSw='NO')과 신고글(complaint='OK')은 볼수없다. 단, 자신이 작성한 글은 볼수 있다.
select count(*) as cnt from board;
select count(*) as cnt from board where openSW = 'OK' and complaint = 'NO';
select count(*) as cnt from board where mid = 'hkd1234';

select * from board2 where openSW = 'OK' and complaint = 'NO';
select * from board2 where mid = 'hkd1234';
select * from board2 where openSW = 'OK' and complaint = 'NO' union select * from board where mid = 'hkd1234';  /* union은 중복된 것 제외 */
select * from board2 where openSW = 'OK' and complaint = 'NO' union all select * from board where mid = 'hkd1234';  /* 중복된 것도 보려면 union all */

select count(*) as cnt from board2 where openSW = 'OK' and complaint = 'NO' union select count(*) as cnt from board2 where mid = 'hkd1234';  /* 중복되어서 각각 카운트 되서 나옴 뒤에 union 더 있으면 더 나옴 */
select sum(a.cnt) from (select count(*) as cnt from board2 where openSW = 'OK' and complaint = 'NO' union select count(*) as cnt from board2 where mid = 'hkd1234') as a;  /* 각각 count를 더해라 */
select sum(a.cnt) from (select count(*) as cnt from board2 where openSW = 'OK' and complaint = 'NO' union select count(*) as cnt from board2 where mid = ?) as a;

select sum(a.cnt) from (select count(*) as cnt from board2 where openSW = 'OK' and complaint = 'NO' union select count(*) as cnt from board2 where mid = 'hkd1234' and (openSW = 'NO' or complaint = 'OK')) as a;

select * from board2 where openSW = 'OK' and complaint = 'NO' union select * from board2 where mid = 'hkd1234' order by idx desc;

/* 댓글 수 연습 */
select * from board2 order by idx desc;  /* 최신글 정렬 */
select * from board2 order by boardIdx, idx desc;

-- 부모글(18)의 댓글만 출력
select * from boardReply2 where boardIdx = 18;
select boardIdx,count(*) as replyCnt from boardReply2 where boardIdx = 18;  /* 자식글 관점에서 본 것 */

select * from board where idx=18;
--select b.*,count(*) as replyCnt from board2 b, boardReply bR where b.idx = bR.boardIdx;
select *,(select count(*) from boardReply2 where boardIdx = 18) as replyCnt from board2 where idx = 18;  /* boardList에 출력하는 것이니 BoardVO에 입력 */
select *,(select count(*) from boardReply2 where boardIdx = b.idx) as replyCnt from board2 b where idx = 18;

/* view / index 파일 연습 */
select * from board2 where mid='admin';

create view adminView as select * from board2 where mid='admin';  /* view 생성 */

select * from adminview;

show tables;

show full tables in javaclass where table_type like 'view';  /* view 확인하기 */

drop view adminview;  /* view 삭제 */

desc board2;

create index nickNameIndex on board2(nickName);  /* board2 테이블의 nickName을 index 키로 넣음 */

show index from board2;  /* board2 테이블의 index 키 확인 */

-- 키에 대한 작업은 전부 alter 사용
alter table board2 drop index nickNameIndex;  /* nickName index 키 제거 */

select * from hoewon;

select mid as 아이디, name as 이름, gender as 성별 from hoewon limit 4,5;
select * from hoewon where name like '%홍%';

select mid as 아이디, gender as 성별, ipsail as 입사일 
from hoewon
where ipsail >= '2000-01-01';