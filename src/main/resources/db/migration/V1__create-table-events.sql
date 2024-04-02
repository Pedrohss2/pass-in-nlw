CREATE TABLE events (
    id varchar(255) NOT NULL PRIMARY KEY,
    title varchar(255) NOT NULL,
    details varchar(255) NOT NULL,
    slug varchar(255) NOT NULL,
    maximum_attendees INTEGER NOT NULL
);