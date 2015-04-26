--Script de criação das entidades
drop table if exists cliente;
drop table if exists funcionario;
drop table if exists usuario cascade;
drop table if exists chamado;
drop table if exists atendimento cascade;
drop table if exists item_pedido;
drop table if exists item cascade;
drop table if exists mesa cascade;
-- Usuario
create table public.usuario (
	id_usuario	serial not null,
	login	varchar(50) not null unique,
	senha	varchar(128) not null,
	cpf	varchar(11) not null unique,
	role	varchar(16) not null,
	constraint usuario_pk primary key(id_usuario)	
);

-- Funcionario
create table public.funcionario (
	id_funcionario	serial not null,
	id_usuario	integer not null,
	nome		varchar(50) not null,
	funcao		varchar(50) not null,
	constraint funcionario_pk primary key(id_funcionario),
	constraint funcionario_usuario_fk foreign key (id_usuario) references usuario (id_usuario)	
);

-- Cliente
create table public.cliente (
	id_cliente	serial not null,
	id_usuario	integer not null,
	nome		varchar(50) not null,
	endereco	varchar(100),
	telefone	varchar(20) not null,
	nascimento	Date,
	email		varchar(50),
	constraint cliente_pk primary key(id_cliente),
	constraint cliente_usuario_fk foreign key (id_usuario)	references usuario (id_usuario)	
);


select * from funcionario f join usuario u on f.id_usuario = u.id_usuario;


-- Mesa
create table public.mesa (
	id_mesa serial not null,
	ip_terminal varchar(15) not null unique,
	capacidade integer not null,
	estado_mesa varchar(8) not null,
	constraint mesa_pk primary key(id_mesa)
);

-- Item Menu
create table public.item (
	id_item serial not null,
	nome varchar(50) not null unique,
	descricao varchar(100),
	tipo_item varchar(10) not null,
	preco numeric(8,2) not null,
	dir_imagem varchar(100),
	constraint item_pk primary key(id_item)	
);


-- Atendimento
create table public.atendimento (
	id_atendimento 	serial not null,
	id_mesa		integer not null,
	finalizado	boolean not null default false,
	forma_pagamento	varchar(8),
	constraint atendimento_pk primary key(id_atendimento),
	constraint atendimento_mesa_fk foreign key (id_mesa) references mesa (id_mesa)
);

-- Item pedido
create table public.item_pedido (
	id_item_pedido	serial not null,
	id_item 	integer not null,
	id_atendimento	integer not null,
	quantidade 	integer not null,
	status_pedido	varchar(25) not null,
	constraint item_pedido_pk primary key(id_item_pedido),
	constraint item_pedido_item_fk foreign key (id_item) references item (id_item),
	constraint item_pedido_atendimento_fk foreign key (id_atendimento) references atendimento (id_atendimento)
);

-- Chamado (Atendente)
create table public.chamado (
	id_chamado	serial not null,
	id_mesa		integer not null,
	atendido	boolean not null default false,
	constraint chamado_pk primary key(id_chamado),
	constraint chamado_mesa_fk foreign key (id_mesa) references mesa (id_mesa)
);


-------------------------------------------- Populando o banco ------------------------------------------------------------
insert into usuario (login, senha, cpf, role) values ('admin', '123456','00000000000','ROLE_ADMIN');
insert into usuario (login, senha, cpf, role) values ('jonatas', '123456','37277853733','ROLE_ADMIN');
insert into usuario (login, senha, cpf, role) values ('juao', '123456','68327682733','ROLE_ADMIN');
insert into usuario (login, senha, cpf, role) values ('ronaldo', '123456','71873581483','ROLE_ADMIN');
insert into usuario (login, senha, cpf, role) values ('jacinto', '123456','38263230845','ROLE_CLIENTE');
insert into usuario (login, senha, cpf, role) values ('julio', '123456','35107231802','ROLE_CLIENTE');
insert into usuario (login, senha, cpf, role) values ('rodrigo', '123456','36178286201','ROLE_CLIENTE');
insert into usuario (login, senha, cpf, role) values ('jose', '123456','82172541842','ROLE_CLIENTE');
insert into usuario (login, senha, cpf, role) values ('luan', '123456','97258943595','ROLE_CLIENTE');
insert into usuario (login, senha, cpf, role) values ('aline', '123456','63165466963','ROLE_CLIENTE');
insert into usuario (login, senha, cpf, role) values ('paula', '123456','40338912428','ROLE_CLIENTE');
insert into usuario (login, senha, cpf, role) values ('gusmao', '123456','05121586817','ROLE_FUNCIONARIO');
insert into usuario (login, senha, cpf, role) values ('tina', '123456','59347652725','ROLE_FUNCIONARIO');
insert into usuario (login, senha, cpf, role) values ('debora', '123456','19450145040','ROLE_FUNCIONARIO');
insert into usuario (login, senha, cpf, role) values ('anita', '123456','87537324484','ROLE_FUNCIONARIO');

insert into funcionario (id_usuario, nome, funcao) values(1,'Administrador','COPA');
insert into funcionario (id_usuario, nome, funcao) values (2,'Jônatas Tonholo','COPA');
insert into funcionario (id_usuario, nome, funcao) values (3,'Juão','COPA');
insert into funcionario (id_usuario, nome, funcao) values (4,'Ronaldo','COPA');
insert into funcionario (id_usuario, nome, funcao) values (12,'Gusmão Santos Borges','COZINHA');
insert into funcionario (id_usuario, nome, funcao) values (13,'Tinta Tuner','COZINHA');
insert into funcionario (id_usuario, nome, funcao) values (14,'Debora Santos','COZINHA');
insert into funcionario (id_usuario, nome, funcao) values (15,'Anita Valadares','COZINHA');

insert into cliente (id_usuario, nome, endereco, telefone, nascimento, email) values (5,'Jacinto Aquino', 'Rua mimimi, 69','25668877', '25/03/1988', 'jacinto@lt.com');
insert into cliente (id_usuario, nome, endereco, telefone, nascimento, email) values (6,'Julio Gonçalves Soares', 'Rua blabla bla, 15','33558779', '02/09/1980', 'julio@aa.com');
insert into cliente (id_usuario, nome, endereco, telefone, nascimento, email) values (7,'Rodrigo Romualdo Soares','Rua pemba, 24','98556633', '21/06/1985', 'rodrigo@globo.com');
insert into cliente (id_usuario, nome, endereco, telefone, nascimento, email) values (8,'José de Arimateia', 'Rua ladeira, 66','85223666', '16/07/1989', 'jose@globo.com');
insert into cliente (id_usuario, nome, endereco, telefone, nascimento, email) values (9,'Luan Pereira Borges', 'Rua dkasjld, 999','98774566', '23/04/1968', 'julio@globo.com');
insert into cliente (id_usuario, nome, endereco, telefone, nascimento, email) values (10,'Aline Pinto Soares', 'Rua do sabão, 666','77445588', '02/12/1986', 'aline@globo.com');
insert into cliente (id_usuario, nome, endereco, telefone, nascimento, email) values (11,'Paula Silva', 'Rua ladeira, 667','9988556', '02/08/1988', 'paula@globo.com');

insert into mesa (ip_terminal, capacidade, estado_mesa) values('127.0.0.1',8,'LIVRE');
insert into mesa (ip_terminal, capacidade, estado_mesa) values('192.168.0.2',4,'LIVRE');
insert into mesa (ip_terminal, capacidade, estado_mesa) values('192.168.0.3',4,'LIVRE');
insert into mesa (ip_terminal, capacidade, estado_mesa) values('192.168.0.4',12,'LIVRE');
insert into mesa (ip_terminal, capacidade, estado_mesa) values('192.168.0.5',8,'LIVRE');
insert into mesa (ip_terminal, capacidade, estado_mesa) values('192.168.0.6',6,'LIVRE');
insert into mesa (ip_terminal, capacidade, estado_mesa) values('192.168.0.7',4,'LIVRE');

insert into item (nome, descricao, tipo_item, preco, dir_imagem) values('Coca-Cola', 'Lata', 'BEBIDAS',3.50, '/public/item/images/coca.jpg');
insert into item (nome, descricao, tipo_item, preco, dir_imagem) values('Sprite', 'Lata', 'BEBIDAS',2.50, '/public/item/images/sprite.jpg');

--------------------------------------------------------------------------------------------------------------------------
/*
---------------------- Desc --------------------------------------------------
select column_name, 
	data_type,
	character_maximum_length, 
	column_default,
	is_nullable	
	from information_schema.columns where table_name = 'cliente';
 --Desc table
------------------------------------------------------------------------------
*/
---------------------- Selects --------------------------------
select * from usuario;
select * from funcionario;
select * from cliente;
select * from mesa;
select * from item;
select * from atendimento;
select * from item_pedido;
select * from chamado;


--select * from usuario u left join  c on u.id_usuario = c.id_usuario where u.id_usuario = 45;

--select * from usuario u where u.cpf = '58768535937';
/*
select count(*) from usuario u left join funcionario f on u.id_usuario = f.id_usuario
				left join cliente c on u.id_usuario = c.id_usuario
			where u.id_usuario = 15
				AND (f.id_usuario is not null OR c.id_usuario is not null);



select MAX(count) from (
	select count(c) from cliente c 
		right outer join usuario u on c.id_usuario=u.id_usuario 
		where u.id_usuario=11 and (c.id_cliente is not null)
Union all
	select count(*) from funcionario f 
		right outer join usuario us on f.id_usuario=us.id_usuario 
		where us.id_usuario=11 and (f.id_funcionario is not null)
	
) as un;
*/
--select nextval ('hibernate_sequence');
---------------------------------------------------------------