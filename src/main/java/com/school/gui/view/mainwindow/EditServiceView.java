package com.school.gui.view.mainwindow;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

import com.school.data.enums.PatientServiceType;
import com.school.data.model.PatientServiceModel;
import com.school.gui.framework.UiContext;
import com.school.gui.view.controller.EditServiceController;
import com.school.gui.view.stateholder.EditPatientStateHolder;
import com.school.gui.view.uicomponent.inputpanel.InputFieldPanel;
import com.school.gui.view.uicomponent.inputpanel.InputFieldPanelModel;

public class EditServiceView extends AbstractViewTemplate
{
    private static final Pattern numberPattern = Pattern.compile("([0-9]+(?:\\.[0-9]+)?)");
    private EditServiceController editServiceController = new EditServiceController();
    private EditPatientStateHolder stateHolder = EditPatientStateHolder.get();

    @Override
    public JPanel getContentPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        JLabel label = new JLabel("Leistung erstellen");
        label.setFont(headerFont);
        panel.add(label, BorderLayout.NORTH);
        panel.add(createInputPanel(), BorderLayout.CENTER);

        return panel;
    }

    private JPanel createInputPanel()
    {
        JComboBox<PatientServiceType> comboBox = new JComboBox<PatientServiceType>(new PatientServiceType[] {PatientServiceType.Medical, PatientServiceType.NonMedical});
        comboBox.setSelectedItem(PatientServiceType.Medical);

        JTextField description = new JTextField();
        JTextField cost = new JTextField();

        JCheckBox checkbox = new JCheckBox();

        checkbox.setSelected(stateHolder.getPatientServiceModel() != null ?
            stateHolder.getPatientServiceModel().getPaid() :
            false);

        checkbox.setEnabled(false);

        if (stateHolder.getPatientServiceModel() != null)
        {
            description.setText(stateHolder.getPatientServiceModel().getDescription());
            cost.setText(stateHolder.getPatientServiceModel().getCost().toString());

            description.setEnabled(false);
            cost.setEnabled(false);
            comboBox.setEnabled(false);

            checkbox.setEnabled(true);
        }

        InputFieldPanelModel[] inputFields = new InputFieldPanelModel[]
        {
            new InputFieldPanelModel(cfg.get("editserviceview.Description"), description),
            new InputFieldPanelModel(cfg.get("editserviceview.Cost"), cost),
            new InputFieldPanelModel(cfg.get("editserviceview.Type"), comboBox),
            new InputFieldPanelModel(cfg.get("editserviceview.Paid"), checkbox)
        };

        JButton[] buttons = new JButton[] {
            new JButton(cfg.get("ui.base.Back")),
            new JButton(cfg.get("ui.base.Save"))
        };

        InputFieldPanel panel = new InputFieldPanel(inputFields, buttons);

        buttons[0].addActionListener(a -> UiContext.get().update(stateHolder.getPatientServiceModel() == null ? EditPatientView.class : ViewServiceView.class));

        buttons[1].addActionListener(a -> {
            PatientServiceModel model = new PatientServiceModel();
            if (stateHolder.getPatientServiceModel() != null)
            {
                model.setId(stateHolder.getPatientServiceModel().getId());
            }

            model.setDescription(((JTextField) inputFields[0].getComponent()).getText());
            model.setType((PatientServiceType) comboBox.getSelectedItem());
            model.setPatient(stateHolder.getPatientModel());

            model.setPaid(checkbox.isSelected());

            Matcher m = numberPattern.matcher(((JTextField) inputFields[1].getComponent()).getText().trim());

            if (m.matches())
            {
                model.setCost(Double.parseDouble(((JTextField) inputFields[1].getComponent()).getText().trim()));

                editServiceController.save(model);
            }
            else
            {
                panel.getMsg().setText(cfg.get("editserviceview.InvalidNumberError"));
            }
            UiContext.get().update(ViewServiceView.class);
        });

        return panel;
    }
}
