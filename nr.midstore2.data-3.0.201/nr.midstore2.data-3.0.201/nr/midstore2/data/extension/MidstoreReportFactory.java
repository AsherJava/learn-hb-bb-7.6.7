/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  com.jiuqi.np.definition.common.SpringBeanProvider
 *  com.jiuqi.nr.mapping2.service.MappingTransferService
 *  com.jiuqi.nvwa.mapping.bean.MappingScheme
 *  com.jiuqi.nvwa.midstore.MidstoreExecutor
 *  com.jiuqi.nvwa.midstore.MidstoreFactory
 *  com.jiuqi.nvwa.midstore.core.definition.bean.mapping.MidstoreParamInfo
 *  com.jiuqi.nvwa.midstore.core.definition.bean.mapping.SchemeMappingInfo
 *  com.jiuqi.nvwa.midstore.core.definition.db.MidstoreException
 *  com.jiuqi.nvwa.midstore.extension.IMidstoreFieldInfoGetor
 *  com.jiuqi.nvwa.midstore.extension.IMidstoreFieldPublishExecutor
 *  com.jiuqi.nvwa.midstore.extension.IMidstoreUIConfiguration
 *  org.json.JSONObject
 */
package nr.midstore2.data.extension;

import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.np.definition.common.SpringBeanProvider;
import com.jiuqi.nr.mapping2.service.MappingTransferService;
import com.jiuqi.nvwa.mapping.bean.MappingScheme;
import com.jiuqi.nvwa.midstore.MidstoreExecutor;
import com.jiuqi.nvwa.midstore.MidstoreFactory;
import com.jiuqi.nvwa.midstore.core.definition.bean.mapping.MidstoreParamInfo;
import com.jiuqi.nvwa.midstore.core.definition.bean.mapping.SchemeMappingInfo;
import com.jiuqi.nvwa.midstore.core.definition.db.MidstoreException;
import com.jiuqi.nvwa.midstore.extension.IMidstoreFieldInfoGetor;
import com.jiuqi.nvwa.midstore.extension.IMidstoreFieldPublishExecutor;
import com.jiuqi.nvwa.midstore.extension.IMidstoreUIConfiguration;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import nr.midstore2.data.extension.MidstoreOrgSelectPluginImpl;
import nr.midstore2.data.extension.MidstoreReportExecutorImpl;
import nr.midstore2.data.extension.MidstoreReportFieldInfoGetorImpl;
import nr.midstore2.data.extension.MidstoreReportFieldPublishExecutorImpl;
import nr.midstore2.data.extension.MidstoreReportPropertyUIConfigImpl;
import nr.midstore2.data.extension.MidstoreReportSourceConfigPluginImpl;
import nr.midstore2.data.extension.MidstoreReportUIConfigurationImpl;
import org.json.JSONObject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class MidstoreReportFactory
extends MidstoreFactory {
    public static final String ID = "nr_midstore_field";
    public static final String TITLE = "NR\u62a5\u8868";
    @Autowired
    private MappingTransferService mappingTransferService;

    public String getSourceTypeID() {
        return ID;
    }

    public String getSourceTypeTitle() {
        return TITLE;
    }

    public boolean overridePeriod() {
        return true;
    }

    public boolean overrideOrg() {
        return true;
    }

    public MidstoreExecutor createMidstoreExecutor() throws MidstoreException {
        return (MidstoreExecutor)SpringBeanProvider.getBean(MidstoreReportExecutorImpl.class);
    }

    public IMidstoreFieldInfoGetor createFieldInfoGetor() throws MidstoreException {
        return (IMidstoreFieldInfoGetor)SpringBeanProvider.getBean(MidstoreReportFieldInfoGetorImpl.class);
    }

    public IMidstoreUIConfiguration getSourceConfigPlugin() {
        return (IMidstoreUIConfiguration)SpringBeanProvider.getBean(MidstoreReportSourceConfigPluginImpl.class);
    }

    public IMidstoreUIConfiguration getUIConfiguration() {
        return (IMidstoreUIConfiguration)SpringBeanProvider.getBean(MidstoreReportUIConfigurationImpl.class);
    }

    public IMidstoreFieldPublishExecutor createFieldPublishExecutor() throws MidstoreException {
        return (IMidstoreFieldPublishExecutor)SpringBeanProvider.getBean(MidstoreReportFieldPublishExecutorImpl.class);
    }

    public IMidstoreUIConfiguration getManageConfiguration() {
        return (IMidstoreUIConfiguration)SpringBeanUtils.getBean(MidstoreReportPropertyUIConfigImpl.class);
    }

    public IMidstoreUIConfiguration getOrgSelectPlugin() {
        return (IMidstoreUIConfiguration)SpringBeanUtils.getBean(MidstoreOrgSelectPluginImpl.class);
    }

    public List<SchemeMappingInfo> getMappingInfo(MidstoreParamInfo param) {
        ArrayList<SchemeMappingInfo> list = new ArrayList<SchemeMappingInfo>();
        if (!StringUtils.hasText(param.getExtendData())) {
            return list;
        }
        JSONObject jsonObject = new JSONObject(param.getExtendData());
        if (!jsonObject.has("taskKey")) {
            return list;
        }
        String taskKey = jsonObject.getString("taskKey");
        if (!StringUtils.hasText(taskKey)) {
            return list;
        }
        MappingTransferService service = (MappingTransferService)SpringBeanProvider.getBean(MappingTransferService.class);
        List mappingSchemes = service.getMappingSchemeByTask(taskKey, "MIDSTORE");
        for (MappingScheme mappingScheme : mappingSchemes) {
            if (!param.getOrgName().equals(mappingScheme.getOrgName())) continue;
            SchemeMappingInfo info = new SchemeMappingInfo();
            BeanUtils.copyProperties(mappingScheme, info);
            list.add(info);
        }
        return list;
    }

    public boolean getExtendEqual(MidstoreParamInfo source, MidstoreParamInfo target) {
        try {
            JSONObject sourceJson = new JSONObject(source.getExtendData());
            JSONObject targetJson = new JSONObject(target.getExtendData());
            return Objects.equals(sourceJson.optString("taskKey"), targetJson.optString("taskKey"));
        }
        catch (Exception e) {
            return super.getExtendEqual(source, target);
        }
    }
}

