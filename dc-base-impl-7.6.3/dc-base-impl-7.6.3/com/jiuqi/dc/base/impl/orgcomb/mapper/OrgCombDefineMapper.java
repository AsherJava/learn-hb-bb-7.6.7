/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.SqlBuildUtil
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.dc.base.client.orgcomb.vo.OrgCombDefineListVO
 *  com.jiuqi.va.mapper.common.BaseOptMapper
 *  com.jiuqi.va.mapper.common.JDialectUtil
 *  org.apache.ibatis.annotations.Mapper
 *  org.apache.ibatis.annotations.SelectProvider
 */
package com.jiuqi.dc.base.impl.orgcomb.mapper;

import com.jiuqi.common.base.util.SqlBuildUtil;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.dc.base.client.orgcomb.vo.OrgCombDefineListVO;
import com.jiuqi.dc.base.impl.orgcomb.domain.OrgCombDefineDO;
import com.jiuqi.dc.base.impl.orgcomb.dto.OrgCombDefineDTO;
import com.jiuqi.dc.base.impl.orgcomb.dto.OrgCombInfoDTO;
import com.jiuqi.va.mapper.common.BaseOptMapper;
import com.jiuqi.va.mapper.common.JDialectUtil;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;

@Mapper
public interface OrgCombDefineMapper
extends BaseOptMapper<OrgCombDefineDO> {
    @SelectProvider(type=SqlProvider.class, method="getSchemeByGroupId")
    public List<String> getSchemeIdByGroupId(OrgCombDefineDO var1);

    @SelectProvider(type=SqlProvider.class, method="getTableDataTotal")
    public Integer getTableDataTotal(OrgCombDefineDTO var1);

    @SelectProvider(type=SqlProvider.class, method="getTableData")
    public List<OrgCombDefineListVO> getTableData(OrgCombDefineDTO var1);

    @SelectProvider(type=SqlProvider.class, method="checkOrgCombGroupCode")
    public List<String> checkOrgCombGroupCode(OrgCombDefineDO var1);

    @SelectProvider(type=SqlProvider.class, method="getOrgCombDefineBySchemeId")
    public List<OrgCombInfoDTO> getOrgCombDefineBySchemeId(OrgCombDefineDO var1);

    @SelectProvider(type=SqlProvider.class, method="getOrderNumByGroupId")
    public Integer getOrderNumByGroupId(OrgCombDefineDO var1);

    public static class SqlProvider {
        public String getSchemeByGroupId(OrgCombDefineDO orgCombDefineDO) {
            StringBuilder sql = new StringBuilder();
            sql.append("SELECT ID \n");
            sql.append("  FROM ").append("DC_DEFINE_ORGCOMB").append(" \n");
            sql.append(" WHERE GROUPID = #{groupId} \n");
            sql.append(" ORDER BY SORTORDER \n");
            return sql.toString();
        }

        public String getTableData(OrgCombDefineDTO orgCombDefineDTO) {
            StringBuilder sql = new StringBuilder();
            sql.append("SELECT ITEM.ID,  G.TITLE AS GROUPNAME, C.CODE AS SCHEMECODE, C.NAME AS SCHEMENAME, ITEM.ORGCODE, ITEM.EXCLUDEORGCODES ");
            sql.append("  FROM ").append("DC_DEFINE_ORGCOMB").append(" C \n");
            sql.append("  JOIN ").append("DC_DEFINE_ORGCOMBGROUP").append(" G ON C.GROUPID = G.ID \n");
            sql.append("  JOIN ").append("DC_DEFINE_ORGCOMBITEM").append(" ITEM ON ITEM.SCHEMEID = C.ID \n");
            sql.append(" WHERE 1 = 1 \n");
            if (!StringUtils.isEmpty((String)orgCombDefineDTO.getGroupId()) && !orgCombDefineDTO.getGroupId().equals("00000000-0000-0000-0000-000000000000")) {
                sql.append("AND G.ID = #{groupId} \n");
            }
            if (orgCombDefineDTO.getItemDefineIdList() != null && !orgCombDefineDTO.getItemDefineIdList().isEmpty()) {
                sql.append("AND ").append(SqlBuildUtil.getStrInCondi((String)"ITEM.ID", orgCombDefineDTO.getItemDefineIdList())).append(" \n");
            }
            return JDialectUtil.getInstance().pagin(orgCombDefineDTO.getPage().intValue(), orgCombDefineDTO.getPageSize().intValue(), sql.toString());
        }

        public String getTableDataTotal(OrgCombDefineDTO orgCombDefineDTO) {
            StringBuilder sql = new StringBuilder();
            sql.append("SELECT COUNT(1) ");
            sql.append("  FROM ").append("DC_DEFINE_ORGCOMB").append(" C \n");
            sql.append("  JOIN ").append("DC_DEFINE_ORGCOMBGROUP").append(" G ON C.GROUPID = G.ID \n");
            sql.append("  JOIN ").append("DC_DEFINE_ORGCOMBITEM").append(" ITEM ON ITEM.SCHEMEID = C.ID \n");
            sql.append(" WHERE 1 = 1 \n");
            if (!StringUtils.isEmpty((String)orgCombDefineDTO.getGroupId()) && !orgCombDefineDTO.getGroupId().equals("00000000-0000-0000-0000-000000000000")) {
                sql.append("AND G.ID = #{groupId} \n");
            }
            if (orgCombDefineDTO.getItemDefineIdList() != null && !orgCombDefineDTO.getItemDefineIdList().isEmpty()) {
                sql.append("AND ").append(SqlBuildUtil.getStrInCondi((String)"ITEM.ID", orgCombDefineDTO.getItemDefineIdList())).append(" \n");
            }
            return sql.toString();
        }

        public String checkOrgCombGroupCode(OrgCombDefineDO orgCombDefineDO) {
            StringBuilder sql = new StringBuilder();
            sql.append("SELECT ID ");
            sql.append("  FROM ").append("DC_DEFINE_ORGCOMB").append(" \n");
            sql.append(" WHERE CODE = #{code} \n");
            return sql.toString();
        }

        public String getOrgCombDefineBySchemeId(OrgCombDefineDO orgCombDefineDO) {
            StringBuilder sql = new StringBuilder();
            sql.append("SELECT T.CODE, T.NAME, T.REMARK, ITEM.ORGCODE, ITEM.EXCLUDEORGCODES");
            sql.append("  FROM ").append("DC_DEFINE_ORGCOMB").append(" T \n");
            sql.append(" INNER JOIN ").append("DC_DEFINE_ORGCOMBITEM").append(" ITEM ON T.ID = ITEM.SCHEMEID \n");
            sql.append(" WHERE T.ID = #{id} \n");
            return sql.toString();
        }

        public String getOrderNumByGroupId(OrgCombDefineDO orgCombDefineDO) {
            StringBuilder sql = new StringBuilder();
            sql.append("SELECT MAX(SORTORDER)");
            sql.append("  FROM ").append("DC_DEFINE_ORGCOMB").append(" T \n");
            sql.append(" WHERE GROUPID = #{groupId} \n");
            return sql.toString();
        }
    }
}

