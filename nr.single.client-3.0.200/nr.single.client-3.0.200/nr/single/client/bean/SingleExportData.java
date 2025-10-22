/*
 * Decompiled with CFR 0.152.
 */
package nr.single.client.bean;

public class SingleExportData {
    private String fileName;
    private byte[] data;

    public SingleExportData(String fileName, byte[] data) {
        this.fileName = fileName;
        this.data = data;
    }

    public SingleExportData() {
    }

    public String getFileName() {
        return this.fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public byte[] getData() {
        return this.data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }
}

