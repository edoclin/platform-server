CREATE DATABASE server CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

use server;

create table server.t_INDEX_carousel
(
    carousel_id          varchar(64)  default ''                not null
        primary key,
    train_id             varchar(64)  default ''                not null,
    carousel_placeholder varchar(256) default ''                not null,
    carousel_img_url     varchar(512) default ''                not null,
    showed               tinyint(1)   default 0                 not null,
    carousel_index       int          default -1                not null,
    deleted              tinyint(1)   default 0                 not null,
    updated_time         timestamp    default CURRENT_TIMESTAMP not null,
    created_time         timestamp    default CURRENT_TIMESTAMP not null
);

create table server.t_INDEX_class
(
    class_id           varchar(64)  default ''                not null
        primary key,
    class_name         varchar(128) default ''                not null,
    class_introduction varchar(128) default ''                not null,
    bg_color           varchar(16)  default ''                not null,
    class_index        int          default -1                not null,
    deleted            tinyint(1)   default 0                 not null,
    updated_time       timestamp    default CURRENT_TIMESTAMP not null,
    created_time       timestamp    default CURRENT_TIMESTAMP not null
);

create table server.t_base
(
    base_id      varchar(64)  default ''                not null
        primary key,
    base_name    varchar(128) default ''                not null,
    base_index   int          default -1                not null,
    deleted      tinyint(1)   default 0                 not null,
    updated_time timestamp    default CURRENT_TIMESTAMP not null,
    created_time timestamp    default CURRENT_TIMESTAMP not null
);

create table server.t_live
(
    live_id               varchar(64)  default ''                not null
        primary key,
    live_cover_img        varchar(512) default ''                not null,
    live_title            varchar(128) default ''                not null,
    live_push_stream_name varchar(128) default ''                not null,
    live_introduction     text                                   not null,
    live_desc             varchar(256) default ''                not null,
    start_time            timestamp    default CURRENT_TIMESTAMP not null,
    end_time              timestamp    default CURRENT_TIMESTAMP not null,
    deleted               tinyint(1)   default 0                 not null,
    updated_time          timestamp    default CURRENT_TIMESTAMP not null,
    created_time          timestamp    default CURRENT_TIMESTAMP not null,
    real_start_time       timestamp                              null,
    real_end_time         timestamp    default CURRENT_TIMESTAMP not null,
    finished              tinyint(1)   default 0                 not null,
    recorded              tinyint(1)   default 0                 not null,
    live_video_url        varchar(512) default ''                not null,
    showed                tinyint(1)   default 0                 not null
);

create table server.t_live_discussion
(
    live_discussion_id varchar(64)  default ''                not null
        primary key,
    message            varchar(256) default ''                not null,
    open_id            varchar(64)  default ''                not null,
    live_id            varchar(64)  default ''                not null,
    deleted            tinyint(1)   default 0                 not null,
    updated_time       timestamp    default CURRENT_TIMESTAMP not null,
    created_time       timestamp    default CURRENT_TIMESTAMP not null
);

create table server.t_role
(
    role_id      varchar(64)  default ''                not null
        primary key,
    role_name    varchar(128) default ''                not null,
    role_key     varchar(128) default ''                not null,
    deleted      tinyint(1)   default 0                 not null,
    updated_time timestamp    default CURRENT_TIMESTAMP not null,
    created_time timestamp    default CURRENT_TIMESTAMP not null
);

create table server.t_theme
(
    theme_id      varchar(64)  default ''                not null
        primary key,
    theme_name    varchar(128) default ''                not null,
    view_name     varchar(128) default ''                not null,
    related_count int          default 0                 not null,
    showed        tinyint(1)   default 0                 not null,
    theme_index   int          default -1                not null,
    deleted       tinyint(1)   default 0                 not null,
    updated_time  timestamp    default CURRENT_TIMESTAMP not null,
    created_time  timestamp    default CURRENT_TIMESTAMP not null
);

create table server.t_train_course
(
    train_course_id           varchar(64)  default ''                not null
        primary key,
    train_course_name         varchar(512) default ''                not null,
    class_id                  varchar(64)  default ''                not null,
    train_course_video        varchar(512) default ''                not null,
    train_course_introduction text                                   not null,
    showed                    tinyint(1)   default 0                 not null,
    train_course_index        int          default -1                not null,
    deleted                   tinyint(1)   default 0                 not null,
    updated_time              timestamp    default CURRENT_TIMESTAMP not null,
    created_time              timestamp    default CURRENT_TIMESTAMP not null,
    train_course_cover        varchar(512) default ''                not null,
    train_course_desc         varchar(256) default ''                not null
);

create table server.t_user
(
    user_id      varchar(64)  default ''                not null
        primary key,
    username     varchar(128) default ''                not null,
    password     varchar(128) default ''                not null,
    wechat_uid   varchar(128) default ''                not null,
    name         varchar(128) default ''                not null,
    male         tinyint(1)   default 0                 not null,
    id_number    varchar(32)  default ''                not null,
    mobile       varchar(16)  default ''                not null,
    deleted      tinyint(1)   default 0                 not null,
    updated_time timestamp    default CURRENT_TIMESTAMP not null,
    created_time timestamp    default CURRENT_TIMESTAMP not null,
    audited      tinyint(1)   default 0                 not null,
    avatar_url   varchar(512) default ''                not null,
    nick_name    varchar(128) default ''                not null
);

create table server.t_user_base
(
    user_base_id varchar(64) default ''                not null
        primary key,
    base_id      varchar(64) default ''                not null,
    user_id      varchar(64) default ''                not null,
    deleted      tinyint(1)  default 0                 not null,
    updated_time timestamp   default CURRENT_TIMESTAMP not null,
    created_time timestamp   default CURRENT_TIMESTAMP not null
);

create table server.t_user_role
(
    user_role_id varchar(64) default ''                not null
        primary key,
    user_id      varchar(64) default ''                not null,
    role_id      varchar(64) default ''                not null,
    deleted      tinyint(1)  default 0                 not null,
    updated_time timestamp   default CURRENT_TIMESTAMP not null,
    created_time timestamp   default CURRENT_TIMESTAMP not null
);


