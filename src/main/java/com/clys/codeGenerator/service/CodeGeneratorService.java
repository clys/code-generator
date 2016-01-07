package com.clys.codeGenerator.service;

import com.bj58.supin.plugins.codegenerator.core.entity.ColumnDefinition;
import com.bj58.supin.plugins.codegenerator.core.util.ClassUtil;
import com.bj58.supin.plugins.codegenerator.core.util.FileUtil;
import com.clys.codeGenerator.entity.ConfigContext;
import com.clys.codeGenerator.helper.ColumnHelper;
import com.clys.codeGenerator.helper.DBHelper;
import com.clys.codeGenerator.utils.VelocityUtil;
import org.apache.velocity.VelocityContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>名称: 代码生成服务</p>
 * <p>创建日期：15-12-28</p>
 *
 * @author 陈李雨声
 * @version 1.0
 */
public class CodeGeneratorService {
    public void dbToCode(String sourcePath) throws Exception {

        //得到配置文件对象
        ConfigContext configContext =ConfigContext.newInstance(sourcePath);

        //初始化DB工具类
        DBHelper dbHelper = new DBHelper(configContext);

        ConfigContext.Target target = configContext.getTarget();
        if (target.getDbToCode() == null || target.getDbToCode().size() == 0) {
            throw new Exception("没有配置要生成代码的表");
        }

        String table, entity;
        List<ColumnDefinition> columns;
        List<VelocityContext> velocityContexts = new ArrayList<VelocityContext>();
        for (Map.Entry<String, String> dtcConfig : target.getDbToCode().entrySet()) {
            table = dtcConfig.getKey();
            entity = dtcConfig.getValue();
            columns = ColumnHelper.descTableColumnDefinition(dbHelper, table);
            velocityContexts.add(VelocityUtil.buildVContext(target.getPackage(), table, entity, columns));

        }
        VelocityUtil.init(configContext.getPath().getSource());
        for (VelocityContext velocityContext : velocityContexts) {
            doGenerator(configContext, velocityContext);
        }
    }

    private static void doGenerator(ConfigContext configContext, VelocityContext velocityContext) throws Exception {
        Map<String, String> mvElements = configContext.getVm().getElements();
        if (mvElements == null || mvElements.size() == 0) {
            throw new Exception("没有配置模板");
        }
        for(Map.Entry<String,String> mvElement:mvElements.entrySet()){
            FileUtil.writeFile(configContext.getPath().getOutput(),                   //输出目录
                    String.format(mvElement.getValue(), velocityContext.get("entity")),    //文件名
                    VelocityUtil.render(mvElement.getKey(), velocityContext));                 //模板生成内容

        }
    }


    private static String getSourcePath() {
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
