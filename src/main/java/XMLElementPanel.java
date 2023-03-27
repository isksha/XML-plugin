import com.intellij.psi.xml.XmlTag;
import com.intellij.ui.table.JBTable;

import java.util.List;

import javax.swing.*;
import java.awt.*;

public class XMLElementPanel extends JPanel {
    private JBTable table;

    public XMLElementPanel(List<XmlTag> xmlTags) {
        super(new BorderLayout());

        XMLTableModel model = new XMLTableModel(xmlTags);
        JTable table = new JBTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);
    }
}