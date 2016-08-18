create database chords;
create user 'chords_api'@'localhost' identified by 'chords_api';
grant all privileges on chords.* to 'chords_api'@'localhost';
