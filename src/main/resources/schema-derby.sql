DROP TABLE "GREYHOUND"."PAGES";

CREATE TABLE "GREYHOUND"."PAGES"
(
   PAGE_ID int PRIMARY KEY NOT NULL,
   TITLE varchar(200) NOT NULL,
   CONTENT clob(1073741823),
   CATEGORY smallint NOT NULL DEFAULT 0,
   PARENT_ID int DEFAULT NULL
); 

CREATE UNIQUE INDEX SQL100813084226120 ON "GREYHOUND"."PAGES"(PAGE_ID);

INSERT INTO "GREYHOUND"."PAGES" (PAGE_ID, TITLE, CONTENT, CATEGORY, PARENT_ID)
VALUES (1, 'sampleCategoryOne', null, 1, null);

INSERT INTO "GREYHOUND"."PAGES" (PAGE_ID, TITLE, CONTENT, CATEGORY, PARENT_ID)
VALUES (2, 'sampleSubcategoryOne', null, 1, 1);

INSERT INTO "GREYHOUND"."PAGES" (PAGE_ID, TITLE, CONTENT, CATEGORY, PARENT_ID)
VALUES (3, 'sampleSubcategoryTwo', null, 1, 1);

INSERT INTO "GREYHOUND"."PAGES" (PAGE_ID, TITLE, CONTENT, CATEGORY, PARENT_ID)
VALUES (4, 'sampleCategoryTwo', null, 1, null);

INSERT INTO "GREYHOUND"."PAGES" (PAGE_ID, TITLE, CONTENT, CATEGORY, PARENT_ID)
VALUES (5, 'Page One', 'Page One Content', 0, 2);

INSERT INTO "GREYHOUND"."PAGES" (PAGE_ID, TITLE, CONTENT, CATEGORY, PARENT_ID)
VALUES (6, 'Page Two', 'Page Two Content', 0, 2);

INSERT INTO "GREYHOUND"."PAGES" (PAGE_ID, TITLE, CONTENT, CATEGORY, PARENT_ID)
VALUES (7, 'Page Three', 'Page Three Content', 0, 3);

INSERT INTO "GREYHOUND"."PAGES" (PAGE_ID, TITLE, CONTENT, CATEGORY, PARENT_ID)
VALUES (8, 'Page Four', 'Page Four Content', 0, 4);


DROP TABLE "GREYHOUND"."HIBERNATE_UNIQUE_KEY";
CREATE TABLE "GREYHOUND"."HIBERNATE_UNIQUE_KEY"
(
   NEXT_HI int
);
INSERT INTO "GREYHOUND"."HIBERNATE_UNIQUE_KEY" (NEXT_HI) VALUES (1);
