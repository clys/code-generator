package com.clys.codeGenerator.utils;

import org.apache.commons.lang3.StringUtils;

/**
 * <p>名称: 路径的工具</p>
 * <p>创建日期：15-12-29</p>
 *
 * @author 陈李雨声
 * @version 1.0
 */
public class PathUtil {
    public static String handle(String base, String target) {
        String path;
        base = base.replaceAll("\\\\", "/");
        target = target.replaceAll("\\\\", "/");
        if (!base.matches(".*/$")) {
            base += "/";
        }
        if (target.matches("^\\./.*")) {
            path = base + target.substring(2);
        } else if (target.matches("^\\.\\./.*")) {
            int total = StringUtils.countMatches(target, "../");
            int index = base.length() - 1;
            for (int i = 0; i < total; i++) {
                index = base.lastIndexOf("/", index - 1);
            }
            if (index == -1) {
                path = target.substring(total * 3 - 1);

            } else {
                path = base.substring(0, index) + target.substring(total * 3 - 1);

            }

        } else if (target.matches("^/.*") || target.matches("^[A-Za-z]:/.*")) {
            path = target;
        } else {
            path = base + target;
        }

        return path;
    }

    public static void main(String[] args) {
        System.out.println(handle("c:/123", "../../asdasd"));
    }
}
