/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.basedata.BaseDataConsts
 *  com.jiuqi.va.domain.basedata.BaseDataDO
 *  com.jiuqi.va.domain.basedata.BaseDataDTO
 *  com.jiuqi.va.domain.basedata.BaseDataDefineDO
 *  com.jiuqi.va.domain.basedata.BaseDataOption$AuthType
 *  com.jiuqi.va.domain.basedata.BaseDataOption$EventType
 *  com.jiuqi.va.domain.basedata.BaseDataOption$MoveType
 *  com.jiuqi.va.domain.basedata.BaseDataOption$QueryChildrenType
 *  com.jiuqi.va.domain.basedata.BaseDataOption$QueryDataStructure
 *  com.jiuqi.va.domain.basedata.handle.BaseDataBatchOptDTO
 *  com.jiuqi.va.domain.basedata.handle.BaseDataMoveDTO
 *  com.jiuqi.va.domain.common.JSONUtil
 *  com.jiuqi.va.domain.common.OrderNumUtil
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.common.ShiroUtil
 *  com.jiuqi.va.domain.datamodel.DataModelColumn
 *  com.jiuqi.va.domain.datamodel.DataModelDO
 *  com.jiuqi.va.domain.datamodel.DataModelDTO
 *  com.jiuqi.va.domain.user.UserLoginDTO
 *  com.jiuqi.va.event.BaseDataEvent
 *  com.jiuqi.va.feign.client.BaseDataClient
 *  com.jiuqi.va.feign.client.DataModelClient
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 *  com.jiuqi.va.mapper.common.JDialectUtil
 *  com.jiuqi.va.mapper.common.JTableModel
 *  com.jiuqi.va.mapper.dao.CommonDao
 *  com.jiuqi.va.mapper.domain.SqlDTO
 *  org.apache.ibatis.jdbc.SQL
 *  org.apache.ibatis.session.ExecutorType
 *  org.apache.ibatis.session.SqlSession
 *  org.mybatis.spring.SqlSessionTemplate
 */
package com.jiuqi.va.basedata.service.impl.help;

import com.jiuqi.va.basedata.common.BaseDataCoreI18nUtil;
import com.jiuqi.va.basedata.dao.VaBaseDataDao;
import com.jiuqi.va.basedata.domain.BaseDataSyncCacheDTO;
import com.jiuqi.va.basedata.service.impl.help.BaseDataCacheCoordinationService;
import com.jiuqi.va.basedata.service.impl.help.BaseDataCacheService;
import com.jiuqi.va.basedata.service.impl.help.BaseDataParamService;
import com.jiuqi.va.basedata.service.impl.help.BaseDataQueryService;
import com.jiuqi.va.domain.basedata.BaseDataConsts;
import com.jiuqi.va.domain.basedata.BaseDataDO;
import com.jiuqi.va.domain.basedata.BaseDataDTO;
import com.jiuqi.va.domain.basedata.BaseDataDefineDO;
import com.jiuqi.va.domain.basedata.BaseDataOption;
import com.jiuqi.va.domain.basedata.handle.BaseDataBatchOptDTO;
import com.jiuqi.va.domain.basedata.handle.BaseDataMoveDTO;
import com.jiuqi.va.domain.common.JSONUtil;
import com.jiuqi.va.domain.common.OrderNumUtil;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.common.ShiroUtil;
import com.jiuqi.va.domain.datamodel.DataModelColumn;
import com.jiuqi.va.domain.datamodel.DataModelDO;
import com.jiuqi.va.domain.datamodel.DataModelDTO;
import com.jiuqi.va.domain.user.UserLoginDTO;
import com.jiuqi.va.event.BaseDataEvent;
import com.jiuqi.va.feign.client.BaseDataClient;
import com.jiuqi.va.feign.client.DataModelClient;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import com.jiuqi.va.mapper.common.JDialectUtil;
import com.jiuqi.va.mapper.common.JTableModel;
import com.jiuqi.va.mapper.dao.CommonDao;
import com.jiuqi.va.mapper.domain.SqlDTO;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.apache.ibatis.jdbc.SQL;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component(value="vaBaseDataModifyService")
public class BaseDataModifyService {
    private static Logger logger = LoggerFactory.getLogger(BaseDataModifyService.class);
    public static final String BASEDATA_BATCH_DAO = "BASEDATA_BATCH_DAO";
    public static final String INIT_FIELD_FINISHED = "initFieldValFinished";
    public static final String BASEDATA_FIXED_VER = "BASEDATA_FIXED_VER";
    public static final String BASEDATA_CODE_IGNORECASE = "BASEDATA_CODE_IGNORECASE";
    public static final String BASEDATA_OBJCODE_SUFFIX = "objcodeSuffix";
    public static final String BASEDATA_PARENTS_INITED = "parentsInited";
    @Autowired
    private VaBaseDataDao baseDataDao;
    @Autowired
    private BaseDataParamService baseDataParamService;
    @Autowired
    private BaseDataQueryService baseDataQueryService;
    @Autowired
    private BaseDataCacheService baseDataCacheService;
    @Autowired
    private CommonDao commonDao;
    @Autowired
    private DataModelClient dataModelClient;
    @Autowired
    private BaseDataCacheCoordinationService coordinationService;
    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

    public R add(BaseDataDTO basedata) {
        R r;
        R r2;
        String tableName = basedata.getTableName();
        if (!this.coordinationService.isCanLoadByCurrentNode(tableName)) {
            BaseDataClient client = this.coordinationService.getClient(tableName);
            if (client != null) {
                basedata.remove((Object)BASEDATA_BATCH_DAO);
                return client.add(basedata);
            }
            throw new RuntimeException("\u8bf7\u6c42\u65e0\u6cd5\u5904\u7406");
        }
        String basedataCode = basedata.getCode();
        if (tableName == null || basedataCode == null) {
            return R.error((String)BaseDataCoreI18nUtil.getParamMissingMsg());
        }
        R checkRs = this.baseDataParamService.modifyVersionCheck(basedata);
        if (checkRs.getCode() != 0) {
            return checkRs;
        }
        if (basedataCode.contains(";") || basedataCode.contains(",") || basedataCode.contains("/")) {
            return R.error((String)BaseDataCoreI18nUtil.getMessage("basedata.error.bddata.code.contain.special.characters", new Object[0]));
        }
        if (basedataCode.replaceAll("\\s*", "").length() != basedataCode.length()) {
            return R.error((String)BaseDataCoreI18nUtil.getMessage("basedata.error.bddata.code.contain.spaces.or.tabs", new Object[0]));
        }
        if (!(basedata.containsKey((Object)BASEDATA_CODE_IGNORECASE) && ((Boolean)basedata.get((Object)BASEDATA_CODE_IGNORECASE)).booleanValue() || basedataCode.toUpperCase().equals(basedataCode))) {
            return R.error((String)BaseDataCoreI18nUtil.getMessage("basedata.error.bddata.code.needs.capitalized", new Object[0]));
        }
        this.basedataTrim(basedata);
        BaseDataDefineDO defineDO = this.baseDataParamService.getBaseDataDefineDO((BaseDataDO)basedata);
        tableName = defineDO.getName();
        basedata.setTableName(tableName);
        if (defineDO.getStructtype() == 3 && (r2 = this.setBasedataLevelCode((BaseDataDO)basedata)).getCode() != 0) {
            return r2;
        }
        Integer recoveryflag = basedata.getRecoveryflag();
        basedata.setRecoveryflag(Integer.valueOf(-1));
        Integer stopflag = basedata.getStopflag();
        basedata.setStopflag(Integer.valueOf(-1));
        R rs = this.baseDataQueryService.exist(basedata);
        if (((Boolean)rs.get((Object)"exist")).booleanValue()) {
            BaseDataDO obj = (BaseDataDO)rs.get((Object)"data");
            if (obj.getRecoveryflag() == null || obj.getRecoveryflag() == 0) {
                return R.error((int)201, (String)BaseDataCoreI18nUtil.getMessage("basedata.error.bddata.check.existed", new Object[0]));
            }
            return R.error((int)202, (String)BaseDataCoreI18nUtil.getMessage("basedata.error.bddata.add.check.recycle", new Object[0]));
        }
        if (recoveryflag != null) {
            basedata.setRecoveryflag(recoveryflag);
        } else {
            basedata.remove((Object)"recoveryflag");
        }
        if (stopflag != null) {
            basedata.setStopflag(stopflag);
        } else {
            basedata.remove((Object)"stopflag");
        }
        int sharetype = defineDO.getSharetype();
        if (sharetype == 3 && "-".equals(basedata.getUnitcode())) {
            String cntSqlInsulate = "select count(0) from %s where CODE = '%s' and UNITCODE <> '-'";
            SqlDTO sqlDTO = new SqlDTO(basedata.getTenantName(), String.format(cntSqlInsulate, tableName, basedata.getCode()));
            if (this.commonDao.countBySql(sqlDTO) > 0) {
                return R.error((String)BaseDataCoreI18nUtil.getMessage("basedata.error.bddata.code.check.in.another.org", new Object[0]));
            }
        }
        this.initFieldVal((BaseDataDO)basedata);
        if (!StringUtils.hasText(basedata.getParents())) {
            this.setBaseDataParents(basedata);
        }
        if ((r = this.baseDataParamService.checkFieldRepeated(basedata, null)).getCode() != 0) {
            return r;
        }
        this.baseDataParamService.encodeSecurityFields((BaseDataDO)basedata);
        r = this.baseDataParamService.checkField(basedata, null);
        if (r.getCode() != 0) {
            return r;
        }
        int flag = 0;
        try {
            if (basedata.containsKey((Object)BASEDATA_BATCH_DAO)) {
                ((VaBaseDataDao)basedata.get((Object)BASEDATA_BATCH_DAO)).add(basedata);
                flag = 1;
            } else {
                flag = this.baseDataDao.add(basedata);
            }
        }
        catch (Exception e) {
            logger.error("\u65b0\u589e\u5165\u5e93\u5931\u8d25: " + JSONUtil.toJSONString((Object)basedata), e);
            return R.error((String)BaseDataCoreI18nUtil.getMessage("basedata.error.bddata.add", new Object[0]));
        }
        if (flag > 0) {
            this.updateDetailData(basedata);
            basedata.setQueryStartVer(basedata.getVer());
            this.updateCache(basedata, BaseDataOption.EventType.ADD, true);
            rs = R.ok((String)BaseDataCoreI18nUtil.getOptSuccessMsg());
            rs.put("finalData", (Object)basedata);
            return rs;
        }
        return R.error((String)BaseDataCoreI18nUtil.getOptFailureMsg());
    }

    public void initFieldVal(BaseDataDO basedata) {
        BaseDataDefineDO defineDO;
        int sharetype;
        Integer recoveryflag;
        Integer stopflag;
        UserLoginDTO user;
        if (basedata.containsKey((Object)INIT_FIELD_FINISHED)) {
            return;
        }
        if (basedata.getId() == null) {
            basedata.setId(UUID.randomUUID());
        }
        BigDecimal ver = null;
        ver = basedata.containsKey((Object)BASEDATA_FIXED_VER) ? (BigDecimal)basedata.get((Object)BASEDATA_FIXED_VER) : OrderNumUtil.getOrderNumByCurrentTimeMillis();
        basedata.setVer(ver);
        if (basedata.getCreateuser() == null && (user = ShiroUtil.getUser()) != null) {
            basedata.setCreateuser(user.getId());
        }
        if (basedata.getCreatetime() == null) {
            basedata.setCreatetime(new Date());
        }
        if ((stopflag = basedata.getStopflag()) == null || stopflag != 0 && stopflag != 1) {
            basedata.setStopflag(Integer.valueOf(0));
        }
        if ((recoveryflag = basedata.getRecoveryflag()) == null || recoveryflag != 0 && recoveryflag != 1) {
            basedata.setRecoveryflag(Integer.valueOf(0));
        }
        if (basedata.getOrdinal() == null) {
            basedata.setOrdinal(ver);
        }
        if ((sharetype = (defineDO = this.baseDataParamService.getBaseDataDefineDO(basedata)).getSharetype().intValue()) == 0) {
            basedata.setUnitcode("-");
        }
        if (defineDO.getStructtype() < 2) {
            basedata.setParentcode("-");
            basedata.setParents(basedata.getCode());
        }
        if (basedata.getObjectcode() == null) {
            if (basedata.get((Object)BASEDATA_OBJCODE_SUFFIX) != null) {
                basedata.setObjectcode(basedata.getCode() + basedata.get((Object)BASEDATA_OBJCODE_SUFFIX).toString());
            } else if (sharetype == 0 || sharetype == 3 && basedata.getUnitcode().equals("-")) {
                basedata.setObjectcode(basedata.getCode());
            } else {
                List sharefields = (List)basedata.get((Object)"sharefields");
                StringBuilder objcode = new StringBuilder(basedata.getCode());
                for (String field : sharefields) {
                    objcode.append("||").append(basedata.get((Object)field));
                }
                basedata.setObjectcode(objcode.toString());
            }
        }
        if (!StringUtils.hasText(basedata.getParentcode())) {
            basedata.setParentcode("-");
        }
        if (basedata.getParentcode().equals("-")) {
            basedata.setParents(basedata.getCode());
        }
        this.baseDataParamService.setVersionInfo(basedata);
        this.handleSensitive(basedata);
        basedata.put(INIT_FIELD_FINISHED, (Object)true);
    }

    public R setBasedataLevelCode(BaseDataDO basedata) {
        if (basedata.containsKey((Object)"initLevelCodeFinished")) {
            return R.ok();
        }
        BaseDataDefineDO define = this.baseDataParamService.getBaseDataDefineDO(basedata);
        String code = basedata.getCode();
        String levelcode = define.getLevelcode();
        int maxLength = 0;
        String[] objs = levelcode.split("\\#");
        String lvcode = objs[0];
        if (objs.length > 1) {
            maxLength = Integer.parseInt(objs[1]);
        } else {
            for (int i = 0; i < lvcode.length(); ++i) {
                maxLength += Integer.parseInt(lvcode.substring(i, i + 1));
            }
        }
        if (code.length() > maxLength) {
            return R.error((String)BaseDataCoreI18nUtil.getMessage("basedata.error.bddata.levelcode.length.exceed", levelcode, maxLength));
        }
        ArrayList<Integer> codePoint = new ArrayList<Integer>();
        int currentPoint = 0;
        int totalPoint = 0;
        for (int i = 0; i < lvcode.length(); ++i) {
            currentPoint = Integer.parseInt(lvcode.substring(i, i + 1));
            codePoint.add(totalPoint += currentPoint);
        }
        if (objs.length > 1) {
            int pointCut = 0;
            for (int i = 0; i < lvcode.length() && code.length() > (pointCut += Integer.parseInt(lvcode.substring(i, i + 1))); ++i) {
                try {
                    if (Integer.parseInt(code.substring(pointCut)) != 0) continue;
                    code = code.substring(0, pointCut);
                    break;
                }
                catch (Exception exception) {
                    // empty catch block
                }
            }
        }
        if (!codePoint.contains(code.length())) {
            return R.error((String)BaseDataCoreI18nUtil.getMessage("basedata.error.bddata.coding.rules", levelcode));
        }
        int pointEnd = 0;
        StringBuilder parents = new StringBuilder();
        String currCode = "";
        String parentCode = "-";
        for (int i = 0; i < lvcode.length() && code.length() >= (pointEnd += Integer.parseInt(lvcode.substring(i, i + 1))); ++i) {
            if (i > 0) {
                parentCode = currCode;
            }
            currCode = code.substring(0, pointEnd);
            if (objs.length > 1) {
                StringBuilder tempStr = new StringBuilder(currCode);
                while (tempStr.length() < maxLength) {
                    tempStr.append("0");
                }
                currCode = tempStr.toString();
            }
            parents.append("/").append(currCode);
        }
        basedata.setCode(currCode);
        basedata.setParentcode(parentCode);
        basedata.setParents(parents.substring(1));
        if ("-".equals(parentCode) || basedata.containsKey((Object)"checkParentDisable")) {
            basedata.put("initLevelCodeFinished", (Object)true);
            return R.ok();
        }
        BaseDataDTO param = new BaseDataDTO();
        param.setTableName(basedata.getTableName());
        this.baseDataParamService.initDefineInfo((BaseDataDO)param);
        if (param.get((Object)"sharefields") != null) {
            List sharefields = (List)param.get((Object)"sharefields");
            for (String field : sharefields) {
                param.put(field, basedata.get((Object)field));
            }
        }
        this.baseDataParamService.initParam(param);
        param.setCode(parentCode);
        R rs = this.baseDataQueryService.exist(param);
        if (!((Boolean)rs.get((Object)"exist")).booleanValue()) {
            return R.error((String)BaseDataCoreI18nUtil.getMessage("basedata.error.bddata.parent.check.not.found", parentCode));
        }
        basedata.put("initLevelCodeFinished", (Object)true);
        return R.ok();
    }

    private void setBaseDataParents(BaseDataDTO currBaseData) {
        String code = currBaseData.getCode();
        String parentcode = currBaseData.getParentcode();
        if (!StringUtils.hasText(parentcode) || parentcode.equals("-")) {
            currBaseData.setParents(code);
            return;
        }
        this.baseDataParamService.initParam(currBaseData);
        BaseDataDTO param = new BaseDataDTO();
        param.setTableName(currBaseData.getTableName());
        if (currBaseData.get((Object)"sharefields") != null) {
            List sharefields = (List)currBaseData.get((Object)"sharefields");
            for (String field : sharefields) {
                param.put(field, currBaseData.get((Object)field));
            }
        }
        param.setCode(parentcode);
        param.setStopflag(Integer.valueOf(-1));
        R rs = this.baseDataQueryService.exist(param);
        if (((Boolean)rs.get((Object)"exist")).booleanValue()) {
            BaseDataDO obj = (BaseDataDO)rs.get((Object)"data");
            currBaseData.setParents(obj.getParents() + "/" + code);
        } else {
            currBaseData.setParentcode("-");
            currBaseData.setParents(code);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void updateDetailData(BaseDataDTO basedata) {
        if (basedata.getId() == null) {
            return;
        }
        if (!basedata.containsKey((Object)"hasMultiValues")) {
            return;
        }
        SqlSession sqlSession = this.sqlSessionTemplate.getSqlSessionFactory().openSession(ExecutorType.BATCH, false);
        CommonDao commonDao = (CommonDao)sqlSession.getMapper(CommonDao.class);
        try {
            String tableName = basedata.getTableName();
            DataModelDTO dataModelDTO = new DataModelDTO();
            dataModelDTO.setName(tableName);
            dataModelDTO.setDeepClone(Boolean.valueOf(false));
            DataModelDO dataModel = this.dataModelClient.get(dataModelDTO);
            LinkedHashMap<String, List> valueMap = new LinkedHashMap<String, List>();
            String colName = null;
            Object value = null;
            for (DataModelColumn column : dataModel.getColumns()) {
                if (column.getMappingType() == null || column.getMappingType() != 1 || !basedata.containsKey((Object)(colName = column.getColumnName().toLowerCase())) && !basedata.containsKey((Object)(colName + "_show")) || !((value = basedata.get((Object)colName)) instanceof ArrayList)) continue;
                valueMap.put(colName.toUpperCase(), (List)value);
            }
            if (valueMap.isEmpty()) {
                return;
            }
            SqlDTO sqlDTO = new SqlDTO(basedata.getTenantName(), null);
            sqlDTO.addParam("tableName", (Object)tableName);
            sqlDTO.addParam("masterid", (Object)basedata.getId());
            sqlDTO.setSql(this.getDeleteSubSql());
            for (Map.Entry filed : valueMap.entrySet()) {
                sqlDTO.addParam("fieldname", filed.getKey());
                commonDao.executeBySql(sqlDTO);
            }
            sqlDTO.setSql(this.getAddSubSql());
            for (Map.Entry filed : valueMap.entrySet()) {
                sqlDTO.addParam("fieldname", filed.getKey());
                int order = 1;
                for (String str : (List)filed.getValue()) {
                    sqlDTO.addParam("id", (Object)UUID.randomUUID());
                    sqlDTO.addParam("fieldvalue", (Object)str);
                    sqlDTO.addParam("ordernum", (Object)order++);
                    commonDao.executeBySql(sqlDTO);
                }
            }
            sqlSession.commit();
            sqlSession.clearCache();
        }
        catch (Exception e) {
            logger.error("\u66f4\u65b0\u591a\u9009\u5f02\u5e38", e);
            sqlSession.rollback();
        }
        finally {
            sqlSession.close();
        }
    }

    public String getAddSubSql() {
        SQL sql = new SQL();
        sql.INSERT_INTO("${param.tableName}_SUBLIST");
        sql.INTO_COLUMNS(new String[]{"ID", "MASTERID", "FIELDNAME", "FIELDVALUE", "ORDERNUM"});
        sql.INTO_VALUES(new String[]{"#{param.id, jdbcType=VARCHAR}", "#{param.masterid, jdbcType=VARCHAR}", "#{param.fieldname, jdbcType=VARCHAR}", "#{param.fieldvalue, jdbcType=VARCHAR}", "#{param.ordernum, jdbcType=NUMERIC}"});
        return sql.toString();
    }

    public String getDeleteSubSql() {
        SQL sql = new SQL();
        sql.DELETE_FROM("${param.tableName}_SUBLIST");
        sql.WHERE("MASTERID = #{param.masterid, jdbcType=VARCHAR}");
        sql.WHERE("FIELDNAME = #{param.fieldname, jdbcType=VARCHAR}");
        return sql.toString();
    }

    private void basedataTrim(BaseDataDTO basedata) {
        if (basedata == null) {
            return;
        }
        for (Map.Entry entry : basedata.entrySet()) {
            if (!(entry.getValue() instanceof String)) continue;
            basedata.put((String)entry.getKey(), (Object)entry.getValue().toString().trim());
        }
    }

    public R update(BaseDataDTO basedata) {
        String tableName = basedata.getTableName();
        if (tableName == null) {
            return R.error((String)BaseDataCoreI18nUtil.getParamMissingMsg());
        }
        if (!this.coordinationService.isCanLoadByCurrentNode(tableName)) {
            BaseDataClient client = this.coordinationService.getClient(tableName);
            if (client != null) {
                basedata.remove((Object)BASEDATA_BATCH_DAO);
                return client.update(basedata);
            }
            throw new RuntimeException("\u8bf7\u6c42\u65e0\u6cd5\u5904\u7406");
        }
        R checkRs = this.baseDataParamService.modifyVersionCheck(basedata);
        if (checkRs.getCode() != 0) {
            return checkRs;
        }
        BaseDataDefineDO defineDO = this.baseDataParamService.getBaseDataDefineDO((BaseDataDO)basedata);
        tableName = defineDO.getName();
        basedata.setTableName(tableName);
        this.basedataTrim(basedata);
        Integer recoveryflag = basedata.getRecoveryflag();
        basedata.setRecoveryflag(Integer.valueOf(-1));
        Integer stopflag = basedata.getStopflag();
        basedata.setStopflag(Integer.valueOf(-1));
        R rs = this.baseDataQueryService.exist(basedata);
        BaseDataDTO oldData = new BaseDataDTO();
        if (!rs.containsKey((Object)"exist") || !((Boolean)rs.get((Object)"exist")).booleanValue()) {
            return R.error((int)301, (String)BaseDataCoreI18nUtil.getMessage("basedata.error.bddata.not.exist", new Object[0]));
        }
        oldData.putAll((Map)((BaseDataDO)rs.get((Object)"data")));
        if (basedata.getVer() != null && basedata.getVer().compareTo(oldData.getVer()) < 0) {
            return R.error((String)BaseDataCoreI18nUtil.getMessage("basedata.info.bddata.ver.changed", new Object[0]));
        }
        if (recoveryflag != null) {
            basedata.setRecoveryflag(recoveryflag);
        } else {
            basedata.remove((Object)"recoveryflag");
        }
        if (stopflag != null) {
            basedata.setStopflag(stopflag);
        } else {
            basedata.remove((Object)"stopflag");
        }
        this.updateField((BaseDataDO)basedata);
        R r = this.baseDataParamService.checkFieldRepeated(basedata, oldData);
        if (r.getCode() != 0) {
            return r;
        }
        this.baseDataParamService.encodeSecurityFields((BaseDataDO)basedata);
        r = this.baseDataParamService.checkField(basedata, oldData);
        if (r.getCode() != 0) {
            return r;
        }
        if (basedata.getUnitcode() != null && "-".equals(oldData.getUnitcode())) {
            basedata.setUnitcode("-");
        }
        boolean parentChange = false;
        if (basedata.containsKey((Object)"parentcode") && !basedata.containsKey((Object)BASEDATA_PARENTS_INITED)) {
            if (!StringUtils.hasText(basedata.getParentcode())) {
                basedata.setParentcode("-");
            }
            if (!basedata.getParentcode().equals(oldData.getParentcode())) {
                this.setBaseDataParents(basedata);
                parentChange = true;
            }
        }
        this.checkAndAddVersionData(basedata, oldData);
        basedata.setId(oldData.getId());
        int flag = 0;
        try {
            if (basedata.containsKey((Object)BASEDATA_BATCH_DAO)) {
                ((VaBaseDataDao)basedata.get((Object)BASEDATA_BATCH_DAO)).update(basedata);
                flag = 1;
            } else {
                flag = this.baseDataDao.update(basedata);
            }
        }
        catch (Exception e) {
            logger.error("\u66f4\u65b0\u5165\u5e93\u5931\u8d25: " + JSONUtil.toJSONString((Object)basedata), e);
            return R.error((String)BaseDataCoreI18nUtil.getMessage("basedata.error.bddata.update", new Object[0]));
        }
        if (flag > 0) {
            this.updateDetailData(basedata);
            if (parentChange) {
                oldData.setTableName(basedata.getTableName());
                String oldParents = oldData.getParents();
                oldData.setParents(basedata.getParents());
                this.updateChildrenParents((BaseDataDO)oldData, oldParents);
            }
            basedata.setQueryStartVer(basedata.getVer());
            this.updateCache(basedata, BaseDataOption.EventType.UPDATE, true);
            rs = R.ok((String)BaseDataCoreI18nUtil.getOptSuccessMsg());
            rs.put("finalData", (Object)basedata);
            return rs;
        }
        return R.error((String)BaseDataCoreI18nUtil.getOptFailureMsg());
    }

    private void checkAndAddVersionData(BaseDataDTO newData, BaseDataDTO oldData) {
        BaseDataDefineDO defineDO = this.baseDataParamService.getBaseDataDefineDO((BaseDataDO)newData);
        Integer defineVersionflag = defineDO.getVersionflag();
        if (defineVersionflag == null || defineVersionflag == 0) {
            return;
        }
        if (oldData.getValidtime() == null) {
            oldData.setValidtime(BaseDataConsts.VERSION_MIN_DATE);
        }
        if (oldData.getInvalidtime() == null) {
            oldData.setInvalidtime(BaseDataConsts.VERSION_MAX_DATE);
        }
        Date newValidtime = newData.getValidtime();
        Date newInvalidtime = newData.getInvalidtime();
        Date oldValidtime = oldData.getValidtime();
        Date oldInvalidtime = oldData.getInvalidtime();
        boolean validFlag = false;
        boolean invalidFlag = false;
        if (newValidtime.compareTo(oldValidtime) == 0) {
            validFlag = true;
        }
        if (newInvalidtime.compareTo(oldInvalidtime) == 0) {
            invalidFlag = true;
        }
        if (validFlag && invalidFlag) {
            return;
        }
        BaseDataDTO copyData = new BaseDataDTO();
        copyData.putAll((Map)newData);
        copyData.putAll((Map)oldData);
        if (!validFlag) {
            copyData.setId(UUID.randomUUID());
            copyData.setValidtime(oldValidtime);
            copyData.setInvalidtime(newValidtime);
            copyData.setVer(OrderNumUtil.getOrderNumByCurrentTimeMillis());
            try {
                this.baseDataDao.add(copyData);
                this.updateDetailData(copyData);
            }
            catch (Exception e) {
                logger.error("\u751f\u6548\u65f6\u95f4\u5dee\u5f02\u7248\u672c\u5165\u5e93\u5931\u8d25: " + JSONUtil.toJSONString((Object)copyData), e);
            }
        }
        if (!invalidFlag) {
            copyData.setId(UUID.randomUUID());
            copyData.setValidtime(newInvalidtime);
            copyData.setInvalidtime(oldInvalidtime);
            copyData.setVer(OrderNumUtil.getOrderNumByCurrentTimeMillis());
            try {
                this.baseDataDao.add(copyData);
                this.updateDetailData(copyData);
            }
            catch (Exception e) {
                logger.error("\u5931\u6548\u65f6\u95f4\u5dee\u5f02\u7248\u672c\u5165\u5e93\u5931\u8d25: " + JSONUtil.toJSONString((Object)copyData), e);
            }
        }
    }

    public void updateField(BaseDataDO basedata) {
        Integer recoveryflag;
        if (basedata.containsKey((Object)INIT_FIELD_FINISHED)) {
            return;
        }
        BaseDataDefineDO defineDO = this.baseDataParamService.getBaseDataDefineDO(basedata);
        int structtype = defineDO.getStructtype();
        if (structtype < 2 || structtype == 3) {
            basedata.remove((Object)"parentcode");
            basedata.remove((Object)"parentcode_show");
            basedata.remove((Object)"parents");
            basedata.remove((Object)"parents_show");
        }
        if (basedata.containsKey((Object)BASEDATA_FIXED_VER)) {
            basedata.setVer((BigDecimal)basedata.get((Object)BASEDATA_FIXED_VER));
        } else {
            basedata.setVer(OrderNumUtil.getOrderNumByCurrentTimeMillis());
        }
        this.baseDataParamService.setVersionInfo(basedata);
        basedata.remove((Object)"objectcode");
        basedata.remove((Object)"objectcode_show");
        Integer stopflag = basedata.getStopflag();
        if (stopflag == null || stopflag == -1) {
            basedata.remove((Object)"stopflag");
        }
        if ((recoveryflag = basedata.getRecoveryflag()) == null || recoveryflag == -1) {
            basedata.remove((Object)"recoveryflag");
        }
        if (basedata.containsKey((Object)"parents") && !StringUtils.hasText(basedata.getParents())) {
            basedata.remove((Object)"parents");
            basedata.remove((Object)"parents_show");
        }
        if (basedata.containsKey((Object)"ordinal") && basedata.getOrdinal() == null) {
            basedata.remove((Object)"ordinal");
        }
        this.handleSensitive(basedata);
        basedata.put(INIT_FIELD_FINISHED, (Object)true);
    }

    private void handleSensitive(BaseDataDO basedata) {
        Map<String, String> sensitiveFields = this.baseDataParamService.getSensitiveFields(basedata);
        if (sensitiveFields != null && !sensitiveFields.isEmpty()) {
            Object val = null;
            String columnName = null;
            for (Map.Entry<String, String> entry : sensitiveFields.entrySet()) {
                columnName = entry.getKey();
                val = basedata.get((Object)columnName);
                if (val == null || !val.toString().contains("*")) continue;
                basedata.remove((Object)columnName);
                basedata.remove((Object)(columnName + "_show"));
            }
        }
    }

    private void updateChildrenParents(BaseDataDO basedata, String oldParents) {
        BaseDataDTO param = new BaseDataDTO();
        param.setTableName(basedata.getTableName());
        BaseDataDefineDO defineDO = this.baseDataParamService.getBaseDataDefineDO((BaseDataDO)param);
        if (defineDO.getStructtype() != 2) {
            return;
        }
        if (param.get((Object)"sharefields") != null) {
            List sharefields = (List)param.get((Object)"sharefields");
            for (String field : sharefields) {
                param.put(field, basedata.get((Object)field));
            }
        }
        this.baseDataParamService.initParam(param);
        param.setCode(basedata.getCode());
        param.setQueryChildrenType(BaseDataOption.QueryChildrenType.ALL_CHILDREN);
        param.setAuthType(BaseDataOption.AuthType.NONE);
        PageVO<BaseDataDO> page = this.baseDataQueryService.list(param);
        if (page != null && page.getTotal() > 0) {
            String newParents = basedata.getParents();
            int point = oldParents != null ? oldParents.length() : 0;
            param.remove((Object)"code");
            BaseDataDTO bddto = new BaseDataDTO();
            for (BaseDataDO baseDataDO : page.getRows()) {
                bddto.clear();
                bddto.putAll((Map)param);
                bddto.setId(baseDataDO.getId());
                bddto.setParents(newParents + baseDataDO.getParents().substring(point));
                bddto.put("unCheck", (Object)true);
                bddto.setCacheSyncDisable(Boolean.valueOf(true));
                this.update(bddto);
            }
        }
    }

    public R remove(BaseDataDTO baseDataDTO) {
        if (baseDataDTO.getTableName() == null || baseDataDTO.getId() == null) {
            return R.error((String)BaseDataCoreI18nUtil.getParamMissingMsg());
        }
        String tableName = baseDataDTO.getTableName();
        if (!this.coordinationService.isCanLoadByCurrentNode(tableName)) {
            BaseDataClient client = this.coordinationService.getClient(tableName);
            if (client != null) {
                return client.remove(baseDataDTO);
            }
            throw new RuntimeException("\u8bf7\u6c42\u65e0\u6cd5\u5904\u7406");
        }
        BaseDataDTO param = new BaseDataDTO();
        param.setTableName(tableName);
        param.setForceRemove(Boolean.valueOf(baseDataDTO.isForceRemove()));
        param.setConfrimRelatedRemove(Boolean.valueOf(baseDataDTO.isConfrimRelatedRemove()));
        param.setForceUpdateHistoryVersionData(Boolean.valueOf(baseDataDTO.isForceUpdateHistoryVersionData()));
        R checkRs = this.baseDataParamService.modifyVersionCheck(param);
        if (checkRs.getCode() != 0) {
            return checkRs;
        }
        param.setId(baseDataDTO.getId());
        R rs = this.checkRemove(param);
        if (rs.getCode() != 0) {
            return rs;
        }
        param.setRecoveryflag(Integer.valueOf(1));
        param.put("unCheck", (Object)true);
        rs = this.update(param);
        if (rs.getCode() == 0) {
            baseDataDTO.setRecoveryflag(Integer.valueOf(1));
            this.updateSubTableStatus(baseDataDTO, 1);
        }
        return rs;
    }

    private R checkRemove(BaseDataDTO param) {
        BaseDataDO old = null;
        R rs = this.baseDataQueryService.exist(param);
        if (!((Boolean)rs.get((Object)"exist")).booleanValue()) {
            return R.error((String)BaseDataCoreI18nUtil.getMessage("basedata.error.bddata.not.exist", new Object[0]));
        }
        old = (BaseDataDO)rs.get((Object)"data");
        BaseDataDefineDO defineDO = this.baseDataParamService.getBaseDataDefineDO((BaseDataDO)param);
        BaseDataDTO param2 = new BaseDataDTO();
        param2.setTableName(param.getTableName());
        int defineStructtype = defineDO.getStructtype();
        if (defineStructtype == 2 || defineStructtype == 3) {
            PageVO<BaseDataDO> page;
            if (!this.baseDataParamService.isCacheDisabled((BaseDataDO)param)) {
                param2.setCode(old.getCode());
                param2.setQueryChildrenType(BaseDataOption.QueryChildrenType.DIRECT_CHILDREN);
                param2.setQueryDataStructure(BaseDataOption.QueryDataStructure.ALL);
                if (param.containsKey((Object)"sharefields")) {
                    List sharefields = (List)param.get((Object)"sharefields");
                    for (String fields : sharefields) {
                        param2.put(fields, old.get((Object)fields));
                    }
                    param2.put("sharefields", param.get((Object)"sharefields"));
                }
            } else {
                param2.setParentcode(old.getCode());
            }
            if ((page = this.baseDataQueryService.list(param2)) != null && page.getTotal() > 0) {
                return R.error((String)BaseDataCoreI18nUtil.getMessage("basedata.error.bddata.remove.check.subordinates", new Object[0]));
            }
        }
        param2.setObjectcode(old.getObjectcode());
        Boolean forceRemove = param.isForceRemove();
        if (!forceRemove.booleanValue() && this.baseDataParamService.checkRelated((BaseDataDO)param2)) {
            if (param.isConfrimRelatedRemove()) {
                return R.error((String)BaseDataCoreI18nUtil.getMessage("basedata.confirm.bddata.remove.check.referenced", param2.get((Object)"relateInfoCheck"))).put("isRelated", (Object)true);
            }
            return R.error((String)BaseDataCoreI18nUtil.getMessage("basedata.error.bddata.remove.check.referenced", param2.get((Object)"relateInfoCheck")));
        }
        return R.ok();
    }

    public R batchRemove(BaseDataBatchOptDTO baseDataDTO) {
        List list = baseDataDTO.getDataList();
        if (list == null || list.isEmpty()) {
            return R.error((String)BaseDataCoreI18nUtil.getMessage("basedata.error.parameter.data.empty", new Object[0]));
        }
        BaseDataDTO param = baseDataDTO.getQueryParam();
        String tableName = param.getTableName();
        if (!this.coordinationService.isCanLoadByCurrentNode(tableName)) {
            BaseDataClient client = this.coordinationService.getClient(tableName);
            if (client != null) {
                return client.batchRemove(baseDataDTO);
            }
            throw new RuntimeException("\u8bf7\u6c42\u65e0\u6cd5\u5904\u7406");
        }
        R checkRs = this.baseDataParamService.modifyVersionCheck(param);
        if (checkRs.getCode() != 0) {
            return checkRs;
        }
        BigDecimal startVer = OrderNumUtil.getOrderNumByCurrentTimeMillis();
        BaseDataDTO tempData = new BaseDataDTO();
        ArrayList<R> results = new ArrayList<R>();
        for (BaseDataDO data : list) {
            R result;
            if (data.getId() == null) {
                result = new R();
                result.put("key", (Object)data.getCode());
                result.put("msg", (Object)"\u7f3a\u5c11\u8bf7\u6c42\u53c2\u6570");
                results.add(result);
                continue;
            }
            tempData.clear();
            tempData.putAll((Map)param);
            tempData.setId(data.getId());
            result = this.checkRemove(tempData);
            if (result.getCode() != 0) {
                result.put("key", (Object)data.getCode());
                results.add(result);
                continue;
            }
            tempData.setRecoveryflag(Integer.valueOf(1));
            tempData.put("unCheck", (Object)true);
            tempData.setCacheSyncDisable(Boolean.valueOf(true));
            result = this.update(tempData);
            result.put("key", (Object)data.getCode());
            results.add(result);
            if (result.getCode() != 0) continue;
            tempData.setCode(data.getCode());
            tempData.setExtInfo(baseDataDTO.getExtInfo());
            if (param.get((Object)"sharefields") != null) {
                List sharefields = (List)param.get((Object)"sharefields");
                for (String field : sharefields) {
                    if (data.get((Object)field) != null) {
                        tempData.put(field, data.get((Object)field));
                        continue;
                    }
                    tempData.put(field, param.get((Object)field));
                }
            }
            this.updateSubTableStatus(tempData, 1);
        }
        param.setCacheSyncDisable(Boolean.valueOf(false));
        param.setQueryStartVer(startVer);
        this.updateCache(param, null, false);
        R r = R.ok((String)BaseDataCoreI18nUtil.getOptSuccessMsg());
        r.put("results", results);
        return r;
    }

    public R stop(BaseDataDTO param) {
        if (param.getTableName() == null || param.getId() == null || param.getStopflag() == null) {
            return R.error((String)BaseDataCoreI18nUtil.getParamMissingMsg());
        }
        String tableName = param.getTableName();
        if (!this.coordinationService.isCanLoadByCurrentNode(tableName)) {
            BaseDataClient client = this.coordinationService.getClient(tableName);
            if (client != null) {
                return client.stop(param);
            }
            throw new RuntimeException("\u8bf7\u6c42\u65e0\u6cd5\u5904\u7406");
        }
        BaseDataDTO base = new BaseDataDTO();
        base.setTableName(param.getTableName());
        base.setForceUpdateHistoryVersionData(Boolean.valueOf(param.isForceUpdateHistoryVersionData()));
        R checkRs = this.baseDataParamService.modifyVersionCheck(base);
        if (checkRs.getCode() != 0) {
            return checkRs;
        }
        base.setId(param.getId());
        base.setStopflag(param.getStopflag());
        base.put("unCheck", (Object)true);
        this.update(base);
        this.updateSubTableStatus(param, 0);
        int stopflag = param.getStopflag();
        if (stopflag == 1 || stopflag == 0 && param.isUpdateChildItemStopFlag()) {
            BaseDataDefineDO defineDO = this.baseDataParamService.getBaseDataDefineDO((BaseDataDO)base);
            int defineStructType = defineDO.getStructtype();
            if (defineStructType != 2 && defineStructType != 3) {
                return R.ok((String)BaseDataCoreI18nUtil.getOptSuccessMsg());
            }
            BaseDataDTO chldParam = new BaseDataDTO();
            chldParam.setTableName(param.getTableName());
            if (base.get((Object)"sharefields") != null) {
                List sharefields = (List)base.get((Object)"sharefields");
                for (String field : sharefields) {
                    chldParam.put(field, param.get((Object)field));
                }
            }
            chldParam.setId(param.getId());
            chldParam.setStopflag(Integer.valueOf(-1));
            chldParam.setQueryChildrenType(BaseDataOption.QueryChildrenType.ALL_CHILDREN);
            chldParam.setAuthType(BaseDataOption.AuthType.NONE);
            PageVO<BaseDataDO> page = this.baseDataQueryService.list(chldParam);
            if (page != null && page.getTotal() > 0) {
                BaseDataDTO chldUpParam = new BaseDataDTO();
                chldUpParam.setTableName(param.getTableName());
                chldUpParam.setAuthType(BaseDataOption.AuthType.NONE);
                chldUpParam.put("unCheck", (Object)true);
                chldUpParam.setCacheSyncDisable(Boolean.valueOf(true));
                if (base.get((Object)"sharefields") != null) {
                    List sharefields = (List)base.get((Object)"sharefields");
                    for (String field : sharefields) {
                        chldUpParam.put(field, param.get((Object)field));
                    }
                }
                chldUpParam.setExtInfo(param.getExtInfo());
                BigDecimal startVer = OrderNumUtil.getOrderNumByCurrentTimeMillis();
                for (BaseDataDO baseData : page.getRows()) {
                    chldUpParam.setId(baseData.getId());
                    chldUpParam.setCode(baseData.getCode());
                    chldUpParam.setStopflag(param.getStopflag());
                    this.update(chldUpParam);
                    this.updateSubTableStatus(chldUpParam, 0);
                }
                chldUpParam.setCacheSyncDisable(Boolean.valueOf(false));
                chldUpParam.setQueryStartVer(startVer);
                this.updateCache(chldUpParam, BaseDataOption.EventType.UPDATE, false);
            }
        }
        return R.ok((String)BaseDataCoreI18nUtil.getOptSuccessMsg());
    }

    private void updateSubTableStatus(BaseDataDTO param, int action) {
        String subTableName = (String)param.getExtInfo("subTableName");
        if (!StringUtils.hasText(subTableName)) {
            return;
        }
        BaseDataDTO subParam = new BaseDataDTO();
        subParam.setTableName(subTableName);
        subParam.setAuthType(BaseDataOption.AuthType.NONE);
        subParam.setStopflag(Integer.valueOf(-1));
        if (action == 1) {
            subParam.setRecoveryflag(Integer.valueOf(-1));
        }
        Map subMianTableRefInfo = (Map)param.getExtInfo("subMianTableRefInfo");
        for (Map.Entry entry : subMianTableRefInfo.entrySet()) {
            if (param.get(entry.getValue()) == null) {
                return;
            }
            subParam.put((String)entry.getKey(), param.get(entry.getValue()));
        }
        PageVO<BaseDataDO> page = this.baseDataQueryService.list(subParam);
        if (page != null && page.getTotal() > 0) {
            subParam.put("unCheck", (Object)true);
            subParam.setCacheSyncDisable(Boolean.valueOf(true));
            if (action == 1) {
                subParam.setStopflag(null);
                subParam.setRecoveryflag(param.getRecoveryflag());
            } else {
                subParam.setStopflag(param.getStopflag());
                subParam.setRecoveryflag(null);
            }
            BigDecimal startVer = OrderNumUtil.getOrderNumByCurrentTimeMillis();
            for (BaseDataDO bdDO : page.getRows()) {
                subParam.setId(bdDO.getId());
                this.update(subParam);
            }
            subParam.setCacheSyncDisable(Boolean.valueOf(false));
            subParam.setQueryStartVer(startVer);
            this.updateCache(subParam, null, false);
        }
    }

    public R batchStop(BaseDataBatchOptDTO baseDataDTO) {
        List list = baseDataDTO.getDataList();
        if (list == null || list.isEmpty()) {
            return R.error((String)BaseDataCoreI18nUtil.getMessage("basedata.error.parameter.data.empty", new Object[0]));
        }
        BaseDataDTO param = baseDataDTO.getQueryParam();
        String tableName = param.getTableName();
        if (!this.coordinationService.isCanLoadByCurrentNode(tableName)) {
            BaseDataClient client = this.coordinationService.getClient(tableName);
            if (client != null) {
                return client.batchStop(baseDataDTO);
            }
            throw new RuntimeException("\u8bf7\u6c42\u65e0\u6cd5\u5904\u7406");
        }
        R checkRs = this.baseDataParamService.modifyVersionCheck(param);
        if (checkRs.getCode() != 0) {
            return checkRs;
        }
        BigDecimal startVer = OrderNumUtil.getOrderNumByCurrentTimeMillis();
        BaseDataDTO tempData = new BaseDataDTO();
        tempData.setTableName(param.getTableName());
        tempData.setAuthType(BaseDataOption.AuthType.NONE);
        tempData.put("unCheck", (Object)true);
        tempData.setCacheSyncDisable(Boolean.valueOf(true));
        tempData.setExtInfo(baseDataDTO.getExtInfo());
        for (BaseDataDO data : list) {
            if (data.getId() == null || data.getStopflag() == null) {
                return R.error((String)BaseDataCoreI18nUtil.getParamMissingMsg());
            }
            tempData.setId(data.getId());
            tempData.setCode(data.getCode());
            tempData.setStopflag(data.getStopflag());
            R result = this.update(tempData);
            if (result.getCode() != 0) continue;
            if (param.get((Object)"sharefields") != null) {
                List sharefields = (List)param.get((Object)"sharefields");
                for (String field : sharefields) {
                    if (data.get((Object)field) != null) {
                        tempData.put(field, data.get((Object)field));
                        continue;
                    }
                    tempData.put(field, param.get((Object)field));
                }
            }
            this.updateSubTableStatus(tempData, 0);
        }
        param.setCacheSyncDisable(Boolean.valueOf(false));
        param.setQueryStartVer(startVer);
        this.updateCache(param, null, false);
        return R.ok((String)BaseDataCoreI18nUtil.getOptSuccessMsg());
    }

    public R recover(BaseDataDTO baseDataDTO) {
        if (baseDataDTO.getTableName() == null || baseDataDTO.getId() == null) {
            return R.error((String)BaseDataCoreI18nUtil.getParamMissingMsg());
        }
        String tableName = baseDataDTO.getTableName();
        if (!this.coordinationService.isCanLoadByCurrentNode(tableName)) {
            BaseDataClient client = this.coordinationService.getClient(tableName);
            if (client != null) {
                return client.recover(baseDataDTO);
            }
            throw new RuntimeException("\u8bf7\u6c42\u65e0\u6cd5\u5904\u7406");
        }
        BaseDataDTO param = new BaseDataDTO();
        param.setTableName(tableName);
        param.setForceUpdateHistoryVersionData(Boolean.valueOf(baseDataDTO.isForceUpdateHistoryVersionData()));
        R checkRs = this.baseDataParamService.modifyVersionCheck(param);
        if (checkRs.getCode() != 0) {
            return checkRs;
        }
        param.put("unCheck", (Object)true);
        param.setId(baseDataDTO.getId());
        param.setRecoveryflag(Integer.valueOf(0));
        R rs = this.update(param);
        if (rs.getCode() == 0) {
            baseDataDTO.setRecoveryflag(Integer.valueOf(0));
            this.updateSubTableStatus(baseDataDTO, 1);
        }
        return rs;
    }

    public R batchRecover(BaseDataBatchOptDTO baseDataDTO) {
        List list = baseDataDTO.getDataList();
        if (list == null || list.isEmpty()) {
            return R.error((String)BaseDataCoreI18nUtil.getMessage("basedata.error.parameter.data.empty", new Object[0]));
        }
        BaseDataDTO param = baseDataDTO.getQueryParam();
        String tableName = param.getTableName();
        if (!this.coordinationService.isCanLoadByCurrentNode(tableName)) {
            BaseDataClient client = this.coordinationService.getClient(tableName);
            if (client != null) {
                return client.batchRecover(baseDataDTO);
            }
            throw new RuntimeException("\u8bf7\u6c42\u65e0\u6cd5\u5904\u7406");
        }
        R checkRs = this.baseDataParamService.modifyVersionCheck(param);
        if (checkRs.getCode() != 0) {
            return checkRs;
        }
        ArrayList<R> results = new ArrayList<R>();
        BigDecimal startVer = OrderNumUtil.getOrderNumByCurrentTimeMillis();
        BaseDataDTO tempData = new BaseDataDTO();
        String tbName = param.getTableName();
        String parentcode = param.getParentcode();
        for (BaseDataDO data : list) {
            if (data.getId() == null) {
                return R.error((String)BaseDataCoreI18nUtil.getParamMissingMsg());
            }
            tempData.clear();
            tempData.setTableName(tbName);
            tempData.setId(data.getId());
            tempData.setRecoveryflag(Integer.valueOf(0));
            if (StringUtils.hasText(parentcode)) {
                tempData.setCode(data.getCode());
                tempData.setParentcode(parentcode);
            }
            tempData.put("unCheck", (Object)true);
            tempData.setCacheSyncDisable(Boolean.valueOf(true));
            R rs = this.update(tempData);
            rs.put("key", (Object)data.getCode());
            results.add(rs);
            if (rs.getCode() != 0) continue;
            tempData.setCode(data.getCode());
            tempData.setExtInfo(baseDataDTO.getExtInfo());
            if (param.get((Object)"sharefields") != null) {
                List sharefields = (List)param.get((Object)"sharefields");
                for (String field : sharefields) {
                    if (data.get((Object)field) != null) {
                        tempData.put(field, data.get((Object)field));
                        continue;
                    }
                    tempData.put(field, param.get((Object)field));
                }
            }
            this.updateSubTableStatus(tempData, 1);
        }
        param.setCacheSyncDisable(Boolean.valueOf(false));
        param.setQueryStartVer(startVer);
        this.updateCache(param, null, false);
        return R.ok((String)BaseDataCoreI18nUtil.getOptSuccessMsg()).put("results", results);
    }

    public R move(BaseDataMoveDTO baseDataMoveDTO) {
        BaseDataOption.MoveType moveType = baseDataMoveDTO.getMoveType();
        String tableName = baseDataMoveDTO.getTableName();
        UUID currId = baseDataMoveDTO.getId();
        if (moveType == null || currId == null || tableName == null) {
            return R.error((String)BaseDataCoreI18nUtil.getParamMissingMsg());
        }
        if (!this.coordinationService.isCanLoadByCurrentNode(tableName)) {
            BaseDataClient client = this.coordinationService.getClient(tableName);
            if (client != null) {
                return client.move(baseDataMoveDTO);
            }
            throw new RuntimeException("\u8bf7\u6c42\u65e0\u6cd5\u5904\u7406");
        }
        BaseDataDTO sParam = baseDataMoveDTO.getQueryParam();
        BaseDataDTO param = new BaseDataDTO();
        param.setTableName(tableName);
        param.setForceUpdateHistoryVersionData(Boolean.valueOf(sParam.isForceUpdateHistoryVersionData()));
        R checkRs = this.baseDataParamService.modifyVersionCheck(param);
        if (checkRs.getCode() != 0) {
            return checkRs;
        }
        param.setId(currId);
        param.setQueryDataStructure(BaseDataOption.QueryDataStructure.ALL);
        param.setAuthType(BaseDataOption.AuthType.NONE);
        PageVO<BaseDataDO> page = this.baseDataQueryService.list(param);
        if (page == null || page.getTotal() == 0) {
            return R.error((String)BaseDataCoreI18nUtil.getMessage("basedata.error.bddata.not.exist", new Object[0]));
        }
        BaseDataDO currBaseData = (BaseDataDO)page.getRows().get(0);
        BigDecimal currOrdinal = currBaseData.getOrdinal();
        this.baseDataParamService.initParam(sParam);
        if (StringUtils.hasText(sParam.getGroupFieldName())) {
            String gfieldName = sParam.getGroupFieldName().toLowerCase();
            sParam.put(gfieldName, currBaseData.get((Object)gfieldName));
        }
        sParam.remove((Object)"code");
        sParam.setPagination(Boolean.valueOf(false));
        sParam.setParentcode(currBaseData.getParentcode());
        sParam.setQueryDataStructure(BaseDataOption.QueryDataStructure.ALL);
        sParam.setStopflag(Integer.valueOf(-1));
        sParam.setAuthType(BaseDataOption.AuthType.NONE);
        PageVO<BaseDataDO> pageData = this.baseDataQueryService.list(sParam);
        List baseDataList = pageData.getRows();
        BaseDataDO targetBaseData = null;
        int targetBaseDataIndex = -1;
        for (int i = 0; i < baseDataList.size(); ++i) {
            if (!currId.equals(((BaseDataDO)baseDataList.get(i)).getId())) continue;
            if (moveType == BaseDataOption.MoveType.UP) {
                targetBaseDataIndex = i - 1;
                break;
            }
            if (moveType != BaseDataOption.MoveType.DOWN) break;
            targetBaseDataIndex = i + 1;
            break;
        }
        if (targetBaseDataIndex < 0 || targetBaseDataIndex >= baseDataList.size()) {
            if (moveType == BaseDataOption.MoveType.UP) {
                return R.error((String)BaseDataCoreI18nUtil.getMessage("basedata.error.common.move.up", new Object[0]));
            }
            return R.error((String)BaseDataCoreI18nUtil.getMessage("basedata.error.common.move.down", new Object[0]));
        }
        targetBaseData = (BaseDataDO)baseDataList.get(targetBaseDataIndex);
        BaseDataDTO tempData = new BaseDataDTO();
        tempData.putAll((Map)param);
        tempData.setId(currId);
        tempData.setOrdinal(targetBaseData.getOrdinal());
        tempData.put("unCheck", (Object)true);
        tempData.setCacheSyncDisable(Boolean.valueOf(true));
        this.update(tempData);
        param.setQueryStartVer(tempData.getVer());
        tempData.clear();
        tempData.putAll((Map)param);
        tempData.setId(targetBaseData.getId());
        tempData.setOrdinal(currOrdinal);
        tempData.put("unCheck", (Object)true);
        tempData.setCacheSyncDisable(Boolean.valueOf(true));
        this.update(tempData);
        this.updateCache(param, null, false);
        return R.ok((String)BaseDataCoreI18nUtil.getOptSuccessMsg());
    }

    private void updateCache(BaseDataDTO param, BaseDataOption.EventType eventType, boolean needSendEvent) {
        if (needSendEvent) {
            PageVO<BaseDataDO> page;
            BaseDataDTO oldData = null;
            if (eventType == BaseDataOption.EventType.UPDATE && (page = this.baseDataQueryService.listAnyWay(param)) != null && page.getTotal() > 0) {
                oldData = new BaseDataDTO();
                oldData.putAll((Map)page.getRows().get(0));
            }
            BaseDataEvent event = new BaseDataEvent((Object)this.getClass().getName());
            event.setEventType(eventType);
            event.setBaseDataOldDTO(oldData);
            event.setBaseDataDTO(param);
            try {
                ApplicationContextRegister.publishEvent((ApplicationEvent)event);
            }
            catch (Throwable e) {
                logger.error("\u57fa\u7840\u6570\u636e\u9879\u53d8\u66f4\u540e\u7f6e\u4e8b\u4ef6\u5f02\u5e38", e);
            }
        }
        if (!param.isCacheSyncDisable()) {
            this.pushSyncMsg(param, false);
        }
    }

    public R pushSyncMsg(BaseDataDTO param, boolean forceUpdate) {
        BaseDataSyncCacheDTO bdsc = new BaseDataSyncCacheDTO();
        bdsc.setTenantName(param.getTenantName());
        bdsc.setBaseDataDTO(param);
        bdsc.setForceUpdate(forceUpdate);
        this.baseDataCacheService.pushSyncMsg(bdsc);
        return R.ok((String)BaseDataCoreI18nUtil.getMessage("basedata.info.bddata.cache.sync.waiting", param.getTableName()));
    }

    public R clearRecovery(BaseDataDTO param) {
        if (!StringUtils.hasText(param.getTableName())) {
            return R.error((String)BaseDataCoreI18nUtil.getParamMissingMsg());
        }
        String tableName = param.getTableName();
        JDialectUtil jDialect = JDialectUtil.getInstance();
        String sqlTemp = "delete from %s where RECOVERYFLAG = 1";
        SqlDTO sqlDTO = new SqlDTO(param.getTenantName(), String.format(sqlTemp, tableName));
        this.commonDao.executeBySql(sqlDTO);
        String subTableName = tableName + "_SUBLIST";
        JTableModel jtm = new JTableModel(param.getTenantName(), subTableName);
        if (jDialect.hasTable(jtm)) {
            sqlTemp = "delete from %s where MASTERID not in (select ID from %s)";
            sqlDTO.setSql(String.format(sqlTemp, subTableName, tableName));
            this.commonDao.executeBySql(sqlDTO);
        }
        this.pushSyncMsg(param, true);
        return R.ok((String)BaseDataCoreI18nUtil.getOptSuccessMsg());
    }

    public R changeShare(BaseDataBatchOptDTO baseDataDTO) {
        List list = baseDataDTO.getDataList();
        ArrayList<R> results = new ArrayList<R>();
        if (list == null || list.isEmpty()) {
            return R.error((String)BaseDataCoreI18nUtil.getParamMissingMsg());
        }
        BaseDataDTO param = baseDataDTO.getQueryParam();
        R checkRs = this.baseDataParamService.modifyVersionCheck(param);
        if (checkRs.getCode() != 0) {
            return checkRs;
        }
        BaseDataDefineDO defineDO = this.baseDataParamService.getBaseDataDefineDO((BaseDataDO)param);
        param.setTableName(defineDO.getName());
        String tableName = param.getTableName();
        List sharefields = (List)param.get((Object)"sharefields");
        String cntSql = "select count(0) from %s where CODE = '%s' and UNITCODE='-'";
        String cntSqlInsulate = "select count(0) from %s where CODE = '%s' and UNITCODE <> '%s'";
        SqlDTO sqlDTO = null;
        BigDecimal startVer = OrderNumUtil.getOrderNumByCurrentTimeMillis();
        param.setQueryStartVer(startVer);
        BigDecimal bd1 = startVer;
        BigDecimal bd2 = new BigDecimal(1.0E-4, new MathContext(19)).setScale(6, RoundingMode.HALF_UP);
        BaseDataDTO currData = new BaseDataDTO();
        currData.setTableName(tableName);
        currData.setCacheSyncDisable(Boolean.valueOf(true));
        for (BaseDataDO data : list) {
            UserLoginDTO currLoginUser;
            String currUnit = data.getUnitcode();
            if (!StringUtils.hasText(currUnit) && (currLoginUser = ShiroUtil.getUser()) != null) {
                currUnit = currLoginUser.getLoginUnit();
            }
            R rs = null;
            if (StringUtils.hasText(currUnit) && this.commonDao.countBySql(sqlDTO = new SqlDTO(baseDataDTO.getTenantName(), String.format(cntSqlInsulate, tableName, data.getCode(), currUnit))) > 0) {
                rs = R.error((String)BaseDataCoreI18nUtil.getMessage("basedata.error.bddata.code.check.in.another.org", new Object[0]));
            }
            if (rs != null) {
                rs.put("key", (Object)data.getCode());
                results.add(rs);
                continue;
            }
            sqlDTO = new SqlDTO(baseDataDTO.getTenantName(), String.format(cntSql, tableName, data.getCode()));
            if (this.commonDao.countBySql(sqlDTO) == 0) {
                currData.setId(data.getId());
                for (String field : sharefields) {
                    currData.put(field, (Object)"-");
                }
                bd1 = bd1.add(bd2);
                currData.setVer(bd1);
                if (this.baseDataDao.update(currData) > 0) {
                    this.updateCache(currData, BaseDataOption.EventType.UPDATE, true);
                    rs = R.ok((String)BaseDataCoreI18nUtil.getOptSuccessMsg());
                } else {
                    rs = R.error();
                }
            } else {
                rs = R.error((String)BaseDataCoreI18nUtil.getMessage("basedata.error.bddata.changeShare.already.shared", new Object[0]));
            }
            rs.put("key", (Object)data.getCode());
            results.add(rs);
        }
        this.updateCache(param, null, false);
        return R.ok((String)BaseDataCoreI18nUtil.getOptSuccessMsg()).put("results", results);
    }
}

