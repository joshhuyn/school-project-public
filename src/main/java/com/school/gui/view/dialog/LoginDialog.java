package com.school.gui.view.dialog;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.school.data.enums.UserRole;
import com.school.data.model.UserModel;
import com.school.gui.framework.UiContext;
import com.school.gui.view.controller.SessionController;
import com.school.gui.view.mainwindow.HomeView;
import com.school.gui.view.uicomponent.inputpanel.InputFieldPanel;
import com.school.gui.view.uicomponent.inputpanel.InputFieldPanelModel;
import com.school.utils.tools.config.ConfigProvider;

public class LoginDialog
{
    private final SessionController sessionController = new SessionController();
    private ConfigProvider cfg = new ConfigProvider();

    private JDialog dialog;

    public LoginDialog()
    {
        dialog = new JDialog();
        dialog.setTitle(cfg.get("login.Header"));
        dialog.setResizable(false);
        dialog.setSize(300, 150);
        dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        dialog.setLocationRelativeTo(null);

        if (sessionController.getUsers().isEmpty())
        {
            setRegisterPanel();
        }
        else
        {
            setLoginPanel();
        }

        dialog.setVisible(true);
    }

    private void setLoginPanel()
    {
        updateContentPanel(createLoginPanel());
    }

    private JPanel createLoginPanel()
    {
        InputFieldPanelModel[] models = new InputFieldPanelModel[] {
            new InputFieldPanelModel(cfg.get("login.Username"), new JTextField()),
            new InputFieldPanelModel(cfg.get("login.Password"), new JPasswordField())
        };

        JButton[] buttons = new JButton[] {
            new JButton(cfg.get("login.Register")),
            new JButton(cfg.get("login.Continue"))
        };

        InputFieldPanel panel = new InputFieldPanel(models, buttons, 125);

        buttons[0].addActionListener(a -> setRegisterPanel());
        buttons[1].addActionListener(a -> {
            UserModel model = new UserModel();
            model.setUsername(((JTextField) models[0].getComponent()).getText().toLowerCase());
            model.setPassword(String.copyValueOf(((JPasswordField) models[1].getComponent()).getPassword()));

            if (sessionController.tryLogin(model))
            {
                panel.getMsg().setText(cfg.get("login.Success"));
                UiContext.get().update(HomeView.class);
                dialog.dispose();
            }
            else
            {
                panel.getMsg().setText(cfg.get("login.Failure"));
            }
        });

        return panel;
    }

    private void setRegisterPanel()
    {
        updateContentPanel(createRegsiterPanel());
    }

    private JPanel createRegsiterPanel()
    {
        InputFieldPanelModel[] inputFields = new InputFieldPanelModel[] {
            new InputFieldPanelModel(cfg.get("register.Username"), new JTextField()),
            new InputFieldPanelModel(cfg.get("register.Password"), new JPasswordField()),
            new InputFieldPanelModel(cfg.get("register.PasswordConfirm"), new JPasswordField())
        };

        JButton[] buttons = new JButton[] {
            new JButton(cfg.get("register.Back")),
            new JButton(cfg.get("register.Proceed"))
        };

        InputFieldPanel panel = new InputFieldPanel(inputFields, buttons, 125);

        buttons[0].addActionListener(a -> setLoginPanel());
        buttons[1].addActionListener(a -> {
            String username = ((JTextField) inputFields[0].getComponent()).getText().toLowerCase();
            JPasswordField pass = (JPasswordField) inputFields[1].getComponent();
            JPasswordField pass_confirm = (JPasswordField) inputFields[2].getComponent();

            boolean passIsSame = String.copyValueOf(pass.getPassword()).equals(String.copyValueOf(pass_confirm.getPassword()));
            boolean userDoesNotExist = !sessionController.usernameExists(username);
            boolean usernameNotEmpty = username != null && !username.equals("");

            if (passIsSame && userDoesNotExist && usernameNotEmpty)
            {
                final UserModel model = new UserModel();

                if (sessionController.getUsers().isEmpty())
                {
                    model.setRole(UserRole.Admin);
                }
                else
                {
                    model.setRole(UserRole.NotVerified);
                }
                model.setUsername(username);
                model.setPassword(String.copyValueOf(pass.getPassword()));

                panel.getMsg().setText(cfg.get("register.Success"));

                sessionController.register(model);
            }
            else if (!passIsSame)
            {
                panel.getMsg().setText(cfg.get("register.FailurePass"));
            }
            else if (!userDoesNotExist)
            {
                panel.getMsg().setText(cfg.get("register.FailureUser"));
            } 
            else
            {
                panel.getMsg().setText(cfg.get("register.FailureNull"));
            }
        });

        return panel;
    }

    private void updateContentPanel(JPanel panel)
    {
        dialog.getContentPane().removeAll();
        dialog.add(panel);
        dialog.repaint();
        dialog.revalidate();
        dialog.setVisible(true);
    }
}
