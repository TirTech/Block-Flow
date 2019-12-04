package ca.blockflow.views.blockeditor;

import ca.blockflow.blocks.OutputBlock;
import ca.blockflow.logic.Variable;
import ca.blockflow.models.AppModel;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OutputBlockEditor extends BlockEditor<OutputBlock> {
    
    private TextField txtString = new TextField();
    private Pattern varRegex = Pattern.compile("%(\\w+)%");
    private List<Variable> vars = AppModel.getInstance().getVariables();
    private Label lblError = new Label("The message contains illegal names");
    private Label howTo = new Label("Enter the text to print, using %var% to reference the value of 'var'");
    
    public OutputBlockEditor() {
        txtString.textProperty().addListener((obs, oldVal, newVal) -> lblError.setVisible(false));
        lblError.setVisible(false);
        this.getChildren().addAll(howTo, txtString, lblError);
    }
    
    @Override
    public void initUI() {
        txtString.setText(backingBlock.getMessage());
    }
    
    @Override
    public boolean onApply(ActionEvent event) {
        ArrayList<String> names = getVarNames(txtString.getText());
        if (! checkVarNames(names)) {
            lblError.setVisible(true);
            return false;
        } else {
            backingBlock.setMessage(txtString.getText());
            backingBlock.setNames(names);
            return true;
        }
    }
    
    private ArrayList<String> getVarNames(String input) {
        ArrayList<String> names = new ArrayList<>();
        Matcher matcher = varRegex.matcher(input);
        while (matcher.find()) names.add(matcher.group(1));
        return names;
    }
    
    private boolean checkVarNames(ArrayList<String> input) {
        for (String name : input) {
            boolean pass = false;
            for (Variable var : vars) {
                if (var.getName().equals(name)) {
                    pass = true;
                    break;
                }
            }
            if (! pass) return false;
        }
        return true;
    }
}
