package com.dkp.solver;

import com.dkp.model.Item;
import com.dkp.model.ItemSet;
import com.dkp.model.DPSolution;

import java.util.List;

/**
 * D{0-1}背包问题动态规划求解器
 * 
 * @author DKP Solver Team
 * @version 1.0
 */
public class DynamicSolver {
    
    /**
     * 求解D{0-1}背包问题
     * @param itemSets 项集列表
     * @param capacity 背包容量
     * @return 求解结果
     */
    public DPSolution solve(List<ItemSet> itemSets, int capacity) {
        long startTime = System.nanoTime();
        
        int n = itemSets.size();
        
        // dp[i][j]：考虑前i个项集，容量为j时的最大价值
        int[][] dp = new int[n + 1][capacity + 1];
        
        // choice[i][j][0]：是否选择了物品（0/1）
        // choice[i][j][1]：如果选择了，选择的是哪个物品（0,1,2）
        int[][][] choice = new int[n + 1][capacity + 1][2];
        
        // 初始化dp[0][j] = 0（Java默认即为0）
        
        // 动态规划填表
        for (int i = 1; i <= n; i++) {
            ItemSet set = itemSets.get(i - 1);
            Item[] items = set.getItems();
            
            // 获取三个物品的重量和价值
            int[] weights = {
                items[0].getWeight(),
                items[1].getWeight(),
                items[2].getWeight()
            };
            int[] values = {
                items[0].getValue(),
                items[1].getValue(),
                items[2].getValue()
            };
            
            for (int j = 0; j <= capacity; j++) {
                // 情况1：不选任何物品（默认选择）
                dp[i][j] = dp[i - 1][j];
                choice[i][j][0] = 0;
                choice[i][j][1] = -1;
                
                // 情况2：尝试选择物品1
                if (j >= weights[0]) {
                    int newValue = dp[i - 1][j - weights[0]] + values[0];
                    if (newValue > dp[i][j]) {
                        dp[i][j] = newValue;
                        choice[i][j][0] = 1;
                        choice[i][j][1] = 0;
                    }
                }
                
                // 情况3：尝试选择物品2
                if (j >= weights[1]) {
                    int newValue = dp[i - 1][j - weights[1]] + values[1];
                    if (newValue > dp[i][j]) {
                        dp[i][j] = newValue;
                        choice[i][j][0] = 1;
                        choice[i][j][1] = 1;
                    }
                }
                
                // 情况4：尝试选择物品3
                if (j >= weights[2]) {
                    int newValue = dp[i - 1][j - weights[2]] + values[2];
                    if (newValue > dp[i][j]) {
                        dp[i][j] = newValue;
                        choice[i][j][0] = 1;
                        choice[i][j][1] = 2;
                    }
                }
            }
        }
        
        // 回溯求解向量
        int[] selectedItems = new int[n];
        int remain = capacity;
        
        for (int i = n; i >= 1; i--) {
            if (choice[i][remain][0] == 1) {
                int itemIndex = choice[i][remain][1];
                selectedItems[i - 1] = itemIndex + 1;
                ItemSet set = itemSets.get(i - 1);
                remain -= set.getItems()[itemIndex].getWeight();
            } else {
                selectedItems[i - 1] = 0;
            }
        }
        
        long endTime = System.nanoTime();
        long solveTime = (endTime - startTime) / 1_000_000; // 转换为毫秒
        
        return new DPSolution(dp[n][capacity], selectedItems, solveTime);
    }
    
    /**
     * 获取dp表（用于可视化）
     * @param itemSets 项集列表
     * @param capacity 背包容量
     * @return dp二维数组
     */
    public int[][] getDpTable(List<ItemSet> itemSets, int capacity) {
        int n = itemSets.size();
        int[][] dp = new int[n + 1][capacity + 1];
        
        for (int i = 1; i <= n; i++) {
            ItemSet set = itemSets.get(i - 1);
            Item[] items = set.getItems();
            
            int[] weights = {
                items[0].getWeight(),
                items[1].getWeight(),
                items[2].getWeight()
            };
            int[] values = {
                items[0].getValue(),
                items[1].getValue(),
                items[2].getValue()
            };
            
            for (int j = 0; j <= capacity; j++) {
                dp[i][j] = dp[i - 1][j];
                
                for (int k = 0; k < 3; k++) {
                    if (j >= weights[k]) {
                        int newValue = dp[i - 1][j - weights[k]] + values[k];
                        if (newValue > dp[i][j]) {
                            dp[i][j] = newValue;
                        }
                    }
                }
            }
        }
        
        return dp;
    }
}