CREATE TABLE profile_venue (
                       id    BIGINT AUTO_INCREMENT PRIMARY KEY,
                       profile_id BIGINT,
                       venue_id BIGINT,
                       notes VARCHAR(1000)
);


INSERT INTO profile_venue (profile_id, venue_id, notes) VALUES
                                           (1, 1, 'Met on January 1st, not a good fit'),
                                           (1, 2, 'Emailed, put me on waiting list'),
                                           (2, 1, 'They like my work'),
                                            (2, 1, 'They really like my work, but no space for now');

