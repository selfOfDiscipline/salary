package com.tyzq.salary.utils;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.result.ExcelImportResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * @ClassName: ExcelEasyPoiUtil
 * @ProjectName: housemanage
 * @Author: 郑稳超 zwc_503@163.com
 * @Date： 2019/7/18 11:30
 * @Description: //TODO 封装easy poi 的导入工具包
 * @Version: 1.0
 */
public class ExcelEasyPoiUtil {

    private static final Logger logger = LoggerFactory.getLogger(ExcelEasyPoiUtil.class);

    /*
     * @Author: 郑稳超 zwc_503@163.com
     * @Date: 2019/7/18 11:55
     * @Param: rowNum表头行数、sheetNum读几个sheet页、multipartFile文件、目标类
     * @return:
     * @Version:
     * @Description: //TODO 封装excel导入,文件针对MultipartFile
     **/
    public static <T> List<T> importExcelMultipartFile(int rowNum, int sheetNum, MultipartFile file, Class<?> pojoClass) throws Exception {
        ImportParams params = new ImportParams();
        params.setHeadRows(rowNum);
        params.setSheetNum(sheetNum);
        ExcelImportResult<T> objectExcelImportResult = ExcelImportUtil.importExcelMore(file.getInputStream(), pojoClass, params);
        return objectExcelImportResult.getList();
    }

    /*
     * @Author: 郑稳超 zwc_503@163.com
     * @Date: 2019/7/18 11:57
     * @Param: rowNum表头行数、sheetNum读几个sheet页、file文件、目标类
     * @return:
     * @Version:
     * @Description: //TODO 封装excel导入,文件针对File
     **/
    public static <T> List<T> importExcelFile(int rowNum, int sheetNum, File file, Class<?> pojoClass) throws Exception {
        ImportParams params = new ImportParams();
        params.setHeadRows(rowNum);
        params.setSheetNum(sheetNum);
        ExcelImportResult<T> objectExcelImportResult = ExcelImportUtil.importExcelMore(file, pojoClass, params);
        return objectExcelImportResult.getList();
    }

    /*
     * @Author: 郑稳超 zwc_503@163.com
     * @Date: 2019/7/18 14:21
     * @Param:
     * @return:
     * @Version:
     * @Description: //TODO 针对于excel文件的日期转换成Date
     **/
    public static Date convertDateByExcelTime(String excelTime) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);
        return format.parse(excelTime);
    }
}
