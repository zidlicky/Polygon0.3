package polygon03;

import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;

import javafx.fxml.FXML;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;

import javafx.scene.paint.Color;

import javafx.scene.web.PopupFeatures;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.util.Callback;


import java.io.File;


import static javafx.util.Duration.millis;


public class uiController implements PrimaryStageAware {

    @FXML
    private Canvas canvasBack, canvasIn, canvasBackColor, canvasInColor;
    @FXML
    private TextField eTxtN, eTxtSize, eTxtOffsetx, eTxtOffsety, eTxtDuration,
            eTxtRedBack, eTxtGreenBack, eTxtBlueBack, eTxtRedIn, eTxtGreenIn, eTxtBlueIn;
    @FXML
    private CheckBox checkStar, checkAni;
    @FXML
    private Slider redSliderBack, greenSliderBack, blueSliderBack, redSliderIn, greenSliderIn, blueSliderIn;
    @FXML
    private TextArea txtArea;
    @FXML
    private MenuItem menuSave, menuSaveAs, menuExport;


    private Stage stage;

    private int n, size, offsetx, offsety, duration;
    private boolean star, ani, polyCreated = false;
    private int[] rgbBack = {0, 0, 0}, rgbIn = {0, 0, 0};
    private RotateTransition rt = new RotateTransition(millis(6000), canvasBack);
    File file = null;

    @FXML
    private void initialize() {

        //ChangeListener eTxt
        ChangeListener cLeTxt = new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                observable.removeListener(this);
                int limit = 0;
                StringProperty textProperty = (StringProperty) observable ;
                TextField textField = (TextField) textProperty.getBean();
                if((!isInteger(textField.getText()))&&(!textField.getText().equals(""))){
                    textField.setText(oldValue.toString());
                }
                switch(textField.getId()){
                    case "eTxtN": limit=3; break;
                    case "eTxtOffsetx": limit=9; break;
                    case "eTxtOffsety": limit=9; break;
                    case "eTxtDuration": limit=6; break;
                    case "eTxtSize": limit=8; break;
                    default: break;
                }
                if ((isInteger(textField.getText()))&&(textField.getText().substring(0,1).equals("0"))&&(textField.getText().length()>=2)){
                    textField.setText(textField.getText().substring(1,textField.getText().length()));
                }
                if(textField.getText().length()>=limit+1){
                    textField.setText(oldValue.toString());
                }
                observable.addListener(this);
            }
        };
        eTxtN.textProperty().addListener(cLeTxt);eTxtDuration.textProperty().addListener(cLeTxt);
        eTxtOffsety.textProperty().addListener(cLeTxt);eTxtOffsetx.textProperty().addListener(cLeTxt);
        eTxtSize.textProperty().addListener(cLeTxt);


        //ChangeListener Sliders... when Sliders Changed, drawCanvas & set Etxt & set Values
        ChangeListener cLSlider = new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                setSliderValue();
            }
        };

        redSliderBack.valueProperty().addListener(cLSlider);
        greenSliderBack.valueProperty().addListener(cLSlider);
        blueSliderBack.valueProperty().addListener(cLSlider);

        redSliderIn.valueProperty().addListener(cLSlider);
        greenSliderIn.valueProperty().addListener(cLSlider);
        blueSliderIn.valueProperty().addListener(cLSlider);

        //eTxtListener

        //<editor-fold desc="eTextListener">

        //<editor-fold desc="eTxtRedBack">

        final ChangeListener<String> etxtRedBackchange = new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!(newValue.isEmpty())) {
                    eTxtRedBack.textProperty().removeListener(this);
                    if (!isInteger(newValue)) {
                        eTxtRedBack.setText(oldValue);
                    } else {
                        redSliderBack.setValue((double) Integer.valueOf(eTxtRedBack.getText()));
                    }
                    if (isInteger(newValue) && Integer.valueOf(newValue) >= 256) {
                        eTxtRedBack.setText("255");
                    }
                    eTxtRedBack.textProperty().addListener(this);
                }
            }
        };
        eTxtRedBack.textProperty().addListener(etxtRedBackchange);
        eTxtRedBack.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (!newValue && eTxtRedBack.getText().isEmpty()) {
                    redSliderBack.setValue(0.0);
                }
            }
        });
        //</editor-fold>
        //<editor-fold desc="eTxtGreenBack">

        final ChangeListener<String> etxtGreenBackchange = new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!(newValue.isEmpty())) {
                    eTxtGreenBack.textProperty().removeListener(this);
                    if (!isInteger(newValue)) {
                        eTxtGreenBack.setText(oldValue);
                    } else {
                        greenSliderBack.setValue((double) Integer.valueOf(eTxtGreenBack.getText()));
                    }
                    if (isInteger(newValue) && Integer.valueOf(newValue) >= 256) {
                        eTxtGreenBack.setText("255");
                    }
                    eTxtGreenBack.textProperty().addListener(this);
                }
            }
        };
        eTxtGreenBack.textProperty().addListener(etxtGreenBackchange);
        eTxtGreenBack.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (!newValue && eTxtGreenBack.getText().isEmpty()) {
                    greenSliderBack.setValue(0.0);
                }
            }
        });
        //</editor-fold>
        //<editor-fold desc="eTxtBlueBack">

        final ChangeListener<String> etxtBlueBackchange = new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!(newValue.isEmpty())) {
                    eTxtBlueBack.textProperty().removeListener(this);
                    if (!isInteger(newValue)) {
                        eTxtBlueBack.setText(oldValue);
                        System.out.print("lol");
                    } else {
                        blueSliderBack.setValue((double) Integer.valueOf(eTxtBlueBack.getText()));
                    }
                    if (isInteger(newValue) && Integer.valueOf(newValue) >= 256) {
                        eTxtBlueBack.setText("255");
                    }
                    eTxtBlueBack.textProperty().addListener(this);
                }
            }
        };
        eTxtBlueBack.textProperty().addListener(etxtBlueBackchange);
        eTxtBlueBack.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (!newValue && eTxtBlueBack.getText().isEmpty()) {
                    blueSliderBack.setValue(0.0);
                }
            }
        });
        //</editor-fold>

        //<editor-fold desc="eTxtRedIn">

        final ChangeListener<String> etxtRedInchange = new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!(newValue.isEmpty())) {
                    eTxtRedIn.textProperty().removeListener(this);
                    if (!isInteger(newValue)) {
                        eTxtRedIn.setText(oldValue);
                        System.out.print("lol");
                    } else {
                        redSliderIn.setValue((double) Integer.valueOf(eTxtRedIn.getText()));
                    }
                    if (isInteger(newValue) && Integer.valueOf(newValue) >= 256) {
                        eTxtRedIn.setText("255");
                    }
                    eTxtRedIn.textProperty().addListener(this);
                }
            }
        };
        eTxtRedIn.textProperty().addListener(etxtRedInchange);
        eTxtRedIn.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (!newValue && eTxtRedIn.getText().isEmpty()) {
                    redSliderIn.setValue(0.0);
                }
            }
        });
        //</editor-fold>
        //<editor-fold desc="eTxtGreenIn">

        final ChangeListener<String> etxtGreenInchange = new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!(newValue.isEmpty())) {
                    eTxtGreenIn.textProperty().removeListener(this);
                    if (!isInteger(newValue)) {
                        eTxtGreenIn.setText(oldValue);
                        System.out.print("lol");
                    } else {
                        greenSliderIn.setValue((double) Integer.valueOf(eTxtGreenIn.getText()));
                    }
                    if (isInteger(newValue) && Integer.valueOf(newValue) >= 256) {
                        eTxtGreenIn.setText("255");
                    }
                    eTxtGreenIn.textProperty().addListener(this);
                }
            }
        };
        eTxtGreenIn.textProperty().addListener(etxtGreenInchange);
        eTxtGreenIn.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (!newValue && eTxtGreenIn.getText().isEmpty()) {
                    greenSliderIn.setValue(0.0);
                }
            }
        });
        //</editor-fold>
        //<editor-fold desc="eTxtBlueIn">

        final ChangeListener<String> etxtBlueInchange = new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!(newValue.isEmpty())) {
                    eTxtBlueIn.textProperty().removeListener(this);
                    if (!isInteger(newValue)) {
                        eTxtBlueIn.setText(oldValue);
                        System.out.print("lol");
                    } else {
                        blueSliderIn.setValue((double) Integer.valueOf(eTxtBlueIn.getText()));
                    }
                    if (isInteger(newValue) && Integer.valueOf(newValue) >= 256) {
                        eTxtBlueIn.setText("255");
                    }
                    eTxtBlueIn.textProperty().addListener(this);
                }
            }
        };
        eTxtBlueIn.textProperty().addListener(etxtBlueInchange);
        eTxtBlueIn.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (!newValue && eTxtBlueIn.getText().isEmpty()) {
                    blueSliderIn.setValue(0.0);
                }
            }
        });
        //</editor-fold>

        //</editor-fold>



        btnPolygon(new ActionEvent());
        txtArea.setText("Hier steht dann der Path...");
        polyCreated = false;
        drawCanvas();
        updateTitle("Polygon0.3");
        ableSaveMenu();
    }


    @FXML
    private void btnPolygon(ActionEvent actionEvent) {
        polyCreated = true;

        GraphicsContext gcBack = canvasBack.getGraphicsContext2D();
        GraphicsContext gcPolygon = canvasIn.getGraphicsContext2D();
        setValues();
        setRotation();

        calculatePolygon cP = new calculatePolygon();
        cP.paintPolygon(gcBack, gcPolygon, rgbBack, rgbIn, n, star);

        String path = cP.getPath(size, offsetx, offsety, duration, n, star, ani, rgbIn);
        txtArea.setText(path);
        ableSaveMenu();
    }

    private void setValues() {
        n = Integer.valueOf(eTxtN.getText());
        size = Integer.valueOf(eTxtSize.getText());
        offsetx = Integer.valueOf(eTxtOffsetx.getText());
        offsety = Integer.valueOf(eTxtOffsety.getText());
        duration = Integer.valueOf(eTxtDuration.getText()) * 1000;

        rgbBack[0] = (int) Math.round(redSliderBack.getValue());
        rgbBack[1] = (int) Math.round(greenSliderBack.getValue());
        rgbBack[2] = (int) Math.round(blueSliderBack.getValue());

        rgbIn[0] = (int) Math.round(redSliderIn.getValue());
        rgbIn[1] = (int) Math.round(greenSliderIn.getValue());
        rgbIn[2] = (int) Math.round(blueSliderIn.getValue());

        star = checkStar.isSelected();
        ani = checkAni.isSelected();
    }

    private void setSliderValue() {
        eTxtRedBack.setText(String.valueOf((int) Math.round(redSliderBack.getValue())));
        eTxtGreenBack.setText(String.valueOf((int) Math.round(greenSliderBack.getValue())));
        eTxtBlueBack.setText(String.valueOf((int) Math.round(blueSliderBack.getValue())));

        eTxtRedIn.setText(String.valueOf((int) Math.round(redSliderIn.getValue())));
        eTxtGreenIn.setText(String.valueOf((int) Math.round(greenSliderIn.getValue())));
        eTxtBlueIn.setText(String.valueOf((int) Math.round(blueSliderIn.getValue())));

        setValues();
        drawCanvas();
    }

    private void drawCanvas() {
        GraphicsContext gcBackColor = canvasBackColor.getGraphicsContext2D();
        GraphicsContext gcInColor = canvasInColor.getGraphicsContext2D();

        gcBackColor.clearRect(0, 0, 100, 100);
        gcBackColor.setFill(Color.rgb(rgbBack[0], rgbBack[1], rgbBack[2]));
        gcBackColor.fillRect(0, 0, 100, 100);
        gcBackColor.setStroke(Color.BLACK);
        gcBackColor.strokeRect(0, 0, 100, 100);

        gcInColor.clearRect(0, 0, 100, 100);
        gcInColor.setFill(Color.rgb(rgbIn[0], rgbIn[1], rgbIn[2]));
        gcInColor.fillRect(0, 0, 100, 100);
        gcInColor.setStroke(Color.BLACK);
        gcInColor.strokeRect(0, 0, 100, 100);
    }

    private void setRotation() {
        rt.stop();
        rt.setNode(canvasIn);
        canvasIn.setRotate(0.0);
        rt.setDuration(millis(duration));
        if (ani) {
            rt.setCycleCount(Animation.INDEFINITE);
            rt.setInterpolator(Interpolator.LINEAR);
            rt.setByAngle(360);
            rt.play();
        }
    }

    //is Integer methode
    private static boolean isInteger(String s) {
        return isInteger(s, 10);
    }

    private static boolean isInteger(String s, int radix) {
        if (s.isEmpty()) return false;
        for (int i = 0; i < s.length(); i++) {
            if (i == 0 && s.charAt(i) == '-') {
                if (s.length() == 1) return false;
                else continue;
            }
            if (Character.digit(s.charAt(i), radix) < 0) return false;
        }
        return true;
    }


    //Menubar
    //menus Data

    @Override
    public void setPrimaryStage(Stage primaryStage) {
        this.stage = primaryStage;
    }


    @FXML
    private void newdata() {
        file = null;
        setDefaultValues();
        ableSaveMenu();
        updateTitle("Polygon0.3");
    }

    @FXML
    private void opendata() {
        Save s = new Save();
        File fileOld = file;
        file = s.openData();
        if (file != null) {
            String[] content = s.encryptOpenData(file);
            if ((!content[0].equals("failed")) && (content.length >= 14)) {
                eTxtN.setText(content[0]);
                eTxtSize.setText(content[1]);
                eTxtOffsetx.setText(content[2]);
                eTxtOffsety.setText(content[3]);
                eTxtDuration.setText(String.valueOf(Integer.valueOf(content[4]) / 1000));
                eTxtRedBack.setText(content[6]);
                eTxtGreenBack.setText(content[7]);
                eTxtBlueBack.setText(content[8]);
                eTxtRedIn.setText(content[10]);
                eTxtGreenIn.setText(content[11]);
                eTxtBlueIn.setText(content[12]);

                checkStar.setSelected(content[13].equals("true"));
                checkAni.setSelected(content[14].equals("true"));

                //checkAni.setSelected(content[14].equals(true));
                //TODO: fertig machen
                btnPolygon(new ActionEvent());
                polyCreated = true;
                ableSaveMenu();
                updateTitle("Polygon0.3 " + file.getName());
            } else {
                ControllerErrorMessage cEM = new ControllerErrorMessage();
                cEM.errorMessage();
                file = fileOld;
                if (file== null){
                    updateTitle("Polygon0.3");
                }else{
                    updateTitle("Polygon0.3 "+file.getName());
                }
            }
        }
    }

    @FXML
    private void savedata() {
        ableSaveMenu();
    }

    @FXML
    private void savedataas() {
        setValues();
        String content = String.valueOf(n) + ";" + String.valueOf(size) + ";" + String.valueOf(offsetx)
                + ";" + String.valueOf(offsety) + ";" + String.valueOf(duration) + ";BACK;"
                + String.valueOf(rgbBack[0]) + ";" + String.valueOf(rgbBack[1]) + ";"
                + String.valueOf(rgbBack[2]) + ";IN;" + String.valueOf(rgbIn[0]) + ";"
                + String.valueOf(rgbIn[1]) + ";" + String.valueOf(rgbIn[2]) + ";" + String.valueOf(star)
                + ";" + String.valueOf(ani) + ";";


        Save s = new Save();

        file = s.saveDataAs(s.encode(content));
        if (file != null) {
            ableSaveMenu();
            updateTitle("Polygon0.3 " + file.getName());
        }
    }

    @FXML
    private void  menuexit() {
        System.exit(0);
    }

    @FXML
    public void exportXML() {
        Save s = new Save();
        int[] sizeProperty = {2 * size + offsetx, 2 * size + offsety};
        s.exportAsXML(txtArea.getText(), rgbBack, sizeProperty);


    }

    //menus bearbeiten

    @FXML
    public void editCenter() {
        eTxtOffsetx.setText(String.valueOf(2 * size));
        eTxtOffsety.setText(String.valueOf(2 * size));
        setValues();
    }

    private void setDefaultValues() {
        eTxtN.setText("5");
        eTxtSize.setText("50");
        eTxtOffsetx.setText("100");
        eTxtOffsety.setText("100");
        eTxtDuration.setText("6");
        checkStar.setSelected(true);
        checkAni.setSelected(false);
        redSliderBack.valueProperty().setValue(0);
        greenSliderBack.valueProperty().setValue(0);
        blueSliderBack.valueProperty().setValue(255);
        redSliderIn.valueProperty().setValue(255);
        greenSliderIn.valueProperty().setValue(255);
        blueSliderIn.valueProperty().setValue(0);
        btnPolygon(new ActionEvent());
        txtArea.setText("Hier steht dann der Path...");
        polyCreated = false;
    }

    private void ableSaveMenu() {
        if (polyCreated) {
            menuSaveAs.setDisable(false);
            menuExport.setDisable(false);
        } else {
            menuSave.setDisable(true);
            menuSaveAs.setDisable(true);
            menuExport.setDisable(true);
        }
        if (file != null) {
            menuSave.setDisable(false);
        }
    }

    private void updateTitle(String title) {
        if (stage != null) {
            stage.setTitle(title);
        } else {
            System.out.println("Warning: null stage");
        }
    }

    public void openhelp(ActionEvent actionEvent) {

      //TODO: Informationen einfügen

    }
}
