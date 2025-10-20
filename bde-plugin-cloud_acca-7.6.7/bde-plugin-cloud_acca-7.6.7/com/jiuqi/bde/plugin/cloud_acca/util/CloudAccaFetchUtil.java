/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.bizmodel.execute.intf.BalanceCondition
 *  com.jiuqi.bde.common.dto.OrgMappingDTO
 *  com.jiuqi.bde.common.dto.OrgMappingItem
 *  com.jiuqi.bde.penetrate.client.dto.PenetrateBaseDTO
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.dc.datamapping.client.dto.DataRefDTO
 *  com.jiuqi.dc.datamapping.client.dto.DataRefListDTO
 *  com.jiuqi.dc.datamapping.client.vo.DataRefListVO
 *  com.jiuqi.dc.datamapping.impl.service.DataRefListConfigureService
 *  com.jiuqi.dc.mappingscheme.client.dto.DataSchemeDTO
 *  com.jiuqi.dc.mappingscheme.impl.service.BaseDataRefDefineService
 *  com.jiuqi.dc.mappingscheme.impl.service.DataSchemeService
 *  com.jiuqi.dc.mappingscheme.impl.service.DataSourceService
 *  com.jiuqi.va.domain.common.PageVO
 *  javax.annotation.PostConstruct
 *  org.springframework.dao.DataAccessException
 *  org.springframework.jdbc.core.ResultSetExtractor
 */
package com.jiuqi.bde.plugin.cloud_acca.util;

import com.jiuqi.bde.bizmodel.execute.intf.BalanceCondition;
import com.jiuqi.bde.common.dto.OrgMappingDTO;
import com.jiuqi.bde.common.dto.OrgMappingItem;
import com.jiuqi.bde.penetrate.client.dto.PenetrateBaseDTO;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.dc.datamapping.client.dto.DataRefDTO;
import com.jiuqi.dc.datamapping.client.dto.DataRefListDTO;
import com.jiuqi.dc.datamapping.client.vo.DataRefListVO;
import com.jiuqi.dc.datamapping.impl.service.DataRefListConfigureService;
import com.jiuqi.dc.mappingscheme.client.dto.DataSchemeDTO;
import com.jiuqi.dc.mappingscheme.impl.service.BaseDataRefDefineService;
import com.jiuqi.dc.mappingscheme.impl.service.DataSchemeService;
import com.jiuqi.dc.mappingscheme.impl.service.DataSourceService;
import com.jiuqi.va.domain.common.PageVO;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

@Component
@Lazy(value=false)
public class CloudAccaFetchUtil {
    @Autowired
    private DataSourceService dataSourceService;
    @Autowired
    private BaseDataRefDefineService baseDataDefineService;
    private static CloudAccaFetchUtil fetchUtil;
    @Autowired
    private DataSchemeService dataSchemeService;
    @Autowired
    @Qualifier(value="BDEDataRefConfigureService")
    private DataRefListConfigureService dataRefListConfigureService;
    private static final String PREASSBALANCE = "GL_PREASSBALANCE_";
    private static final String BALANCE = "GL_ASSBALANCE_";
    public static final String DEFAULT_ORG_MAPPING_TYPE = "CloudAcca";
    public static final String GL_MAINBODY_ORG_MAPPING_TYPE = "GlMainBody";

    @PostConstruct
    public void init() {
        fetchUtil = this;
        CloudAccaFetchUtil.fetchUtil.dataSourceService = this.dataSourceService;
        CloudAccaFetchUtil.fetchUtil.baseDataDefineService = this.baseDataDefineService;
        CloudAccaFetchUtil.fetchUtil.dataSchemeService = this.dataSchemeService;
        CloudAccaFetchUtil.fetchUtil.dataRefListConfigureService = this.dataRefListConfigureService;
    }

    public static String getBalanceTableName(Boolean includeUnPost, Integer year) {
        includeUnPost = includeUnPost == null || includeUnPost != false;
        return includeUnPost != false ? PREASSBALANCE + year : BALANCE + year;
    }

    public static Map<String, Integer> loadSubject(BalanceCondition condi) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT SUBJECT.CODE, MAX(SUBJECT.ORIENT) AS ORIENT  \n");
        sql.append("  FROM MD_ACCTSUBJECT SUBJECT  \n");
        sql.append(" WHERE (SUBJECT.UNITCODE IN  \n");
        sql.append("       (SELECT ORG.CODE  \n");
        sql.append("           FROM MD_ORG_FIN ORG  \n");
        sql.append("          INNER JOIN (SELECT MDORG.PARENTS  \n");
        sql.append("                       FROM MD_ORG_FIN MDORG  \n");
        sql.append("                      WHERE MDORG.CODE = '%1$s') ORGT  \n");
        sql.append("             ON ORGT.PARENTS LIKE CONCAT(ORG.PARENTS, '%%')) OR SUBJECT.UNITCODE = '-') \n");
        if (!StringUtils.isEmpty((String)condi.getOrgMapping().getAcctBookCode())) {
            sql.append(String.format("   AND SUBJECT.BOOKCODE = '%1$s'  \n", condi.getOrgMapping().getAcctBookCode()));
        }
        sql.append(" GROUP BY SUBJECT.CODE  \n");
        return (Map)CloudAccaFetchUtil.fetchUtil.dataSourceService.query(condi.getOrgMapping().getDataSourceCode(), String.format(sql.toString(), condi.getOrgMapping().getAcctOrgCode()), null, (ResultSetExtractor)new ResultSetExtractor<Map<String, Integer>>(){

            public Map<String, Integer> extractData(ResultSet rs) throws SQLException, DataAccessException {
                HashMap<String, Integer> result = new HashMap<String, Integer>(512);
                while (rs.next()) {
                    result.put(rs.getString(1), rs.getInt(2));
                }
                return result;
            }
        });
    }

    public static String buildGlMainBodyOrgMappingType(BalanceCondition condi, String mainTable) {
        String dataSchemeCode = condi.getOrgMapping().getDataSchemeCode();
        DataSchemeDTO dataScheme = CloudAccaFetchUtil.fetchUtil.dataSchemeService.findByCode(dataSchemeCode);
        String orgMappingType = dataScheme.getDataMapping().getOrgMapping().getOrgMappingType();
        if (!GL_MAINBODY_ORG_MAPPING_TYPE.equals(orgMappingType)) {
            return "";
        }
        return CloudAccaFetchUtil.buildGlMainBodyCondiSql(condi.getOrgMapping(), mainTable);
    }

    public static String penetrateGlMainBodyOrgMappingType(PenetrateBaseDTO condi, String mainTable) {
        DataSchemeDTO dataScheme = condi.getDataScheme();
        String orgMappingType = dataScheme.getDataMapping().getOrgMapping().getOrgMappingType();
        if (!GL_MAINBODY_ORG_MAPPING_TYPE.equals(orgMappingType)) {
            return "";
        }
        return CloudAccaFetchUtil.buildGlMainBodyCondiSql(condi.getOrgMapping(), mainTable);
    }

    private static String buildGlMainBodyCondiSql(OrgMappingDTO orgMapping, String mainTable) {
        String sql;
        if (CollectionUtils.isEmpty((Collection)orgMapping.getOrgMappingItems())) {
            String assistCode = orgMapping.getAssistCode();
            if (StringUtils.isEmpty((String)assistCode)) {
                OrgMappingDTO orgMappingDTO = CloudAccaFetchUtil.rewriteOrgMapping(orgMapping);
                assistCode = orgMappingDTO.getAssistCode();
            }
            sql = String.format("AND %1$s.MD_GL_MAINBODY  = '%2$s'", mainTable, assistCode);
        } else {
            String assistCode = ((OrgMappingItem)orgMapping.getOrgMappingItems().get(0)).getAssistCode();
            OrgMappingDTO orgMappingDTO = orgMapping;
            if (StringUtils.isEmpty((String)assistCode)) {
                orgMappingDTO = CloudAccaFetchUtil.rewriteOrgMapping(orgMapping);
            }
            StringBuilder sqlbuilder = new StringBuilder("AND %1$s.MD_GL_MAINBODY IN (");
            for (OrgMappingItem orgMappingItem : orgMappingDTO.getOrgMappingItems()) {
                if (Objects.isNull(orgMappingItem.getAssistCode())) continue;
                sqlbuilder.append("'").append(orgMappingItem.getAssistCode()).append("',");
            }
            sqlbuilder.deleteCharAt(sqlbuilder.length() - 1).append(")");
            sql = String.format(sqlbuilder.toString(), mainTable);
        }
        return sql;
    }

    private static OrgMappingDTO rewriteOrgMapping(OrgMappingDTO orgMappingDTO) {
        DataRefListDTO dataRefListDTO = new DataRefListDTO();
        dataRefListDTO.setDataSchemeCode(orgMappingDTO.getDataSchemeCode());
        dataRefListDTO.setTableName("MD_ORG");
        dataRefListDTO.setPageNum(Integer.valueOf(1));
        dataRefListDTO.setPageSize(Integer.valueOf(50));
        dataRefListDTO.setPagination(true);
        dataRefListDTO.setFilterType("HASREF");
        HashMap<String, String> filterParam = new HashMap<String, String>();
        filterParam.put("CODE", orgMappingDTO.getReportOrgCode());
        dataRefListDTO.setFilterParam(filterParam);
        DataRefListVO dataRefListVO = CloudAccaFetchUtil.fetchUtil.dataRefListConfigureService.list(dataRefListDTO);
        PageVO pageVo = dataRefListVO.getPageVo();
        if (Objects.isNull(pageVo)) {
            return orgMappingDTO;
        }
        if (pageVo.getTotal() > 1) {
            List dataRefDTOList = pageVo.getRows();
            LinkedList<OrgMappingItem> orgMappingItems = new LinkedList<OrgMappingItem>();
            for (DataRefDTO dataRefDTO : dataRefDTOList) {
                OrgMappingItem orgMappingItem = new OrgMappingItem();
                orgMappingItem.setAcctOrgCode(dataRefDTO.get((Object)"ODS_CODE").toString());
                orgMappingItem.setAcctOrgName(dataRefDTO.get((Object)"ODS_NAME").toString());
                orgMappingItem.setAcctBookCode(dataRefDTO.get((Object)"ODS_BOOKCODE").toString());
                orgMappingItem.setAssistCode(dataRefDTO.get((Object)"ODS_ASSISTCODE").toString());
                orgMappingItem.setAssistName(dataRefDTO.get((Object)"ODS_ASSISTNAME").toString());
                orgMappingItems.add(orgMappingItem);
            }
            orgMappingDTO.setOrgMappingItems(orgMappingItems);
        } else {
            Object odsAssistcode = ((DataRefDTO)pageVo.getRows().get(0)).get((Object)"ODS_ASSISTCODE");
            Object odsAssistname = ((DataRefDTO)pageVo.getRows().get(0)).get((Object)"ODS_ASSISTNAME");
            orgMappingDTO.setAssistCode(Objects.isNull(odsAssistcode) ? null : odsAssistcode.toString());
            orgMappingDTO.setAssistName(Objects.isNull(odsAssistname) ? null : odsAssistname.toString());
        }
        return orgMappingDTO;
    }

    public static String getInCondi(String fieldName, List<String> codes) {
        StringBuilder result = new StringBuilder("(");
        if (null != fieldName && null != codes && codes.size() > 0) {
            StringBuilder temp = new StringBuilder("");
            int index = 0;
            for (String code : codes) {
                temp.append(",'");
                temp.append(code);
                temp.append("'");
                if (1000 != ++index) continue;
                if (result.length() < 2) {
                    result.append(fieldName);
                    result.append(" in (");
                    result.append(temp.substring(1));
                    result.append(")");
                } else {
                    result.append(" or ");
                    result.append(fieldName);
                    result.append(" in (");
                    result.append(temp.substring(1));
                    result.append(")");
                }
                temp.delete(0, temp.length());
                index = 0;
            }
            if (result.length() < 2) {
                result.append(fieldName);
                result.append(" in (");
                result.append(temp.substring(1));
                result.append(")");
            } else if (temp.length() > 0) {
                result.append(" or ");
                result.append(fieldName);
                result.append(" in (");
                result.append(temp.substring(1));
                result.append(")");
            }
        }
        result.append(")");
        return result.toString();
    }
}

