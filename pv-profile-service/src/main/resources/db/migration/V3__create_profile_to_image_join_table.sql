CREATE TABLE profile_image (
                       id    BIGINT AUTO_INCREMENT PRIMARY KEY,
                       profile_id BIGINT,
                       image_id BIGINT,
                       notes VARCHAR(1000)
);


INSERT INTO profile_image (profile_id, image_id, notes) VALUES
                                           (1, 1, 'picture 1'),
                                           (1, 2, 'Emailed, put me on waiting list'),
                                           (2, 1, 'They like my work'),
                                            (2, 1, 'They really like my work, but no space for now');

