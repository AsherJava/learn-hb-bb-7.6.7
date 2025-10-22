/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.OrderGenerator
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.domain.org.OrgVersionDO
 *  com.jiuqi.va.domain.org.OrgVersionDTO
 *  com.jiuqi.va.feign.client.OrgVersionClient
 *  org.springframework.jdbc.core.JdbcTemplate
 */
package com.jiuqi.nr.entity.component.fix.org.ordinal;

import com.jiuqi.bi.util.OrderGenerator;
import com.jiuqi.nr.entity.component.fix.dto.OrgDataDTO;
import com.jiuqi.nr.entity.component.fix.dto.OrgDataFixDTO;
import com.jiuqi.nr.entity.component.fix.org.IOrgDataFix;
import com.jiuqi.nr.entity.log.SystemEntityLog;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.domain.org.OrgVersionDO;
import com.jiuqi.va.domain.org.OrgVersionDTO;
import com.jiuqi.va.feign.client.OrgVersionClient;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.StringUtils;

public class OrgDataFixOrdinalImpl
implements IOrgDataFix {
    final OrgVersionClient orgVersionClient;
    final JdbcTemplate jdbcTemplate;
    final SystemEntityLog logger;
    private final Map<String, List<OrgDataDTO>> lastVersionParentLink = new HashMap<String, List<OrgDataDTO>>();
    private final Map<String, List<OrgDataDTO>> currentParentLink = new HashMap<String, List<OrgDataDTO>>();
    private final Map<String, OrgDataDTO> currentNodeLink = new HashMap<String, OrgDataDTO>();

    public OrgDataFixOrdinalImpl(OrgVersionClient orgVersionClient, JdbcTemplate jdbcTemplate) {
        this.orgVersionClient = orgVersionClient;
        this.jdbcTemplate = jdbcTemplate;
        this.logger = new SystemEntityLog(OrderGenerator.newOrder());
    }

    @Override
    public List<OrgDataFixDTO> fixOrgData(String orgCategory, String version) {
        OrgVersionDTO param = new OrgVersionDTO();
        param.setCategoryname(orgCategory);
        PageVO list = this.orgVersionClient.list(param);
        List versions = list.getRows();
        versions.sort(Comparator.comparing(OrgVersionDO::getValidtime));
        this.createColumn(orgCategory);
        for (OrgVersionDO versionDO : versions) {
            this.logger.debug("\u4fee\u590d\u7ec4\u7ec7\u673a\u6784\u6392\u5e8f\u503c\u673a\u6784:{},\u7248\u672c:{}", orgCategory, versionDO.getTitle());
            List<OrgDataDTO> orgList = this.listDate(orgCategory, versionDO.getInvalidtime());
            this.logger.debug("\u67e5\u8be2\u5230\u6570\u636e\u5171\u8ba1:{}\u6761", orgList.size());
            List<OrgDataFixDTO> fixRows = this.doFix(orgList);
            this.updateData(orgCategory, fixRows);
        }
        this.changeOrdinal(orgCategory);
        this.dropColumn(orgCategory);
        return null;
    }

    private List<OrgDataFixDTO> doFix(List<OrgDataDTO> orgList) {
        Map<String, List<OrgDataDTO>> parentMap = orgList.stream().collect(Collectors.groupingBy(OrgDataDTO::getParentCode));
        Set<String> parentKeys = parentMap.keySet();
        ArrayList<OrgDataFixDTO> result = new ArrayList<OrgDataFixDTO>();
        for (String parentKey : parentKeys) {
            this.currentNodeLink.clear();
            List<OrgDataDTO> previousRows = this.lastVersionParentLink.get(parentKey);
            previousRows.sort(Comparator.comparing(OrgDataDTO::getOrdinal));
            List<OrgDataDTO> currentRows = parentMap.get(parentKey);
            this.currentParentLink.put(parentKey, currentRows);
            List<OrgDataFixDTO> fixRows = this.rebuildOrdinal(currentRows, previousRows);
            result.addAll(fixRows);
        }
        this.lastVersionParentLink.putAll(this.currentParentLink);
        this.currentParentLink.clear();
        return result;
    }

    private List<OrgDataFixDTO> rebuildOrdinal(List<OrgDataDTO> currentRows, List<OrgDataDTO> previousRows) {
        ArrayList<OrgDataFixDTO> fixRows = new ArrayList<OrgDataFixDTO>(currentRows.size());
        currentRows.sort(Comparator.comparing(OrgDataDTO::getOrdinal));
        List<OrgDataDTO> lcsNodes = this.findLCS(previousRows, currentRows);
        Map<String, OrgDataDTO> previousMap = previousRows.stream().collect(Collectors.toMap(OrgDataDTO::getCode, e -> e));
        Set<String> lcsCodes = lcsNodes.stream().map(OrgDataDTO::getCode).collect(Collectors.toSet());
        for (OrgDataDTO node2 : lcsNodes) {
            OrgDataDTO lastNode = previousMap.get(node2.getCode());
            node2.setNewOrdinal(lastNode.getNewOrdinal());
            this.currentNodeLink.put(node2.getCode(), node2);
        }
        BigDecimal baseOrder = BigDecimal.ONE;
        List changeNodes = currentRows.stream().filter(node -> !lcsCodes.contains(node.getCode())).collect(Collectors.toList());
        for (OrgDataDTO node3 : changeNodes) {
            BigDecimal max;
            OrgDataDTO preNode = this.findPreviousLCSNode(node3, lcsCodes, currentRows);
            OrgDataDTO nextNode = this.findNextLCSNode(node3, lcsCodes, currentRows);
            BigDecimal min = preNode != null && preNode.getNewOrdinal() != null ? preNode.getNewOrdinal() : baseOrder;
            BigDecimal bigDecimal = max = nextNode != null && nextNode.getNewOrdinal() != null ? nextNode.getNewOrdinal() : min.add(BigDecimal.ONE);
            if (min.compareTo(max) == 0) {
                max = min.add(BigDecimal.ONE);
            }
            node3.setNewOrdinal(min.add(max.subtract(min).divide(BigDecimal.valueOf(2L), RoundingMode.HALF_DOWN)));
            this.currentNodeLink.put(node3.getCode(), node3);
        }
        this.currentNodeLink.values().forEach(e -> {
            OrgDataFixDTO fixRow = new OrgDataFixDTO();
            fixRow.setId(e.getId());
            fixRow.setCode(e.getCode());
            fixRow.setOldValue(e.getOrdinal());
            fixRow.setNewValue(e.getNewOrdinal());
            fixRows.add(fixRow);
        });
        return fixRows;
    }

    private List<OrgDataDTO> findLCS(List<OrgDataDTO> previousRows, List<OrgDataDTO> currentRows) {
        int m = previousRows.size();
        int n = currentRows.size();
        int[][] dp = new int[m + 1][n + 1];
        for (int i = 1; i <= m; ++i) {
            for (int j = 1; j <= n; ++j) {
                dp[i][j] = previousRows.get(i - 1).getCode().equals(currentRows.get(j - 1).getCode()) ? dp[i - 1][j - 1] + 1 : Math.max(dp[i - 1][j], dp[i][j - 1]);
            }
        }
        ArrayList<OrgDataDTO> lcs = new ArrayList<OrgDataDTO>();
        int i = m;
        int j = n;
        while (i > 0 && j > 0) {
            if (previousRows.get(i - 1).getCode().equals(currentRows.get(j - 1).getCode())) {
                lcs.add(0, currentRows.get(j - 1));
                --i;
                --j;
                continue;
            }
            if (dp[i - 1][j] > dp[i][j - 1]) {
                --i;
                continue;
            }
            --j;
        }
        Collections.reverse(lcs);
        return lcs;
    }

    private OrgDataDTO findPreviousLCSNode(OrgDataDTO node, Set<String> lcsNodes, List<OrgDataDTO> currentRows) {
        int idx = currentRows.indexOf(node);
        for (int i = idx - 1; i >= 0; --i) {
            OrgDataDTO preNode = currentRows.get(i);
            if (!lcsNodes.contains(preNode.getCode())) continue;
            return this.currentNodeLink.get(preNode.getCode());
        }
        return null;
    }

    private OrgDataDTO findNextLCSNode(OrgDataDTO node, Set<String> lcsNodes, List<OrgDataDTO> currentRows) {
        int idx = currentRows.indexOf(node);
        for (int i = idx + 1; i < currentRows.size(); ++i) {
            OrgDataDTO nextNode = currentRows.get(i);
            if (!lcsNodes.contains(nextNode.getCode())) continue;
            return this.currentNodeLink.get(nextNode.getCode());
        }
        return null;
    }

    private List<OrgDataDTO> listDate(String category, Date version) {
        ArrayList<OrgDataDTO> fixData = new ArrayList<OrgDataDTO>();
        String sql = String.format("SELECT * FROM %s WHERE STOPFLAG = ? AND VALIDTIME <= ? AND INVALIDTIME > ? order by ordinal", category);
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
            fixDTO.setParentCode(parentCode);
            fixDTO.setValidtime(rs.getDate("validtime"));
            fixDTO.setInvalidtime(rs.getDate("invalidtime"));
            fixDTO.setOrdinal(rs.getBigDecimal("ordinal"));
            fixDTO.setNewOrdinal(rs.getBigDecimal("newordinal"));
            existCodes.add(code);
            fixData.add(fixDTO);
        }, new Object[]{0, version});
        for (OrgDataDTO fixDTO : fixData) {
            if (existCodes.contains(fixDTO.getParentCode())) continue;
            fixDTO.setParentCode("-");
        }
        return fixData;
    }

    private void updateData(String category, List<OrgDataFixDTO> fixRows) {
        String sql = String.format("UPDATE %s SET %s = ? WHERE %s = ?", category, "newordinal", "id");
        ArrayList<Object[]> args = new ArrayList<Object[]>();
        for (OrgDataFixDTO fixRow : fixRows) {
            Object[] arg = new Object[]{fixRow.getNewValue(), fixRow.getId()};
            args.add(arg);
        }
        this.jdbcTemplate.batchUpdate(sql, args);
    }

    private void changeOrdinal(String orgCategory) {
        String sql = String.format("UPDATE %s SET %s = %s ", orgCategory, "ordinal", "newordinal");
        this.jdbcTemplate.update(sql);
    }

    private void createColumn(String orgCategory) {
        String sql = String.format("ALTER TABLE %s ADD newordinal number(19,6)", orgCategory);
        this.jdbcTemplate.update(sql);
    }

    private void dropColumn(String orgCategory) {
        String sql = String.format("ALTER TABLE %s drop column newordinal", orgCategory);
        this.jdbcTemplate.update(sql);
    }
}

