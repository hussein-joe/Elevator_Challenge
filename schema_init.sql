CREATE DATABASE [elevator_challenge];
Go
use [elevator_challenge];

create table Elevator (
        	[id] [bigint] IDENTITY(1,1) NOT NULL,
			[elevator_code] [varchar](255) NOT NULL,
			[elevator_name] [varchar](255) NOT NULL,
			[elevator_status] [varchar](255) NOT NULL,
			[max_capacity] [int] NOT NULL,
			[OPTLOCK_VERSION] [int] NULL,
			primary key (id)
);
GO

CREATE TABLE [dbo].[Building_Level](
	[id] [bigint] IDENTITY(1,1) NOT NULL,
	[level_number] [int] NOT NULL,
	[name] [varchar](255) NOT NULL,
	[OPTLOCK_VERSION] [int] NULL,
	primary key (id)
);

GO
CREATE TABLE [dbo].[ElevatorMovement](
	[id] [bigint] IDENTITY(1,1) NOT NULL,
	[movementTime] [datetime2](7) NULL,
	[passengers] [int] NOT NULL,
	[OPTLOCK_VERSION] [int] NULL,
	[elevator_id] [bigint] NOT NULL,
	[fromLevel_id] [bigint] NOT NULL,
	[toLevel_id] [bigint] NOT NULL,
	primary key (id)
);

GO
alter table Elevator add constraint UK_dpi3vveeefv7eqpt28k37etnp unique (elevator_code);

alter table ElevatorMovement add constraint FK67s7wu99xm0ulkiwnfhp39x00 foreign key (elevator_id) references Elevator;

alter table ElevatorMovement add constraint FKcermuot7umi0dqjeouc6cai2a foreign key (fromLevel_id)  references Building_Level;

alter table ElevatorMovement add constraint FK2haojagaaiiybwxbf7kxirwdq foreign key (toLevel_id)  references Building_Level;

alter table Building_Level add constraint UK_om74ppjmufu4c7s8ggwbcvbcr unique (level_number);



insert into Elevator(elevator_code, elevator_name, max_capacity, elevator_status, OPTLOCK_VERSION) values('A', 'Elevator A', 20, 'ACTIVE', 0);
insert into Elevator(elevator_code, elevator_name, max_capacity, elevator_status, OPTLOCK_VERSION) values('B', 'Elevator B', 20, 'ACTIVE', 0);
insert into Elevator(elevator_code, elevator_name, max_capacity, elevator_status, OPTLOCK_VERSION) values('C', 'Elevator C', 20, 'ACTIVE', 0);
insert into Elevator(elevator_code, elevator_name, max_capacity, elevator_status, OPTLOCK_VERSION) values('D', 'Elevator D', 20, 'ACTIVE', 0);

insert into Building_Level(level_number, name, optlock_version) values (1, '1', 1);
insert into Building_Level(level_number, name, optlock_version) values (2, '2', 1);
insert into Building_Level(level_number, name, optlock_version) values (3, '3', 1);
insert into Building_Level(level_number, name, optlock_version) values (4, '4', 1);
insert into Building_Level(level_number, name, optlock_version) values (5, '5', 1);
insert into Building_Level(level_number, name, optlock_version) values (6, '6', 1);
insert into Building_Level(level_number, name, optlock_version) values (7, '7', 1);
insert into Building_Level(level_number, name, optlock_version) values (8, '8', 1);
insert into Building_Level(level_number, name, optlock_version) values (9, '9', 1);
insert into Building_Level(level_number, name, optlock_version) values (10, '10', 1);
