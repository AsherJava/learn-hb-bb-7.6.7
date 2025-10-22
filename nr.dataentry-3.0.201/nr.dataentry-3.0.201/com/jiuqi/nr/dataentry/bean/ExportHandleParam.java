/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.np.core.context.NpContext
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.jtable.params.base.EntityViewData
 *  com.jiuqi.nr.jtable.service.ICustomRegionsGradeService
 *  com.jiuqi.nr.jtable.service.IJtableEntityService
 *  com.jiuqi.nr.jtable.service.IJtableParamService
 *  com.jiuqi.nr.jtable.service.ISecretLevelService
 *  org.springframework.jdbc.core.JdbcTemplate
 */
package com.jiuqi.nr.dataentry.bean;

import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.core.context.NpContext;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.dataentry.bean.BatchExportInfo;
import com.jiuqi.nr.dataentry.export.IReportExport;
import com.jiuqi.nr.dataentry.model.BatchDimensionParam;
import com.jiuqi.nr.dataentry.provider.DimensionValueProvider;
import com.jiuqi.nr.dataentry.readwrite.ReadWriteAccessProvider;
import com.jiuqi.nr.dataentry.readwrite.impl.ReadWriteAccessCacheManager;
import com.jiuqi.nr.dataentry.service.ExportExcelNameService;
import com.jiuqi.nr.jtable.params.base.EntityViewData;
import com.jiuqi.nr.jtable.service.ICustomRegionsGradeService;
import com.jiuqi.nr.jtable.service.IJtableEntityService;
import com.jiuqi.nr.jtable.service.IJtableParamService;
import com.jiuqi.nr.jtable.service.ISecretLevelService;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;

public class ExportHandleParam {
    private String companyType;
    private String dateType;
    private List<EntityViewData> entityList;
    private String dateDir;
    private List<String> listEntity;
    private List<String> formKeys;
    private List<String> repeatCompanyNameList;
    private List<String> companyList;
    private ReadWriteAccessCacheManager readWriteAccessCacheManager;
    private List<Map<String, DimensionValue>> currentDimension;
    private BatchDimensionParam dimensionInfoBuild;
    private int currentIndex;
    private List<String> splitAllForms;
    private ReadWriteAccessProvider readWriteAccessProvider;
    private IJtableParamService jtableParamService;
    private IReportExport reportExportService;
    private IJtableEntityService jtableEntityService;
    private DimensionValueProvider dimensionValueProvider;
    private NpContext npContext;
    private BatchExportInfo info;
    private AsyncTaskMonitor asyncTaskMonitor;
    private CountDownLatch latch;
    private DataSource dataSource;
    private JdbcTemplate jdbcTemplate;
    private ExportExcelNameService exportExcelNameService;
    private ISecretLevelService iSecretLevelService;
    private ICustomRegionsGradeService iCustomRegionsGradeService;

    public JdbcTemplate getJdbcTemplate() {
        return this.jdbcTemplate;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public DataSource getDataSource() {
        return this.dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public String getCompanyType() {
        return this.companyType;
    }

    public void setCompanyType(String companyType) {
        this.companyType = companyType;
    }

    public String getDateType() {
        return this.dateType;
    }

    public void setDateType(String dateType) {
        this.dateType = dateType;
    }

    public List<EntityViewData> getEntityList() {
        return this.entityList;
    }

    public void setEntityList(List<EntityViewData> entityList) {
        this.entityList = entityList;
    }

    public String getDateDir() {
        return this.dateDir;
    }

    public void setDateDir(String dateDir) {
        this.dateDir = dateDir;
    }

    public List<String> getListEntity() {
        return this.listEntity;
    }

    public void setListEntity(List<String> listEntity) {
        this.listEntity = listEntity;
    }

    public List<String> getFormKeys() {
        return this.formKeys;
    }

    public void setFormKeys(List<String> formKeys) {
        this.formKeys = formKeys;
    }

    public List<String> getRepeatCompanyNameList() {
        return this.repeatCompanyNameList;
    }

    public void setRepeatCompanyNameList(List<String> repeatCompanyNameList) {
        this.repeatCompanyNameList = repeatCompanyNameList;
    }

    public List<String> getCompanyList() {
        return this.companyList;
    }

    public void setCompanyList(List<String> companyList) {
        this.companyList = companyList;
    }

    public ReadWriteAccessCacheManager getReadWriteAccessCacheManager() {
        return this.readWriteAccessCacheManager;
    }

    public void setReadWriteAccessCacheManager(ReadWriteAccessCacheManager readWriteAccessCacheManager) {
        this.readWriteAccessCacheManager = readWriteAccessCacheManager;
    }

    public List<Map<String, DimensionValue>> getCurrentDimension() {
        return this.currentDimension;
    }

    public void setCurrentDimension(List<Map<String, DimensionValue>> currentDimension) {
        this.currentDimension = currentDimension;
    }

    public BatchDimensionParam getDimensionInfoBuild() {
        return this.dimensionInfoBuild;
    }

    public void setDimensionInfoBuild(BatchDimensionParam dimensionInfoBuild) {
        this.dimensionInfoBuild = dimensionInfoBuild;
    }

    public int getCurrentIndex() {
        return this.currentIndex;
    }

    public void setCurrentIndex(int currentIndex) {
        this.currentIndex = currentIndex;
    }

    public NpContext getNpContext() {
        return this.npContext;
    }

    public void setNpContext(NpContext npContext) {
        this.npContext = npContext;
    }

    public BatchExportInfo getInfo() {
        return this.info;
    }

    public void setInfo(BatchExportInfo info) {
        this.info = info;
    }

    public AsyncTaskMonitor getAsyncTaskMonitor() {
        return this.asyncTaskMonitor;
    }

    public void setAsyncTaskMonitor(AsyncTaskMonitor asyncTaskMonitor) {
        this.asyncTaskMonitor = asyncTaskMonitor;
    }

    public CountDownLatch getLatch() {
        return this.latch;
    }

    public void setLatch(CountDownLatch latch) {
        this.latch = latch;
    }

    public List<String> getSplitAllForms() {
        return this.splitAllForms;
    }

    public void setSplitAllForms(List<String> splitAllForms) {
        this.splitAllForms = splitAllForms;
    }

    public ReadWriteAccessProvider getReadWriteAccessProvider() {
        return this.readWriteAccessProvider;
    }

    public void setReadWriteAccessProvider(ReadWriteAccessProvider readWriteAccessProvider) {
        this.readWriteAccessProvider = readWriteAccessProvider;
    }

    public IJtableParamService getJtableParamService() {
        return this.jtableParamService;
    }

    public void setJtableParamService(IJtableParamService jtableParamService) {
        this.jtableParamService = jtableParamService;
    }

    public IReportExport getReportExportService() {
        return this.reportExportService;
    }

    public void setReportExportService(IReportExport reportExportService) {
        this.reportExportService = reportExportService;
    }

    public IJtableEntityService getJtableEntityService() {
        return this.jtableEntityService;
    }

    public void setJtableEntityService(IJtableEntityService jtableEntityService) {
        this.jtableEntityService = jtableEntityService;
    }

    public DimensionValueProvider getDimensionValueProvider() {
        return this.dimensionValueProvider;
    }

    public void setDimensionValueProvider(DimensionValueProvider dimensionValueProvider) {
        this.dimensionValueProvider = dimensionValueProvider;
    }

    public ExportExcelNameService getExportExcelNameService() {
        return this.exportExcelNameService;
    }

    public void setExportExcelNameService(ExportExcelNameService exportExcelNameService) {
        this.exportExcelNameService = exportExcelNameService;
    }

    public ISecretLevelService getiSecretLevelService() {
        return this.iSecretLevelService;
    }

    public void setiSecretLevelService(ISecretLevelService iSecretLevelService) {
        this.iSecretLevelService = iSecretLevelService;
    }

    public ICustomRegionsGradeService getiCustomRegionsGradeService() {
        return this.iCustomRegionsGradeService;
    }

    public void setiCustomRegionsGradeService(ICustomRegionsGradeService iCustomRegionsGradeService) {
        this.iCustomRegionsGradeService = iCustomRegionsGradeService;
    }
}

