CREATE TABLE venue_profile (
                       id    BIGINT AUTO_INCREMENT PRIMARY KEY,
                       venue_id BIGINT,
                       profile_id BIGINT,
                       notes VARCHAR(1000)
);


INSERT INTO venue_profile (venue_id, profile_id, notes) VALUES
                                           (1, 1, 'Follow this artist'),
                                           (1, 2, 'Trying to get in touch'),
                                           (2, 1, 'Scheduled to drop off their work'),
                                           (2, 1, 'Unable to reach the artist)');
