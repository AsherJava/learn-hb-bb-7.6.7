/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 */
package nr.single.para.compare.internal.defintion.service;

import com.jiuqi.bi.util.StringUtils;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;
import nr.single.para.compare.definition.CompareDataPrintSchemeDTO;
import nr.single.para.compare.definition.ISingleCompareDataPrintScemeService;
import nr.single.para.compare.definition.convert.Convert;
import nr.single.para.compare.definition.exception.SingleCompareException;
import nr.single.para.compare.internal.defintion.CompareDataPrintSchemeDO;
import nr.single.para.compare.internal.defintion.dao.ICompareDataDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SingleCompareDataPrintSchemeServiceImpl
implements ISingleCompareDataPrintScemeService {
    @Autowired
    private ICompareDataDao<CompareDataPrintSchemeDO> compareDataDao;
    private final Function<CompareDataPrintSchemeDO, CompareDataPrintSchemeDTO> toDto = Convert::cdpsDo;
    private final Function<List<CompareDataPrintSchemeDO>, List<CompareDataPrintSchemeDTO>> list2Dto = r -> r.stream().map(this.toDto).filter(Objects::nonNull).collect(Collectors.toList());

    @Override
    public List<CompareDataPrintSchemeDTO> list(CompareDataPrintSchemeDTO compareDataDTO) {
        ArrayList<CompareDataPrintSchemeDTO> list = new ArrayList<CompareDataPrintSchemeDTO>();
        if (StringUtils.isNotEmpty((String)compareDataDTO.getKey())) {
            CompareDataPrintSchemeDO obj = this.compareDataDao.get(compareDataDTO.getKey());
            list.add(this.toDto.apply(obj));
        } else if (StringUtils.isNotEmpty((String)compareDataDTO.getInfoKey())) {
            List<CompareDataPrintSchemeDO> list2 = this.compareDataDao.getByInfoKey(compareDataDTO.getInfoKey());
            list.addAll((Collection<CompareDataPrintSchemeDTO>)this.list2Dto.apply(list2));
        }
        return list;
    }

    @Override
    public void add(CompareDataPrintSchemeDTO compareDataDTO) throws SingleCompareException {
        this.compareDataDao.insert(compareDataDTO);
    }

    @Override
    public void update(CompareDataPrintSchemeDTO compareDataDTO) throws SingleCompareException {
        this.compareDataDao.update(compareDataDTO);
    }

    @Override
    public void delete(CompareDataPrintSchemeDTO compareDataDTO) throws SingleCompareException {
        if (StringUtils.isNotEmpty((String)compareDataDTO.getKey())) {
            this.compareDataDao.delete(compareDataDTO.getKey());
        } else if (StringUtils.isNotEmpty((String)compareDataDTO.getInfoKey())) {
            this.compareDataDao.deleteByInfoKey(compareDataDTO.getInfoKey());
        }
    }

    @Override
    public void batchAdd(List<CompareDataPrintSchemeDTO> compareDataDTOs) throws SingleCompareException {
        ArrayList<CompareDataPrintSchemeDTO> list2 = new ArrayList<CompareDataPrintSchemeDTO>();
        list2.addAll(compareDataDTOs);
        this.compareDataDao.batchInsert(list2);
    }

    @Override
    public void batchUpdate(List<CompareDataPrintSchemeDTO> compareDataDTOs) throws SingleCompareException {
        ArrayList<CompareDataPrintSchemeDTO> list2 = new ArrayList<CompareDataPrintSchemeDTO>();
        list2.addAll(compareDataDTOs);
        this.compareDataDao.batchUpdate(list2);
    }

    @Override
    public void batchDelete(List<CompareDataPrintSchemeDTO> compareDataDTOs) throws SingleCompareException {
        ArrayList<String> list2 = new ArrayList<String>();
        for (CompareDataPrintSchemeDTO dto : compareDataDTOs) {
            list2.add(dto.getKey());
        }
        this.compareDataDao.batchDelete(list2);
    }
}

