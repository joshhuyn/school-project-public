package com.school.utils.tools;

import java.util.ArrayList;
import java.util.List;
import java.awt.BorderLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import com.school.data.model.PatientModel;
import com.school.gui.view.uicomponent.LabelTextFieldComponent;
import com.school.utils.tools.config.ConfigProvider;

public class ComponentBuilder
{
    private static ConfigProvider cfg = new ConfigProvider();

    public static JPanel createMessageComponent()
    {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JLabel messageLabel = new JLabel();
        panel.add(messageLabel, BorderLayout.EAST);

        return panel;
    }

    public static List<LabelTextFieldComponent> getPatientModelFieldsMap()
    {
        return getPatientModelFieldsMap(null);
    }

    public static List<LabelTextFieldComponent> getPatientModelFieldsMap(PatientModel model)
    {
        List<LabelTextFieldComponent> components = new ArrayList<>();

        int labelAlign = SwingConstants.LEFT;

        components.add(new LabelTextFieldComponent(new JLabel(cfg.get("base.Id"), labelAlign), new JTextField()));
        components.add(new LabelTextFieldComponent(new JLabel(cfg.get("base.Name"), labelAlign), new JTextField()));
        components.add(new LabelTextFieldComponent(new JLabel(cfg.get("base.First"), labelAlign), new JTextField()));
        components.add(new LabelTextFieldComponent(new JLabel(cfg.get("base.City"), labelAlign), new JTextField()));
        components.add(new LabelTextFieldComponent(new JLabel(cfg.get("base.Postal"), labelAlign), new JTextField()));
        components.add(new LabelTextFieldComponent(new JLabel(cfg.get("base.Street"), labelAlign), new JTextField()));
        components.add(new LabelTextFieldComponent(new JLabel(cfg.get("base.Tel"), labelAlign), new JTextField()));

        if (model != null)
        {
            components.get(0).getTextField().setText(Long.toString(model.getId()));
            components.get(1).getTextField().setText(model.getName());
            components.get(2).getTextField().setText(model.getFirst());
            components.get(3).getTextField().setText(model.getCity());
            components.get(4).getTextField().setText(model.getPostal());
            components.get(5).getTextField().setText(model.getStreet());
            components.get(6).getTextField().setText(model.getTel());
        }

        return components;
    }
}
