/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  juan
 * Created: Apr 21, 2018
 */

create table host(
    id serial primary key,
    appID int,
    URL varchar(60)
);

create table token(
    tokenValue varchar(36) primary key,
    appID int references host(appID),
    createdIn BIGINT
);
