alter table question
	add id bigint auto_increment;

alter table question
	add constraint question_pk
		primary key (id);