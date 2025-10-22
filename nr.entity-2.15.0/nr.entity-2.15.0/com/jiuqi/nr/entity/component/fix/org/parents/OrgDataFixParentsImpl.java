/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.feign.client.OrgVersionClient
 *  org.springframework.jdbc.core.JdbcTemplate
 */
package com.jiuqi.nr.entity.component.fix.org.parents;

import com.jiuqi.nr.entity.component.fix.dto.OrgDataDTO;
import com.jiuqi.nr.entity.component.fix.dto.OrgDataFixDTO;
import com.jiuqi.nr.entity.component.fix.org.OrgDataQueryService;
import com.jiuqi.va.feign.client.OrgVersionClient;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.StringUtils;

public class OrgDataFixParentsImpl
extends OrgDataQueryService {
    public OrgDataFixParentsImpl(OrgVersionClient orgVersionClient, JdbcTemplate jdbcTemplate) {
        super(orgVersionClient, jdbcTemplate);
    }

    @Override
    protected List<OrgDataFixDTO> doCheck(List<OrgDataDTO> orgDataList) {
        HashMap graph = new HashMap();
        HashMap<String, Integer> inDegree = new HashMap<String, Integer>();
        ArrayList<OrgDataFixDTO> invalidOrgs = new ArrayList<OrgDataFixDTO>();
        for (OrgDataDTO data : orgDataList) {
            inDegree.put(data.getCode(), 0);
            graph.putIfAbsent(data.getCode(), new ArrayList());
            if (!StringUtils.hasText(data.getParentCode())) continue;
            if ("-".equals(data.getParentCode())) {
                if (data.getCode().equals(data.getParents())) continue;
                OrgDataFixDTO fixDTO = new OrgDataFixDTO();
                fixDTO.setId(data.getId());
                fixDTO.setCode(data.getCode());
                fixDTO.setName(data.getName());
                fixDTO.setOldValue(data.getParents());
                fixDTO.setNewValue(data.getCode());
                invalidOrgs.add(fixDTO);
                continue;
            }
            graph.putIfAbsent(data.getParentCode(), new ArrayList());
            ((List)graph.get(data.getParentCode())).add(data.getCode());
            inDegree.put(data.getCode(), (Integer)inDegree.get(data.getCode()) + 1);
        }
        LinkedList<Object> queue = new LinkedList<Object>();
        HashMap calculatedParentsMap = new HashMap();
        for (Map.Entry entry : inDegree.entrySet()) {
            if ((Integer)entry.getValue() != 0) continue;
            queue.offer(entry.getKey());
            calculatedParentsMap.put(entry.getKey(), new ArrayList());
        }
        while (!queue.isEmpty()) {
            String currentCode = (String)queue.poll();
            List currentParents = calculatedParentsMap.getOrDefault(currentCode, new ArrayList());
            for (String childCode : (List)graph.get(currentCode)) {
                ArrayList<String> childParents = new ArrayList<String>(currentParents);
                childParents.add(currentCode);
                calculatedParentsMap.put(childCode, childParents);
                inDegree.put(childCode, (Integer)inDegree.get(childCode) - 1);
                if ((Integer)inDegree.get(childCode) != 0) continue;
                queue.offer(childCode);
            }
        }
        for (OrgDataDTO data : orgDataList) {
            ArrayList<String> calculatedParents = (ArrayList<String>)calculatedParentsMap.get(data.getCode());
            if (calculatedParents == null) {
                calculatedParents = new ArrayList<String>();
            }
            calculatedParents.add(data.getCode());
            String fixedParents = String.join((CharSequence)"/", calculatedParents);
            if (fixedParents.equals(data.getParents())) continue;
            OrgDataFixDTO fixDTO = new OrgDataFixDTO();
            Optional<OrgDataFixDTO> findRow = invalidOrgs.stream().filter(e -> e.getId().equals(data.getId())).findFirst();
            if (findRow.isPresent()) {
                fixDTO = findRow.get();
            }
            fixDTO.setId(data.getId());
            fixDTO.setCode(data.getCode());
            fixDTO.setName(data.getName());
            fixDTO.setOldValue(data.getParents());
            fixDTO.setNewValue(fixedParents);
            if (findRow.isPresent()) continue;
            invalidOrgs.add(fixDTO);
        }
        return invalidOrgs;
    }

    @Override
    protected void doFix(List<OrgDataFixDTO> orgDataList) {
        StringBuffer buffer = new StringBuffer("\u4fee\u590d\u7236\u8282\u70b9\u8def\u5f84\uff1a");
        String sql = String.format("UPDATE %s SET %s = ? WHERE %s = ?", this.category, "parents", "id");
        ArrayList<Object[]> args = new ArrayList<Object[]>(orgDataList.size());
        for (OrgDataFixDTO data : orgDataList) {
            buffer.append("\u4e3b\u952e\uff1a").append(data.getId()).append("\u6807\u8bc6\uff1a").append(data.getCode()).append("\u65e7\u503c\uff1a").append(data.getOldValue()).append("\u65b0\u503c\uff1a").append(data.getNewValue()).append("\r\n");
            Object[] arg = new Object[]{data.getNewValue(), data.getId()};
            args.add(arg);
        }
        this.jdbcTemplate.batchUpdate(sql, args);
        LOGGER.info(buffer.toString());
    }
}

