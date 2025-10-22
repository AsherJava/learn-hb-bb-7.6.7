/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.dataentry.bean.AbstractImportResultItem
 *  com.jiuqi.nr.dataentry.bean.ImportResultItem
 */
package nr.single.client.bean;

import com.jiuqi.nr.dataentry.bean.AbstractImportResultItem;
import com.jiuqi.nr.dataentry.bean.ImportResultItem;
import java.io.Serializable;

public class JioImportResultItem
extends AbstractImportResultItem
implements ImportResultItem,
Serializable {
    private static final long serialVersionUID = 1L;
    private String formName;
    private String formCode;
    private String unitName;

    public String getFormName() {
        return this.formName;
    }

    public void setFormName(String formName) {
        this.formName = formName;
    }

    public String getFormCode() {
        return this.formCode;
    }

    public void setFormCode(String formCode) {
        this.formCode = formCode;
    }

    public String getUnitName() {
        return this.unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }
}

