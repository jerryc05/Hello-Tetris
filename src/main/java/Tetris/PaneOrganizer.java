package Tetris;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class PaneOrganizer {

  private final BorderPane mBorderPane;

  public PaneOrganizer() {
    mBorderPane = new BorderPane();
    mBorderPane.setPrefSize(500, 620);
    mBorderPane.setStyle("-fx-background-color:#B0C4DE;");

    Pane topSpace = new Pane();
    topSpace.setPrefHeight(50);
    topSpace.setStyle("-fx-background-color:#B0C4DE;");
    Pane leftSpace = new Pane();
    leftSpace.setPrefSize(50, 620);
    leftSpace.setStyle("-fx-background-color:#B0C4DE;");
    Pane rightSpace = new Pane();
    rightSpace.setPrefSize(50, 620);
    rightSpace.setStyle("-fx-background-color:#B0C4DE;");

    Pane tetrisPane = new Pane();
    tetrisPane.setPrefSize(300, 600);
    tetrisPane.setStyle("-fx-background-color:#FFFAF0;");
    VBox buttonPane = new VBox();
    buttonPane.setPrefHeight(70);
    buttonPane.setStyle("-fx-background-color:#B0C4DE;");

    mBorderPane.setCenter(tetrisPane);
    mBorderPane.setBottom(buttonPane);
    mBorderPane.setLeft(leftSpace);
    mBorderPane.setRight(rightSpace);
    mBorderPane.setTop(topSpace);

    TetrisGame.withPane(tetrisPane);

    Button quit = new Button("Bye-bye!");
    buttonPane.setAlignment(Pos.CENTER);

    buttonPane.getChildren().addAll(quit);
    quit.setOnAction(event -> System.exit(0));
  }

  public Pane getRoot() {
    return mBorderPane;
  }
}