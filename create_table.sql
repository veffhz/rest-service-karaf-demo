CREATE DATABASE test
GO

CREATE TABLE test.dbo.persons (
	id int IDENTITY(0,1) NOT NULL,
	name varchar(100) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	CONSTRAINT persons_PK PRIMARY KEY (id)
) GO
