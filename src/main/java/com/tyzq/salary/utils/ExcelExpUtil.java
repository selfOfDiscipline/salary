package com.tyzq.salary.utils;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.TemplateExportParams;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.DataValidationConstraint;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.xssf.usermodel.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * excel导出
 */
public class ExcelExpUtil {

	/**
	 * fileName 文件名称 url excel模板相对路径 map response
	 */
	public static void templateExp(String fileName, String url, Map<String, Object> map, HttpServletResponse response) {
		TemplateExportParams params = new TemplateExportParams(url);// 例 :
																	// "templates/Export.xlsx"
		Workbook workbook = ExcelExportUtil.exportExcel(params, map);
		excelDownload(workbook, response, fileName);
	}
	
	/**
	 * params 模板导出参数设置    fileName 文件名称  map response
	 */
	public static void templateExp(String fileName, TemplateExportParams params, Map<String, Object> map, HttpServletResponse response) {
		Workbook workbook = ExcelExportUtil.exportExcel(params, map);
		excelDownload(workbook, response, fileName);
	}

	/**
	 * flieName 文件名称 pojoClass 实体Class(添加@Excel注解标明要导出的列) styleClass 样式类 dataSet
	 * 数据集合 response
	 */
	public static void excelExp(String flieName, Class<?> pojoClass, Class<?> styleClass, Collection<?> dataSet,
			HttpServletResponse response) {
		ExportParams exportParams = new ExportParams(flieName, flieName);
		if (null != styleClass) {
			exportParams.setStyle(styleClass);
		}
		Workbook workbook = ExcelExportUtil.exportExcel(exportParams, pojoClass, dataSet);
		excelDownload(workbook, response, flieName);
	}
	
	/**
	 * exportParams Excel 导出参数
	 * flieName 文件名称 pojoClass 实体Class(添加@Excel注解标明要导出的列) styleClass 样式类 dataSet
	 * 数据集合 response
	 */
	public static void excelExp(ExportParams exportParams, String flieName, Class<?> pojoClass, Collection<?> dataSet,
                                HttpServletResponse response) {
		Workbook workbook = ExcelExportUtil.exportExcel(exportParams, pojoClass, dataSet);
		excelDownload(workbook, response, flieName);
	}

	public static void excelDownloadRequest(Workbook wirthExcelWB, HttpServletRequest request, HttpServletResponse response, String fileName) {
		try {
			// 重置响应对象
//			response.reset();
			// 当前日期，用于导出文件名称
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			String dateStr = fileName + "-" + sdf.format(new Date());
			String header = request.getHeader("Origin");
			// 设置输出的格式
			response.reset();
			response.setContentType("application/vnd.ms-excel;charset=UTF-8");
			response.addHeader("Access-Control-Allow-Origin", header);
			response.addHeader("Access-Control-Allow-Credentials", "true");
			response.addHeader("Access-Control-Allow-Headers", "*");
			response.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
			response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(dateStr + ".xlsx", "UTF-8"));
			// 写出数据输出流到页面
			OutputStream output = response.getOutputStream();
			BufferedOutputStream bufferedOutPut = new BufferedOutputStream(output);
			wirthExcelWB.write(bufferedOutPut);
			bufferedOutPut.flush();
			bufferedOutPut.close();
			output.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void excelDownload(Workbook wirthExcelWB, HttpServletResponse response, String fileName) {
		try {
			// 重置响应对象
//			response.reset();
			// 当前日期，用于导出文件名称
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			String dateStr = fileName + "-" + sdf.format(new Date());
			// 设置输出的格式
			response.reset();
			response.setContentType("application/vnd.ms-excel;charset=UTF-8");
			response.addHeader("Access-Control-Allow-Origin", "*");
			response.addHeader("Access-Control-Allow-Credentials", "true");
			response.addHeader("Access-Control-Allow-Headers", "*");
			response.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
			response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(dateStr + ".xlsx", "UTF-8"));
			// 写出数据输出流到页面
			OutputStream output = response.getOutputStream();
			BufferedOutputStream bufferedOutPut = new BufferedOutputStream(output);
			wirthExcelWB.write(bufferedOutPut);
			bufferedOutPut.flush();
			bufferedOutPut.close();
			output.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 单元格添加下拉菜单(不限制菜单可选项个数)<br/>
	 * [注意：此方法会添加隐藏的sheet，可调用getDataSheetInDropMenuBook方法获取用户输入数据的未隐藏的sheet]<br/>
	 * [待添加下拉菜单的单元格 -> 以下简称：目标单元格]
	 * @param @param workbook
	 * @param @param tarSheet 目标单元格所在的sheet
	 * @param @param menuItems 下拉菜单可选项数组
	 * @param @param firstRow 第一个目标单元格所在的行号(从0开始)
	 * @param @param lastRow 最后一个目标单元格所在的行(从0开始)
	 * @param @param column 待添加下拉菜单的单元格所在的列(从0开始)
	 */
	public static void addDropDownList(XSSFWorkbook workbook, XSSFSheet tarSheet, String[] menuItems, int firstRow, int lastRow, int column) throws Exception
	{
		if(null == workbook){
			throw new Exception("workbook为null");
		}
		if(null == tarSheet){
			throw new Exception("待添加菜单的sheet为null");
		}

		//必须以字母开头，最长为31位
		String hiddenSheetName = "a" + UUID.randomUUID().toString().replace("-", "").substring(1, 31);
		//excel中的"名称"，用于标记隐藏sheet中的用作菜单下拉项的所有单元格
		String formulaId = "form" + UUID.randomUUID().toString().replace("-", "");
		XSSFSheet hiddenSheet = workbook.createSheet(hiddenSheetName);//用于存储 下拉菜单数据
		//存储下拉菜单项的sheet页不显示
		workbook.setSheetHidden(workbook.getSheetIndex(hiddenSheet), true);

		XSSFRow row = null;
		XSSFCell cell = null;
		//隐藏sheet中添加菜单数据
		for (int i = 0; i < menuItems.length; i++)
		{
			row = hiddenSheet.createRow(i);
			//隐藏表的数据列必须和添加下拉菜单的列序号相同，否则不能显示下拉菜单
			cell = row.createCell(column);
			cell.setCellValue(menuItems[i]);
		}

		XSSFName namedCell = workbook.createName();//创建"名称"标签，用于链接
		namedCell.setNameName(formulaId);
		namedCell.setRefersToFormula(hiddenSheetName + "!A$1:A$" + menuItems.length);
		XSSFDataValidationHelper dvHelper = new XSSFDataValidationHelper(tarSheet);
		DataValidationConstraint dvConstraint = dvHelper.createFormulaListConstraint(formulaId);

		CellRangeAddressList addressList = new CellRangeAddressList(firstRow, lastRow, column, column);
		XSSFDataValidation validation = (XSSFDataValidation)dvHelper.createValidation(dvConstraint, addressList);//添加菜单(将单元格与"名称"建立关联)
		tarSheet.addValidationData(validation);
	}


	/**
	 * 从调用addDropDownList后添加下拉菜单的Workbook中获取用户输入数据的shee列表
	 * @param book
	 * @return
	 */
	public static List<HSSFSheet> getDataSheetInDropMenuBook(HSSFWorkbook book){
		return getUnHideSheets(book);
	}

	/**
	 * 获取所有未隐藏的sheet
	 * @param book
	 * @return
	 */
	public static List<HSSFSheet> getUnHideSheets(HSSFWorkbook book) {
		List<HSSFSheet> ret = new ArrayList<HSSFSheet>();
		if (null == book) {
			return ret;
		}

		int sheetCnt = book.getNumberOfSheets();
		for (int i = 0; i < sheetCnt; i++) {
			if (!book.isSheetHidden(i)) {
				ret.add(book.getSheetAt(i));
			}
		}

		return ret;
	}

}
