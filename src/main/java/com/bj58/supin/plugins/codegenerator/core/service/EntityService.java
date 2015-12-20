package com.bj58.supin.plugins.codegenerator.core.service;

import com.bj58.supin.plugins.codegenerator.core.ConfigContext;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;

import java.util.Properties;

/**
 * @author lujianing01@58.com
 * @Description:
 * @date 2015/12/18
 */
public class EntityService{

    public static void doGenerator(ConfigContext configContext, Object data,Callback callback) {
        //配置velocity的资源加载路径
        Properties velocityPros = new Properties();
        velocityPros.setProperty(VelocityEngine.FILE_RESOURCE_LOADER_PATH, configContext.getSourcePath());
        Velocity.init(velocityPros);

        //封装velocity数据
        VelocityContext context = new VelocityContext();
        context.put("table", configContext.getTargetTable());
        context.put("entity", configContext.getTargetName());
        context.put("package", configContext.getTargetPackage());
        context.put("columns", data);

        callback.write(configContext, context);

    }
}
