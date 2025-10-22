/*
 * Decompiled with CFR 0.152.
 */
package nr.single.map.configurations.internal.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import nr.single.map.configurations.enumaration.ErrorLevel;
import nr.single.map.configurations.enumaration.FileKind;

public class IllegalData
implements Serializable {
    private static final long serialVersionUID = 1L;
    public static final String NOTEXIST_ENTITY = "\u4e0d\u5b58\u5728\u7684\u5355\u4f4d\uff01";
    public static final String NOTEXIST_ENTITY_BBLX = "\u4e0d\u5b58\u5728\u4e0e\u5f53\u524d\u62a5\u8868\u7c7b\u578b\u5339\u914d\u7684\u5355\u4f4d";
    public static final String NOTEXIST_ENTITY_SINGLE = "\u4e0d\u5b58\u5728\u7684\u5e73\u53f0\u5355\u4f4d\uff01";
    public static final String NOTEXIST_ZB = "\u4e0d\u5b58\u5728\u7684\u6307\u6807\uff01";
    public static final String NOTEXIST_TABLE = "\u4e0d\u5b58\u5728\u7684\u5b58\u50a8\u8868\uff01";
    public static final String NOTEXIST_FORMULA = "\u4e0d\u5b58\u5728\u7684\u516c\u5f0f\u7f16\u53f7\uff01";
    public static final String NOTEXIST_NET_FORM = "\u4e0d\u5b58\u5728\u7684\u7f51\u7edc\u62a5\u8868\uff01";
    public static final String NOTEXIST_SINGLE_FORM = "\u4e0d\u5b58\u5728\u7684\u5e73\u53f0\u62a5\u8868\uff01";
    public static final String NOTEXIST_BBLX = "\u65e0\u6cd5\u8bbe\u7f6e\u62a5\u8868\u7c7b\u578b\uff01";
    public static final String ERROR_FORMAT_ENTITY_SINGLE = "\u9519\u8bef\u683c\u5f0f\u7684\u5e73\u53f0\u5355\u4f4d\u4ee3\u7801\uff01";
    private int total = 0;
    private List<ErrorItem> errorItems = new ArrayList<ErrorItem>();
    private String errorInfo = UUID.randomUUID().toString();

    public IllegalData() {
    }

    public IllegalData(int total, List<ErrorItem> errorItems, String errorInfo) {
        this.total = total;
        this.errorItems = errorItems;
        this.errorInfo = errorInfo;
    }

    public int getTotal() {
        return this.total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<ErrorItem> getErrorItems() {
        return this.errorItems;
    }

    public void setErrorItems(List<ErrorItem> errorItems) {
        this.errorItems = errorItems;
    }

    public String getErrorInfo() {
        return this.errorInfo;
    }

    public void setErrorInfo(String errorInfo) {
        this.errorInfo = errorInfo;
    }

    public void addErrorZb(String msg, String tableCode, String index, String errorData) {
        this.addErrorItem(msg, this.getZbIndex(tableCode, index), errorData, FileKind.ZB_FILE);
    }

    public void addErrorEntity(String msg, String index, String errorData) {
        this.addErrorItem(msg, index, errorData, FileKind.ENTITY_FILE);
    }

    public void addErrorFormula(String msg, String index, String errorData) {
        this.addErrorItem(msg, index, errorData, FileKind.FORMULA_FILE);
    }

    public void addErrorSingleForm(String msg, String index, String errorData) {
        this.addErrorItem(msg, index, errorData, FileKind.ZB_FILE);
    }

    protected String getZbIndex(String tableCode, String idx) {
        if (idx == null) {
            return "0";
        }
        return idx + "_" + tableCode;
    }

    public static String getFormIdx(String idx) {
        if (idx == null) {
            return "0";
        }
        return idx.substring(0, idx.indexOf("_"));
    }

    public static String getFormluaIdx(String idx) {
        if (idx == null) {
            return "0";
        }
        return idx.substring(idx.indexOf("_") + 1, idx.length());
    }

    private void addErrorItem(String msg, String index, String errorData, FileKind entityFile) {
        ErrorItem item = new ErrorItem(msg, index, entityFile, ErrorLevel.ERROR_INFO, errorData);
        this.errorItems.add(item);
        ++this.total;
    }

    public void addErrorItems(List<ErrorItem> errorItems) {
        this.errorItems.addAll(errorItems);
        this.total += errorItems.size();
    }

    public class ErrorItem
    implements Serializable {
        private static final long serialVersionUID = 1L;
        private String msg;
        private String index;
        private FileKind kind;
        private ErrorLevel level;
        private String errorData;

        public ErrorItem() {
        }

        public ErrorItem(String msg, String index, FileKind kind, ErrorLevel level, String errorData) {
            this.msg = msg;
            this.index = index;
            this.kind = kind;
            this.level = level;
            this.errorData = errorData;
        }

        public String getMsg() {
            return this.msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public String getIndex() {
            return this.index;
        }

        public void setIndex(String index) {
            this.index = index;
        }

        public FileKind getKind() {
            return this.kind;
        }

        public void setKind(FileKind kind) {
            this.kind = kind;
        }

        public ErrorLevel getLevel() {
            return this.level;
        }

        public void setLevel(ErrorLevel level) {
            this.level = level;
        }

        public String getErrorData() {
            return this.errorData;
        }

        public void setErrorData(String errorData) {
            this.errorData = errorData;
        }
    }
}

