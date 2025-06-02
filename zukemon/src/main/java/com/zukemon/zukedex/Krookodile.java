package com.zukemon.zukedex;

import com.zukemon.DamageType;
import com.zukemon.TeamManager;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Krookodile implements Zukemon {

    private final TeamManager teamManager;

    public Krookodile(TeamManager teamManager) {
        this.teamManager = teamManager;
    }

    @Override
    public int getAttackerType() {
        return 553;
    }

    /**
     * assumes that all previously called zukemons are its team members
     * assumes that Krookodiles do not stack. eg. if there are 2 krookodiles in the team. both krookodiles
     * do not consider the damage of each other and only considers the other zukemons
     * @return
     */
    @Override
    public int getBaseDamage() {
        List<Zukemon> teamMembers = teamManager.getTeamMembers();
        return teamMembers.stream().filter(member -> !(member instanceof Krookodile))
                .mapToInt(Zukemon::getActualDamage)
                .sum();
    }

    @Override
    public DamageType getDamageType() {
        return DamageType.DARK;
    }

    @Override
    public int getCriticalHitPercentage() {
        return 0;
    }
}
