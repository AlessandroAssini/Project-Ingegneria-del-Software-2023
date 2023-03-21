package it.polimi.ingsw;

import java.util.ArrayList;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.helpers.Pair;
import it.polimi.ingsw.model.modelSupport.BoardCard;
import it.polimi.ingsw.model.modelSupport.Player;
import it.polimi.ingsw.model.modelSupport.enums.colorType;
import it.polimi.ingsw.model.modelSupport.exceptions.UnselectableCardException;

public class Main {
    public static void main(String[] args) {
        ArrayList<Player> players = new ArrayList<>();
        players.add(new Player("Max"));
        players.add(new Player("Asso"));
        players.add(new Player("Rick"));
        players.add(new Player("Chicco"));
        Game myGame = new Game(players);
        ArrayList<Pair<Integer, Integer>> sel = new ArrayList<>();
        sel.add(new Pair<>(0, 3));
        sel.add(new Pair<>(0, 4));
        System.out.println("\n\n\nNEW PLAYER 1");
        try {
            myGame.selectedCards(sel);
        } catch (UnselectableCardException e) {
            // TODO Auto-generated catch block
            System.out.println("Not sel");
            e.printStackTrace();
        }
        ArrayList<BoardCard> crds = new ArrayList<>();
        crds.add(new BoardCard(colorType.BLUE));
        crds.add(new BoardCard(colorType.PURPLE));
        myGame.selectedColumn(crds, 1);

        //---- new round
        System.out.println("\n\n\nNEW PLAYER 2");
        sel = new ArrayList<>();
        sel.add(new Pair<>(1, 4));
        sel.add(new Pair<>(1, 5));

        try {
            myGame.selectedCards(sel);
        } catch (UnselectableCardException e) {
            // TODO Auto-generated catch block
            System.out.println("Not sel");
            e.printStackTrace();
        }
        crds = new ArrayList<>();
        crds.add(new BoardCard(colorType.LIGHT_BLUE));
        crds.add(new BoardCard(colorType.YELLOW));
        myGame.selectedColumn(crds, 1);

        //---- new round
        System.out.println("\n\n\nNEW PLAYER 3");
        sel = new ArrayList<>();
        sel.add(new Pair<>(4, 0));
        sel.add(new Pair<>(5, 0));

        try {
            myGame.selectedCards(sel);
        } catch (UnselectableCardException e) {
            // TODO Auto-generated catch block
            System.out.println("Not sel");
            e.printStackTrace();
        }
        crds = new ArrayList<>();
        crds.add(new BoardCard(colorType.LIGHT_BLUE));
        crds.add(new BoardCard(colorType.YELLOW));
        myGame.selectedColumn(crds, 1);

        //---- new round
        System.out.println("\n\n\nNEW PLAYER 4");
        sel = new ArrayList<>();
        sel.add(new Pair<>(2, 4));
        sel.add(new Pair<>(2, 5));
        sel.add(new Pair<>(2, 6));

        try {
            myGame.selectedCards(sel);
        } catch (UnselectableCardException e) {
            // TODO Auto-generated catch block
            System.out.println("Not sel");
            e.printStackTrace();
        }
        crds = new ArrayList<>();
        crds.add(new BoardCard(colorType.BLUE));
        crds.add(new BoardCard(colorType.YELLOW));
        myGame.selectedColumn(crds, 1);

        //---- new round
        System.out.println("\n\n\nNEW PLAYER 12");
        sel = new ArrayList<>();
        sel.add(new Pair<>(1, 3));
        sel.add(new Pair<>(2, 3));

        try {
            myGame.selectedCards(sel);
        } catch (UnselectableCardException e) {
            // TODO Auto-generated catch block
            System.out.println("Not sel");
            e.printStackTrace();
        }
        crds = new ArrayList<>();
        crds.add(new BoardCard(colorType.BLUE));
        crds.add(new BoardCard(colorType.YELLOW));
        myGame.selectedColumn(crds, 1);

        //---- new round
        System.out.println("\n\n\nNEW PLAYER 22");
        sel = new ArrayList<>();
        sel.add(new Pair<>(3, 3));
        sel.add(new Pair<>(3, 4));

        try {
            myGame.selectedCards(sel);
        } catch (UnselectableCardException e) {
            // TODO Auto-generated catch block
            System.out.println("Not sel");
            e.printStackTrace();
        }
        crds = new ArrayList<>();
        crds.add(new BoardCard(colorType.YELLOW));
        crds.add(new BoardCard(colorType.LIGHT_BLUE));
        myGame.selectedColumn(crds, 3);

    }
} // ciao