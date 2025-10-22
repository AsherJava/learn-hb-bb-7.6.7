/*
 * Decompiled with CFR 0.152.
 */
package nr.midstore.core.definition.service;

import java.util.List;
import nr.midstore.core.definition.db.MidstoreException;
import nr.midstore.core.definition.dto.MidstoreOrgDataFieldDTO;

public interface IMidstoreOrgDataFieldService {
    public List<MidstoreOrgDataFieldDTO> list(MidstoreOrgDataFieldDTO var1);

    public void add(MidstoreOrgDataFieldDTO var1) throws MidstoreException;

    public void update(MidstoreOrgDataFieldDTO var1) throws MidstoreException;

    public void delete(MidstoreOrgDataFieldDTO var1) throws MidstoreException;

    public void batchAdd(List<MidstoreOrgDataFieldDTO> var1) throws MidstoreException;

    public void batchUpdate(List<MidstoreOrgDataFieldDTO> var1) throws MidstoreException;

    public void batchDelete(List<MidstoreOrgDataFieldDTO> var1) throws MidstoreException;

    public void batchDeleteByKeys(List<String> var1) throws MidstoreException;
}

