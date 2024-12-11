package cn.master.tauren.util;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileWriter;

/**
 * @author Created by 11's papa on 12/11/2024
 **/
@Slf4j
public class FileUtils {

    public static void genFile(String filePath, String content, String type) {
        FileWriter fw = null;
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                file.createNewFile();
            }
            fw = new FileWriter(filePath);
            fw.write(content);
            log.info("{} created successfully", type);
        } catch (Exception e) {
            log.error("", e);
        } finally {
            try {
                assert fw != null;
                fw.close();
            } catch (Exception e) {
                log.error("", e);
            }
        }
    }
}
