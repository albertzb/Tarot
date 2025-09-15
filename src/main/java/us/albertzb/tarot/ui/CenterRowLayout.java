/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package us.albertzb.tarot.ui;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager;

/**
 *
 * @author albertzb [albertzb42@gmail.com]
 */
public class CenterRowLayout implements LayoutManager {
    
    private final int gap;
    public CenterRowLayout(int gap) {
        this.gap = gap;
    }

    @Override
    public void addLayoutComponent(String name, Component comp) {
        //Do nothing
    }

    @Override
    public void removeLayoutComponent(Component comp) {
        //Do nothing
    }

    @Override
    public Dimension preferredLayoutSize(Container parent) {
        return calculateSize(parent);
    }

    @Override
    public Dimension minimumLayoutSize(Container parent) {
        return calculateSize(parent);
    }

    private Dimension calculateSize(Container parent) {
        int width = 0;
        int height = 0;
        Component[] components = parent.getComponents();
        if (components.length > 0) {
            for (Component comp : components) {
                Dimension d = comp.getPreferredSize();
                width += d.width;
                height = Math.max(height, d.height);
            }
            width += (components.length - 1) * gap;
        }
        return new Dimension(width, height);
    }
    
    @Override
    public void layoutContainer(Container parent) {
        Dimension containerSize = parent.getSize();
        Component[] components = parent.getComponents();
        if (components.length == 0) {
            return;
        }

        int totalWidth = 0;
        for (Component comp : components) {
            totalWidth += comp.getPreferredSize().width;
        }
        totalWidth += (components.length - 1) * gap;

        int x = (containerSize.width - totalWidth) / 2;
        int y = (containerSize.height - components[0].getPreferredSize().height) / 2;

        for (Component comp : components) {
            Dimension d = comp.getPreferredSize();
            comp.setBounds(x, y, d.width, d.height);
            x += d.width + gap;
        }
    }
    
}
