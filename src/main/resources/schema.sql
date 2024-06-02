DROP TABLE IF EXISTS yearly_training_summary;
DROP TABLE IF EXISTS trainer_summary;


CREATE TABLE trainer_summary (
    user_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    user_name VARCHAR(255) NOT NULL,
    is_active BOOLEAN NOT NULL
);


CREATE TABLE yearly_training_summary (
     id BIGINT AUTO_INCREMENT PRIMARY KEY,
     trainer_user_id BIGINT NOT NULL,
     training_year INT NOT NULL,
     january_duration BIGINT DEFAULT 0,
     february_duration BIGINT DEFAULT 0,
     march_duration BIGINT DEFAULT 0,
     april_duration BIGINT DEFAULT 0,
     may_duration BIGINT DEFAULT 0,
     june_duration BIGINT DEFAULT 0,
     july_duration BIGINT DEFAULT 0,
     august_duration BIGINT DEFAULT 0,
     september_duration BIGINT DEFAULT 0,
     october_duration BIGINT DEFAULT 0,
     november_duration BIGINT DEFAULT 0,
     december_duration BIGINT DEFAULT 0,
     CONSTRAINT fk_trainer_user_id FOREIGN KEY (trainer_user_id) REFERENCES trainer_summary(user_id) ON DELETE CASCADE
);
