CREATE TABLE profile (
                         id    BIGINT AUTO_INCREMENT PRIMARY KEY,
                         name  VARCHAR(255),
                         email VARCHAR(255),
                         description VARCHAR(1000)
);

INSERT INTO profile (name, email, description) VALUES
                                             ('Alice', 'alice@example.com', 'Alice enjoys painting landscapes with watercolors'),
                                             ('Bob',   'bob@example.com',   'Bob likes to paint portraits with oil');
