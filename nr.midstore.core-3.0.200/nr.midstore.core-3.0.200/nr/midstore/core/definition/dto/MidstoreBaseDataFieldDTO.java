/*
 * Decompiled with CFR 0.152.
 */
package nr.midstore.core.definition.dto;

import nr.midstore.core.definition.IMidstoreBaseDataField;
import nr.midstore.core.internal.definition.MidstoreBaseDataFieldDO;
import org.springframework.beans.BeanUtils;

public class MidstoreBaseDataFieldDTO
extends MidstoreBaseDataFieldDO {
    private static final long serialVersionUID = 1L;

    public static MidstoreBaseDataFieldDTO valueOf(IMidstoreBaseDataField o) {
        if (o == null) {
            return null;
        }
        if (o instanceof MidstoreBaseDataFieldDTO) {
            return (MidstoreBaseDataFieldDTO)o;
        }
        MidstoreBaseDataFieldDTO t = new MidstoreBaseDataFieldDTO();
        BeanUtils.copyProperties(o, t);
        return t;
    }
}

