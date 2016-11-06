/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package styles;

import javafx.scene.control.TextField;

/**
 * @author Nil
 */
public class NonEditableTextFieldSkin extends TextFieldWithButtonSkin {

    public NonEditableTextFieldSkin(TextField textField) {
        super(textField, false);
    }

}
