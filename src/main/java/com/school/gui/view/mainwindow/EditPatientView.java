package com.school.gui.view.mainwindow;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.school.data.model.PatientModel;
import com.school.gui.framework.UiContext;
import com.school.gui.view.stateholder.EditPatientStateHolder;
import com.school.gui.view.uicomponent.inputpanel.InputFieldPanel;
import com.school.gui.view.uicomponent.inputpanel.InputFieldPanelModel;
import com.school.gui.view.controller.EditPatientController;

public class EditPatientView extends AbstractViewTemplate
{
    private final EditPatientController editPatientController = new EditPatientController();

    private EditPatientStateHolder stateHolder = EditPatientStateHolder.get();

    @Override
    public JPanel getContentPanel()
    {
        JPanel contentPanel = new JPanel(new BorderLayout());

        JLabel label = new JLabel(cfg.get("editpatientview.Header"));
        label.setFont(headerFont);
        contentPanel.add(label, BorderLayout.NORTH);

        stateHolder.setPatientServiceModel(null);

        contentPanel.add(createInputFieldPanel(), BorderLayout.CENTER);

        return contentPanel;
    }

    private JPanel createInputFieldPanel()
    {
        InputFieldPanelModel[] inputFields = new InputFieldPanelModel[] {
            new InputFieldPanelModel(cfg.get("base.Id"), new JTextField(Long.toString(stateHolder.getPatientModel().getId()))),
            new InputFieldPanelModel(cfg.get("base.Name"), new JTextField(stateHolder.getPatientModel().getName())),
            new InputFieldPanelModel(cfg.get("base.First"), new JTextField(stateHolder.getPatientModel().getFirst())),
            new InputFieldPanelModel(cfg.get("base.City"), new JTextField(stateHolder.getPatientModel().getCity())),
            new InputFieldPanelModel(cfg.get("base.Postal"), new JTextField(stateHolder.getPatientModel().getPostal())),
            new InputFieldPanelModel(cfg.get("base.Street"), new JTextField(stateHolder.getPatientModel().getStreet())),
            new InputFieldPanelModel(cfg.get("base.Tel"), new JTextField(stateHolder.getPatientModel().getTel())),
        };

        JButton[] buttons = new JButton[] {
            new JButton(cfg.get("ui.base.Back")),
            new JButton(cfg.get("ui.base.Save")),
            new JButton(cfg.get("editpatientview.RegisterService")),
            new JButton(cfg.get("editpatientview.ViewServices"))
        };

        buttons[0].addActionListener(a -> {
            UiContext.get().update(ViewPatientView.class);
        });

        buttons[1].addActionListener(a -> {
            PatientModel patientModel = new PatientModel();

            patientModel.setId(Long.parseLong(((JTextField) inputFields[0].getComponent()).getText()));
            patientModel.setName(((JTextField) inputFields[1].getComponent()).getText());
            patientModel.setFirst(((JTextField) inputFields[2].getComponent()).getText());
            patientModel.setCity(((JTextField) inputFields[3].getComponent()).getText());
            patientModel.setPostal(((JTextField) inputFields[4].getComponent()).getText());
            patientModel.setStreet(((JTextField) inputFields[5].getComponent()).getText());
            patientModel.setTel(((JTextField) inputFields[6].getComponent()).getText());

            editPatientController.save(patientModel);
        });


        buttons[2].addActionListener(a -> {
            UiContext.get().update(EditServiceView.class);
        });

        buttons[3].addActionListener(a -> {
            UiContext.get().update(ViewServiceView.class);
        });

        return new InputFieldPanel(inputFields, buttons);
    }
}
