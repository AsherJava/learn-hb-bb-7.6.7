/*
 * Decompiled with CFR 0.152.
 */
package nr.midstore.core.definition.dto;

import nr.midstore.core.definition.IMidstoreField;
import nr.midstore.core.internal.definition.MidstoreFieldDO;
import org.springframework.beans.BeanUtils;

public class MidstoreFieldDTO
extends MidstoreFieldDO {
    private static final long serialVersionUID = 1L;

    public static MidstoreFieldDTO valueOf(IMidstoreField o) {
        if (o == null) {
            return null;
        }
        if (o instanceof MidstoreFieldDTO) {
            return (MidstoreFieldDTO)o;
        }
        MidstoreFieldDTO t = new MidstoreFieldDTO();
        BeanUtils.copyProperties(o, t);
        return t;
    }
}

