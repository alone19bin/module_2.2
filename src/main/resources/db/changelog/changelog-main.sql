CREATE TABLE writer (
                           id int NOT NULL AUTO_INCREMENT,
                           first_name varchar(100) DEFAULT NULL,
                           last_name varchar(100) DEFAULT NULL,
                           writerStatus varchar(30) DEFAULT NULL,
                           PRIMARY KEY (id)
);
CREATE TABLE label (
                          id int NOT NULL AUTO_INCREMENT,
                          name varchar(255) DEFAULT NULL,
                          labelStatus varchar(255) DEFAULT NULL,
                          PRIMARY KEY (id)
);
CREATE TABLE post (
                         id int NOT NULL AUTO_INCREMENT PRIMARY KEY,
                         content varchar(255) DEFAULT NULL,
                         created varchar(255) DEFAULT NULL,
                         updated varchar(255) DEFAULT NULL,
                         postStatus varchar(255) DEFAULT NULL,
                         writer_id int DEFAULT NULL,
                         PRIMARY KEY (id),
                         FOREIGN KEY (writer_id) REFERENCES (writers) id,
);
CREATE TABLE post_labels (
                               post_id int NOT NULL,
                               label_id int NOT NULL,
                               CONSTRAINT fk_post_labels_post_id FOREIGN KEY (post_id) REFERENCES posts (id),
                               CONSTRAINT fk_post_labels_label_id FOREIGN KEY (label_id) REFERENCES labels (id),
                                UNIQUE (post_id, label_id)
);
