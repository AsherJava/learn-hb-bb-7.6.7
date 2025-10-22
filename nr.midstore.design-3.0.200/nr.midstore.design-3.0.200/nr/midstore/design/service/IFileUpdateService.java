/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  nr.midstore.core.definition.bean.MidstoreResultObject
 *  nr.midstore.core.definition.db.MidstoreException
 *  nr.midstore.core.definition.dto.MidstorePlanTaskDTO
 */
package nr.midstore.design.service;

import java.util.List;
import nr.midstore.core.definition.bean.MidstoreResultObject;
import nr.midstore.core.definition.db.MidstoreException;
import nr.midstore.core.definition.dto.MidstorePlanTaskDTO;
import nr.midstore.design.domain.ExchangeSchemeDTO;
import nr.midstore.design.domain.SchemeBaseDTO;
import nr.midstore.design.domain.SchemeBaseDataDTO;
import nr.midstore.design.domain.SchemeBaseDataFieldDTO;
import nr.midstore.design.domain.SchemeFieldDTO;
import nr.midstore.design.domain.SchemeGroupDTO;
import nr.midstore.design.domain.SchemeInfoDTO;
import nr.midstore.design.domain.SchemeOrgDataFieldDTO;
import nr.midstore.design.domain.SchemeOrgDataItemDTO;

public interface IFileUpdateService {
    public MidstoreResultObject saveMidstoreScheme(SchemeBaseDTO var1) throws MidstoreException;

    public MidstoreResultObject saveMidstoreScheme(ExchangeSchemeDTO var1) throws MidstoreException;

    public MidstoreResultObject deleteMidstoreScheme(String var1) throws MidstoreException;

    public MidstoreResultObject saveSchemeGroup(SchemeGroupDTO var1) throws MidstoreException;

    public MidstoreResultObject deleteMidstoreSchemeGroup(String var1) throws MidstoreException;

    public MidstoreResultObject saveSchemeInfo(SchemeInfoDTO var1) throws MidstoreException;

    public MidstoreResultObject updateSchemeUsePlanTask(String var1, boolean var2) throws MidstoreException;

    public MidstoreResultObject addDefaultSchemeInfo(String var1) throws MidstoreException;

    public MidstoreResultObject saveSchemeOrgDataItems(String var1, List<SchemeOrgDataItemDTO> var2) throws MidstoreException;

    public MidstoreResultObject saveSchemeOrgDataFields(String var1, List<SchemeOrgDataFieldDTO> var2) throws MidstoreException;

    public MidstoreResultObject saveSchemeBaseDatas(String var1, List<SchemeBaseDataDTO> var2) throws MidstoreException;

    public MidstoreResultObject saveSchemeBaseDataFields(String var1, List<SchemeBaseDataFieldDTO> var2) throws MidstoreException;

    public MidstoreResultObject saveSchemeBaseDataFields(String var1, String var2, List<SchemeBaseDataFieldDTO> var3) throws MidstoreException;

    public MidstoreResultObject saveSchemFields(String var1, List<SchemeFieldDTO> var2) throws MidstoreException;

    public MidstoreResultObject saveSchemFields(String var1, String var2, List<SchemeFieldDTO> var3) throws MidstoreException;

    public MidstoreResultObject deleteOrgDataItemsByScheme(String var1) throws MidstoreException;

    public MidstoreResultObject deleteOrgDataItemsByKeys(List<String> var1) throws MidstoreException;

    public MidstoreResultObject deleteOrgDataFieldsByScheme(String var1) throws MidstoreException;

    public MidstoreResultObject deleteOrgDataFieldsByKeys(List<String> var1) throws MidstoreException;

    public MidstoreResultObject deleteBaseDatasByScheme(String var1) throws MidstoreException;

    public MidstoreResultObject deleteBaseDatasByKeys(List<String> var1) throws MidstoreException;

    public MidstoreResultObject deleteBaseDataFieldsByScheme(String var1) throws MidstoreException;

    public MidstoreResultObject deleteBaseDataFieldsBySchemeAndBaseData(String var1, String var2) throws MidstoreException;

    public MidstoreResultObject deleteBaseDataFieldsByKeys(List<String> var1) throws MidstoreException;

    public MidstoreResultObject deleteFieldsByScheme(String var1) throws MidstoreException;

    public MidstoreResultObject deleteFieldsByKeys(List<String> var1) throws MidstoreException;

    public MidstoreResultObject savePlanTask(MidstorePlanTaskDTO var1) throws MidstoreException;
}

