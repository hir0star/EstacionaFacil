-- EstacionaFacil - estrutura inicial do banco de dados
CREATE DATABASE IF NOT EXISTS estacionafacil
    DEFAULT CHARACTER SET utf8mb4;

USE estacionafacil;

CREATE TABLE IF NOT EXISTS avaliacao (
    id INT AUTO_INCREMENT PRIMARY KEY,
    comentario TEXT NOT NULL,
    categoria VARCHAR(50),
    sentimento VARCHAR(20),
    contrato VARCHAR(20),
    veiculo VARCHAR(30),
    data_avaliacao DATETIME DEFAULT CURRENT_TIMESTAMP
);
