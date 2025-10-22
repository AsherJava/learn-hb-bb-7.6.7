/*
 * Decompiled with CFR 0.152.
 */
package nr.midstore.core.definition.service;

import java.util.List;
import nr.midstore.core.definition.db.MidstoreException;
import nr.midstore.core.definition.dto.MidstoreSchemeInfoDTO;

public interface IMidstoreSchemeInfoService {
    public MidstoreSchemeInfoDTO getByKey(String var1);

    public MidstoreSchemeInfoDTO getBySchemeKey(String var1);

    public List<MidstoreSchemeInfoDTO> list(MidstoreSchemeInfoDTO var1);

    public void add(MidstoreSchemeInfoDTO var1) throws MidstoreException;

    public void update(MidstoreSchemeInfoDTO var1) throws MidstoreException;

    public void delete(MidstoreSchemeInfoDTO var1) throws MidstoreException;

    public void batchAdd(List<MidstoreSchemeInfoDTO> var1) throws MidstoreException;

    public void batchUpdate(List<MidstoreSchemeInfoDTO> var1) throws MidstoreException;

    public void batchDelete(List<MidstoreSchemeInfoDTO> var1) throws MidstoreException;

    public void batchDeleteByKeys(List<String> var1) throws MidstoreException;
}

