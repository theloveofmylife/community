alter table question
	add id bigint auto_increment,
	add constraint question_pk
		primary key (id);