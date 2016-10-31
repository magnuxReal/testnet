/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

import javax.swing.DefaultCellEditor;
import javax.swing.JFormattedTextField;

/**
 *
 * @author Karolis
 */
public class DoublesCellEditor extends DefaultCellEditor {

    private JFormattedTextField textField;

    public DoublesCellEditor(JFormattedTextField jft) {
        super(jft);
        this.textField = jft;
        super.delegate = new EditorDelegate() {
            public void setValue(Object value) {
                textField.setValue(value != null ? ((Number) value).doubleValue() : value);
            }

            public Object getCellEditorValue() {
                Object value = textField.getValue();
                return value != null ? ((Number) value).doubleValue() : value;
            }
        };
    }
}
