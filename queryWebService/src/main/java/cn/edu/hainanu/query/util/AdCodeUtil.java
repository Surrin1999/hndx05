package cn.edu.hainanu.query.util;

import cn.hutool.core.util.StrUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;

import java.util.List;
import java.util.Map;

/**
 * The util to obtain the adcode by city
 *
 * @author Junyan Liang
 * Date: 2021.11.6
 */
public class AdCodeUtil {

    private static final List<Map<String, Object>> allRows;

    static {
        ExcelReader reader = ExcelUtil.getReader(AdCodeUtil.class.getClassLoader().getResourceAsStream("adcode.xlsx"));
        allRows = reader.readAll();
    }

    public static String getAdCode(String city) {
        if (StrUtil.isBlank(city)) {
            return null;
        }
        for (Map<String, Object> row : allRows) {
            String name = ((String) row.get("中文名"));
            if (name.contains(city)) {
                return ((String) row.get("adcode"));
            }
        }
        return null;
    }
}