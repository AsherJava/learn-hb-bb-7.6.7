/*
 * Decompiled with CFR 0.152.
 */
package nr.single.client.app;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import nr.single.client.app.ReportMainMenu;
import nr.single.client.internal.service.SingleFuncExecuteServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReportMain
extends JFrame {
    private static final Logger log = LoggerFactory.getLogger(SingleFuncExecuteServiceImpl.class);
    int i = 0;

    public ReportMain() {
        this.setTitle("\u4e45\u5176\u6570\u636e\u5de5\u5177");
        ReportMainMenu mainMenu = new ReportMainMenu();
        mainMenu.setMainForm(this);
        this.setJMenuBar(mainMenu);
        this.createTestButton();
        this.setBounds(300, 250, 500, 200);
        this.setVisible(true);
        this.setCloseEvent();
    }

    private void createTestButton() {
        JPanel jp = new JPanel();
        final JButton jb = new JButton("\u6309\u94ae");
        JLabel jl = new JLabel("\u63d0\u793a\u6309\u7167\u70b9\u51fb\u589e\u52a0\uff1a");
        jp.add(jl);
        jp.add(jb);
        jb.setMnemonic('F');
        jb.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                jb.setText("\u6b21\u6570\uff1a" + ++ReportMain.this.i);
            }
        });
        this.add(jp);
    }

    private void setCloseEvent() {
        this.addWindowListener(new WindowAdapter(){

            @Override
            public void windowClosing(WindowEvent e) {
                System.out.println("\u89e6\u53d1windowClosing\u4e8b\u4ef6");
                System.exit(0);
            }

            @Override
            public void windowClosed(WindowEvent e) {
                System.out.println("\u89e6\u53d1windowClosed\u4e8b\u4ef6");
            }
        });
    }

    private void closeFrame() {
        System.out.println("\u8c03\u7528\u7a97\u4f53\u5173\u95ed\u529f\u80fd");
        int result = JOptionPane.showConfirmDialog(null, "\u662f\u5426\u8981\u9000\u51fa\uff1f", "\u9000\u51fa\u786e\u8ba4", 0, 3);
        if (result == 0) {
            this.dispose();
            System.exit(0);
        } else {
            this.setVisible(true);
        }
    }

    public void openDBFTable() throws Exception {
        JOptionPane.showMessageDialog(null, "\u6253\u5f00DBF");
    }

    public static void main(String[] args) {
        ReportMain s1 = new ReportMain();
        s1.setSize(800, 600);
    }
}

