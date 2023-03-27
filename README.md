# XML locator plugin

## Building the project
Clone the repo and either Build the project and use the "Run Plugin configuration" or use the terminal command  `./gradlew runIde`.
I used the following XML file for testing:
```
<note>
    <to>Tove</to>
    <body>Hello</body>
    <from>Jani</from>



    <body>Don't forget me this weekend!</body>
    <body>Don't forget me this weekend!</body>
</note>
```
<!-- Plugin description -->
This is a plugin to locate XML tags inside an XML file and display their line number in the file, the tag type, and the content between the tags. 

After your run the "Run Plugin Configuration", in the "Tools" tab of the IDE you will find an option to "Locate XML Elements" with a magnifying glass icon next to it. If your caret is currently inside a file editor for an XML file, clicking this button will display salient info about the XML elements in the file inside a ToolWindow in the IDE. Reclicking the button will update the data in the ToolWindow. Being outside of an XML file's editing space or working on a non-XML file while invoking the plugin will make it display a warning message.
<!-- Plugin description end -->

## Development

- `LocateXMLElementsAction.java`: main class in the plugin, defines an action to fetch info about XML tags of a file when the plugin is invoked via the "Tools" tab 
- `XMLElementPanel.java`: defines a JBTable to display the fetched info about XML tags in the current XML file
- `XMLTableModel.java`: extends an AbstractTableModel to fit it to the needs of displaying info about XML tags as rows inside a ToolWindow

## What's next

The plugin takes a few more seconds to start than the other functionalities in the "Tools" tab, so some code refactoring is desired to optimize performance. Further, after tinkering with the .update() method of the main action (to fetch and display info), I have decided to forgo implemeting live updates to the table so as not to slow down the IDE's other functionalities, as my attempts to filter through events to only update at the right time worked well but came at the expense of many calls to the XML tag info fetching mechanism and it is not great for a plugin of this kinda to hog IDE's computational resources like this. Further, the plugin would benefit from unit testing.