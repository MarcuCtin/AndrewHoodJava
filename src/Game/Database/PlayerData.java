package Game.Database;

public class PlayerData {
    private int id;
    private String name;
    private String lastPosition;
    private int life;
    private int score;
    public PlayerData(int id, String name, String lastPosition, int life) {
        this.id = id;
        this.name = name;
        this.lastPosition = lastPosition;
        this.life = life;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLastPosition() {
        return lastPosition;
    }
    public int getScore() {
        return score;
    }
    public int getLife() {
        return life;
    }
}