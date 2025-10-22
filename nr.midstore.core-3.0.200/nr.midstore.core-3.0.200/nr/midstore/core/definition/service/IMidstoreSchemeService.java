/*
 * Decompiled with CFR 0.152.
 */
package nr.midstore.core.definition.service;

import java.util.List;
import nr.midstore.core.definition.db.MidstoreException;
import nr.midstore.core.definition.dto.MidstoreSchemeDTO;

public interface IMidstoreSchemeService {
    public MidstoreSchemeDTO getByKey(String var1);

    public MidstoreSchemeDTO getByCode(String var1);

    public List<MidstoreSchemeDTO> list(MidstoreSchemeDTO var1);

    public List<MidstoreSchemeDTO> list(List<String> var1);

    public void add(MidstoreSchemeDTO var1) throws MidstoreException;

    public void update(MidstoreSchemeDTO var1) throws MidstoreException;

    public void delete(MidstoreSchemeDTO var1) throws MidstoreException;

    public void batchAdd(List<MidstoreSchemeDTO> var1) throws MidstoreException;

    public void batchUpdate(List<MidstoreSchemeDTO> var1) throws MidstoreException;

    public void batchDelete(List<MidstoreSchemeDTO> var1) throws MidstoreException;

    public void batchDeleteByKeys(List<String> var1) throws MidstoreException;
}

