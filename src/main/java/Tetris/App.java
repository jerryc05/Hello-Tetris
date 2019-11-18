package Tetris;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * It's time for Tetris! This is the main class to get things started.
 * <p>
 * The main method of this application calls the start method. You
 * will need to fill in the start method to instantiate your game.
 * <p>
 * Class comments here...
 */
public class App extends Application {

  @Override
  public void start(Stage stage) {

    PaneOrganizer organizer = new PaneOrganizer();
    Scene scene = new Scene(organizer.getRoot(), 400,
      720);
    stage.setScene(scene);
    stage.setTitle("Tetris ❤");
    stage.show();
  }

  /*
   * Here is the mainline! No need to change this.
   */
  public static void main(String[] argv) {
    // launch is a method inherited from Application
    launch(argv);
  }
}
