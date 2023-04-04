package it.polimi.ingsw.model.CommonGoals.Strategy;

import it.polimi.ingsw.model.modelSupport.enums.colorType;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class Double2x2GoalStrategyTest {

    @Test
    void goalCompleted() {

        /* Caso limite 10 carte con disposizione killer in verticale verso dx: */
        colorType[] firstLine = {colorType.GREEN,colorType.YELLOW,colorType.GREEN,colorType.YELLOW,colorType.GREEN};
        colorType[] secondLine = {colorType.GREEN,colorType.YELLOW,colorType.GREEN,colorType.YELLOW,colorType.GREEN};
        colorType[] thirdLine = {colorType.GREEN,colorType.BLUE,colorType.BLUE,colorType.YELLOW,colorType.BLUE};
        colorType[] fourthLine = {colorType.GREEN,colorType.BLUE,colorType.BLUE,colorType.BLUE,colorType.BLUE};
        colorType[] fifthLine = {colorType.GREEN,colorType.BLUE,colorType.BLUE,colorType.BLUE,colorType.YELLOW};
        colorType[] sixthLine = {colorType.GREEN,colorType.BLUE,colorType.BLUE,colorType.YELLOW,colorType.LIGHT_BLUE};

        /* Caso limite 10 carte con disposizione killer in orizzontale verso l'alto: */
        colorType[] firstLine = {colorType.GREEN,colorType.BLUE,colorType.BLUE,colorType.YELLOW,colorType.GREEN};
        colorType[] secondLine = {colorType.BLUE,colorType.BLUE,colorType.BLUE,colorType.BLUE,colorType.GREEN};
        colorType[] thirdLine = {colorType.BLUE,colorType.BLUE,colorType.BLUE,colorType.BLUE,colorType.YELLOW};
        colorType[] fourthLine = {colorType.GREEN,colorType.YELLOW,colorType.GREEN,colorType.YELLOW,colorType.GREEN};
        colorType[] fifthLine = {colorType.GREEN,colorType.YELLOW,colorType.GREEN,colorType.YELLOW,colorType.GREEN};
        colorType[] sixthLine = {colorType.GREEN,colorType.YELLOW,colorType.GREEN,colorType.YELLOW,colorType.GREEN};


        ArrayList<colorType[]> testColors = new ArrayList<>();
        testColors.add(firstLine);
        testColors.add(secondLine);
        testColors.add(thirdLine);
        testColors.add(fourthLine);
        testColors.add(fifthLine);
        testColors.add(sixthLine);
        BoardCard[][] prova = new BoardCard[6][5];
        for(int i = 0; i < 6; i++){
            for(int j = 0; j < 5; j++){
                BoardCard piece = new BoardCard(testColors.get(i)[j]);
                prova[i][j] = piece;
            }
        }

        System.out.println("Stampo 'prova:'");
        for(int i = 0; i < 6; i++){
            for(int j = 0;j < 5; j++){
                System.out.print(prova[i][j].getColor() + "  ");
            }
            System.out.print("\n");
        }
        System.out.println("\n");
        System.out.println("reachedFirstGoal:\n" + reachedFirstGoal + "\n");
        System.out.println("reachedSecondGoal:\n" + reachedSecondGoal + "\n");

    }
}