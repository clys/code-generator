package com.clys.codeGenerator.entity;

import com.clys.codeGenerator.utils.Dom4jUtils;
import com.clys.codeGenerator.utils.PathUtil;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * <p>名称: 插件配置</p>
 * <p>创建日期：15-12-28</p>
 *
 * @author 陈李雨声
 * @version 1.0
 */
public class ConfigContext {
    public ConfigContext(String sourcePath) throws Exception {
        SAXReader reader = new SAXReader();
        File file = new File(sourcePath + "codeGeneratorContext.xml");
        Document document = reader.read(file);
        Element root = document.getRootElement(), aBean;
        List<Element> childElements = root.elements("bean");
        Map<String, Element> beanMap = Dom4jUtils.elementListToMap(childElements, "id");

        this.setJdbc(new Jdbc());
        aBean = beanMap.get("jdbc");
        if (aBean != null) {
            Dom4jUtils.extractProperty(this.getJdbc(), aBean.elements("property"));
        }

        this.setPath(new Path(sourcePath));
        aBean = beanMap.get("path");
        if (aBean != null) {
            Dom4jUtils.extractProperty(this.getPath(), aBean.elements("property"));
        }

        this.setTarget(new Target());
        aBean = beanMap.get("target");
        if (aBean != null) {
            Dom4jUtils.extractProperty(this.getTarget(), aBean.elements("property"));
        }

        this.setVm(new Vm());
        aBean = beanMap.get("vm");
        if (aBean != null) {
            Dom4jUtils.extractProperty(this.getVm(), aBean.elements("property"));
        }

    }

    private Jdbc jdbc;
    private Path path;
    private Target target;
    private Vm vm;

    public Vm getVm() {
        return vm;
    }

    public void setVm(Vm vm) {
        this.vm = vm;
    }

    public Target getTarget() {
        return target;
    }

    public void setTarget(Target target) {
        this.target = target;
    }

    public Path getPath() {
        return path;
    }

    public void setPath(Path path) {
        this.path = path;
    }

    public Jdbc getJdbc() {
        return jdbc;
    }

    public void setJdbc(Jdbc jdbc) {
        this.jdbc = jdbc;
    }

    public class Jdbc {
        private String driver;
        private String url;
        private String userName;
        private String password;

        public String getDriver() {
            return driver;
        }

        public void setDriver(String driver) {
            this.driver = driver;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String username) {
            this.userName = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }

    public class Path {
        public Path(String source) {
            this.source = source;
        }

        private String source;
        private String output;

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public String getOutput() {
            return output;
        }

        public void setOutput(String output) {
            this.output = PathUtil.handle(this.getSource(),output);
        }
    }

    public class Target{
        private String pack;
        private Map<String,String> dbToCode;

        public String getPackage() {
            return pack;
        }

        public void setPackage(String pack) {
            this.pack = pack;
        }

        public Map<String, String> getDbToCode() {
            return dbToCode;
        }

        public void setDbToCode(Map<String, String> dbToCode) {
            this.dbToCode = dbToCode;
        }
    }

    public class Vm{
        private Map<String,String> elements;

        public Map<String, String> getElements() {
            return elements;
        }

        public void setElements(Map<String, String> elements) {
            this.elements = elements;
        }
    }
}