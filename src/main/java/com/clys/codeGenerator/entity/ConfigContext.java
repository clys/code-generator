package com.clys.codeGenerator.entity;

import com.clys.codeGenerator.utils.PathUtil;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.File;
import java.io.FileReader;
import java.util.Map;

/**
 * <p>名称: 插件配置</p>
 * <p>创建日期：15-12-28</p>
 *
 * @author 陈李雨声
 * @version 1.0
 */
@XmlRootElement
public class ConfigContext {
    public static ConfigContext newInstance(String sourcePath) throws Exception {
        File file = new File(sourcePath + "codeGeneratorContext.xml");
        JAXBContext context = JAXBContext.newInstance(ConfigContext.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        ConfigContext configContext = (ConfigContext) unmarshaller.unmarshal(new FileReader(file));
        configContext.getPath().setSource(sourcePath);
        configContext.getPath().setOutput(
                PathUtil.handle(sourcePath, configContext.getPath().getOutput())
        );

        return configContext;
    }

    public ConfigContext() {
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

    public static class Jdbc {
        public Jdbc() {
        }

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

    public static class Path {
        public Path() {
        }

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
            this.output = output;
        }
    }

    public static class Target {
        public Target() {
        }

        private String pack;
        private Map<String, String> dbToCode;

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

    public static class Vm {
        public Vm() {
        }

        private Map<String, String> elements;

        public Map<String, String> getElements() {
            return elements;
        }

        public void setElements(Map<String, String> elements) {
            this.elements = elements;
        }
    }
}