package com.dkp.exporter;

import com.dkp.model.DPSolution;
import com.dkp.model.Item;
import com.dkp.model.ItemSet;

import java.io.*;
import java.util.List;

/**
 * 结果导出类
 * 支持导出为TXT和CSV格式
 * 
 * @author DKP Solver Team
 * @version 1.0
 */
public class ResultExporter {
    
    /**
     * 导出结果为TXT文件
     * @param solution 求解结果
     * @param itemSets 项集列表
     * @param capacity 背包容量
     * @param filePath 输出文件路径
     * @throws IOException 文件写入异常
     */
    public void exportToTxt(DPSolution solution, List<ItemSet> itemSets, 
                             int capacity, String filePath) throws IOException {
        
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write("========================================\n");
            writer.write("    D{0-1}背包问题求解结果报告\n");
            writer.write("========================================\n\n");
            
            writer.write("【问题参数】\n");
            writer.write("项集数量：" + itemSets.size() + "\n");
            writer.write("背包容量：" + capacity + "\n\n");
            
            writer.write("【物品详细信息】\n");
            writer.write("序号\t物品1(重量,价值)\t物品2(重量,价值)\t物品3(重量,价值)\t第三项比值\n");
            for (int i = 0; i < itemSets.size(); i++) {
                ItemSet set = itemSets.get(i);
                writer.write(String.format("%d\t(%d,%d)\t\t(%d,%d)\t\t(%d,%d)\t\t%.4f\n",
                    i + 1,
                    set.getItem1().getWeight(), set.getItem1().getValue(),
                    set.getItem2().getWeight(), set.getItem2().getValue(),
                    set.getItem3().getWeight(), set.getItem3().getValue(),
                    set.getThirdItemRatio()));
            }
            
            writer.write("\n【求解结果】\n");
            writer.write("最优解价值：" + solution.getOptimalValue() + "\n");
            writer.write("求解耗时：" + solution.getSolveTime() + " ms\n\n");
            
            writer.write("【选择方案】\n");
            writer.write(solution.getFormattedSolution());
            
            writer.write("\n【解向量】\n");
            int[] selected = solution.getSelectedItems();
            for (int i = 0; i < selected.length; i++) {
                writer.write(selected[i] + " ");
            }
            writer.write("\n");
            
            writer.write("\n========================================\n");
            writer.write("报告生成时间：" + new java.util.Date() + "\n");
            writer.write("========================================\n");
        }
    }
    
    /**
     * 导出结果为CSV文件
     * @param solution 求解结果
     * @param itemSets 项集列表
     * @param capacity 背包容量
     * @param filePath 输出文件路径
     * @throws IOException 文件写入异常
     */
    public void exportToCsv(DPSolution solution, List<ItemSet> itemSets,
                             int capacity, String filePath) throws IOException {
        
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            // 写入表头
            writer.write("参数,值\n");
            writer.write("项集数量," + itemSets.size() + "\n");
            writer.write("背包容量," + capacity + "\n");
            writer.write("最优解价值," + solution.getOptimalValue() + "\n");
            writer.write("求解耗时(ms)," + solution.getSolveTime() + "\n");
            writer.write("\n");
            
            writer.write("项集索引,物品1重量,物品1价值,物品2重量,物品2价值,物品3重量,物品3价值,第三项比值,选择物品\n");
            
            int[] selected = solution.getSelectedItems();
            for (int i = 0; i < itemSets.size(); i++) {
                ItemSet set = itemSets.get(i);
                writer.write(String.format("%d,%d,%d,%d,%d,%d,%d,%.4f,%d\n",
                    i + 1,
                    set.getItem1().getWeight(), set.getItem1().getValue(),
                    set.getItem2().getWeight(), set.getItem2().getValue(),
                    set.getItem3().getWeight(), set.getItem3().getValue(),
                    set.getThirdItemRatio(),
                    selected[i]));
            }
        }
    }
}