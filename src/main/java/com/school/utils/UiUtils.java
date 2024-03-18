package com.school.utils;

import java.awt.Component;

import javax.swing.JPanel;

public class UiUtils
{
    public static void disablePanel(JPanel panel)
    {
        for (Component c : panel.getComponents())
        {
            if (c instanceof JPanel p)
            {
                disablePanel(p);
            }
            c.setEnabled(false);
        }
    }
}
