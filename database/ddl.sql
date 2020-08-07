alter table matricula add confirmacao date default current_date;
alter table matricula add situacao varchar(255) default 'Concluído';
alter table curso add numerovagas int default 0;
