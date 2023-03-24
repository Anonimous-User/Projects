package Finished.TicTacToe.src;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.Timer;
import java.io.*;
import javax.swing.*;
import javax.imageio.*;
import sun.audio.*;

//tourny mode? board size change?
 
public class TicTacToe extends JPanel implements MouseListener, KeyListener{
    //variables
    private static Image title;
    private static Image[] GameBackGround;
    private static Image P1;
    private static Image P2;
    private static AudioStream click;
    private static AudioStream win;
   
    
    private static String[][] board = {{"_", "_", "_"}, {"_", "_", "_"}, {"_", "_", "_"}};
    private static int screen = 1;
    //titlepage = 1; board graphics = 2; end page = 5; AI selection = 6;
    private int curbase;
    private static String[] score = {"undecided", "0", "0"};//winner, P1 score, P2 score
    private static String[] winSeq = {"", ""};//winner, win seq
    private static int gamemode = -1;
    //AIfirst = 2; 1V1 = 1; AIsecond = 0;
    private static int AImode = -1;
    //easy = 1; hard = 2;
    private static String AISymbol;
    private static String plyr = "X";
    private static int mousePressedX = -1;
    private static int mousePressedY = -1;
    Timer timer = new Timer();

    //main
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setBounds(0, 0, 615, 630);
        frame.setTitle("Tic-Tac-Toe: The Roast Me Version");
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        TicTacToe game = new TicTacToe();
        frame.add(game);
        frame.setVisible(true);
    }
    //reset all values
    public void NewGame(){
        timer.cancel();
        time = -1;
        screen = 1;
        board = new String[][]{{"_", "_", "_"}, {"_", "_", "_"}, {"_", "_", "_"}};
        score = new String[]{"undecided", "0", "0"};
        winSeq = new String[]{"", ""};
        gamemode = -1;
        AImode = -1;
        AISymbol = null;
        plyr = "X";
    }
    //constructor
    public TicTacToe() {
        addMouseListener(this);
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        GameBackGround = new Image[12];
        try {
            title = ImageIO.read(new File(".\\src\\randothing.png"));
            P1 = ImageIO.read(new File(".\\src\\download.png"));
            P2 = ImageIO.read(new File(".\\src\\download(1).png"));
            String smth;
            for(int i=0; i<12; i++){
                smth = "board"+i;
                GameBackGround[i] = ImageIO.read(new File(".\\src\\"+smth+".png"));
            }
            click = new AudioStream(new FileInputStream(".\\src\\clicksound.wav"));
        } catch(IOException e) {}
    }
    //paint screen
    public void paint(Graphics g) {
        if(screen==1) {
            titlePage(g);
        } else if(screen==2) {
            drawBase(g);
            screen = 3;
        } else if(screen==4){
            drawWin(g);
        } else if(screen==5){
            endScreen(g);
        } else if(screen==6){
            AIselection(g);
        }
        if(screen==3){
            drawCur(g);
        }
    }
    //AI selection screen
    public void AIselection(Graphics g){
        g.setColor(Color.white);
        g.fillRect(0, 0, 600, 600);
        g.setColor(Color.black);
        g.setFont(new Font("Arial", Font.BOLD, 50));
        g.drawString("Choose your AI settings", 15, 75);
        g.drawString("Difficulty", 200, 150);
        g.drawString("Move", 235, 330);
        //quality of life changes
        if(AImode==1){
            g.setColor(Color.MAGENTA);
        } else{
            g.setColor(Color.BLACK);
        }
        g.fillRect(50, 170, 200, 100);
        if(AImode==2){
            g.setColor(Color.MAGENTA);
        } else{
            g.setColor(Color.BLACK);
        }
        g.fillRect(350, 170, 200, 100);
        if(gamemode==0){
            g.setColor(Color.MAGENTA);
        } else{
            g.setColor(Color.BLACK);
        }
        g.fillRect(50, 350, 200, 100);
        if(gamemode==2){
            g.setColor(Color.MAGENTA);
        } else{
            g.setColor(Color.BLACK);
        }
        g.fillRect(350, 350, 200, 100);
        g.setColor(Color.BLACK);
        g.fillRect(200, 470, 200, 100);
        g.setColor(Color.WHITE);
        g.drawString("X", 130, 415);
        g.drawString("O", 430, 415);
        g.drawString("Dumb", 80, 235);
        g.drawString("Normal", 367, 235);
        g.setColor(Color.GREEN);
        g.drawString("READY", 215, 540);
    }
    //title page
    public void titlePage(Graphics g) {
        g.drawImage(title, 0, 0, null);
        g.setColor(Color.BLACK);
        g.fillRect(75, 400, 200, 75);
        g.fillRect(325, 400, 200, 75);
        g.setColor(Color.WHITE);
        g.drawString("For Lonely People", 125, 440);
        g.drawString("For People With Friends", 363, 440);
    }
    //board base
    public void drawBase(Graphics g) {
    	curbase = (int)(12*Math.random());
        g.drawImage(GameBackGround[curbase], 0, 0, null);
        g.setColor(Color.BLACK);
        g.drawLine(200, 0, 200, 600);
        g.drawLine(400, 0, 400, 600);
        g.drawLine(0, 200, 600, 200);
        g.drawLine(0, 400, 600, 400);
    }
    //draws current board situation
    public void drawCur(Graphics g){
        for(int i=0; i<3; i++){
            for(int j=0; j<3; j++){
                if(board[i][j].equals("X")){
                    g.drawImage(P1, j*200, i*200, null);
                } else if(board[i][j].equals("O")){
                    g.drawImage(P2, j*200, i*200, null);
                }
            }
        }
    }
    //draw winning sequence
    public void drawWin(Graphics g){
        g.drawImage(GameBackGround[curbase], 0, 0, null);
        if(!winSeq[1].equals("")){
            try {
                win = new AudioStream(new FileInputStream(".\\TicTacToe\\src\\winsound.wav"));
            } catch(IOException e) {}
            AudioPlayer.player.start(win);
        }
        Image win = null;
        if(score[0].equals("P1")) {
        	win = P1;
        } else if(score[0].equals("P2")) {
        	win = P2;
        }
        //checks for how game is won, aka win seq, and draws it out
        if(winSeq[1].equals("")){ /**tie is skipped */
            screen=5;
            repaint();
        } else if(winSeq[1].substring(0, 1).equals("H")){
            for(int i=0; i<3; i++){
                g.drawImage(win, i*200, Integer.parseInt(winSeq[1].substring(1, 2))*200, null);
            }
        } else if(winSeq[1].substring(0, 1).equals("V")){
            for(int i=0; i<3; i++){
                g.drawImage(win, Integer.parseInt(winSeq[1].substring(1, 2))*200, i*200, null);
            }
        } else if(winSeq[1].equals("D1")){
            for(int i=0; i<3; i++){
                g.drawImage(win, i*200, i*200, null);
            }
        } else if(winSeq[1].equals("D2")){
            for(int i=1; i<4; i++){
                g.drawImage(win, 600-i*200, (i-1)*200, null);
            }
        }
    }
    //end screen
    public void endScreen(Graphics g) {
        g.drawImage(GameBackGround[(int)(12*Math.random())], 0, 0, null);
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 50));
        if(!score[0].equals("tie")) {
            g.drawString(score[0] + " wins", 210, 200);
        } else {
            g.drawString("Tie", 265, 200);
        }
        g.setFont(new Font("Arial", 0, 25));
        //just me having some fun :)
        if(gamemode==1){
            if((Integer.parseInt(score[1])==0||Integer.parseInt(score[2])==0)&&Math.abs(Integer.parseInt(score[1])-Integer.parseInt(score[2]))==1){
                g.setColor(Color.yellow);
                g.fillRect(0, 0, 425, 33);
                g.setColor(Color.black);
                g.drawString("And the humiliation begins... Please?", 0, 25);
            } else if(Math.abs(Integer.parseInt(score[1])-Integer.parseInt(score[2]))>=10){
                g.setColor(Color.yellow);
                g.fillRect(0, 0, 250, 33);
                g.setColor(Color.black);
                g.drawString("\"Hello? Brain?\" - Felix", 0, 25);
            } else if(Math.abs(Integer.parseInt(score[1])-Integer.parseInt(score[2]))==5){
                g.setColor(Color.yellow);
                g.fillRect(0, 0, 320, 33);
                g.setColor(Color.black);
                g.drawString("Imagine being this bad smh.", 0, 25);
            }
        } else{
            if(Math.abs(Integer.parseInt(score[1])-Integer.parseInt(score[2]))>=10){
                g.setColor(Color.yellow);
                g.fillRect(0, 0, 240, 33);
                g.setColor(Color.black);
                g.drawString("...Touch some grass.", 0, 25);
            } else if(Math.abs(Integer.parseInt(score[1])-Integer.parseInt(score[2]))>=5){
                g.setColor(Color.yellow);
                g.fillRect(0, 0, 285, 33);
                g.setColor(Color.black);
                g.drawString("Hmmmmm... Interesting.", 0, 25);
            } else{
                g.setColor(Color.yellow);
                g.fillRect(0, 0, 290, 33);
                g.setColor(Color.black);
                g.drawString("No friends moment? Heh.", 0, 25);
            }
        }
        g.drawString("P1: "+score[1]+"      P2: "+score[2], 220, 300);
        g.fillRect(50, 400, 200, 100);
        g.fillRect(350, 400, 200, 100);
        g.setColor(Color.WHITE);
        g.drawString("play again", 90, 458);
        g.drawString("scared to lose", 373, 458);
    }
    //switches between players
    public String players(String original){
        if(original=="X"){
            original="O";
        } else {
            original="X";
        }
        return original;
    }
    //Win Lose Tie
    public static String[] WLT(String[][] board){
        //checks for win and tie and also returns variable reprersenting the winning sequence
        //vertical
        for(int i=0; i<3; i++){
            if(board[0][i]==board[1][i]&&board[1][i]==board[2][i]){
                if(board[0][i].contentEquals("X")){
                    return new String[]{"P1", "V"+i};
                } else if(board[0][i].contentEquals("O")){
                    return new String[]{"P2", "V"+i};
                }
            }
        }
        //horizontal
        for(int i=0; i<3; i++){
            if(board[i][0]==board[i][1]&&board[i][1]==board[i][2]){
                if(board[i][0].contentEquals("X")){
                    return new String[]{"P1", "H"+i};
                } else if(board[i][0].contentEquals("O")){
                    return new String[]{"P2", "H"+i};
                }
            }
        }
        //diagnal
        if(board[0][0]==board[1][1]&&board[1][1]==board[2][2]){
            if(board[0][0].contentEquals("X")){
                return new String[]{"P1", "D1"};
            } else if(board[0][0].contentEquals("O")){
                return new String[]{"P2", "D1"};
            }
        }
        //diagnal
        if(board[0][2]==board[1][1]&&board[1][1]==board[2][0]){
            if(board[0][2].contentEquals("X")){
                return new String[]{"P1", "D2"};
            } else if(board[0][2].contentEquals("O")){
                return new String[]{"P2", "D2"};
            }
        }
        if((!Arrays.asList(board[0]).contains("_"))&&(!Arrays.asList(board[1]).contains("_"))&&(!Arrays.asList(board[2]).contains("_"))) {
            return new String[]{"tie", ""};
        }
        return new String[]{"", ""};
    }
    //score-board of the game
    public String[] ScoreBoard(String[] WinnerAndScore){
        String winner = WinnerAndScore[0];
        String p1 = WinnerAndScore[1];
        String p2 = WinnerAndScore[2];
        int P1score = Integer.parseInt(p1);
        int P2score = Integer.parseInt(p2);
        if(winner.contentEquals("P1")){
            P1score++;
        } else if(winner.contentEquals("P2")){
            P2score++;
        }
        WinnerAndScore[1]=String.valueOf(P1score);
        WinnerAndScore[2]=String.valueOf(P2score);
        return WinnerAndScore;
    }
    //AI system
    public void AI(int[] position) {
        try{
            if(position[0]!=-1) {
                HumanVSHuman(position);
                if(AImode==1){
                    position = AmericanizedAI();
                } else if(AImode==2){
                    position = AsianizedAI();
                }
                //checks if game is won prior to AI turn
                if(!(winSeq[0].equals("P1")||winSeq[0].equals("P2")||winSeq[0].equals("tie"))||position==null) {
                    HumanVSHuman(position);
                }
            }
        } catch(NullPointerException e){}
       
    }
    //dumb AI, goes random
    public static int[] AmericanizedAI(){
        int[] arr = new int[]{(int)(Math.random()*3), (int)(Math.random()*3)};
        try {
            if(!(winSeq[0].equals("P1")||winSeq[0].equals("P2")||winSeq[0].equals("tie"))) {
                while(!board[arr[0]][arr[1]].equals("_")) {
                    arr = new int[]{(int)(Math.random()*3), (int)(Math.random()*3)};
                }
            }
        } catch(NullPointerException e) {}
        return arr;
    }
    //normal/smart AI, acts like a human, favours winning over defence
    public static int[] AsianizedAI(){
        //check if two are same in line
        //vertical
        int[] arr = null;
        for(int i=0; i<3; i++){
            if(board[0][i]==board[1][i]&&!(board[0][i].equals("_"))){
                if(board[2][i].equals("_")){
                    if(board[0][i].equals(AISymbol)){
                        return new int[]{2, i};
                    } else{
                        arr = new int[]{2, i};
                    }
                }
            }
            if(board[1][i]==board[2][i]&&!(board[1][i].equals("_"))){
                if(board[0][i].equals("_")){
                    if(board[1][i].equals(AISymbol)){
                        return new int[]{0, i};
                    } else{
                        arr = new int[]{0, i};
                    }
                }
            }
            if(board[0][i]==board[2][i]&&!(board[0][i].equals("_"))){
                if(board[1][i].equals("_")){
                    if(board[0][i].equals(AISymbol)){
                        return new int[]{1, i};
                    } else{
                        arr = new int[]{1, i};
                    }
                }
            }
        }
        //horizontal
        for(int i=0; i<3; i++){
            if(board[i][0]==board[i][1]&&!(board[i][0].equals("_"))){
                if(board[i][2].equals("_")){
                    if(board[i][0].equals(AISymbol)){
                        return new int[]{i, 2};
                    } else{
                        arr = new int[]{i, 2};
                    }
                }
            }
            if(board[i][1]==board[i][2]&&!(board[i][1].equals("_"))){
                if(board[i][0].equals("_")){
                    if(board[i][1].equals(AISymbol)){
                        return new int[]{i, 0};
                    } else{
                        arr = new int[]{i, 0};
                    }
                }
            }
            if(board[i][0]==board[i][2]&&!(board[i][0].equals("_"))){
                if(board[i][1].equals("_")){
                    if(board[i][0].equals(AISymbol)){
                        return new int[]{i, 1};
                    } else{
                        arr = new int[]{i, 1};
                    }
                }
            }
        }
        //diagnal
        if(board[0][0]==board[1][1]&&!(board[0][0].equals("_"))){
            if(board[2][2].equals("_")){
                if(board[0][0].equals(AISymbol)){
                    return new int[]{2, 2};
                } else{
                    arr = new int[]{2, 2};
                }
            }
        }
        if(board[1][1]==board[2][2]&&!(board[1][1].equals("_"))){
            if(board[0][0].equals("_")){
                if(board[1][1].equals(AISymbol)){
                    return new int[]{0, 0};
                } else{
                    arr = new int[]{0, 0};
                }
            }
        }
        if(board[0][0]==board[2][2]&&!(board[0][0].equals("_"))){
            if(board[1][1].equals("_")){
                if(board[0][0].equals(AISymbol)){
                    return new int[]{1, 1};
                } else{
                    arr = new int[]{1, 1};
                }
            }
        }
        //diagnal
        if(board[0][2]==board[1][1]&&!(board[0][2].equals("_"))){
            if(board[2][0].equals("_")){
                if(board[0][2].equals(AISymbol)){
                    return new int[]{2, 0};
                } else{
                    arr = new int[]{2, 0};
                }
            }
        }
        if(board[1][1]==board[2][0]&&!(board[1][1].equals("_"))){
            if(board[0][2].equals("_")){
                if(board[1][1].equals(AISymbol)){
                    return new int[]{0, 2};
                } else{
                    arr = new int[]{0, 2};
                }
            }
        }
        if(board[0][2]==board[2][0]&&!(board[0][2].equals("_"))){
            if(board[1][1].equals("_")){
                if(board[0][2].equals(AISymbol)){
                    return new int[]{1, 1};
                } else{
                    arr = new int[]{1, 1};
                }
            }
        }
        //if none are about to make line, go random
        try {
            if(!(winSeq[0].equals("P1")||winSeq[0].equals("P2")||winSeq[0].equals("tie"))) {
                if(arr==null){
                    arr = new int[]{(int)(Math.random()*3), (int)(Math.random()*3)};
                    while(!board[arr[0]][arr[1]].equals("_")) {
                        arr = new int[]{(int)(Math.random()*3), (int)(Math.random()*3)};
                    }
                }
            }
        } catch(NullPointerException e) {}
        return arr;
    }
    //human vs human
    public void HumanVSHuman(int[] position) {
        if(position[0]!=-1){
            board[position[0]][position[1]]=plyr;
            try {
                click = new AudioStream(new FileInputStream(".\\TicTacToe\\src\\clicksound.wav"));
            } catch(IOException a) {}
            AudioPlayer.player.start(click);
            repaint();
            winSeq=WLT(board);
            String wlt = winSeq[0];
            //checks if game ended
            if(wlt.contentEquals("P1")||wlt.contentEquals("P2")||wlt.contentEquals("tie")){
            	timer.cancel();
                if(wlt.contentEquals("P1")){
                    score[0]="P1";
                } else if(wlt.contentEquals("P2")){
                    score[0]="P2";
                } else if(wlt.contentEquals("tie")){
                    score[0]="tie";
                }
                score = ScoreBoard(score);
                screen = 4;
            } else {
                plyr=players(plyr);
            }
        }
    }
    //resets current game
    public void reset() {
        timer.cancel();
        time = -1;
        winSeq = new String[]{"", ""};
        board = new String[][]{{"_", "_", "_"}, {"_", "_", "_"}, {"_", "_", "_"}};
        plyr = "X";
        screen = 2;
        //checks current mode to reset to that mode
        if(gamemode == 0) {
            AI(new int[] {-1, -1});
            repaint();
        } else if(gamemode==2){
            //AI goes first
            if(AImode==1){
                HumanVSHuman(AmericanizedAI());
            } else if(AImode==2){
                HumanVSHuman(AsianizedAI());
            }
            AI(new int[]{-1, -1});
        } else {
            HumanVSHuman(new int[]{-1, -1});
            repaint();
        }
    }
    //translate pixel to board square
    public int[] translateToSquare(int x, int y){
        int[] square = new int[2];
        if(x<=200){
            square[1] = 0;
        } else if(x<=400){
            square[1] = 1;
        } else if(x<=600){
            square[1] = 2;
        }
        if(y<=200){
            square[0] = 0;
        } else if(y<=400){
            square[0] = 1;
        } else if(y<=600){
            square[0] = 2;
        }
        return square;
    }
    @Override
    public void mouseClicked(MouseEvent e) {}
 
    @Override
    public void mouseEntered(MouseEvent e) {}
 
    @Override
    public void mouseExited(MouseEvent e) {}
 
    @Override
    public void mousePressed(MouseEvent e) {
        mousePressedX = e.getX();
        mousePressedY = e.getY();
        try {
            click = new AudioStream(new FileInputStream(".\\TicTacToe\\src\\clicksound.wav"));
        } catch(IOException a) {a.printStackTrace();}
        int[] position = translateToSquare(mousePressedX, mousePressedY);
        //title page
        if(screen == 1){
            if(mousePressedX>=75&&mousePressedX<=275&&mousePressedY>=400&&mousePressedY<=475){
                AudioPlayer.player.start(click);
                screen = 6;
            } else if(mousePressedX>=325&&mousePressedX<=525&&mousePressedY>=400&&mousePressedY<=475){
                AudioPlayer.player.start(click);
                screen = 2;
                gamemode = 1;
            }
            repaint();
        }else if(screen==3){ /**game */
            if(board[position[0]][position[1]].equals("_")){
            	if(time>0) {
            		timer.cancel();
            	}
                if(gamemode==1){
                    HumanVSHuman(position);
                    if(time>0) {
                        //re-init new timer task because timer class is stupid
                        TimerTask tasks = new TimerTask() {
                            public void run() {
                                HumanVSHuman(AmericanizedAI());
                            }
                        };
                        //re-init new timer because timer class is stupid
                        timer = new Timer();
                        timer.schedule(tasks, time*1000, time*1000);
                    }
                } else if(gamemode==0||gamemode==2){
                    AI(position);
                }
            }
        } else if(screen==4){ /**pause on screen for win seq to show */
        	timer.cancel();
            screen=5;
            repaint();
        } else if(screen==5){ /**end page */
            if(mousePressedX>=50&&mousePressedX<=250&&mousePressedY>=400&&mousePressedY<=500) {
                AudioPlayer.player.start(click);
                reset();
            } else if(mousePressedX>=350&&mousePressedX<=550&&mousePressedY>=400&&mousePressedY<=500) {
                System.exit(0);
            }
        } else if(screen==6){ /**AI character select */
            if(mousePressedX>=50&&mousePressedX<=250&&mousePressedY>=350&&mousePressedY<=450){
                gamemode = 0;
                AISymbol = "O";
                AudioPlayer.player.start(click);
            } else if(mousePressedX>=350&&mousePressedX<=550&&mousePressedY>=350&&mousePressedY<=450){
                gamemode = 2;
                AISymbol = "X";
                AudioPlayer.player.start(click);
            } else if(mousePressedX>=50&&mousePressedX<=250&&mousePressedY>=170&&mousePressedY<=270){
                AImode = 1;
                AudioPlayer.player.start(click);
            } else if(mousePressedX>=350&&mousePressedX<=550&&mousePressedY>=170&&mousePressedY<=270){
                AImode = 2;
                AudioPlayer.player.start(click);
            } else if(mousePressedX>=200&&mousePressedX<=400&&mousePressedY>=470&&mousePressedY<=570){
                if(AImode!=-1&&gamemode!=-1){
                    screen = 2;
                    if(gamemode==2){
                        if(AImode==1){
                            HumanVSHuman(AmericanizedAI());
                        } else if(AImode==2){
                            HumanVSHuman(AsianizedAI());
                        }
                    }
                }
                AudioPlayer.player.start(click);
            }
            repaint();
        }
    }
    @Override
    public void mouseReleased(MouseEvent e) {
        mousePressedX = -1;
        mousePressedY = -1;
    }
    @Override
    public void keyTyped(KeyEvent e) {
    }
    private String str="";
    private int time=-1;
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_R) {
            NewGame();
            repaint();
        } else if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
            System.exit(0);
        }
        //use must input "t="+number to use timed mode, only available in human vs human
        else if(str.length()==2){
            if(str.equals("t=")) {
                if(0<e.getKeyCode()-48&&e.getKeyCode()-48<10) {
                    time = e.getKeyCode()-48;
                    if(gamemode==1&&screen==3){
                        //init timer task
                        TimerTask task = new TimerTask() {
                            public void run() {
                                HumanVSHuman(AmericanizedAI());
                            }
                        };
                        //init timer
                        timer = new Timer();
                        timer.schedule(task, time*1000, time*1000);
                    }
                }
            }
            str="";
        }
        if(e.getKeyCode() == KeyEvent.VK_T) {
            str+="t";
        } else if(e.getKeyCode() == KeyEvent.VK_EQUALS){
            str+="=";
        }
    }
    @Override
    public void keyReleased(KeyEvent e) {}
}
