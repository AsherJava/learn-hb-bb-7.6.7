/*
 * Decompiled with CFR 0.152.
 */
package nr.midstore.core.definition.service;

import java.util.List;
import nr.midstore.core.definition.db.MidstoreException;
import nr.midstore.core.definition.dto.MidstoreBaseDataFieldDTO;

public interface IMidstoreBaseDataFieldService {
    public List<MidstoreBaseDataFieldDTO> list(MidstoreBaseDataFieldDTO var1);

    public void add(MidstoreBaseDataFieldDTO var1) throws MidstoreException;

    public void update(MidstoreBaseDataFieldDTO var1) throws MidstoreException;

    public void delete(MidstoreBaseDataFieldDTO var1) throws MidstoreException;

    public void batchAdd(List<MidstoreBaseDataFieldDTO> var1) throws MidstoreException;

    public void batchUpdate(List<MidstoreBaseDataFieldDTO> var1) throws MidstoreException;

    public void batchDelete(List<MidstoreBaseDataFieldDTO> var1) throws MidstoreException;

    public void batchDeleteByKeys(List<String> var1) throws MidstoreException;
}

