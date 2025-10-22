/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.i18n.ext.I18NResource
 *  com.jiuqi.np.i18n.ext.I18NResourceItem
 *  com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService
 */
package com.jiuqi.nr.dataentry.i18n;

import com.jiuqi.np.i18n.ext.I18NResource;
import com.jiuqi.np.i18n.ext.I18NResourceItem;
import com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CheckResultExportI18nResourceRegister
implements I18NResource {
    private static final long serialVersionUID = 4372658815165835927L;
    @Autowired
    private INvwaSystemOptionService iNvwaSystemOptionService;

    public String name() {
        return "\u65b0\u62a5\u8868/\u6570\u636e\u5f55\u5165/\u5ba1\u6838\u7ed3\u679c\u5bfc\u51fa";
    }

    public String getNameSpace() {
        return "nr";
    }

    public List<I18NResourceItem> getResource(String parentId) {
        ArrayList<I18NResourceItem> resourceObjects = new ArrayList<I18NResourceItem>();
        if (null == parentId || "".equals(parentId)) {
            resourceObjects.add(new I18NResourceItem("SerialNumber", "\u5e8f\u53f7"));
            resourceObjects.add(new I18NResourceItem("Review-Type", "\u5ba1\u6838\u7c7b\u578b"));
            resourceObjects.add(new I18NResourceItem("Unit-Code", "\u5355\u4f4d\u4ee3\u7801"));
            resourceObjects.add(new I18NResourceItem("Unit-Title", "\u5355\u4f4d\u540d\u79f0"));
            resourceObjects.add(new I18NResourceItem("ReportName", "\u6240\u5728\u62a5\u8868\u540d\u79f0"));
            resourceObjects.add(new I18NResourceItem("ReportCode", "\u6240\u5728\u62a5\u8868\u6807\u8bc6"));
            resourceObjects.add(new I18NResourceItem("Formula-Number", "\u516c\u5f0f\u7f16\u53f7"));
            resourceObjects.add(new I18NResourceItem("Formula-Description", "\u516c\u5f0f\u8bf4\u660e"));
            resourceObjects.add(new I18NResourceItem("Formula", "\u516c\u5f0f"));
            resourceObjects.add(new I18NResourceItem("Difference", "\u5dee\u989d"));
            resourceObjects.add(new I18NResourceItem("ErrorData", "\u9519\u8bef\u6570\u636e"));
            resourceObjects.add(new I18NResourceItem("ErrorDescription", "\u5ba1\u6838\u9519\u8bef\u8bf4\u660e"));
            resourceObjects.add(new I18NResourceItem("Check-Result", "\u5ba1\u6838\u7ed3\u679c"));
            if ("1".equals(this.iNvwaSystemOptionService.get("nr-audit-group", "EXPORT_ROW_DIM"))) {
                resourceObjects.add(new I18NResourceItem("RowDim", "\u884c\u7ef4\u5ea6"));
            }
        }
        return resourceObjects;
    }
}

