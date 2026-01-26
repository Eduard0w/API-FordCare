DROP TABLE IF EXISTS veiculo_alertas; -- Tabela da lista de alertas
DROP TABLE IF EXISTS veiculos;        -- Tabela que aponta para usuarios
DROP TABLE IF EXISTS usuarios;        -- Tabela independente

CREATE TABLE usuarios (
id SERIAL PRIMARY KEY,
nome VARCHAR(100) NOT NULL,
email VARCHAR(100) UNIQUE NOT NULL,
senha VARCHAR(100) NOT NULL
);

CREATE TABLE veiculos (
id SERIAL PRIMARY KEY,
marca VARCHAR(100),
modelo VARCHAR(100),
ano_veiculo INTEGER,
placa VARCHAR(20),
tipo_combustivel VARCHAR(50),
km INTEGER,
tipo_uso VARCHAR(50),
km_medio INTEGER,
imagem_veiculo VARCHAR(1000),
saude_veiculo INTEGER,

    usuario_id INTEGER REFERENCES usuarios(id), 

    -- Campos Embedded (RegistroManutencao) - Definidos nos @AttributeOverrides
    ult_troca_oleo_data DATE,
    ult_troca_oleo_km INTEGER,
    
    ult_troca_filtro_data DATE,
    ult_troca_filtro_km INTEGER,
    
    ult_troca_pastilhas_data DATE,
    ult_troca_pastilhas_km INTEGER
);

-- 4. Tabela Extra para o @ElementCollection (List<String> alertaPainel)
-- O JPA cria essa tabela separada automaticamente para listas simples
CREATE TABLE veiculo_alertas (
veiculo_id INTEGER NOT NULL REFERENCES veiculos(id),
alerta VARCHAR(255)
);