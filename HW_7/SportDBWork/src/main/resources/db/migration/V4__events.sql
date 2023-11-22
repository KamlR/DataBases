create table Events (
    event_id char(7) unique,
    name char(40),
    eventtype char(20),
    olympic_id char(7),
    is_team_event integer check (is_team_event in (0, 1)),
    num_players_in_team integer,
    result_noted_in char(100),
    foreign key (olympic_id) references Olympics(olympic_id)
);