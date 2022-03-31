


--- DEFAULT VALUES ---
-- Role Table:
-- INSERT INTO ROLE(role_id, name) VALUES(1,'Customer');
-- INSERT INTO ROLE(role_id, name) VALUES(2,'Freelancer');
-- INSERT INTO ROLE(role_id, name) VALUES(3,'Admin');



--- TEMP VALUES ---
INSERT INTO USER(user_id,created_date,email,first_name,password,role) VALUES(1,sysdate(),'of.c.58@hotmail.com','Faruk', 'test1234','CUSTOMER'); -- Creation of Dummy User
INSERT INTO USER(user_id,created_date,email,first_name,password,role) VALUES(2,sysdate(),'freelancer@freelancer','Freelancer', 'free','FREELANCER'); -- Creation of Dummy Freelancer
-- INSERT INTO USER(user_id,created_date,email,first_name,password,role_id) VALUES(1,sysdate(),'of.c.58@hotmail.com','Faruk', 'test1234',1) -- Creation of Dummy Admin
