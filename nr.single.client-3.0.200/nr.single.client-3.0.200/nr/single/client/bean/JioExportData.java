/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.dataentry.bean.ExportData
 */
package nr.single.client.bean;

import com.jiuqi.nr.dataentry.bean.ExportData;

public class JioExportData
extends ExportData {
    private int attachFileNum;

    public JioExportData(String fileName, byte[] data) {
        super(fileName, data);
    }

    public JioExportData(String fileName, String alisFileName, String fileLocation) {
        super(fileName, alisFileName, fileLocation);
    }

    public int getAttachFileNum() {
        return this.attachFileNum;
    }

    public void setAttachFileNum(int attachFileNum) {
        this.attachFileNum = attachFileNum;
    }
}

