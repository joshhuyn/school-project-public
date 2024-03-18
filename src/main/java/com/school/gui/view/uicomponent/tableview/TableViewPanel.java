package com.school.gui.view.uicomponent.tableview;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

public class TableViewPanel extends JPanel
{
    private Border cellBorder;

    private String[] headers;
    private JComponent[][] data;

    private int maxWidth = 600;
    private Dimension cellDim;

    public TableViewPanel(String[] headers, JComponent[][] data)
    {
        this.headers = headers;
        this.data = data;

        cellBorder = BorderFactory.createLineBorder(Color.GRAY);

        cellDim = new Dimension(maxWidth / headers.length, 25);

        addData();
    }

    private JPanel getHeader()
    {
        JPanel headerPanel = new JPanel(new GridLayout(1, headers.length));
        
        for (String header : headers)
        {
            JLabel currentHeader = new JLabel(header);
            currentHeader.setPreferredSize(cellDim);
            currentHeader.setBorder(cellBorder);
            headerPanel.add(currentHeader);
        }

        return headerPanel;
    }

    private void addData()
    {
        JPanel dataPanel = new JPanel();
        dataPanel.setLayout(new BoxLayout(dataPanel, BoxLayout.Y_AXIS));

        dataPanel.add(getHeader());

        for (int row = 0; row < data.length; row++)
        {
            JPanel rowPanel = new JPanel(new GridLayout(1, headers.length));

            for (JComponent col : data[row])
            {
                col.setPreferredSize(cellDim);
                col.setBorder(cellBorder);
                rowPanel.add(col);
            }

            dataPanel.add(rowPanel);
        }

        add(dataPanel);
    }
}
