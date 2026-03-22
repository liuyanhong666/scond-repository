package com.dkp.model;

import java.util.Arrays;

/**
 * 动态规划求解结果类
 * 封装最优解价值和选择向量
 * 
 * @author DKP Solver Team
 * @version 1.0
 */
public class DPSolution {
    
    /** 最优解价值 */
    private int optimalValue;
    
    /** 选择向量，每个元素表示对应项集选择的物品索引（1-3，0表示不选） */
    private int[] selectedItems;
    
    /** 求解耗时（毫秒） */
    private long solveTime;
    
    /**
     * 构造函数
     * @param optimalValue 最优解价值
     * @param selectedItems 选择向量
     */
    public DPSolution(int optimalValue, int[] selectedItems) {
        this.optimalValue = optimalValue;
        this.selectedItems = selectedItems;
        this.solveTime = 0;
    }
    
    /**
     * 构造函数（带耗时）
     * @param optimalValue 最优解价值
     * @param selectedItems 选择向量
     * @param solveTime 求解耗时
     */
    public DPSolution(int optimalValue, int[] selectedItems, long solveTime) {
        this.optimalValue = optimalValue;
        this.selectedItems = selectedItems;
        this.solveTime = solveTime;
    }
    
    public int getOptimalValue() {
        return optimalValue;
    }
    
    public void setOptimalValue(int optimalValue) {
        this.optimalValue = optimalValue;
    }
    
    public int[] getSelectedItems() {
        return selectedItems;
    }
    
    public void setSelectedItems(int[] selectedItems) {
        this.selectedItems = selectedItems;
    }
    
    public long getSolveTime() {
        return solveTime;
    }
    
    public void setSolveTime(long solveTime) {
        this.solveTime = solveTime;
    }
    
    /**
     * 获取格式化的解向量字符串
     * @return 解向量字符串
     */
    public String getFormattedSolution() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < selectedItems.length; i++) {
            if (selectedItems[i] > 0) {
                sb.append(String.format("项集%d -> 选择物品%d\n", i + 1, selectedItems[i]));
            } else {
                sb.append(String.format("项集%d -> 不选择任何物品\n", i + 1));
            }
        }
        return sb.toString();
    }
    
    @Override
    public String toString() {
        return String.format("DPSolution{optimalValue=%d, selectedItems=%s, solveTime=%dms}",
                optimalValue, Arrays.toString(selectedItems), solveTime);
    }
}