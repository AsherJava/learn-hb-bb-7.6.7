/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.nvwa.mapping.bean.BaseDataItemMapping
 *  com.jiuqi.nvwa.mapping.bean.BaseDataMapping
 *  com.jiuqi.nvwa.mapping.bean.MappingGroup
 *  com.jiuqi.nvwa.mapping.bean.MappingScheme
 *  com.jiuqi.nvwa.mapping.bean.OrgMapping
 *  com.jiuqi.nvwa.mapping.service.IBaseDataMappingService
 *  com.jiuqi.nvwa.mapping.service.IMappingSchemeService
 *  com.jiuqi.nvwa.mapping.service.IOrgMappingService
 *  com.jiuqi.nvwa.mapping.transfer.TransferUtil
 */
package com.jiuqi.nr.mapping2.service.impl;

import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.nr.mapping2.common.NrMappingUtil;
import com.jiuqi.nr.mapping2.dto.NrMappingSchemeDTO;
import com.jiuqi.nr.mapping2.provider.NrMappingParam;
import com.jiuqi.nr.mapping2.service.MappingTransferService;
import com.jiuqi.nvwa.mapping.bean.BaseDataItemMapping;
import com.jiuqi.nvwa.mapping.bean.BaseDataMapping;
import com.jiuqi.nvwa.mapping.bean.MappingGroup;
import com.jiuqi.nvwa.mapping.bean.MappingScheme;
import com.jiuqi.nvwa.mapping.bean.OrgMapping;
import com.jiuqi.nvwa.mapping.service.IBaseDataMappingService;
import com.jiuqi.nvwa.mapping.service.IMappingSchemeService;
import com.jiuqi.nvwa.mapping.service.IOrgMappingService;
import com.jiuqi.nvwa.mapping.transfer.TransferUtil;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Service
public class MappingTransferServiceImpl
implements MappingTransferService {
    @Autowired
    IMappingSchemeService msService;
    @Autowired
    IOrgMappingService orgService;
    @Autowired
    IBaseDataMappingService baseService;

    @Override
    public List<String> getSchemeByForm(String formSchemeKey) {
        ArrayList<String> res = new ArrayList<String>();
        if (!StringUtils.hasText(formSchemeKey)) {
            return res;
        }
        List schemes = this.msService.getAllSchemes();
        if (!CollectionUtils.isEmpty(schemes)) {
            for (MappingScheme s : schemes) {
                NrMappingParam nrMappingParam;
                if (!s.getSource().contains("NR-MAPPING-FACTORY") || (nrMappingParam = NrMappingUtil.getNrMappingParam(s)) == null || !formSchemeKey.equals(nrMappingParam.getFormSchemeKey())) continue;
                res.add(TransferUtil.getBusinessNodeId((String)s.getKey()));
            }
        }
        return res;
    }

    @Override
    public List<MappingScheme> getMappingSchemeByFormScheme(String formSchemeKey) {
        ArrayList<MappingScheme> res = new ArrayList<MappingScheme>();
        if (!StringUtils.hasText(formSchemeKey)) {
            return res;
        }
        List schemes = this.msService.getAllSchemes();
        if (!CollectionUtils.isEmpty(schemes)) {
            for (MappingScheme s : schemes) {
                NrMappingParam nrMappingParam;
                if (!s.getSource().contains("NR-MAPPING-FACTORY") || (nrMappingParam = NrMappingUtil.getNrMappingParam(s)) == null || !formSchemeKey.equals(nrMappingParam.getFormSchemeKey())) continue;
                res.add(s);
            }
        }
        return res;
    }

    @Override
    public List<MappingScheme> getMappingSchemeByFormScheme(String formSchemeKey, String type) {
        ArrayList<MappingScheme> res = new ArrayList<MappingScheme>();
        if (!StringUtils.hasText(formSchemeKey)) {
            return res;
        }
        List schemes = this.msService.getAllSchemes();
        if (!CollectionUtils.isEmpty(schemes)) {
            for (MappingScheme s : schemes) {
                NrMappingParam nrMappingParam;
                if (!s.getSource().contains("NR-MAPPING-FACTORY") || (nrMappingParam = NrMappingUtil.getNrMappingParam(s)) == null || !formSchemeKey.equals(nrMappingParam.getFormSchemeKey()) || !type.equals(nrMappingParam.getType())) continue;
                res.add(s);
            }
        }
        return res;
    }

    @Override
    public List<MappingScheme> getMappingSchemeByTask(String taskKey) {
        ArrayList<MappingScheme> res = new ArrayList<MappingScheme>();
        if (!StringUtils.hasText(taskKey)) {
            return res;
        }
        List schemes = this.msService.getAllSchemes();
        if (!CollectionUtils.isEmpty(schemes)) {
            for (MappingScheme s : schemes) {
                NrMappingParam nrMappingParam;
                if (!s.getSource().contains("NR-MAPPING-FACTORY") || (nrMappingParam = NrMappingUtil.getNrMappingParam(s)) == null || !taskKey.equals(nrMappingParam.getTaskKey())) continue;
                res.add(s);
            }
        }
        return res;
    }

    @Override
    public List<MappingScheme> getMappingSchemeByTask(String taskKey, String type) {
        ArrayList<MappingScheme> res = new ArrayList<MappingScheme>();
        if (!StringUtils.hasText(taskKey)) {
            return res;
        }
        List schemes = this.msService.getAllSchemes();
        if (!CollectionUtils.isEmpty(schemes)) {
            for (MappingScheme s : schemes) {
                NrMappingParam nrMappingParam;
                if (!s.getSource().contains("NR-MAPPING-FACTORY") || (nrMappingParam = NrMappingUtil.getNrMappingParam(s)) == null || !taskKey.equals(nrMappingParam.getTaskKey()) || !type.equals(nrMappingParam.getType())) continue;
                res.add(s);
            }
        }
        return res;
    }

    @Override
    public MappingScheme createScheme(NrMappingSchemeDTO schemeDTO) throws JQException {
        MappingScheme scheme = NrMappingUtil.parseNRMappingToNvwa(schemeDTO);
        scheme.setKey(UUID.randomUUID().toString());
        scheme.setSource(NrMappingUtil.getJioSources());
        scheme.setUpdateTime(new Date());
        this.msService.addScheme(scheme);
        return scheme;
    }

    @Override
    public String createGroup(MappingGroup group) throws JQException {
        return this.msService.addGroup(group);
    }

    @Override
    public void batchAddOrgMapping(String msKey, List<OrgMapping> mappings) {
        this.orgService.addOrgMapping(msKey, mappings);
    }

    @Override
    public void addBaseDataMapping(String msKey, List<BaseDataMapping> mappings) {
        this.baseService.addBaseDataMapping(msKey, mappings);
    }

    @Override
    public void addBaseDataItemMapping(String msKey, List<BaseDataItemMapping> mappings) {
        this.baseService.addBaseDataItemMapping(msKey, mappings);
    }

    @Override
    public void saveBaseDataItemMappings(String msKey, String baseDataCode, List<BaseDataItemMapping> mappings) {
        this.baseService.saveBaseDataItemMappings(msKey, baseDataCode, mappings);
    }
}

