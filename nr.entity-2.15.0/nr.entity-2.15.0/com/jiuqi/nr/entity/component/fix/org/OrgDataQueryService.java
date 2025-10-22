/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.domain.org.OrgVersionDO
 *  com.jiuqi.va.domain.org.OrgVersionDTO
 *  com.jiuqi.va.feign.client.OrgVersionClient
 *  org.springframework.jdbc.core.JdbcTemplate
 */
package com.jiuqi.nr.entity.component.fix.org;

import com.jiuqi.nr.entity.component.fix.dto.OrgDataDTO;
import com.jiuqi.nr.entity.component.fix.dto.OrgDataFixDTO;
import com.jiuqi.nr.entity.component.fix.org.IOrgDataFix;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.domain.org.OrgVersionDO;
import com.jiuqi.va.domain.org.OrgVersionDTO;
import com.jiuqi.va.feign.client.OrgVersionClient;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

public abstract class OrgDataQueryService
implements IOrgDataFix {
    protected static final Logger LOGGER = LoggerFactory.getLogger("com.jiuqi.nr.entity.logger");
    protected final OrgVersionClient orgVersionClient;
    protected final JdbcTemplate jdbcTemplate;
    protected String category;
    protected String version;

    public OrgDataQueryService(OrgVersionClient orgVersionClient, JdbcTemplate jdbcTemplate) {
        this.orgVersionClient = orgVersionClient;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<OrgDataFixDTO> fixOrgData(String orgCategory, String version) {
        this.category = orgCategory;
        this.version = version;
        List<OrgDataDTO> orgDataList = this.queryData();
        if (CollectionUtils.isEmpty(orgDataList)) {
            return new ArrayList<OrgDataFixDTO>();
        }
        LOGGER.info("\u5bf9\u7ec4\u7ec7\u673a\u6784:{}\uff0c\u7248\u672c\uff1a{}\u8fdb\u884c\u6570\u636e\u4fee\u590d\u68c0\u67e5", (Object)orgCategory, (Object)version);
        List<OrgDataFixDTO> checkResult = this.doCheck(orgDataList);
        if (!CollectionUtils.isEmpty(checkResult)) {
            LOGGER.info("\u68c0\u67e5\u5230{}\u6761\u6709\u95ee\u9898\u7684\u6570\u636e\uff0c\u51c6\u5907\u8fdb\u884c\u4fee\u590d", (Object)checkResult.size());
            this.doFix(checkResult);
        } else {
            LOGGER.info("\u672a\u68c0\u67e5\u5230\u9519\u8bef\u6570\u636e");
        }
        return checkResult;
    }

    protected List<OrgDataDTO> queryData() {
        ArrayList<OrgDataDTO> fixData = new ArrayList<OrgDataDTO>();
        Object queryArg = "1";
        String whereSql = " ? = ? ";
        Integer stopFlag = 0;
        if (StringUtils.hasText(this.version)) {
            OrgVersionDTO versionDO = new OrgVersionDTO();
            versionDO.setCategoryname(this.category);
            versionDO.setId(UUID.fromString(this.version));
            PageVO versions = this.orgVersionClient.list(versionDO);
            List rows = versions.getRows();
            if (rows.isEmpty()) {
                throw new RuntimeException("\u9519\u8bef\u7684\u65f6\u671f\uff1a" + this.category + "version:" + this.version);
            }
            queryArg = ((OrgVersionDO)rows.get(0)).getValidtime();
            whereSql = "VALIDTIME <= ? AND INVALIDTIME > ?";
        }
        String sql = String.format("SELECT * FROM %s WHERE STOPFLAG = ? AND %s", this.category, whereSql);
        HashSet existCodes = new HashSet();
        this.jdbcTemplate.query(sql, rs -> {
            OrgDataDTO fixDTO = new OrgDataDTO();
            fixDTO.setId(rs.getString("id"));
            String code = rs.getString("code");
            String parentCode = rs.getString("parentcode");
            if (!StringUtils.hasText(parentCode)) {
                parentCode = "-";
            }
            fixDTO.setCode(code);
            fixDTO.setOrgCode(rs.getString("orgcode"));
            fixDTO.setName(rs.getString("name"));
            fixDTO.setParentCode(parentCode);
            fixDTO.setParents(rs.getString("parents"));
            fixDTO.setValidtime(rs.getDate("validtime"));
            fixDTO.setInvalidtime(rs.getDate("invalidtime"));
            existCodes.add(code);
            fixData.add(fixDTO);
        }, new Object[]{stopFlag, queryArg, queryArg});
        for (OrgDataDTO fixDTO : fixData) {
            if (existCodes.contains(fixDTO.getParentCode())) continue;
            fixDTO.setParentCode("-");
        }
        return fixData;
    }

    protected abstract List<OrgDataFixDTO> doCheck(List<OrgDataDTO> var1);

    protected abstract void doFix(List<OrgDataFixDTO> var1);
}

