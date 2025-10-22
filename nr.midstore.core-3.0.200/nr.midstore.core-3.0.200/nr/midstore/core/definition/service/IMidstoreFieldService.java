/*
 * Decompiled with CFR 0.152.
 */
package nr.midstore.core.definition.service;

import java.util.List;
import nr.midstore.core.definition.db.MidstoreException;
import nr.midstore.core.definition.dto.MidstoreFieldDTO;

public interface IMidstoreFieldService {
    public List<MidstoreFieldDTO> list(MidstoreFieldDTO var1);

    public void add(MidstoreFieldDTO var1) throws MidstoreException;

    public void update(MidstoreFieldDTO var1) throws MidstoreException;

    public void delete(MidstoreFieldDTO var1) throws MidstoreException;

    public void batchAdd(List<MidstoreFieldDTO> var1) throws MidstoreException;

    public void batchUpdate(List<MidstoreFieldDTO> var1) throws MidstoreException;

    public void batchDelete(List<MidstoreFieldDTO> var1) throws MidstoreException;

    public void batchDeleteByKeys(List<String> var1) throws MidstoreException;
}

