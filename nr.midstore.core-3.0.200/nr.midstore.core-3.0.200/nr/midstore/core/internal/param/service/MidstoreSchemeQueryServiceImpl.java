/*
 * Decompiled with CFR 0.152.
 */
package nr.midstore.core.internal.param.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import nr.midstore.core.definition.dto.MidstoreOrgDataDTO;
import nr.midstore.core.definition.dto.MidstoreSchemeDTO;
import nr.midstore.core.definition.dto.MidstoreSchemeInfoDTO;
import nr.midstore.core.definition.service.IMidstoreOrgDataService;
import nr.midstore.core.definition.service.IMidstoreSchemeInfoService;
import nr.midstore.core.definition.service.IMidstoreSchemeService;
import nr.midstore.core.param.service.IMidstoreSchemeQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MidstoreSchemeQueryServiceImpl
implements IMidstoreSchemeQueryService {
    @Autowired
    private IMidstoreSchemeService schemeService;
    @Autowired
    private IMidstoreSchemeInfoService schemeInfoService;
    @Autowired
    private IMidstoreOrgDataService midstoreOrgService;

    @Override
    public List<MidstoreSchemeDTO> getSchemesByOrgCode(String taskKey, String orgCode) {
        ArrayList<MidstoreSchemeDTO> schemes = new ArrayList<MidstoreSchemeDTO>();
        MidstoreSchemeDTO param = new MidstoreSchemeDTO();
        param.setTaskKey(taskKey);
        List<MidstoreSchemeDTO> list = this.schemeService.list(param);
        for (MidstoreSchemeDTO scheme : list) {
            MidstoreOrgDataDTO orgParam = new MidstoreOrgDataDTO();
            orgParam.setSchemeKey(scheme.getKey());
            List<MidstoreOrgDataDTO> orgList = this.midstoreOrgService.list(orgParam);
            MidstoreSchemeInfoDTO info = this.schemeInfoService.getBySchemeKey(scheme.getKey());
            if (info == null) continue;
            if (info.isAllOrgData()) {
                schemes.add(scheme);
                continue;
            }
            List collect = orgList.stream().filter(a -> a.getCode().equals(orgCode)).collect(Collectors.toList());
            if (collect == null || collect.isEmpty()) continue;
            schemes.add(scheme);
        }
        return schemes;
    }

    @Override
    public List<MidstoreSchemeDTO> getSchemesByCodes(String taskKey, List<String> schemeCodes) {
        ArrayList<MidstoreSchemeDTO> schemes = new ArrayList<MidstoreSchemeDTO>();
        for (String schemeCode : schemeCodes) {
            MidstoreSchemeDTO scheme = this.schemeService.getByCode(schemeCode);
            if (scheme == null || !taskKey.equalsIgnoreCase(scheme.getTaskKey())) continue;
            schemes.add(scheme);
        }
        return schemes;
    }

    @Override
    public List<MidstoreSchemeDTO> getSchemesByTask(String taskKey) {
        MidstoreSchemeDTO param = new MidstoreSchemeDTO();
        param.setTaskKey(taskKey);
        List<MidstoreSchemeDTO> list = this.schemeService.list(param);
        return list;
    }
}

