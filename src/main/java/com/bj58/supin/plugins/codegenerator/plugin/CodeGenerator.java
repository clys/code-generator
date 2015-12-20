package com.bj58.supin.plugins.codegenerator.plugin;

/*
 * Copyright 2001-2005 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import com.bj58.supin.plugins.codegenerator.core.entity.ColumnDefinition;
import com.bj58.supin.plugins.codegenerator.core.helper.ColumnHelper;
import com.bj58.supin.plugins.codegenerator.core.ConfigContext;
import com.bj58.supin.plugins.codegenerator.core.helper.DBHelper;
import com.bj58.supin.plugins.codegenerator.core.service.Callback;
import com.bj58.supin.plugins.codegenerator.core.service.EntityService;
import com.bj58.supin.plugins.codegenerator.core.util.FileUtil;
import com.bj58.supin.plugins.codegenerator.core.util.VelocityUtil;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.velocity.VelocityContext;


import java.io.*;
import java.util.List;
import java.util.Map;


/**
 *
 * @goal generator
 */
public class CodeGenerator extends AbstractMojo {
    /**
     * Location of the file.
     *
     * @parameter expression="${project.build.directory}"
     * @required
     */
    private File outputDirectory;

    /**
     * @parameter expression="${project.build.sourceDirectory}"
     * @required
     * @readonly
     */
    private File sourcedir;

    /**
     * @parameter expression="${project.basedir}"
     * @required
     * @readonly
     */
    private File basedir;



    private String getSourcePath(){
        return String.format("%s/src/main/resources/",basedir.getAbsolutePath());
    }


    private String getOutputPath(){
        return String.format("%s/generator/",outputDirectory.getAbsolutePath());
    }



    public void execute() throws MojoExecutionException {

        //得到配置文件对象
        ConfigContext configContext = new ConfigContext(getSourcePath(),getOutputPath());

        //初始化DB工具类
        DBHelper dbHelper = new DBHelper(configContext);

        //得到数据库表的元数据
        List<Map<String,Object>>  resultList= dbHelper.descTable();

        //元数据处理
        List<ColumnDefinition> columnDefinitionList = ColumnHelper.covertColumnDefinition(resultList);


        EntityService.doGenerator(configContext, columnDefinitionList, new Callback() {
            public void write(ConfigContext configContext, VelocityContext context) {

                FileUtil.writeFile(configContext.getOutputPath(),                   //输出目录
                        String.format("%s.java",configContext.getTargetName()),    //文件名
                        VelocityUtil.render("entity.vm", context));                 //模板生成内容

                FileUtil.writeFile(configContext.getOutputPath(),
                        String.format("I%sDasService.java", configContext.getTargetName()),
                        VelocityUtil.render("contract.vm", context));

                FileUtil.writeFile(configContext.getOutputPath(),
                        String.format("%sDao.java", configContext.getTargetName()),
                        VelocityUtil.render("dao.vm", context));

                FileUtil.writeFile(configContext.getOutputPath(),
                        String.format("%sDasService.java", configContext.getTargetName()),
                        VelocityUtil.render("service.vm", context));
            }
        });

    }
}
