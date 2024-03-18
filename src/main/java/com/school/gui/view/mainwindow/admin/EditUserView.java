package com.school.gui.view.mainwindow.admin;

import java.util.List;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.school.data.enums.UserRole;
import com.school.data.model.UserModel;
import com.school.gui.framework.UiContext;
import com.school.gui.view.mainwindow.AbstractViewTemplate;
import com.school.gui.view.stateholder.admin.AccountStateHolder;
import com.school.gui.view.uicomponent.inputpanel.InputFieldPanel;
import com.school.gui.view.uicomponent.inputpanel.InputFieldPanelModel;

public class EditUserView extends AbstractViewTemplate
{
    private AccountStateHolder stateHolder = AccountStateHolder.get();

    @Override
    public JPanel getContentPanel()
    {
        JPanel contentPanel = new JPanel(new BorderLayout());

        JLabel label = new JLabel(cfg.get("edituserview.Header"));
        label.setFont(headerFont);

        contentPanel.add(label, BorderLayout.NORTH);
        contentPanel.add(createInputPanel(), BorderLayout.CENTER);

        return contentPanel;
    }

    private JPanel createInputPanel()
    {
        UserModel user = sessionController.getUserByUsername(stateHolder.getUserModel().getUsername());

        JComboBox<UserRole> box = new JComboBox<>(new UserRole[] {UserRole.Admin, UserRole.User, UserRole.NotVerified});
        box.setSelectedItem(user.getRole());

        InputFieldPanelModel[] inputFields = new InputFieldPanelModel[] {
            new InputFieldPanelModel("Id", new JTextField(Long.toString(user.getId()))),
            new InputFieldPanelModel("Username", new JTextField(user.getUsername())),
            new InputFieldPanelModel("Salt", new JTextField(user.getSalt())),
            new InputFieldPanelModel("Role", box)
        };

        List.of(inputFields).forEach(i -> i.getComponent().setEnabled(false));
        inputFields[3].getComponent().setEnabled(user.getId() != sessionController.getLoggedInUser().getId());

        JButton[] buttons = new JButton[] {
            new JButton(cfg.get("ui.base.Back")),
            new JButton(cfg.get("ui.base.Save"))
        };

        InputFieldPanel panel = new InputFieldPanel(inputFields, buttons);

        buttons[0].addActionListener(a -> {
            UiContext.get().update(AccountsView.class);
        });

        buttons[1].addActionListener(a -> {
            user.setRole((UserRole) box.getSelectedItem());

            sessionController.save(user);

            panel.getMsg().setText(cfg.get("base.SaveSuccess"));
        });

        return new InputFieldPanel(inputFields, buttons);
    }
}
