package com.homespot.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "agent_history")
public class AgentHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "estate_id", nullable = false)
    private Estate estate;

    @ManyToOne
    @JoinColumn(name = "agent_id", nullable = false)
    private User agent;

    @Column(name = "assigned_at", nullable = false, updatable = false,
            columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime assignedAt = LocalDateTime.now();

    @Column(name = "unassigned_at")
    private LocalDateTime unassignedAt;

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Estate getEstate() { return estate; }
    public void setEstate(Estate estate) { this.estate = estate; }

    public User getAgent() { return agent; }
    public void setAgent(User agent) { this.agent = agent; }

    public LocalDateTime getAssignedAt() { return assignedAt; }
    public void setAssignedAt(LocalDateTime assignedAt) { this.assignedAt = assignedAt; }

    public LocalDateTime getUnassignedAt() { return unassignedAt; }
    public void setUnassignedAt(LocalDateTime unassignedAt) { this.unassignedAt = unassignedAt; }
}
