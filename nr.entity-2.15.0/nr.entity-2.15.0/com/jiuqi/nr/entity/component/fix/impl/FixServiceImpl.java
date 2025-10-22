/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.org.OrgCategoryDO
 *  com.jiuqi.va.domain.org.OrgCategoryDTO
 *  com.jiuqi.va.domain.org.OrgVersionDTO
 *  com.jiuqi.va.feign.client.OrgCategoryClient
 *  com.jiuqi.va.feign.client.OrgDataClient
 *  com.jiuqi.va.feign.client.OrgVersionClient
 *  org.springframework.jdbc.core.JdbcTemplate
 */
package com.jiuqi.nr.entity.component.fix.impl;

import com.jiuqi.nr.entity.component.fix.IFixService;
import com.jiuqi.nr.entity.component.fix.dto.OrgDataFixDTO;
import com.jiuqi.nr.entity.component.fix.dto.SimpleDefineDTO;
import com.jiuqi.nr.entity.component.fix.org.parents.OrgDataFixParentsImpl;
import com.jiuqi.va.domain.org.OrgCategoryDO;
import com.jiuqi.va.domain.org.OrgCategoryDTO;
import com.jiuqi.va.domain.org.OrgVersionDTO;
import com.jiuqi.va.feign.client.OrgCategoryClient;
import com.jiuqi.va.feign.client.OrgDataClient;
import com.jiuqi.va.feign.client.OrgVersionClient;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

public class FixServiceImpl
implements IFixService {
    @Autowired
    private OrgCategoryClient orgCategoryClient;
    @Autowired
    private OrgVersionClient orgVersionClient;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private OrgDataClient orgDataClient;
    private static final String ALL_VERSION = "-";

    @Override
    public List<SimpleDefineDTO> listOrg() {
        ArrayList<SimpleDefineDTO> list = new ArrayList<SimpleDefineDTO>();
        this.orgCategoryClient.list(new OrgCategoryDO()).getRows().forEach(e -> {
            SimpleDefineDTO defineDTO = new SimpleDefineDTO();
            defineDTO.setKey(e.getName());
            defineDTO.setCode(e.getName());
            defineDTO.setTitle(e.getTitle() + "[" + e.getName() + "]");
            list.add(defineDTO);
        });
        return list;
    }

    @Override
    public List<SimpleDefineDTO> listOrgVersion(String orgCategory) {
        ArrayList<SimpleDefineDTO> list = new ArrayList<SimpleDefineDTO>();
        OrgVersionDTO param = new OrgVersionDTO();
        param.setCategoryname(orgCategory);
        this.orgVersionClient.list(param).getRows().forEach(e -> {
            SimpleDefineDTO defineDTO = new SimpleDefineDTO();
            defineDTO.setKey(e.getId().toString());
            defineDTO.setCode(e.getId().toString());
            defineDTO.setTitle(e.getTitle().equals(ALL_VERSION) ? "\u9ed8\u8ba4\u7248\u672c" : e.getTitle());
            list.add(defineDTO);
        });
        if (list.size() > 1) {
            SimpleDefineDTO allDTO = new SimpleDefineDTO();
            allDTO.setKey(ALL_VERSION);
            allDTO.setCode(ALL_VERSION);
            allDTO.setTitle("\u6240\u6709\u7248\u672c");
            list.add(0, allDTO);
        }
        return list;
    }

    @Override
    public List<OrgDataFixDTO> fixParents(String orgCategory, String version) {
        Set<String> versions = new HashSet<String>();
        if (ALL_VERSION.equals(version)) {
            OrgVersionDTO versionDTO = new OrgVersionDTO();
            versionDTO.setCategoryname(orgCategory);
            versions = this.orgVersionClient.list(versionDTO).getRows().stream().map(e -> e.getId().toString()).collect(Collectors.toSet());
        } else {
            versions.add(version);
        }
        ArrayList<OrgDataFixDTO> fixeDTO = new ArrayList<OrgDataFixDTO>(16);
        for (String v : versions) {
            OrgDataFixParentsImpl orgDataParentsFix = new OrgDataFixParentsImpl(this.orgVersionClient, this.jdbcTemplate);
            List<OrgDataFixDTO> orgDataFixDTOS = orgDataParentsFix.fixOrgData(orgCategory, v);
            fixeDTO.addAll(orgDataFixDTOS);
        }
        OrgCategoryDTO orgCategoryDTO = new OrgCategoryDTO();
        orgCategoryDTO.setName(orgCategory);
        this.orgDataClient.syncCache(orgCategoryDTO);
        return fixeDTO;
    }

    @Override
    public void fixOrdinal(String category, String versionTitle) {
    }
}

