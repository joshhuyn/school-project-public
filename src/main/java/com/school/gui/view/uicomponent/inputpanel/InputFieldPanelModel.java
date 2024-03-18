package com.school.gui.view.uicomponent.inputpanel;

import javax.swing.JComponent;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class InputFieldPanelModel
{
    private String labelText;
    private JComponent component;
}
