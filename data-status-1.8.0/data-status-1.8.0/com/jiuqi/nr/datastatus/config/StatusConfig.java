/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.attachment.listener.IFileListenerProvider
 *  com.jiuqi.nr.dataservice.core.datastatus.IDataStatusPreInitService
 *  com.jiuqi.nr.dataservice.core.datastatus.obj.DataStatusPresetInfo
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 */
package com.jiuqi.nr.datastatus.config;

import com.jiuqi.nr.attachment.listener.IFileListenerProvider;
import com.jiuqi.nr.dataservice.core.datastatus.IDataStatusPreInitService;
import com.jiuqi.nr.dataservice.core.datastatus.obj.DataStatusPresetInfo;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.datastatus.config.ConfigProperties;
import com.jiuqi.nr.datastatus.facade.obj.BatchRefreshStatusPar;
import com.jiuqi.nr.datastatus.facade.obj.ClearStatusPar;
import com.jiuqi.nr.datastatus.facade.obj.ICopySetting;
import com.jiuqi.nr.datastatus.facade.obj.RefreshStatusPar;
import com.jiuqi.nr.datastatus.facade.obj.RollbackStatusPar;
import com.jiuqi.nr.datastatus.facade.service.IDataStatusService;
import com.jiuqi.nr.datastatus.internal.impl.DataStatusPreInitServiceImpl;
import com.jiuqi.nr.datastatus.internal.impl.DataStatusServiceImpl2;
import com.jiuqi.nr.datastatus.internal.impl.FileListenerProviderImpl;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages={"com.jiuqi.nr.datastatus"})
@EnableConfigurationProperties(value={ConfigProperties.class})
public class StatusConfig {
    private final ConfigProperties configProperties;

    public StatusConfig(ConfigProperties configProperties) {
        this.configProperties = configProperties;
    }

    @Bean
    public IDataStatusService getDataStatusService() {
        if (StatusConfig.enableService(this.configProperties)) {
            return new DataStatusServiceImpl2();
        }
        return new IDataStatusService(){

            @Override
            public List<String> getFilledPeriod(String formSchemeKey, DimensionCollection filterDim) {
                return Collections.emptyList();
            }

            @Override
            public List<String> getFilledAdjust(String formSchemeKey, String period) {
                return Collections.emptyList();
            }

            @Override
            public List<String> getFilledUnit(String formSchemeKey, DimensionCollection filterDim) {
                return Collections.emptyList();
            }

            @Override
            public List<String> getFilledFormKey(String formSchemeKey, DimensionCombination filterDim) {
                return Collections.emptyList();
            }

            @Override
            public void refreshDataStatus(RefreshStatusPar par) throws Exception {
            }

            @Override
            public void rollbackDataStatus(RollbackStatusPar par) throws Exception {
            }

            @Override
            public void clearDataStatusByForm(ClearStatusPar par) throws Exception {
            }

            @Override
            public void copyDataStatus(ICopySetting par) throws Exception {
            }

            @Override
            public void batchRefreshDataStatus(BatchRefreshStatusPar par) throws Exception {
            }
        };
    }

    @Bean
    public IFileListenerProvider getFileListenerProvider() {
        if (StatusConfig.enableService(this.configProperties)) {
            return new FileListenerProviderImpl();
        }
        return null;
    }

    @Bean
    public IDataStatusPreInitService getDataStatusPreInitService() {
        if (StatusConfig.enableService(this.configProperties)) {
            return new DataStatusPreInitServiceImpl();
        }
        return new IDataStatusPreInitService(){

            public DataStatusPresetInfo initInfo(DimensionCombination dimensionCombination, String formSchemeKey, String formulaSchemeKey, Collection<String> formulaKeys) throws Exception {
                return null;
            }

            public DataStatusPresetInfo initInfo(DimensionCombination dimensionCombination, String formSchemeKey, Collection<String> formKeys) throws Exception {
                return null;
            }
        };
    }

    private static boolean enableService(ConfigProperties configProperties) {
        if (configProperties == null) {
            return true;
        }
        return configProperties.isEnable();
    }
}

