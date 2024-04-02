CREATE TABLE attendees (
    id varchar(255) NOT NULL PRIMARY KEY,
    name varchar(255),
    email varchar(255),
    event_id varchar(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT attendees_event_id_fkey FOREIGN KEY  (event_id) REFERENCES events (id) ON DELETE  RESTRICT ON UPDATE CASCADE
);