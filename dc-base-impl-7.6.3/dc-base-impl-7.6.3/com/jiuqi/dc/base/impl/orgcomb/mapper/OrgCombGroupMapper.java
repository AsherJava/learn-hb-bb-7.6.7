/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.SqlBuildUtil
 *  com.jiuqi.dc.base.client.orgcomb.dto.OrgCombGroupDTO
 *  com.jiuqi.dc.base.client.orgcomb.vo.OrgCombDefineVO
 *  com.jiuqi.va.mapper.common.BaseOptMapper
 *  org.apache.ibatis.annotations.Mapper
 *  org.apache.ibatis.annotations.SelectProvider
 */
package com.jiuqi.dc.base.impl.orgcomb.mapper;

import com.jiuqi.common.base.util.SqlBuildUtil;
import com.jiuqi.dc.base.client.orgcomb.dto.OrgCombGroupDTO;
import com.jiuqi.dc.base.client.orgcomb.vo.OrgCombDefineVO;
import com.jiuqi.dc.base.impl.orgcomb.domain.OrgCombGroupDO;
import com.jiuqi.va.mapper.common.BaseOptMapper;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;

@Mapper
public interface OrgCombGroupMapper
extends BaseOptMapper<OrgCombGroupDO> {
    @SelectProvider(type=SqlProvider.class, method="getAllTreeNodes")
    public List<OrgCombGroupDO> getAllTreeNodes(OrgCombGroupDO var1);

    @SelectProvider(type=SqlProvider.class, method="getOrgCombinationTitleById")
    public String getOrgCombinationTitleById(OrgCombGroupDO var1);

    @SelectProvider(type=SqlProvider.class, method="getSchemeIdById")
    public List<String> getSchemeIdById(OrgCombGroupDTO var1);

    @SelectProvider(type=SqlProvider.class, method="getDefineByGroupId")
    public List<OrgCombDefineVO> getDefineByGroupId(OrgCombGroupDTO var1);

    @SelectProvider(type=SqlProvider.class, method="updateGroupName")
    public Boolean updateGroupName(OrgCombGroupDO var1);

    public static class SqlProvider {
        public String getAllTreeNodes(OrgCombGroupDO orgCombGroupDO) {
            StringBuilder sql = new StringBuilder();
            sql.append("SELECT ID, TITLE, NODETYPE, SORTNUM \n");
            sql.append("  FROM ").append("DC_DEFINE_ORGCOMBGROUP").append(" \n");
            sql.append(" GROUP BY ID, TITLE, NODETYPE, SORTNUM");
            return sql.toString();
        }

        public String getOrgCombinationTitleById(OrgCombGroupDO orgCombGroupDO) {
            StringBuilder sql = new StringBuilder();
            sql.append("SELECT NODETYPE \n");
            sql.append("  FROM ").append("DC_DEFINE_ORGCOMBGROUP").append(" \n");
            sql.append(" WHERE ID = #{id} \n");
            return sql.toString();
        }

        public String getSchemeIdById(OrgCombGroupDTO orgCombGroupDTO) {
            StringBuilder sql = new StringBuilder();
            sql.append("SELECT SCHEMEID \n");
            sql.append("  FROM ").append("DC_DEFINE_ORGCOMBITEM").append(" \n");
            sql.append(" WHERE ").append(SqlBuildUtil.getStrInCondi((String)"ORGCODE", (List)orgCombGroupDTO.getCodes())).append(" \n");
            sql.append(" GROUP BY SCHEMEID");
            return sql.toString();
        }

        public String getDefineByGroupId(OrgCombGroupDTO orgCombGroupDTO) {
            StringBuilder sql = new StringBuilder();
            sql.append("SELECT ID, CODE, NAME \n");
            sql.append("  FROM ").append("DC_DEFINE_ORGCOMB").append(" \n");
            sql.append(" WHERE GROUPID =#{id} \n");
            return sql.toString();
        }

        public String updateGroupName(OrgCombGroupDO orgCombGroupDO) {
            StringBuilder sql = new StringBuilder();
            sql.append("UPDATE ").append("DC_DEFINE_ORGCOMBGROUP").append(" \n");
            sql.append("   SET TITLE = #{title} \n");
            sql.append(" WHERE ID = #{id} \n");
            return sql.toString();
        }
    }
}

