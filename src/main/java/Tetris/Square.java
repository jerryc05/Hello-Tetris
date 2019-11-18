package Tetris;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Square {

  private final Rectangle mRectangle;

  {
    mRectangle = new Rectangle(Constants.SQUARE_LENGTH,
      Constants.SQUARE_LENGTH);
    mRectangle.setStroke(Color.WHITE);
  }

  public static Square createFromCoordinate(int x, int y) {
    return new Square().withLocation(x, y);
  }

  public Square withLocation(int x, int y) {
    mRectangle.setX(x * Constants.SQUARE_LENGTH);
    mRectangle.setY(y * Constants.SQUARE_LENGTH);
    return this;
  }

  public void moveDownOnce() {
    withLocation(getX(), getY() + 1);
  }

  public Square withColor(Color color) {
    mRectangle.setFill(color);
    return this;
  }

  public int getX() {
    return (int) (mRectangle.getX() / Constants.SQUARE_LENGTH);
  }

  public int getY() {
    return (int) (mRectangle.getY() / Constants.SQUARE_LENGTH);
  }

  public Rectangle getRectangle() {
    return mRectangle;
  }
}