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
import nr.single.para.compare.definition.CompareDataPrintItemDTO;
import nr.single.para.compare.definition.ISingleCompareDataPrintItemService;
import nr.single.para.compare.definition.convert.Convert;
import nr.single.para.compare.definition.exception.SingleCompareException;
import nr.single.para.compare.internal.defintion.CompareDataPrintItemDO;
import nr.single.para.compare.internal.defintion.dao.ICompareDataDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SingleCompareDataPrintItemServiceImpl
implements ISingleCompareDataPrintItemService {
    @Autowired
    private ICompareDataDao<CompareDataPrintItemDO> compareDataDao;
    private final Function<CompareDataPrintItemDO, CompareDataPrintItemDTO> toDto = Convert::cdpiDo;
    private final Function<List<CompareDataPrintItemDO>, List<CompareDataPrintItemDTO>> list2Dto = r -> r.stream().map(this.toDto).filter(Objects::nonNull).collect(Collectors.toList());

    @Override
    public List<CompareDataPrintItemDTO> list(CompareDataPrintItemDTO compareDataDTO) {
        ArrayList<CompareDataPrintItemDTO> list = new ArrayList<CompareDataPrintItemDTO>();
        if (StringUtils.isNotEmpty((String)compareDataDTO.getKey())) {
            CompareDataPrintItemDO obj = this.compareDataDao.get(compareDataDTO.getKey());
            list.add(this.toDto.apply(obj));
        } else if (StringUtils.isNotEmpty((String)compareDataDTO.getInfoKey())) {
            if (StringUtils.isNotEmpty((String)compareDataDTO.getSinglePrintScheme())) {
                List<CompareDataPrintItemDO> list2 = this.compareDataDao.getByParentInInfo(compareDataDTO.getInfoKey(), compareDataDTO.getSinglePrintScheme());
                list.addAll((Collection<CompareDataPrintItemDTO>)this.list2Dto.apply(list2));
            } else {
                List<CompareDataPrintItemDO> list2 = this.compareDataDao.getByInfoKey(compareDataDTO.getInfoKey());
                list.addAll((Collection<CompareDataPrintItemDTO>)this.list2Dto.apply(list2));
            }
        }
        return list;
    }

    @Override
    public void add(CompareDataPrintItemDTO compareDataDTO) throws SingleCompareException {
        this.compareDataDao.insert(compareDataDTO);
    }

    @Override
    public void update(CompareDataPrintItemDTO compareDataDTO) throws SingleCompareException {
        this.compareDataDao.update(compareDataDTO);
    }

    @Override
    public void delete(CompareDataPrintItemDTO compareDataDTO) throws SingleCompareException {
        if (StringUtils.isNotEmpty((String)compareDataDTO.getKey())) {
            this.compareDataDao.delete(compareDataDTO.getKey());
        } else if (StringUtils.isNotEmpty((String)compareDataDTO.getInfoKey())) {
            this.compareDataDao.deleteByInfoKey(compareDataDTO.getInfoKey());
        }
    }

    @Override
    public void batchAdd(List<CompareDataPrintItemDTO> compareDataDTOs) throws SingleCompareException {
        ArrayList<CompareDataPrintItemDTO> list2 = new ArrayList<CompareDataPrintItemDTO>();
        list2.addAll(compareDataDTOs);
        this.compareDataDao.batchInsert(list2);
    }

    @Override
    public void batchUpdate(List<CompareDataPrintItemDTO> compareDataDTOs) throws SingleCompareException {
        ArrayList<CompareDataPrintItemDTO> list2 = new ArrayList<CompareDataPrintItemDTO>();
        list2.addAll(compareDataDTOs);
        this.compareDataDao.batchUpdate(list2);
    }

    @Override
    public void batchDelete(List<CompareDataPrintItemDTO> compareDataDTOs) throws SingleCompareException {
        ArrayList<String> list2 = new ArrayList<String>();
        for (CompareDataPrintItemDTO dto : compareDataDTOs) {
            list2.add(dto.getKey());
        }
        this.compareDataDao.batchDelete(list2);
    }
}

