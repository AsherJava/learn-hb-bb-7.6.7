/*
 * Decompiled with CFR 0.152.
 */
package nr.midstore.core.definition.dto;

import nr.midstore.core.definition.IMidstoreSchemeGroup;
import nr.midstore.core.internal.definition.MidstoreSchemeGroupDO;
import org.springframework.beans.BeanUtils;

public class MidstoreSchemeGroupDTO
extends MidstoreSchemeGroupDO {
    private static final long serialVersionUID = 1L;

    public static MidstoreSchemeGroupDTO valueOf(IMidstoreSchemeGroup o) {
        if (o == null) {
            return null;
        }
        if (o instanceof MidstoreSchemeGroupDTO) {
            return (MidstoreSchemeGroupDTO)o;
        }
        MidstoreSchemeGroupDTO t = new MidstoreSchemeGroupDTO();
        BeanUtils.copyProperties(o, t);
        return t;
    }
}

