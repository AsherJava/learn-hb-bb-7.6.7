/*
 * Decompiled with CFR 0.152.
 */
package nr.midstore.core.definition.dto;

import nr.midstore.core.definition.IMidstoreBaseData;
import nr.midstore.core.internal.definition.MidstoreBaseDataDO;
import org.springframework.beans.BeanUtils;

public class MidstoreBaseDataDTO
extends MidstoreBaseDataDO {
    private static final long serialVersionUID = 1L;

    public static MidstoreBaseDataDTO valueOf(IMidstoreBaseData o) {
        if (o == null) {
            return null;
        }
        if (o instanceof MidstoreBaseDataDTO) {
            return (MidstoreBaseDataDTO)o;
        }
        MidstoreBaseDataDTO t = new MidstoreBaseDataDTO();
        BeanUtils.copyProperties(o, t);
        return t;
    }
}

