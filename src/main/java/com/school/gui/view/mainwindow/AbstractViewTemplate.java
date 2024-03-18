package com.school.gui.view.mainwindow;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.school.data.enums.UserRole;
import com.school.gui.framework.UiContext;
import com.school.gui.view.AbstractView;
import com.school.gui.view.controller.SessionController;
import com.school.gui.view.dialog.LoginDialog;
import com.school.gui.view.mainwindow.admin.AccountsView;
import com.school.utils.UiUtils;
import com.school.utils.tools.config.ConfigProvider;

public abstract class AbstractViewTemplate extends AbstractView
{
    protected final ConfigProvider cfg = new ConfigProvider();
    protected final Font headerFont = new Font("Hack", Font.BOLD, 20);

    protected final SessionController sessionController = new SessionController();

    private final JPanel panel = new JPanel();

    public abstract JPanel getContentPanel();

    @Override
    public JPanel getView()
    {
        panel.setLayout(new BorderLayout());

        panel.add(createNavigator(), BorderLayout.WEST);

        JPanel contentPanel = getContentPanel();

        JPanel marginContentPanel = new JPanel(new BorderLayout());
        marginContentPanel.add(contentPanel, BorderLayout.CENTER);

        JScrollPane scrollPane = new JScrollPane(
            marginContentPanel, 
            JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
            JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED
        );

        panel.add(scrollPane, BorderLayout.CENTER);

        if (!sessionController.isLoggedIn())
        {
            UiUtils.disablePanel(panel);
            new LoginDialog();
        }
        else if (sessionController.getLoggedInUser().getRole() == UserRole.NotVerified)
        {
            UiUtils.disablePanel(panel);
        }

        return panel;
    }

    private JPanel createNavigator()
    {
        JPanel navigator = new JPanel();
        navigator.setLayout(new GridBagLayout());

        JButton homeView = new JButton(cfg.get("navigator.Homeview"));
        JButton addPatientView = new JButton(cfg.get("navigator.AddPatient"));
        JButton viewPatientView = new JButton(cfg.get("navigator.ViewPatient"));


        homeView.addActionListener(a -> UiContext.get().update(HomeView.class));
        addPatientView.addActionListener(a -> UiContext.get().update(AddPatientView.class));
        viewPatientView.addActionListener(a -> UiContext.get().update(ViewPatientView.class));

        List<JButton> buttons = new ArrayList<>(List.of(homeView, addPatientView, viewPatientView));

        if (sessionController.isLoggedIn() && sessionController.getLoggedInUser().getRole() == UserRole.Admin)
        {
            JButton userView = new JButton(cfg.get("navigator.ViewUsers"));
            userView.addActionListener(a -> UiContext.get().update(AccountsView.class));
            buttons.add(userView);
        }
        
        buttons.forEach(b -> b.setPreferredSize(new Dimension(175, 25)));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        buttons.forEach(b -> {
            navigator.add(b, gbc);
            gbc.gridy += 1;
        });

        navigator.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, Color.BLACK));

        return navigator;
    }
}
