/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.midstore.core.definition.common.PublishStateType
 *  com.jiuqi.nvwa.midstore.core.definition.dto.MidstoreOrgDataDTO
 *  com.jiuqi.nvwa.midstore.core.definition.dto.MidstoreSchemeDTO
 *  com.jiuqi.nvwa.midstore.core.definition.dto.MidstoreSchemeInfoDTO
 *  com.jiuqi.nvwa.midstore.core.definition.dto.MidstoreSourceDTO
 *  com.jiuqi.nvwa.midstore.core.definition.service.IMidstoreOrgDataService
 *  com.jiuqi.nvwa.midstore.core.definition.service.IMidstoreSchemeInfoService
 *  com.jiuqi.nvwa.midstore.core.definition.service.IMidstoreSchemeService
 *  com.jiuqi.nvwa.midstore.core.definition.service.IMidstoreSourceService
 */
package nr.midstore2.data.param.internal;

import com.jiuqi.nvwa.midstore.core.definition.common.PublishStateType;
import com.jiuqi.nvwa.midstore.core.definition.dto.MidstoreOrgDataDTO;
import com.jiuqi.nvwa.midstore.core.definition.dto.MidstoreSchemeDTO;
import com.jiuqi.nvwa.midstore.core.definition.dto.MidstoreSchemeInfoDTO;
import com.jiuqi.nvwa.midstore.core.definition.dto.MidstoreSourceDTO;
import com.jiuqi.nvwa.midstore.core.definition.service.IMidstoreOrgDataService;
import com.jiuqi.nvwa.midstore.core.definition.service.IMidstoreSchemeInfoService;
import com.jiuqi.nvwa.midstore.core.definition.service.IMidstoreSchemeService;
import com.jiuqi.nvwa.midstore.core.definition.service.IMidstoreSourceService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import nr.midstore2.data.extension.bean.ReportDataSourceDTO;
import nr.midstore2.data.param.IReportMidstoreSchemeQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReportMidstoreSchemeQueryServiceImpl
implements IReportMidstoreSchemeQueryService {
    @Autowired
    private IMidstoreSchemeService schemeService;
    @Autowired
    private IMidstoreSchemeInfoService schemeInfoService;
    @Autowired
    private IMidstoreOrgDataService midstoreOrgService;
    @Autowired
    private IMidstoreSourceService sourceService;

    @Override
    public List<MidstoreSchemeDTO> getSchemesByOrgCode(String taskKey, String orgCode) {
        ArrayList<MidstoreSchemeDTO> schemes = new ArrayList<MidstoreSchemeDTO>();
        List<MidstoreSchemeDTO> list = this.getSchemesByTask(taskKey);
        for (MidstoreSchemeDTO scheme : list) {
            MidstoreOrgDataDTO orgParam = new MidstoreOrgDataDTO();
            orgParam.setSchemeKey(scheme.getKey());
            List orgList = this.midstoreOrgService.list(orgParam);
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
    public MidstoreSchemeDTO getSchemeByKey(String schemeKey) {
        return this.schemeService.getByKey(schemeKey);
    }

    @Override
    public List<MidstoreSchemeDTO> getSchemesBySchemeKeys(List<String> schemeKeys) {
        ArrayList<MidstoreSchemeDTO> schemes = new ArrayList<MidstoreSchemeDTO>();
        for (String schemekey : schemeKeys) {
            MidstoreSchemeDTO scheme = this.schemeService.getByKey(schemekey);
            if (scheme == null) continue;
            schemes.add(scheme);
        }
        return schemes;
    }

    @Override
    public List<MidstoreSchemeDTO> getSchemesBySchemeCodes(List<String> schemeCodes) {
        ArrayList<MidstoreSchemeDTO> schemes = new ArrayList<MidstoreSchemeDTO>();
        for (String schemeCode : schemeCodes) {
            MidstoreSchemeDTO scheme = this.schemeService.getByCode(schemeCode);
            if (scheme == null) continue;
            schemes.add(scheme);
        }
        return schemes;
    }

    @Override
    public List<MidstoreSchemeDTO> getSchemesByCodes(String taskKey, List<String> schemeCodes) {
        ArrayList<MidstoreSchemeDTO> schemes = new ArrayList<MidstoreSchemeDTO>();
        for (String schemeCode : schemeCodes) {
            MidstoreSchemeDTO scheme = this.schemeService.getByCode(schemeCode);
            if (!this.isReportScheme(scheme, taskKey)) continue;
            schemes.add(scheme);
        }
        return schemes;
    }

    @Override
    public List<MidstoreSchemeDTO> getSchemesByTask(String taskKey) {
        ArrayList<MidstoreSchemeDTO> schemes = new ArrayList<MidstoreSchemeDTO>();
        MidstoreSchemeDTO param = new MidstoreSchemeDTO();
        List list = this.schemeService.list(param);
        for (MidstoreSchemeDTO scheme : list) {
            if (!this.isReportScheme(scheme, taskKey)) continue;
            schemes.add(scheme);
        }
        return schemes;
    }

    @Override
    public List<MidstoreSchemeDTO> getSchemesByTasks(List<String> taskKeys) {
        ArrayList<MidstoreSchemeDTO> schemes = new ArrayList<MidstoreSchemeDTO>();
        MidstoreSchemeDTO param = new MidstoreSchemeDTO();
        List list = this.schemeService.list(param);
        HashSet<String> taskKeyDic = new HashSet<String>(taskKeys);
        for (MidstoreSchemeDTO scheme : list) {
            ReportDataSourceDTO reportSource;
            MidstoreSourceDTO sourceDTO;
            String code = ";" + scheme.getSourceTypes() + ";";
            if (!code.contains(";nr_midstore_field;") || (sourceDTO = this.sourceService.getBySchemeAndSource(scheme.getKey(), "nr_midstore_field")) == null || !taskKeyDic.contains((reportSource = new ReportDataSourceDTO(sourceDTO)).getTaskKey())) continue;
            schemes.add(scheme);
        }
        return schemes;
    }

    private boolean isReportScheme(MidstoreSchemeDTO scheme, String taskKey) {
        ReportDataSourceDTO reportSource;
        MidstoreSourceDTO sourceDTO;
        boolean result = false;
        String code = ";" + scheme.getSourceTypes() + ";";
        if (code.contains(";nr_midstore_field;") && (sourceDTO = this.sourceService.getBySchemeAndSource(scheme.getKey(), "nr_midstore_field")) != null && taskKey.equalsIgnoreCase((reportSource = new ReportDataSourceDTO(sourceDTO)).getTaskKey())) {
            result = true;
        }
        return result;
    }

    private boolean isSamePublishSateScheme(MidstoreSchemeDTO scheme, PublishStateType publishState) {
        MidstoreSchemeInfoDTO schemeInfo = this.schemeInfoService.getBySchemeKey(scheme.getKey());
        if (schemeInfo != null) {
            return schemeInfo.getPublishState() == publishState;
        }
        return false;
    }

    @Override
    public List<ReportDataSourceDTO> getDataSoruceBySchemeCodes(List<String> schemeCodes) {
        ArrayList<ReportDataSourceDTO> list = new ArrayList<ReportDataSourceDTO>();
        HashMap<String, MidstoreSchemeDTO> schemMaps = new HashMap<String, MidstoreSchemeDTO>();
        for (String schemeCode : schemeCodes) {
            MidstoreSchemeDTO scheme = this.schemeService.getByCode(schemeCode);
            schemMaps.put(scheme.getKey(), scheme);
        }
        List sources = this.sourceService.getBySourceType("nr_midstore_field");
        if (sources != null && !sources.isEmpty()) {
            for (MidstoreSourceDTO source : sources) {
                if (!schemMaps.containsKey(source.getSchemeKey())) continue;
                ReportDataSourceDTO reportSource = new ReportDataSourceDTO(source);
                list.add(reportSource);
            }
        }
        return list;
    }

    @Override
    public List<ReportDataSourceDTO> getDataSoruceBySchemeKeys(List<String> schemeKeys) {
        ArrayList<ReportDataSourceDTO> list = new ArrayList<ReportDataSourceDTO>();
        HashMap<String, MidstoreSchemeDTO> schemMaps = new HashMap<String, MidstoreSchemeDTO>();
        for (String schemeKey : schemeKeys) {
            MidstoreSchemeDTO scheme = this.schemeService.getByKey(schemeKey);
            schemMaps.put(scheme.getKey(), scheme);
        }
        List sources = this.sourceService.getBySourceType("nr_midstore_field");
        if (sources != null && !sources.isEmpty()) {
            for (MidstoreSourceDTO source : sources) {
                if (!schemMaps.containsKey(source.getSchemeKey())) continue;
                ReportDataSourceDTO reportSource = new ReportDataSourceDTO(source);
                list.add(reportSource);
            }
        }
        return list;
    }

    @Override
    public List<MidstoreSchemeDTO> getSchemesBySchemeKeys(List<String> schemeKeys, PublishStateType publishState) {
        List<MidstoreSchemeDTO> list = this.getSchemesBySchemeKeys(schemeKeys);
        ArrayList<MidstoreSchemeDTO> list2 = new ArrayList<MidstoreSchemeDTO>();
        if (list != null && !list.isEmpty()) {
            for (MidstoreSchemeDTO scheme : list) {
                if (!this.isSamePublishSateScheme(scheme, publishState)) continue;
                list2.add(scheme);
            }
        }
        return list2;
    }

    @Override
    public List<MidstoreSchemeDTO> getSchemesByTask(String taskKey, PublishStateType publishState) {
        List<MidstoreSchemeDTO> list = this.getSchemesByTask(taskKey);
        ArrayList<MidstoreSchemeDTO> list2 = new ArrayList<MidstoreSchemeDTO>();
        if (list != null && !list.isEmpty()) {
            for (MidstoreSchemeDTO scheme : list) {
                if (!this.isSamePublishSateScheme(scheme, publishState)) continue;
                list2.add(scheme);
            }
        }
        return list2;
    }
}

