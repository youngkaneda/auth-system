/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  juan
 * Created: Apr 18, 2018
 */

create table user(
    id serial primary key,
    email varchar(60) unique,
    senha varchar(20)
    /*
    another infos ...
    */
);

create table token(
    id serial primary key,
    tokenValue varchar(36),
    createdIn BIGINT
);