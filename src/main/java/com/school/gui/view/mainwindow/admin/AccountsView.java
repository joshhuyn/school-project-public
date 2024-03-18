package com.school.gui.view.mainwindow.admin;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.school.data.model.UserModel;
import com.school.gui.framework.UiContext;
import com.school.gui.view.mainwindow.AbstractViewTemplate;
import com.school.gui.view.stateholder.admin.AccountStateHolder;
import com.school.gui.view.uicomponent.tableview.TableViewPanel;

public class AccountsView extends AbstractViewTemplate
{
    private final Dimension cellDim = new Dimension(140, 27);
    private AccountStateHolder stateHolder = AccountStateHolder.get();

    @Override
    public JPanel getContentPanel()
    {
        JPanel panel = new JPanel(new BorderLayout());

        JLabel label = new JLabel("Edit Users");
        label.setFont(headerFont);
        panel.add(label, BorderLayout.NORTH);

        panel.add(createUsersList(), BorderLayout.CENTER);

        return panel;
    }

    private JPanel createUsersList()
    {
        String[] headers = new String[] {
            cfg.get("base.user.Id"),
            cfg.get("base.user.Username"),
            cfg.get("base.user.Role"),
            "Aktionen"
        };

        List<UserModel> users = sessionController.getUsers();
        JComponent[][] data = new JComponent[users.size()][];
            
        for (int usr = 0; usr < users.size(); usr++)
        {
            JButton edit = new JButton(cfg.get("ui.base.Edit"));
            JButton delete = new JButton(cfg.get("ui.base.Delete"));

            final int usrIndex = usr;

            edit.addActionListener(a -> {
                stateHolder.setUserModel(users.get(usrIndex));
                UiContext.get().update(EditUserView.class);
            });

            delete.addActionListener(a -> {
                sessionController.deleteUserById(users.get(usrIndex).getId());
                UiContext.get().update(this.getClass());
            });

            delete.setEnabled(users.get(usr).getId() != sessionController.getLoggedInUser().getId());

            JPanel btnPanel = new JPanel(new GridLayout(1, 2));

            btnPanel.add(edit);
            btnPanel.add(delete);

            data[usr] = new JComponent[] {
                new JLabel(Long.toString(users.get(usr).getId())),
                new JLabel(users.get(usr).getUsername()),
                new JLabel(users.get(usr).getRole().getDbLabel()),
                btnPanel
            };
        }

        return new TableViewPanel(headers, data);
    }
}
