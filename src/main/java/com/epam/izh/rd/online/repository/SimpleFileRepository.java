package com.epam.izh.rd.online.repository;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class SimpleFileRepository implements FileRepository {
    private static long countFilesInDir;
    private static long countDirs;

    /**
     * Метод рекурсивно подсчитывает количество файлов в директории
     *
     * @param path путь до директори
     * @return файлов, в том числе скрытых
     */
    @Override
    public long countFilesInDirectory(String path) {
        File scanDir = new File("src/main/resources" + File.separator + path);
        if (!scanDir.exists()) {
            return 0;
        }
        if (scanDir.listFiles() != null) {
            for (File file : scanDir.listFiles()) {
                if (file.isFile()) {
                    countFilesInDir++;
                } else {
                    countFilesInDirectory(path + File.separator + file.getName());
                }
            }
        }
        return countFilesInDir;
    }

    /**
     * Метод рекурсивно подсчитывает количество папок в директории, считая корень
     *
     * @param path путь до директории
     * @return число папок
     */
    @Override
    public long countDirsInDirectory(String path) {
        File scanDir = new File("src/main/resources" + File.separator + path);
        if (!scanDir.exists()) {
            return 0;
        }
        countDirs++;
        FileFilter dirFilter = pathname -> pathname.isDirectory();
        if (scanDir.listFiles(dirFilter) != null) {
            for (File file : scanDir.listFiles(dirFilter)) {
                countDirsInDirectory(path + File.separator + file.getName());
            }
        }
        return countDirs;
    }

    /**
     * Метод копирует все файлы с расширением .txt
     *
     * @param from путь откуда
     * @param to   путь куда
     */
    @Override
    public void copyTXTFiles(String from, String to) {
        if (from.equals(to)) {
            System.out.println("Пути исходной папки и папки назначения совпадают.");
            return;
        }

        File srcDir = new File(from);
        if (!srcDir.exists()) {
            System.out.println("Исходная папка не существует");
            return;
        }
        if (!srcDir.isDirectory()) {
            System.out.println("Указанный путь не является папкой.");
            return;
        }

        File destDir = new File(to);
        if (!destDir.exists()) {
            destDir.mkdir();
        }
        FilenameFilter txtType = (dir, name) -> name.toLowerCase().endsWith(".txt");
        if (srcDir.listFiles(txtType) != null) {
            for (File txtFile : srcDir.listFiles(txtType)) {
                try (FileInputStream inputStream = new FileInputStream(txtFile);
                    FileOutputStream outputStream = new FileOutputStream(new File(destDir.getPath() + File.separator + txtFile.getName()))) {
                    byte[] buffer = new byte[1024];
                    int length;
                    while ((length = inputStream.read(buffer)) > 0) {
                        outputStream.write(buffer, 0, length);
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Метод создает файл на диске с расширением txt
     *
     * @param path путь до нового файла
     * @param name имя файла
     * @return был ли создан файл
     */
    @Override
    public boolean createFile (String path, String name) {

        File newDir = new File("src/main/resources" + File.separator + path);
        if (!newDir.exists()) {
            newDir.mkdir();
        }
        File file = new File(newDir.getAbsolutePath() + File.separator + name);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file.exists(); // Почему тест фозвращает false, хотя файл создается?
    }

    /**
     * Метод считывает тело файла .txt из папки src/main/resources
     *
     * @param fileName имя файла
     * @return контент
     */
    @Override
    public String readFileFromResources(String fileName) {
        String srcPath = "src/main/resources" + File.separator + fileName;
        File srcFile = new File(srcPath);
        if (!srcFile.exists()) {
            return "";
        }
        StringBuilder stringBuilder = new StringBuilder();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(srcPath));
            do {
                stringBuilder.append(reader.readLine());
            } while (reader.readLine() != null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }
}
