-- This CLP file was created using DB2LOOK Version "9.7" 

-- Timestamp: 04-04-2013 10.46.09 PM

-- Database Name: HROPS          

-- Database Manager Version: DB2/NT Version 9.7.4          

-- Database Codepage: 1208

-- Database Collating Sequence is: IDENTITY


CREATE DATABASE HROPS;

CONNECT TO HROPS;


--GRANT DBADM,CREATETAB,BINDADD,CONNECT,CREATE_NOT_FENCED_ROUTINE,IMPLICIT_SCHEMA,LOAD,CREATE_EXTERNAL_ROUTINE,QUIESCE_CONNECT,SECADM ON DATABASE TO USER DB2ADMIN;




------------------------------------------------

-- DDL Statements for table "HROPS_SCHEMA"."POSITION"

------------------------------------------------

 



CREATE TABLE "HROPS_SCHEMA"."POSITION"  (

		  "POSITION_ID" INTEGER NOT NULL, 

		  "POS_DESC" VARCHAR(30) NOT NULL )   

		 IN "USERSPACE1" ; 





-- DDL Statements for primary key on Table "HROPS_SCHEMA"."POSITION"



ALTER TABLE "HROPS_SCHEMA"."POSITION" 

	ADD CONSTRAINT "POSITION_POSITION_ID_PK" PRIMARY KEY

		("POSITION_ID");










------------------------------------------------

-- DDL Statements for table "HROPS_SCHEMA"."QUALIFICATION"

------------------------------------------------

 



CREATE TABLE "HROPS_SCHEMA"."QUALIFICATION"  (

		  "QUAL_ID" INTEGER NOT NULL , 

		  "QUAL_DESC" VARCHAR(30) NOT NULL )   

		 IN "USERSPACE1" ; 





-- DDL Statements for primary key on Table "HROPS_SCHEMA"."QUALIFICATION"



ALTER TABLE "HROPS_SCHEMA"."QUALIFICATION" 

	ADD CONSTRAINT "QUALIFICATION_QUAL_ID_PK" PRIMARY KEY

		("QUAL_ID");










------------------------------------------------

-- DDL Statements for table "HROPS_SCHEMA"."JOB_OPPORTUNITY"

------------------------------------------------

 



CREATE TABLE "HROPS_SCHEMA"."JOB_OPPORTUNITY"  (

		  "OPPORTUNITY_ID" VARCHAR(20) NOT NULL , 

		  "DEPARTMENT_ID" INTEGER NOT NULL , 

		  "POSITION_ID" INTEGER NOT NULL , 

		  "NO_OF_VACANCIES" INTEGER NOT NULL , 

		  "LAST_DATE" DATE NOT NULL )   

		 IN "USERSPACE1" ; 





-- DDL Statements for primary key on Table "HROPS_SCHEMA"."JOB_OPPORTUNITY"



ALTER TABLE "HROPS_SCHEMA"."JOB_OPPORTUNITY" 

	ADD CONSTRAINT "JOB_OPPORTUNITY_OPPORTUNITY_ID_PK" PRIMARY KEY

		("OPPORTUNITY_ID");







------------------------------------------------

-- DDL Statements for table "HROPS_SCHEMA"."APPLICATION"

------------------------------------------------

 



CREATE TABLE "HROPS_SCHEMA"."APPLICATION"  (

		  "APPLICATION_ID" INTEGER NOT NULL , 

		  "EMAIL" VARCHAR(50) NOT NULL , 

		  "OPPORTUNITY_ID" VARCHAR(20) NOT NULL , 

		  "APPLICATION_DATE" DATE NOT NULL , 

		  "RESUME_LOCATION" VARCHAR(260) NOT NULL , 

		  "STATUS" INTEGER NOT NULL , 

		  "OFFER_ID" VARCHAR(10) , 

		  "INTERVIEWER" INTEGER , 

		  "INTERVIEW_DATE" DATE , 

		  "INTERVIEW_TIME" TIME , 

		  "RESULT" INTEGER , 

		  "REASON" VARCHAR(260) )   

		 IN "USERSPACE1" ; 





-- DDL Statements for primary key on Table "HROPS_SCHEMA"."APPLICATION"



ALTER TABLE "HROPS_SCHEMA"."APPLICATION" 

	ADD CONSTRAINT "APPLICATION_APPLICATION_ID_PK" PRIMARY KEY

		("APPLICATION_ID");











------------------------------------------------

-- DDL Statements for table "HROPS_SCHEMA"."DEPARTMENT"

------------------------------------------------

 



CREATE TABLE "HROPS_SCHEMA"."DEPARTMENT"  (

		  "DEPT_ID" INTEGER NOT NULL , 

		  "DEPT_NAME" VARCHAR(30) NOT NULL , 

		  "DEPT_HEAD" INTEGER )   

		 IN "USERSPACE1" ; 





-- DDL Statements for primary key on Table "HROPS_SCHEMA"."DEPARTMENT"



ALTER TABLE "HROPS_SCHEMA"."DEPARTMENT" 

	ADD CONSTRAINT "DEPARTMENT_DEPT_ID_PK" PRIMARY KEY

		("DEPT_ID");










------------------------------------------------

-- DDL Statements for table "HROPS_SCHEMA"."EMPLOYEE"

------------------------------------------------

 



CREATE TABLE "HROPS_SCHEMA"."EMPLOYEE"  (

		  "EMPLOYEE_ID" INTEGER NOT NULL , 

		  "PASSWORD" VARCHAR(50) NOT NULL , 

		  "POSITION_ID" INTEGER NOT NULL , 

		  "SUPERVISOR" INTEGER , 

		  "HIREDATE" DATE NOT NULL , 

		  "SALARY" INTEGER , 

		  "DEPT_ID" INTEGER NOT NULL , 

		  "QUAL_ID" INTEGER , 

		  "GENDER" CHAR(1) , 

		  "NAME" VARCHAR(40) , 

		  "ROLE" VARCHAR(20) )   

		 IN "USERSPACE1" ; 





-- DDL Statements for primary key on Table "HROPS_SCHEMA"."EMPLOYEE"



ALTER TABLE "HROPS_SCHEMA"."EMPLOYEE" 

	ADD CONSTRAINT "EMPLOYEE_EMPLOYEE_ID_PK" PRIMARY KEY

		("EMPLOYEE_ID");











------------------------------------------------

-- DDL Statements for table "HROPS_SCHEMA"."ATTENDANCE"

------------------------------------------------

 



CREATE TABLE "HROPS_SCHEMA"."ATTENDANCE"  (

		  "ID" INTEGER NOT NULL , 

		  "EMPLOYEE_ID" INTEGER NOT NULL , 

		  "IN_TIME" TIMESTAMP , 

		  "OUT_TIME" TIMESTAMP , 

		  "STATE" SMALLINT NOT NULL )   

		 IN "USERSPACE1" ; 





-- DDL Statements for primary key on Table "HROPS_SCHEMA"."ATTENDANCE"



ALTER TABLE "HROPS_SCHEMA"."ATTENDANCE" 

	ADD CONSTRAINT "ATTENDANCE_ID_PK" PRIMARY KEY

		("ID");











------------------------------------------------

-- DDL Statements for table "HROPS_SCHEMA"."LEAVE"

------------------------------------------------

 



CREATE TABLE "HROPS_SCHEMA"."LEAVE"  (

		  "LEAVE_ID" INTEGER NOT NULL , 

		  "EMPLOYEE_ID" INTEGER NOT NULL , 

		  "FROM_DATE" DATE NOT NULL , 

		  "TO_DATE" DATE NOT NULL , 

		  "REASON" VARCHAR(300) NOT NULL , 

		  "STATUS" VARCHAR(1) NOT NULL )   

		 IN "USERSPACE1" ; 





-- DDL Statements for primary key on Table "HROPS_SCHEMA"."LEAVE"



ALTER TABLE "HROPS_SCHEMA"."LEAVE" 

	ADD CONSTRAINT "LEAVE_LEAVE_ID_PK" PRIMARY KEY

		("LEAVE_ID");



COMMIT WORK;



CONNECT RESET;



TERMINATE;



-- USER is: DB2ADMIN

-- Specified SCHEMA is: HROPS_SCHEMA

-- Creating DDL for table(s)



-- Schema name is ignored for the Federated Section

;
