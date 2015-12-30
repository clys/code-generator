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

import com.clys.codeGenerator.service.CodeGeneratorService;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;

import java.io.File;


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

        CodeGeneratorService codeGeneratorService = new CodeGeneratorService();
        try {
            codeGeneratorService.dbToCode(getSourcePath());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
