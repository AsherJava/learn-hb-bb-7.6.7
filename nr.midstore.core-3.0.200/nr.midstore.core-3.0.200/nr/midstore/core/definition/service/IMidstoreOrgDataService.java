/*
 * Decompiled with CFR 0.152.
 */
package nr.midstore.core.definition.service;

import java.util.List;
import nr.midstore.core.definition.db.MidstoreException;
import nr.midstore.core.definition.dto.MidstoreOrgDataDTO;

public interface IMidstoreOrgDataService {
    public List<MidstoreOrgDataDTO> list(MidstoreOrgDataDTO var1);

    public void add(MidstoreOrgDataDTO var1) throws MidstoreException;

    public void update(MidstoreOrgDataDTO var1) throws MidstoreException;

    public void delete(MidstoreOrgDataDTO var1) throws MidstoreException;

    public void batchAdd(List<MidstoreOrgDataDTO> var1) throws MidstoreException;

    public void batchUpdate(List<MidstoreOrgDataDTO> var1) throws MidstoreException;

    public void batchDelete(List<MidstoreOrgDataDTO> var1) throws MidstoreException;

    public void batchDeleteByKeys(List<String> var1) throws MidstoreException;
}

