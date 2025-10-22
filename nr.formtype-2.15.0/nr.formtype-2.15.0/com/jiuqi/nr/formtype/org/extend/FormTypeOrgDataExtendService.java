/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.org.OrgBatchOptDTO
 *  com.jiuqi.va.domain.org.OrgDO
 *  com.jiuqi.va.domain.org.OrgDTO
 *  com.jiuqi.va.domain.org.OrgDataOption$AuthType
 *  com.jiuqi.va.domain.org.ZB
 *  com.jiuqi.va.feign.client.OrgDataClient
 */
package com.jiuqi.nr.formtype.org.extend;

import com.jiuqi.nr.formtype.common.FormTypeConsts;
import com.jiuqi.nr.formtype.common.FormTypeUtils;
import com.jiuqi.nr.formtype.common.UnitNature;
import com.jiuqi.nr.formtype.common.UnitNatureGetter;
import com.jiuqi.nr.formtype.facade.FormTypeDataDefine;
import com.jiuqi.nr.formtype.org.extend.FormTypeOrgDataCheckService;
import com.jiuqi.nr.formtype.org.extend.FormTypeOrgDataHelper;
import com.jiuqi.nr.formtype.service.IFormTypeApplyService;
import com.jiuqi.nr.formtype.service.IFormTypeService;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.org.OrgBatchOptDTO;
import com.jiuqi.va.domain.org.OrgDO;
import com.jiuqi.va.domain.org.OrgDTO;
import com.jiuqi.va.domain.org.OrgDataOption;
import com.jiuqi.va.domain.org.ZB;
import com.jiuqi.va.feign.client.OrgDataClient;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class FormTypeOrgDataExtendService {
    @Autowired
    private IFormTypeApplyService iFormTypeApplyService;
    @Autowired
    private OrgDataClient orgDataClient;
    @Autowired
    private FormTypeOrgDataHelper formTypeOrgHelper;
    @Autowired
    private IFormTypeService iFormTypeService;
    @Autowired
    private FormTypeOrgDataCheckService formTypeOrgDataCheckService;
    private final Logger logger = LoggerFactory.getLogger(FormTypeOrgDataExtendService.class);

    private void doCompInfo(OrgDO orgDO, OrgDO oldOrgDO) {
        if (!StringUtils.hasText(orgDO.getOrgcode())) {
            if (null == oldOrgDO) {
                orgDO.setOrgcode(orgDO.getCode());
            } else {
                orgDO.setOrgcode(oldOrgDO.getOrgcode());
            }
        }
    }

    private OrgDO getFullOrgDO(OrgDO orgDO, OrgDO oldOrgDO) {
        this.doCompInfo(orgDO, oldOrgDO);
        OrgDO fullOrgDO = new OrgDO((Map)oldOrgDO);
        fullOrgDO.putAll((Map)orgDO);
        return fullOrgDO;
    }

    public void doImportExtends(ZB formTypeZb, List<OrgDO> orgDatas) {
        UnitNatureGetter unitNatureGetter = this.iFormTypeApplyService.getUnitNatureGetter(formTypeZb.getReltablename());
        for (OrgDO orgData : orgDatas) {
            this.doImportExtends(formTypeZb, unitNatureGetter, this.formTypeOrgHelper.getParentGetter(orgDatas), orgData);
        }
    }

    public void doImportExtends(ZB formTypeZb, OrgDO orgData) {
        UnitNatureGetter unitNatureGetter = this.iFormTypeApplyService.getUnitNatureGetter(formTypeZb.getReltablename());
        this.doImportExtends(formTypeZb, unitNatureGetter, this.formTypeOrgHelper.getParentGetter(null), orgData);
    }

    private void doImportExtends(ZB formTypeZb, UnitNatureGetter unitNatureGetter, Function<OrgDO, OrgDO> parentGetter, OrgDO orgData) {
        if (FormTypeConsts.ORG_EXTEND_IMPORTSTATE_ERRVALUE.equals(orgData.get((Object)"importstate"))) {
            return;
        }
        OrgDO parentOrgData = parentGetter.apply(orgData);
        if (this.formTypeOrgDataCheckService.isAutoGenUnit(unitNatureGetter, formTypeZb, orgData, parentOrgData)) {
            orgData.put("ignoreCodeRefImp", (Object)true);
            orgData.put("ignoreCategoryAdd", (Object)true);
        }
        OrgDO oldOrgData = this.formTypeOrgHelper.getOldOrgData(orgData);
        this.doCompInfo(orgData, oldOrgData);
        if (UnitNature.JTHZB == unitNatureGetter.getUnitNature(orgData, formTypeZb) && null != oldOrgData && !orgData.getOrgcode().equals(oldOrgData.getOrgcode())) {
            this.doUpdateChildrenOrgcode(formTypeZb, unitNatureGetter, orgData);
        }
    }

    public void doAddExtends(ZB formTypeZb, List<OrgDO> orgDatas) {
        UnitNatureGetter unitNatureGetter = this.iFormTypeApplyService.getUnitNatureGetter(formTypeZb.getReltablename());
        for (OrgDO orgData : orgDatas) {
            if (UnitNature.JTHZB != unitNatureGetter.getUnitNature(orgData, formTypeZb)) continue;
            this.doCompInfo(orgData, null);
            this.doAutoSyncUnit(formTypeZb, orgData);
        }
    }

    public void doAddExtends(ZB formTypeZb, OrgDO orgData) {
        UnitNature unitNature = this.getUnitNature(orgData, formTypeZb);
        if (UnitNature.JTHZB == unitNature) {
            this.doCompInfo(orgData, null);
            this.doAutoSyncUnit(formTypeZb, orgData);
        }
    }

    private UnitNature getUnitNature(OrgDO orgDO, ZB formTypeZb) {
        String formTypeDataCode = FormTypeUtils.getZbValue(orgDO, formTypeZb);
        FormTypeDataDefine formTypeData = this.iFormTypeService.queryFormTypeData(formTypeZb.getReltablename(), formTypeDataCode);
        if (null == formTypeData) {
            return null;
        }
        return formTypeData.getUnitNatrue();
    }

    public void doUpdateExtends(ZB formTypeZb, List<OrgDO> orgDatas, Function<OrgDO, OrgDO> oldOrgDataGetter) {
        if (null == oldOrgDataGetter) {
            oldOrgDataGetter = data -> this.formTypeOrgHelper.getOldOrgData((OrgDO)data);
        }
        UnitNatureGetter unitNatureGetter = this.iFormTypeApplyService.getUnitNatureGetter(formTypeZb.getReltablename());
        for (OrgDO orgData : orgDatas) {
            OrgDO oldOrgData = oldOrgDataGetter.apply(orgData);
            if (null == oldOrgData) {
                this.doAddExtends(formTypeZb, orgData);
                continue;
            }
            this.doUpdateExtends(formTypeZb, unitNatureGetter, orgData, oldOrgData);
        }
    }

    public void doUpdateExtends(ZB formTypeZb, OrgDO orgData, OrgDO oldOrgData) {
        UnitNatureGetter unitNatureGetter = this.iFormTypeApplyService.getUnitNatureGetter(formTypeZb.getReltablename());
        this.doUpdateExtends(formTypeZb, unitNatureGetter, orgData, oldOrgData);
    }

    private void doUpdateExtends(ZB formTypeZb, UnitNatureGetter unitNatureGetter, OrgDO orgData, OrgDO oldOrgData) {
        UnitNature oldUnitNature;
        OrgDO fullOrgData = this.getFullOrgDO(orgData, oldOrgData);
        UnitNature unitNature = unitNatureGetter.getUnitNature(orgData, formTypeZb);
        if (unitNature == (oldUnitNature = unitNatureGetter.getUnitNature(oldOrgData, formTypeZb))) {
            if (UnitNature.JTHZB == unitNatureGetter.getUnitNature(orgData, formTypeZb) && !orgData.getOrgcode().equals(oldOrgData.getOrgcode())) {
                this.doUpdateChildrenOrgcode(formTypeZb, unitNatureGetter, orgData);
            }
        } else if (UnitNature.JTHZB == unitNature) {
            this.doAutoAddUnit(formTypeZb, fullOrgData);
        } else if (UnitNature.JTHZB == oldUnitNature) {
            this.logger.info("\u62a5\u8868\u7c7b\u578b\u6269\u5c55\u670d\u52a1\uff1a{}[{}]\u96c6\u56e2\u6c47\u603b\u5355\u4f4d\u88ab\u4fee\u6539\u4e3a{}\u3002", new Object[]{orgData.getName(), orgData.getOrgcode(), unitNature});
        }
    }

    private void doUpdateChildrenOrgcode(ZB formTypeZb, UnitNatureGetter unitNatureGetter, OrgDO unitData) {
        for (FormTypeDataDefine needAddFormType : unitNatureGetter.getNeedAddFormTypes()) {
            String autoGenCode = this.iFormTypeApplyService.getAutoGenUnitCode(unitData.getCode(), needAddFormType.getUnitNatrue());
            OrgDO needUpdateOrgData = this.formTypeOrgHelper.getOrgDataByCode(unitData.getCategoryname(), autoGenCode, FormTypeOrgDataHelper.getVersionDate((Map<String, Object>)unitData));
            if (null == needUpdateOrgData) continue;
            OrgDTO orgDTO = new OrgDTO();
            orgDTO.setCategoryname(unitData.getCategoryname());
            orgDTO.setVersionDate((Date)unitData.get((Object)"versionDate"));
            orgDTO.setCode(autoGenCode);
            orgDTO.setAuthType(OrgDataOption.AuthType.NONE);
            orgDTO.setForceUpdateHistoryVersionData(Boolean.valueOf(true));
            orgDTO.setOrgcode(unitData.getOrgcode());
            orgDTO.put(formTypeZb.getName().toLowerCase(), needUpdateOrgData.get((Object)formTypeZb.getName().toLowerCase()));
            orgDTO.setParents(unitData.getParents() + "/" + autoGenCode);
            orgDTO.setParentcode(unitData.getCode());
            orgDTO.put("ignoreCategoryAdd", (Object)true);
            this.logger.info("\u62a5\u8868\u7c7b\u578b\u6269\u5c55\u670d\u52a1\uff1a\u66f4\u65b0\u81ea\u52a8\u751f\u6210\u5355\u4f4d\u673a\u6784\u7f16\u7801{}.", (Object)orgDTO);
            R rs = this.orgDataClient.update(orgDTO);
            if (rs.getCode() == 0) continue;
            this.logger.error("\u62a5\u8868\u7c7b\u578b\u6269\u5c55\u670d\u52a1:\u66f4\u65b0\u81ea\u52a8\u751f\u6210\u5355\u4f4d\u673a\u6784\u7f16\u7801\u5931\u8d25\uff1a{}", (Object)rs);
        }
    }

    private void doAutoAddUnit(ZB formTypeZb, OrgDO unitData) {
        UnitNatureGetter unitNatureGetter = this.iFormTypeApplyService.getUnitNatureGetter(formTypeZb.getReltablename());
        for (FormTypeDataDefine needAddFormType : unitNatureGetter.getNeedAddFormTypes()) {
            String autoGenCode = this.iFormTypeApplyService.getAutoGenUnitCode(unitData.getCode(), needAddFormType.getUnitNatrue());
            OrgDO oldOrgData = this.formTypeOrgHelper.getOrgDataByCode(unitData.getCategoryname(), autoGenCode, FormTypeOrgDataHelper.getVersionDate((Map<String, Object>)unitData));
            if (null != oldOrgData) {
                this.logger.info("\u62a5\u8868\u7c7b\u578b\u6269\u5c55\u670d\u52a1\uff1a\u81ea\u52a8\u65b0\u589e\u5355\u4f4d\u65f6\uff0c{}\u5355\u4f4d\u5df2\u5b58\u5728.", (Object)needAddFormType.getName());
                continue;
            }
            this.doAutoAddUnit(autoGenCode, needAddFormType, formTypeZb, unitData);
        }
    }

    private void doAutoAddUnit(String autoGenCode, FormTypeDataDefine needAddFormType, ZB formTypeZb, OrgDO unitData) {
        OrgDTO autoGenOrgDO = new OrgDTO();
        autoGenOrgDO.putAll((Map)unitData);
        autoGenOrgDO.setId(UUID.randomUUID());
        autoGenOrgDO.setCode(autoGenCode);
        autoGenOrgDO.setParentcode(unitData.getCode());
        autoGenOrgDO.put(formTypeZb.getName().toLowerCase(), (Object)needAddFormType.getCode());
        if (UnitNature.JTCEB == needAddFormType.getUnitNatrue()) {
            autoGenOrgDO.setOrdinal(BigDecimal.ZERO);
            autoGenOrgDO.setName(unitData.getName() + "\uff08\u5dee\u989d\uff09");
        } else {
            autoGenOrgDO.setOrdinal(BigDecimal.ONE);
            autoGenOrgDO.setName(unitData.getName() + "\uff08\u672c\u90e8\uff09");
        }
        autoGenOrgDO.put("ignoreCategoryAdd", (Object)true);
        autoGenOrgDO.setSyncOrgBaseInfo(Boolean.valueOf(true));
        this.logger.info("\u62a5\u8868\u7c7b\u578b\u6269\u5c55\u670d\u52a1\uff1a\u81ea\u52a8\u65b0\u589e\u5355\u4f4d{}.", (Object)autoGenOrgDO);
        R rs = this.orgDataClient.add(autoGenOrgDO);
        if (rs.getCode() != 0) {
            this.logger.error("\u62a5\u8868\u7c7b\u578b\u6269\u5c55\u670d\u52a1:\u81ea\u52a8\u751f\u6210\u5355\u4f4d\u5931\u8d25\uff1a{}", (Object)rs);
        }
    }

    private void doAutoSyncUnit(ZB formTypeZb, OrgDO unitData) {
        R rs;
        OrgBatchOptDTO opt = new OrgBatchOptDTO();
        OrgDTO queryParam = new OrgDTO();
        queryParam.setCategoryname(unitData.getCategoryname());
        queryParam.setAuthType(OrgDataOption.AuthType.NONE);
        queryParam.setVer(unitData.getVer());
        queryParam.setVersionDate(FormTypeOrgDataHelper.getVersionDate((Map<String, Object>)unitData));
        queryParam.setForceUpdateHistoryVersionData(Boolean.valueOf(true));
        opt.setQueryParam(queryParam);
        opt.setFullFieldOverride(true);
        opt.setDataList(new ArrayList());
        UnitNatureGetter unitNatureGetter = this.iFormTypeApplyService.getUnitNatureGetter(formTypeZb.getReltablename());
        for (FormTypeDataDefine needAddFormType : unitNatureGetter.getNeedAddFormTypes()) {
            String autoGenCode = this.iFormTypeApplyService.getAutoGenUnitCode(unitData.getCode(), needAddFormType.getUnitNatrue());
            OrgDO oldOrgData = this.formTypeOrgHelper.getOrgDataByCode(unitData.getCategoryname(), autoGenCode, FormTypeOrgDataHelper.getVersionDate((Map<String, Object>)unitData));
            if (null != oldOrgData) {
                this.logger.info("\u62a5\u8868\u7c7b\u578b\u6269\u5c55\u670d\u52a1\uff1a\u81ea\u52a8\u65b0\u589e\u5355\u4f4d\u65f6\uff0c{}\u5355\u4f4d\u5df2\u5b58\u5728.", (Object)needAddFormType.getName());
                continue;
            }
            opt.getDataList().add(this.doAutoSyncUnit(autoGenCode, needAddFormType, formTypeZb, unitData));
        }
        if (!opt.getDataList().isEmpty() && (rs = this.orgDataClient.sync(opt)).getCode() != 0) {
            this.logger.error("\u62a5\u8868\u7c7b\u578b\u6269\u5c55\u670d\u52a1:\u81ea\u52a8\u751f\u6210\u5355\u4f4d\u5931\u8d25\uff1a{}", (Object)rs);
        }
    }

    private OrgDTO doAutoSyncUnit(String autoGenCode, FormTypeDataDefine needAddFormType, ZB formTypeZb, OrgDO unitData) {
        OrgDTO autoGenOrgDO = new OrgDTO();
        autoGenOrgDO.putAll((Map)unitData);
        autoGenOrgDO.setId(UUID.randomUUID());
        autoGenOrgDO.setCode(autoGenCode);
        autoGenOrgDO.setParentcode(unitData.getCode());
        autoGenOrgDO.setParents(unitData.getParents() + "/" + autoGenCode);
        autoGenOrgDO.put(formTypeZb.getName().toLowerCase(), (Object)needAddFormType.getCode());
        if (UnitNature.JTCEB == needAddFormType.getUnitNatrue()) {
            autoGenOrgDO.setOrdinal(BigDecimal.ZERO);
            autoGenOrgDO.setName(unitData.getName() + "\uff08\u5dee\u989d\uff09");
        } else {
            autoGenOrgDO.setOrdinal(BigDecimal.ONE);
            autoGenOrgDO.setName(unitData.getName() + "\uff08\u672c\u90e8\uff09");
        }
        autoGenOrgDO.put("ignoreCategoryAdd", (Object)true);
        autoGenOrgDO.setSyncOrgBaseInfo(Boolean.valueOf(true));
        this.logger.info("\u62a5\u8868\u7c7b\u578b\u6269\u5c55\u670d\u52a1\uff1a\u81ea\u52a8\u65b0\u589e\u5355\u4f4d{}.", (Object)autoGenOrgDO);
        return autoGenOrgDO;
    }
}

