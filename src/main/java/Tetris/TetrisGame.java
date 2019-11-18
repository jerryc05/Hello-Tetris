package Tetris;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Duration;

public class TetrisGame {

  private static final Square[][] mBoard;
  private static       Piece      mFallingPiece;
  private static       Pane       mPane;
  private static final Label      mGameOverLabel;
  private static final Timeline   mTimeline;
  private static       long       mScore;
  private static       boolean    mIsPaused;

  static {
    mBoard = new Square[10][20];
    mGameOverLabel = new Label();
    mTimeline = new Timeline(
      new KeyFrame(Duration.seconds(1),
        event -> {
          if (!tryMove(0, 1) && !Piece.tryGeneratePiece())
            endGame();
        }));

  }

  public static void withPane(Pane pane) {
    pane.setOnKeyPressed(e -> {
      if (mIsPaused) {
        if (e.getCode() == KeyCode.P) {
          mIsPaused = false;
          mTimeline.play();
        }
      } else
        switch (e.getCode()) {
          case P:
            mIsPaused = true;
            mTimeline.pause();
            break;

          case LEFT:
            tryMove(-1, 0);
            break;

          case RIGHT:
            tryMove(1, 0);
            break;

          case DOWN:
            tryMove(0, 1);
            break;

          case UP:
            int centerX = mFallingPiece.getRotationCenterSquare().getX();
            int centerY = mFallingPiece.getRotationCenterSquare().getY();
            boolean canMove = true;

            for (Square eachSquare : mFallingPiece.getSquares()) { // can move
              int locX = eachSquare.getX();
              int locY = eachSquare.getY();
              int newX = centerX - centerY + locY;
              int newY = centerY + centerX - locX;

              if (newX < 0 || newX > 9 || newY < 0 || newY > 19
                || (mBoard[newX][newY] != null
                && isNotInFallingSquares(newX, newY))) {
                canMove = false;
                break;
              }
            }

            if (!canMove)
              break;

            for (Square eachSquare : mFallingPiece.getSquares()) { // perform move
              int locX = eachSquare.getX();
              int locY = eachSquare.getY();
              int newX = centerX - centerY + locY;
              int newY = centerY + centerX - locX;

              mBoard[locX][locY] = null;
              eachSquare.withLocation(newX, newY);
            }

            for (Square eachSquare : mFallingPiece.getSquares())
              mBoard[eachSquare.getX()][eachSquare.getY()] = eachSquare;
            break;

          case SPACE:
            while (tryMove(0, 1)) ;

          default:
            break;
        }
      e.consume();
    });
    pane.setFocusTraversable(true);
    mPane = pane;

    Piece.tryGeneratePiece();
    mTimeline.setCycleCount(Animation.INDEFINITE);
    mTimeline.play();
  }

  public static boolean tryMove(int xOffset, int yOffset) {
    synchronized (mBoard) {
      if (canMove(xOffset, yOffset)) {
        moveNow(xOffset, yOffset);
        return true;
      }
      return false;
    }
  }

  private static boolean canMove(int xOffset, int yOffset) {
    for (int i = 0; i < 4; i++) {
      Square eachSquare = mFallingPiece.getSquares()[i];
      int    newX       = eachSquare.getX() + xOffset;
      int    newY       = eachSquare.getY() + yOffset;

      if (newX < 0 || newX > 9 || newY < 0 || newY > 19
        || (mBoard[newX][newY] != null && isNotInFallingSquares(newX, newY)))
        return false;
    }
    return true;
  }

  private static boolean isNotInFallingSquares(int x, int y) {
    for (Square eachFallingSquare : mFallingPiece.getSquares()) {
      if (eachFallingSquare.getX() == x
        && eachFallingSquare.getY() == y)
        return false;
    }
    return true;
  }

  private static void moveNow(int xOffset, int yOffset) {
    for (Square eachSquare : mFallingPiece.getSquares()) {
      int locX = eachSquare.getX();
      int locY = eachSquare.getY();
      int newX = eachSquare.getX() + xOffset;
      int newY = eachSquare.getY() + yOffset;

      mBoard[locX][locY] = null;
      eachSquare.withLocation(newX, newY);
    }

    for (Square eachSquare : mFallingPiece.getSquares())
      mBoard[eachSquare.getX()][eachSquare.getY()] = eachSquare;

    tryClearLine();
    printBoard();
  }

  private static void tryClearLine() {
    for (int y = 19; y > 0; y--) {
      boolean rowIsFull = true;

      for (int x = 0; x < 10; x++)
        if (mBoard[x][y] == null) {
          rowIsFull = false;
          break;
        }

      if (!rowIsFull)
        continue;

      for (int y2 = y; y2 > 0; y2--)
        for (int x = 0; x < 10; x++) {
          // graphically remove
          if (y2 == y)
            mPane.getChildren().remove(mBoard[x][y2].getRectangle());

          Square upperSquare = mBoard[x][y2 - 1];
          if (upperSquare != null)
            upperSquare.moveDownOnce();

          // logically remove
          mBoard[x][y2] = upperSquare;
          ++mScore;
        }
    }
  }

  public static void endGame() {
    System.out.println("stop!");
    mTimeline.stop();
    mGameOverLabel.setText("Game Over!");
    mGameOverLabel.setTextFill(Color.CRIMSON);
    mGameOverLabel.setFont(new Font("Arial", 35));
    mGameOverLabel.setVisible(true);
    mPane.getChildren().add(mGameOverLabel);
    mPane.setOnKeyPressed(null);
  }

  private static void printBoard() {
    for (int y = 0; y < 20; y++) {
      System.out.print("|");
      for (int x = 0; x < 10; x++) {
        System.out.print(mBoard[x][y] != null ? 'x' : '.');
        System.out.print(' ');
      }
      System.out.println("|");
    }
    System.out.println("score = " + mScore);
    System.out.println("----------------------");
  }

  public static void setFallingPieces(Piece fallingPieces) {
    mFallingPiece = fallingPieces;
  }

  public static Pane getPane() {
    return mPane;
  }

  public static Square[][] getBoard() {
    return mBoard;
  }
}