package com.example.homework2;

import org.springframework.util.Assert;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
public class HelloClassLoader extends ClassLoader{
    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        try {
            byte[] classData = this.loadClassData(name);
            return super.defineClass(name,classData,0,classData.length);
        } catch (Exception e) {
            throw new ClassNotFoundException("加载xlass失败",e);
        }
    }

    /**
     * 将xlass文件加载为标准字节码
     * @param xlassName xlass文件名，无后缀
     * @return 标准字节码
     * @throws Exception xlass文件加载失败
     */
    private byte[] loadClassData(String xlassName) throws Exception {
        byte[] rawByte = this.loadXlassFile(xlassName);
        return decodeXlass(rawByte);
    }

    /**
     * 加载xlass文件
     * @param xlassName xlass文件名，无后缀
     * @return 原始字节数组
     * @throws URISyntaxException xlass文件录解析失败
     * @throws IOException xlass文件内容读取失败
     */
    private byte[] loadXlassFile(String xlassName) throws URISyntaxException, IOException {
        String fileName = "/"+xlassName+".xlass";
        URL xlass =HelloClassLoader.class.getResource(fileName);
        Assert.notNull(xlass,"未找到xlass文件,"+fileName);
        URI xlassURI = xlass.toURI();
        Path path = Paths.get(xlassURI);
        return Files.readAllBytes(path);
    }

    /**
     * 将 xlass 原始字节码转为标准字节码
     * @param rawBytes 未解码的原始字节数组
     * @return 解码后的标准字节码
     */
    private byte[] decodeXlass(byte[] rawBytes){
        byte[] decoded = new byte[rawBytes.length];
        for (int i = 0; i < rawBytes.length; i++) {
            decoded[i] = (byte) (255-rawBytes[i]);
        }
        return decoded;
    }

}
