package graduation.java.test;

import graduation.java.conf.ConfigurationManager;

/**
 * FileName: ConfigurationManagerTest
 * Author:   hadoop
 * Email:    3165845957@qq.com
 * Date:     19-2-26 上午11:04
 * Description:
 */
public class ConfigurationManagerTest {
    public static void main(String[] args){
        String key1 = ConfigurationManager.getProperty("key1");
        String key2 = ConfigurationManager.getProperty("key2");
        System.out.println(key1);
        System.out.println(key2);
    }
}
