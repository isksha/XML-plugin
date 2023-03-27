import javax.swing.table.AbstractTableModel;
import com.intellij.openapi.editor.Document;
import com.intellij.psi.xml.XmlTag;

import java.util.Arrays;
import java.util.List;

public class XMLTableModel extends AbstractTableModel {
    private static final String[] COLUMN_NAMES = {"Line number", "Tag", "Text"};
    private List<XmlTag> xmlTags;
    private Document document;

    /**
     * Initializes the XMLTable model's salient parameters.
     *
     * @param xmlTags tags to display in a ToolWindow
     * @param document document object used to fetch line number
     */
    public XMLTableModel(List<XmlTag> xmlTags, Document document) {
        this.xmlTags = xmlTags;
        this.document = document;
    }

    /**
     * @return the number of XML elements in a file
     */
    @Override
    public int getRowCount() {
        return xmlTags.size();
    }

    /**
     * @return the number of spectated XML tag attributes
     */
    @Override
    public int getColumnCount() {
        return COLUMN_NAMES.length;
    }

    /**
     *
     * @param colIdx  the column being queried
     * @return column name with the specified index
     */
    @Override
    public String getColumnName(int colIdx) {
        return COLUMN_NAMES[colIdx];
    }

    /**
     *
     * @param rowIdx the row whose value is to be queried
     * @param colIdx the column whose value is to be queried
     * @return value relating to an XML value specified by input row and col
     */
    @Override
    public Object getValueAt(int rowIdx, int colIdx) {
        XmlTag tag = xmlTags.get(rowIdx);
        if (colIdx == 0) {
            int offset = tag.getTextOffset();
            return document.getLineNumber(offset) + 1;
        } else if (colIdx == 1) {
            return tag.getName();
        } else if (colIdx == 2) {
            return tag.getValue().getTrimmedText();
        }

        return null;
    }
}