import javax.swing.table.AbstractTableModel;
import com.intellij.psi.xml.XmlTag;
import java.util.List;

public class XMLTableModel extends AbstractTableModel {
    private static final String[] COLUMN_NAMES = {"Name", "Value"};
    private List<XmlTag> xmlTags;

    public XMLTableModel(List<XmlTag> xmlTags) {
        this.xmlTags = xmlTags;
    }

    @Override
    public int getRowCount() {
        return xmlTags.size();
    }

    @Override
    public int getColumnCount() {
        return COLUMN_NAMES.length;
    }

    @Override
    public String getColumnName(int colIdx) {
        return COLUMN_NAMES[colIdx];
    }

    @Override
    public Object getValueAt(int rowIdx, int colIdx) {
        XmlTag tag = xmlTags.get(rowIdx);
        if (colIdx == 0) {
            return tag.getName();
        }

        return tag.getValue();
    }
}