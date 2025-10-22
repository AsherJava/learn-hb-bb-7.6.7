/*
 * Decompiled with CFR 0.152.
 */
package nr.midstore.core.definition.dto;

import nr.midstore.core.definition.IMidstoreOrgData;
import nr.midstore.core.internal.definition.MidstoreOrgDataDO;
import org.springframework.beans.BeanUtils;

public class MidstoreOrgDataDTO
extends MidstoreOrgDataDO {
    private static final long serialVersionUID = 1L;

    public static MidstoreOrgDataDTO valueOf(IMidstoreOrgData o) {
        if (o == null) {
            return null;
        }
        if (o instanceof MidstoreOrgDataDTO) {
            return (MidstoreOrgDataDTO)o;
        }
        MidstoreOrgDataDTO t = new MidstoreOrgDataDTO();
        BeanUtils.copyProperties(o, t);
        return t;
    }
}

