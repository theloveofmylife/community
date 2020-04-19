alter table QUESTION
	add id int auto_increment;

alter table QUESTION
	add constraint QUESTION_pk
		primary key (id);