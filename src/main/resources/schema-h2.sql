DROP TABLE IF EXISTS "PAGES";

CREATE TABLE "PAGES"
(
   PAGE_ID int PRIMARY KEY NOT NULL,
   TITLE varchar(200) NOT NULL,
   CONTENT clob(1073741823),
   CATEGORY boolean default false,
   PARENT_ID int default null
);

CREATE UNIQUE INDEX SQL100813084226120 ON "PAGES"(PAGE_ID);
INSERT INTO "PAGES" (PAGE_ID, TITLE, CONTENT, CATEGORY, PARENT_ID)
VALUES (1, 'Tech', null, 1, null);

INSERT INTO "PAGES" (PAGE_ID, TITLE, CONTENT, CATEGORY, PARENT_ID)
VALUES (2, 'Linux', null, 1, 1);

INSERT INTO "PAGES" (PAGE_ID, TITLE, CONTENT, CATEGORY, PARENT_ID)
VALUES (3, 'Java', null, 1, 1);

INSERT INTO "PAGES" (PAGE_ID, TITLE, CONTENT, CATEGORY, PARENT_ID)
VALUES (4, 'TODO', null, 1, null);

INSERT INTO "PAGES" (PAGE_ID, TITLE, CONTENT, CATEGORY, PARENT_ID)
VALUES (5, 'Debian', 'Page One Content', 0, 2);

INSERT INTO "PAGES" (PAGE_ID, TITLE, CONTENT, CATEGORY, PARENT_ID)
VALUES (6, 'Mint', 'Page Two Content', 0, 2);

INSERT INTO "PAGES" (PAGE_ID, TITLE, CONTENT, CATEGORY, PARENT_ID)
VALUES (7, 'Hibernate', 'Page Three Content', 0, 3);

INSERT INTO "PAGES" (PAGE_ID, TITLE, CONTENT, CATEGORY, PARENT_ID)
VALUES (8, 'Buy hardware', 'Buy undersink', 0, 4);



DROP TABLE IF EXISTS "HIBERNATE_UNIQUE_KEY";
CREATE TABLE "HIBERNATE_UNIQUE_KEY"
(
   NEXT_HI int
);
INSERT INTO "HIBERNATE_UNIQUE_KEY" (NEXT_HI) VALUES (1);
