alter table QUESTION
	add id bigint auto_increment;

alter table QUESTION
	add constraint QUESTION_pk
		primary key (id);