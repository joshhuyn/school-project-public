package com.school.gui.view.mainwindow;

import java.util.List;
import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.school.data.model.PatientModel;
import com.school.gui.framework.UiContext;
import com.school.gui.view.controller.ViewPatientController;
import com.school.gui.view.stateholder.EditPatientStateHolder;
import com.school.gui.view.uicomponent.tableview.TableViewPanel;

public class ViewPatientView extends AbstractViewTemplate
{
    private final ViewPatientController viewPatientController = new ViewPatientController();

    private EditPatientStateHolder stateHolder = EditPatientStateHolder.get();

    @Override
    public JPanel getContentPanel()
    {
        JPanel contentPanel = new JPanel(new BorderLayout());

        JLabel label = new JLabel(cfg.get("viewpatientview.Header"));
        label.setFont(headerFont);
        contentPanel.add(label, BorderLayout.NORTH);

        contentPanel.add(createTable(), BorderLayout.CENTER);

        return contentPanel;
    }

    private JPanel createTable()
    {
        String[] headers = new String[] {
            cfg.get("base.Id"),
            cfg.get("base.First"),
            cfg.get("base.Name"),
            "Aktionen"
        };

        List<PatientModel> patients = viewPatientController.getPatients();
        JComponent[][] data = new JComponent[patients.size()][];


        for (int pat = 0; pat < patients.size(); pat++)
        {
            JButton edit = new JButton(cfg.get("ui.base.Edit"));
            JButton delete = new JButton(cfg.get("ui.base.Delete"));

            final int i = pat;

            edit.addActionListener(a -> {
                stateHolder.setPatientModel(patients.get(i));
                UiContext.get().update(EditPatientView.class);
            });

            delete.addActionListener(a -> {
                viewPatientController.deletePatient(patients.get(i).getId());
                UiContext.get().update(this.getClass());
            });

            JPanel btnPanel = new JPanel(new GridLayout(1, 2));

            btnPanel.add(edit);
            btnPanel.add(delete);

            data[pat] = new JComponent[] {
                new JLabel(Long.toString(patients.get(pat).getId())),
                new JLabel(patients.get(pat).getFirst()),
                new JLabel(patients.get(pat).getName()),
                btnPanel
            };
        }

        return new TableViewPanel(headers, data);
    }
}
