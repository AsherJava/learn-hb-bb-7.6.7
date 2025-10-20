/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.common.dto.OrgMappingDTO
 *  com.jiuqi.bde.common.dto.OrgMappingItem
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.OuterDataSourceUtils
 *  org.springframework.jdbc.core.BeanPropertyRowMapper
 *  org.springframework.jdbc.core.RowMapper
 */
package com.jiuqi.bde.bizmodel.impl.orgmapping.dao.impl;

import com.jiuqi.bde.bizmodel.impl.orgmapping.dao.BdeOrgMappingDao;
import com.jiuqi.bde.common.dto.OrgMappingDTO;
import com.jiuqi.bde.common.dto.OrgMappingItem;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.OuterDataSourceUtils;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class BdeOrgMappingDaoImpl
implements BdeOrgMappingDao {
    private static final Logger logger = LoggerFactory.getLogger(BdeOrgMappingDaoImpl.class);
    private static final String SELECT_SQL = "SELECT DATASCHEMECODE AS DATASCHEMECODE, ODS_CODE AS ACCTORGCODE, ODS_NAME AS ACCTORGNAME, ODS_BOOKCODE AS ACCTBOOKCODE, \nCODE AS REPORTORGCODE, CODE AS REPORTORGNAME,ODS_ASSISTCODE AS ASSISTCODE, ODS_ASSISTNAME AS ASSISTNAME FROM REF_MD_ORG WHERE CODE = ? ORDER BY ODS_CODE ";
    private static final String SELECT_SQL_BYACCORGCODE = "SELECT DATASCHEMECODE AS DATASCHEMECODE, ODS_CODE AS ACCTORGCODE, ODS_NAME AS ACCTORGNAME, ODS_BOOKCODE AS ACCTBOOKCODE, \nCODE AS REPORTORGCODE, CODE AS REPORTORGNAME,ODS_ASSISTCODE AS ASSISTCODE, ODS_ASSISTNAME AS ASSISTNAME FROM REF_MD_ORG WHERE ODS_CODE = ? ";

    @Override
    public OrgMappingDTO get(String code) {
        List list = OuterDataSourceUtils.getJdbcTemplate().query(SELECT_SQL, (RowMapper)new BeanPropertyRowMapper(OrgMappingDTO.class), new Object[]{code});
        if (CollectionUtils.isEmpty((Collection)list)) {
            return null;
        }
        OrgMappingDTO orgMappingDTO = (OrgMappingDTO)list.get(0);
        ArrayList<OrgMappingItem> orgMappingItems = new ArrayList<OrgMappingItem>();
        if (list.size() > 1) {
            for (OrgMappingDTO org : list) {
                OrgMappingItem orgMappingItem = new OrgMappingItem();
                BeanUtils.copyProperties(org, orgMappingItem);
                orgMappingItems.add(orgMappingItem);
            }
        }
        orgMappingDTO.setOrgMappingItems(orgMappingItems);
        return orgMappingDTO;
    }

    @Override
    public OrgMappingDTO getByAcctOrgCode(String acctOrgCode) {
        List list = OuterDataSourceUtils.getJdbcTemplate().query(SELECT_SQL_BYACCORGCODE, (RowMapper)new BeanPropertyRowMapper(OrgMappingDTO.class), new Object[]{acctOrgCode});
        if (CollectionUtils.isEmpty((Collection)list)) {
            return null;
        }
        OrgMappingDTO orgMappingDTO = (OrgMappingDTO)list.get(0);
        ArrayList orgMappingItems = new ArrayList();
        if (list.size() > 1) {
            logger.info("\u6838\u7b97\u5355\u4f4d\u3010{}\u3011\u5bf9\u5e94\u4e86\u591a\u4e2a\u62a5\u8868\u5355\u4f4d", (Object)acctOrgCode);
        }
        orgMappingDTO.setOrgMappingItems(orgMappingItems);
        return orgMappingDTO;
    }
}

