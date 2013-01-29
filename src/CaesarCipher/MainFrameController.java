package CaesarCipher;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.util.StringConverter;
import javafx.scene.input.KeyEvent;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Raphael Beer <raphael.beer at gmx.de>
 */
public class MainFrameController implements Initializable {
    
    @FXML private TextArea txt_Original;
    @FXML private TextArea txt_Shifted;
    @FXML private TextField txt_ShiftAmount;
    @FXML private ScrollBar sb_ShiftAmount;
    
    @FXML
    private void handleAHomeAction(ActionEvent _ev) {
        try {
            URI _uri = new URI("http://wp.me/p2sOfo-1H");
            java.awt.Desktop.getDesktop().browse(_uri);
        } catch (URISyntaxException | IOException _ex) {
            Logger.getLogger(MainFrameController.class.getName()).log(Level.SEVERE, null, _ex);
        }
    }
    
    @FXML
    private void handleBtnShiftClicked(MouseEvent _ev) {
        char[] _origCharArray = this.txt_Original.getText().toCharArray();
        int _shiftAmount = Integer.parseInt(this.txt_ShiftAmount.getText());
        
        this.txt_Shifted.clear();
        
        for (char _c : _origCharArray) {
            this.txt_Shifted.appendText(this.getShiftedChar(_c, _shiftAmount));
        }
        
//        System.out.println("_firstChar: ".concat(String.valueOf(_firstChar)));
//        System.out.println("(int) _firstChar: ".concat(String.valueOf((int) _firstChar)));
//        System.out.println("_shiftAmount: ".concat(String.valueOf(_shiftAmount)));
    }
    
    @FXML
    private void handleChangeShiftAmountClick(MouseEvent _ev) {
        this.handleBtnShiftClicked(_ev);
    }
    
    private String getShiftedChar(char _baseChar, int _add) {
        int _IntFrom_baseChar = (int) _baseChar;
        int _ascii = _IntFrom_baseChar + _add;
        
        // don't shift all other characters than a-z and A-Z
        if ((_IntFrom_baseChar < 65 || _IntFrom_baseChar > 122) || (_IntFrom_baseChar > 90 && _IntFrom_baseChar < 97)) {
            //return _baseChar;
//            System.out.println("_baseChar is non-alpha!");
            return String.valueOf(_baseChar);
        }
        
        // UPPER case A-Z
        if (_IntFrom_baseChar <= 90) {
//            System.out.println("UPPER Case");
            if (_ascii > 90) {
                _ascii = 64 + (_ascii - 90);
            }
//            System.out.println(_ascii);
            return String.valueOf((char) _ascii);
        }
        // lower case a-z
        else {
//            System.out.println("lower Case");
            if (_ascii > 122) {
                _ascii = 96 + (_ascii - 122);
            }
//            System.out.println("_ascii: ".concat(String.valueOf(_ascii)));
            return String.valueOf((char) _ascii);
        }
        
        //return (char) _ascii;
    }
    
    /**
     * Calculate shift amount between to characters.
     * @param _start Character to start counting from.
     * @param _end Shifted Character.
     * @param _lcase Set to true if characters are lower case.
     * @return Distance between _start and _end. <br />
     * 
     * @deprecated 
     * <ul>
     * <li>A->C returns 2</li>
     * <li>V->I returns 13</li>
     * <li>M->M returns 0</li>
     * </ul><br />
     * Returns -1 if _start or _end is not supported.
     */
    private int getCharDistance(int _start, int _end, Boolean _lcase) {
        
        int _endInt = (int) _end;
        int _startInt = (int) _start;
        int _r = _endInt - _startInt;
        
        if (_r < 0) {
            
            int _lowerEnd = (_lcase) ? 97 : 65;
            int _upperEnd = (_lcase) ? 123 : 91;
            
            _r = (_endInt - _lowerEnd) + (_upperEnd - _startInt);
        }
        return _r;
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        this.txt_ShiftAmount.textProperty().bindBidirectional(this.sb_ShiftAmount.valueProperty(),
                                                                                            new StringConverter<Number>() {

            @Override
            public String toString(Number _doubleValue) {
                return String.format("%d", _doubleValue.intValue());
            }

            @Override
            public Number fromString(String _stringValue) {
                return Double.parseDouble(_stringValue);
            }
        });
        
        this.txt_ShiftAmount.addEventFilter(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {

            @Override
            public void handle(KeyEvent _event) {
                
                if (!"0123456789".contains(_event.getText())) {
                    _event.consume();
                }
            }
        });
        
        this.txt_ShiftAmount.textProperty().addListener(new ChangeListener<String>() {

            @Override
            public void changed(ObservableValue<? extends String> _observable, String _oldValue, String _newValue) {
                
                try {
                    
                    if (Integer.valueOf(_newValue) > 25) {
                        txt_ShiftAmount.setText("25");
                    }
                    
                } catch (Exception e) {
                    
                    txt_ShiftAmount.setText(null);
                    
                }
            }
        });

    }
}
        


