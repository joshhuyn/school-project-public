package com.school.gui.view.mainwindow;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.school.data.model.PatientModel;
import com.school.gui.view.controller.AddPatientController;
import com.school.gui.view.uicomponent.inputpanel.InputFieldPanel;
import com.school.gui.view.uicomponent.inputpanel.InputFieldPanelModel;

public class AddPatientView extends AbstractViewTemplate
{
    private final AddPatientController controller = new AddPatientController();

    @Override
    public JPanel getContentPanel()
    {
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BorderLayout());

        JLabel label = new JLabel(cfg.get("addpatientview.Header"));
        label.setFont(headerFont);
        contentPanel.add(label, BorderLayout.NORTH);
        contentPanel.add(getInputFieldPanel(), BorderLayout.CENTER);

        return contentPanel;
    }

    private JPanel getInputFieldPanel()
    {
        InputFieldPanelModel[] inputFields = new InputFieldPanelModel[] {
            new InputFieldPanelModel(cfg.get("base.Id"), new JTextField()),
            new InputFieldPanelModel(cfg.get("base.Name"), new JTextField()),
            new InputFieldPanelModel(cfg.get("base.First"), new JTextField()),
            new InputFieldPanelModel(cfg.get("base.City"), new JTextField()),
            new InputFieldPanelModel(cfg.get("base.Postal"), new JTextField()),
            new InputFieldPanelModel(cfg.get("base.Street"), new JTextField()),
            new InputFieldPanelModel(cfg.get("base.Tel"), new JTextField()),
        };

        JButton[] buttons = new JButton[] { new JButton(cfg.get("addpatientview.Submit")) };

        buttons[0].addActionListener(a -> {
            PatientModel model = new PatientModel();

            model.setName(((JTextField) inputFields[0].getComponent()).getText());
            model.setFirst(((JTextField) inputFields[1].getComponent()).getText());
            model.setCity(((JTextField) inputFields[2].getComponent()).getText());
            model.setPostal(((JTextField) inputFields[3].getComponent()).getText());
            model.setStreet(((JTextField) inputFields[4].getComponent()).getText());
            model.setTel(((JTextField) inputFields[5].getComponent()).getText());

            controller.save(model);
        });

        return new InputFieldPanel(inputFields, buttons);
    }
}
