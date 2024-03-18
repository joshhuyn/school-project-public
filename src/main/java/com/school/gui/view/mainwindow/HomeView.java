package com.school.gui.view.mainwindow;

import java.awt.BorderLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class HomeView extends AbstractViewTemplate
{
    @Override
    public JPanel getContentPanel()
    {
        JPanel contentPanel = new JPanel(new BorderLayout());

        JLabel label = new JLabel();
        label.setText(cfg.get("homeview.Header"));
        label.setFont(headerFont);

        contentPanel.add(label, BorderLayout.NORTH);

        return contentPanel;
    }
}
