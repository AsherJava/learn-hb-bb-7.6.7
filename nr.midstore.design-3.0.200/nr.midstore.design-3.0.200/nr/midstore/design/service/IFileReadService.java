/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  nr.midstore.core.definition.db.MidstoreException
 *  nr.midstore.core.definition.dto.MidstorePlanTaskDTO
 */
package nr.midstore.design.service;

import java.util.List;
import nr.midstore.core.definition.db.MidstoreException;
import nr.midstore.core.definition.dto.MidstorePlanTaskDTO;
import nr.midstore.design.domain.CommonParamDTO;
import nr.midstore.design.domain.ExchangeSchemeDTO;
import nr.midstore.design.domain.SchemeBaseDTO;
import nr.midstore.design.domain.SchemeBaseDataDTO;
import nr.midstore.design.domain.SchemeBaseDataFieldDTO;
import nr.midstore.design.domain.SchemeFieldDTO;
import nr.midstore.design.domain.SchemeGroupDTO;
import nr.midstore.design.domain.SchemeInfoDTO;
import nr.midstore.design.domain.SchemeOrgDataFieldDTO;
import nr.midstore.design.domain.SchemeOrgDataItemDTO;
import nr.midstore.design.vo.ExchangeSchemeVO;

public interface IFileReadService {
    public List<SchemeGroupDTO> listAllSchemeGroups();

    public List<SchemeGroupDTO> listRootSchemeGroups();

    public List<SchemeGroupDTO> listSchemeGroupsByParent(String var1);

    public List<SchemeBaseDTO> listAllMidstoreSchemes();

    public List<SchemeBaseDTO> listMidstoreSchemesInGroup(String var1);

    public List<ExchangeSchemeDTO> listSchemesInGroup(String var1);

    public List<ExchangeSchemeVO> listSchemeVOsInGroup(String var1);

    public ExchangeSchemeDTO listMidstoreSchemeByKey(String var1);

    public List<ExchangeSchemeDTO> listMidstoreSchemeByKeys(List<String> var1);

    public List<ExchangeSchemeDTO> listMidstoreSchemeBytablePrefix(String var1);

    public SchemeInfoDTO getMidstoreSchemeInfo(String var1);

    public List<SchemeOrgDataItemDTO> listMidstoreOrgDataByScheme(String var1);

    public List<SchemeOrgDataFieldDTO> listMidstoreOrgDataFieldByScheme(String var1);

    public List<SchemeBaseDataDTO> listMidstoreBaseDataByScheme(String var1);

    public List<SchemeBaseDataFieldDTO> listMidstoreBaseDataFieldByScheme(String var1);

    public List<SchemeBaseDataFieldDTO> listMidstoreBaseDataFieldBySchemeAndBaseData(String var1, String var2);

    public List<CommonParamDTO> listMidstoreDataTableBySheme(String var1);

    public List<SchemeFieldDTO> listMidstoreFieldBySheme(String var1);

    public List<SchemeFieldDTO> listMidstoreFieldByShemeAndTable(String var1, String var2);

    public MidstorePlanTaskDTO getMidstorePlanTask(String var1) throws MidstoreException;

    public boolean existPlanTask(String var1);

    public String getMidstorePlanTaskGroup();

    public String getPlanTaskLogDetail(String var1) throws MidstoreException;
}

