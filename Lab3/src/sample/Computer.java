package sample;

import javafx.animation.*;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.stage.Stage;
import javafx.util.Duration;


public class Computer extends Application {

    public static void main(String args[]) {
        launch(args);
    }

    private int WIDTH = 700;
    private int HEIGHT = 300;

    @Override
    public void start(Stage primaryStage) {
        Group root = new Group();
        Scene scene = new Scene(root, WIDTH, HEIGHT);

        // Support Arc
        Arc supportArc = new Arc(79, 61, 11, 11, 150, 220);
        supportArc.setStroke(Color.BLACK);
        supportArc.setStrokeWidth(1);
        supportArc.setFill(Color.WHITE);

        // Support Rectangles
        Rectangle supportTopRectangle = new Rectangle(52, 70, 55, 5);
        supportTopRectangle.setStroke(Color.BLACK);
        supportTopRectangle.setStrokeWidth(1);
        supportTopRectangle.setFill(Color.WHITE);

        Rectangle supportBottomRectangle = new Rectangle(36, 73, 83, 19);
        supportBottomRectangle.setStroke(Color.BLACK);
        supportBottomRectangle.setStrokeWidth(1);
        supportBottomRectangle.setFill(Color.rgb(80, 152, 195));

        // Buttons
        Rectangle button1 = new Rectangle(40, 78, 10, 3);
        button1.setStroke(Color.BLACK);
        button1.setStrokeWidth(1);
        button1.setFill(Color.WHITE);

        Rectangle button2 = new Rectangle(106, 78, 10, 3);
        button2.setStroke(Color.BLACK);
        button2.setStrokeWidth(1);
        button2.setFill(Color.WHITE);

        Rectangle button3 = new Rectangle(93, 78, 10, 3);
        button3.setStroke(Color.BLACK);
        button3.setStrokeWidth(1);
        button3.setFill(Color.WHITE);


        // White Borders
        Path whiteBorder = new Path();
        whiteBorder.setStrokeWidth(1);
        MoveTo moveTo_11 = new MoveTo(40, 10);
        QuadCurveTo curve_11 = new QuadCurveTo(71, 4, 104, 2);
        QuadCurveTo curve_12 = new QuadCurveTo(106, 28, 112, 59);
        QuadCurveTo curve_13 = new QuadCurveTo(79, 62, 46, 67);
        QuadCurveTo curve_14 = new QuadCurveTo(45, 38, 40, 10);
        whiteBorder.getElements().addAll(moveTo_11, curve_11, curve_12, curve_13, curve_14);
        whiteBorder.setFill(Color.WHITE);

        // Blue Screen
        Path blueScreen = new Path();
        blueScreen.setStrokeWidth(1);
        MoveTo moveTo_21 = new MoveTo(49, 16);
        QuadCurveTo curve_21 = new QuadCurveTo(72, 10, 97, 9);
        QuadCurveTo curve_22 = new QuadCurveTo(101, 29, 102, 51);
        QuadCurveTo curve_23 = new QuadCurveTo(79, 57, 55, 58);
        QuadCurveTo curve_24 = new QuadCurveTo(49, 34, 49, 16);
        blueScreen.getElements().addAll(moveTo_21, curve_21, curve_22, curve_23, curve_24);
        blueScreen.setFill(Color.rgb(80, 152, 195));

        // Eyes
        Ellipse leftEye = new Ellipse(66, 27, 3, 5);
        leftEye.setStroke(Color.BLACK);
        leftEye.setStrokeWidth(1);
        leftEye.setFill(Color.WHITE);

        Ellipse rightEye = new Ellipse(83, 25, 3, 5);
        rightEye.setStroke(Color.BLACK);
        rightEye.setStrokeWidth(1);
        rightEye.setFill(Color.WHITE);

        // Pupils
        Ellipse leftPupil = new Ellipse(66, 27, 1, 1);
        leftPupil.setFill(Color.BLACK);

        Ellipse rightPupil = new Ellipse(83, 25, 1, 1);
        rightPupil.setFill(Color.BLACK);

        // Mouth
        Path smile = new Path();
        MoveTo moveTo_31 = new MoveTo(57, 35);
        smile.setStrokeWidth(1);
        QuadCurveTo curve_31 = new QuadCurveTo(66, 44, 80, 41);
        QuadCurveTo curve_32 = new QuadCurveTo(77, 48, 71, 41.8);
        smile.getElements().addAll(moveTo_31, curve_31, curve_32);
        smile.setFill(Color.WHITE);

        Path mouth = new Path();
        MoveTo moveTo_32 = new MoveTo(80, 41);
        QuadCurveTo curve_33 = new QuadCurveTo(88, 37, 93, 31);
        mouth.setStrokeWidth(1);
        mouth.getElements().addAll(moveTo_32, curve_33);


        // Arms
        Line leftArm = new Line(45, 50, 28, 60);
        leftArm.setStroke(Color.BLACK);
        leftArm.setStrokeLineCap(StrokeLineCap.ROUND);
        leftArm.setStrokeWidth(3);

        Line rightArm = new Line(109, 43, 131, 49);
        rightArm.setStroke(Color.BLACK);
        rightArm.setStrokeLineCap(StrokeLineCap.ROUND);
        rightArm.setStrokeWidth(3);

        // add all elements
        root.getChildren().add(supportArc);
        root.getChildren().add(supportTopRectangle);
        root.getChildren().add(supportBottomRectangle);
        root.getChildren().add(button1);
        root.getChildren().add(button2);
        root.getChildren().add(button3);
        root.getChildren().add(leftArm);
        root.getChildren().add(rightArm);
        root.getChildren().add(whiteBorder);
        root.getChildren().add(blueScreen);
        root.getChildren().add(leftEye);
        root.getChildren().add(rightEye);
        root.getChildren().add(leftPupil);
        root.getChildren().add(rightPupil);
        root.getChildren().add(mouth);
        root.getChildren().add(smile);

        // animation
        int cycleCount = 2;
        int time = 3000;

        RotateTransition rotateTransition = new RotateTransition(Duration.millis(time), root);
        rotateTransition.setByAngle(720.0);
        rotateTransition.setCycleCount(cycleCount);
        rotateTransition.setAutoReverse(true);

        TranslateTransition translateTransition = new TranslateTransition(Duration.millis(time), root);
        translateTransition.setFromX(50);
        translateTransition.setFromY(50);
        translateTransition.setToX(WIDTH - 100);
        translateTransition.setToY(HEIGHT - 100);
        translateTransition.setCycleCount(cycleCount);
        translateTransition.setAutoReverse(true);

        ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(time), root);
        scaleTransition.setToX(0.1);
        scaleTransition.setToY(0.1);
        scaleTransition.setCycleCount(cycleCount);
        scaleTransition.setAutoReverse(true);

        ParallelTransition parallelTransition = new ParallelTransition();
        parallelTransition.getChildren().addAll(
                scaleTransition,
                rotateTransition,
                translateTransition
        );

        parallelTransition.setCycleCount(Timeline.INDEFINITE);
        parallelTransition.play();

        primaryStage.setResizable(false);
        primaryStage.setTitle("Computer");
        primaryStage.setScene(scene);
        primaryStage.show();

    }

}

