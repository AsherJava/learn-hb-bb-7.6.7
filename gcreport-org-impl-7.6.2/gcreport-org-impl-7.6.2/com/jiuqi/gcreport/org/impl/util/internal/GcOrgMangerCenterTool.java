/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.NumberUtils
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.common.base.util.UUIDUtils
 *  com.jiuqi.common.web.util.BusinessLogUtils
 *  com.jiuqi.gcreport.org.api.enums.EventChangeTypeEnum
 *  com.jiuqi.gcreport.org.api.period.YearPeriodDO
 *  com.jiuqi.gcreport.org.api.period.YearPeriodUtil
 *  com.jiuqi.gcreport.org.api.vo.FrontEndParams
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.gcreport.org.api.vo.OrgSplitVO
 *  com.jiuqi.gcreport.org.api.vo.OrgToJsonVO
 *  com.jiuqi.gcreport.org.api.vo.OrgTypeVO
 *  com.jiuqi.gcreport.org.api.vo.OrgVersionVO
 *  com.jiuqi.np.definition.common.JDBCHelper
 *  com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService
 */
package com.jiuqi.gcreport.org.impl.util.internal;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.Assert;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.NumberUtils;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.common.web.util.BusinessLogUtils;
import com.jiuqi.gcreport.org.api.enums.EventChangeTypeEnum;
import com.jiuqi.gcreport.org.api.period.YearPeriodDO;
import com.jiuqi.gcreport.org.api.period.YearPeriodUtil;
import com.jiuqi.gcreport.org.api.vo.FrontEndParams;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.gcreport.org.api.vo.OrgSplitVO;
import com.jiuqi.gcreport.org.api.vo.OrgToJsonVO;
import com.jiuqi.gcreport.org.api.vo.OrgTypeVO;
import com.jiuqi.gcreport.org.api.vo.OrgVersionVO;
import com.jiuqi.gcreport.org.impl.base.GcOrgBaseParam;
import com.jiuqi.gcreport.org.impl.base.GcOrgCodeConfig;
import com.jiuqi.gcreport.org.impl.base.InspectOrgUtils;
import com.jiuqi.gcreport.org.impl.cache.impl.GcOrgParam;
import com.jiuqi.gcreport.org.impl.cache.service.FGcOrgEditService;
import com.jiuqi.gcreport.org.impl.util.base.GcOrgCenterBase;
import com.jiuqi.gcreport.org.impl.util.base.OrgParamParse;
import com.jiuqi.gcreport.org.impl.util.base.OrgParse;
import com.jiuqi.gcreport.org.impl.util.bean.GcOrgManageModel;
import com.jiuqi.gcreport.org.impl.util.bean.GcOrgModelProvider;
import com.jiuqi.gcreport.org.impl.util.internal.GcOrgBaseTool;
import com.jiuqi.np.definition.common.JDBCHelper;
import com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ObjectUtils;

public class GcOrgMangerCenterTool
extends GcOrgCenterBase {
    private static final Logger logger = LoggerFactory.getLogger(GcOrgMangerCenterTool.class);
    private GcOrgBaseParam param;
    private GcOrgManageModel model;
    private FrontEndParams frontEnd;

    public FrontEndParams getFrontEnd() {
        return this.frontEnd;
    }

    public void setFrontEnd(FrontEndParams frontEnd) {
        this.frontEnd = frontEnd;
    }

    private GcOrgMangerCenterTool() {
    }

    @Override
    protected FGcOrgEditService getGcReportService() {
        return this.model.getOrgEditService();
    }

    @Override
    protected GcOrgBaseParam getParam() {
        return this.param;
    }

    public static GcOrgMangerCenterTool getInstance() {
        return GcOrgMangerCenterTool.getInstance(null, "");
    }

    public static GcOrgMangerCenterTool getInstance(String orgType) {
        return GcOrgMangerCenterTool.getInstance(orgType, "");
    }

    public static GcOrgMangerCenterTool getInstance(String orgType, String yyyyTmmmm) {
        YearPeriodDO yp = YearPeriodUtil.transform(null, (String)yyyyTmmmm);
        return GcOrgMangerCenterTool.getInstance(orgType, yp);
    }

    public static GcOrgMangerCenterTool getInstance(String orgType, YearPeriodDO yp) {
        GcOrgMangerCenterTool tool = new GcOrgMangerCenterTool();
        try {
            tool.model = GcOrgModelProvider.getGcOrgManageModel();
            tool.param = new GcOrgBaseParam(tool.model, orgType, yp.getEndDate());
        }
        catch (Exception e) {
            throw new RuntimeException("\u52a0\u8f7d\u7ec4\u7ec7\u673a\u6784\u7ba1\u7406\u67e5\u8be2\u5668\u5931\u8d25,\u8bf7\u8054\u7cfb\u7cfb\u7edf\u7ba1\u7406\u5458", e);
        }
        return tool;
    }

    @Override
    public void delete(String orgCode) {
        this.deleteWithChildrens(orgCode);
        OrgToJsonVO vo = new OrgToJsonVO();
        vo.setId(orgCode);
        InspectOrgUtils.syncOrgCache(this.param.getType().getName());
    }

    @Override
    protected void deleteWithChildrens(String orgCode) {
        OrgToJsonVO parent = (OrgToJsonVO)this.getGcReportService().getByCode(this.getParam(), orgCode);
        if (null == parent) {
            return;
        }
        List childrens = this.getGcReportService().listDirectSubordinate(this.getParam(), orgCode);
        if (!CollectionUtils.isEmpty(childrens)) {
            childrens.stream().forEach(entity -> {
                try {
                    this.deleteWithChildrens(entity.getCode());
                }
                catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });
        }
        parent.setUpdateTime(new Date());
        this.deleteSingleUnitWithoutCheck(parent);
        if (this.getFrontEnd() != null) {
            int totalCount = this.getFrontEnd().getTotalCount();
            this.getFrontEnd().getProgressData().addProgressValueAndRefresh(1.0 / (double)totalCount * 100.0);
        }
    }

    public void deleteSingleUnitWithoutCheck(OrgToJsonVO orgToJsonVO) {
        GcOrgParam vaJsonVo = OrgParse.toVaJsonVo(orgToJsonVO, this.getParam());
        vaJsonVo.setCacheSyncDisable(true);
        this.getGcReportService().remove(vaJsonVo);
        BusinessLogUtils.operate((String)"\u5408\u5e76\u7ec4\u7ec7\u7ba1\u7406", (String)"\u5220\u9664\u7ec4\u7ec7\u673a\u6784", (String)("\u5220\u9664\u7ec4\u7ec7\u673a\u6784\uff1a" + orgToJsonVO.getCode()));
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public void deleteSingleUnit(String orgCode) {
        OrgToJsonVO current = (OrgToJsonVO)this.getGcReportService().getByCode(this.getParam(), orgCode);
        if (null == current) {
            return;
        }
        List childrens = this.getGcReportService().listDirectSubordinate(this.getParam(), orgCode);
        if (!CollectionUtils.isEmpty(childrens)) throw new BusinessRuntimeException(current.getTitle() + ": \u5408\u5e76\u8282\u70b9\u4e0d\u5141\u8bb8\u5220\u9664");
        HashMap<String, String> columnMap = new HashMap<String, String>();
        columnMap.put("BASEUNITID", "\u672c\u90e8\u5355\u4f4d");
        columnMap.put("DIFFUNITID", "\u5dee\u989d\u5355\u4f4d");
        columnMap.put("SPLITID", "\u62c6\u5206\u5355\u4f4d");
        Map<String, Object> r = this.checkRelated(orgCode, columnMap, this.param.getType(), this.param.getVersion());
        if (((Boolean)r.get("success")).booleanValue()) {
            boolean remove = this.getGcReportService().remove(OrgParse.toVaJsonVo(current, this.getParam()));
            if (remove) {
                this.updateParentFieldsByUnit(current);
            }
        } else {
            String message = (String)r.get("message");
            throw new BusinessRuntimeException(message);
        }
        OrgToJsonVO vo = new OrgToJsonVO();
        vo.setId(orgCode);
        this.model.publishEvent(EventChangeTypeEnum.DELETE, this.param, vo);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private Map<String, Object> checkRelated(String orgCode, Map<String, String> columns, OrgTypeVO type, OrgVersionVO version) {
        HashMap<String, Object> ret = new HashMap<String, Object>();
        JDBCHelper jdbcHelper = (JDBCHelper)SpringContextUtils.getBean(JDBCHelper.class);
        Connection conn = null;
        PreparedStatement prep = null;
        ResultSet resultSet = null;
        Date validTime = version.getValidTime();
        String formatSQLDate = InspectOrgUtils.getDateFormat(validTime);
        boolean flag = true;
        String message = "";
        try {
            conn = jdbcHelper.getConnection();
            Set<String> keySet = columns.keySet();
            for (String key : keySet) {
                String tableName = type.getName();
                if (key.equalsIgnoreCase("SPLITID")) {
                    tableName = "MD_ORG_MANAGEMENT";
                }
                if (key.equalsIgnoreCase("SPLITID") && tableName.equalsIgnoreCase("MD_ORG_CORPORATE")) continue;
                String sql = "select name,code from %s where %s = ? and VALIDTIME<= %s and %s < INVALIDTIME";
                sql = String.format(sql, tableName, key, formatSQLDate, formatSQLDate);
                prep = conn.prepareStatement(sql);
                prep.setString(1, orgCode);
                resultSet = prep.executeQuery();
                if (!resultSet.next()) continue;
                String hbOrgName = resultSet.getString(1);
                String code = resultSet.getString(2);
                String orgTitle = code + "|" + hbOrgName;
                message = key.equalsIgnoreCase("SPLITID") ? String.format("\u8be5\u5355\u4f4d\u4e3a\u88ab\u62c6\u5206\u5355\u4f4d\uff0c\u4e0d\u5141\u8bb8\u5220\u9664", new Object[0]) : (key.equalsIgnoreCase("BASEUNITID") || key.equalsIgnoreCase("DIFFUNITID") ? String.format("\u5408\u5e76\u5355\u4f4d%s\u7684\u672c\u90e8\u6216\u5dee\u989d\u5f15\u7528,\u4e0d\u5141\u8bb8\u5220\u9664", orgTitle) : String.format("\u8be5\u5355\u4f4d\u88ab\u8bbe\u7f6e\u4e3a%s\u7684%s,\u4e0d\u5141\u8bb8\u5220\u9664", orgTitle, columns.get(key)));
                flag = false;
                break;
            }
        }
        catch (SQLException e) {
            flag = false;
            message = e.getMessage();
            e.printStackTrace();
        }
        finally {
            jdbcHelper.close(conn, prep, resultSet);
        }
        ret.put("success", flag);
        ret.put("message", message);
        return ret;
    }

    private void updateParentFieldsByUnit(OrgToJsonVO current) {
        OrgToJsonVO oldParent = this.getOrgByCode(current.getParentid());
        this.updateParentAutoFields(this.getGcReportService(), oldParent);
    }

    @Override
    public void add(OrgToJsonVO vo) {
        this.checkData(vo);
        if (ObjectUtils.isEmpty(vo.getFieldValue("IGNORE_CALC_ORGTYPE"))) {
            vo.setFieldValue("ORGTYPEID", (Object)"MD_ORG_CORPORATE");
        }
        vo.setFieldValue("ADJTYPEIDS", (Object)"BEFOREADJ");
        if (ObjectUtils.isEmpty(vo.getFieldValue("IGNORE_CALC_BBLX"))) {
            vo.setFieldValue("BBLX", (Object)"0");
        }
        vo.setFieldValue("PARENTS", (Object)"");
        this.setDefaultCurrency(vo);
        int endPosition = Math.min(vo.getTitle().length(), 90);
        vo.setSimpletitle(StringUtils.isEmpty((String)vo.getSimpletitle()) ? vo.getTitle().substring(0, endPosition) : vo.getSimpletitle());
        boolean add = this.addOrg(this.getGcReportService(), vo);
        if (add) {
            this.updateParentFieldsByUnit(vo);
            if (!Boolean.parseBoolean(String.valueOf(vo.getFieldValue("NO_CACHE_EVENT")))) {
                this.model.publishEvent(EventChangeTypeEnum.INSERT, this.param, vo);
            }
        }
    }

    private void checkData(OrgToJsonVO vo) {
        String code = String.valueOf(vo.getDatas().get("CODE"));
        String baseUnitid = vo.getBaseUnitId();
        String diffUnitid = vo.getDiffUnitId();
        OrgToJsonVO old = this.getOrgByCode(code);
        vo.setTitle(vo.getTitle().trim());
        vo.setSimpletitle(vo.getSimpletitle().trim());
        GcOrgCodeConfig orgCodeConfig = this.model.getOrgEditService().getOrgCodeConfig();
        boolean variableLength = orgCodeConfig.isVariableLength();
        int len = orgCodeConfig.getCodeLength();
        if (!variableLength && code.length() != len) {
            throw new BusinessRuntimeException("\u7ec4\u7ec7\u673a\u6784\u4ee3\u7801\u957f\u5ea6\u53ea\u80fd\u4e3a " + len);
        }
        if (variableLength && code.length() > len) {
            throw new BusinessRuntimeException("\u7ec4\u7ec7\u673a\u6784\u4ee3\u7801\u957f\u5ea6\u4e0d\u80fd\u8d85\u8fc7 " + len);
        }
        if (code.equals(baseUnitid)) {
            throw new BusinessRuntimeException(vo.getTitle() + "\u672c\u90e8\u5355\u4f4d\u4ee3\u7801\u4e0d\u80fd\u4e0e\u5355\u4f4d\u4ee3\u7801\u4e00\u81f4!");
        }
        if (!StringUtils.isEmpty((String)baseUnitid) && !StringUtils.isEmpty((String)diffUnitid) && baseUnitid.equals(diffUnitid)) {
            throw new BusinessRuntimeException(vo.getTitle() + "\u5dee\u989d\u5355\u4f4d\u4ee3\u7801\u4e0d\u80fd\u4e0e\u672c\u90e8\u5355\u4f4d\u4ee3\u7801\u4e00\u81f4!");
        }
        if (old == null) {
            return;
        }
        if (!StringUtils.isEmpty((String)baseUnitid) && this.getOrgByCode(baseUnitid) == null) {
            vo.setFieldValue("BASEUNITID", (Object)old.getBaseUnitId());
        }
        if (!StringUtils.isEmpty((String)diffUnitid) && this.getOrgByCode(diffUnitid) == null) {
            vo.setFieldValue("DIFFUNITID", (Object)old.getDiffUnitId());
        }
    }

    public void clearCache() throws RuntimeException {
        this.model.publishEvent(EventChangeTypeEnum.UPDATE, this.param, new OrgToJsonVO());
    }

    @Override
    public void update(OrgToJsonVO vo) {
        this.checkData(vo);
        this.innerUpdate(vo);
        if (!Boolean.parseBoolean(String.valueOf(vo.getFieldValue("NO_CACHE_EVENT")))) {
            this.model.publishEvent(EventChangeTypeEnum.UPDATE, this.param, vo);
        }
    }

    public void updateWithAnyPreProcess(OrgToJsonVO vo) {
        this.getGcReportService().update(OrgParse.toVaJsonVo(vo, this.getParam()));
        if (!Boolean.parseBoolean(String.valueOf(vo.getFieldValue("NO_CACHE_EVENT")))) {
            this.model.publishEvent(EventChangeTypeEnum.UPDATE, this.param, vo);
        }
    }

    public void addWithoutAnyPreProcess(OrgToJsonVO vo) {
        this.setDefaultCurrency(vo);
        GcOrgParam vaJsonVo = OrgParse.toVaJsonVo(vo, this.getParam());
        vaJsonVo.setCategoryname(this.getParam().getOrgtypeName());
        if (Boolean.parseBoolean(String.valueOf(vo.getFieldValue("NO_CACHE_EVENT")))) {
            vaJsonVo.setCacheSyncDisable(true);
        }
        this.getGcReportService().add(vaJsonVo);
    }

    private void innerUpdate(OrgToJsonVO vo) {
        FGcOrgEditService service = this.getGcReportService();
        String parentid = StringUtils.isEmpty((String)vo.getParentid()) ? "-" : vo.getParentid();
        OrgToJsonVO orgByID = this.getOrgByCode(vo.getCode());
        vo.setFieldValue("UPDATETIME", (Object)new Date());
        vo.setFieldValue("PARENTCODE", (Object)parentid);
        orgByID.getDatas().forEach((key, value) -> {
            if (!vo.getDatas().containsKey(key)) {
                vo.setFieldValue(key, value);
            }
        });
        this.updateOrg(service, vo);
        if (!parentid.equals(orgByID.getParentid())) {
            OrgToJsonVO updated = this.getOrgByCode(vo.getCode());
            service.move(OrgParamParse.createParam(this.getParam(), v -> {
                v.setCode(updated.getCode());
                v.setParentcode(parentid);
            }));
        }
        this.updateAutoCalcField(vo, orgByID);
    }

    private void updateAutoCalcField(OrgToJsonVO vo, OrgToJsonVO oldOrg) {
        this.updateParentAutoFields(vo, oldOrg);
        this.updateAutoByDiffUnit(vo, oldOrg);
    }

    private boolean checkEnableAutoCalc() {
        INvwaSystemOptionService systemOptionsService = (INvwaSystemOptionService)SpringContextUtils.getBean(INvwaSystemOptionService.class);
        String option = systemOptionsService.get("gc_option_org_fieldCalc", "NOT_AUTO_CALC_FIELD");
        if (!StringUtils.isEmpty((String)option)) {
            ArrayList notAutoCalctypes = CollectionUtils.newArrayList((Object[])option.split(";"));
            return !notAutoCalctypes.contains(this.param.getOrgtypeName());
        }
        return true;
    }

    private void updateAutoByDiffUnit(OrgToJsonVO vo, OrgToJsonVO oldOrg) {
        OrgToJsonVO newDiffOrg;
        OrgToJsonVO oldDiffOrg;
        String oldDiffUnitId;
        String diffUnitId = vo.getDiffUnitId();
        if (diffUnitId.equals(oldDiffUnitId = oldOrg.getDiffUnitId())) {
            return;
        }
        boolean autoCalc = this.checkEnableAutoCalc();
        if (!StringUtils.isEmpty((String)oldDiffUnitId) && (oldDiffOrg = this.getOrgByID(oldOrg.getDiffUnitId())) != null) {
            if (autoCalc) {
                oldDiffOrg.setFieldValue("ORGTYPEID", (Object)"MD_ORG_CORPORATE");
            }
            oldDiffOrg.setFieldValue("BBLX", (Object)"0");
            this.updateOrg(this.getGcReportService(), oldDiffOrg);
        }
        if (!StringUtils.isEmpty((String)diffUnitId) && (newDiffOrg = this.getOrgByID(diffUnitId)) != null) {
            if (autoCalc) {
                newDiffOrg.setFieldValue("ORGTYPEID", (Object)this.getParam().getOrgtypeName());
            }
            newDiffOrg.setFieldValue("BBLX", (Object)"1");
            this.updateOrg(this.getGcReportService(), newDiffOrg);
        }
    }

    private void updateParentAutoFields(OrgToJsonVO vo, OrgToJsonVO oldOrg) {
        String oldParentId;
        String parentid = vo.getParentid() == null ? "-" : vo.getParentid();
        if (!parentid.equals(oldParentId = oldOrg.getParentid())) {
            this.updateParentFieldsByUnit(vo);
            this.updateParentFieldsByUnit(oldOrg);
        }
    }

    private void updateParentAutoFields(FGcOrgEditService service, OrgToJsonVO parent) {
        boolean orgTypeChange;
        boolean autoCalc = this.checkEnableAutoCalc();
        if (null == parent) {
            return;
        }
        String bblx = (String)parent.getFieldValue("BBLX");
        String orgType = (String)parent.getFieldValue("ORGTYPEID");
        bblx = StringUtils.isEmpty((String)bblx) ? "" : bblx;
        orgType = StringUtils.isEmpty((String)orgType) ? "" : orgType;
        List currentChildren = service.listDirectSubordinate(this.getParam(), parent.getCode());
        if (CollectionUtils.isEmpty(currentChildren)) {
            if (autoCalc) {
                parent.setFieldValue("ORGTYPEID", (Object)"MD_ORG_CORPORATE");
                parent.setFieldValue("BBLX", (Object)"0");
            }
        } else if (autoCalc) {
            parent.setFieldValue("ORGTYPEID", (Object)this.getParam().getOrgtypeName());
            parent.setFieldValue("BBLX", (Object)"9");
        }
        boolean bblxChange = !bblx.equalsIgnoreCase((String)parent.getFieldValue("BBLX"));
        boolean bl = orgTypeChange = !orgType.equalsIgnoreCase((String)parent.getFieldValue("ORGTYPEID"));
        if (bblxChange || orgTypeChange) {
            this.updateOrg(service, parent);
        }
    }

    public List<OrgToJsonVO> diffUnitExist(String code) {
        if (StringUtils.isEmpty((String)code)) {
            return null;
        }
        GcOrgCacheVO org = this.model.getQueryService().getByCode(this.getParam(), code);
        if (org == null || StringUtils.isEmpty((String)org.getMergeUnitId())) {
            return null;
        }
        OrgToJsonVO morg = (OrgToJsonVO)this.getGcReportService().getByCode(this.getParam(), org.getMergeUnitId());
        if (morg == null) {
            return null;
        }
        return Arrays.asList(morg);
    }

    @Override
    public void moveParent(OrgToJsonVO org, OrgToJsonVO tagertOrg) {
        FGcOrgEditService service = this.getGcReportService();
        service.move(OrgParamParse.createParam(this.getParam(), v -> {
            v.setCode(org.getCode());
            v.setParentcode(tagertOrg.getCode());
        }));
        this.model.publishEvent(EventChangeTypeEnum.UPDATE, this.param, org);
    }

    public OrgToJsonVO batchSaveOrg(OrgToJsonVO otjo) {
        FGcOrgEditService service = this.getGcReportService();
        this.batchSaveCheck(otjo);
        String parentCode = otjo.getCode();
        if (StringUtils.isEmpty((String)parentCode)) {
            parentCode = "-";
        }
        List<OrgToJsonVO> oldOrgs = this.listChildOrgByParent(parentCode);
        List<Object> oldIds = new ArrayList();
        if (oldOrgs != null) {
            oldIds = oldOrgs.stream().map(OrgToJsonVO::getCode).collect(Collectors.toList());
        }
        List newOrgs = otjo.getChildren();
        try {
            if (newOrgs != null && !newOrgs.isEmpty()) {
                for (int i = 0; i < newOrgs.size(); ++i) {
                    OrgToJsonVO orgToJsonVO = (OrgToJsonVO)newOrgs.get(i);
                    OrgToJsonVO oldOrg = this.getOrgByCode(orgToJsonVO.getCode());
                    if (oldOrg != null && oldOrg.getCode() != null) {
                        orgToJsonVO.setFieldValue("ID", oldOrg.getFieldValue("ID"));
                        oldOrg.setFieldValue("NO_CACHE_EVENT", (Object)true);
                        if (oldIds.contains(oldOrg.getCode())) {
                            oldOrg.setFieldValue("ORDINAL", orgToJsonVO.getFieldValue("ORDINAL"));
                            if (oldOrg.isRecoveryFlag()) {
                                oldIds.remove(oldOrg.getCode());
                                continue;
                            }
                            this.innerUpdate(oldOrg);
                            oldIds.remove(oldOrg.getCode());
                            continue;
                        }
                        oldOrg.setFieldValue("ORDINAL", orgToJsonVO.getFieldValue("ORDINAL"));
                        oldOrg.setParentid(parentCode);
                        this.innerUpdate(oldOrg);
                        continue;
                    }
                    OrgToJsonVO org = (OrgToJsonVO)service.getBaseUnit(OrgParamParse.createBaseOrgParam(v -> v.setCode(newOrg.getId())));
                    org.setParentid(parentCode);
                    org.setFieldValue("ORGID", (Object)org.getCode());
                    org.setFieldValue("STOPFLAG", (Object)0);
                    org.setFieldValue("ID", (Object)UUIDUtils.newUUIDStr());
                    org.setFieldValue("ORDINAL", orgToJsonVO.getFieldValue("ORDINAL"));
                    org.setFieldValue("NO_CACHE_EVENT", (Object)true);
                    if (Optional.ofNullable(org.getFieldValue("BBLX")).isPresent()) {
                        org.setFieldValue("IGNORE_CALC_BBLX", (Object)true);
                    }
                    this.add(org);
                }
            }
            if (!parentCode.equals("-") && oldIds.size() > 0) {
                for (String string : oldIds) {
                    this.deleteSingleUnit(string);
                }
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException("\u4fdd\u5b58\u5931\u8d25\uff1a" + e.getMessage());
        }
        finally {
            this.model.publishEvent(EventChangeTypeEnum.UPDATE, this.param, otjo);
        }
        return otjo;
    }

    public List<OrgSplitVO> getSplitedOrgs(String parentId) {
        GcOrgBaseTool tool = GcOrgBaseTool.getInstance();
        List<OrgToJsonVO> allChildren = tool.listAllSubordinates(parentId);
        if (CollectionUtils.isEmpty(allChildren)) {
            allChildren.add(tool.getOrgById(parentId));
        }
        Map<String, List<OrgToJsonVO>> splitMap = this.getSplitUnitByCondition();
        ArrayList<OrgSplitVO> splitVOS = new ArrayList<OrgSplitVO>();
        for (OrgToJsonVO orgToJsonVO : allChildren) {
            if (!splitMap.containsKey(orgToJsonVO.getId())) continue;
            List<OrgToJsonVO> orgToJsonVOS = splitMap.get(orgToJsonVO.getId());
            ArrayList<OrgSplitVO> orgSplitVOS = new ArrayList<OrgSplitVO>();
            for (OrgToJsonVO toJsonVO : orgToJsonVOS) {
                Double scale = NumberUtils.parseDouble((Object)toJsonVO.getFieldValue("SPLITSCALE"));
                String viewString = StringUtils.toViewString((Object)toJsonVO.getFieldValue("SPLITDIFFFLAG"));
                boolean splitDiffFlag = !StringUtils.isEmpty((String)viewString) && Integer.parseInt(viewString) == 1;
                OrgSplitVO orgSplitVO = new OrgSplitVO(orgToJsonVO, toJsonVO, Double.valueOf(NumberUtils.mul((double)scale, (double)100.0)), splitDiffFlag);
                orgSplitVOS.add(orgSplitVO);
            }
            splitVOS.addAll(orgSplitVOS);
        }
        return splitVOS;
    }

    public void batchSaveSplitOrg(List<OrgSplitVO> splitedOrgs) {
        String splitedId = splitedOrgs.get(0).getSplitedUnit().getId();
        for (int i = 0; i < splitedOrgs.size(); ++i) {
            boolean equals = splitedId.equals(splitedOrgs.get(i).getSplitUnit().getId());
            Assert.isFalse((boolean)equals, (String)("\u7b2c" + (i + 1) + "\u62c6\u5206\u524d\u5355\u4f4d\u548c\u62c6\u5206\u540e\u5355\u4f4d\u4e0d\u5141\u8bb8\u76f8\u540c,\u8bf7\u91cd\u65b0\u9009\u62e9"), (Object[])new Object[0]);
        }
        List splitIds = splitedOrgs.stream().map(orgSplitVO -> orgSplitVO.getSplitUnit().getId()).collect(Collectors.toList());
        long count = splitIds.stream().distinct().count();
        if (count != (long)splitIds.size()) {
            throw new RuntimeException("\u62c6\u5206\u540e\u5355\u4f4d\u4e2d\u51fa\u73b0\u91cd\u590d\u9879!");
        }
        List<OrgToJsonVO> oldSps = this.listOrg().stream().filter(orgToJsonVO -> {
            String splitid = orgToJsonVO.getSplitUnitId();
            return splitedId.equals(splitid);
        }).collect(Collectors.toList());
        oldSps.forEach(orgToJsonVO -> this.deleteOrgSplit(orgToJsonVO.getCode()));
        splitedOrgs.stream().forEach(orgSplitVO -> {
            try {
                OrgToJsonVO alreSplit;
                OrgToJsonVO orgByID = this.getOrgByCode(orgSplitVO.getSplitUnit().getId());
                String currId = orgSplitVO.getSplitedUnit().getId();
                String splitid = orgByID.getSplitUnitId();
                if (!StringUtils.isEmpty((String)splitid) && (alreSplit = this.getOrgByID(splitid)) != null && null != alreSplit.getId() && !currId.equals(alreSplit.getId())) {
                    throw new RuntimeException(orgByID.getTitle() + " \u5df2\u7ecf\u88ab\u62c6\u5206,\u62c6\u5206\u5355\u4f4d\u4e3a: " + alreSplit.getTitle());
                }
                orgByID.setFieldValue("SPLITID", (Object)orgSplitVO.getSplitedUnit().getId());
                orgByID.setFieldValue("SPLITSCALE", (Object)NumberUtils.div((double)orgSplitVO.getScale(), (double)100.0, (int)6));
                orgByID.setFieldValue("ORGTYPEID", (Object)this.getParam().getOrgtypeName());
                orgByID.setFieldValue("SPLITDIFFFLAG", (Object)(orgSplitVO.getSplitDiffFlag() ? 1 : 0));
                this.innerUpdate(orgByID);
            }
            catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        this.model.publishEvent(EventChangeTypeEnum.UPDATE, this.param, new OrgToJsonVO());
    }

    public void deleteOrgSplit(List<String> codes) {
        if (codes != null && codes.size() > 0) {
            codes.forEach(v -> this.deleteOrgSplit((String)v));
        }
        this.model.publishEvent(EventChangeTypeEnum.UPDATE, this.param, new OrgToJsonVO());
    }

    private void deleteOrgSplit(String splitUnit) {
        OrgToJsonVO orgByID = this.getOrgByCode(splitUnit);
        orgByID.setFieldValue("SPLITID", (Object)"");
        orgByID.setFieldValue("SPLITSCALE", (Object)"0");
        orgByID.setFieldValue("ORGTYPEID", (Object)"MD_ORG_CORPORATE");
        this.innerUpdate(orgByID);
    }

    private Map<String, List<OrgToJsonVO>> getSplitUnitByCondition() {
        HashMap<String, List<OrgToJsonVO>> retMap = new HashMap<String, List<OrgToJsonVO>>();
        List list = this.getGcReportService().list(this.getParam(), null);
        if (CollectionUtils.isEmpty(list)) {
            return retMap;
        }
        list.stream().forEach(vo -> {
            String splitId = vo.getSplitUnitId();
            if (!StringUtils.isEmpty((String)splitId)) {
                ArrayList<OrgToJsonVO> vos = (ArrayList<OrgToJsonVO>)retMap.get(splitId);
                if (CollectionUtils.isEmpty((Collection)vos)) {
                    vos = new ArrayList<OrgToJsonVO>();
                    retMap.put(splitId, vos);
                }
                vos.add((OrgToJsonVO)vo);
            }
        });
        return retMap;
    }

    private void setDefaultCurrency(OrgToJsonVO org) {
        String baseOrgCurrency = (String)org.getFieldValue("CURRENCYID");
        if (!StringUtils.isEmpty((String)baseOrgCurrency) && baseOrgCurrency.equalsIgnoreCase("CNY")) {
            org.setFieldValue("CURRENCYID", org.getFieldValue("CURRENCYID"));
            org.setFieldValue("CURRENCYIDS", org.getFieldValue("CURRENCYID"));
        }
        if (!StringUtils.isEmpty((String)baseOrgCurrency) && !baseOrgCurrency.equalsIgnoreCase("CNY")) {
            org.setFieldValue("CURRENCYID", org.getFieldValue("CURRENCYID"));
            org.setFieldValue("CURRENCYIDS", (Object)(org.getFieldValue("CURRENCYID") + ";" + "CNY"));
        }
        if (StringUtils.isEmpty((String)baseOrgCurrency)) {
            org.setFieldValue("CURRENCYID", (Object)"CNY");
            org.setFieldValue("CURRENCYIDS", (Object)"CNY");
        }
    }

    private boolean batchSaveCheck(OrgToJsonVO batchSaveOrg) {
        boolean checkState = true;
        String code = batchSaveOrg.getCode();
        List children = batchSaveOrg.getChildren();
        if (StringUtils.isEmpty((String)code)) {
            code = "-";
        }
        if (!"-".equalsIgnoreCase(code)) {
            OrgToJsonVO orgByCode = this.getOrgByCode(code);
            String parents = orgByCode.getParents();
            List<String> split = Arrays.asList(parents.split("/"));
            for (OrgToJsonVO newOrg : children) {
                if (!split.contains(newOrg.getCode())) continue;
                OrgToJsonVO byCode = this.getOrgByCode(newOrg.getCode());
                checkState = false;
                throw new BusinessRuntimeException(byCode.getTitle() + " \u65e0\u6cd5\u4f5c\u4e3a\u5f53\u524d\u5355\u4f4d\u7684\u4e0b\u7ea7");
            }
        }
        return checkState;
    }
}

