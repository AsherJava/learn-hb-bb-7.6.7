/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.sql.type.Convert
 *  com.jiuqi.nr.configuration.common.ConfigContentType
 *  com.jiuqi.nr.configuration.config.ConfigurationConfig
 *  com.jiuqi.nr.configuration.controller.IBusinessConfigurationController
 *  com.jiuqi.nr.configuration.facade.BusinessConfigurationDefine
 *  com.jiuqi.nr.definition.facade.print.DataTransformUtil
 */
package nr.single.map.data.internal;

import com.jiuqi.np.sql.type.Convert;
import com.jiuqi.nr.configuration.common.ConfigContentType;
import com.jiuqi.nr.configuration.config.ConfigurationConfig;
import com.jiuqi.nr.configuration.controller.IBusinessConfigurationController;
import com.jiuqi.nr.configuration.facade.BusinessConfigurationDefine;
import com.jiuqi.nr.definition.facade.print.DataTransformUtil;
import nr.single.map.data.facade.SingleMapFormSchemeDefine;
import nr.single.map.data.internal.SingleMapFormSchemeDefineImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SingleMapNrService {
    private static final Logger logger = LoggerFactory.getLogger(SingleMapNrService.class);
    @Autowired
    private ConfigurationConfig config;

    public SingleMapFormSchemeDefine QuerySingleMapDefine(String taskKey, String formSchemeKey) {
        SingleMapFormSchemeDefine define = this.QueryAndCreateSingleMapDefine(taskKey, formSchemeKey, false);
        return define;
    }

    public SingleMapFormSchemeDefine QueryAndCreateSingleMapDefine(String taskKey, String formSchemeKey, boolean isCreate) {
        boolean isNew;
        SingleMapFormSchemeDefine define = null;
        IBusinessConfigurationController confingController = this.config.getBusinessConfigurationController();
        BusinessConfigurationDefine configDefine = confingController.getConfiguration(taskKey, formSchemeKey, "FormSchemeMapSingle");
        boolean bl = isNew = null == configDefine;
        if (isNew) {
            if (isCreate) {
                define = new SingleMapFormSchemeDefineImpl();
            }
        } else {
            try {
                String code = configDefine.getConfigurationContent();
                byte[] data = Convert.base64ToBytes((CharSequence)code);
                define = (SingleMapFormSchemeDefine)DataTransformUtil.deserialize((byte[])data, SingleMapFormSchemeDefine.class);
            }
            catch (Exception e) {
                logger.info("\u65e7\u683c\u5f0f\u6620\u5c04\u51fa\u9519\uff1aQueryAndCreateSingleMapDefine");
                define = isCreate ? new SingleMapFormSchemeDefineImpl() : null;
            }
        }
        if (isCreate && define == null) {
            define = new SingleMapFormSchemeDefineImpl();
        }
        return define;
    }

    public SingleMapFormSchemeDefine CreateSingleMapDefine() {
        return new SingleMapFormSchemeDefineImpl();
    }

    public void UpdateSingleMapDefine(String taskKey, String formSchemeKey, SingleMapFormSchemeDefine define) {
        boolean isNew;
        if (null == define) {
            return;
        }
        IBusinessConfigurationController confingController = this.config.getBusinessConfigurationController();
        BusinessConfigurationDefine configDefine = confingController.getConfiguration(taskKey, formSchemeKey, "FormSchemeMapSingle");
        boolean bl = isNew = null == configDefine;
        if (isNew) {
            configDefine = confingController.createConfiguration();
            configDefine.setTaskKey(taskKey);
            configDefine.setFormSchemeKey(formSchemeKey);
            configDefine.setCode("FormSchemeMapSingle");
        }
        byte[] data = DataTransformUtil.serializeToByteArray((Object)define);
        String value = "";
        if (data.length > 0) {
            value = Convert.bytesToBase64((byte[])data);
        }
        configDefine.setConfigurationContent(value);
        configDefine.setTitle(define.getTaskInfo().getSingleTaskTitle() + "");
        configDefine.setDescription(define.getTaskInfo().getSingleTaskTitle() + "-\u5bf9\u5e94\u7684\u6620\u5c04\u6587\u4ef6");
        configDefine.setCategory("MapFile");
        configDefine.setContentType(ConfigContentType.CCT_BINARY);
        if (isNew) {
            confingController.addConfiguration(configDefine);
        }
        confingController.updateConfiguration(configDefine);
    }
}

