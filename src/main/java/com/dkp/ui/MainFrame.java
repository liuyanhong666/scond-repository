package com.dkp.ui;

import com.dkp.model.ItemSet;
import com.dkp.model.DPSolution;
import com.dkp.reader.DatasetReader;
import com.dkp.solver.DynamicSolver;
import com.dkp.sorter.DataSorter;
import com.dkp.visualizer.DataVisualizer;
import com.dkp.exporter.ResultExporter;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.util.List;

/**
 * 主界面类
 * 提供图形用户界面
 * 
 * @author DKP Solver Team
 * @version 1.0
 */
public class MainFrame extends JFrame {
    
    private JTable dataTable;
    private DefaultTableModel tableModel;
    private JTextArea resultArea;
    private JPanel chartPanel;
    private JButton loadBtn;
    private JButton solveBtn;
    private JButton sortBtn;
    private JButton plotBtn;
    private JButton exportBtn;
    
    private List<ItemSet> currentItemSets;
    private int currentCapacity;
    private DPSolution currentSolution;
    
    private DatasetReader reader;
    private DynamicSolver solver;
    private DataSorter sorter;
    private DataVisualizer visualizer;
    private ResultExporter exporter;
    
    public MainFrame() {
        initComponents();
        initListeners();
        
        reader = new DatasetReader();
        solver = new DynamicSolver();
        sorter = new DataSorter();
        visualizer = new DataVisualizer();
        exporter = new ResultExporter();
        
        setTitle("D{0-1}背包问题求解系统");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 800);
        setLocationRelativeTo(null);
    }
    
    private void initComponents() {
        setLayout(new BorderLayout());
        
        // 顶部工具栏
        JToolBar toolBar = new JToolBar();
        loadBtn = new JButton("加载数据");
        solveBtn = new JButton("动态规划求解");
        sortBtn = new JButton("按比值排序");
        plotBtn = new JButton("绘制散点图");
        exportBtn = new JButton("导出结果");
        
        toolBar.add(loadBtn);
        toolBar.add(solveBtn);
        toolBar.add(sortBtn);
        toolBar.add(plotBtn);
        toolBar.add(exportBtn);
        add(toolBar, BorderLayout.NORTH);
        
        // 中心区域：左侧表格，右侧结果
        JSplitPane centerSplit = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        
        // 左侧：数据表格
        String[] columns = {"项集", "物品1(重量,价值)", "物品2(重量,价值)", "物品3(重量,价值)", "第三项比值"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        dataTable = new JTable(tableModel);
        dataTable.setRowHeight(25);
        JScrollPane tableScroll = new JScrollPane(dataTable);
        tableScroll.setBorder(BorderFactory.createTitledBorder("数据集"));
        centerSplit.setLeftComponent(tableScroll);
        
        // 右侧：结果区域
        JPanel rightPanel = new JPanel(new BorderLayout());
        resultArea = new JTextArea();
        resultArea.setEditable(false);
        resultArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane resultScroll = new JScrollPane(resultArea);
        resultScroll.setBorder(BorderFactory.createTitledBorder("求解结果"));
        rightPanel.add(resultScroll, BorderLayout.CENTER);
        
        // 图表面板
        chartPanel = new JPanel(new BorderLayout());
        chartPanel.setBorder(BorderFactory.createTitledBorder("可视化"));
        chartPanel.setPreferredSize(new Dimension(500, 300));
        rightPanel.add(chartPanel, BorderLayout.SOUTH);
        
        centerSplit.setRightComponent(rightPanel);
        centerSplit.setDividerLocation(600);
        
        add(centerSplit, BorderLayout.CENTER);
        
        // 底部状态栏
        JPanel statusPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        statusPanel.add(new JLabel("状态：就绪"));
        add(statusPanel, BorderLayout.SOUTH);
    }
    
    private void initListeners() {
        loadBtn.addActionListener(this::loadData);
        solveBtn.addActionListener(this::solveProblem);
        sortBtn.addActionListener(this::sortData);
        plotBtn.addActionListener(this::plotData);
        exportBtn.addActionListener(this::exportResult);
    }
    
    private void loadData(ActionEvent e) {
        JFileChooser fileChooser = new JFileChooser("./data");
        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try {
                currentItemSets = reader.readFile(file.getAbsolutePath());
                currentCapacity = reader.getCapacity();
                
                updateTable(currentItemSets);
                resultArea.setText("数据加载成功！\n项集数量：" + currentItemSets.size() 
                    + "\n背包容量：" + currentCapacity);
                
                // 清空图表
                chartPanel.removeAll();
                chartPanel.revalidate();
                chartPanel.repaint();
                
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "加载失败：" + ex.getMessage(), 
                    "错误", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        }
    }
    
    private void updateTable(List<ItemSet> itemSets) {
        tableModel.setRowCount(0);
        
        for (int i = 0; i < itemSets.size(); i++) {
            ItemSet set = itemSets.get(i);
            Object[] row = {
                i + 1,
                "(" + set.getItem1().getWeight() + "," + set.getItem1().getValue() + ")",
                "(" + set.getItem2().getWeight() + "," + set.getItem2().getValue() + ")",
                "(" + set.getItem3().getWeight() + "," + set.getItem3().getValue() + ")",
                String.format("%.4f", set.getThirdItemRatio())
            };
            tableModel.addRow(row);
        }
    }
    
    private void solveProblem(ActionEvent e) {
        if (currentItemSets == null || currentItemSets.isEmpty()) {
            JOptionPane.showMessageDialog(this, "请先加载数据！", "提示", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        try {
            currentSolution = solver.solve(currentItemSets, currentCapacity);
            
            StringBuilder sb = new StringBuilder();
            sb.append("========== 求解结果 ==========\n\n");
            sb.append("背包容量：" + currentCapacity + "\n");
            sb.append("项集数量：" + currentItemSets.size() + "\n\n");
            sb.append("最优解价值：" + currentSolution.getOptimalValue() + "\n");
            sb.append("求解耗时：" + currentSolution.getSolveTime() + " ms\n\n");
            sb.append("选择方案：\n");
            sb.append(currentSolution.getFormattedSolution());
            sb.append("\n解向量：\n");
            int[] selected = currentSolution.getSelectedItems();
            for (int i = 0; i < selected.length; i++) {
                sb.append(selected[i] + " ");
            }
            
            resultArea.setText(sb.toString());
            
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "求解失败：" + ex.getMessage(), 
                "错误", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }
    
    private void sortData(ActionEvent e) {
        if (currentItemSets == null || currentItemSets.isEmpty()) {
            JOptionPane.showMessageDialog(this, "请先加载数据！", "提示", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        List<ItemSet> sortedList = sorter.sortByValueWeightRatio(currentItemSets);
        updateTable(sortedList);
        resultArea.setText("已按第三项价值重量比进行非递增排序。\n"
            + "排序后列表已更新。");
    }
    
    private void plotData(ActionEvent e) {
        if (currentItemSets == null || currentItemSets.isEmpty()) {
            JOptionPane.showMessageDialog(this, "请先加载数据！", "提示", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        chartPanel.removeAll();
        ChartPanel cp = visualizer.drawScatterPlot(currentItemSets, 
            "D{0-1}背包问题物品散点图");
        chartPanel.add(cp, BorderLayout.CENTER);
        chartPanel.revalidate();
        chartPanel.repaint();
    }
    
    private void exportResult(ActionEvent e) {
        if (currentSolution == null) {
            JOptionPane.showMessageDialog(this, "请先求解问题！", "提示", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        JFileChooser fileChooser = new JFileChooser("./results");
        if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            String path = file.getAbsolutePath();
            
            try {
                if (path.endsWith(".csv")) {
                    exporter.exportToCsv(currentSolution, currentItemSets, 
                        currentCapacity, path);
                } else {
                    if (!path.endsWith(".txt")) {
                        path += ".txt";
                    }
                    exporter.exportToTxt(currentSolution, currentItemSets, 
                        currentCapacity, path);
                }
                JOptionPane.showMessageDialog(this, "结果已导出到：" + path, 
                    "导出成功", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "导出失败：" + ex.getMessage(), 
                    "错误", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            new MainFrame().setVisible(true);
        });
    }
}