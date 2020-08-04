alter table matricula add confirmacao date default current_date;
alter table unidade add ativa boolean default true;
alter table curso add numerovagas int default 0;
alter table matricula add situacao varchar(255) default 'Concluído';