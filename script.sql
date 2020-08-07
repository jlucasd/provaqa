START TRANSACTION;

-- Necessário criar uma base de nome "prova" primeiro.

CREATE SEQUENCE public.hibernate_sequence
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;
ALTER TABLE public.hibernate_sequence
  OWNER TO postgres;

CREATE TABLE public.unidade
(
  id bigint NOT NULL,
  nome character varying(255),
  CONSTRAINT unidade_pkey PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.unidade
  OWNER TO postgres;

alter table unidade add ativa boolean default true;

CREATE TABLE public.curso
(
  id bigint NOT NULL,
  inicio timestamp without time zone,
  nome character varying(55) NOT NULL,
  termino timestamp without time zone,
  idunidade bigint,
  CONSTRAINT curso_pkey PRIMARY KEY (id),
  CONSTRAINT fk4bdea9bgbickjnvfftdlfw4rd FOREIGN KEY (idunidade)
      REFERENCES public.unidade (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.curso
  OWNER TO postgres;

CREATE TABLE public.aluno
(
  id bigint NOT NULL,
  cpf character varying(255) NOT NULL,
  email character varying(255),
  nascimento character varying(255),
  nome character varying(25) NOT NULL,
  telefone character varying(255),
  CONSTRAINT aluno_pkey PRIMARY KEY (id),
  CONSTRAINT ukg6otv1ccqwf8a15re4tc1sr9q UNIQUE (cpf)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.aluno
  OWNER TO postgres;

CREATE TABLE public.matricula
(
  id bigint NOT NULL,
  idaluno bigint,
  idcurso bigint,
  CONSTRAINT matricula_pkey PRIMARY KEY (id),
  CONSTRAINT fk5bdl53ub529x1bcl87sw9phax FOREIGN KEY (idcurso)
      REFERENCES public.curso (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk6ckq1hb0w11nn743kigefvo9 FOREIGN KEY (idaluno)
      REFERENCES public.aluno (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.matricula
  OWNER TO postgres;

INSERT INTO unidade (id, nome) VALUES (1, 'SENAI Tubarão');
INSERT INTO unidade (id, nome) VALUES (2, 'SENAI Capivari de Baixo');
INSERT INTO unidade (id, nome) VALUES (3, 'SESI Tubarão');

INSERT INTO aluno (id, cpf, email, nascimento, nome, telefone) VALUES (4, '12345678901', 'homer@email.com',   TO_DATE('01/02/1989','DD/MM/YYYY'), 'Homer Simpson',      '48548956825');
INSERT INTO aluno (id, cpf, email, nascimento, nome, telefone) VALUES (5, '58479658412', 'willian@email.com', TO_DATE('02/04/1985','DD/MM/YYYY'), 'Willian Smith',      '48859458541');
INSERT INTO aluno (id, cpf, email, nascimento, nome, telefone) VALUES (6, '12548954623', 'norman@email.com',  TO_DATE('09/07/1958','DD/MM/YYYY'), 'Norman Bates',       '48958746663');
INSERT INTO aluno (id, cpf, email, nascimento, nome, telefone) VALUES (7, '55556458238', 'norma@email.com',   TO_DATE('05/12/1984','DD/MM/YYYY'), 'Norma Bates',        '48932548789');
INSERT INTO aluno (id, cpf, email, nascimento, nome, telefone) VALUES (8, '75154369461', 'sheldon@email.com', TO_DATE('08/04/1952','DD/MM/YYYY'), 'Sheldon Cooper',     '48985478963');
INSERT INTO aluno (id, cpf, email, nascimento, nome, telefone) VALUES (9, '56489453189', 'leonard@email.com', TO_DATE('01/11/1983','DD/MM/YYYY'), 'Leonard Hofstadter', '48912548966');
INSERT INTO aluno (id, cpf, email, nascimento, nome, telefone) VALUES (10, '48945623145', 'chris@email.com',  TO_DATE('22/06/1986','DD/MM/YYYY'), 'Chris Rock',         '48925448965');
INSERT INTO aluno (id, cpf, email, nascimento, nome, telefone) VALUES (11, '34894332138', 'harper@email.com', TO_DATE('03/03/1945','DD/MM/YYYY'), 'Charlie Harper',     '48936521488');
INSERT INTO aluno (id, cpf, email, nascimento, nome, telefone) VALUES (12, '99151231643', 'eric@email.com',   TO_DATE('13/06/1998','DD/MM/YYYY'), 'Eric Cartman',       '48995888545');
INSERT INTO aluno (id, cpf, email, nascimento, nome, telefone) VALUES (13, '83215185324', 'peter@email.com',  TO_DATE('11/11/1994','DD/MM/YYYY'), 'Peter Griffin',      '48996658751');

INSERT INTO curso (id, nome, idunidade, inicio, termino) VALUES (14, 'Técnico em Manutenção',                  1, TO_DATE('24/09/2016','DD/MM/YYYY'), TO_DATE('20/06/2017','DD/MM/YYYY'));
INSERT INTO curso (id, nome, idunidade, inicio, termino) VALUES (15, 'Técnico em Eletricidade',                2, TO_DATE('20/05/2017','DD/MM/YYYY'), TO_DATE('18/05/2018','DD/MM/YYYY'));
INSERT INTO curso (id, nome, idunidade, inicio, termino) VALUES (16, 'Técnico em Informátiva',                 1, TO_DATE('01/03/2017','DD/MM/YYYY'), TO_DATE('27/10/2018','DD/MM/YYYY'));
INSERT INTO curso (id, nome, idunidade, inicio, termino) VALUES (17, 'Técnico em Automação industrial',        1, TO_DATE('25/09/2017','DD/MM/YYYY'), TO_DATE('12/12/2018','DD/MM/YYYY'));
INSERT INTO curso (id, nome, idunidade, inicio, termino) VALUES (18, 'Técnico em Metrologia',                  3, TO_DATE('01/11/2017','DD/MM/YYYY'), TO_DATE('12/12/2018','DD/MM/YYYY'));
INSERT INTO curso (id, nome, idunidade, inicio, termino) VALUES (19, 'Técnico em Panificação',                 1, TO_DATE('25/09/2016','DD/MM/YYYY'), TO_DATE('30/05/2017','DD/MM/YYYY'));
INSERT INTO curso (id, nome, idunidade, inicio, termino) VALUES (20, 'Superior em Mecânica de Automóveis',     2, TO_DATE('02/11/2017','DD/MM/YYYY'), TO_DATE('30/12/2019','DD/MM/YYYY'));
INSERT INTO curso (id, nome, idunidade, inicio, termino) VALUES (21, 'Aprendizagem Industrial em Confeitaria', 3, TO_DATE('01/06/2017','DD/MM/YYYY'), TO_DATE('30/09/2017','DD/MM/YYYY'));


INSERT INTO matricula (id, idaluno, idcurso) VALUES (22, 4, 16);
INSERT INTO matricula (id, idaluno, idcurso) VALUES (23, 9, 18);
INSERT INTO matricula (id, idaluno, idcurso) VALUES (24, 13, 14);

ALTER SEQUENCE public.hibernate_sequence RESTART WITH 25;

COMMIT;
