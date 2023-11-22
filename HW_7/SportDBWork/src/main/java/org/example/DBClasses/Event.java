package org.example.DBClasses;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


@Entity
@Table(name = "Events")
public class Event {
    @Id
    @Column(name = "event_id", length = 7, unique = true)
    private String eventId;

    @Column(name = "name", length = 40)
    private String name;

    @Column(name = "eventtype", length = 20)
    private String eventType;

    @Column(name = "olympic_id", length = 7)
    private String olympicId;

    @Column(name = "is_team_event")
    private int isTeamEvent;

    @Column(name = "num_players_in_team")
    private int numPlayersInTeam;

    @Column(name = "result_noted_in", length = 100)
    private String resultNotedIn;

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getOlympicId() {
        return olympicId;
    }

    public void setOlympicId(String olympicId) {
        this.olympicId = olympicId;
    }

    public int getIsTeamEvent() {
        return isTeamEvent;
    }

    public void setIsTeamEvent(int isTeamEvent) {
        this.isTeamEvent = isTeamEvent;
    }

    public int getNumPlayersInTeam() {
        return numPlayersInTeam;
    }

    public void setNumPlayersInTeam(int numPlayersInTeam) {
        this.numPlayersInTeam = numPlayersInTeam;
    }

    public String getResultNotedIn() {
        return resultNotedIn;
    }

    public void setResultNotedIn(String resultNotedIn) {
        this.resultNotedIn = resultNotedIn;
    }

}
