/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.basedata.BaseDataDO
 *  com.jiuqi.va.domain.basedata.BaseDataDTO
 *  com.jiuqi.va.domain.basedata.BaseDataDefineDO
 *  com.jiuqi.va.domain.common.OrderNumUtil
 *  com.jiuqi.va.domain.datamodel.DataModelType
 *  com.jiuqi.va.domain.datamodel.DataModelType$ColumnType
 *  com.jiuqi.va.domain.enumdata.EnumDataDO
 *  com.jiuqi.va.domain.org.OrgDO
 *  com.jiuqi.va.domain.org.OrgDTO
 *  com.jiuqi.va.domain.org.OrgDataOption$AuthType
 *  com.jiuqi.va.domain.org.ZB
 *  com.jiuqi.va.domain.user.UserDO
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 */
package com.jiuqi.va.organization.common;

import com.jiuqi.va.domain.basedata.BaseDataDO;
import com.jiuqi.va.domain.basedata.BaseDataDTO;
import com.jiuqi.va.domain.basedata.BaseDataDefineDO;
import com.jiuqi.va.domain.common.OrderNumUtil;
import com.jiuqi.va.domain.datamodel.DataModelType;
import com.jiuqi.va.domain.enumdata.EnumDataDO;
import com.jiuqi.va.domain.org.OrgDO;
import com.jiuqi.va.domain.org.OrgDTO;
import com.jiuqi.va.domain.org.OrgDataOption;
import com.jiuqi.va.domain.org.ZB;
import com.jiuqi.va.domain.user.UserDO;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import com.jiuqi.va.organization.common.DataQueryUtil;
import com.jiuqi.va.organization.common.FormatValidationUtil;
import com.jiuqi.va.organization.domain.ZBDTO;
import com.jiuqi.va.organization.service.impl.help.OrgDataModifyService;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

public class OrgBatchHandleUtil {
    private static Logger logger = LoggerFactory.getLogger(OrgBatchHandleUtil.class);
    @Autowired
    private OrgDataModifyService orgDataModifyService = (OrgDataModifyService)ApplicationContextRegister.getBean(OrgDataModifyService.class);
    private OrgDTO orgDataDTO;
    private String category;
    private OrgDataOption.AuthType authType;
    private List<ZBDTO> zbList;
    private List<OrgDTO> dataList;
    private DataQueryUtil dataQueryUtil;

    public OrgBatchHandleUtil(String tenantName, OrgDTO orgDataDTO, List<ZBDTO> zbList, List<OrgDTO> dataList) {
        this.orgDataDTO = orgDataDTO;
        this.category = orgDataDTO.getCategoryname();
        this.zbList = zbList;
        this.dataList = dataList;
        this.authType = orgDataDTO.getAuthType() == null ? OrgDataOption.AuthType.MANAGE : orgDataDTO.getAuthType();
        this.dataQueryUtil = new DataQueryUtil(tenantName);
    }

    public List<BaseDataDTO> importOrg() {
        ArrayList<BaseDataDTO> importRecords = new ArrayList<BaseDataDTO>();
        this.orgDataDTO.getCategoryname();
        for (OrgDTO newOrg : this.dataList) {
            BaseDataDTO importRecord = new BaseDataDTO();
            if (!importRecord.containsKey((Object)"ImportState")) {
                importRecord.put("ImportState", (Object)0);
            }
            importRecord.put("orgData", (Object)newOrg);
            newOrg.setCategoryname(this.orgDataDTO.getCategoryname());
            newOrg.setVersionDate(this.orgDataDTO.getVersionDate());
            this.checkFieldValueValid(newOrg, importRecord);
            this.checkRequird(newOrg, importRecord);
            this.checkReg(newOrg, importRecord);
            this.checkExist(newOrg, importRecord);
            this.checkStruct(newOrg, importRecord);
            this.checkRelation(newOrg, importRecord);
            this.checkUnique(newOrg, importRecord);
            this.executeUpdate(newOrg, importRecord);
            importRecords.add(importRecord);
        }
        return importRecords;
    }

    private void checkFieldValueValid(OrgDTO newData, BaseDataDTO importRecord) {
        for (ZBDTO column : this.zbList) {
            Object value = newData.getValueOf(column.getName());
            if (value == null || !this.isDefaultRefField(column.getRelatetype(), column.getRelfieldname())) continue;
            String baseInfo = column.getTitle() + "(" + column.getName() + ")";
            int valLength = value.toString().length();
            Integer precision = column.getPrecision();
            if (column.getDatatype() == DataModelType.columnTypeToModelType((DataModelType.ColumnType)DataModelType.ColumnType.NVARCHAR)) {
                if (valLength <= precision) continue;
                this.setImportRecordInfo(importRecord, 1, baseInfo + "\u7684\u957f\u5ea6[" + value.toString().length() + "]\u8d85\u51fa\u5b9a\u4e49\u5141\u8bb8\u7684\u6700\u5927\u957f\u5ea6[" + precision + "]");
                continue;
            }
            if (column.getDatatype() == DataModelType.columnTypeToModelType((DataModelType.ColumnType)DataModelType.ColumnType.NUMERIC)) {
                String[] values = value.toString().split("\\.");
                int decimalCount = 0;
                if (values.length > 1) {
                    if (values[1].length() > column.getDecimal()) {
                        this.setImportRecordInfo(importRecord, 1, baseInfo + "\u7684\u7cbe\u5ea6[" + values[1].length() + "]\u8d85\u51fa\u5b9a\u4e49\u5141\u8bb8\u7684\u6700\u5927\u7cbe\u5ea6[" + column.getDecimal() + "]");
                    }
                    decimalCount = values[1].length();
                }
                if (values.length <= 0 || values[0].length() + decimalCount <= column.getPrecision()) continue;
                this.setImportRecordInfo(importRecord, 1, baseInfo + "\u7684\u957f\u5ea6[" + (values[0].length() + decimalCount) + "]\u8d85\u51fa\u5b9a\u4e49\u5141\u8bb8\u7684\u6700\u5927\u957f\u5ea6[" + precision + "]");
                continue;
            }
            if (column.getDatatype() != DataModelType.columnTypeToModelType((DataModelType.ColumnType)DataModelType.ColumnType.INTEGER)) continue;
            if (valLength > precision) {
                this.setImportRecordInfo(importRecord, 1, baseInfo + "\u7684\u957f\u5ea6[" + value.toString().length() + "]\u8d85\u51fa\u5b9a\u4e49\u5141\u8bb8\u7684\u6700\u5927\u957f\u5ea6[" + precision + "]");
            }
            if (value.toString().compareTo(String.valueOf(Integer.MAX_VALUE)) <= 0) continue;
            this.setImportRecordInfo(importRecord, 1, baseInfo + "\u7684\u503c[" + value.toString() + "]\u8d85\u51fa\u5b9a\u4e49\u5141\u8bb8\u7684\u6700\u5927\u503c[" + Integer.MAX_VALUE + "]");
        }
    }

    private void checkRequird(OrgDTO newOrg, BaseDataDTO importRecord) {
        if (!this.isCurrentLegal(importRecord)) {
            return;
        }
        String categoryName = newOrg.getCategoryname();
        for (ZB zB : this.zbList) {
            if (zB.getRequiredflag() == null || zB.getRequiredflag() != 1) continue;
            String requiedField = zB.getName();
            boolean error = false;
            if (requiedField.equalsIgnoreCase("code")) {
                if (!StringUtils.hasText(newOrg.getCode())) {
                    error = true;
                }
            } else if (requiedField.equalsIgnoreCase("name")) {
                if (categoryName.equals("MD_ORG") && !StringUtils.hasText(newOrg.getName())) {
                    error = true;
                }
            } else if (requiedField.equalsIgnoreCase("shortname")) {
                if (categoryName.equals("MD_ORG") && !StringUtils.hasText(newOrg.getShortname())) {
                    error = true;
                }
            } else if (requiedField.equalsIgnoreCase("parentcode")) {
                if (!StringUtils.hasText(newOrg.getParentcode())) {
                    error = false;
                }
            } else {
                Object value = newOrg.getValueOf(requiedField);
                if (value == null || !StringUtils.hasText(value.toString())) {
                    error = true;
                }
            }
            if (!error) continue;
            String memo = zB.getTitle() + "(" + zB.getName() + ")\u662f\u5fc5\u586b\u9879\uff0c\u4e0d\u80fd\u4e3a\u7a7a";
            this.setImportRecordInfo(importRecord, 2, memo);
        }
    }

    private void checkReg(OrgDTO newOrg, BaseDataDTO importRecord) {
        Object union;
        if (!this.isCurrentLegal(importRecord)) {
            return;
        }
        if (!StringUtils.hasText(newOrg.getCode()) || !FormatValidationUtil.isOrgCode(newOrg.getCode())) {
            this.setImportRecordInfo(importRecord, -2, "\u673a\u6784\u4ee3\u7801[" + newOrg.getCode() + "]\u4e0d\u5141\u8bb8\u5305\u542b\u7279\u6b8a\u5b57\u7b26 ; , /\uff0c\u4e14\u957f\u5ea6\u5e94\u57282-50\u4e4b\u95f4");
        }
        if ((union = newOrg.getValueOf("unioncode")) != null && !union.toString().equals("") && !FormatValidationUtil.isOrgUnion(union.toString())) {
            this.setImportRecordInfo(importRecord, -2, "\u793e\u4f1a\u4fe1\u7528\u4ee3\u7801(\u7a0e\u53f7)[ " + union + " ]\u5e94\u53ea\u5305\u542b\u5b57\u6bcd\u3001\u6570\u5b57");
        }
    }

    private boolean isDefaultRefField(Integer refType, String refField) {
        if (refType == null || !StringUtils.hasText(refField)) {
            return true;
        }
        if (1 == refType && "objectcode".equals(refField)) {
            return true;
        }
        if (3 == refType && "id".equals(refField)) {
            return true;
        }
        if (4 == refType && "code".equals(refField)) {
            return true;
        }
        return 2 == refType && "val".equals(refField);
    }

    private void checkStruct(OrgDTO newOrg, BaseDataDTO importRecord) {
        int errorcode = 6;
        String parentcode = newOrg.getParentcode();
        if (!StringUtils.hasText(parentcode)) {
            newOrg.setParentcode("-");
            return;
        }
        List<OrgDO> orgList = this.dataQueryUtil.getOrg(this.category, OrgDataOption.AuthType.NONE, "code", parentcode);
        List<OrgDO> authList = this.dataQueryUtil.getOrg(this.category, this.authType, "code", parentcode);
        OrgDO orgDO = orgList == null || orgList.isEmpty() ? null : orgList.get(0);
        String baseMemo = "\u4e0a\u7ea7\u673a\u6784(PARENTCODE)\u5173\u8054\u7684\u7ec4\u7ec7\u673a\u6784[" + parentcode + "]";
        if (orgDO == null) {
            this.setImportRecordInfo(importRecord, errorcode, baseMemo + "\u4e0d\u5b58\u5728");
        } else if (orgDO.getRecoveryflag() != null && orgDO.getRecoveryflag() == 1) {
            this.setImportRecordInfo(importRecord, errorcode, baseMemo + "\u5df2\u56de\u6536");
        } else if (orgDO.getStopflag() != null && orgDO.getStopflag() == 1) {
            this.setImportRecordInfo(importRecord, errorcode, baseMemo + "\u5df2\u505c\u7528");
        } else if (authList == null || authList.isEmpty()) {
            this.setImportRecordInfo(importRecord, errorcode, baseMemo + "\u65e0\u6743\u9650");
        }
    }

    private void checkRelation(OrgDTO newOrg, BaseDataDTO importRecord) {
        if (!this.isCurrentLegal(importRecord)) {
            return;
        }
        int errorCode = 7;
        for (ZBDTO zb : this.zbList) {
            EnumDataDO goal;
            Object valueObj;
            boolean isDefaultRefField;
            String fieldName = zb.getName().toLowerCase();
            Integer refType = zb.getRelatetype();
            String refTable = zb.getReltablename();
            String refField = zb.getRelfieldname();
            if (refType == null || !StringUtils.hasText(refTable) || !StringUtils.hasText(refField) || (isDefaultRefField = this.isDefaultRefField(refType, refField = zb.getRelfieldname().toLowerCase())) && !zb.isCheckval() || (valueObj = newOrg.getValueOf(fieldName)) == null) continue;
            String valueStr = valueObj.toString();
            String baseInfo = zb.getTitle() + "(" + zb.getName() + ")\u5173\u8054\u7684";
            if (refType == 4 && (refTable.equals("MD_ORG") || refTable.startsWith("MD_ORG_"))) {
                List<OrgDO> orgList = this.dataQueryUtil.getOrg(refTable, OrgDataOption.AuthType.NONE, refField, valueObj);
                List<OrgDO> authList = this.dataQueryUtil.getOrg(refTable, this.authType, refField, valueObj);
                OrgDO orgDO = orgList == null || orgList.isEmpty() ? null : orgList.get(0);
                String baseMemo = baseInfo + "\u7ec4\u7ec7\u673a\u6784[" + valueStr + "]";
                if (zb.isCheckval()) {
                    if (orgDO == null) {
                        this.setImportRecordInfo(importRecord, errorCode, baseMemo + "\u4e0d\u5b58\u5728");
                    } else if (orgDO.getRecoveryflag() != null && orgDO.getRecoveryflag() == 1) {
                        this.setImportRecordInfo(importRecord, errorCode, baseMemo + "\u5df2\u56de\u6536");
                    } else if (orgDO.getStopflag() != null && orgDO.getStopflag() == 1) {
                        this.setImportRecordInfo(importRecord, errorCode, baseMemo + "\u5df2\u505c\u7528");
                    } else if (authList == null || authList.isEmpty()) {
                        // empty if block
                    }
                }
                if (isDefaultRefField) continue;
                newOrg.put(fieldName.toLowerCase(), (Object)(orgDO == null ? null : orgDO.getCode()));
                continue;
            }
            if (refType == 1 && refTable.startsWith("MD_")) {
                BaseDataDO dataDO;
                BaseDataDefineDO refTableDefine = this.dataQueryUtil.getBasedataDefine(refTable);
                String baseMemo = baseInfo + refTableDefine.getTitle() + "[" + valueStr + "]";
                int shareType = refTableDefine.getSharetype();
                if (shareType != 0 && newOrg.getId() == null && zb.isCheckval()) {
                    this.setImportRecordInfo(importRecord, errorCode, baseMemo + "\u4e0d\u5b58\u5728");
                    continue;
                }
                List<BaseDataDO> dataList = this.dataQueryUtil.getBaseData(refTable, shareType == 0 ? "-" : newOrg.getCode(), refField, valueObj);
                BaseDataDO baseDataDO = dataDO = dataList == null || dataList.isEmpty() ? null : dataList.get(0);
                if (zb.isCheckval()) {
                    if (dataDO == null) {
                        this.setImportRecordInfo(importRecord, errorCode, baseMemo + "\u4e0d\u5b58\u5728");
                    } else if (dataDO.getRecoveryflag() != null && dataDO.getRecoveryflag() == 1) {
                        this.setImportRecordInfo(importRecord, errorCode, baseMemo + "\u5df2\u56de\u6536");
                    } else if (dataDO.getStopflag() != null && dataDO.getStopflag() == 1) {
                        this.setImportRecordInfo(importRecord, errorCode, baseMemo + "\u5df2\u505c\u7528");
                    }
                }
                Map<String, String> showTitleMap = null;
                if (importRecord.containsKey((Object)"showTitleMap")) {
                    showTitleMap = (Map)importRecord.get((Object)"showTitleMap");
                } else {
                    showTitleMap = new HashMap();
                    importRecord.put("showTitleMap", showTitleMap);
                }
                showTitleMap.put(fieldName.toLowerCase(), valueStr);
                if (isDefaultRefField) continue;
                newOrg.put(fieldName, (Object)(dataDO == null ? null : dataDO.getObjectcode()));
                continue;
            }
            if (refType == 3 && refTable.equalsIgnoreCase("AUTH_USER")) {
                List<UserDO> goalList = this.dataQueryUtil.getUser(refField, valueObj);
                UserDO goal2 = goalList == null || goalList.isEmpty() ? null : goalList.get(0);
                String baseMemo = baseInfo + "\u7528\u6237[" + valueStr + "]";
                if (zb.isCheckval()) {
                    if (goal2 == null) {
                        this.setImportRecordInfo(importRecord, errorCode, baseMemo + "\u4e0d\u5b58\u5728");
                    } else if (goal2.getStopflag() != null && goal2.getStopflag() == 1) {
                        this.setImportRecordInfo(importRecord, errorCode, baseMemo + "\u5df2\u505c\u7528");
                    } else if (goal2.getLockflag() == null || goal2.getLockflag() == 1) {
                        // empty if block
                    }
                }
                if (isDefaultRefField) continue;
                newOrg.put(fieldName.toLowerCase(), (Object)(goal2 == null ? null : goal2.getId()));
                continue;
            }
            if (refType != 2 || !refTable.startsWith("EM_")) continue;
            String baseMemo = baseInfo + "\u6570\u636e";
            List<EnumDataDO> goalList = this.dataQueryUtil.getEnumData(refTable, refField, valueObj);
            EnumDataDO enumDataDO = goal = goalList == null || goalList.isEmpty() ? null : goalList.get(0);
            if (zb.isCheckval()) {
                if (goal == null) {
                    this.setImportRecordInfo(importRecord, errorCode, baseMemo + "\u4e0d\u5b58\u5728");
                } else if (goal.getStatus() != null && goal.getStatus() == 1) {
                    this.setImportRecordInfo(importRecord, errorCode, baseMemo + "\u5df2\u505c\u7528");
                }
            }
            if (isDefaultRefField) continue;
            newOrg.put(fieldName.toLowerCase(), (Object)(goal == null ? null : goal.getVal()));
        }
    }

    private void checkUnique(OrgDTO newOrg, BaseDataDTO importRecord) {
        if (!this.isCurrentLegal(importRecord)) {
            return;
        }
        int errorCode = 7;
        for (ZB zB : this.zbList) {
            List<OrgDO> list;
            String fieldName;
            Object value;
            if (zB.getUniqueflag() == null || zB.getUniqueflag() != 1 || (value = newOrg.getValueOf(fieldName = zB.getName())) == null || (list = this.dataQueryUtil.getOrg(newOrg.getCategoryname(), OrgDataOption.AuthType.NONE, fieldName.toLowerCase(), value)) == null || list.size() <= 1) continue;
            String memo = zB.getTitle() + "(" + zB.getName() + ")\u4e0d\u80fd\u91cd\u590d";
            this.setImportRecordInfo(importRecord, errorCode, memo);
        }
    }

    private boolean isCurrentLegal(BaseDataDTO importRecord) {
        int state = (Integer)importRecord.get((Object)"ImportState");
        return state == 0;
    }

    private void executeUpdate(OrgDTO newOrg, BaseDataDTO importRecord) {
        block11: {
            if (!this.isCurrentLegal(importRecord)) {
                return;
            }
            int errorCode = 8;
            newOrg.setRecoveryflag(Integer.valueOf(0));
            newOrg.setVer(OrderNumUtil.getOrderNumByCurrentTimeMillis());
            if (newOrg.getId() == null) {
                newOrg.setId(UUID.randomUUID());
                if (newOrg.getStopflag() == null) {
                    newOrg.setStopflag(Integer.valueOf(0));
                }
                newOrg.setCreatetime(new Date(System.currentTimeMillis()));
                newOrg.setOrdinal(OrderNumUtil.getOrderNumByCurrentTimeMillis());
                try {
                    int flag = this.orgDataModifyService.add(newOrg);
                    if (flag > 0) {
                        this.updataQueryDate(newOrg, 0);
                        this.setImportRecordInfo(importRecord, 0, "\u65b0\u589e\u6210\u529f");
                        break block11;
                    }
                    this.setImportRecordInfo(importRecord, errorCode, "\u65b0\u589e\u5931\u8d25");
                }
                catch (Exception e) {
                    this.setImportRecordInfo(importRecord, errorCode, "\u65b0\u589e\u5931\u8d25");
                    logger.error("\u65b0\u589e\u7ec4\u7ec7\u673a\u6784\u5931\u8d25", e);
                }
            } else {
                try {
                    int flag = this.orgDataModifyService.update(newOrg);
                    if (flag > 0) {
                        this.updataQueryDate(newOrg, 1);
                        this.setImportRecordInfo(importRecord, 0, "\u66f4\u65b0\u6210\u529f");
                    } else {
                        this.setImportRecordInfo(importRecord, errorCode, "\u66f4\u65b0\u5931\u8d25");
                    }
                }
                catch (Exception e) {
                    this.setImportRecordInfo(importRecord, errorCode, "\u66f4\u65b0\u5931\u8d25");
                    logger.error("\u66f4\u65b0\u7ec4\u7ec7\u673a\u6784\u5931\u8d25", e);
                }
            }
        }
    }

    private void updataQueryDate(OrgDTO org, int i) {
        this.dataQueryUtil.setOrg(this.category, OrgDataOption.AuthType.NONE, "code", (OrgDO)org, i);
        if (this.authType != OrgDataOption.AuthType.NONE) {
            this.dataQueryUtil.setOrg(this.category, this.authType, "code", (OrgDO)org, i);
        }
        for (ZBDTO zb : this.zbList) {
            if (zb.getUniqueflag() == null || zb.getUniqueflag() != 1 || zb.getName().equalsIgnoreCase("code")) continue;
            this.dataQueryUtil.setOrg(this.category, OrgDataOption.AuthType.NONE, zb.getName(), (OrgDO)org, i);
            if (this.authType == OrgDataOption.AuthType.NONE) continue;
            this.dataQueryUtil.setOrg(this.category, this.authType, "code", (OrgDO)org, i);
        }
    }

    private void checkExist(OrgDTO newOrg, BaseDataDTO importRecord) {
        OrgDO authCurCatOrg;
        if (!this.isCurrentLegal(importRecord)) {
            return;
        }
        int errorCode = 4;
        if (!newOrg.getCategoryname().equals("MD_ORG")) {
            OrgDO goalOrg;
            List<OrgDO> goalList = this.dataQueryUtil.getOrg("MD_ORG", OrgDataOption.AuthType.NONE, "code", newOrg.getCode());
            List<OrgDO> authList = this.dataQueryUtil.getOrg("MD_ORG", this.authType, "code", newOrg.getCode());
            OrgDO orgDO = goalOrg = goalList == null || goalList.isEmpty() ? null : goalList.get(0);
            if (goalOrg == null) {
                this.setImportRecordInfo(importRecord, errorCode, "\u673a\u6784\u4ee3\u7801[" + newOrg.getCode() + "]\u5173\u8054\u7684\u884c\u653f\u7ec4\u7ec7\u4e0d\u5b58\u5728");
            } else if (goalOrg.getRecoveryflag() != null && goalOrg.getRecoveryflag() == 1) {
                this.setImportRecordInfo(importRecord, errorCode, "\u673a\u6784\u4ee3\u7801[" + newOrg.getCode() + "]\u5173\u8054\u7684\u884c\u653f\u7ec4\u7ec7\u5df2\u56de\u6536");
            } else if (goalOrg.getStopflag() != null && goalOrg.getStopflag() == 1) {
                this.setImportRecordInfo(importRecord, errorCode, "\u673a\u6784\u4ee3\u7801[" + newOrg.getCode() + "]\u5173\u8054\u7684\u884c\u653f\u7ec4\u7ec7\u5df2\u505c\u7528");
            } else if (authList == null || authList.isEmpty()) {
                this.setImportRecordInfo(importRecord, errorCode, "\u673a\u6784\u4ee3\u7801[" + newOrg.getCode() + "]\u5173\u8054\u7684\u884c\u653f\u7ec4\u7ec7\u65e0\u6743\u9650");
            }
            if (!this.isCurrentLegal(importRecord)) {
                return;
            }
            if (goalOrg != null) {
                newOrg.setName(goalOrg.getName());
                newOrg.setShortname(goalOrg.getShortname());
            }
        }
        if (!this.isCurrentLegal(importRecord)) {
            return;
        }
        String categoryname = newOrg.getCategoryname();
        List<OrgDO> cateAllOrgList = this.dataQueryUtil.getOrg(categoryname, OrgDataOption.AuthType.NONE, "code", newOrg.getCode());
        List<OrgDO> cateAuthOrgList = this.dataQueryUtil.getOrg(categoryname, this.authType, "code", newOrg.getCode());
        OrgDO curCatOrg = cateAllOrgList == null || cateAllOrgList.isEmpty() ? null : cateAllOrgList.get(0);
        OrgDO orgDO = authCurCatOrg = cateAuthOrgList == null || cateAuthOrgList.isEmpty() ? null : cateAuthOrgList.get(0);
        if (curCatOrg != null) {
            if (authCurCatOrg == null) {
                this.setImportRecordInfo(importRecord, errorCode, "\u673a\u6784\u4ee3\u7801[" + newOrg.getCode() + "]\u5173\u8054\u7684" + newOrg.getCategoryname() + "\u65e0\u6743\u9650");
            } else {
                newOrg.setId(authCurCatOrg.getId());
                if (newOrg.getStopflag() == null) {
                    newOrg.setStopflag(authCurCatOrg.getStopflag());
                }
                newOrg.setRecoveryflag(Integer.valueOf(0));
            }
        }
    }

    private void setImportRecordInfo(BaseDataDTO importRecord, int errorCode, String errorInfo) {
        importRecord.put("ImportState", (Object)errorCode);
        importRecord.put("ImportMemo", (Object)errorInfo);
    }
}

