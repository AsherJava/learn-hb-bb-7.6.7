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
package com.jiuqi.nr.entity.component.fix.org.version;

import com.jiuqi.nr.entity.component.fix.dto.OrgDataDTO;
import com.jiuqi.nr.entity.component.fix.dto.OrgDataFixDTO;
import com.jiuqi.nr.entity.component.fix.org.OrgDataQueryService;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.domain.org.OrgVersionDO;
import com.jiuqi.va.domain.org.OrgVersionDTO;
import com.jiuqi.va.feign.client.OrgVersionClient;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.jdbc.core.JdbcTemplate;

public class OrgDataFixVersionImpl
extends OrgDataQueryService {
    private final SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public OrgDataFixVersionImpl(OrgVersionClient orgVersionClient, JdbcTemplate jdbcTemplate) {
        super(orgVersionClient, jdbcTemplate);
    }

    @Override
    protected List<OrgDataFixDTO> doCheck(List<OrgDataDTO> orgDataList) {
        ArrayList<OrgDataFixDTO> invalidRecords = new ArrayList<OrgDataFixDTO>();
        OrgVersionDTO param = new OrgVersionDTO();
        param.setCategoryname(this.category);
        PageVO list = this.orgVersionClient.list(param);
        List versions = list.getRows().stream().sorted(Comparator.comparing(OrgVersionDO::getValidtime)).collect(Collectors.toList());
        if (versions.size() == 1) {
            return new ArrayList<OrgDataFixDTO>(0);
        }
        Map<String, List<OrgDataDTO>> codeMap = orgDataList.stream().collect(Collectors.groupingBy(OrgDataDTO::getCode));
        for (Map.Entry<String, List<OrgDataDTO>> entry : codeMap.entrySet()) {
            List<OrgDataDTO> records = entry.getValue();
            records.sort(Comparator.comparing(OrgDataDTO::getValidtime).thenComparing(OrgDataDTO::getInvalidtime));
            this.findInvalidRecords(records, invalidRecords);
        }
        return invalidRecords;
    }

    private void findInvalidRecords(List<OrgDataDTO> records, List<OrgDataFixDTO> invalidRecords) {
        for (int i = 0; i < records.size(); ++i) {
            OrgDataDTO prev;
            OrgDataDTO curr = records.get(i);
            if (i <= 0 || (prev = records.get(i - 1)).getInvalidtime().getTime() == curr.getValidtime().getTime()) continue;
            if (!prev.getInvalidtime().before(curr.getInvalidtime())) {
                OrgDataDTO temp = curr;
                curr = prev;
                prev = temp;
            }
            String currId = curr.getId();
            Optional<OrgDataFixDTO> findRecord = invalidRecords.stream().filter(e -> e.getId().equals(currId)).findFirst();
            if (findRecord.isPresent()) {
                OrgDataFixDTO fixDTO = findRecord.get();
                fixDTO.setNewValue(this.SDF.format(prev.getInvalidtime()));
            } else {
                OrgDataFixDTO invalidRecord = new OrgDataFixDTO();
                invalidRecord.setId(currId);
                invalidRecord.setCode(curr.getCode());
                invalidRecord.setName(curr.getName());
                invalidRecord.setOldValue(this.SDF.format(curr.getValidtime()));
                invalidRecord.setNewValue(this.SDF.format(prev.getInvalidtime()));
                invalidRecords.add(invalidRecord);
            }
            curr.setValidtime(prev.getInvalidtime());
            records.sort(Comparator.comparing(OrgDataDTO::getValidtime).thenComparing(OrgDataDTO::getInvalidtime));
            this.findInvalidRecords(records, invalidRecords);
            return;
        }
    }

    @Override
    protected void doFix(List<OrgDataFixDTO> orgDataList) {
        StringBuffer buffer = new StringBuffer("\u4fee\u590d\u9519\u8bef\u7248\u672c\u6570\u636e");
        String sql = String.format("UPDATE %s SET %s = ? WHERE %s = ?", this.category, "validtime", "id");
        ArrayList<Object[]> args = new ArrayList<Object[]>(orgDataList.size());
        for (OrgDataFixDTO dataFixDTO : orgDataList) {
            try {
                Date newValue = this.SDF.parse(dataFixDTO.getNewValue().toString());
                Date oldValue = this.SDF.parse(dataFixDTO.getOldValue().toString());
                Object[] arg = new Object[]{newValue, dataFixDTO.getId()};
                buffer.append("\u4e3b\u952e\uff1a").append(dataFixDTO.getId()).append("\u65e7\u503c\uff1a").append(oldValue).append("\u65b0\u503c\uff1a").append(newValue).append("\r\n");
                args.add(arg);
            }
            catch (ParseException e) {
                throw new RuntimeException(e);
            }
        }
        this.jdbcTemplate.batchUpdate(sql, args);
        LOGGER.info(buffer.toString());
    }
}

