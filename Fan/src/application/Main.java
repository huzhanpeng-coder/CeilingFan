package application;
	
import java.io.FileInputStream;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Circle;
import javafx.stage.*;
import javafx.scene.*;

public class Main extends Application {
	
	
	int valueSpeed = 600;
	CeilingFan fan = new CeilingFan();
	
	@Override
	public void start(Stage primaryStage) throws Exception{
		
		HBox hbox = new HBox(8);
		Button btnCordSpeed = new Button("1");
		Button btnCordReverse = new Button("2");
		hbox.getChildren().addAll(btnCordSpeed, btnCordReverse);
		hbox.setAlignment(Pos.CENTER);
		
		Image img = new Image("cord.png");
	    ImageView view = new ImageView(img);
	    ImageView view2 = new ImageView(img);
	    
	    view.setFitHeight(200);
	    view.setPreserveRatio(true);
	    view2.setFitHeight(200);
	    view2.setPreserveRatio(true);
	    
	    btnCordSpeed.setGraphic(view);
	    btnCordReverse.setGraphic(view2);
		
		HBox.setHgrow(btnCordSpeed, Priority.ALWAYS);
		
		BorderPane pane= new BorderPane();
		pane.setCenter(fan);
		pane.setBottom(hbox);
		
		Scene scene = new Scene(pane, 200, 500);
		primaryStage.setTitle("Ceiling Fan");
		primaryStage.setScene(scene);
		primaryStage.show();
		
		scene.widthProperty().addListener(e -> fan.setWidth(fan.getWidth()));
		scene.heightProperty().addListener(e -> fan.setHeight(fan.getHeight()));
		
		Timeline animation = new Timeline(new KeyFrame(Duration.millis(valueSpeed), e -> fan.move() ));
		animation.setCycleCount(Timeline.INDEFINITE);
		
		btnCordSpeed.setOnAction(v -> {
			animation.stop();
		    animation.getKeyFrames().clear();
		    
			if (valueSpeed == 600) {
				animation.getKeyFrames().add(new KeyFrame(Duration.millis(valueSpeed), e -> fan.move() ));
				animation.play();
				valueSpeed=75;
			}else if (valueSpeed == 75) {
				animation.getKeyFrames().add(new KeyFrame(Duration.millis(valueSpeed), e -> fan.move() ));
				animation.play();
				valueSpeed=10;
			}else if (valueSpeed == 10) {
				animation.getKeyFrames().add(new KeyFrame(Duration.millis(valueSpeed), e -> fan.move() ));
				animation.play();
				valueSpeed=0;
			}else if (valueSpeed == 0){
				animation.pause();
				valueSpeed=600;
			}
		});
		
		btnCordReverse.setOnAction(e-> fan.reverse());
	
	}
	
	
	
	public static void main(String[] args) {
		launch(args);
	}
}

class CeilingFan extends Pane {
	
	private double width = 250;
	private double height = 250;
	private double radius = Math.min(width,height)*0.45;
	private Arc arc[] = new Arc[6];
	private double startingAngle = 0;
	private Circle circle = new Circle(width/64,height/64, radius);
	
	public CeilingFan() {
		circle.setStroke(Color.BLACK);
		circle.setFill(Color.WHITE);
		getChildren().add(circle);
		
		for (int i = 0 ; i<6; i++) {
			arc[i] = new Arc(width/64, height/64, radius*0.9, radius*0.9, startingAngle+i*90, 35);
			arc[i].setFill(Color.GREEN);
			arc[i].setType(ArcType.ROUND);
			getChildren().addAll(arc[i]);
			
		}
	}
	
	private double steps = 10;
	
	public void move() {
		setStartAngle(startingAngle+steps);
	}
	
	public void setStartAngle(double angle) {
		startingAngle = angle;
		setValues();
	};
	
	public void reverse() {
		steps = -steps;
	};
	
	public void setHeight(double height) {
		this.height = height;
		setValues();
	}
	
	public void setWidth(double width) {
		this.width = width;
		setValues();
	}
	
	public void setValues() {
		radius = Math.min(height,width)*0.3;
		circle.setRadius(radius);
		circle.setCenterX(width/64);
		circle.setCenterY(height/64);
		
		for (int i=0 ; i<6; i++) {
			arc[i].setRadiusX(radius*1.45);
			arc[i].setRadiusY(radius*1.45);
			arc[i].setCenterX(width/64);
			arc[i].setCenterY(height/64);
			arc[i].setStartAngle(startingAngle+i*90);
		}
	}
	
}
