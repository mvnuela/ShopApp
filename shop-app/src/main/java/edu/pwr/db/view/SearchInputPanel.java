package edu.pwr.db.view;

import edu.pwr.db.model.Item;
import edu.pwr.db.model.SmallItemJdbcTemplate;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class SearchInputPanel extends JPanel {
    protected final JComboBox<Item> brands, colors, coverageLevels, types;
    protected final JButton search;
    protected final AppWindow appWindow;

    public SearchInputPanel(AppWindow appWindow) {
        this.appWindow = appWindow;
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();
        gc.fill = GridBagConstraints.HORIZONTAL;
        gc.anchor = GridBagConstraints.NORTHWEST;
        gc.gridx = 0;
        gc.gridy = 0;
        gc.weighty = 2;

        JLabel jlBrand = new JLabel("brand: ");
        gc.weightx = 1;
        gc.gridwidth = 1;
        panel.add(jlBrand, gc);

        brands = new JComboBox<>();
        brands.addItem(Item.ANY);
        gc.gridx++;
        gc.weightx = 5;
        panel.add(brands, gc);

        JLabel jlColor = new JLabel("color: ");
        gc.gridx = 0;
        gc.weightx = 1;
        gc.gridy++;
        panel.add(jlColor, gc);

        colors = new JComboBox<>();
        colors.addItem(Item.ANY);
        gc.weightx = 5;
        gc.gridx++;
        panel.add(colors, gc);

        JLabel jlLevel = new JLabel("coverage level: ");
        gc.gridx = 0;
        gc.gridy = 2;
        gc.weightx = 1;
        panel.add(jlLevel, gc);

        coverageLevels = new JComboBox<>();
        coverageLevels.addItem(Item.ANY);
        gc.gridx++;
        panel.add(coverageLevels, gc);

        JLabel jlTypes = new JLabel("type: ");
        gc.gridx = 0;
        gc.gridy = 3;
        panel.add(jlTypes, gc);

        types = new JComboBox<>();
        types.addItem(Item.ANY);
        gc.gridx++;
        panel.add(types, gc);

        search = new JButton("search");
        gc.gridx = 0;
        gc.gridy = 4;
        gc.gridwidth = 2;
        gc.weighty = 0;
        gc.fill = GridBagConstraints.NONE;
        gc.anchor = GridBagConstraints.NORTH;
        panel.add(search, gc);

        setLayout(new BorderLayout());
        add(panel, BorderLayout.CENTER);
        //panel.setBackground(Color.BLACK);
        setBorder(new EmptyBorder(40, 40, 50, 50)); // padding
    }

    public void refreshContents() {
        try {
            SmallItemJdbcTemplate template = appWindow.getDbConnection()
                    .getSmallItemTemplate("color");
            List<Item> list = template.list();
            colors.removeAllItems();
            colors.addItem(Item.ANY);
            for (Item item : list) {
                colors.addItem(item);
            }
        }
        catch (SQLException ex) {
            // TODO: show error message
        }
    }
}
