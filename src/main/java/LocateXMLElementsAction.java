import com.intellij.icons.AllIcons;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowAnchor;
import com.intellij.openapi.wm.ToolWindowManager;
import com.intellij.psi.PsiFile;
import com.intellij.psi.xml.XmlFile;
import com.intellij.psi.xml.XmlTag;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;

import java.util.ArrayList;
import java.util.List;

public class LocateXMLElementsAction extends AnAction {

    /**
     * Upon the user's invocation of the plugin via the "Tools" tab,
     * this action checks whether the current file is an XML file
     * (and alerts if not), fetches info about the tags of this file
     * and displays it to the user using a ToolWindow inside the IDE
     *
     * @param e Carries information on the invocation place
     */
    @Override
    public void actionPerformed(AnActionEvent e) {
        // get the selected file and project
        Project project = e.getProject();
        PsiFile psiFile = e.getDataContext().getData(PlatformDataKeys.PSI_FILE);

        // check that the file is an XML file, if not then warn
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
        ToolWindow toolWindow = toolWindowManager.getToolWindow("XMLElements");
        if (toolWindow == null) {
            toolWindow = toolWindowManager.registerToolWindow("XMLElements", true,
                    ToolWindowAnchor.BOTTOM);
            toolWindow.setIcon(AllIcons.Actions.Find);
        } else {
            try {
                Content lastActionContent = toolWindow.getContentManager().findContent("XML tags");
                toolWindow.getContentManager().removeContent(lastActionContent, true);
            } catch (IllegalArgumentException iae) {}
        }

        // display the panel with the data
        Editor editor = FileEditorManager.getInstance(project).getSelectedTextEditor();
        Document document = editor.getDocument();
        XMLElementPanel panel = new XMLElementPanel(xmlElts, document);
        Content tagsToPut = ContentFactory.SERVICE.
                getInstance().
                createContent(panel, "XML tags", false);
        toolWindow.getContentManager().addContent(tagsToPut);

        toolWindow.show(null);
    }

    /**
     * Recursively populates the input collection xmlElts with all XML tags
     * from the current file
     *
     * @param xmlElts collection storing XML tags from current file
     * @param root current XML tag to be put into the collection of tags
     */
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
