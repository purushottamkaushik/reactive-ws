CREATE TABLE users(
                      id IDENTITY PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL ,
    last_name VARCHAR(50) NOT NULL ,
    email VARCHAR(50)  NOT NULL UNIQUE ,
    password VARCHAR(255) NOT NULL

);