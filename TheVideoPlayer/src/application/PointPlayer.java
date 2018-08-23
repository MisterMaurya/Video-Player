package application;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class PointPlayer extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {

			Parent root = FXMLLoader.load(getClass().getResource("/application/Main.fxml"));
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("/application/application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setTitle("PinPointPlayer created by Pawan_Maurya");
			Image icon = new Image(getClass().getResourceAsStream("/icon/pinicon.png"));
			primaryStage.getIcons().add(icon);
			
		
			primaryStage.setFullScreenExitHint("Press ESC or Double click to exit the full Screen");

			scene.setOnMouseClicked(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent clicked) {
					if (clicked.getClickCount() == 2) {
						if (!primaryStage.isFullScreen()) {
							primaryStage.setFullScreen(true);
						} else {
							primaryStage.setFullScreen(false);
						}

					}
				}

			});
			primaryStage.setMinHeight(200);
			primaryStage.setMinWidth(500);

		
			primaryStage.show();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);

	}
}
