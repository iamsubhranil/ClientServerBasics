package styles;

import com.sun.javafx.scene.control.skin.TextFieldSkin;
import javafx.beans.value.ObservableValue;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;

/**
 * Created by pedro_000 on 12/15/13.
 */
public class TextFieldWithButtonSkin extends TextFieldSkin {

    private final StackPane rightButton;
    private final Region rightButtonGraphic;

    protected TextField textField;

    private boolean isVisible = true;

    public TextFieldWithButtonSkin(TextField textField) {
        super(textField);

        this.textField = textField;

        rightButton = new StackPane();
        rightButton.getStyleClass().setAll("right-button");
        rightButton.setFocusTraversable(false);

        rightButtonGraphic = new Region();
        rightButtonGraphic.getStyleClass().setAll("right-button-graphic");
        rightButtonGraphic.setFocusTraversable(false);

        rightButtonGraphic.setMaxWidth(Region.USE_PREF_SIZE);
        rightButtonGraphic.setMaxHeight(Region.USE_PREF_SIZE);

        rightButton.setVisible(false);
        rightButtonGraphic.setVisible(false);

        rightButton.getChildren().add(rightButtonGraphic);
        getChildren().addAll(rightButton);

        setupListeners();
    }

    public TextFieldWithButtonSkin(TextField textField, boolean visible) {
        this(textField);
        isVisible = visible;
    }

    private void setupListeners() {

        final TextField textField = getSkinnable();
        rightButton.setOnMousePressed((MouseEvent event) -> {
            rightButtonPressed();
        });
        rightButton.setOnMouseReleased((MouseEvent event) -> {
            rightButtonReleased();
        });

        textField.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            textChanged();
        });
        textField.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            focusChanged();
        });
    }

    protected void textChanged() {
        if (textField.getText() == null || !isVisible) {
            return;
        }

        rightButton.setVisible(!textField.getText().isEmpty());
        rightButtonGraphic.setVisible(!textField.getText().isEmpty());
    }

    protected void focusChanged() {
        if (textField.getText() == null || !isVisible) {
            return;
        }

        rightButton.setVisible(textField.isFocused() && !textField.getText().isEmpty());
        rightButtonGraphic.setVisible(textField.isFocused() && !textField.getText().isEmpty());
    }

    @Override
    protected void layoutChildren(double x, double y, double w, double h) {
        super.layoutChildren(x, y, w, h);

        final double clearGraphicWidth = snapSize(rightButtonGraphic.prefWidth(-1));
        final double clearButtonWidth = rightButton.snappedLeftInset() + clearGraphicWidth + rightButton.snappedRightInset();
        rightButton.resize(clearButtonWidth, h);
        //don't abuse unecessary space
        if (rightButton.isVisible()) {
            //Resize Content to prevent text overlap on the button
            getChildren().get(0).resize(w - clearButtonWidth - 5, h);

            positionInArea(rightButton,
                    (x + w) - clearButtonWidth, y,
                    clearButtonWidth, h, 0, HPos.CENTER, VPos.CENTER);
        }
    }

    protected void rightButtonPressed() {
    }

    protected void rightButtonReleased() {
        getSkinnable().setText("");
    }

    protected void hideRightButton(boolean hide) {
        rightButton.setVisible(hide);
        rightButtonGraphic.setVisible(hide);
    }

}
