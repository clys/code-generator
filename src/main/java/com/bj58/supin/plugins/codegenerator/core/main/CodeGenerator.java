package com.bj58.supin.plugins.codegenerator.core.main;


import com.bj58.supin.plugins.codegenerator.core.ConfigContext;
import com.bj58.supin.plugins.codegenerator.core.entity.ColumnDefinition;
import com.bj58.supin.plugins.codegenerator.core.helper.ColumnHelper;
import com.bj58.supin.plugins.codegenerator.core.helper.DBHelper;
import com.bj58.supin.plugins.codegenerator.core.service.*;
import com.bj58.supin.plugins.codegenerator.core.util.ClassUtil;
import com.bj58.supin.plugins.codegenerator.core.util.FileUtil;
import com.bj58.supin.plugins.codegenerator.core.util.VelocityUtil;
import org.apache.velocity.VelocityContext;

import java.util.List;
import java.util.Map;


public class CodeGenerator{



    private String getSourcePath(){
        return ClassUtil.getClassPath();
    }


    private String getOutputPath(){
        String classPath = ClassUtil.getClassPath();
        String outBasePath = classPath.substring(0,classPath.substring(0,classPath.length()-1).lastIndexOf("/"));


        return String.format("%s/generator/",outBasePath);
    }


    public static void main(String[] args) {
        CodeGenerator generator = new CodeGenerator();
        generator.execute();
    }

    public void execute() {

        //得到配置文件对象
        ConfigContext configContext = new ConfigContext(getSourcePath(), getOutputPath());

        //初始化DB工具类
        DBHelper dbHelper = new DBHelper(configContext);

        //得到数据库表的元数据
        List<Map<String, Object>> resultList = dbHelper.descTable();

        //元数据处理
        List<ColumnDefinition> columnDefinitionList = ColumnHelper.covertColumnDefinition(resultList);

        //生成代码
        EntityService.doGenerator(configContext, columnDefinitionList, new Callback() {
            public void write(ConfigContext configContext, VelocityContext context) {

                FileUtil.writeFile(configContext.getOutputPath(),                   //输出目录
                        String.format("%s.java",configContext.getTargetName()),    //文件名
                        VelocityUtil.render("entity.vm", context));                 //模板生成内容

                FileUtil.writeFile(configContext.getOutputPath(),
                        String.format("I%sDasService.java",configContext.getTargetName()),
                        VelocityUtil.render("contract.vm", context));

                FileUtil.writeFile(configContext.getOutputPath(),
                        String.format("%sDao.java",configContext.getTargetName()),
                        VelocityUtil.render("dao.vm", context));

                FileUtil.writeFile(configContext.getOutputPath(),
                        String.format("%sDasService.java",configContext.getTargetName()),
                        VelocityUtil.render("service.vm", context));
            }
        });

    }
}
