/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.common.FieldType
 */
package nr.single.para.compare.definition;

import com.jiuqi.np.definition.common.FieldType;
import java.io.Serializable;

public class CompareDataFieldExtendDTO
implements Serializable {
    private static final long serialVersionUID = 1L;
    private FieldType fieldType;

    public FieldType getFieldType() {
        return this.fieldType;
    }

    public void setFieldType(FieldType fieldType) {
        this.fieldType = fieldType;
    }
}

