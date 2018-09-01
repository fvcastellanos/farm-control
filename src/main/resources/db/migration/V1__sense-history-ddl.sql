CREATE TABLE farm_control.sense_history (
	id INT UNSIGNED NOT NULL AUTO_INCREMENT,
	created TIMESTAMP NOT NULL,
	temperature_value DOUBLE NULL,
	temperature_dimension varchar(10) NULL,
	temperature_threshold DOUBLE NOT NULL,
	humidity_value DOUBLE NULL,
	humidity_threshold DOUBLE NOT NULL,
	pump_activated TINYINT NOT NULL,
	read_error TINYINT NOT NULL,
	message varchar(200) NULL,
	CONSTRAINT sense_history_PK PRIMARY KEY (id)
)
ENGINE=InnoDB
DEFAULT CHARSET=utf8
COLLATE=utf8_general_ci;
CREATE INDEX sense_history_created_IDX USING BTREE ON farm_control.sense_history (created) ;
CREATE INDEX sense_history_read_error_IDX USING BTREE ON farm_control.sense_history (read_error) ;
CREATE INDEX sense_history_pump_activated_IDX USING BTREE ON farm_control.sense_history (pump_activated) ;
