/* 투표자 기본 인적사항 */
create table votePersonInfor(
  idx    int  not null primary key,
  gender char(2) not null,				/* 성별 */
  age    varchar(5) not null,			/* 나이대 */
  job		 varchar(10) not null,		/* 직업 */
  area   varchar(10) not null		  /* 지역  */
);
drop table votePersonInfor;
select max(idx) from votePersonInfor;
select * from votePersonInfor;

/* 투표지 내용 테이블 */
create table votePopular (
  idx      int          not null  auto_increment primary key,
  part     varchar(10)  not null,	/* 분류 */
  title    varchar(100) not null,	/* 설문주제(그림주제) 설명 */
  subTitle varchar(200) not null, /* 개별 그림 설명 */
  realImg  varchar(300) not null, /* 실제 서버에 저장되는 파일명 */
  thumbImg varchar(300) not null, /* 썸네일 */
  progress char(3) default 'ON'		/* 진행상태(ON/OFF) */
);
/* 추가로 들어갈 내용 : 날짜(언제부터 언제까지) */
drop table votePopular;
delete from votePopular where idx=2;
select * from votePopular;
alter table votePopular auto_increment 6;

/* 투표지 */
create table vote (
  idx  int  not null auto_increment primary key,
  personInforIdx int not null,				/* 사람정보테이블의 고유번호 */
  popularIdx int not null,						/* 설문지 고유번호 */
  voteNum    int not null,						/* 설문지에 투표한 항목번호 */
  foreign key (personInforIdx) references votePersonInfor(idx), /* 외래키로 personInforIdx 설정 */
  foreign key (popularIdx) references votePopular(idx) on update cascade on delete restrict /* 외래키로 popularIdx를 설정했고, popular테이블의 idx가 변경되면 함께 영향을 받고(cascade), 현재 레코드가 있다면 원본테이블의 idx는 삭제될 수 없다(restrict). */
);
drop table vote;
insert into vote value (default,1,1,3);
select * from vote;
select * from vote where popularIdx=1;
select voteNum, count(voteNum) from vote where popularIdx=1 group by voteNum;
select a.voteNum, count(a.voteNum) as voteSum, b.subTitle from vote a, votePopular b where a.popularIdx=2 group by a.voteNum;
select a.voteNum, count(a.voteNum) as voteSum, b.subTitle from vote a, votePopular b where a.popularIdx=2 group by a.voteNum;
select voteNum, count(voteNum) as voteSum, popularIdx,
  (select title from votePopular where idx=2) as title, 
  (select subTitle from votePopular where idx=2) as subTitle
  from vote where popularIdx=2 group by voteNum;

/* 2번항목을 투표한 사람들? */
select popularIdx,personInforIdx,
  (select gender from votepersonInfor where idx=vote.personInforIdx) as gender 
  from vote where popularIdx=2;
  
/* 2번항목을 투표한 사람들?(중복배제) */
select popularIdx,personInforIdx,
  (select gender from votepersonInfor where idx=vote.personInforIdx) as gender 
  from vote where popularIdx=2 group by personInforIdx;
  
/* 2번항목을 투표한 사람들?(중복배제) - 이중에서 남자와 여자의 인원수??? */
select popularIdx, (select title from votePopular where idx=2) as title, gender, count(gender) as voteSum
  from (select popularIdx,personInforIdx,
  (select gender from votePersonInfor where idx=vote.personInforIdx) as gender 
  from vote where popularIdx=2 group by personInforIdx) imsi group by imsi.gender;
  
/* 2번항목을 투표한 사람들?(중복배제) - 이중에서 지역별 투표 인원수??? */
select popularIdx, (select title from votePopular where idx=2) as title, area, count(area) as voteSum
  from (select popularIdx,personInforIdx,
  (select area from votePersonInfor where idx=vote.personInforIdx) as area 
  from vote where popularIdx=2 group by personInforIdx) imsi group by imsi.area;
  
/* 2번항목을 투표한 사람들?(중복배제) - 이중에서 연령별 투표 인원수??? */
select popularIdx, (select title from votePopular where idx=2) as title, age, count(age) as voteSum
  from (select popularIdx,personInforIdx,
  (select age from votePersonInfor where idx=vote.personInforIdx) as age 
  from vote where popularIdx=2 group by personInforIdx) imsi group by imsi.age;
  
/* 2번항목을 투표한 사람들?(중복배제) - 이중에서 지역별 투표 인원수??? */
select popularIdx,personInforIdx,(select area from votePersonInfor where idx=vote.personInforIdx) as area, voteNum, count(*)
  from vote where popularIdx=2 and voteNum=1 group by area;
select popularIdx,personInforIdx,(select area from votePersonInfor where idx=vote.personInforIdx) as area, voteNum, count(*)
  from vote where popularIdx=2 and voteNum=2 group by area;
select popularIdx,personInforIdx,(select area from votePersonInfor where idx=vote.personInforIdx) as area, voteNum, count(*)
  from vote where popularIdx=2 and voteNum=3 group by area;
select popularIdx,personInforIdx,(select area from votePersonInfor where idx=vote.personInforIdx) as area, voteNum, count(*)
  from vote where popularIdx=2 and voteNum=4 group by area;
select popularIdx,personInforIdx,(select area from votePersonInfor where idx=vote.personInforIdx) as area, voteNum, count(*)
  from vote where popularIdx=2 and voteNum=5 group by area;
  
/* 2번항목을 투표한 사람들?(중복배제) - 이중에서 지역별 투표 인원수??? */
select popularIdx, (select title from votePopular where idx=2) as title, job, count(job) as voteSum
  from (select popularIdx,personInforIdx,
  (select job from votePersonInfor where idx=vote.personInforIdx) as job
  from vote where popularIdx=2 group by personInforIdx) imsi group by imsi.job;

/* 2번항목을 투표한 사람들?(중복배제) - 이중에서 직업별 투표 인원수??? */
select popularIdx,personInforIdx,(select job from votePersonInfor where idx=vote.personInforIdx) as job, voteNum, count(*) as voteSum
  		from vote where popularIdx=2 and voteNum=1 group by job;