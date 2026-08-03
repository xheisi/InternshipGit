SET FOREIGN_KEY_CHECKS = 0;
DROP TABLE IF EXISTS reservations;
DROP TABLE IF EXISTS guestProfile;
DROP TABLE IF EXISTS rooms;
DROP TABLE IF EXISTS guest;
DROP TABLE IF EXISTS hotel;
SET FOREIGN_KEY_CHECKS = 1;
CREATE TABLE hotel (
                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                       name VARCHAR(150) NOT NULL,
                       city VARCHAR(100) NOT NULL,
                       address VARCHAR(255) NOT NULL,
                       star_rating INTEGER NOT NULL,
                       CONSTRAINT chk_hotel_star_rating
                           CHECK (star_rating BETWEEN 1 AND 5)
);
CREATE TABLE guest (
                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                       first_name VARCHAR(100) NOT NULL,
                       last_name VARCHAR(100) NOT NULL,
                       email VARCHAR(150) NOT NULL,
                       phone_number VARCHAR(50),
                       CONSTRAINT uk_guest_email UNIQUE (email)
);
CREATE TABLE guestProfile (
                              id BIGINT AUTO_INCREMENT PRIMARY KEY,
                              guest_id BIGINT NOT NULL,
                              address VARCHAR(255),
                              date_of_birth DATE,
                              nationality VARCHAR(100),
                              preferred_language VARCHAR(50),
                              CONSTRAINT uk_guest_profile_guest UNIQUE (guest_id),
                              CONSTRAINT fk_guest_profile_guest
                                  FOREIGN KEY (guest_id)
                                      REFERENCES guest (id)
                                      ON DELETE CASCADE
);
CREATE TABLE rooms (
                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                       hotel_id BIGINT NOT NULL,
                       room_number VARCHAR(20) NOT NULL,
                       room_type VARCHAR(30) NOT NULL,
                       capacity INTEGER NOT NULL,
                       price_per_night DECIMAL(10, 2) NOT NULL,
                       status VARCHAR(30) NOT NULL,
                       CONSTRAINT chk_room_capacity
                           CHECK (capacity > 0),
                       CONSTRAINT chk_room_price
                           CHECK (price_per_night > 0),
                       CONSTRAINT chk_room_type
                           CHECK (room_type IN ('STANDARD', 'DELUXE', 'SUITE')),
                       CONSTRAINT chk_room_status
                           CHECK (status IN ('AVAILABLE', 'MAINTENANCE')),
                       CONSTRAINT uk_room_number_per_hotel
                           UNIQUE (hotel_id, room_number),
                       CONSTRAINT fk_room_hotel
                           FOREIGN KEY (hotel_id)
                               REFERENCES hotel (id)
                               ON DELETE CASCADE
);
CREATE TABLE reservations (
                              id BIGINT AUTO_INCREMENT PRIMARY KEY,
                              guest_id BIGINT NOT NULL,
                              room_id BIGINT NOT NULL,
                              check_in_date DATE NOT NULL,
                              check_out_date DATE NOT NULL,
                              number_of_guests INTEGER NOT NULL,
                              total_price DECIMAL(10, 2) NOT NULL,
                              status VARCHAR(30) NOT NULL,
                              created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                              CONSTRAINT chk_reservation_dates
                                  CHECK (check_out_date > check_in_date),
                              CONSTRAINT chk_reservation_guests
                                  CHECK (number_of_guests > 0),
                              CONSTRAINT chk_reservation_total
                                  CHECK (total_price >= 0),
                              CONSTRAINT chk_reservation_status
                                  CHECK (status IN ('PENDING', 'CONFIRMED', 'CANCELLED')),
                              CONSTRAINT fk_reservation_guest
                                  FOREIGN KEY (guest_id)
                                      REFERENCES guest (id),
                              CONSTRAINT fk_reservation_room
                                  FOREIGN KEY (room_id)
                                      REFERENCES rooms (id)
);

CREATE TABLE IF NOT EXISTS app_user (
                                        id INT AUTO_INCREMENT PRIMARY KEY,
                                        email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(50) NOT NULL
    );

CREATE INDEX idx_hotel_city
    ON hotel (city);
CREATE INDEX idx_room_hotel_status
    ON rooms (hotel_id, status);
CREATE INDEX idx_reservation_guest
    ON reservations (guest_id);
CREATE INDEX idx_reservation_room_dates
    ON reservations (room_id, check_in_date, check_out_date);