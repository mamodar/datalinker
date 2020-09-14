-- CREATE DATABASE for the datalinker;
-- \connect datalinker
-- DROPS ALL TABLES
DROP MATERIALIZED VIEW IF EXISTS search_view;
DROP MATERIALIZED VIEW IF EXISTS question_answer_view;

DROP TABLE IF EXISTS "project_owner";
DROP TABLE IF EXISTS "project_value";
DROP TABLE if EXISTS "project_resource";

DROP TABLE IF EXISTS "project" CASCADE;
DROP TABLE IF EXISTS "users" CASCADE;
DROP TABLE if EXISTS "resource" CASCADE;

DROP TABLE if EXISTS "rdmo_option";
DROP TABLE if EXISTS "rdmo_question";
DROP TABLE if EXISTS "rdmo_value";
DROP TABLE IF EXISTS "rdmo_datalinker_connection";

DROP SEQUENCE if exists hibernate_sequence;
CREATE SEQUENCE hibernate_sequence START 1;

-- CREATE PROJECT
CREATE TABLE "project"
(
    "id"                 BIGINT    NOT NULL,
    "creation_timestamp" TIMESTAMP NULL DEFAULT NULL,
    "description"        VARCHAR   NULL DEFAULT 'NULL::character varying',
    "project_name"       VARCHAR   NULL DEFAULT 'NULL::character varying',
    "rdmo_id"            BIGINT    NULL DEFAULT NULL,
    "search_id"          BIGINT    NULL DEFAULT NULL,
    "updated_timestamp"  TIMESTAMP NULL DEFAULT NULL,
    PRIMARY KEY ("id")
)
;

-- CREATE users ("user" is disallowed by postgresql)
CREATE TABLE "users"
(
    "id"       BIGINT  NOT NULL,
    "dn"       VARCHAR NULL DEFAULT NULL,
    "username" VARCHAR NULL DEFAULT NULL,
    PRIMARY KEY ("id")
)
;

-- CREATE resource

CREATE TABLE "resource"
(
    "id"                 BIGINT    NOT NULL,
    "creation_timestamp" TIMESTAMP NULL DEFAULT NULL,
    "path"               VARCHAR   NOT NULL,
    "location"           VARCHAR   NOT NULL,
    "type"               VARCHAR   NOT NULL,
    "description"        VARCHAR   NULL DEFAULT 'NULL::character varying',
    "license"            VARCHAR   NOT NULL,
    "archived"           BOOLEAN   NULL DEFAULT NULL,
    "personal"           BOOLEAN   NULL DEFAULT NULL,
    "size"               REAL      NULL DEFAULT NULL,
    "updated_timestamp"  TIMESTAMP NULL DEFAULT NULL,
    "created_by_user_id" BIGINT    NOT NULL,
    "project_id"         BIGINT    NOT NULL,
    "updated_by_user_id" BIGINT    NOT NULL,
    PRIMARY KEY ("id"),
    CONSTRAINT "fk959t5quy9sgha1ikrhaedo260" FOREIGN KEY ("project_id") REFERENCES "public"."project" ("id") ON UPDATE NO ACTION ON DELETE NO ACTION,
    CONSTRAINT "fkgo5o5p15d2co1bun83j0a9ydn" FOREIGN KEY ("updated_by_user_id") REFERENCES "public"."users" ("id") ON UPDATE NO ACTION ON DELETE NO ACTION,
    CONSTRAINT "fko8ngrycha638ht39a7ip5u5hw" FOREIGN KEY ("created_by_user_id") REFERENCES "public"."users" ("id") ON UPDATE NO ACTION ON DELETE NO ACTION
)
;


-- CREATE RDMO Tables
-- CREATE RDMO Options
CREATE TABLE "rdmo_option"
(
    "id"               BIGINT  NOT NULL,
    "additional_input" BOOLEAN NULL DEFAULT NULL,
    "key"              VARCHAR NULL DEFAULT NULL,
    "optionset"        INTEGER NULL DEFAULT NULL,
    "rdmo_id"          INTEGER NULL DEFAULT NULL,
    "text"             VARCHAR NULL DEFAULT 'NULL::character varying',
    "text_de"          VARCHAR NULL DEFAULT 'NULL::character varying',
    "text_en"          VARCHAR NULL DEFAULT 'NULL::character varying',
    "uri_prefix"       VARCHAR NULL DEFAULT 'NULL::character varying',
    PRIMARY KEY ("id")
)
;

-- CREATE RDMO Questions
CREATE TABLE "rdmo_question"
(
    "id"                     BIGINT  NOT NULL,
    "attribute"              INTEGER NULL DEFAULT NULL,
    "keywords"               VARCHAR NULL DEFAULT NULL,
    "rdmo_id"                INTEGER NULL DEFAULT NULL,
    "text_de"                VARCHAR NULL DEFAULT 'NULL::character varying',
    "text_en"                VARCHAR NULL DEFAULT 'NULL::character varying',
    "uri_prefix"             VARCHAR NULL DEFAULT NULL,
    "verbose_name_de"        VARCHAR NULL DEFAULT NULL,
    "verbose_name_en"        VARCHAR NULL DEFAULT NULL,
    "verbose_name_plural_de" VARCHAR NULL DEFAULT NULL,
    "verbose_name_plural_en" VARCHAR NULL DEFAULT NULL,
    PRIMARY KEY ("id")
);
--CREATE RDMO Values

CREATE TABLE "rdmo_value"
(
    "id"               BIGINT  NOT NULL,
    "attribute"        BIGINT  NULL DEFAULT NULL,
    "collection_index" INTEGER NULL DEFAULT NULL,
    "option"           BIGINT  NULL DEFAULT NULL,
    "project"          BIGINT  NULL DEFAULT NULL,
    "rdmo_id"          BIGINT  NULL DEFAULT NULL,
    "set_index"        INTEGER NULL DEFAULT NULL,
    "text"             VARCHAR NULL DEFAULT NULL,
    "unit"             VARCHAR NULL DEFAULT NULL,
    "value_type"       VARCHAR NULL DEFAULT NULL,
    PRIMARY KEY ("id")
);
-- CREATE relationship Tables
-- CREATE project_owner relationship
CREATE TABLE "project_owner"
(
    "project_id" BIGINT NOT NULL,
    "owner_id"   BIGINT NOT NULL,
    CONSTRAINT "fk5iy9lk0uau6523pkp9ei9bk64" FOREIGN KEY ("project_id") REFERENCES "project" ("id") ON UPDATE NO ACTION ON DELETE NO ACTION,
    CONSTRAINT "fkqb39868ogh7w8fjc2b4fvddr1" FOREIGN KEY ("owner_id") REFERENCES "users" ("id") ON UPDATE NO ACTION ON DELETE NO ACTION
)
;

CREATE TABLE "project_resource"
(
    "project_id"  BIGINT NOT NULL,
    "resource_id" BIGINT NOT NULL,
    PRIMARY KEY ("project_id", "resource_id"),
    CONSTRAINT "fkgvyjy4pwnuerf1ojtk71e4edo" FOREIGN KEY ("project_id") REFERENCES "public"."project" ("id") ON UPDATE NO ACTION ON DELETE CASCADE,
    CONSTRAINT "fkqloe28wnxlwb5plj7msbnf2j3" FOREIGN KEY ("resource_id") REFERENCES "public"."resource" ("id") ON UPDATE NO ACTION ON DELETE CASCADE
)
;

CREATE TABLE "project_value"
(
    "project_id" BIGINT NOT NULL,
    "value_id"   BIGINT NOT NULL,
    PRIMARY KEY ("project_id", "value_id"),
    CONSTRAINT "fko3yp0csvtfcd28oxpjb5rse0k" FOREIGN KEY ("project_id") REFERENCES "public"."project" ("id") ON UPDATE NO ACTION ON DELETE NO ACTION
)
;

CREATE TABLE "rdmo_datalinker_connection"
(
    "attribute"     BIGINT NOT NULL,
    "order"         INTEGER,
    "is_filter"     BOOLEAN,
    "is_collection" BOOLEAN,
    PRIMARY KEY ("attribute")
);
TRUNCATE TABLE rdmo_datalinker_connection;
INSERT INTO rdmo_datalinker_connection
VALUES (311, 1, FALSE, NULL),  -- contact person
       (9, 2, FALSE, NULL),    -- principal investigator
       (12, 3, TRUE, NULL),    -- Organizational units
       (292, 4, FALSE, NULL),  -- Titles
       (269, 5, FALSE, NULL),  -- Acronyms
       (250, 6, FALSE, NULL),  --Keywords
       (181, 7, TRUE, NULL),   -- Third-party funded project
       (173, 8, TRUE, NULL),   -- Funders
       (221, 9, FALSE, NULL),  --  Cooperation partners (internal)
       (274, 10, TRUE, NULL),  -- Cooperation partners (external)
       (152, 11, FALSE, NULL), --Storage locations
       (138, 12, TRUE, NULL);
-- Planned Licenses / Terms of use


-- Create RDMO question answer pairs
CREATE MATERIALIZED VIEW question_answer_view AS
SELECT DISTINCT v.id              AS id,
                p.id              AS project_id,
                p.rdmo_id         AS project_rdmo_id,
                v.attribute       AS attribute,
                q.verbose_name_de AS question_text,
                v.text            AS answer,
                v.unit            AS unit,
                o.text            AS option_text,
                rdc.order         AS order,
                rdc.is_filter     AS is_filter,
                rdc.is_collection AS is_collection
FROM project p
         LEFT JOIN
     rdmo_value v ON p.rdmo_id = v.project
         LEFT JOIN
     rdmo_option o ON v.option = o.id
         LEFT JOIN
     rdmo_question q ON v.attribute = q.attribute
         LEFT JOIN
     rdmo_datalinker_connection rdc on q.attribute = rdc.attribute;
REFRESH MATERIALIZED VIEW question_answer_view;

-- CREATE haystack
CREATE MATERIALIZED VIEW search_view AS
    -- concat all fields, aggregate it over multiple resources, replace non-alphanumeric by space
SELECT p.id,
       p.rdmo_id,
       setweight(to_tsvector(COALESCE(string_agg(p.agg, ' '), '')), 'A') ||
       setweight(to_tsvector(COALESCE(string_agg(qav.agg, ' '), '')), 'B') ||
       setweight(to_tsvector(COALESCE(string_agg(u.agg, ' '), '')), 'C') ||
       setweight(to_tsvector(COALESCE(string_agg(r.agg, ' '), '')), 'D') AS tsv
FROM (SELECT p.id, p.rdmo_id, string_agg(CONCAT_WS(' ', p.description, p.project_name), ' ') AS agg
      FROM project p
      GROUP BY p.id, p.rdmo_id) AS p
         FULL OUTER JOIN
     (SELECT po.project_id, string_agg(CONCAT_WS(' ', u.username), ' ') AS agg
      FROM users u,
           project_owner po
      WHERE u.id = po.owner_id
      GROUP BY po.project_id) AS u ON p.id = u.project_id
         FULL OUTER JOIN
     (SELECT r.project_id,
             regexp_replace(string_agg(CONCAT_WS(' ', r.description, r.path, r.location), ' '), '\W', ' ', 'g') AS agg
      FROM resource r
      GROUP BY r.project_id) AS r ON p.id = r.project_id
         FULL OUTER JOIN
     (SELECT qav.project_id, string_agg(CONCAT_WS(' ', qav.option_text, qav.answer), ' ') AS agg
      FROM question_answer_view qav
      GROUP BY qav.project_id) AS qav ON qav.project_id = p.id
GROUP BY p.id, p.rdmo_id;
CREATE INDEX weighted_tsv_idx ON search_view USING GIST (tsv);

-- http://www.vinsguru.com/cloud-design-patterns-materialized-view-pattern-using-spring-boot-postgresql/

REFRESH MATERIALIZED VIEW search_view;
--For HeidiSQL add DELIMITER //
CREATE OR REPLACE FUNCTION refresh_search_view() RETURNS TRIGGER
    SECURITY DEFINER
AS
$$
BEGIN
    REFRESH MATERIALIZED VIEW search_view;
    REFRESH MATERIALIZED VIEW question_answer_view;
    RETURN new;
END
$$ LANGUAGE plpgsql;
--For HeidiSQL add DELIMITER //
DROP TRIGGER IF EXISTS refresh_mat_view_after_po_insert ON project;
CREATE TRIGGER refresh_mat_view_after_po_insert
    AFTER INSERT OR DELETE OR UPDATE OR TRUNCATE
    ON project
    FOR EACH STATEMENT
EXECUTE PROCEDURE refresh_search_view();

DROP TRIGGER IF EXISTS refresh_mat_view_after_rdmo_update ON rdmo_value;
CREATE TRIGGER refresh_mat_view_after_rdmo_update
    AFTER INSERT OR DELETE OR UPDATE OR TRUNCATE
    ON rdmo_value
    FOR EACH STATEMENT
EXECUTE PROCEDURE refresh_search_view();

REFRESH MATERIALIZED VIEW search_view;
REFRESH MATERIALIZED VIEW question_answer_view;

-- If non root is running the database
GRANT SELECT ON ALL TABLES IN SCHEMA public TO datalinker;
GRANT UPDATE ON ALL TABLES IN SCHEMA public TO datalinker;
GRANT INSERT ON ALL TABLES IN SCHEMA public TO datalinker;

GRANT USAGE, SELECT ON ALL SEQUENCES IN SCHEMA public TO datalinker;
GRANT EXECUTE ON ALL FUNCTIONS IN SCHEMA public to datalinker;

GRANT ALL PRIVILEGES ON TABLE rdmo_question TO datalinker;
GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA public TO datalinker;
GRANT ALL PRIVILEGES ON TABLE rdmo_option TO datalinker;

ALTER ROLE datalinker WITH LOGIN;

