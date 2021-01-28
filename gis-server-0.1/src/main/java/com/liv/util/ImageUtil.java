package com.liv.util;

import sun.misc.BASE64Decoder;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPOutputStream;

/**
 * @author LiV
 * @Title:
 * @Package com.liv.util
 * @Description: 图片工具类
 * @date 2020.3.26  09:55
 * @email 453826286@qq.com
 */
public class ImageUtil {
    /**
     * @Author: LiV
     * @Date: 2020.3.26 14:26
     * @Description: 压缩输出图片
     **/
    public static void gzipResponseOut(byte[] b, HttpServletResponse response ) throws IOException {
        {

            ByteArrayOutputStream bout = new ByteArrayOutputStream();
            GZIPOutputStream gout = null;
            try {
                gout = new GZIPOutputStream(bout);
                gout.write(b);
                gout.flush();
            } catch (IOException e1) {
                e1.printStackTrace();
            }finally{
                gout.close();
            }
            // 得到压缩后的数据
            byte g[] = bout.toByteArray();
            response.setHeader("Content-Encoding", "gzip");
            response.getOutputStream().write(g);
        }
    }

    /**
     * @Author: LiV
     * @Date: 2020.3.26 14:26
     * @Description: 压缩输出空白瓦片
     **/
    public static void gzipNoTileResponseOut(HttpServletResponse response ) throws IOException {
        {

            ByteArrayOutputStream bout = new ByteArrayOutputStream();
            BASE64Decoder decoder =new BASE64Decoder();
            GZIPOutputStream gout = null;
            try {
                gout = new GZIPOutputStream(bout);

                gout.write(decoder.decodeBuffer(BLANK_TILE));
                gout.flush();
            } catch (IOException e1) {
                e1.printStackTrace();
            }finally{
                gout.close();
            }
            // 得到压缩后的数据
            byte g[] = bout.toByteArray();
            response.setHeader("Content-Encoding", "gzip");
            response.getOutputStream().write(g);
        }
    }

    /*空白瓦片*/
    private static final String BLANK_TILE = "/9j/4AAQSkZJRgABAQEAYABgAAD/4QBYRXhpZgAATU0AKgAAAAgABAExAAIAAAARAAAAPlEQAAEAAAABAQAAAFERAAQAAAABAAAAAFESAAQAAAABAAAAAAAAAABBZG9iZSBJbWFnZVJlYWR5AAD/2wBDAAIBAQIBAQICAgICAgICAwUDAwMDAwYEBAMFBwYHBwcGBwcICQsJCAgKCAcHCg0KCgsMDAwMBwkODw0MDgsMDAz/2wBDAQICAgMDAwYDAwYMCAcIDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAz/wAARCAEAAQADASIAAhEBAxEB/8QAHwAAAQUBAQEBAQEAAAAAAAAAAAECAwQFBgcICQoL/8QAtRAAAgEDAwIEAwUFBAQAAAF9AQIDAAQRBRIhMUEGE1FhByJxFDKBkaEII0KxwRVS0fAkM2JyggkKFhcYGRolJicoKSo0NTY3ODk6Q0RFRkdISUpTVFVWV1hZWmNkZWZnaGlqc3R1dnd4eXqDhIWGh4iJipKTlJWWl5iZmqKjpKWmp6ipqrKztLW2t7i5usLDxMXGx8jJytLT1NXW19jZ2uHi4+Tl5ufo6erx8vP09fb3+Pn6/8QAHwEAAwEBAQEBAQEBAQAAAAAAAAECAwQFBgcICQoL/8QAtREAAgECBAQDBAcFBAQAAQJ3AAECAxEEBSExBhJBUQdhcRMiMoEIFEKRobHBCSMzUvAVYnLRChYkNOEl8RcYGRomJygpKjU2Nzg5OkNERUZHSElKU1RVVldYWVpjZGVmZ2hpanN0dXZ3eHl6goOEhYaHiImKkpOUlZaXmJmaoqOkpaanqKmqsrO0tba3uLm6wsPExcbHyMnK0tPU1dbX2Nna4uPk5ebn6Onq8vP09fb3+Pn6/9oADAMBAAIRAxEAPwD9qKKKK8s6AooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooqvJqCrIyRpJMy8EIPu/UnA/WgCxRVaPVIzcLDIGhmf7qSY+b6EEj9as0AFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQBV1y8NhpM0oyCq4BHUEnH9ahTULddBeWCTZCqEBuhU/wCOfzNWdSshqNhNCePMXAPoe361xF59q06NrOXcke7dtPQn1FADrHVNmoLJc7rhWG1ixJYA9x7iuy0W8+3aXDJktkEbj1bBIz+OK5OOeTVrG3sbe2AZG3M4Odx9Txx/9YV1+m2Q06whhHPlrgn1Pf8AWgCeiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKAI3nWOWNGbDSZCjHXHNNiu45rXzlb93jdk8cVkyL5mqkGVof8ASX+dSMj90vrkUlnbRw6P5kkjPNLBIIwTwowcgCgDR/t+y/5+of8AvqpF1SCSJXjkWRWcRgrzzWPNcTEMtm80kflj7Rt+by+mdn+1jPFWNUe3k0yzdZmWESIAQ2OPf6UAaF3qMdiV81tu7ODg9sf41Hf31kreXcSW+eu2TBrnWgAmVWtYRIW2KpjA5Knr7A/yrW1h51sRFDJbNHcgRRqqHcQRjg5x0yenSgC5Y31iW8u2kt+mdseBxR/b9l/z9Q/99VDp1zcSQW8jzWqxSHaF8shj7feIzxTrqVoNYDLG0pEB+VcZ+8PWgCwNUt2tWnWRWhU4LLyBUba9aopJkYKOSTG3H6Vn3peLQ7jz0Mcl1KWSPcOOmMnoOmSfSs6W4WcyRFlUNH182Pqcj+9j9c0AdHc6vb2b7ZJNp27/ALpPy+vA9jTY9btpJFUSHc52jKMMn8qydczLI3zbY2tYyw80KDy+OeQfzxTNNDNsf5HVriMEh/u89AoyPxzQB0tFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFAFNdCsxO0hgV2Y7iXy/P40Q6HbwFtqsdwZcFyQoPXHpn2q5RQBTXQLVFwqzKPQTuP60+PSbeNNvlhsZxvJfGevJz6VZooAz5fDlpIzHy9hIAXZ8vl47jHeprbSoba48xQzMqhE3HPlrjGB6VaooAop4ft42XmYqpJVDIdqk56D8TS/2Ba7922bdjGfPfOPzq7RQBVXSYVjZVaZQxBP75jnr6n3qP8AsC3Em7dNuIwT5hq9RQBTfRY3lD+ZcKwQJlZWXIBJ5x9aZH4et4pVdWm3KwfmUkEjpkGr9FABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAf/2Q==";
}
