import com.intellij.psi.xml.XmlTag;
import com.intellij.ui.table.JBTable;
import com.intellij.openapi.editor.Document;

import java.util.List;

import javax.swing.*;
import java.awt.*;

public class XMLElementPanel extends JPanel {
    private JBTable table;

    /**
     * Creates a JTable to display info about a file's XML tags
     *
     * @param xmlTags tags to display in a ToolWindow
     * @param document document object used to fetch line number
     */
    public XMLElementPanel(List<XmlTag> xmlTags, Document document) {
        super(new BorderLayout());

        XMLTableModel model = new XMLTableModel(xmlTags, document);
        JTable table = new JBTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);
    }
}