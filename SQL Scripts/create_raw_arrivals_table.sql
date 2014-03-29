CREATE TABLE raw_arrivals_table (
    id INT NOT NULL AUTO_INCREMENT,
    PRIMARY KEY (id),
	arrival_date DATE NOT NULL,
    origin VARCHAR(50) NOT NULL,
    flight_number VARCHAR(50),
    airline VARCHAR(50),
    scheduled_arrival_time TIME NOT NULL,
    actual_arrival_time TIME NOT NULL,
    gate VARCHAR(50),
    status VARCHAR(50),
    equipment VARCHAR(50)
)