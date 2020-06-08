-- CREATE DATABASE mamodar;
-- \connect mamodar
-- DROPS ALL TABLES
DROP TABLE IF EXISTS "project" CASCADE;
DROP TABLE IF exists "users" CASCADE;
DROP TABLE if EXISTS "resource" CASCADE;
DROP TABLE if exists "relationship" CASCADE;
DROP TABLE IF EXISTS "project_owner" CASCADE;
DROP SEQUENCE if exists hibernate_sequence;
CREATE SEQUENCE hibernate_sequence START 1;

-- CREATE PROJECT
CREATE TABLE "project"
(
    "id"                 BIGINT       NOT NULL,
    "creation_timestamp" TIMESTAMP    NULL DEFAULT NULL,
    "description"        VARCHAR(255) NULL DEFAULT NULL,
    "project_name"       VARCHAR(255) NULL DEFAULT NULL,
    "rdmo_id"            BIGINT       NULL DEFAULT NULL,
    "updated_timestamp"  TIMESTAMP    NULL DEFAULT NULL,
    PRIMARY KEY ("id")
)
;
ALTER TABLE project
    ADD COLUMN tsv tsvector;
UPDATE project
SET tsv =
            setweight(to_tsvector(project_name), 'A') ||
            setweight(to_tsvector(description), 'B');

CREATE INDEX tsv_idx ON project USING gin (tsv);

-- CREATE users ("user" is disallowed by postgresql)
CREATE TABLE "users"
(
    "id"       BIGINT       NOT NULL,
    "dn"       VARCHAR(255) NULL DEFAULT NULL,
    "username" VARCHAR(255) NULL DEFAULT NULL,
    PRIMARY KEY ("id")
)
;

-- CREATE resource

CREATE TABLE "resource"
(
    "id"                 BIGINT       NOT NULL,
    "creation_timestamp" TIMESTAMP    NULL DEFAULT NULL,
    "description"        VARCHAR(255) NULL DEFAULT NULL,
    "archived"           BOOLEAN      NULL DEFAULT NULL,
    "personal"           BOOLEAN      NULL DEFAULT NULL,
    "third_party"        BOOLEAN      NULL DEFAULT NULL,
    "path"               VARCHAR(255) NOT NULL,
    "location"           VARCHAR(255) NOT NULL,
    "size"               REAL         NULL DEFAULT NULL,
    "updated_timestamp"  TIMESTAMP    NULL DEFAULT NULL,
    "created_by_user_id" BIGINT       NOT NULL,
    "project_id"         BIGINT       NOT NULL,
    "updated_by_user_id" BIGINT       NOT NULL,
    PRIMARY KEY ("id"),
    CONSTRAINT "fk959t5quy9sgha1ikrhaedo260" FOREIGN KEY ("project_id") REFERENCES "project" ("id") ON UPDATE NO ACTION ON DELETE NO ACTION,
    CONSTRAINT "fkgo5o5p15d2co1bun83j0a9ydn" FOREIGN KEY ("updated_by_user_id") REFERENCES "users" ("id") ON UPDATE NO ACTION ON DELETE NO ACTION,
    CONSTRAINT "fko8ngrycha638ht39a7ip5u5hw" FOREIGN KEY ("created_by_user_id") REFERENCES "users" ("id") ON UPDATE NO ACTION ON DELETE NO ACTION
)
;


-- CREATE project_owner relationship
CREATE TABLE "project_owner"
(
    "project_id" BIGINT NOT NULL,
    "owner_id"   BIGINT NOT NULL,
    CONSTRAINT "fk5iy9lk0uau6523pkp9ei9bk64" FOREIGN KEY ("project_id") REFERENCES "project" ("id") ON UPDATE NO ACTION ON DELETE NO ACTION,
    CONSTRAINT "fkqb39868ogh7w8fjc2b4fvddr1" FOREIGN KEY ("owner_id") REFERENCES "users" ("id") ON UPDATE NO ACTION ON DELETE NO ACTION
)
;
-- CREATE RDMO Tables
-- CREATE RDMO Options
CREATE TABLE "rdmo_option"
(
    "id"               BIGINT        NOT NULL,
    "additional_input" BOOLEAN       NULL DEFAULT NULL,
    "key"              VARCHAR(255)  NULL DEFAULT NULL,
    "optionset"        INTEGER       NULL DEFAULT NULL,
    "rdmo_id"          INTEGER       NULL DEFAULT NULL,
    "text"             VARCHAR(1000) NULL DEFAULT 'NULL::character varying',
    "text_de"          VARCHAR(1000) NULL DEFAULT 'NULL::character varying',
    "text_en"          VARCHAR(1000) NULL DEFAULT 'NULL::character varying',
    "uri_prefix"       VARCHAR(1000) NULL DEFAULT 'NULL::character varying',
    PRIMARY KEY ("id")
)
;

-- CREATE RDMO Questions
CREATE TABLE "rdmo_question"
(
    "id"                     BIGINT        NOT NULL,
    "attribute"              INTEGER       NULL DEFAULT NULL,
    "keywords"               VARCHAR(255)  NULL DEFAULT NULL,
    "rdmo_id"                INTEGER       NULL DEFAULT NULL,
    "text_de"                VARCHAR(1000) NULL DEFAULT 'NULL::character varying',
    "text_en"                VARCHAR(1000) NULL DEFAULT 'NULL::character varying',
    "uri_prefix"             VARCHAR(255)  NULL DEFAULT NULL,
    "verbose_name_de"        VARCHAR(255)  NULL DEFAULT NULL,
    "verbose_name_en"        VARCHAR(255)  NULL DEFAULT NULL,
    "verbose_name_plural_de" VARCHAR(255)  NULL DEFAULT NULL,
    "verbose_name_plural_en" VARCHAR(255)  NULL DEFAULT NULL,
    PRIMARY KEY ("id")
)
--CREATE RDMO Values

CREATE TABLE "rdmo_value"
(
    "id"               BIGINT       NOT NULL,
    "attribute"        BIGINT       NULL DEFAULT NULL,
    "collection_index" INTEGER      NULL DEFAULT NULL,
    "option"           BIGINT       NULL DEFAULT NULL,
    "project"          BIGINT       NULL DEFAULT NULL,
    "rdmo_id"          BIGINT       NULL DEFAULT NULL,
    "set_index"        INTEGER      NULL DEFAULT NULL,
    "text"             VARCHAR(255) NULL DEFAULT NULL,
    "unit"             VARCHAR(255) NULL DEFAULT NULL,
    "value_type"       VARCHAR(255) NULL DEFAULT NULL,
    PRIMARY KEY ("id")
);


-- CREATE search

DROP MATERIALIZED VIEW IF EXISTS search_view;
DROP INDEX IF EXISTS tsv_idx;
CREATE MATERIALIZED VIEW search_view AS
-- concat all fields, aggregate it over multiple resources, replace non-alphanumeric by space
SELECT p.id,
       p.project_name,
       to_tsvector(regexp_replace(
               string_agg(CONCAT_WS(' ', p.description, u.username, p.project_name, r.description, r.location, r.path),
                          ' '), '\W', ' ', 'g')) AS tsv
FROM resource r
         FULL JOIN
     project p ON r.project_id = p.id
         FULL JOIN
     project_owner po on p.id = po.project_id
         FULL JOIN
     users u ON u.id = po.owner_id
GROUP BY p.id, p.project_name;

-- http://www.vinsguru.com/cloud-design-patterns-materialized-view-pattern-using-spring-boot-postgresql/

REFRESH MATERIALIZED VIEW search_view;
--For HeidiSQL add DELIMITER //
CREATE OR REPLACE FUNCTION refresh_search_view() RETURNS TRIGGER
    SECURITY DEFINER
AS
$$
BEGIN
    REFRESH MATERIALIZED VIEW search_view;
    RETURN new;
END
$$ LANGUAGE plpgsql;
--For HeidiSQL add DELIMITER //
CREATE TRIGGER refresh_mat_view_after_po_insert
    AFTER INSERT OR DELETE OR UPDATE OR TRUNCATE
    ON project
    FOR EACH STATEMENT
EXECUTE PROCEDURE refresh_search_view();

-- Create RDMO question answer pairs
DROP MATERIALIZED VIEW IF EXISTS question_answer_view;

CREATE MATERIALIZED VIEW question_answer_view AS
SELECT DISTINCT v.id        AS id,
                v.project   AS project_id,
                v.attribute AS attribute,
                q.text_de   AS question_text,
                v.text      AS answer,
                o.text      AS option_text
FROM rdmo_value v
         LEFT JOIN
     rdmo_option o ON v.option = o.rdmo_id
         LEFT JOIN
     rdmo_question q ON v.attribute = q.attribute;

-- If non root is running the database
GRANT SELECT ON ALL TABLES IN SCHEMA public TO mamodar;
GRANT UPDATE ON ALL TABLES IN SCHEMA public TO mamodar;
GRANT INSERT ON ALL TABLES IN SCHEMA public TO mamodar;

GRANT USAGE, SELECT ON ALL SEQUENCES IN SCHEMA public TO mamodar;
GRANT EXECUTE ON ALL FUNCTIONS IN SCHEMA public to mamodar;

ALTER ROLE mamodar WITH LOGIN;
