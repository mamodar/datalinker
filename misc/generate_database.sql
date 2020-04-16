-- DROPS ALL TABLES
DROP TABLE IF EXISTS "project";
DROP TABLE IF exists "users";
DROP TABLE if EXISTS "resource";
DROP TABLE if exists "relationship";

CREATE SEQUENCE hibernate_sequence START 1;

-- CREATE PROJECT
CREATE  TABLE "project" (
                           "id" BIGINT NOT NULL,
                           "creation_timestamp" TIMESTAMP NULL DEFAULT NULL,
                           "description" VARCHAR(255) NULL DEFAULT NULL,
                           "owner" VARCHAR(255) NULL DEFAULT NULL,
                           "project_name" VARCHAR(255) NULL DEFAULT NULL,
                           "rdmo_id" BIGINT NULL DEFAULT NULL,
                           PRIMARY KEY ("id")
);
ALTER TABLE project ADD COLUMN tsv tsvector;
UPDATE project SET
    tsv =
            setweight(to_tsvector(project_name),'A') ||
            setweight(to_tsvector(description),'B');

CREATE INDEX tsv_idx ON project USING gin(tsv);
-- CREATE resource

CREATE TABLE "resource" (
                            "id" BIGINT NOT NULL,
                            "creation_timestamp" TIMESTAMP NULL DEFAULT NULL,
                            "description" VARCHAR(255) NULL DEFAULT NULL,
                            "location" VARCHAR(255) NULL DEFAULT NULL,
                            "path" VARCHAR(255) NULL DEFAULT NULL,
                            "personal" BOOLEAN NULL DEFAULT NULL,
                            "user_id" BIGINT NULL DEFAULT NULL,
                            PRIMARY KEY ("id")
)
;

-- CREATE project_resource

CREATE TABLE "relationship" (
                                    "id" BIGINT NOT NULL,
                                    "creation_timestamp" TIMESTAMP NULL DEFAULT NULL,
                                    "project_id" BIGINT NULL DEFAULT NULL,
                                    "resource_id" BIGINT NULL DEFAULT NULL,
                                    PRIMARY KEY ("id"),
                                    CONSTRAINT "fkf5dm3ds6o6uqbbk8is7sge7jb" FOREIGN KEY ("resource_id") REFERENCES "resource" ("id") ON UPDATE NO ACTION ON DELETE NO ACTION,
                                    CONSTRAINT "fksia08gjbokwkenn8ipylim7e8" FOREIGN KEY ("project_id") REFERENCES "project" ("id") ON UPDATE NO ACTION ON DELETE NO ACTION
)
;
-- CREATE users ("user" is disallowed by postgresql)

CREATE TABLE "users" (
                         "id" BIGINT NOT NULL,
                         "username" VARCHAR(255) NULL DEFAULT NULL,
                         PRIMARY KEY ("id")
)
;

-- CREATE search

DROP  MATERIALIZED VIEW  IF EXISTS search_view;
DROP INDEX IF EXISTS tsv_idx;
CREATE MATERIALIZED VIEW search_view AS
-- concat all fields, aggregate it over multiple resources, replace non-alphanumeric by space
SELECT p.id,p.project_name,
       to_tsvector(regexp_replace(string_agg(CONCAT_WS(' ', p.description,p.owner,p.project_name,r.description,r.location,r.path), ' '),'\W',' ', 'g')) AS tsv
FROM project p FULL
                   JOIN relationship pr ON pr.project_id = p.id FULL
                   JOIN resource r ON r.id = pr.resource_id
GROUP BY p.id, p.project_name;

-- http://www.vinsguru.com/cloud-design-patterns-materialized-view-pattern-using-spring-boot-postgresql/

REFRESH MATERIALIZED VIEW  search_view;
--For HeidiSQL add DELIMITER //
CREATE OR REPLACE FUNCTION refresh_search_view() RETURNS TRIGGER AS $$
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
--For HeidiSQL add DELIMITER //
CREATE TRIGGER refresh_mat_view_after_po_insert
    AFTER INSERT OR DELETE OR UPDATE OR TRUNCATE
    ON relationship
    FOR EACH STATEMENT
EXECUTE PROCEDURE refresh_search_view();



