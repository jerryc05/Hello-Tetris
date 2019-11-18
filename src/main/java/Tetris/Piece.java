package Tetris;

import javafx.scene.paint.Color;

public class Piece {

  private Square[] mSquares;

  /**
   * Generate new piece in the game, and return true if successful.
   *
   * @return true when successful, false otherwise.
   */
  public static boolean tryGeneratePiece() {
    Piece newPiece = generatePieceNow();

    for (int i = 0; i < 10; i++) {
      if (TetrisGame.getBoard()[i][1] != null)
        return false;
    }

    for (Square eachSquare : newPiece.mSquares) {
      if (TetrisGame.getBoard()[eachSquare.getX()][eachSquare.getY()] != null)
        return false;
    }

    for (Square eachSquare : newPiece.mSquares)
      TetrisGame.getPane().getChildren().add(eachSquare.getRectangle());

    TetrisGame.setFallingPieces(newPiece);
    // record new piece location into game's board.
    TetrisGame.tryMove(0, 0);
    return true;
  }

  private static Piece generatePieceNow() {
    switch ((int) (Math.random() * 7)) {
      case 0: // Create I-shape Piece
        return initPiece(new int[][]{{5, 1}, {5, 0}, {5, 2}, {5, 3}},
          Color.PINK);

      case 1: // Create L-shape Piece
        return initPiece(new int[][]{{6, 0}, {5, 0}, {6, 1}, {6, 2}},
          Color.THISTLE);

      case 2: // Create J-shape Piece
        return initPiece(new int[][]{{5, 0}, {6, 0}, {5, 1}, {5, 2}},
          Color.PLUM);

      case 3: // Create S-shape Piece
        return initPiece(new int[][]{{5, 1}, {5, 0}, {6, 1}, {6, 2}},
          Color.LAVENDER);

      case 4: // Create Z-shape Piece
        return initPiece(new int[][]{{5, 1}, {5, 0}, {4, 1}, {4, 2}},
          Color.ORCHID);

      case 5: // Create T-shape Piece
        return initPiece(new int[][]{{5, 1}, {5, 0}, {6, 1}, {5, 2}},
          Color.MEDIUMPURPLE);

      case 6:
      default:// Create O-shape Piece
        return initPiece(new int[][]{{5, 0}, {4, 0}, {4, 1}, {5, 1}},
          Color.PALEVIOLETRED);
    }
  }

  private static Piece initPiece(int[][] coordinates, Color color) {
    Piece piece = new Piece();
    piece.mSquares = new Square[4];

    for (int i = 0; i < 4; i++) {
      piece.mSquares[i] = Square
        .createFromCoordinate(coordinates[i][0], coordinates[i][1])
        .withColor(color);
    }
    return piece;
  }

  public Square[] getSquares() {
    return mSquares;
  }

  public Square getRotationCenterSquare() {
    return mSquares[0];
  }
}