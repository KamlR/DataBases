create table Results (
    event_id char(7),
    player_id char(10),
    medal char(7),
    result float,
    foreign key (event_id) references Events(event_id),
    foreign key (player_id) references players(player_id)
);