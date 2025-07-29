package com.zukemon.refactor.gameconsole;

import com.zukemon.refactor.equipment.Sword;
import com.zukemon.refactor.fight.FightModeFactory;
import com.zukemon.refactor.fightmodes.DefendFightMode;
import com.zukemon.refactor.fightmodes.FightModeType;
import com.zukemon.refactor.fightmodes.NormalFightMode;
import com.zukemon.refactor.fightmodes.RoyalRumbleFightMode;
import com.zukemon.refactor.gameconsole.commands.Command;
import com.zukemon.refactor.gameconsole.commands.InitiateFightModeCommand;
import com.zukemon.refactor.zukemons.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SimpleGameConsoleTest {

    SimpleGameConsole simpleGameConsole;

    @BeforeEach
    void setUp() {
        simpleGameConsole = new SimpleGameConsole();
        simpleGameConsole.init();
    }

    @Test
    void shouldHaveCommandsLoaded() {
        assertThat(simpleGameConsole.displayNameAndCommand).hasSize(4);
    }

    @Test
    void shouldFailIfRunInvalidCommand() {
        assertThatThrownBy(() -> simpleGameConsole.runButtonCommand("abcd", null))
                .isInstanceOf(NullPointerException.class);
        assertThatThrownBy(() -> simpleGameConsole.runButtonPrompt("abcd"))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    void shouldSelectZukemon() {
        simpleGameConsole.runButtonCommand("select zukemon", 25);

        assertThat(simpleGameConsole.gameConsoleMemory.getSelectedZukemons().getFirst()).isInstanceOf(Pikachu.class);
    }

    @Test
    void shouldFailWhenInvalidZukemonIsSelected() {
        assertThatThrownBy(() -> simpleGameConsole.runButtonCommand("select zukemon", 99))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("No Zukemon for type 99");
    }

    @Test
    void shouldSelectFightMode() {
        simpleGameConsole.runButtonCommand("select fightmode", 0);

        assertThat(simpleGameConsole.gameConsoleMemory.getSelectedFightMode()).isEqualTo(FightModeType.NORMAL);
    }

    @Test
    void shouldFailWhenInvalidFightModeIsSelected() {
        assertThatThrownBy(() -> simpleGameConsole.runButtonCommand("select fightmode", 99))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("99 is not a supported fightmode option");
    }

    @Test
    void shouldEquipZukemon() throws NoSuchFieldException, IllegalAccessException {
        List<Zukemon> selectedZukemons = simpleGameConsole.gameConsoleMemory.getSelectedZukemons();
        selectedZukemons.add(new Pikachu());

        simpleGameConsole.runButtonCommand("equip zukemon", "sword");
        simpleGameConsole.runButtonCommand("equip zukemon", "sword");

        Zukemon equippedZukemon = selectedZukemons.getLast();
        assertThat(equippedZukemon.equipped()).containsIgnoringCase("sword, sword");
        Sword sword = new Sword(null);
        Field field = sword.getClass().getDeclaredField("DAMAGE");
        field.setAccessible(true);
        int damage = (int)field.get(sword);
        assertThat(equippedZukemon.hit()).isEqualTo(new Pikachu().hit() + damage*2);
    }

    @Test
    void shouldFailIfNoZukemonToEquip() {
        assertThatThrownBy(() -> simpleGameConsole.runButtonCommand("equip zukemon", "sword"))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("no zukemon to equip");
    }

    @Test
    void shouldFailIfEquipmentIsInvalid() {
        List<Zukemon> selectedZukemons = simpleGameConsole.gameConsoleMemory.getSelectedZukemons();
        selectedZukemons.add(new Pikachu());

        assertThatThrownBy(() -> simpleGameConsole.runButtonCommand("equip zukemon", "axe"))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("equipment: axe doesnt exists");
    }

    @Test
    @DisplayName("normal fight should work when having correct parameters")
    void normalFight() {
        GameConsoleMemory gameConsoleMemoryMock = getMockedGameConsole(FightModeType.NORMAL);

        NormalFightMode normalFightModeMock = mock(NormalFightMode.class);
        FightModeFactory fightModeFactoryMock = mock(FightModeFactory.class);
        when(fightModeFactoryMock.createFight(any(), any())).thenReturn(normalFightModeMock);
        when(normalFightModeMock.fight(anyList())).thenReturn(new Blastoise());

        Command initiateFightModeCommand = new InitiateFightModeCommand(fightModeFactoryMock, "history.txt", gameConsoleMemoryMock);
        Map<String, Command> displayNameAndCommand = new HashMap<>();
        displayNameAndCommand.put("fight!", initiateFightModeCommand);

        GameConsole simpleGameConsoleMocked = new SimpleGameConsole(displayNameAndCommand, gameConsoleMemoryMock);
        Zukemon winner = (Zukemon) simpleGameConsoleMocked.runButtonCommand("fight!", null);

        assertThat(winner).isInstanceOf(Blastoise.class);
    }

    /*
    is this test pointless?
     */
    @Test
    @DisplayName("defend fight should work when having correct parameters")
    void defendFight() {
        GameConsoleMemory gameConsoleMemoryMock = getMockedGameConsole(FightModeType.DEFEND);

        DefendFightMode defendFightMode = mock(DefendFightMode.class);
        FightModeFactory fightModeFactoryMock = mock(FightModeFactory.class);
        when(fightModeFactoryMock.createFight(any(), any())).thenReturn(defendFightMode);
        when(defendFightMode.fight(anyList())).thenReturn(new Blastoise());

        Command initiateFightModeCommand = new InitiateFightModeCommand(fightModeFactoryMock, "history.txt", gameConsoleMemoryMock);
        Map<String, Command> displayNameAndCommand = new HashMap<>();
        displayNameAndCommand.put("fight!", initiateFightModeCommand);

        GameConsole simpleGameConsoleMocked = new SimpleGameConsole(displayNameAndCommand, gameConsoleMemoryMock);
        //simpleGameConsoleMocked.init();
        Zukemon winner = (Zukemon) simpleGameConsoleMocked.runButtonCommand("fight!", null);

        assertThat(winner).isInstanceOf(Blastoise.class);
    }

    /*
    is this test pointless?
    difference from other 2 is that having more than 2 zukemons in the list is actually meaningful
    useful for documentation purposes? but return value is also mocked..
     */
    @Test
    @DisplayName("royal rumble fight should work when having correct parameters")
    void RoyalRumbleFight() {
        List<Zukemon> selectedZukemons = new ArrayList<>();
        selectedZukemons.add(new Pikachu());
        selectedZukemons.add(new Blastoise());
        selectedZukemons.add(new Mudkip());

        GameConsoleMemory gameConsoleMemoryMock = mock(GameConsoleMemory.class);
        when(gameConsoleMemoryMock.getSelectedZukemons()).thenReturn(selectedZukemons);
        when(gameConsoleMemoryMock.getSelectedFightMode()).thenReturn(FightModeType.ROYAL_RUMBLE);

        RoyalRumbleFightMode royalRumble = mock(RoyalRumbleFightMode.class);
        FightModeFactory fightModeFactoryMock = mock(FightModeFactory.class);
        when(fightModeFactoryMock.createFight(any(), any())).thenReturn(royalRumble);
        when(royalRumble.fight(anyList())).thenReturn(new Blastoise());

        Command initiateFightModeCommand = new InitiateFightModeCommand(fightModeFactoryMock, "history.txt", gameConsoleMemoryMock);
        Map<String, Command> displayNameAndCommand = new HashMap<>();
        displayNameAndCommand.put("fight!", initiateFightModeCommand);

        GameConsole simpleGameConsoleMocked = new SimpleGameConsole(displayNameAndCommand, gameConsoleMemoryMock);
        //simpleGameConsoleMocked.init();
        Zukemon winner = (Zukemon) simpleGameConsoleMocked.runButtonCommand("fight!", null);

        assertThat(winner).isInstanceOf(Blastoise.class);
    }

    @Test
    void shouldFailWhenNotEnoughZukemons() {
        List<Zukemon> selectedZukemons = new ArrayList<>();

        GameConsoleMemory gameConsoleMemoryMock = mock(GameConsoleMemory.class);
        when(gameConsoleMemoryMock.getSelectedZukemons()).thenReturn(selectedZukemons);

        Command initiateFightModeCommand = new InitiateFightModeCommand(null, "history.txt", gameConsoleMemoryMock);
        Map<String, Command> displayNameAndCommand = new HashMap<>();
        displayNameAndCommand.put("fight!", initiateFightModeCommand);

        GameConsole simpleGameConsoleMocked = new SimpleGameConsole(displayNameAndCommand, gameConsoleMemoryMock);

        assertThatThrownBy(() -> simpleGameConsoleMocked.runButtonCommand("fight!", null))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("should have at least 2 zukemons selected");
    }

    @Test
    void shouldFailWhenFightModeIsNotSelected() {
        List<Zukemon> selectedZukemons = new ArrayList<>();
        selectedZukemons.add(new Pikachu());
        selectedZukemons.add(new Blastoise());

        GameConsoleMemory gameConsoleMemoryMock = mock(GameConsoleMemory.class);
        when(gameConsoleMemoryMock.getSelectedZukemons()).thenReturn(selectedZukemons);
        when(gameConsoleMemoryMock.getSelectedFightMode()).thenReturn(null);

        Command initiateFightModeCommand = new InitiateFightModeCommand(null, "history.txt", gameConsoleMemoryMock);
        Map<String, Command> displayNameAndCommand = new HashMap<>();
        displayNameAndCommand.put("fight!", initiateFightModeCommand);

        GameConsole simpleGameConsoleMocked = new SimpleGameConsole(displayNameAndCommand, gameConsoleMemoryMock);

        assertThatThrownBy(() -> simpleGameConsoleMocked.runButtonCommand("fight!", null))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("should have selected a fightMode");
    }

    @Test
    @DisplayName("simulate a complete flow without mocking inputs")
    void integrationFight() {
        simpleGameConsole.runButtonCommand("select zukemon", 151);
        simpleGameConsole.runButtonCommand("select zukemon", 25);

        simpleGameConsole.runButtonCommand("select fightmode", 0);

        Zukemon winner = (Zukemon) simpleGameConsole.runButtonCommand("fight!", null);
        assertThat(winner).isInstanceOf(Mew.class);
    }


    private GameConsoleMemory getMockedGameConsole(FightModeType fightModeType) {
        List<Zukemon> selectedZukemons = new ArrayList<>();
        selectedZukemons.add(new Pikachu());
        selectedZukemons.add(new Blastoise());

        GameConsoleMemory gameConsoleMemoryMock = mock(GameConsoleMemory.class);
        when(gameConsoleMemoryMock.getSelectedZukemons()).thenReturn(selectedZukemons);
        when(gameConsoleMemoryMock.getSelectedFightMode()).thenReturn(fightModeType);
        return gameConsoleMemoryMock;
    }
}