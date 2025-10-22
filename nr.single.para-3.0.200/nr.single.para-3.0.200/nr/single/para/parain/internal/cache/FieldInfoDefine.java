/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.facade.DesignFieldDefine
 *  com.jiuqi.nr.datascheme.api.DesignDataField
 *  com.jiuqi.nr.entity.model.IEntityAttribute
 */
package nr.single.para.parain.internal.cache;

import com.jiuqi.np.definition.facade.DesignFieldDefine;
import com.jiuqi.nr.datascheme.api.DesignDataField;
import com.jiuqi.nr.entity.model.IEntityAttribute;
import java.util.ArrayList;
import java.util.List;

public class FieldInfoDefine {
    DesignFieldDefine fieldDefine;
    DesignDataField dataField;
    IEntityAttribute entityAttr;

    public FieldInfoDefine(DesignFieldDefine fieldDefine) {
        this.fieldDefine = fieldDefine;
    }

    public FieldInfoDefine(DesignDataField dataField) {
        this.dataField = dataField;
    }

    public FieldInfoDefine(IEntityAttribute entityAttr) {
        this.entityAttr = entityAttr;
    }

    public String getKey() {
        if (this.fieldDefine != null) {
            return this.fieldDefine.getKey();
        }
        if (this.dataField != null) {
            return this.dataField.getKey();
        }
        if (this.entityAttr != null) {
            return this.entityAttr.getID();
        }
        return null;
    }

    public String getCode() {
        if (this.fieldDefine != null) {
            return this.fieldDefine.getCode();
        }
        if (this.dataField != null) {
            return this.dataField.getCode();
        }
        if (this.entityAttr != null) {
            return this.entityAttr.getCode();
        }
        return null;
    }

    public String getAlis() {
        if (this.fieldDefine != null) {
            return this.fieldDefine.getCode();
        }
        if (this.dataField != null) {
            return this.dataField.getAlias();
        }
        if (this.entityAttr != null) {
            return this.entityAttr.getCode();
        }
        return null;
    }

    public String getTitle() {
        if (this.fieldDefine != null) {
            return this.fieldDefine.getTitle();
        }
        if (this.dataField != null) {
            return this.dataField.getTitle();
        }
        if (this.entityAttr != null) {
            return this.entityAttr.getTitle();
        }
        return null;
    }

    public int getSize() {
        if (this.fieldDefine != null) {
            return this.fieldDefine.getSize();
        }
        if (this.dataField != null) {
            return this.dataField.getDecimal();
        }
        if (this.entityAttr != null) {
            return this.entityAttr.getPrecision();
        }
        return 0;
    }

    public String getOwnerTableKey() {
        if (this.fieldDefine != null) {
            return this.fieldDefine.getOwnerTableKey();
        }
        if (this.dataField != null) {
            return this.dataField.getDataTableKey();
        }
        if (this.entityAttr != null) {
            return this.entityAttr.getTableID();
        }
        return null;
    }

    public DesignFieldDefine getFieldDefine() {
        return this.fieldDefine;
    }

    public void setFieldDefine(DesignFieldDefine fieldDefine) {
        this.fieldDefine = fieldDefine;
    }

    public DesignDataField getDataField() {
        return this.dataField;
    }

    public void setDataField(DesignDataField dataField) {
        this.dataField = dataField;
    }

    public IEntityAttribute getEntityAttr() {
        return this.entityAttr;
    }

    public void setEntityAttr(IEntityAttribute entityAttr) {
        this.entityAttr = entityAttr;
    }

    public static List<FieldInfoDefine> getFieldInfos(List<DesignFieldDefine> oldList) {
        ArrayList<FieldInfoDefine> list = new ArrayList<FieldInfoDefine>();
        if (oldList != null) {
            for (DesignFieldDefine field : oldList) {
                list.add(new FieldInfoDefine(field));
            }
        }
        return list;
    }

    public static List<FieldInfoDefine> getFieldInfos2(List<DesignDataField> oldList) {
        ArrayList<FieldInfoDefine> list = new ArrayList<FieldInfoDefine>();
        if (oldList != null) {
            for (DesignDataField field : oldList) {
                list.add(new FieldInfoDefine(field));
            }
        }
        return list;
    }
}

