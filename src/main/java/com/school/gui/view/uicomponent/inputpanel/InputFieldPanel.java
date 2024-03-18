package com.school.gui.view.uicomponent.inputpanel;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import lombok.Getter;
import lombok.Setter;

public class InputFieldPanel extends JPanel
{
    @Getter private InputFieldPanelModel[] models;
    @Getter private JButton[] buttons;
    @Getter private JLabel msg;

    private final int dimHeight = 25;
    @Getter @Setter private int dimUnit;

    private Dimension labelDim;
    private Dimension inputDim;
    private Dimension buttonDim;

    public InputFieldPanel(InputFieldPanelModel[] models, JButton[] buttons)
    {
        this(models, buttons, 150);
    }

    public InputFieldPanel(InputFieldPanelModel[] models, JButton[] buttons, int dimUnit)
    {
        super();

        this.dimUnit = dimUnit;

        this.models = models;
        this.buttons = buttons;
        msg = new JLabel();

        labelDim = new Dimension(dimUnit, dimHeight);
        inputDim = new Dimension(buttons.length > 1 ? dimUnit * (buttons.length - 1) : dimUnit, dimHeight);
        buttonDim = new Dimension((int) (labelDim.getWidth() + inputDim.getWidth()) / buttons.length, dimHeight);

        init();
    }

    public void init()
    {
        setLayout(new GridBagLayout());

        GridBagConstraints c = new GridBagConstraints();

        c.gridy = 0;
        c.gridx = 0;

        for (InputFieldPanelModel model : models)
        {
            add(new JLabel(model.getLabelText(), SwingConstants.LEFT), c);
            getComponent(getComponentCount() - 1).setPreferredSize(labelDim);

            c.gridx = 1;

            c.gridwidth = buttons.length > 1 ? buttons.length - 1 : 1;
            add(model.getComponent(), c);
            getComponent(getComponentCount() - 1).setPreferredSize(inputDim);
            
            c.gridwidth = 1;
            c.gridx = 0;
            c.gridy++;
        }

        c.gridwidth = buttons.length + 1;
        
        add(msg, c);

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new GridLayout(1, buttons.length));

        for (JButton button : buttons)
        {
            button.setPreferredSize(buttonDim);
            buttonsPanel.add(button);
        }

        add(buttonsPanel, c);
    }
}
