/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simplefileviewerandeditor;

import CustomDialogs.FindReplaceDialog;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.JTextPane;
import javax.swing.JToolBar;
import javax.swing.SpinnerNumberModel;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;

/**
 *
 * @author MWIGO JON MARK
 */
public class SimpleFileViewerAndEditor extends JFrame
{
    // <editor-fold defaultstate="collapsed" desc="Component Orientation Here.">
    public static String[] lafList =new String[]{"Windows", "WindowsClassic", "Nimbus", "Metal"};
    public static JTextPane viewer = new JTextPane();
    public static JScrollPane viewerScroll = new JScrollPane(viewer);
    public static JComboBox<String> cb1 = new JComboBox<>(lafList);
    JFileChooser fileOpen = new JFileChooser();
    JPanel menuPanel = new JPanel();
    JPanel toolBarPanel = new JPanel();
    JPanel statusPanel = this.getStatusPanel();
    //Creating a menu bar
    JMenuBar menuBar = this.getMenubar();
    //Creating a Content Pane container
    Container mainPane = this.getContentPane();
    //Creating a Vertical Box
    Box vBox = Box.createVerticalBox();
    //Creating a tool bar
    JToolBar toolBar = this.getToolbar();
    // </editor-fold>
        
    public SimpleFileViewerAndEditor()
    {
        initComponets();
    }
    /**
     * 
     * @param none
     * @return void
     * Function initializes GUI Components to the frame.
     */
    private void initComponets()
    {
        // <editor-fold defaultstate="collapsed" desc="Function Body Here.">
        JSlider rightIndents = new JSlider(1, 100, 5);
        JSlider leftIndents = new JSlider(1, 100, 10);
        JSlider upIndents = new JSlider(JSlider.VERTICAL, 1, 50, 15);
        rightIndents.setPaintTicks(true);
        leftIndents.setPaintTicks(true);
        upIndents.setPaintTicks(true);
        
        this.setSize(500, 400);
        this.setLocationRelativeTo(null);
        
        //Set viewer to not-editable.
        viewer.setEditable(false);
        viewer.putClientProperty("html.disabled", Boolean.TRUE);
        
        // <editor-fold defaultstate="collapsed" desc="GUI Setup Code Here.">
        //Adding tool bar to the toolbarPanel.
        toolBarPanel.add(toolBar);
        //Add components to the Vertical box.
        vBox.add(menuPanel);
        vBox.add(toolBarPanel);
        vBox.add(rightIndents);
        vBox.add(leftIndents);
        //Set layout for menuPaneland toolBar panel.
        menuPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        toolBarPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        //Add coponents to the content pane, which initially has a border layout.
        mainPane.add(vBox, BorderLayout.NORTH);
        mainPane.add(statusPanel, BorderLayout.SOUTH);
        mainPane.add(viewerScroll);
        mainPane.add(upIndents, BorderLayout.WEST);
        // </editor-fold>
        
        viewer.setDragEnabled(true);
        StyleContext styleContext = StyleContext.getDefaultStyleContext();
        Style defaultStyleContext = styleContext.getStyle(StyleContext.DEFAULT_STYLE);
        
        StyledDocument document = viewer.getStyledDocument();
        Style normalStyle = document.addStyle("normal", defaultStyleContext);
        
        leftIndents.addChangeListener(e->{
            Style leftIndent = document.addStyle("leftindent", normalStyle);
            StyleConstants.setLeftIndent(leftIndent, rightIndents.getValue());
            setNewStyle("leftindent", false);
        });
        rightIndents.addChangeListener(e->{
            Style rightIndent = document.addStyle("rightindent", normalStyle);
            StyleConstants.setRightIndent(rightIndent, leftIndents.getValue());
            setNewStyle("rightindent", false);
        });
        upIndents.addChangeListener(e->{
            Style upIndent = document.addStyle("upindent", normalStyle);
            StyleConstants.setSpaceAbove(upIndent, upIndents.getValue());
            setNewStyle("upindent", false);
        });
        
        this.repaint(100);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //Call to the styling function.
        this.addStyles();
        // </editor-fold>
    }
    /**
     * 
     * @return a menu bar with components added to it.
     * Function to create a menu bar.
     */
    private JMenuBar getMenubar()
    {
        // <editor-fold defaultstate="collapsed" desc="Component Class Instantion">
        Insets geneInsets = new Insets(0, 0, 0, 0);
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
            JMenuItem newFile = new JMenuItem("New");
            JMenuItem open = new JMenuItem("Open");
            JMenuItem save = new JMenuItem("Save");
            JMenuItem saveAs = new JMenuItem("Save As");
            JMenuItem exit = new JMenuItem("Exit");
        JMenu editMenu = new JMenu("Edit");
            JMenuItem reDo = new JMenuItem("Redo");
            JMenuItem unDo = new JMenuItem("Undo");
            JMenuItem cut = new JMenuItem("Cut");
            JMenuItem copy = new JMenuItem("Copy");
            JMenuItem paste = new JMenuItem("Paste");
            JMenuItem del = new JMenuItem("Delete");
            JMenuItem replace = new JMenuItem("Find & Replace");
        JMenu viewMenu = new JMenu("View");
            JMenuItem editor = new JMenuItem("Editor");
            JMenuItem max = new JMenuItem("Maximise");
            JMenuItem min = new JMenuItem("Restore");
        JMenu navigateMenu = new JMenu("Navigate");
            JMenuItem top = new JMenuItem("Page Top");
            JMenuItem bottom = new JMenuItem("Page Bottom");
            JMenuItem cPos = new JMenuItem("Cursor Position");
        JMenu settingsMenu = new JMenu("Settings");
            JMenu laf = new JMenu("Look & Feel");
                JMenuItem LAF = new JMenu("App L & F");
                JMenuItem theme = new JMenuItem("Theme Color");
                JMenuItem editorclr = new JMenuItem("Editor Color");
            JMenu txtColor = new JMenu("Text Color");
                JMenuItem white = new JMenuItem("White");
                JMenuItem black = new JMenuItem("Black");
        JMenu helpMenu = new JMenu("Help");
        
        JPopupMenu popupMenu = new JPopupMenu();
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Popup Menu.">
        popupMenu.add(newFile);
        popupMenu.add(open);
        popupMenu.add(save);
        popupMenu.add(save);
        popupMenu.add(saveAs);
        popupMenu.addSeparator();
        popupMenu.add(exit);
        
        MouseListener ml = new MouseAdapter()
        {
            @Override
            public void mousePressed(MouseEvent e)
            {
                if(e.isPopupTrigger())
                {
                    popupMenu.show(e.getComponent(), e.getX(), e.getY());
                }
            }
            
            @Override
            public void mouseReleased(MouseEvent e)
            {
                if(e.isPopupTrigger())
                {
                    popupMenu.show(e.getComponent(), e.getX(), e.getY());
                }
            }
        };
        this.addMouseListener(ml);
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="GUI Setup.">
        menuBar.setMargin(geneInsets);
        //File menu.
        fileMenu.add(newFile);
        fileMenu.add(open);
        fileMenu.add(save);
        fileMenu.add(saveAs);
        fileMenu.addSeparator();
        fileMenu.add(exit);
        //Edit menu.
        editMenu.add(reDo);
        editMenu.add(unDo);
        editMenu.addSeparator();
        editMenu.add(cut);
        editMenu.add(copy);
        editMenu.add(paste);
        editMenu.add(del);
        editMenu.addSeparator();
        editMenu.add(replace);
        //View menu.
        viewMenu.add(editor);
        viewMenu.addSeparator();
        viewMenu.add(min);
        viewMenu.add(max);
        //Navigate menu
        navigateMenu.add(top);
        navigateMenu.add(bottom);
        navigateMenu.add(cPos);
        //laf Menu.
        laf.add(theme);
        laf.add(editorclr);
        //Text mune item.
        txtColor.add(white);
        txtColor.add(black);
        //LAF menu item.
        LAF.add(cb1);
        //Settings menu
        settingsMenu.add(LAF);
        settingsMenu.add(laf);
        settingsMenu.add(txtColor);
        
        menuBar.add(fileMenu);
        menuBar.add(editMenu);
        menuBar.add(viewMenu);
        menuBar.add(navigateMenu);
        menuBar.add(settingsMenu);
        menuBar.add(helpMenu);
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Files Management Logic Here.">
        newFile.addActionListener(e->{
            //Set viewer to editable.
            viewer.setEditable(true);
            viewer.setText("");
            this.setTitle("New File - Editor & Viewer");
        });
        
        open.addActionListener(e->{
            int select = fileOpen.showOpenDialog(this);
            fileOpen.setDialogTitle("Select A File");
            
            File selectedFile = fileOpen.getSelectedFile();
            if(select == JFileChooser.APPROVE_OPTION)
            {
                try
                {
                    viewer.setPage(selectedFile.toURL());
                }
                catch (MalformedURLException ex)
                {
                    java.util.logging.Logger.getLogger(SimpleFileViewerAndEditor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                }
                catch (IOException ex)
                {
                    java.util.logging.Logger.getLogger(SimpleFileViewerAndEditor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                }
            }
           
            this.setTitle("Editor & Viewer \t(" + selectedFile.getName() + ")");
            
        });
        
        save.addActionListener(e->{
            //Set viewer to not-editable.
            viewer.setEditable(false);
            
            int saveFile = fileOpen.showSaveDialog(this);
            fileOpen.setDialogTitle("Save As File");
            
            File filePath = new File(fileOpen.getSelectedFile().getAbsolutePath());
            if(!filePath.exists())
            {
                if(saveFile==JFileChooser.APPROVE_OPTION)
                {
                    try(final OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(filePath), StandardCharsets.UTF_8))
                    {
                        filePath.createNewFile();
                        writer.write(viewer.getText());
                        this.setTitle("Editor & Viewer \t(" + filePath.getName() + ")  Saved");
                    }
                    catch (IOException ex)
                    {
                        Logger.getLogger(SimpleFileViewerAndEditor.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
            else
            {
                try(final OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(filePath), StandardCharsets.UTF_8))
                    {
                        writer.write(viewer.getText());
                        this.setTitle("Editor & Viewer \t(" + filePath.getName() + ")  Saved");
                    }
                    catch (IOException ex)
                    {
                        Logger.getLogger(SimpleFileViewerAndEditor.class.getName()).log(Level.SEVERE, null, ex);
                    }
            }
        });
        
        saveAs.addActionListener(e->{
            //Set viewer to not-editable.
            viewer.setEditable(false);
            
            int saveFile = fileOpen.showSaveDialog(this);
            fileOpen.setDialogTitle("Save File Before Exit");
            
            File filePath = new File(fileOpen.getSelectedFile().getAbsolutePath());
            if(saveFile==JFileChooser.APPROVE_OPTION)
            {
                try(final OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(filePath), StandardCharsets.UTF_8))
                {
                    filePath.createNewFile();
                    writer.write(viewer.getText());
                    this.setTitle("Editor & Viewer \t(" + filePath.getName() + ")  Saved");
                }
                catch (IOException ex)
                {
                    Logger.getLogger(SimpleFileViewerAndEditor.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        
        exit.addActionListener(e->{
            if(this.getTitle() == "New File - Editor & Viewer")
            {
                JOptionPane.showMessageDialog(this, "Save this file?");
                int saveFile = fileOpen.showSaveDialog(this);
                fileOpen.setDialogTitle("Save File");
            
                File filePath = new File(fileOpen.getSelectedFile().getAbsolutePath());
                if(saveFile==JFileChooser.APPROVE_OPTION)
                {
                    try(final OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(filePath), StandardCharsets.UTF_8))
                    {
                        filePath.createNewFile();
                        writer.write(viewer.getText());
                        this.setTitle("Editor & Viewer \t(" + filePath.getName() + ")  Saved");
                    }
                    catch (IOException ex)
                    {
                        Logger.getLogger(SimpleFileViewerAndEditor.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
            System.exit(0);
        });
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Editing Logic Here.">
        reDo.addActionListener(e->{});
        
        unDo.addActionListener(e->{});
        
        cut.addActionListener(e->{viewer.cut();});
        
        copy.addActionListener(e->{viewer.copy();});
        
        paste.addActionListener(e->{viewer.paste();});
        
        del.addActionListener(e->{
            try
            {
                String highLight = viewer.getText(viewer.getSelectionStart(), viewer.getSelectionEnd()-viewer.getSelectionStart());
                highLight.replaceAll(highLight, "");
            }
            catch (BadLocationException ex)
            {
                Logger.getLogger(SimpleFileViewerAndEditor.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        replace.addActionListener(e->{
            FindReplaceDialog frd = new FindReplaceDialog();
            
        });
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Settings Logic Here.">
        theme.addActionListener(e->{
            Color color = JColorChooser.showDialog(this, "Editor Color", null, true);
            
            if(color!=null)
            {
                menuPanel.setBackground(color);
                menuBar.setBackground(color);
                toolBarPanel.setBackground(color);
                toolBar.setBackground(color);
                statusPanel.setBackground(color);
                viewer.setBackground(color);
                viewerScroll.getVerticalScrollBar().setBackground(color);
                viewerScroll.getHorizontalScrollBar().setBackground(color);
                
            }
            this.validate();
        });
        
        editorclr.addActionListener(e->{
            Color color = JColorChooser.showDialog(this, "Editor Color", null, true);
            
            if(color!=null)
            {
                viewer.setBackground(color);
            }
        });
        
        white.addActionListener(e->{
            viewer.setForeground(Color.white);
            viewer.setCaretColor(Color.white);
        });
        
        black.addActionListener(e->{
            viewer.setForeground(Color.black);
            viewer.setCaretColor(Color.black);
        });
        
        cb1.addActionListener(e->{cb1.getSelectedItem().toString();});
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="View Logic Here.">
        editor.addActionListener(e->{});
        max.addActionListener(e->{});
        min.addActionListener(e->{this.setVisible(false);});
        //</editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Navigate Logic Here.">
        top.addActionListener(e->{viewerScroll.getVerticalScrollBar().setValue(0);});
        bottom.addActionListener(e->{viewerScroll.getVerticalScrollBar().setValue(100000);});
        cPos.addActionListener(e->{viewerScroll.getVerticalScrollBar().setValue(viewer.getCaretPosition());});
        // </editor-fold>
        
        return menuBar;
    }
    /**
     * 
     * @return a tool bar with components already added to it.
     * Function to create the tool bar.
     */
    private JToolBar getToolbar()
    {
        // <editor-fold defaultstate="collapsed" desc="Component Class Instantion">
        JToolBar toolBar = new JToolBar();
        JButton btnBold = new JButton("<html><body><b>B</b></body></html>");
        JButton btnItalisize = new JButton("<html><body><i>i</i></body></html>");
        JButton btnUnderline = new JButton("<html><body><u>u</u></body></html>");
        JButton btnSup = new JButton("<html><body>H<sup>2</sup>O</body></html>");
        JButton btnSub = new JButton("<html><body>H<Sub>2</sub>O</body></html>");
        JButton btnStrikeThrough = new JButton("<html><body><strike>Strike</strike></body></html>");
        JButton btnIndent = new JButton("Indent");
        JButton btnBlock = new JButton("Block");
        JButton btnJustify = new JButton("Justify");
        JButton btnColor = new JButton("Color");
        JButton btnLeftAlign = new JButton("Align Left");
        JButton btnCenterAlign = new JButton("Align Center");
        JButton btnRightAlign = new JButton("Align Right");
        JButton btnNormal = new JButton("Norm");
        
        int minNo = 8;
        int maxNo = 72;
        int currentNo = 14;
        int steps = 2;
        SpinnerNumberModel model = new SpinnerNumberModel(currentNo,minNo, maxNo, steps);
        JSpinner sizeSpinner = new JSpinner(model);
        
        String[] list = new String[]{"Align Left", 
            "Align Center", "Align Right"};
        
        JComboBox<String> alignCombo = new JComboBox<>(list);
        
        String[] fontsList = new String[]{"Mono-Spaced", "Serif", "Sans-Serif", "Dialog", "Dialog-Input"};
        
        JComboBox<String> fontsCombo = new JComboBox<>(fontsList);
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="GUI Setup.">
        Insets geneInsets = new Insets(0, 0, 0, 0);
        menuPanel.add(menuBar);
        //Set tool tips for the user.
        //Accessibility.
        btnBold.setToolTipText("Bold");
        fontsCombo.setToolTipText("Font Style");
        sizeSpinner.setToolTipText("Text Size");
        btnColor.setToolTipText("Text Color");
        btnSup.setToolTipText("Text Superscript");
        btnSub.setToolTipText("Text Subscript");
        btnIndent.setToolTipText("Indent Paragraphing");
        btnBlock.setToolTipText("Block Paragraphing");
        btnUnderline.setToolTipText("Text Underline");
        btnItalisize.setToolTipText("Text Italisize");
        btnStrikeThrough.setToolTipText("Text Strike Through");
        btnNormal.setToolTipText("Normal Text format");
        //Add components to the tool bar.
        toolBar.setMargin(geneInsets);
        toolBar.add(btnNormal);
        toolBar.addSeparator();
        toolBar.add(btnBold);
        toolBar.addSeparator();
        toolBar.add(btnItalisize);
        toolBar.addSeparator();
        toolBar.add(btnUnderline);
        toolBar.addSeparator();
        toolBar.add(btnSup);
        toolBar.addSeparator();
        toolBar.add(btnSub);
        toolBar.addSeparator();
        toolBar.add(btnStrikeThrough);
        toolBar.addSeparator();
        toolBar.add(btnJustify);
        toolBar.addSeparator();
        toolBar.add(btnIndent);
        toolBar.addSeparator();
        toolBar.add(btnBlock);
        toolBar.addSeparator();
        toolBar.add(alignCombo);
        toolBar.addSeparator();
        toolBar.add(fontsCombo);
        toolBar.addSeparator();
        toolBar.add(sizeSpinner);
        toolBar.addSeparator();
        toolBar.add(btnColor);
        
        btnBold.setBackground(this.getBackground());
        fontsCombo.setBackground(this.getBackground());
        sizeSpinner.setBackground(this.getBackground());
        btnColor.setBackground(this.getBackground());
        btnItalisize.setBackground(this.getBackground());
        btnUnderline.setBackground(this.getBackground());
        btnSup.setBackground(this.getBackground());
        btnSub.setBackground(this.getBackground());
        btnJustify.setBackground(this.getBackground());
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Document Styling Code Here.">
        btnNormal.addActionListener(e->{setNewStyle("normal", true);});
        btnBold.addActionListener(e->{setNewStyle("bold", true);});
        btnUnderline.addActionListener(e->{setNewStyle("underline", true);});
        btnItalisize.addActionListener(e->{setNewStyle("italisize", true);});
        btnJustify.addActionListener(e->{setNewStyle("jusitify", true);});
        btnSup.addActionListener(e->{setNewStyle("superscript", true);});
        btnSub.addActionListener(e->{setNewStyle("subscript", true);});
        btnLeftAlign.addActionListener(e->{setNewStyle("leftalign", true);});
        btnCenterAlign.addActionListener(e->{setNewStyle("centeralign", true);});
        btnRightAlign.addActionListener(e->{setNewStyle("roghtalign", true);});
        btnIndent.addActionListener(e->{setNewStyle("indent", false);});
        btnBlock.addActionListener(e->{setNewStyle("block", false);});
        btnStrikeThrough.addActionListener(e->{setNewStyle("strikethrough", true);});
        sizeSpinner.addChangeListener(e->{
            int size = (int) sizeSpinner.getNextValue();
            
            //Get the default style.
            StyleContext styleContext = StyleContext.getDefaultStyleContext();
            Style defaultStyleContext = styleContext.getStyle(StyleContext.DEFAULT_STYLE);
            //Create a styled Document.
            StyledDocument document = viewer.getStyledDocument();
            Style normalStyle = document.addStyle("normal", defaultStyleContext);
            //Setting font size.
            Style fontSize = document.addStyle("fontsize", normalStyle);
            StyleConstants.setFontSize(fontSize, size);
            setNewStyle("fontsize", false);
        });
        alignCombo.addItemListener(e->{
            var selected = e.getItem();
            if(selected.equals("Align Left"))
            {
                setNewStyle("leftalign", false);
            }
            else if(selected.equals("Align Center"))
            {
                setNewStyle("centeralign", false);
            }
            else if(selected.equals("Align Right"))
            {
                setNewStyle("rightalign", false);
            }
        });
        
        fontsCombo.addItemListener(e->{
            var selected = e.getItem();
            if(selected.equals("Mono-Spaced"))
            {
                Font monoSpaced = new Font(Font.MONOSPACED, Font.PLAIN, 8);
                viewer.setFont(monoSpaced);
            }
            else if(selected.equals("Serif"))
            {
                Font serif = new Font(Font.SERIF, Font.PLAIN, 8);
                viewer.setFont(serif);
            }
            else if(selected.equals("Sans-Serif"))
            {
                Font sansSerif = new Font(Font.SANS_SERIF, Font.PLAIN, 8);
                viewer.setFont(sansSerif);
            }
            else if(selected.equals("Dialog"))
            {
                Font dialog = new Font(Font.DIALOG, Font.PLAIN, 8);
                viewer.setFont(dialog);
            }
            else if(selected.equals("Dialog-Input"))
            {
                Font dialogInput = new Font(Font.DIALOG_INPUT, Font.PLAIN, 8);
                viewer.setFont(dialogInput);
            }
        });
        
        btnColor.addActionListener(e->{
            Color color = JColorChooser.showDialog(this, "Text Color", Color.white);
            if(color != null)
            {
                //Get the default style.
                StyleContext styleContext = StyleContext.getDefaultStyleContext();
                Style defaultStyleContext = styleContext.getStyle(StyleContext.DEFAULT_STYLE);
                //Create a styled Document.
                StyledDocument document = viewer.getStyledDocument();
                Style normalStyle = document.addStyle("normal", defaultStyleContext);
                
                Style colorText = document.addStyle("textcolor", normalStyle);
                StyleConstants.setForeground(colorText, color);
                
                setNewStyle("textcolor", true);
            }
        });
        // </editor-fold>
        return toolBar;
    }
    
    private JPanel getStatusPanel()
    {
        JPanel statusPanel = new JPanel();
        JProgressBar readerProgress = new JProgressBar(viewerScroll.getVerticalScrollBar().getMinimum(), viewerScroll.getVerticalScrollBar().getBlockIncrement());
        String[] viewerStates = new String[]{"Read-Only", "Edit"};
        JComboBox<String> viewerState = new JComboBox<>(viewerStates);
        Box hBox = Box.createHorizontalBox();
        
        hBox.add(viewerState);
        
        statusPanel.add(readerProgress, BorderLayout.WEST);
        statusPanel.add(hBox, BorderLayout.EAST);
        
        readerProgress.setStringPainted(false);
        
        viewerState.addItemListener(e->{
            var selected = viewerState.getSelectedItem();
            if(selected.equals("Edit"))
            {
                viewer.setEditable(true);
                readerProgress.setEnabled(false);
            }
            else
            {
                viewer.setEditable(false);
            }
        });
        
        viewerScroll.getVerticalScrollBar().addAdjustmentListener(e->
            readerProgress.setValue(viewerScroll.getVerticalScrollBar()
                    .getValue()));
        
        return statusPanel;
    }
    /**
     * 
     * @param none
     * Add some styling capabilities to the app.
     */
    private void addStyles()
    {
        // <editor-fold defaultstate="collapsed" desc="Function Body Here.">
        //Get the default style.
        StyleContext styleContext = StyleContext.getDefaultStyleContext();
        Style defaultStyleContext = styleContext.getStyle(StyleContext.DEFAULT_STYLE);
        //Create a styled Document.
        StyledDocument document = viewer.getStyledDocument();
        Style normalStyle = document.addStyle("normal", defaultStyleContext);
        //Bold Style.
        Style boldStyle = document.addStyle("bold", normalStyle);
        StyleConstants.setBold(boldStyle, true);
        //Underline logic here.
        Style underlineStyle = document.addStyle("underline", normalStyle);
        StyleConstants.setUnderline(underlineStyle, true);
        //Italicize logic here.
        Style italicStyle = document.addStyle("italisize", normalStyle);
        StyleConstants.setItalic(italicStyle, true);
        //Left align logic here.
        Style alignLeft = document.addStyle("leftalign", normalStyle);
        StyleConstants.setAlignment(alignLeft, StyleConstants.ALIGN_LEFT);
        //Right alignr logic here.
        Style alignRight = document.addStyle("rightalign", normalStyle);
        StyleConstants.setAlignment(alignRight, StyleConstants.ALIGN_RIGHT);
        //Center align logic here.
        Style alignCenter = document.addStyle("centeralign", normalStyle);
        StyleConstants.setAlignment(alignCenter, StyleConstants.ALIGN_CENTER);
        //Justify logic here.
        Style justifyStyle = document.addStyle("jusitify", normalStyle);
        StyleConstants.setAlignment(justifyStyle, StyleConstants.ALIGN_JUSTIFIED);
        //Indent paragraph logic here.
        Style indent = document.addStyle("indent", normalStyle);
        StyleConstants.setFirstLineIndent(indent, 10.0f);
        //Block paragraph logic here.
        Style block = document.addStyle("block", normalStyle);
        StyleConstants.setFirstLineIndent(block, 0.0f);
        //Supescript logic here
        Style superScriptStyle = document.addStyle("superscript", normalStyle);
        StyleConstants.setSuperscript(superScriptStyle, true);
        //Subscript logic here.
        Style subScriptStyle = document.addStyle("subscript", normalStyle);
        StyleConstants.setSubscript(subScriptStyle, true);
        //Strike-Through logic here.
        Style strikeThroughStyle = document.addStyle("strikethrough", normalStyle);
        StyleConstants.setStrikeThrough(strikeThroughStyle, true);
        // </editor-fold>
    }
    /**
     * 
     * @param styleName the name of the style to be applied.
     * @param isCharacterStyle Boolean if the style is Character
     * based(highlighted text only) or Document based(entire document).
     * 
     * Function sets a new style to the document in the text pane.
     */
    private void setNewStyle(String styleName, boolean isCharacterStyle)
    {
        // <editor-fold defaultstate="collapsed" desc="Function Body Here.">
        StyledDocument document = viewer.getStyledDocument();
        
        Style newStyle = document.getStyle(styleName);
        
        int start = viewer.getSelectionStart();
        int end = viewer.getSelectionEnd();
        
        if(isCharacterStyle)
        {
            boolean replaceOld = styleName.equals("normal");
            document.setCharacterAttributes(start, end-start, newStyle, replaceOld);
        }
        else
        {
            document.setParagraphAttributes(start, end-start, newStyle, isCharacterStyle);
        }
        // </editor-fold>
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {//"Resources/icon/icon.jpg"
        
        // <editor-fold defaultstate="collapsed" desc="UI Settings Here">
        try
        {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels())
            {
                if ("Windows".equals(info.getName()))
                {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        }
        catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex)
        {
            java.util.logging.Logger.getLogger(SimpleFileViewerAndEditor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        // </editor-fold>
        // <editor-fold defaultstate="collapsed" desc="Application Runner Code Here.">
        EventQueue.invokeLater(() ->
        {
            new SimpleFileViewerAndEditor().setVisible(true);
        });
        // </editor-fold>
    }
}
