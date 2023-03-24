package TicTacToe.TxtBased.src;

import java.util.*;
public class App {
	public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        // stores data about the board
        char t1, t2, t3, t4, t5, t6, t7, t8, t9;
        t1 = t2 = t3 = t4 = t5 = t6 = t7 = t8 = t9 = ' ';
        boolean playerX = true;
        // draws the board with same values for each tile
        System.out.printf("%c | %c | %c\n", t1, t2, t3);
        System.out.printf("---------\n");
        System.out.printf("%c | %c | %c\n", t4, t5, t6);
        System.out.printf("---------\n");
        System.out.printf("%c | %c | %c\n", t7, t8, t9);
        int gameOver = 0;
        boolean tie = true;
        // collect int from user to for the place to place piece
        while (gameOver == 0 && (t1 == ' ' || t2 == ' ' || t3 == ' ' || t4 == ' ' || t5 == ' ' || t6 == ' ' || t7 == ' '
                || t8 == ' ' || t9 == ' ')) {
            int input = in.nextInt();
            // check which tile is entered and place a piece or ask again
            switch (input) {
            case 1:
                if (t1 != ' '){
                    System.out.println("This tile is taken");
                    playerX = !playerX;
                    break;
                }
                if (playerX == true)
                    t1 = 'X';
                else
                    t1 = 'O';
                if (t1 == 'X' && t2 == 'X' && t3 == 'X' || t1 == 'X' && t4 == 'X' && t7 == 'X'
                        || t1 == 'X' && t5 == 'X' && t9 == 'X') {
                    System.out.println("PLAYER X WINS!");
                    gameOver = 1;
                    tie = false;
                }
                if (t1 == 'O' && t2 == 'O' && t3 == 'O' || t1 == 'O' && t4 == 'O' && t7 == 'O'
                        || t1 == 'O' && t5 == 'O' && t9 == 'O') {
                    System.out.println("PLAYER O WINS!");
                    gameOver = 1;
                    tie = false;
                }
                break;
            case 2:
                if (t2 != ' '){
                    System.out.println("This tile is taken");
                    playerX = !playerX;
                    break;
                }
                if (playerX == true)
                    t2 = 'X';
                else
                    t2 = 'O';
                if (t2 == 'X' && t1 == 'X' && t3 == 'X' || t2 == 'X' && t5 == 'X' && t8 == 'X') {
                    System.out.println("PLAYER X WINS!");
                    gameOver = 1;
                    tie = false;
                }
                if (t2 == 'O' && t1 == 'O' && t3 == 'O' || t2 == 'O' && t5 == 'O' && t8 == 'O') {
                    System.out.println("PLAYER O WINS");
                    gameOver = 1;
                    tie = false;
                }
                break;
            case 3:
                if (t3 != ' '){
                    System.out.println("This tile is taken");
                    playerX = !playerX;
                    break;
                }
                if (playerX == true)
                    t3 = 'X';
                else
                    t3 = 'O';
                if (t3 == 'X' && t1 == 'X' && t2 == 'X' || t3 == 'X' && t6 == 'X' && t9 == 'X'
                        || t3 == 'X' && t5 == 'X' && t7 == 'X') {
                    System.out.println("PLAYER X WINS!");
                    gameOver = 1;
                    tie = false;
                }
                if (t3 == 'O' && t1 == 'O' && t2 == 'O' || t3 == 'O' && t6 == 'O' && t9 == 'O'
                        || t3 == 'O' && t5 == 'O' && t7 == 'O') {
                    System.out.println("PLAYER O WINS");
                    gameOver = 1;
                    tie = false;
                }
                break;
            case 4:
                if (t4 != ' '){
                    System.out.println("This tile is taken");
                    playerX = !playerX;
                    break;
                }
                if (playerX == true)
                    t4 = 'X';
                else
                    t4 = 'O';
                if (t4 == 'X' && t1 == 'X' && t7 == 'X' || t4 == 'X' && t5 == 'X' && t6 == 'X') {
                    System.out.println("PLAYER X WINS!");
                    gameOver = 1;
                    tie = false;
                }
                if (t4 == 'O' && t1 == 'O' && t7 == 'O' || t4 == 'O' && t5 == 'O' && t6 == 'O') {
                    System.out.println("PLAYER O WINS");
                    gameOver = 1;
                    tie = false;
                }
                break;
            case 5:
                if (t5 != ' '){
                    System.out.println("This tile is taken");
                    playerX = !playerX;
                    break;
                }
                if (playerX == true)
                    t5 = 'X';
                else
                    t5 = 'O';
                if (t5 == 'X' && t4 == 'X' && t6 == 'X' || t5 == 'X' && t2 == 'X' && t8 == 'X'
                        || t5 == 'X' && t1 == 'X' && t9 == 'X' || t3 == 'X' && t7 == 'X') {
                    System.out.println("PLAYER X WINS!");
                    gameOver = 1;
                    tie = false;
                }
                if (t5 == 'O' && t4 == 'O' && t6 == 'O' || t5 == 'O' && t2 == 'O' && t8 == 'O'
                        || t5 == 'O' && t1 == 'O' && t9 == 'O' || t3 == 'O' && t7 == 'O') {
                    System.out.println("PLAYER O WINS");
                    gameOver = 1;
                    tie = false;
                }
                break;
            case 6:
                if (t6 != ' '){
                    System.out.println("This tile is taken");
                    playerX = !playerX;
                    break;
                }
                if (playerX == true)
                    t6 = 'X';
                else
                    t6 = 'O';
                if (t6 == 'X' && t3 == 'X' && t9 == 'X' || t6 == 'X' && t4 == 'X' && t5 == 'X') {
                    System.out.println("PLAYER X WINS!");
                    gameOver = 1;
                    tie = false;
                }
                if (t6 == 'O' && t3 == 'O' && t9 == 'O' || t6 == 'O' && t4 == 'O' && t5 == 'O') {
                    System.out.println("PLAYER O WINS");
                    gameOver = 1;
                    tie = false;
                }
                break;
            case 7:
                if (t7 != ' '){
                    System.out.println("This tile is taken");
                    playerX = !playerX;
                    break;
                }
                if (playerX == true)
                    t7 = 'X';
                else
                    t7 = 'O';
                if (t7 == 'X' && t1 == 'X' && t4 == 'X' || t7 == 'X' && t8 == 'X' && t9 == 'X'
                        || t7 == 'X' && t5 == 'X' && t3 == 'X') {
                    System.out.println("PLAYER X WINS!");
                    gameOver = 1;
                    tie = false;
                }
                if (t7 == 'O' && t1 == 'O' && t4 == 'O' || t7 == 'O' && t8 == 'O' && t9 == 'O'
                        || t7 == 'O' && t5 == 'O' && t3 == 'O') {
                    System.out.println("PLAYER O WINS");
                    gameOver = 1;
                    tie = false;
                }
                break;
            case 8:
                if (t8 != ' '){
                    System.out.println("This tile is taken");
                    playerX = !playerX;
                    break;
                }
                if (playerX == true)
                    t8 = 'X';
                else
                    t8 = 'O';
                if (t8 == 'X' && t7 == 'X' && t9 == 'X' || t8 == 'X' && t2 == 'X' && t5 == 'X') {
                    System.out.println("PLAYER X WINS!");
                    gameOver = 1;
                    tie = false;
                }
                if (t8 == 'O' && t7 == 'O' && t9 == 'O' || t8 == 'O' && t2 == 'O' && t5 == 'O') {
                    System.out.println("PLAYER O WINS");
                    gameOver = 1;
                    tie = false;
                }
                break;
            case 9:
                if (t9 != ' '){
                    System.out.println("This tile is taken");
                    playerX = !playerX;
                    break;
                }
                if (playerX == true)
                    t9 = 'X';
                else
                    t9 = 'O';
                if (t9 == 'X' && t1 == 'X' && t5 == 'X' || t9 == 'X' && t3 == 'X' && t6 == 'X'
                        || t9 == 'X' && t7 == 'X' && t8 == 'X') {
                    System.out.println("PLAYER X WINS!");
                    gameOver = 1;
                    tie = false;
                }
                if (t9 == 'O' && t1 == 'O' && t5 == 'O' || t9 == 'O' && t3 == 'O' && t6 == 'O'
                        || t9 == 'O' && t7 == 'O' && t8 == 'O') {
                    System.out.println("PLAYER O WINS");
                    gameOver = 1;
                    tie = false;
                }
                break;

            default:
                System.out.println("Not a valid tile number");
            }
            System.out.printf("%c | %c | %c\n", t1, t2, t3);
            System.out.printf("---------\n");
            System.out.printf("%c | %c | %c\n", t4, t5, t6);
            System.out.printf("---------\n");
            System.out.printf("%c | %c | %c\n", t7, t8, t9);
            playerX = !playerX;
        }
        if (tie == true)
            System.out.println("Tie");
        
        in.close();
    }
}
