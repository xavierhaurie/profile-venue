CREATE TABLE venue (
                         id    BIGINT AUTO_INCREMENT PRIMARY KEY,
                         name  VARCHAR(255),
                         email VARCHAR(255),
                         description VARCHAR(1000)
);

INSERT INTO venue (name, email, description) VALUES
                                             ('The expensive gallery on Newbury St', 'newbury-gallery@example.com', 'This gallery only shows art from established artists'),
                                             ('Watertown Library',   'watertown-library@example.com',   'Although there is a bit of a waiting list (6 months to 1 year), this library is a great place to show your work to the public for the first time');
