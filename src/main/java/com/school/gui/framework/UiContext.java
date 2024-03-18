package com.school.gui.framework;

import com.school.gui.view.AbstractView;
import com.school.gui.view.mainwindow.HomeView;
import com.school.gui.view.stateholder.SessionStateHolder;

import lombok.Getter;
import lombok.Setter;

import java.awt.Dimension;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.event.WindowEvent;
import java.lang.reflect.InvocationTargetException;

import javax.swing.JFrame;

@Getter
@Setter
public class UiContext
{
    private static UiContext ctx;

    public static UiContext get()
    {
        if (ctx == null)
        {
            ctx = new UiContext();
            return get();
        }

        return ctx;
    }

    public static UiContext getNew()
    {
        ctx = null;
        return get();
    }

    private UiContext() {}

    private JFrame frame;

    public void init()
    {
        frame = new JFrame();   
        frame.setSize(840, 527);
        frame.setResizable(false);
        frame.setPreferredSize(new Dimension(800, 527));
        frame.setLocationRelativeTo(null);

        addMenuBar(frame);

        update(HomeView.class);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    private void addMenuBar(JFrame frame)
    {
        MenuBar menubar = new MenuBar();
        Menu menu = new Menu();
        menu.setLabel("File");

        // kill session
        MenuItem logout = new MenuItem();
        logout.setLabel("Logout");
        logout.addActionListener(a -> { 
            SessionStateHolder.getNew();
            update(HomeView.class);
        });

        MenuItem quit = new MenuItem();
        quit.setLabel("Quit");
        quit.addActionListener(e -> frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING)));

        menu.add(logout);
        menu.add(quit);
        menubar.add(menu);
        
        frame.setMenuBar(menubar);
    }

    public void update(Class<? extends AbstractView> viewClazz)
    {
        frame.getContentPane().removeAll();

        try
        {
            AbstractView view = viewClazz.getDeclaredConstructor().newInstance();
            frame.add(view.getView());
        }
        catch (InvocationTargetException | IllegalAccessException |
                InstantiationException | NoSuchMethodException ie)
        {
            ie.printStackTrace();
        }

        frame.setVisible(true);
        frame.repaint();
    }
}
