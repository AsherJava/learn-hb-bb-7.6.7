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
import nr.single.para.compare.definition.CompareDataEnumItemDTO;
import nr.single.para.compare.definition.ISingleCompareDataEnumItemService;
import nr.single.para.compare.definition.convert.Convert;
import nr.single.para.compare.definition.exception.SingleCompareException;
import nr.single.para.compare.internal.defintion.CompareDataEnumItemDO;
import nr.single.para.compare.internal.defintion.dao.ICompareDataDao;
import nr.single.para.compare.internal.defintion.dao.ICompareDataEnumItemDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SingleCompareDataEnumItemServiceImpl
implements ISingleCompareDataEnumItemService {
    @Autowired
    private ICompareDataDao<CompareDataEnumItemDO> compareDataDao;
    private final Function<CompareDataEnumItemDO, CompareDataEnumItemDTO> toDto = Convert::cdei2Do;
    private final Function<List<CompareDataEnumItemDO>, List<CompareDataEnumItemDTO>> list2Dto = r -> r.stream().map(this.toDto).filter(Objects::nonNull).collect(Collectors.toList());

    @Override
    public List<CompareDataEnumItemDTO> list(CompareDataEnumItemDTO compareDataDTO) {
        ArrayList<CompareDataEnumItemDTO> list = new ArrayList<CompareDataEnumItemDTO>();
        if (StringUtils.isNotEmpty((String)compareDataDTO.getKey())) {
            CompareDataEnumItemDO obj = this.compareDataDao.get(compareDataDTO.getKey());
            list.add(this.toDto.apply(obj));
        } else if (StringUtils.isNotEmpty((String)compareDataDTO.getInfoKey())) {
            if (StringUtils.isNotEmpty((String)compareDataDTO.getEnumCompareKey())) {
                List<CompareDataEnumItemDO> list2 = this.compareDataDao.getByParentInInfo(compareDataDTO.getInfoKey(), compareDataDTO.getEnumCompareKey());
                list.addAll((Collection<CompareDataEnumItemDTO>)this.list2Dto.apply(list2));
            } else {
                List<CompareDataEnumItemDO> list2 = this.compareDataDao.getByInfoKey(compareDataDTO.getInfoKey());
                list.addAll((Collection<CompareDataEnumItemDTO>)this.list2Dto.apply(list2));
            }
        }
        return list;
    }

    @Override
    public void add(CompareDataEnumItemDTO compareDataDTO) throws SingleCompareException {
        this.compareDataDao.insert(compareDataDTO);
    }

    @Override
    public void update(CompareDataEnumItemDTO compareDataDTO) throws SingleCompareException {
        this.compareDataDao.update(compareDataDTO);
    }

    @Override
    public void delete(CompareDataEnumItemDTO compareDataDTO) throws SingleCompareException {
        if (StringUtils.isNotEmpty((String)compareDataDTO.getKey())) {
            this.compareDataDao.delete(compareDataDTO.getKey());
        } else if (StringUtils.isNotEmpty((String)compareDataDTO.getInfoKey()) && StringUtils.isNotEmpty((String)compareDataDTO.getEnumCompareKey())) {
            ICompareDataEnumItemDao itemDao = (ICompareDataEnumItemDao)((Object)this.compareDataDao);
            itemDao.deleteByEnumId(compareDataDTO.getInfoKey(), compareDataDTO.getEnumCompareKey());
        } else if (StringUtils.isNotEmpty((String)compareDataDTO.getInfoKey())) {
            this.compareDataDao.deleteByInfoKey(compareDataDTO.getInfoKey());
        }
    }

    @Override
    public void batchAdd(List<CompareDataEnumItemDTO> compareDataDTOs) throws SingleCompareException {
        if (compareDataDTOs.size() <= 1000) {
            ArrayList<CompareDataEnumItemDTO> list2 = new ArrayList<CompareDataEnumItemDTO>();
            list2.addAll(compareDataDTOs);
            this.compareDataDao.batchInsert(list2);
        } else {
            ArrayList<CompareDataEnumItemDTO> list2 = new ArrayList<CompareDataEnumItemDTO>();
            for (int i = 0; i < compareDataDTOs.size(); ++i) {
                if (list2.size() >= 600) {
                    this.compareDataDao.batchInsert(list2);
                    list2.clear();
                }
                list2.add(compareDataDTOs.get(i));
            }
            if (list2.size() > 0) {
                this.compareDataDao.batchInsert(list2);
            }
        }
    }

    @Override
    public void batchUpdate(List<CompareDataEnumItemDTO> compareDataDTOs) throws SingleCompareException {
        if (compareDataDTOs.size() <= 1000) {
            ArrayList<CompareDataEnumItemDTO> list2 = new ArrayList<CompareDataEnumItemDTO>();
            list2.addAll(compareDataDTOs);
            this.compareDataDao.batchUpdate(list2);
        } else {
            ArrayList<CompareDataEnumItemDTO> list2 = new ArrayList<CompareDataEnumItemDTO>();
            for (int i = 0; i < compareDataDTOs.size(); ++i) {
                if (list2.size() >= 600) {
                    this.compareDataDao.batchUpdate(list2);
                    list2.clear();
                }
                list2.add(compareDataDTOs.get(i));
            }
            if (list2.size() > 0) {
                this.compareDataDao.batchUpdate(list2);
            }
        }
    }

    @Override
    public void batchDelete(List<CompareDataEnumItemDTO> compareDataDTOs) throws SingleCompareException {
        ArrayList<String> list2 = new ArrayList<String>();
        for (CompareDataEnumItemDTO dto : compareDataDTOs) {
            list2.add(dto.getKey());
        }
        this.compareDataDao.batchDelete(list2);
    }
}

