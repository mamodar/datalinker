-- CREATE DATABASE mamodar;
-- DROPS ALL TABLES
DROP TABLE IF EXISTS "project" CASCADE;
DROP TABLE IF exists "users" CASCADE ;
DROP TABLE if EXISTS "resource" CASCADE ;
DROP TABLE if exists "relationship" CASCADE ;
DROP SEQUENCE if exists hibernate_sequence;
CREATE SEQUENCE hibernate_sequence START 1;

-- CREATE PROJECT
CREATE TABLE "project" (
                           "id" BIGINT NOT NULL,
                           "creation_timestamp" TIMESTAMP NULL DEFAULT NULL,
                           "description" VARCHAR(255) NULL DEFAULT NULL,
                           "owner" VARCHAR(255) NULL DEFAULT NULL,
                           "project_name" VARCHAR(255) NULL DEFAULT NULL,
                           "rdmo_id" BIGINT NULL DEFAULT NULL,
                           "updated_timestamp" TIMESTAMP NULL DEFAULT NULL,
                           PRIMARY KEY ("id")
)
;
ALTER TABLE project ADD COLUMN tsv tsvector;
UPDATE project SET
    tsv =
            setweight(to_tsvector(project_name),'A') ||
            setweight(to_tsvector(description),'B');

CREATE INDEX tsv_idx ON project USING gin(tsv);
-- CREATE users ("user" is disallowed by postgresql)

CREATE TABLE "users" (
                         "id" BIGINT NOT NULL,
                         "dn" VARCHAR(255) NULL DEFAULT NULL,
                         "username" VARCHAR(255) NULL DEFAULT NULL,
                         PRIMARY KEY ("id")
)
;

-- CREATE resource

CREATE TABLE "resource" (
                            "id" BIGINT NOT NULL,
                            "creation_timestamp" TIMESTAMP NULL DEFAULT NULL,
                            "description" VARCHAR(255) NULL DEFAULT NULL,
                            "archived" BOOLEAN NULL DEFAULT NULL,
                            "personal" BOOLEAN NULL DEFAULT NULL,
                            "third_party" BOOLEAN NULL DEFAULT NULL,
                            "location" VARCHAR(255) NOT NULL,
                            "path" VARCHAR(255) NOT NULL,
                            "size" REAL NULL DEFAULT NULL,
                            "updated_timestamp" TIMESTAMP NULL DEFAULT NULL,
                            "created_by_user_id" BIGINT NOT NULL,
                            "project_id" BIGINT NOT NULL,
                            "updated_by_user_id" BIGINT NOT NULL,
                            PRIMARY KEY ("id"),
                            CONSTRAINT "fk959t5quy9sgha1ikrhaedo260" FOREIGN KEY ("project_id") REFERENCES "project" ("id") ON UPDATE NO ACTION ON DELETE NO ACTION,
                            CONSTRAINT "fkgo5o5p15d2co1bun83j0a9ydn" FOREIGN KEY ("updated_by_user_id") REFERENCES "users" ("id") ON UPDATE NO ACTION ON DELETE NO ACTION,
                            CONSTRAINT "fko8ngrycha638ht39a7ip5u5hw" FOREIGN KEY ("created_by_user_id") REFERENCES "users" ("id") ON UPDATE NO ACTION ON DELETE NO ACTION
)
;

-- CREATE search

DROP  MATERIALIZED VIEW  IF EXISTS search_view;
DROP INDEX IF EXISTS tsv_idx;
CREATE MATERIALIZED VIEW search_view AS
-- concat all fields, aggregate it over multiple resources, replace non-alphanumeric by space
SELECT p.id,p.project_name,
       to_tsvector(regexp_replace(string_agg(CONCAT_WS(' ', p.description,p.owner,p.project_name,r.description,r.location,r.path), ' '),'\W',' ', 'g')) AS tsv
FROM resource r FULL
                JOIN project p ON r.project_id = p.id
GROUP BY p.id, p.project_name;

-- http://www.vinsguru.com/cloud-design-patterns-materialized-view-pattern-using-spring-boot-postgresql/

REFRESH MATERIALIZED VIEW  search_view;
--For HeidiSQL add DELIMITER //
CREATE OR REPLACE FUNCTION refresh_search_view() RETURNS TRIGGER
    SECURITY DEFINER
AS $$
BEGIN
    REFRESH MATERIALIZED VIEW search_view;
    RETURN new;
END $$ LANGUAGE plpgsql;
--For HeidiSQL add DELIMITER //
CREATE TRIGGER refresh_mat_view_after_po_insert
    AFTER INSERT OR DELETE OR UPDATE OR TRUNCATE
    ON project
    FOR EACH STATEMENT
EXECUTE PROCEDURE refresh_search_view();


 -- If non root is running the database
GRANT SELECT ON ALL TABLES IN SCHEMA public TO mamodaruser;
GRANT UPDATE ON ALL TABLES IN SCHEMA public TO mamodaruser;
GRANT INSERT ON ALL TABLES IN SCHEMA public TO mamodaruser;

GRANT USAGE, SELECT ON ALL SEQUENCES IN SCHEMA public TO mamodaruser;
GRANT EXECUTE ON ALL FUNCTIONS IN SCHEMA public to mamodaruser;

