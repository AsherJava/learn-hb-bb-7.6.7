/*
 * Decompiled with CFR 0.152.
 */
package nr.midstore.core.definition.db;

import nr.midstore.core.definition.IMidstoreBaseData;
import nr.midstore.core.definition.IMidstoreBaseDataField;
import nr.midstore.core.definition.IMidstoreData;
import nr.midstore.core.definition.IMidstoreField;
import nr.midstore.core.definition.IMidstoreOrgData;
import nr.midstore.core.definition.IMidstoreOrgDataField;
import nr.midstore.core.definition.IMidstoreScheme;
import nr.midstore.core.definition.IMidstoreSchemeGroup;
import nr.midstore.core.definition.IMidstoreSchemeInfo;
import nr.midstore.core.definition.dto.MidstoreBaseDataDTO;
import nr.midstore.core.definition.dto.MidstoreBaseDataFieldDTO;
import nr.midstore.core.definition.dto.MidstoreDataDTO;
import nr.midstore.core.definition.dto.MidstoreFieldDTO;
import nr.midstore.core.definition.dto.MidstoreOrgDataDTO;
import nr.midstore.core.definition.dto.MidstoreOrgDataFieldDTO;
import nr.midstore.core.definition.dto.MidstoreSchemeDTO;
import nr.midstore.core.definition.dto.MidstoreSchemeGroupDTO;
import nr.midstore.core.definition.dto.MidstoreSchemeInfoDTO;

public class Convert {
    public static MidstoreDataDTO mdd2Do(IMidstoreData value) {
        return MidstoreDataDTO.valueOf(value);
    }

    public static MidstoreSchemeDTO mds2Do(IMidstoreScheme value) {
        return MidstoreSchemeDTO.valueOf(value);
    }

    public static MidstoreSchemeGroupDTO mdg2Do(IMidstoreSchemeGroup value) {
        return MidstoreSchemeGroupDTO.valueOf(value);
    }

    public static MidstoreSchemeInfoDTO mdi2Do(IMidstoreSchemeInfo value) {
        return MidstoreSchemeInfoDTO.valueOf(value);
    }

    public static MidstoreOrgDataDTO mdo2Do(IMidstoreOrgData value) {
        return MidstoreOrgDataDTO.valueOf(value);
    }

    public static MidstoreOrgDataFieldDTO mdof2Do(IMidstoreOrgDataField value) {
        return MidstoreOrgDataFieldDTO.valueOf(value);
    }

    public static MidstoreBaseDataDTO mdb2Do(IMidstoreBaseData value) {
        return MidstoreBaseDataDTO.valueOf(value);
    }

    public static MidstoreBaseDataFieldDTO mdbf2Do(IMidstoreBaseDataField value) {
        return MidstoreBaseDataFieldDTO.valueOf(value);
    }

    public static MidstoreFieldDTO mdf2Do(IMidstoreField value) {
        return MidstoreFieldDTO.valueOf(value);
    }
}

