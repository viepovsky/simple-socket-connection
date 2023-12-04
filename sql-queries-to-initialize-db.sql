/*STWORZENIE TABEL */
CREATE TABLE users (
    id bigint PRIMARY KEY NOT NULL,
    nick text NOT NULL,
    login text UNIQUE NOT NULL,
    password text NOT NULL,
    insert_time timestamp NOT NULL DEFAULT now()
);
CREATE TABLE vehicles (
    id bigint PRIMARY KEY NOT NULL,
    login text NOT NULL REFERENCES users(login),
    brand text NOT NULL,
    model text NOT NULL,
    insert_time timestamp NOT NULL
);
CREATE TABLE insurance_offers (
    id bigint PRIMARY KEY NOT NULL,
    vehicle_id bigint NOT NULL REFERENCES vehicles(id),
    insurer text NOT NULL,
    price float NOT NULL,
    insert_time timestamp NOT NULL
);

/*DODANIE DANYCH DO TABEL */
INSERT INTO users (id, nick, login, password) VALUES
  (20, 'marynarz', 'krawczyk', 'krawczyk123'),
  (25, 'kowal', 'kowalski', 'kowalski123');

INSERT INTO vehicles (id, login, brand, model, insert_time) VALUES
  (1, 'krawczyk', 'OPEL', 'ASTRA', now()),
  (2, 'krawczyk', 'OPEL', 'CORSA', now()),
  (3, 'kowalski', 'CITROEN', 'C3', now()),
  (4, 'kowalski', 'BENTLEY', 'JAVA', now());

INSERT INTO insurance_offers (id, vehicle_id, insurer, price, insert_time) VALUES
  (55, 1, 'UNIQA', 1000.00, now()),
  (60, 1, 'ALLIANZ', 1200.00, now()),
  (65, 2, 'LINK4', 500.00, now()),
  (70, 3, 'UNIQA', 500.00, now()),
  (75, 3, 'ALLIANZ', 700.00, now()),
  (80, 3, 'LINK4', 430.00, now()),
  (85, 4, 'UNIQA', 7030.00, now()),
  (90, 4, 'ALLIANZ', 5500.00, now()),
  (95, 4, 'LINK4', 3900.00, now()),
  (100, 4, 'AXA', 4300.00, now());