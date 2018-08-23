package application;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;
import javafx.util.Duration;

public class MainController implements Initializable {

	@FXML
	private MediaView mv;
	@FXML
	private MediaPlayer mp;
	private Media me;
	@FXML
	private Slider volumeSilder;
	@FXML
	private Slider timeSlider;
	public String mediaPath;
	@FXML
	private Label playTime;
	Status status;
	File file;
	
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
	}

	@FXML
	public void play(ActionEvent e) {
		mp.play();
		mp.setRate(1);
	}

	@FXML
	public void pause(ActionEvent e) {
		status = mp.getStatus();
		if (status == Status.PLAYING) {
			mp.pause();
		} else {
			mp.play();
		}
	}

	@FXML
	public void fast(ActionEvent e) {
		mp.setRate(2);
	}

	@FXML
	public void slow(ActionEvent e) {
		mp.setRate(0.5);
	}

	@FXML
	public void reload(ActionEvent e) {
		mp.seek(mp.getStartTime());
		mp.play();
		mp.setRate(1);
	}

	@FXML
	public void mute(ActionEvent e) {
		boolean mute;
		mute = mp.isMute();
		if (mute) {
			mp.setMute(false);
		} else {
			mp.setMute(true);
		}
	}

	@FXML
	public void selectFile(ActionEvent event) {
		try {
			FileChooser fileChooser = new FileChooser();
			fileChooser.setTitle("Open Resource File");
			FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("Select a file (*.mp3", "*.mp4",
					"*.3gp", "*.mkv", "*.MP4", "*.MKV", "*.3GP", "*.flv", "*.wmv)", "*.mp4", "*.mp3", "*.3gp", "*.mkv",
					"*.MP4", "*.MKV", "*.3GP", "*.flv", "*.wmv");
			fileChooser.getExtensionFilters().add(filter);

			file = fileChooser.showOpenDialog(null);
		} catch (Exception e) {
			System.out.println("Exception : " + e);
		}
		if (file != null) {

			mediaPath = file.toURI().toString();

			if (mp != null) {
				mp.stop();
			}

			me = new Media(mediaPath);

			mp = new MediaPlayer(me);
			mv.setMediaPlayer(mp);
		
			DoubleProperty height = mv.fitHeightProperty();
			DoubleProperty width = mv.fitWidthProperty();
			mv.setPreserveRatio(true);
			width.bind(Bindings.selectDouble(mv.sceneProperty(), "width"));
			height.bind(Bindings.selectDouble(mv.sceneProperty(), "height"));


			volumeSilder.setValue(mp.getVolume() * 100);
			volumeSilder.valueProperty().addListener(new InvalidationListener() {

				@Override
				public void invalidated(Observable observable) {
					mp.setVolume(volumeSilder.getValue() / 100);
				}
			});

			mp.setOnReady(new Runnable() {
				@Override
				public void run() {
					// System.out.println("Duration: "+
					// mediaPlayer.getTotalDuration().toSeconds());
					timeSlider.setMin(0.0);
					timeSlider.setValue(0.0);
					timeSlider.setMax(mp.getTotalDuration().toSeconds());

					mp.currentTimeProperty().addListener(new ChangeListener<Duration>() {
						@Override
						public void changed(ObservableValue<? extends Duration> observable, Duration oldValue,
								Duration newValue) {
							timeSlider.setValue(newValue.toSeconds());
						}

					});
					
					

					timeSlider.setOnMouseDragged(new EventHandler<MouseEvent>() {
						@Override
						public void handle(MouseEvent event) {
							mp.seek(Duration.seconds(timeSlider.getValue()));

						}

					});
					
					
					timeSlider.setOnMousePressed(new EventHandler<MouseEvent>() {
						@Override
						public void handle(MouseEvent event) {
							mp.seek(Duration.seconds(timeSlider.getValue()));

						}

					});
				}
			});

			mp.play();
		} else {
			if (mp == null) {
				
			} else if (mp.getStatus() == Status.PAUSED) {

				mp.pause();
			} else {
				mp.play();

			}
		}
	}
}
