package com.zukemon;

import com.zukemon.zukedex.Zukemon;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class TeamManager {
    private final List<Zukemon> teamMembers = new ArrayList<>();

    public List<Zukemon> getTeamMembers() {
        return teamMembers;
    }

    public void addTeamMember(Zukemon zukemon) {
        teamMembers.add(zukemon);
    }
}
