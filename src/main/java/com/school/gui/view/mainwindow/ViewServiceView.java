package com.school.gui.view.mainwindow;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.school.data.model.PatientServiceModel;
import com.school.gui.framework.UiContext;
import com.school.gui.view.controller.EditServiceController;
import com.school.gui.view.stateholder.EditPatientStateHolder;
import com.school.gui.view.uicomponent.tableview.TableViewPanel;

public class ViewServiceView extends AbstractViewTemplate
{
    private EditServiceController controller = new EditServiceController();
    private EditPatientStateHolder patientStateHolder = EditPatientStateHolder.get();

    @Override
    public JPanel getContentPanel()
    {
        JPanel panel = new JPanel(new BorderLayout());

        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.X_AXIS));

        JLabel header = new JLabel("Leistungen einsehen");
        header.setFont(headerFont);

        JButton back = new JButton(cfg.get("ui.base.Back"));
        back.addActionListener(a -> UiContext.get().update(EditPatientView.class));

        headerPanel.add(header);
        headerPanel.add(back);

        panel.add(headerPanel, BorderLayout.NORTH);
        panel.add(createTable(), BorderLayout.CENTER);

        return panel;
    }

    private JPanel createTable()
    {
        String[] headers = new String[] {
            "Id",
            "Paid",
            "Cost",
            "Action"
        };

        List<PatientServiceModel> models = controller.getAllById(patientStateHolder.getPatientModel().getId());
        JComponent[][] data = new JComponent[models.size()][];

        for (int i = 0; i < models.size(); i++)
        {
            JButton editBtn = new JButton(cfg.get("ui.base.Edit"));
            JButton deleteBtn = new JButton(cfg.get("ui.base.Delete"));

            final int modelIndex = i;
            deleteBtn.addActionListener(a -> {
                controller.deleteById(models.get(modelIndex).getId());
                UiContext.get().update(this.getClass());
            });

            editBtn.addActionListener(a -> {
                patientStateHolder.setPatientServiceModel(models.get(modelIndex));
                UiContext.get().update(EditServiceView.class);
            });

            JPanel actionPanel = new JPanel(new GridLayout(1, 2));

            actionPanel.add(editBtn);
            actionPanel.add(deleteBtn);

            data[i] = new JComponent[] {
                new JLabel(Long.toString(models.get(i).getId())),
                new JLabel(models.get(i).getPaid() ? "Ja" : "Nein"),
                new JLabel(String.format("%f Euro", models.get(i).getCost())),
                actionPanel
            };
            
        }

        return new TableViewPanel(headers, data);
    }
}
