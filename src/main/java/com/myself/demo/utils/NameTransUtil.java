package com.myself.demo.utils;

import cn.hutool.core.util.StrUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.sql.Clob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 名称转换
 */
public class NameTransUtil {

    /**
     * 将驼峰式命名的字符串转换为下划线大写方式。如果转换前的驼峰式命名的字符串为空，则返回空字符串。
     * 例如：HelloWorld->HELLO_WORLD
     * @param name 转换前的驼峰式命名的字符串
     * @return 转换后下划线大写方式命名的字符串
     */
    public static String underscoreName(String name) {
        StringBuilder result = new StringBuilder();
        if (name != null && name.length() > 0) {
            // 将第一个字符处理成大写
            result.append(name.substring(0, 1).toUpperCase());
            // 循环处理其余字符
            for (int i = 1; i < name.length(); i++) {
                String s = name.substring(i, i + 1);
                // 在大写字母前添加下划线
                if (s.equals(s.toUpperCase()) && !Character.isDigit(s.charAt(0))) {
                    result.append("_");
                }
                // 其他字符直接转成大写
                result.append(s.toUpperCase());
            }
        }
        return result.toString();
    }


    /**
     * 将下划线大写方式命名的字符串转换为驼峰式。如果转换前的下划线大写方式命名的字符串为空，则返回空字符串。
     * 例如：HELLO_WORLD->HelloWorld
     * @param name 转换前的下划线大写方式命名的字符串
     * @return 转换后的驼峰式命名的字符串
     */
    public static String camelName(String name) {
        StringBuilder result = new StringBuilder();
        // 快速检查
        if (name == null || name.isEmpty()) {
            // 没必要转换
            return "";
        } else if (!name.contains("_")) {
            // 不含下划线，仅将首字母小写 (原)
//            return name.substring(0, 1).toLowerCase() + name.substring(1);
            // 不含下划线，全部小写
            return name.toLowerCase();
        }
        // 用下划线将原始字符串分割
        String camels[] = name.split("_");
        for (String camel :  camels) {
            // 跳过原始字符串中开头、结尾的下换线或双重下划线
            if (camel.isEmpty()) {
                continue;
            }
            // 处理真正的驼峰片段
            if (result.length() == 0) {
                // 第一个驼峰片段，全部字母都小写
                result.append(camel.toLowerCase());
            } else {
                // 其他的驼峰片段，首字母大写
                result.append(camel.substring(0, 1).toUpperCase());
                result.append(camel.substring(1).toLowerCase());
            }
        }
        return result.toString();
    }

    /**
     * 下划线转驼峰命名， List<Map<String, Object>>
     * @param list
     * @return
     */
    public static List<Map<String, Object>> transListMap2camelName(List<Map<String, Object>> list) {
        List<Map<String, Object>> newList = new ArrayList<>();
        for(Map<String, Object> m : list){
            Map<String, Object> map = new HashMap<>();
            for (Map.Entry<String, Object> entry : m.entrySet()) {
                if(entry.getValue() instanceof Clob){
                    map.put(camelName(entry.getKey()), ClobToString((Clob)entry.getValue()));
                }else{
                    map.put(camelName(entry.getKey()), entry.getValue());
                }
            }
            newList.add(map);
        }
        return newList;
    }

    /**
     * clob2string， List<Map<String, Object>>
     * @param list
     * @return
     */
    public static List<Map<String, Object>> clob2string(List<Map<String, Object>> list) {
        List<Map<String, Object>> newList = new ArrayList<>();
        for(Map<String, Object> m : list){
            Map<String, Object> map = new HashMap<>();
            for (Map.Entry<String, Object> entry : m.entrySet()) {
                if(entry.getValue() instanceof Clob){
                    map.put(entry.getKey(), ClobToString((Clob)entry.getValue()));
                }else{
                    map.put(entry.getKey(), entry.getValue());
                }
            }
            newList.add(map);
        }
        return newList;
    }

    // Clob类型 转String
    public static String ClobToString(Clob clob) {
        String reString = "";
        Reader is = null;
        try {
            is = clob.getCharacterStream();
            BufferedReader br = new BufferedReader(is);
            String s = br.readLine();
            StringBuffer sb = new StringBuffer();
            while (s != null) {
                sb.append(s);
                s = br.readLine();
            }
            reString = sb.toString();
            if(br!=null){
                br.close();
            }
            if(is!=null){
                is.close();
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
        return reString;
    }

    /**
     * 驼峰命名转下划线， List<Map<String, Object>>
     * @param list
     * @return
     */
    public static List<Map> transListMap2underscoreName(List<Map> list) {
        List<Map> newList = new ArrayList<>();
        for(Map<String, Object> m : list){
            Map<String, Object> map = new HashMap<>();
            for (Map.Entry<String, Object> entry : m.entrySet()) {
                map.put(underscoreName(entry.getKey()), entry.getValue());
            }
            newList.add(map);
        }
        return newList;
    }

    /**
     * 驼峰命名转下划线， Map<String, Object>
     * @param map
     * @return
     */
    public static Map<String, Object> transMap2underscoreName(Map<String, Object> map, boolean canEmpty) {
        Map<String, Object> newMap = new HashMap<>();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            if(canEmpty){
                newMap.put(underscoreName(entry.getKey()), entry.getValue());
            }else {
                if(entry.getValue()!=null){
                    if(entry.getValue() instanceof String ){
                        if(StrUtil.isNotBlank(entry.getValue().toString())){
                            newMap.put(underscoreName(entry.getKey()), entry.getValue());
                        }
                    }else{
                        newMap.put(underscoreName(entry.getKey()), entry.getValue());
                    }
                }
            }

        }
        return newMap;
    }

}


