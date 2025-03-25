package com.siyu.generator;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.ArrayUtil;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

/**
* 静态文件生成器
* */
public class StaticGenerator {
    public static void main(String[] args) {
        String projectPath = System.getProperty("user.dir");

        File parentFile = new File(projectPath).getParentFile();
        String inputPath = new File(parentFile, "siyu-generator-demo-projects" +
                File.separator + "acm-template").getAbsolutePath();
        System.out.println(inputPath);
        String outputPath = projectPath;
        copyFilesByHutool(inputPath, outputPath);
    }
/**
* 复制文件或目录(Hutool实现，会将输入目录完整拷贝到输出目录下)
* 如果目标文件为目录，则将源文件以相同文件名拷贝到目标目录
* Params:
* srcPath – 源文件或目录
* destPath – 目标文件或目录，目标不存在会自动创建（目录、文件都创建）
* isOverride – 是否覆盖目标文件
* Returns:
* 目标目录或文件
* Throws:
* IORuntimeException – IO异
* */
    public static void copyFilesByHutool(String InputPath, String OutputPath) {
        FileUtil.copy(InputPath, OutputPath, false);
    }

    /**
     * 递归拷贝文件（递归实现，会将输入目录完整拷贝到输出目录下）
     * @param inputPath
     * @param outputPath
     */
    public static void copyFilesByRecursive(String inputPath, String outputPath) {
        File inputFile = new File(inputPath);
        File outputFile = new File(outputPath);
        try {
            copyFileByRecursive(inputFile, outputFile);
        } catch (Exception e) {
            System.err.println("文件复制失败");
            e.printStackTrace();
        }
    }

    /**
     * 文件 A => 目录 B，则文件 A 放在目录 B 下
     * 文件 A => 文件 B，则文件 A 覆盖文件 B
     * 目录 A => 目录 B，则目录 A 放在目录 B 下
     *
     * 核心思路：先创建目录，然后遍历目录内的文件，依次复制
     * @param inputFile
     * @param outputFile
     * @throws IOException
     */
    private static void copyFileByRecursive(File inputFile, File outputFile) throws IOException {
        // 区分是文件还是目录
        if (inputFile.isDirectory()) {
            System.out.println(inputFile.getName());;
            File destOutputFile = new File(outputFile, inputFile.getName());
            // 如果是目录，首先创建目标目录
            if (!destOutputFile.exists()) {
                destOutputFile.mkdirs();
            }
            // 获取目录下的所有文件和子目录
            File[] files = inputFile.listFiles();
            // 无子文件，直接结束
            if (ArrayUtil.isEmpty(files)) {
                return;
            }
            for (File file : files) {
                // 递归拷贝下一层文件
                copyFileByRecursive(file, destOutputFile);
            }
        } else {
            // 是文件，直接复制到目标目录下
            Path destPath = outputFile.toPath().resolve(inputFile.getName());
            Files.copy(inputFile.toPath(), destPath, StandardCopyOption.REPLACE_EXISTING);
        }
    }


}
