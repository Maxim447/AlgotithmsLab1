import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xddf.usermodel.chart.*;
import org.apache.poi.xssf.usermodel.*;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Practice1 {

    static List<Double> forLadder = new ArrayList<>();
    static List<Double> forBinary = new ArrayList<>();
    static List<Double> forLadderPlusExp = new ArrayList<>();
    static List<String> forMatrix = new ArrayList<>();

    static List<Double> forLadderSecondWay = new ArrayList<>();
    static List<Double> forBinarySecondWay = new ArrayList<>();
    static List<Double> forLadderPlusExpSecondWay = new ArrayList<>();

    public static void main(String[] args) {
        tests();
        createXlsx();
    }

    public static long[][] firstArray(int m, int n) {
        long[][] array = new long[m][n];
        for (int i = 0; i < m; ++i) {
            for (int j = 0; j < n; ++j) {
                array[i][j] = ((long) n / m * i + j) * 2;
            }
        }
        return array;
    }

    public static long[][] secondArray(int m, int n) {
        long[][] array = new long[m][n];
        for (int i = 0; i < m; ++i) {
            for (int j = 0; j < n; ++j) {
                array[i][j] = ((long) n / m * (i + 1) * (j + 1)) * 2;
            }
        }
        return array;
    }


    public static void ladderSearch(long[][] array, long target) {
        int n1 = 0, m1 = array[0].length - 1;
        while (n1 < array.length - 1 && m1 > 0) {
            if (array[n1][m1] == target) {
                return;
            } else if (array[n1][m1] < target) {
                n1++;
            } else if (array[n1][m1] > target) {
                m1--;
            }
        }
    }

    public static void binarySearch(long[][] array, long target) {
        for (long[] newArray : array) {
            if (newArray[binary(newArray, target)] == target) {
                System.out.println("Target is " + target);
                return;
            }
        }
    }
    public static int binary(long[] array, long target) {
        int low = 0, high = array.length - 1;
        while (low <= high) {
            int mid = low + (high - low) / 2;
            if (array[mid] == target) {
                return mid;
            } else if (target > array[mid]) {
                low = mid + 1;
            } else {
                high = mid -1;
            }
        }
        return low > array.length -1 ? low - 1 : low;
    }

    public static void ladderPlusExpSearch(long[][] array, long target) {
        int n1 = 0, m1 = array[0].length - 1;
        while (n1 < array.length - 1 && m1 > 0) {
            if (array[n1][m1] == target) {
                System.out.println("Target is " + n1 + " " + m1);
                return;
            } else if (array[n1][m1] < target) {
                int start = n1;
                int n2 = n1;
                int i = 1;
                while (array[n2][m1] <= target && n2 < array.length -1) {
                    i = i * 2;
                    n2 = n2 + i;
                    if (n2 >= array.length){
                        n2 = array.length - 1;
                    }
                }
                long[] arr = new long[n2 - start + 1];
                int position = 0;
                for (int j = start; j <= n2; j++) {
                    arr[position++] = array[j][m1];
                }
                int c = binary(arr, target);
                n1 += c;
                if (n1 == array.length){
                    n1 -= 1;
                }
                if (array[n1][m1] == target) {
                    System.out.println("Target is " + n1 + " " + m1);
                    return;
                }
            } else if (array[n1][m1] > target) {
                m1--;
            }
        }
    }


    public static void tests(){
        int t = 1;
        while (t < 14) {
            long[][] firstArray = firstArray((int) Math.pow(2,t), (int) Math.pow(2, 13));
            long[][] secondArray = secondArray((int) Math.pow(2,t), (int) Math.pow(2, 13));

            long targetFirstWay = (long) ((2 * Math.pow(2,13))+1);
            long targetSecondWay = (long) ((16 * Math.pow(2,13))+1);

            List<Long> nanosArrayForStairs = new ArrayList<>();
            List<Long> nanosArrayForBinary = new ArrayList<>();
            List<Long> nanosArrayForLadderPlusExp = new ArrayList<>();

            List<Long> nanosArrayForStairsSecondWay = new ArrayList<>();
            List<Long> nanosArrayForBinarySecondWay = new ArrayList<>();
            List<Long> nanosArrayForLadderPlusExpSecondWay = new ArrayList<>();

            int c = 0;
            while (c++ < 1000) {

                long startLadderTime = System.nanoTime();
                ladderSearch(firstArray, targetFirstWay);
                long nanosForLadder = (System.nanoTime() - startLadderTime);

                long startBinaryTime = System.nanoTime();
                binarySearch(firstArray, targetFirstWay);
                long nanosForBinary = (System.nanoTime() - startBinaryTime);

                long startLadderPlusExpTime = System.nanoTime();
                ladderPlusExpSearch(firstArray, targetFirstWay);
                long nanosForLadderPlusExp = (System.nanoTime() - startLadderPlusExpTime);


                long startLadderTimeSecondWay = System.nanoTime();
                ladderSearch(secondArray, targetSecondWay);
                long nanosForLadderSecondWay = (System.nanoTime() - startLadderTimeSecondWay);

                long startBinaryTimeSecondWay = System.nanoTime();
                binarySearch(secondArray, targetSecondWay);
                long nanosForBinarySecondWay = (System.nanoTime() - startBinaryTimeSecondWay);

                long startLadderPlusExpTimeSecondWay = System.nanoTime();
                ladderPlusExpSearch(secondArray, targetSecondWay);
                long nanosForLadderPlusExpSecondWay = (System.nanoTime() - startLadderPlusExpTimeSecondWay);


                if (c != 1) {
                    nanosArrayForLadderPlusExp.add(nanosForLadderPlusExp);
                    nanosArrayForStairs.add(nanosForLadder);
                    nanosArrayForBinary.add(nanosForBinary);

                    nanosArrayForLadderPlusExpSecondWay.add(nanosForLadderPlusExpSecondWay);
                    nanosArrayForStairsSecondWay.add(nanosForLadderSecondWay);
                    nanosArrayForBinarySecondWay.add(nanosForBinarySecondWay);
                }
            }
            arraysForXlsx(t, nanosArrayForStairs, nanosArrayForBinary, nanosArrayForLadderPlusExp, forLadder, forBinary, forLadderPlusExp);
            arraysForXlsx(t, nanosArrayForStairsSecondWay, nanosArrayForBinarySecondWay, nanosArrayForLadderPlusExpSecondWay, forLadderSecondWay, forBinarySecondWay, forLadderPlusExpSecondWay);

            forMatrix.add("2**" + t + "x" + "N");
            t++;
        }
    }

    private static void arraysForXlsx(int t, List<Long> nanosArrayForStairs, List<Long> nanosArrayForBinary, List<Long> nanosArrayForLadderPlusExp, List<Double> forLadder, List<Double> forBinary, List<Double> forLadderPlusExp) {
        double sum = nanosArrayForStairs.stream().mapToDouble(a -> a).sum();
        forLadder.add(sum / nanosArrayForStairs.size() / 1000000000);
        System.out.println("Average time for StairsSearch in " + ((int)Math.pow(2,t)) + "xN matrix: " + sum / nanosArrayForStairs.size() + " ns");

        double sum1 = nanosArrayForBinary.stream().mapToDouble(a -> a).sum();
        forBinary.add(sum1 / nanosArrayForBinary.size() / 1000000000);
        System.out.println("Average time for BinarySearch in " + ((int)Math.pow(2,t)) + "xN matrix " + sum1 / nanosArrayForBinary.size() + " ns");

        double sum2 = nanosArrayForLadderPlusExp.stream().mapToDouble(a -> a).sum();
        forLadderPlusExp.add(sum2 / nanosArrayForLadderPlusExp.size() / 1000000000);
        System.out.println("Average time for LadderPlusExp in " + ((int)Math.pow(2,t)) + "xN matrix " + sum2 / nanosArrayForLadderPlusExp.size() + " ns");
    }

    public static void createXlsx() {
        try (XSSFWorkbook wb = new XSSFWorkbook()) {
            String sheetName = "FirstMatrix";
            XSSFSheet sheet = wb.createSheet(sheetName);

            Row row1 = sheet.createRow((short) 0);

            Cell cell1 = row1.createCell((short) 0);
            cell1.setCellValue("MatrixSize");

            cell1 = row1.createCell((short) 1);
            cell1.setCellValue("Binary");

            cell1 = row1.createCell((short) 2);
            cell1.setCellValue("Ladder");

            cell1 = row1.createCell((short) 3);
            cell1.setCellValue("LadderPlusExp");

            String sheetName2 = "SecondMatrix";
            XSSFSheet sheet2 = wb.createSheet(sheetName2);

            Row row2 = sheet2.createRow((short) 0);

            Cell cell2 = row2.createCell((short) 0);
            cell2.setCellValue("MatrixSize");

            cell2 = row2.createCell((short) 1);
            cell2.setCellValue("Binary");

            cell2 = row2.createCell((short) 2);
            cell2.setCellValue("Ladder");

            cell2 = row2.createCell((short) 3);
            cell2.setCellValue("LadderPlusExp");
            int count = 1;
            while (count < 14) {
                createRows(sheet, count, forBinary, forLadder, forLadderPlusExp);
                createRows(sheet2, count, forBinarySecondWay, forLadderSecondWay, forLadderPlusExpSecondWay);
                count++;
            }
            graph(sheet,"The first type of generation");
            graph(sheet2,"The second type of generation");
            String filename = "statistics.xlsx";
            try (FileOutputStream fileOut = new FileOutputStream(filename)) {
                wb.write(fileOut);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void createRows(XSSFSheet sheet, int count, List<Double> forBinary, List<Double> forLadder, List<Double> forLadderPlusExp) {
        Row row1;
        Cell cell1;
        row1 = sheet.createRow((short) count);

        cell1 = row1.createCell((short) 0);
        cell1.setCellValue(forMatrix.get(count - 1));

        cell1 = row1.createCell((short) 1);
        cell1.setCellValue(forBinary.get(count - 1));

        cell1 = row1.createCell((short) 2);
        cell1.setCellValue(forLadder.get(count - 1));

        cell1 = row1.createCell((short) 3);
        cell1.setCellValue(forLadderPlusExp.get(count - 1));
    }

    public static void graph(XSSFSheet sheet, String name){
        XSSFDrawing drawing = sheet.createDrawingPatriarch();
        XSSFClientAnchor anchor = drawing.createAnchor(0, 0, 0, 0, 5, 2, 15, 23);
        XSSFChart chart = drawing.createChart(anchor);
        chart.setTitleText(name);
        chart.setTitleOverlay(false);
        XDDFChartLegend legend = chart.getOrAddLegend();
        legend.setPosition(LegendPosition.TOP_RIGHT);
        XDDFCategoryAxis bottomAxis = chart.createCategoryAxis(AxisPosition.BOTTOM);
        bottomAxis.setTitle("Size");
        XDDFValueAxis leftAxis = chart.createValueAxis(AxisPosition.LEFT);
        leftAxis.setTitle("Time in sec");
        XDDFDataSource<String> columns = XDDFDataSourcesFactory.fromStringCellRange(sheet, new CellRangeAddress(1, 13, 0, 0));
        XDDFNumericalDataSource<Double> naive = XDDFDataSourcesFactory.fromNumericCellRange(sheet, new CellRangeAddress(1, 13, 1, 1));
        XDDFNumericalDataSource<Double> ladder = XDDFDataSourcesFactory.fromNumericCellRange(sheet, new CellRangeAddress(1, 13, 2, 2));
        XDDFNumericalDataSource<Double> ladderPlusExp = XDDFDataSourcesFactory.fromNumericCellRange(sheet, new CellRangeAddress(1, 13, 3, 3));
        XDDFLineChartData data = (XDDFLineChartData) chart.createData(ChartTypes.LINE, bottomAxis, leftAxis);
        XDDFLineChartData.Series series1 = (XDDFLineChartData.Series) data.addSeries(columns, naive);
        series1.setTitle("Binary", null);
        series1.setSmooth(false);
        series1.setMarkerStyle(MarkerStyle.STAR);
        XDDFLineChartData.Series series2 = (XDDFLineChartData.Series) data.addSeries(columns, ladder);
        series2.setTitle("Ladder", null);
        series2.setSmooth(true);
        series2.setMarkerStyle(MarkerStyle.SQUARE);
        XDDFLineChartData.Series series3 = (XDDFLineChartData.Series) data.addSeries(columns, ladderPlusExp);
        series3.setTitle("LadderPlusExp", null);
        series3.setSmooth(true);
        series3.setMarkerStyle(MarkerStyle.DIAMOND);
        chart.plot(data);
    }
}
