/*
 * Decompiled with CFR 0.152.
 */
package nr.midstore.core.definition.dto;

import nr.midstore.core.definition.IMidstoreData;
import nr.midstore.core.internal.definition.MidstoreDataDO;
import org.springframework.beans.BeanUtils;

public class MidstoreDataDTO
extends MidstoreDataDO {
    private static final long serialVersionUID = 1L;

    public static MidstoreDataDTO valueOf(IMidstoreData o) {
        if (o == null) {
            return null;
        }
        if (o instanceof MidstoreDataDTO) {
            return (MidstoreDataDTO)o;
        }
        MidstoreDataDTO t = new MidstoreDataDTO();
        BeanUtils.copyProperties(o, t);
        return t;
    }
}

