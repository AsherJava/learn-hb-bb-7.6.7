/*
 * Decompiled with CFR 0.152.
 */
package nr.single.client.app;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;
import nr.single.client.app.TableFrame;

public class ReportMainMenu
extends JMenuBar {
    private JFrame mainForm;

    public ReportMainMenu() {
        this.add(this.createFileMenu());
        this.add(this.createEditMenu());
        this.setVisible(true);
    }

    public static void main(String[] agrs) {
        JFrame frame = new JFrame("\u83dc\u5355\u680f");
        frame.setSize(300, 200);
        frame.setJMenuBar(new ReportMainMenu());
        frame.setDefaultCloseOperation(3);
        frame.setVisible(true);
    }

    private JMenu createFileMenu() {
        JMenu menu = new JMenu("\u6587\u4ef6(F)");
        menu.setMnemonic(70);
        JMenuItem item = new JMenuItem("\u65b0\u5efa(N)", 78);
        item.setAccelerator(KeyStroke.getKeyStroke(78, 2));
        menu.add(item);
        item = new JMenuItem("\u6253\u5f00(O)", 79);
        item.setAccelerator(KeyStroke.getKeyStroke(79, 2));
        item.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser(".");
                fileChooser.showOpenDialog(ReportMainMenu.this.mainForm);
                File file = fileChooser.getSelectedFile();
                ReportMainMenu.this.mainForm.setVisible(false);
                TableFrame tFrame = new TableFrame();
                tFrame.openDBF(file.getAbsolutePath());
            }
        });
        menu.add(item);
        item = new JMenuItem("\u4fdd\u5b58(S)", 83);
        item.setAccelerator(KeyStroke.getKeyStroke(83, 2));
        menu.add(item);
        menu.addSeparator();
        item = new JMenuItem("\u9000\u51fa(E)", 69);
        item.setAccelerator(KeyStroke.getKeyStroke(69, 2));
        item.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        menu.add(item);
        return menu;
    }

    private JMenu createEditMenu() {
        JMenu menu = new JMenu("\u7f16\u8f91(E)");
        menu.setMnemonic(69);
        JMenuItem item = new JMenuItem("\u64a4\u9500(U)", 85);
        item.setEnabled(false);
        menu.add(item);
        menu.addSeparator();
        item = new JMenuItem("\u526a\u8d34(T)", 84);
        menu.add(item);
        item = new JMenuItem("\u590d\u5236(C)", 67);
        menu.add(item);
        menu.addSeparator();
        JCheckBoxMenuItem cbMenuItem = new JCheckBoxMenuItem("\u81ea\u52a8\u6362\u884c");
        menu.add(cbMenuItem);
        return menu;
    }

    public JFrame getMainForm() {
        return this.mainForm;
    }

    public void setMainForm(JFrame mainForm) {
        this.mainForm = mainForm;
    }
}

