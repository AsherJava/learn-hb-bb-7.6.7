/*
 * Decompiled with CFR 0.152.
 */
package nr.midstore.core.definition.service;

import java.util.List;
import nr.midstore.core.definition.db.MidstoreException;
import nr.midstore.core.definition.dto.MidstoreBaseDataDTO;

public interface IMidstoreBaseDataService {
    public List<MidstoreBaseDataDTO> list(MidstoreBaseDataDTO var1);

    public void add(MidstoreBaseDataDTO var1) throws MidstoreException;

    public void update(MidstoreBaseDataDTO var1) throws MidstoreException;

    public void delete(MidstoreBaseDataDTO var1) throws MidstoreException;

    public void batchAdd(List<MidstoreBaseDataDTO> var1) throws MidstoreException;

    public void batchUpdate(List<MidstoreBaseDataDTO> var1) throws MidstoreException;

    public void batchDelete(List<MidstoreBaseDataDTO> var1) throws MidstoreException;

    public void batchDeleteByKeys(List<String> var1) throws MidstoreException;
}

