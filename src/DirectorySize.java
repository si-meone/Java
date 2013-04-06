import java.io.*;
import java.awt.*;
import java.awt.event.*;
import java.io.FileFilter;
import java.text.DecimalFormat;
import java.util.*;
import javax.swing.*;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.*;

/*
 * DirectorySize.java
 */
public class DirectorySize extends JPanel
                             implements ActionListener {
    static private final String newline = "\n";
    JButton openButton, saveButton;
    JTextArea log;
    JFileChooser fc;
 
    public DirectorySize() {
        super(new BorderLayout());
 
        //Create the log first, because the action listeners
        //need to refer to it.
        log = new JTextArea(50,100);
        log.setMargin(new Insets(5,5,5,5));
        log.setEditable(false);
        JScrollPane logScrollPane = new JScrollPane(log);
 
        //Create a file chooser
        fc = new JFileChooser();
 
        //Uncomment one of the following lines to try a different
        //file selection mode.  The first allows just directories
        //to be selected (and, at least in the Java look and feel,
        //shown).  The second allows both files and directories
        //to be selected.  If you leave these lines commented out,
        //then the default mode (FILES_ONLY) will be used.
        //
        fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        //fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
 
        //Create the open button.  We use the image from the JLF
        //Graphics Repository (but we extracted it from the jar).
        openButton = new JButton("Open Folder...");
        openButton.addActionListener(this);
 
        //Create the save button.  We use the image from the JLF
        //Graphics Repository (but we extracted it from the jar).
        saveButton = new JButton("Clear Log...");
        saveButton.addActionListener(this);
 
        //For layout purposes, put the buttons in a separate panel
        JPanel buttonPanel = new JPanel(); //use FlowLayout
        buttonPanel.add(openButton);
        buttonPanel.add(saveButton);
 
        //Add the buttons and the log to this panel.
        add(buttonPanel, BorderLayout.PAGE_START);
        add(logScrollPane, BorderLayout.CENTER);
    }
 
    public void actionPerformed(ActionEvent e) {
 
        //Handle open button action.
        if (e.getSource() == openButton) {
            int returnVal = fc.showOpenDialog(DirectorySize.this);
 
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = fc.getSelectedFile();
                //This is where a real application would open the file.
                ListSubDirectorySizes(file);
            } else {
                log.append("Open command cancelled by user." + newline);
            }
            log.setCaretPosition(log.getDocument().getLength());
 
        //Handle save button action.
        } else if (e.getSource() == saveButton) {
                log.setText("");  //reset
        }
    }

    private void ListSubDirectorySizes(File file) {
        File[] files;
        files = file.listFiles(new FileFilter() {
            @Override
            public boolean accept(File file) {
//                if (!file.isDirectory()){
//                    return false;  //To change body of implemented methods use File | Settings | File Templates.
//                }else{
//                    return true;
//                }
                return true;
            }
        });
        Map<String,Long> dirListing = new HashMap<String, Long>();
        for (File dir : files){
            DiskUsage diskUsage = new DiskUsage();
            diskUsage.accept(dir);
//            long size = diskUsage.getSize() / (1024 * 1024);
            long size = diskUsage.getSize();
            dirListing.put(dir.getName(), size);
        }

        ValueComparator bvc =  new ValueComparator(dirListing);
        TreeMap<String,Long> sorted_map = new TreeMap<String,Long>(bvc);
        sorted_map.putAll(dirListing);

        PrettyPrint(file, sorted_map);

    }

    private void PrettyPrint(File file, TreeMap<String, Long> sortedMap) {

//        Long size = 0L;
//        ArrayList<Long> values = new ArrayList<Long>(sortedMap.values());

        Long total = 0L;
        for (Long value : sortedMap.values()) {
            total = total + value; // Can also be done by total += value;
        }

        log.append(file.getName() + ": " + readableFileSize(total) + "\n\n");
        for (Map.Entry<String, Long> entry : sortedMap.entrySet()) {
            log.append("[ " + readableFileSize(entry.getValue()) + " ]" );
            log.append(" --> " + entry.getKey() +  "\n");
        }
//        log.append(sortedMap + "\n");
    }

    public static String readableFileSize(long size) {
        if(size <= 0) return "0";
        final String[] units = new String[] { "B", "KB", "MB", "GB", "TB" };
        int digitGroups = (int) (Math.log10(size)/Math.log10(1024));
        return new DecimalFormat("#,##0.#").format(size/Math.pow(1024, digitGroups)) + " " + units[digitGroups];
    }

    /** Returns an ImageIcon, or null if the path was invalid. */
    protected static ImageIcon createImageIcon(String path) {
        java.net.URL imgURL = DirectorySize.class.getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }
 
    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event dispatch thread.
     */
    private static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("FileChooserDemo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 
        //Add content to the window.
        frame.add(new DirectorySize());
 
        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }
 
    public static void main(String[] args) {
        //Schedule a job for the event dispatch thread:
        //creating and showing this application's GUI.
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                //Turn off metal's use of bold fonts
                UIManager.put("swing.boldMetal", Boolean.FALSE); 
                createAndShowGUI();
            }
        });
    }
}

class DiskUsage implements FileFilter
{
    public DiskUsage(){};
    private long size = 0;
    public boolean accept(File file)
    {
        if ( file.isFile())
            size += file.length();
        else
            file.listFiles(this);
        return false;
    }
    public long getSize()
    {
        return size;
    }
}

class ValueComparator implements Comparator<String> {

    Map<String, Long> base;

    public ValueComparator(Map<String, Long> base) {
        this.base = base;
    }

    // Note: this comparator imposes orderings that are inconsistent with equals.
    public int compare(String a, String b) {
        if (base.get(a) >= base.get(b)) {
            return -1;
        } else {
            return 1;
        } // returning 0 would merge keys
    }
}