import com.intellij.icons.AllIcons;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowAnchor;
import com.intellij.openapi.wm.ToolWindowManager;
import com.intellij.psi.PsiFile;
import com.intellij.psi.xml.XmlAttribute;
import com.intellij.psi.xml.XmlFile;
import com.intellij.psi.xml.XmlTag;
import com.intellij.ui.content.ContentFactory;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class LocateXMLElementsAction extends AnAction {
    @Override
    public void actionPerformed(AnActionEvent e) {
        // get the selected file and project
        Project project = e.getProject();
        PsiFile psiFile = e.getDataContext().getData(PlatformDataKeys.PSI_FILE);

        // Check that the file is an XML file, if not don't do anything
        if (!(psiFile instanceof XmlFile)) {
            Messages.showWarningDialog("This is not an XML file!", "Invalid File");
            return;
        }

        // else get all xml tags, store them in a collection
        XmlFile xmlFile = (XmlFile) psiFile;
        XmlTag root = xmlFile.getRootTag();
        List<XmlTag> xmlElts = new ArrayList<XmlTag>();
        populateWithXmls(xmlElts, root);

        // display all tags and the info about them in a table
        ToolWindowManager toolWindowManager = ToolWindowManager.getInstance(project);
        // check if the tool window has already been registered
//        ToolWindow existingToolWindow = toolWindowManager.getToolWindow(TOOL_WINDOW_ID);
//        if (existingToolWindow != null) {
//            existingToolWindow.show();
//            return;
//        }

        ToolWindow toolWindow = toolWindowManager.registerToolWindow("XML Elements", true, ToolWindowAnchor.BOTTOM);
        toolWindow.setIcon(AllIcons.Actions.Find);
        XMLElementPanel panel = new XMLElementPanel(xmlElts);
        toolWindow.getContentManager().addContent(ContentFactory.SERVICE.
                getInstance().
                createContent(panel, "", false));
        toolWindow.show(null);
    }

    public void populateWithXmls(List<XmlTag> xmlElts, XmlTag root) {
        xmlElts.add(root);
        XmlTag[] kids = root.getSubTags();
        if (kids.length != 0) {
            for (XmlTag kid : kids) {
                populateWithXmls(xmlElts, kid);
            }
        }
    }
}
