/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.i18n.ext.I18NResource
 *  com.jiuqi.np.i18n.ext.I18NResourceItem
 */
package com.jiuqi.nr.dataentry.i18n;

import com.jiuqi.np.i18n.ext.I18NResource;
import com.jiuqi.np.i18n.ext.I18NResourceItem;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class ExceptionI18nResourceRegister
implements I18NResource {
    private static final long serialVersionUID = -8253502389972008674L;
    private static final String SYSTEMERROR = "SYSTEMERROR";
    private static final String FILEERROR = "FILEERROR";
    private static final String SHEETERROR = "SHEETERROR";
    private static final String REPORTERROR = "REPORTERROR";
    private static final String REGIONERROR = "REGIONERROR";
    private static final String DATAERROR = "DATAERROR";
    private static final String TABLESAMPLEERROR = "TABLESAMPLEERROR";
    private static final String FILENAMEUNITCODEERROR = "FILENAMEUNITCODEERROR";
    private static final String ZIPNOTEXCELERROR = "ZIPNOTEXCELERROR";
    private static final String IMPORTFILETYPEERROR = "IMPORTFILETYPEERROR";
    private static final String NOTOPENDATAENTRY = "NOTOPENDATAENTRY";

    public String name() {
        return "\u65b0\u62a5\u8868/\u6570\u636e\u5f55\u5165/\u5f02\u5e38\u4fe1\u606f\u6536\u96c6";
    }

    public String getNameSpace() {
        return "nr";
    }

    public List<I18NResourceItem> getResource(String parentId) {
        ArrayList<I18NResourceItem> resourceObjects = new ArrayList<I18NResourceItem>();
        if (null == parentId || "".equals(parentId)) {
            resourceObjects.add(new I18NResourceItem("Exception_500", "\u4efb\u52a1\u9519\u8bef\u4fe1\u606f"));
            resourceObjects.add(new I18NResourceItem(SYSTEMERROR, "\u7cfb\u7edf\u9519\u8bef"));
            resourceObjects.add(new I18NResourceItem(FILEERROR, "\u6587\u4ef6\u9519\u8bef"));
            resourceObjects.add(new I18NResourceItem(SHEETERROR, "sheet\u9875\u9519\u8bef"));
            resourceObjects.add(new I18NResourceItem(REPORTERROR, "\u62a5\u8868\u9519\u8bef"));
            resourceObjects.add(new I18NResourceItem(REGIONERROR, "\u533a\u57df\u9519\u8bef"));
            resourceObjects.add(new I18NResourceItem(DATAERROR, "\u5355\u5143\u683c\u9519\u8bef"));
            resourceObjects.add(new I18NResourceItem(TABLESAMPLEERROR, "\u8868\u6837\u5339\u914d\u5931\u8d25"));
            resourceObjects.add(new I18NResourceItem(FILENAMEUNITCODEERROR, "\u6587\u4ef6\u540d\u79f0\u672a\u5339\u914d\u5230\u5355\u4f4d\u6216\u5355\u4f4d\u4e0d\u552f\u4e00"));
            resourceObjects.add(new I18NResourceItem(ZIPNOTEXCELERROR, "zip\u4e2d\u6ca1\u6709excel\u6587\u4ef6"));
            resourceObjects.add(new I18NResourceItem(IMPORTFILETYPEERROR, "\u5bfc\u5165\u6587\u4ef6\u7c7b\u578b\u4e0d\u7b26"));
            resourceObjects.add(new I18NResourceItem(NOTOPENDATAENTRY, "\u672a\u5f00\u653e\u586b\u62a5"));
        }
        return resourceObjects;
    }
}

