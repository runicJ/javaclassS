show tables;

create table webMessage(
	idx  int not null auto_increment,  /* 메시지 고유번호 */
	title  varchar(100) not null,  /* 메시지 제목 */
	content  text not null,  /* 메시지 내용 */
	sendId  varchar(20) not null,  /* 보내는 사람 아이디 */
	sendSw  char(1) not null default 's',  /* 보낸메시지(s), 휴지통(g), 휴지통삭제(x) 표시 / 원래 default s로 주면 되는데 프로그램으로 규칙 설정 */
	sendDate  datetime default now(),  /* 메시지 보낸날짜 */
	receiveId  varchar(20) not null,  /* 받는 사람 아이디 */
	receiveSw  char(1) not null default 'n',  /* 받은메시지(n) - 읽지않음, 읽은메시지(r), 휴지통(g), 휴지통삭제(x) 표시 / 같은 테이블이므로 둘다 x여야 완전히 db에서 삭제 */
	receiveDate  datetime default now(),  /* 메시지 받은날짜/읽은날짜 */
	primary key(idx),
	foreign key(sendId) references member2(mid),
	foreign key(receiveId) references member2(mid)
);

desc webMessage;
drop table webMessage;

ALTER TABLE webMessage AUTO_INCREMENT = 1;

insert into webMessage values(default,'안녕! 앨리스야~~','이번주 주말에 뭐하니?','hkd1234',default,default,'aris1234','r',default);
insert into webMessage values(default,'re: 반갑다 길동아~~','이번주 주말에는 프로젝트 작업으로 바쁨','aris1234',default,default,'hkd1234','r',default);
insert into webMessage values(default,'물어볼게 있는데!','다음에 프로젝트 끝나고 만나자','hkd1234',default,default,'aris1234',default,default);
insert into webMessage values(default,'OK!!','끝나고 편할떄 보자','aris1234',default,default,'hkd1234','r',default);
insert into webMessage values(default,'그래 다음에 보자..','바쁘지 다음에 연락할게','hkd1234',default,default,'aris1234',default,default);
insert into webMessage values(default,'안녕! 소소쓰~~','이번주 주말에 뭐하냐???','hkd1234',default,default,'soso1234',default,default);

delete from webMessage;
select * from webMessage;

select *, timestampdiff(hour, sendDate, now()) as hour_diff from webMessage  /* 보낸사람의 보낸날짜(받는사람이 보는 것) */
where receiveId='aris1234' and (receiveSw='n' or receiveSw='r') order by idx desc;  /* 페이징 처리하면 limit까지 */

select *,timestampdiff(hour, sendDate, now()) as hour_diff from webMessage where receiveId='hkd1234' and (receiveSw='n' or receiveSw='r') order by idx desc limit 0,5;