/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.single.core.dbf.DbfTable
 *  com.jiuqi.nr.single.core.util.datatable.DataRow
 */
package nr.single.client.app;

import com.jiuqi.nr.single.core.dbf.DbfTable;
import com.jiuqi.nr.single.core.util.datatable.DataRow;
import java.util.Date;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import nr.single.client.internal.service.SingleFuncExecuteServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TableFrame {
    private static final Logger log = LoggerFactory.getLogger(SingleFuncExecuteServiceImpl.class);
    JFrame jf = new JFrame("DBF\u7f16\u8f91\u5668");
    Object[] titles = new Object[]{"\u59d3\u540d", "\u5e74\u9f84", "\u6027\u522b"};
    Object[][] datas = new Object[][]{{"1", 29, "\u5973"}, {"1", 30, "\u7537"}};

    public void openDBF(String fileName) {
        try {
            System.out.println(String.valueOf(new Date()) + "\u52a0\u8f7d\u6587\u4ef61:" + fileName);
            DbfTable dbf = new DbfTable(fileName);
            System.out.println(String.valueOf(new Date()) + "\u7ed3\u675f\u52a0\u8f7d\u6587\u4ef6");
            this.titles = new Object[dbf.getFieldCount2()];
            for (int i = 0; i < dbf.getFieldCount2(); ++i) {
                this.titles[i] = dbf.geDbfFields()[i].getFieldName();
            }
            int rowCount = dbf.getDataRealRowCount();
            this.datas = new Object[rowCount][dbf.getFieldCount2()];
            for (int j = 0; j < dbf.getTable().getRowCount(); ++j) {
                DataRow row = (DataRow)dbf.getTable().getRows().get(j);
                for (int i = 0; i < dbf.getFieldCount2(); ++i) {
                    this.datas[j][i] = row.getValueString(i);
                }
            }
            System.out.println("\u8bbe\u7f6e\u8868\u5934");
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        JTable jTable = new JTable(this.datas, this.titles);
        JScrollPane jScroll = new JScrollPane(jTable);
        jScroll.setHorizontalScrollBarPolicy(30);
        this.jf.add(jScroll);
        this.jf.setDefaultCloseOperation(3);
        this.jf.pack();
        this.jf.setVisible(true);
    }

    public static void main(String[] args) {
        new TableFrame().openDBF("D:\\Sys_CCSHSM.DBF");
    }
}

