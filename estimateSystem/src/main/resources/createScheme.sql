create database interline_EstimateSystem default character set utf8;
create user 'EstimateSystem'@'%' identified by "EstimateSystem123456#";
GRANT ALL privileges ON interline_EstimateSystem.* TO 'EstimateSystem'@'%';
SET GLOBAL log_bin_trust_function_creators = 1;