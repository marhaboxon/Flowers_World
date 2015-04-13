# --- Created by Slick DDL
# To stop Slick DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table "TYPE" ("ID" SERIAL NOT NULL PRIMARY KEY,"NAME" VARCHAR(254) DEFAULT '' NOT NULL);
create table "UZFLO" ("ID" SERIAL NOT NULL PRIMARY KEY,"IMGNAME" VARCHAR(254) DEFAULT '' NOT NULL,"TYPE_ID" INTEGER NOT NULL,"NAME" VARCHAR(254) DEFAULT '' NOT NULL,"DESCRIPTION" VARCHAR(254) DEFAULT '' NOT NULL,"COST" VARCHAR(254) DEFAULT '' NOT NULL);
alter table "UZFLO" add constraint "FLOWER_FK_TYPE_ID" foreign key("TYPE_ID") references "TYPE"("ID") on update NO ACTION on delete NO ACTION;

# --- !Downs

alter table "UZFLO" drop constraint "FLOWER_FK_TYPE_ID";
drop table "UZFLO";
drop table "TYPE";

