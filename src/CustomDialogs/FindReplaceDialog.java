/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CustomDialogs;

import java.awt.Container;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;
import simplefileviewerandeditor.SimpleFileViewerAndEditor;

/**
 *
 * @author MWIGO JON MARK
 */
public class FindReplaceDialog extends JDialog
{
    JLabel find;//bmgmhgjjh
    JLabel replace;//bmgmhgjjh
    JTextField findText;//bmgmhgjjh
    JTextField replaceText;//bmgmhgjjh
    JButton findBtn;//bmgmhgjjh
    JButton findNextBtn;//bmgmhgjjh
    JButton replaceBtn;//bmgmhgjjh
    JButton replaceAllBtn;//bmgmhgjjh
    /**
     * @param none
     */
    public FindReplaceDialog()
    {
        SimpleFileViewerAndEditor parent = new SimpleFileViewerAndEditor();
        find = new JLabel("Find: ");
        replace = new JLabel("Replace: ");
        findText = new JTextField();
        replaceText = new JTextField();
        findBtn = new JButton("Find");
        findNextBtn = new JButton("Find Next");
        replaceBtn = new JButton("Replace");
        replaceAllBtn = new JButton("Replace All");
        //Box mainPane = Box.createVerticalBox();
        Box vBox = Box.createVerticalBox();
        Container mainPane = this.getContentPane();
        
        this.setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
        this.setSize(300, 200);
        
        find.setSize(50, 30);
        replace.setSize(90, 30);
        findText.setSize(200, 30);
        replaceText.setSize(200, 30);
        findBtn.setSize(59, 30);
        replaceBtn.setSize(80, 30);
        findNextBtn.setSize(90, 30);
        replaceAllBtn.setSize(120, 30);
        
//        findBtn.setLocation(150, );
//        findNextBtn.setLocation(, );
//        replaceBtn.setLocation(150, );
//        replaceAllBtn.setLocation(, );
        
        find.setLocation(2, 4);
        replace.setLocation(2, 40);
        findText.setLocation(80, 4);
        replaceText.setLocation(80, 40);
        findBtn.setLocation(2, 90);
        replaceBtn.setLocation(2, 140);
        findNextBtn.setLocation(190, 90);
        replaceAllBtn.setLocation(160, 140);
        
        
        mainPane.add(find);
        mainPane.add(findText);
        mainPane.add(replace);
        mainPane.add(replaceText);
        
        mainPane.add(findBtn);
        mainPane.add(findNextBtn);
        mainPane.add(replaceBtn);
        mainPane.add(replaceAllBtn);
        
        mainPane.setLayout(null);
        
        this.setTitle("Find & Replace");
        this.setLocationRelativeTo(parent);
        this.setVisible(true);
    }
    /**
     * 
     * @param args
     * main Function
     */
    public static void main(String[] args)
    {
        FindReplaceDialog dialog = new FindReplaceDialog();
    }
}
