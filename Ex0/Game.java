import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
import static java.time.temporal.TemporalAdjusters.firstDayOfYear;
import static java.time.temporal.TemporalAdjusters.lastDayOfYear;


interface GameCharacter {
    String getDescription();
    int getAttackForce();
    int getDefenseForce();
}

class Person implements GameCharacter {

    public static final int ATTACK_FORCE = 15;
    public static final int DEFENSE_FORCE = 10;

    @Override
    public String getDescription() {
        return "I am a person";
    }

    @Override
    public int getAttackForce() {
        return ATTACK_FORCE;
    }

    @Override
    public int getDefenseForce() {
        return DEFENSE_FORCE;
    }
}

class Dragon implements GameCharacter {

    public static final int ATTACK_FORCE = 20;
    public static final int DEFENSE_FORCE = 15;

    @Override
    public String getDescription() {
        return "I am a dragon";
    }

    @Override
    public int getAttackForce() {
        return ATTACK_FORCE;
    }

    @Override
    public int getDefenseForce() {
        return DEFENSE_FORCE;
    }
}

interface CharacterDecorator extends GameCharacter {}

class CharacterSword implements CharacterDecorator{
    GameCharacter gameCharacter;
    private String sword;

    public CharacterSword(GameCharacter GameCharacter, String swords){
        this.gameCharacter = GameCharacter;
        this.sword = swords;

    }
    @Override
    public String getDescription(){
        return this.gameCharacter.getDescription() + "with " + this.sword;
    }

    @Override
    public int getAttackForce() {
        return this.gameCharacter.getAttackForce() * 2;
    }

    @Override
    public int getDefenseForce() {
        return this.gameCharacter.getDefenseForce();
    }
}

class CharacterInvisibleShield implements GameCharacter{
    GameCharacter gameCharacter;

    public CharacterInvisibleShield(GameCharacter gameCharacter){
        this.gameCharacter = gameCharacter;
    }

    @Override
    public String getDescription() {
        return this.gameCharacter.getDescription();
    }

    @Override
    public int getAttackForce() {
        return this.gameCharacter.getAttackForce();
    }

    @Override
    public int getDefenseForce() {
        return this.gameCharacter.getDefenseForce() + 10;
    }
}

class CharacterHelmet implements GameCharacter{
    GameCharacter gameCharacter;
    private String Helmet;
    public CharacterHelmet(GameCharacter gameCharacter, String helmet){
        this.gameCharacter = gameCharacter;
        this.Helmet = helmet;
    }

    @Override
    public String getDescription() {
        return this.gameCharacter.getDescription() + "with " + this.Helmet;
    }

    @Override
    public int getAttackForce() {
        return this.gameCharacter.getAttackForce();
    }

    @Override
    public int getDefenseForce() {
        return this.gameCharacter.getDefenseForce() + 10;
    }
}

/*add CharacterSword, CharacterHelmet and CharacterInvisibleShield decorators HERE!!!*/

public class Game {
    public static boolean attack(GameCharacter attacker, GameCharacter defender){
        return attacker.getAttackForce()-defender.getDefenseForce()>0;
    }

    public static void main(String[] args) {
        //Initialize hero with two swords and a helmet!
        GameCharacter hero=new Person();
        hero = new CharacterSword(hero, "swords");
        hero = new CharacterSword(hero, "swords");
        hero = new CharacterHelmet(hero, "helmet");
        //Initialize evilDragon with an invisible shield!
        GameCharacter evilDragon=new Dragon();
        evilDragon = new CharacterInvisibleShield(evilDragon);

        System.out.println(hero.getDescription());
        System.out.println(evilDragon.getDescription());

        if(attack(hero,evilDragon)){
            System.out.println("The hero defeated the evil dragon");
        }
    }
}


