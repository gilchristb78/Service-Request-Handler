package edu.wpi.cs3733.c22.teamB.controllers;

import com.jfoenix.controls.JFXComboBox;
import edu.wpi.cs3733.c22.teamB.AutoCompleteComboBox;
import edu.wpi.cs3733.c22.teamB.ComboBoxAutoComplete;
import edu.wpi.cs3733.c22.teamB.entity.DatabaseWrapper;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Tooltip;

import java.util.stream.Collectors;

public class DashboardController {

    @FXML private JFXComboBox<String> testComboBox;
    @FXML private JFXComboBox<String> cmb;
    private AutoCompleteComboBox<String> cb;
    private ComboBoxAutoComplete cb2;

    @FXML
    public void initialize() {
        cb = new AutoCompleteComboBox<>(testComboBox, (new DatabaseWrapper()).getAllLocation().stream().map(location -> location.getLongName()).collect(Collectors.toList()));

        cmb.getItems().addAll((new DatabaseWrapper()).getAllLocation().stream().map(location -> location.getLongName()).collect(Collectors.toList()));
        cb2 = new ComboBoxAutoComplete(cmb);
    }
}