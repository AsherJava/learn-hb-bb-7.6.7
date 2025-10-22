/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.sql.CustomClassExecutor
 *  com.jiuqi.nr.configuration.config.ConfigurationConfig
 *  com.jiuqi.nr.definition.controller.IDesignTimeViewController
 *  com.jiuqi.nr.definition.facade.DesignFormSchemeDefine
 *  com.jiuqi.nvwa.systemoption.extend.impl.SystemOptionItemValue
 *  com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService
 *  com.jiuqi.nvwa.systemoption.vo.SystemOptionSave
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 *  nr.single.map.data.service.SingleMappingService
 */
package nr.single.para.compare.update.upper;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.sql.CustomClassExecutor;
import com.jiuqi.nr.configuration.config.ConfigurationConfig;
import com.jiuqi.nr.definition.controller.IDesignTimeViewController;
import com.jiuqi.nr.definition.facade.DesignFormSchemeDefine;
import com.jiuqi.nvwa.systemoption.extend.impl.SystemOptionItemValue;
import com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService;
import com.jiuqi.nvwa.systemoption.vo.SystemOptionSave;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import java.util.Collections;
import java.util.List;
import javax.sql.DataSource;
import nr.single.map.data.service.SingleMappingService;
import nr.single.para.compare.definition.CompareInfoDTO;
import nr.single.para.compare.definition.ISingleCompareInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class SingleDataTranOrgCodeUpperUpdate
implements CustomClassExecutor {
    private final Logger logger = LoggerFactory.getLogger(SingleDataTranOrgCodeUpperUpdate.class);
    @Autowired
    private INvwaSystemOptionService systemOptionService;
    @Autowired
    private ISingleCompareInfoService compareInfoService;
    @Autowired
    private ConfigurationConfig configService;
    @Autowired
    private SingleMappingService mappingService;
    @Autowired
    private IDesignTimeViewController viewController;

    public void execute(DataSource dataSource) throws Exception {
        try {
            String value;
            if (this.systemOptionService == null) {
                this.systemOptionService = (INvwaSystemOptionService)ApplicationContextRegister.getBean(INvwaSystemOptionService.class);
            }
            if (StringUtils.isNotEmpty((String)(value = this.systemOptionService.get("single_para_option_id", "singledata_option_changeupper"))) && "1".equalsIgnoreCase(value)) {
                return;
            }
            if (this.viewController == null) {
                this.viewController = (IDesignTimeViewController)ApplicationContextRegister.getBean(IDesignTimeViewController.class);
            }
            if (this.compareInfoService == null) {
                this.compareInfoService = (ISingleCompareInfoService)ApplicationContextRegister.getBean(ISingleCompareInfoService.class);
            }
            if (this.configService == null) {
                this.configService = (ConfigurationConfig)ApplicationContextRegister.getBean(ConfigurationConfig.class);
            }
            if (this.mappingService == null) {
                this.mappingService = (SingleMappingService)ApplicationContextRegister.getBean(SingleMappingService.class);
            }
            List formSchemes = this.viewController.queryAllFormSchemeDefine();
            for (DesignFormSchemeDefine formScheme : formSchemes) {
                if (!StringUtils.isNotEmpty((String)formScheme.getTaskPrefix())) continue;
                return;
            }
            CompareInfoDTO param = new CompareInfoDTO();
            List<CompareInfoDTO> list = this.compareInfoService.list(param);
            if (!list.isEmpty()) {
                return;
            }
            List list2 = this.mappingService.getAllMapping();
            if (!list2.isEmpty()) {
                return;
            }
            this.saveUpperDefaultValue();
        }
        catch (Exception e) {
            this.logger.error(e.getMessage(), e);
            throw e;
        }
    }

    private void saveUpperDefaultValue() {
        this.logger.info("JIO\u5bfc\u5165\u65f6\u7ec4\u7ec7\u673a\u6784\u4ee3\u7801\u81ea\u52a8\u8f6c\u4e3a\u5927\u5199");
        String value = "1";
        SystemOptionSave option = new SystemOptionSave();
        option.setKey("single_para_option_id");
        SystemOptionItemValue item = new SystemOptionItemValue();
        item.setKey("singledata_option_changeupper");
        item.setValue(value);
        option.setItemValues(Collections.singletonList(item));
        this.systemOptionService.save(option);
    }
}

