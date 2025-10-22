/*
 * Decompiled with CFR 0.152.
 */
package nr.midstore.core.definition.dto;

import nr.midstore.core.definition.IMidstoreSchemeInfo;
import nr.midstore.core.internal.definition.MidstoreSchemeInfoDO;
import org.springframework.beans.BeanUtils;

public class MidstoreSchemeInfoDTO
extends MidstoreSchemeInfoDO {
    private static final long serialVersionUID = 1L;

    public static MidstoreSchemeInfoDTO valueOf(IMidstoreSchemeInfo o) {
        if (o == null) {
            return null;
        }
        if (o instanceof MidstoreSchemeInfoDTO) {
            return (MidstoreSchemeInfoDTO)o;
        }
        MidstoreSchemeInfoDTO t = new MidstoreSchemeInfoDTO();
        BeanUtils.copyProperties(o, t);
        return t;
    }
}

