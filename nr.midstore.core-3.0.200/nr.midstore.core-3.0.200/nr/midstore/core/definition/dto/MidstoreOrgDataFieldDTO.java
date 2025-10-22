/*
 * Decompiled with CFR 0.152.
 */
package nr.midstore.core.definition.dto;

import nr.midstore.core.definition.IMidstoreOrgDataField;
import nr.midstore.core.internal.definition.MidstoreOrgDataFieldDO;
import org.springframework.beans.BeanUtils;

public class MidstoreOrgDataFieldDTO
extends MidstoreOrgDataFieldDO {
    private static final long serialVersionUID = 1L;

    public static MidstoreOrgDataFieldDTO valueOf(IMidstoreOrgDataField o) {
        if (o == null) {
            return null;
        }
        if (o instanceof MidstoreOrgDataFieldDTO) {
            return (MidstoreOrgDataFieldDTO)o;
        }
        MidstoreOrgDataFieldDTO t = new MidstoreOrgDataFieldDTO();
        BeanUtils.copyProperties(o, t);
        return t;
    }
}

