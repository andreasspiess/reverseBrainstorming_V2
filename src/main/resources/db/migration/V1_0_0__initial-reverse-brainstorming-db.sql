CREATE TABLE problem (
id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
problem VARCHAR NOT NULL,
);

CREATE TABLE positiv (
id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
positiv VARCHAR NOT NULL,
problem_id INT NOT NULL,
CONSTRAINT FK_NEGATIV_PROBLEM FOREIGN KEY (problem_id) REFERENCES problem(id)
);

CREATE TABLE negativ (
id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
negativ VARCHAR NOT NULL,
positiv_id INT NOT NULL,
CONSTRAINT FK_POSITIV_PROBLEM FOREIGN KEY (positiv_id) REFERENCES problem(id)
);