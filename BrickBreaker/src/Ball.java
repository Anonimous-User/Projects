package BrickBreaker.src;
import java.awt.*;
//import javax.swing.*;

public class Ball {
    private int x;//top left
    private int y;//top left
    private int size;//diameter
    private int dx;//direction
    private int dy;//direction
    private Color color;
    public Ball(int coordx, int coordy, int d, int directionx, int directiony, Color colour){
        x = coordx;
        y = coordy;
        size = d;
        dx = directionx;
        dy = directiony;
        color = colour;
    }
    //accessor & mutator
    public int GetX() {
    	return x;
    }
    public int GetY() {
    	return y;
    }
    public int GetSize() {
    	return size;
    }
    public int GetDx() {
    	return dx;
    }
    public int GetDy() {
    	return dy;
    }
    public Color getColor() {
    	return color;
    }
    public void setX(int newx) {
    	x = newx;
    }
    public void setY(int newy) {
    	y = newy;
    }
    public void setSize(int newsize) {
    	size = newsize;
    }
    public void setDx(int newdx) {
    	dx = newdx;
    }
    public void setDy(int newdy) {
    	dy = newdy;
    }
    public void setColor(Color newColor) {
    	color = newColor;
    }
    public void move(Graphics g){
        collision();
        x+=dx;
        y+=dy;
        draw(g);
    }
    public void collision() {
    	//collision with wall
    	if(x<5||x+size>490) {
    		dx*=-1;
    	}
    	if(y<5) {
    		dy*=-1;
    	}
    	if(y+size>500) {
    		dy=0;
    		dx=0;
    	}
    }
    public void draw(Graphics g){
        g.setColor(color);
        g.fillOval(x, y, size, size);
    }
}
