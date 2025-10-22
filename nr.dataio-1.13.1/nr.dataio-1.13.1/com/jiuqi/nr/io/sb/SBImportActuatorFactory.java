/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.temptable.ITempTableManager
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.fielddatacrud.ActuatorConfig
 *  com.jiuqi.nr.fielddatacrud.ISBActuator
 *  com.jiuqi.nr.fielddatacrud.config.FieldDataProperties
 *  com.jiuqi.nr.fielddatacrud.spi.ISBImportActuatorFactory
 *  com.jiuqi.nvwa.encryption.crypto.SymmetricEncryptFactory
 *  org.springframework.jdbc.core.JdbcTemplate
 */
package com.jiuqi.nr.io.sb;

import com.jiuqi.nr.common.temptable.ITempTableManager;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.fielddatacrud.ActuatorConfig;
import com.jiuqi.nr.fielddatacrud.ISBActuator;
import com.jiuqi.nr.fielddatacrud.config.FieldDataProperties;
import com.jiuqi.nr.fielddatacrud.spi.ISBImportActuatorFactory;
import com.jiuqi.nr.io.bufdb.IIOBufDBProvider;
import com.jiuqi.nr.io.sb.ISBImportActuator;
import com.jiuqi.nr.io.sb.SBImportActuatorConfig;
import com.jiuqi.nr.io.sb.SBImportActuatorType;
import com.jiuqi.nr.io.sb.service.Impl.BufDBBatchByUnitOrderActuator;
import com.jiuqi.nr.io.sb.service.Impl.BufDBDataDealActuator;
import com.jiuqi.nr.io.sb.service.Impl.BufDBImportActuator;
import com.jiuqi.nr.io.sb.service.Impl.MdCodeTableCreateDao;
import com.jiuqi.nr.io.sb.service.Impl.SbIdBufDBImportActuator;
import com.jiuqi.nr.io.sb.service.Impl.SbIdTableCreateDao;
import com.jiuqi.nr.io.tz.listener.TzDataChangeListener;
import com.jiuqi.nvwa.encryption.crypto.SymmetricEncryptFactory;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class SBImportActuatorFactory
implements ISBImportActuatorFactory {
    @Autowired
    private IIOBufDBProvider provider;
    @Autowired
    private IRuntimeDataSchemeService schemeService;
    @Autowired
    private MdCodeTableCreateDao mdCodeTableCreateDao;
    @Autowired
    private SbIdTableCreateDao sbIdTableCreateDao;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired(required=false)
    private List<TzDataChangeListener> listeners;
    @Autowired
    private ITempTableManager tempTableManager;
    @Autowired
    private SymmetricEncryptFactory sEFactory;
    @Autowired(required=false)
    private FieldDataProperties fieldDataProperties;

    public ISBImportActuator getImportActuator(SBImportActuatorConfig cfg) {
        if (cfg == null) {
            return null;
        }
        SBImportActuatorType type = cfg.getImportActuatorType();
        if (type == SBImportActuatorType.BUF_DB) {
            if (cfg.isSpecifySbId()) {
                return new SbIdBufDBImportActuator().setSBImportActuatorConfig(cfg).setProvider(this.provider).setSchemeService(this.schemeService).setMdCodeTableCreateDao(this.mdCodeTableCreateDao).setSbIdTableCreateDao(this.sbIdTableCreateDao).setJdbcTemplate(this.jdbcTemplate).setChangeListener(this.listeners).setTempTableManager(this.tempTableManager).setSymmetricEncryptFactory(this.sEFactory);
            }
            return new BufDBImportActuator().setSBImportActuatorConfig(cfg).setProvider(this.provider).setSchemeService(this.schemeService).setMdCodeTableCreateDao(this.mdCodeTableCreateDao).setSbIdTableCreateDao(this.sbIdTableCreateDao).setJdbcTemplate(this.jdbcTemplate).setChangeListener(this.listeners).setTempTableManager(this.tempTableManager).setSymmetricEncryptFactory(this.sEFactory);
        }
        return null;
    }

    public ISBActuator getActuator(ActuatorConfig cfg) {
        if (cfg == null) {
            return null;
        }
        if (cfg.getActuatorType() == SBImportActuatorType.BUF_DB.getValue()) {
            if (cfg.isBatchByUnit()) {
                if (cfg.isRowByDw()) {
                    return new BufDBBatchByUnitOrderActuator(this.fieldDataProperties, this, cfg);
                }
                return new BufDBDataDealActuator(this.fieldDataProperties, this, cfg).setProvider(this.provider);
            }
            if (cfg.isSpecifySbId()) {
                return new SbIdBufDBImportActuator().setSBImportActuatorConfig(cfg).setProvider(this.provider).setSchemeService(this.schemeService).setMdCodeTableCreateDao(this.mdCodeTableCreateDao).setSbIdTableCreateDao(this.sbIdTableCreateDao).setJdbcTemplate(this.jdbcTemplate).setChangeListener(this.listeners).setTempTableManager(this.tempTableManager).setSymmetricEncryptFactory(this.sEFactory);
            }
            return new BufDBImportActuator().setSBImportActuatorConfig(cfg).setProvider(this.provider).setSchemeService(this.schemeService).setMdCodeTableCreateDao(this.mdCodeTableCreateDao).setSbIdTableCreateDao(this.sbIdTableCreateDao).setJdbcTemplate(this.jdbcTemplate).setChangeListener(this.listeners).setTempTableManager(this.tempTableManager).setSymmetricEncryptFactory(this.sEFactory);
        }
        return null;
    }
}

