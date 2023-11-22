package org.example.DBClasses;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
@Entity
@Table(name = "Results")
public class Result {
    @Id
    @Column(name = "result_id")
    private int resultId;

    @Column(name = "event_id", length = 7)
    private String eventId;

    @Column(name = "player_id", length = 10)
    private String playerId;

    @Column(name = "medal", length = 7)
    private String medal;

    @Column(name = "result")
    private float resultValue;


    // Геттеры и сеттеры

    public int getResultId() {
        return resultId;
    }

    public void setResultId(int resultId) {
        this.resultId = resultId;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getPlayerId() {
        return playerId;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    public String getMedal() {
        return medal;
    }

    public void setMedal(String medal) {
        this.medal = medal;
    }

    public float getResultValue() {
        return resultValue;
    }

    public void setResultValue(float resultValue) {
        this.resultValue = resultValue;
    }
}