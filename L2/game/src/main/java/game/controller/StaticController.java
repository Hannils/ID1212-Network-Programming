package game.controller;

import game.View;

public class StaticController {
    View view;

    public StaticController(View view) {
        this.view = view;
    }

    public boolean canHandle(String method, String s) {
        return false;
    }

    public void handle(String method, String s) {
    }
}
