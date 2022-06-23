CREATE DATABASE server CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

use server;

drop table if exists t_theme;
create table t_theme
(
    theme_id      varchar(64) primary key default ''    not null,
    theme_name    varchar(128)            default ''    not null,
    view_name     varchar(128)            default ''    not null,
    related_count int                     default 0     not null,
    showed        bool                    default false not null,
    theme_index   int                     default -1    not null,
    deleted       bool                    default false not null,
    updated_time timestamp                  not null default now(),
    created_time timestamp                  not null default now()
);

drop table if exists t_INDEX_carousel;
create table t_INDEX_carousel
(
    carousel_id          varchar(64)  default ''    not null primary key,
    train_id             varchar(64)  default ''    not null,
    carousel_placeholder varchar(256) default ''    not null,
    carousel_img_url     varchar(512) default ''    not null,
    showed               bool         default false not null,
    carousel_index       int          default -1    not null,
    deleted              bool         default false not null,
    updated_time timestamp                  not null default now(),
    created_time timestamp                  not null default now()
);

drop table if exists t_train_course;
create table t_train_course
(
    train_course_id           varchar(64)  default ''    not null primary key,
    train_course_name         varchar(512) default ''    not null,
    train_course_video        varchar(512) default ''    not null,
    train_course_introduction text                       not null,
    showed                    bool         default false not null,
    train_course_index        int          default -1    not null,
    deleted                   bool         default false not null,
    updated_time timestamp                  not null default now(),
    created_time timestamp                  not null default now()
);

drop table if exists t_base;
create table t_base
(
    base_id      varchar(64)  default ''    not null primary key,
    base_name    varchar(128) default ''    not null,
    base_index   int          default -1    not null,
    deleted      bool         default false not null,
    updated_time timestamp                  not null default now(),
    created_time timestamp                  not null default now()
);


drop table if exists t_role;
create table t_role
(
    role_id      varchar(64)  default ''    not null primary key,
    role_name    varchar(128) default ''    not null,
    role_key    varchar(128) default ''    not null,
    deleted      bool         default false not null,
    updated_time timestamp                  not null default now(),
    created_time timestamp                  not null default now()
);

drop table if exists t_user_role;
create table t_user_role
(
    user_role_id      varchar(64)  default ''    not null primary key,
    user_id      varchar(64)  default ''    not null,
    role_id      varchar(64)  default ''    not null,
    deleted      bool         default false not null,
    updated_time timestamp                  not null default now(),
    created_time timestamp                  not null default now()
);


drop table if exists t_user;
create table t_user
(
    user_id      varchar(64)  default ''    not null primary key,
    username    varchar(128) default ''    not null,
    password    varchar(128) default ''    not null,
    wechat_uid    varchar(128) default ''    not null,
    name varchar(128) default ''    not null,
    male bool default false not null ,
    id_number varchar(32) default '' not null ,
    mobile varchar(16) default '' not null ,
    base_id varchar(64) default '' not null ,
    deleted      bool         default false not null,
    updated_time timestamp                  not null default now(),
    created_time timestamp                  not null default now()
);
