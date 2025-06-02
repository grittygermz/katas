package com.zukemon;

import com.zukemon.zukedex.Zukemon;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class ZukemonFactory {
    private final TeamManager teamManager;
    private final List<Zukemon> registeredZukemons;

    public ZukemonFactory(List<Zukemon> registeredZukemons, TeamManager teamManager) {
        this.registeredZukemons = registeredZukemons;
        this.teamManager = teamManager;
    }

    public Zukemon getZukemon(int attackerType) {
        Optional<Zukemon> selectedZukemonOptional = registeredZukemons.stream()
                .filter(zukemon -> zukemon.getAttackerType() == attackerType)
                .findAny();

        if(selectedZukemonOptional.isPresent()) {
            Zukemon selectedZukemon = selectedZukemonOptional.get();
            teamManager.addTeamMember(selectedZukemon);
            return selectedZukemon;
        } else {
            throw new InvalidAttackerTypeException("attackerType of %d does not exists".formatted(attackerType));
        }
    }
}
