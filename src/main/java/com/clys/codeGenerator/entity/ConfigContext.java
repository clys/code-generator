package com.clys.codeGenerator.entity;

import com.clys.codeGenerator.utils.Dom4jUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.util.List;
import java.util.Map;

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
public class ConfigContext {
    public ConfigContext(String sourcePath) throws Exception {
        SAXReader reader = new SAXReader();
        File file = new File(sourcePath + "codeGeneratorContext.xml");
        Document document = reader.read(file);
        Element root = document.getRootElement(),aBean;
        List<Element> childElements = root.elements("bean");
        Map<String, Element> beanMap = Dom4jUtils.elementListToMap(childElements, "id");

        this.setJdbc(new Jdbc());
        aBean = beanMap.get("jdbc");
        if(aBean != null){
            Dom4jUtils.extractProperty(this.getJdbc(), aBean.elements("property"));
        }

    }

    private Jdbc jdbc;

    public Jdbc getJdbc() {
        return jdbc;
    }

    public void setJdbc(Jdbc jdbc) {
        this.jdbc = jdbc;
    }

    public class Jdbc {
        private String driver;
        private String url;
        private String username;
        private String password;
        private List<String> l;
        private Map<String,String> m;

        public List<String> getL() {
            return l;
        }

        public void setL(List<String> l) {
            this.l = l;
        }

        public Map<String, String> getM() {
            return m;
        }

        public void setM(Map<String, String> m) {
            this.m = m;
        }

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

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }
}