-- Create the database only if it doesn't exist
SELECT 'CREATE DATABASE gameloft'
    WHERE NOT EXISTS (
    SELECT FROM pg_database WHERE datname = 'gameloft'
)\gexec

-- Switch to the gameloft database
\c gameloft

create table campaign_items (
                                id uuid not null,
                                is_available boolean,
                                campaign_id uuid,
                                item_id uuid,
                                primary key (id)
);

create table campaign_players (
                                  id uuid not null,
                                  campaign_id uuid,
                                  player_id uuid,
                                  primary key (id)
);

create table campaigns (
                           id uuid not null,
                           countries text[],
                           enabled boolean not null,
                           end_date timestamp(6) with time zone,
                           game varchar(255),
                           last_updated timestamp(6) with time zone,
                           max_level integer not null,
                           min_level integer not null,
                           name varchar(255),
                           priority float(53),
                           start_date timestamp(6) with time zone,
                           primary key (id)
);

create table clans (
                       id uuid not null,
                       name varchar(255),
                       primary key (id)
);

create table devices (
                         id bigint not null,
                         carrier varchar(255),
                         firmware varchar(255),
                         model varchar(255),
                         player_id uuid,
                         primary key (id)
);

create table inventories (
                             id uuid not null,
                             cash integer not null,
                             coins integer not null,
                             player_id uuid,
                             primary key (id)
);

create table inventory_items (
                                 id uuid not null,
                                 quantity integer not null,
                                 inventory_id uuid,
                                 item_id uuid,
                                 primary key (id)
);

create table items (
                       id uuid not null,
                       name varchar(255),
                       primary key (id)
);

create table players (
                         id uuid not null,
                         birth_date timestamp(6) with time zone,
                         country varchar(255),
                         created timestamp(6) with time zone,
                         gender varchar(255),
                         language varchar(255),
                         last_purchase timestamp(6) with time zone,
                         last_session timestamp(6) with time zone,
                         level integer not null,
                         modified timestamp(6) with time zone,
                         total_play_time integer not null,
                         total_refund integer not null,
                         total_spent integer not null,
                         total_transactions integer not null,
                         xp integer not null,
                         primary key (id)
);

alter table if exists inventories
drop constraint if exists UKjoepqlppoi4x8ybgln3rq0gjj;

alter table if exists inventories
    add constraint UKjoepqlppoi4x8ybgln3rq0gjj unique (player_id);

alter table if exists campaign_items
    add constraint FK800uypj43tnftoiasjmrdm529
    foreign key (campaign_id)
    references campaigns;

alter table if exists campaign_items
    add constraint FKg3kb9vfjvko82vq7o6ram0j2o
    foreign key (item_id)
    references items;

alter table if exists campaign_players
    add constraint FKqjwtfro1a0oq69f96uh67k4qj
    foreign key (campaign_id)
    references campaigns;

alter table if exists campaign_players
    add constraint FKh72e69pivopk99mm90fecbx6
    foreign key (player_id)
    references players;

alter table if exists devices
    add constraint FK56xquyy5687tfg8gywi0lrq7c
    foreign key (player_id)
    references players;

alter table if exists inventories
    add constraint FKdu0xsuugjpvmd8l00e83m3yfo
    foreign key (player_id)
    references players;

alter table if exists inventory_items
    add constraint FKmckma5ra1ec4pu4of4ck8wpuq
    foreign key (inventory_id)
    references inventories;

alter table if exists inventory_items
    add constraint FKtdwjhb34ref66x4k4g1ffn69l
    foreign key (item_id)
    references items;

alter table if exists players
    add column clan_id uuid;

alter table if exists players
    add constraint FK8w3swxmumnfmduqe7px6ktnn4
    foreign key (clan_id)
    references clans;

INSERT INTO campaigns (
    id, game, name, priority, min_level, max_level, countries, enabled,
    start_date, end_date, last_updated
) VALUES (
             gen_random_uuid(), -- or supply a UUID manually
             'mygame',
             'mycampaign',
             10.5,
             1,
             3,
             ARRAY['US','RO','CA'], -- assuming countries is stored as an array
             TRUE,
             '2022-01-25 00:00:00+00',
             '2022-02-25 00:00:00+00',
             '2021-07-13 11:46:58+00'
         );

INSERT INTO clans(id, name)
VALUES(gen_random_uuid(), 'Hello world clan');

INSERT INTO items (id, name) VALUES
                                 (gen_random_uuid(), 'item_1'),
                                 (gen_random_uuid(), 'item_4'),
                                 (gen_random_uuid(), 'item_34'),
                                 (gen_random_uuid(), 'item_55');

INSERT INTO players (
    id, created, modified, last_session, total_spent, total_refund, total_transactions,
    last_purchase, level, xp, total_play_time, country, language, birth_date, gender, clan_id
) VALUES (
             '97983be2-98b7-11e7-90cf-082e5f28d836',
             '2021-01-10 13:37:17+00',
             '2021-01-23 13:37:17+00',
             '2021-01-23 13:37:17+00',
             400,
             0,
             5,
             '2021-01-22 13:37:17+00',
             3,
             1000,
             144,
             'CA', 'fr',
             '2000-01-10 13:37:17+00',
             'male',
            (SELECT id from clans where name = 'Hello world clan')
         );


INSERT INTO devices (id, player_id, model, carrier, firmware)
VALUES (
           1,
           '97983be2-98b7-11e7-90cf-082e5f28d836',
           'apple iphone 11',
           'vodafone',
           '123'
       );

INSERT INTO inventories (id, player_id, cash, coins)
VALUES (
           gen_random_uuid(),
           '97983be2-98b7-11e7-90cf-082e5f28d836',
           123, 123
       );

INSERT INTO inventory_items (id, inventory_id, item_id, quantity)
values
    (gen_random_uuid(), (SELECT id FROM inventories WHERE player_id = '97983be2-98b7-11e7-90cf-082e5f28d836'), (SELECT id FROM items WHERE name = 'item_1'), 1),
    (gen_random_uuid(), (SELECT id FROM inventories WHERE player_id = '97983be2-98b7-11e7-90cf-082e5f28d836'), (SELECT id FROM items WHERE name = 'item_34'), 3),
    (gen_random_uuid(), (SELECT id FROM inventories WHERE player_id = '97983be2-98b7-11e7-90cf-082e5f28d836'), (SELECT id FROM items WHERE name = 'item_55'), 2);


INSERT INTO campaign_items(id, campaign_id, item_id, is_available)
VALUES
    (gen_random_uuid(), (SELECT id FROM campaigns WHERE enabled is true), (SELECT id FROM items WHERE name = 'item_1'), true),
    (gen_random_uuid(), (SELECT id FROM campaigns WHERE enabled is true), (SELECT id FROM items WHERE name = 'item_4'), false);
