/*
 * Decompiled with CFR 0.152.
 */
package nr.midstore.core.definition.dto;

import nr.midstore.core.definition.IMidstoreData;
import nr.midstore.core.internal.definition.MidstoreSchemeDO;
import org.springframework.beans.BeanUtils;

public class MidstoreSchemeDTO
extends MidstoreSchemeDO {
    private static final long serialVersionUID = 1L;

    public static MidstoreSchemeDTO valueOf(IMidstoreData o) {
        if (o == null) {
            return null;
        }
        if (o instanceof MidstoreSchemeDTO) {
            return (MidstoreSchemeDTO)o;
        }
        MidstoreSchemeDTO t = new MidstoreSchemeDTO();
        BeanUtils.copyProperties(o, t);
        return t;
    }
}

