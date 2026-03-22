package com.dkp.sorter;

import com.dkp.model.ItemSet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * 数据排序类
 * 按项集第三项的价值重量比进行排序
 * 
 * @author DKP Solver Team
 * @version 1.0
 */
public class DataSorter {
    
    /**
     * 按第三项价值重量比进行非递增排序
     * @param itemSets 原始项集列表
     * @return 排序后的新列表
     */
    public List<ItemSet> sortByValueWeightRatio(List<ItemSet> itemSets) {
        List<ItemSet> sortedList = new ArrayList<>(itemSets);
        
        Collections.sort(sortedList, new Comparator<ItemSet>() {
            @Override
            public int compare(ItemSet set1, ItemSet set2) {
                double ratio1 = set1.getThirdItemRatio();
                double ratio2 = set2.getThirdItemRatio();
                
                // 非递增排序（从大到小）
                if (ratio1 < ratio2) {
                    return 1;
                } else if (ratio1 > ratio2) {
                    return -1;
                } else {
                    return 0;
                }
            }
        });
        
        return sortedList;
    }
    
    /**
     * 获取排序后的项集及其原始索引
     * @param itemSets 原始项集列表
     * @return 排序后的索引列表
     */
    public List<Integer> getSortedIndices(List<ItemSet> itemSets) {
        List<Integer> indices = new ArrayList<>();
        for (int i = 0; i < itemSets.size(); i++) {
            indices.add(i);
        }
        
        Collections.sort(indices, new Comparator<Integer>() {
            @Override
            public int compare(Integer i1, Integer i2) {
                double ratio1 = itemSets.get(i1).getThirdItemRatio();
                double ratio2 = itemSets.get(i2).getThirdItemRatio();
                
                if (ratio1 < ratio2) {
                    return 1;
                } else if (ratio1 > ratio2) {
                    return -1;
                } else {
                    return 0;
                }
            }
        });
        
        return indices;
    }
}