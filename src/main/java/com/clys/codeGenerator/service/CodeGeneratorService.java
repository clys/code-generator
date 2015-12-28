package com.clys.codeGenerator.service;

import com.bj58.supin.plugins.codegenerator.core.util.ClassUtil;
import com.clys.codeGenerator.entity.ConfigContext;

/**
 * <p>名称: </p>
 * <p>描述: 本类描述</p>
 * <p>内容摘要: // 简要描述本文件的内容，包括主要模块、函数及其功能的说明</p>
 * <p>其他说明: // 其它内容的说明</p>
 * <p>创建日期：15-12-28</p>
 *
 * @author 陈李雨声
 * @version 1.0
 */
public class CodeGeneratorService {
    public void dbToCode(String sourcePath) throws Exception {
        ConfigContext configContext = new ConfigContext(sourcePath);
    }

    private static String getSourcePath(){
        return ClassUtil.getClassPath();
    }

    public static void main(String[] args) {
        CodeGeneratorService codeGeneratorService = new CodeGeneratorService();
        try {
            codeGeneratorService.dbToCode(getSourcePath());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
