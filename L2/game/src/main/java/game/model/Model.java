package game.model;

import java.util.HashMap;

public class Model {
    HashMap<String, Game> games;

    public Model() {
        games = new HashMap<String, Game>();

    }

    public Game getGame(String gameId) {
        return games.get(gameId);
    }

    public void addGame(String gameId, Game game) {
        games.put(gameId, game);
    }

    public void removeGame(String gameId) {
        games.remove(gameId);
    }

    public void printGames() {
        System.out.println(games.keySet().toString());
    }
}
