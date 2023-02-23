CREATE TABLE Jugadores (
idJugador INT PRIMARY KEY,
rank TEXT,
wins INT,
kills INT,
deaths INT,
assists INT,
scoreround FLOAT,
kad FLOAT,
killsround FLOAT,
plants INT,
firstbloods INT,
clutches INT,
flawless INT,
aces INT
);

CREATE TABLE Mapas (
idMapa INT PRIMARY KEY,
name TEXT,
porcentaje_win TEXT,
wins INT,
losses INT,
kd FLOAT,
adr FLOAT,
acs FLOAT
);

CREATE TABLE Partidas (
idJugador INT,
idMapa INT,
idPartida INT PRIMARY KEY,
type TEXT,
result TEXT,
);

CREATE TABLE Armas (
idArma INT PRIMARY KEY,
name TEXT,
type TEXT
);