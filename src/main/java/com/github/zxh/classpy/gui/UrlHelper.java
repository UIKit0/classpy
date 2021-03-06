package com.github.zxh.classpy.gui;

import java.net.URL;

/**
 *
 * @author zxh
 */
public class UrlHelper {
    
    public static String getExtension(URL url) {
        String urlStr = url.toString();
        int idxOfDot = urlStr.lastIndexOf('.');
        return idxOfDot < 0 ? urlStr : urlStr.substring(idxOfDot);
    }
    
    public static String getFileName(URL url) {
        String urlStr = url.toString();
        int idxOfDot = urlStr.lastIndexOf('/');
        return idxOfDot < 0 ? urlStr : urlStr.substring(idxOfDot + 1);
    }
    
}
