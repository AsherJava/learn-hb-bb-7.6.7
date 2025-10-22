/*
 * Decompiled with CFR 0.152.
 */
package nr.midstore.core.definition.service;

import java.util.List;
import nr.midstore.core.definition.db.MidstoreException;
import nr.midstore.core.definition.dto.MidstoreSchemeGroupDTO;

public interface IMidstoreSchemeGroupService {
    public List<MidstoreSchemeGroupDTO> list(MidstoreSchemeGroupDTO var1);

    public String add(MidstoreSchemeGroupDTO var1) throws MidstoreException;

    public void update(MidstoreSchemeGroupDTO var1) throws MidstoreException;

    public void delete(MidstoreSchemeGroupDTO var1) throws MidstoreException;

    public void batchAdd(List<MidstoreSchemeGroupDTO> var1) throws MidstoreException;

    public void batchUpdate(List<MidstoreSchemeGroupDTO> var1) throws MidstoreException;

    public void batchDelete(List<MidstoreSchemeGroupDTO> var1) throws MidstoreException;

    public void batchDeleteByKeys(List<String> var1) throws MidstoreException;
}

