package com.handy.base.utils;

import android.app.Activity;
import android.content.Context;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 文件管理工具类
 * <p>
 * Created by LiuJie on 2016/1/19.
 */
public class FileUT {
    private static FileUT fileUT = null;

    private FileUT() {
    }

    public synchronized static FileUT getInstance() {
        if (fileUT == null) {
            fileUT = new FileUT();
        }
        return fileUT;
    }

    /**
     * ===================================================================
     * 按行读取文本文件中的内容
     *
     * @param strFilePath 文件存储路径
     * @return
     */
    public String ReadTxtFile(String strFilePath) {
        String content = ""; //文件内容字符串
        //打开文件
        File file = new File(strFilePath);
        //如果path是传递过来的参数，可以做一个非目录的判断
        if (file.isDirectory()) {
            PrintfUT.getInstance().LogD("The File doesn't not exist.");
        } else {
            try {
                InputStream instream = new FileInputStream(file);
                InputStreamReader inputreader = new InputStreamReader(instream);
                BufferedReader buffreader = new BufferedReader(inputreader);
                String line;
                //分行读取
                while ((line = buffreader.readLine()) != null) {
                    content += line + "\n";
                }
                instream.close();
            } catch (FileNotFoundException e) {
                PrintfUT.getInstance().LogD("The File doesn't not exist.");
            } catch (IOException e) {
                PrintfUT.getInstance().LogD(e.getMessage());
            }
        }
        return content;
    }

    /**
     * ===================================================================
     * 从resource中的raw文件夹中获取文件并读取数据（资源文件只能读不能写）
     *
     * @param activity 执行动作的Activity
     * @param ResId    R.raw资源下的文件资源ID
     * @return 从Raw中读取的文件内容
     */
    public String getDataFromRaw(Activity activity, int ResId, String codeType) {
        String res = "";
        try {
            //得到资源中的Raw数据流
            InputStream in = activity.getResources().openRawResource(ResId);
            //得到数据的大小
            int length = in.available();
            byte[] buffer = new byte[length];
            //读取数据
            in.read(buffer);
            //依test.txt的编码类型选择合适的编码，如果不调整会乱码
            res = EncodeUT.getInstance().getEncodeString(buffer, codeType);
            //关闭
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    /**
     * ===================================================================
     * 从resource的asset中读取文件数据（资源文件只能读不能写）
     *
     * @param activity 执行动作的Activity
     * @return 从Assets中读取的文件内容
     */
    public String getDataFromAssets(Activity activity, String codeType) {
        String fileName = "yan.txt"; //文件名字
        String res = "";
        try {
            InputStream in = activity.getResources().getAssets().open(fileName);
            //\Test\assets\yan.txt这里有这样的文件存在
            int length = in.available();
            byte[] buffer = new byte[length];
            in.read(buffer);
            res = EncodeUT.getInstance().getEncodeString(buffer, codeType);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    /**
     * ===================================================================
     * 从手机/data/data/<应用程序名>目录上读取文件
     *
     * @param context  上下文
     * @param fileName 文件获取路径
     * @return 文件读取的内容
     * @throws IOException
     */
    public String readFile(Context context, String fileName, String codeType) throws IOException {
        String res = "";
        try {
            FileInputStream fin = context.openFileInput(fileName);
            int length = fin.available();
            byte[] buffer = new byte[length];
            fin.read(buffer);
            res = EncodeUT.getInstance().getEncodeString(buffer, codeType);
            fin.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    /**
     * 从手机/data/data/<应用程序名>目录上写入文件
     *
     * @param context  上下文
     * @param fileName 文件写入路径
     * @param writestr 文件写入内容
     * @throws IOException
     */
    public void writeFile(Context context, String fileName, String writestr) throws IOException {
        try {
            FileOutputStream fout = context.openFileOutput(fileName, Activity.MODE_PRIVATE);
            byte[] bytes = writestr.getBytes();
            fout.write(bytes);
            fout.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * ===================================================================
     * 从SD卡的/mnt/sdcard/目录下面的读取文件
     *
     * @param fileName 文件获取路径
     * @return
     * @throws IOException
     */
    //读SD中的文件
    public String readFileSdcardFile(String fileName, String codeType) throws IOException {
        String res = "";
        try {
            FileInputStream fin = new FileInputStream(fileName);
            int length = fin.available();
            byte[] buffer = new byte[length];
            fin.read(buffer);
            res = EncodeUT.getInstance().getEncodeString(buffer, codeType);
            fin.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    /**
     * 从SD卡的/mnt/sdcard/目录下面的写入文件
     *
     * @param fileName  文件保存路径
     * @param write_str 文件写入内容
     * @throws IOException
     */
    //写数据到SD中的文件
    public void writeFileSdcardFile(String fileName, String write_str) throws IOException {
        try {
            FileOutputStream fout = new FileOutputStream(fileName);
            byte[] bytes = write_str.getBytes();
            fout.write(bytes);
            fout.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * ===================================================================
     * 使用File类读取文件
     *
     * @param fileName 文件获取路径
     * @return
     * @throws IOException
     */
    public String readSDFile(String fileName, String codeType) throws IOException {
        File file = new File(fileName);
        FileInputStream fis = new FileInputStream(file);
        int length = fis.available();
        byte[] buffer = new byte[length];
        fis.read(buffer);
        String res = EncodeUT.getInstance().getEncodeString(buffer, codeType);
        fis.close();
        return res;
    }

    /**
     * 使用File类写入文件
     *
     * @param fileName  文件保存路径
     * @param write_str 文件写入内容
     * @throws IOException
     */
    public void writeSDFile(String fileName, String write_str) throws IOException {
        File file = new File(fileName);
        FileOutputStream fos = new FileOutputStream(file);
        byte[] bytes = write_str.getBytes();
        fos.write(bytes);
        fos.close();
    }
    /*
     * File类还有下面一些常用的操作：
     * String Name = File.getName();  //获得文件或文件夹的名称：
     * String parentPath = File.getParent();  //获得文件或文件夹的父目录
     * String path = File.getAbsoultePath(); //绝对路经
     * String path = File.getPath(); //相对路经
     * File.createNewFile(); //建立文件
     * File.mkDir(); //建立文件夹
     * File.isDirectory(); //判断是文件或文件夹
     * File[] files = File.listFiles();  //列出文件夹下的所有文件和文件夹名
     * File.renameTo(dest);  //修改文件夹和文件名
     * File.delete();  //删除文件夹或文件
     */

    /**
     * ===================================================================
     * 从Assets资源下复制文件到data目录
     *
     * @param strAssetsFilePath 资源文件路径
     * @param strDesFilePath    data目录下复制的文件路径
     * @return
     */
    public boolean assetsCopyData(Activity activity, String strAssetsFilePath, String strDesFilePath) {
        boolean bIsSuc = true;
        InputStream inputStream = null;
        OutputStream outputStream = null;
        File file = new File(strDesFilePath);
        if (!file.exists()) {
            try {
                file.createNewFile();
                Runtime.getRuntime().exec("chmod 766 " + file); //chmod 766 == rw即读写
            } catch (IOException e) {
                bIsSuc = false;
            }
        } else { //复制文件改路径下已存在
            return true;
        }
        try {
            inputStream = activity.getResources().getAssets().open(strAssetsFilePath);
            outputStream = new FileOutputStream(file);
            int nLen = 0;
            byte[] buff = new byte[1024];
            while ((nLen = inputStream.read(buff)) > 0) {
                outputStream.write(buff, 0, nLen);
            }
            //完成
        } catch (IOException e) {
            bIsSuc = false;
        } finally {
            try {
                if (outputStream != null) {
                    outputStream.close();
                }
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                bIsSuc = false;
            }
        }
        return bIsSuc;
    }

    /**
     * ===================================================================
     * 根据文件路径获取文件
     *
     * @param filePath 文件路径
     * @return 文件
     */
    public File getFileByPath(String filePath) {
        return ValidityUT.getInstance().isEmpty(filePath) ? null : new File(filePath);
    }

    /**
     * 判断文件是否存在
     *
     * @param filePath 文件路径
     * @return {@code true}: 存在<br>{@code false}: 不存在
     */
    public boolean isFileExists(String filePath) {
        return isFileExists(getFileByPath(filePath));
    }

    /**
     * 判断文件是否存在
     *
     * @param file 文件
     * @return {@code true}: 存在<br>{@code false}: 不存在
     */
    public boolean isFileExists(File file) {
        return file != null && file.exists();
    }

    /**
     * 判断是否是目录
     *
     * @param dirPath 目录路径
     * @return {@code true}: 是<br>{@code false}: 否
     */
    public boolean isDir(String dirPath) {
        return isDir(getFileByPath(dirPath));
    }

    /**
     * 判断是否是目录
     *
     * @param file 文件
     * @return {@code true}: 是<br>{@code false}: 否
     */
    public boolean isDir(File file) {
        return isFileExists(file) && file.isDirectory();
    }

    /**
     * 判断是否是文件
     *
     * @param filePath 文件路径
     * @return {@code true}: 是<br>{@code false}: 否
     */
    public boolean isFile(String filePath) {
        return isFile(getFileByPath(filePath));
    }

    /**
     * 判断是否是文件
     *
     * @param file 文件
     * @return {@code true}: 是<br>{@code false}: 否
     */
    public boolean isFile(File file) {
        return isFileExists(file) && file.isFile();
    }

    /**
     * 判断目录是否存在，不存在则判断是否创建成功
     *
     * @param dirPath 文件路径
     * @return {@code true}: 存在或创建成功<br>{@code false}: 不存在或创建失败
     */
    public boolean createOrExistsDir(String dirPath) {
        return createOrExistsDir(getFileByPath(dirPath));
    }

    /**
     * 判断目录是否存在，不存在则判断是否创建成功
     *
     * @param file 文件
     * @return {@code true}: 存在或创建成功<br>{@code false}: 不存在或创建失败
     */
    public boolean createOrExistsDir(File file) {
        //如果存在，是目录则返回true，是文件则返回false，不存在则返回是否创建成功
        return file != null && (file.exists() ? file.isDirectory() : file.mkdirs());
    }

    /**
     * 判断文件是否存在，不存在则判断是否创建成功
     *
     * @param filePath 文件路径
     * @return {@code true}: 存在或创建成功<br>{@code false}: 不存在或创建失败
     */
    public boolean createOrExistsFile(String filePath) {
        return createOrExistsFile(getFileByPath(filePath));
    }

    /**
     * 判断文件是否存在，不存在则判断是否创建成功
     *
     * @param file 文件
     * @return {@code true}: 存在或创建成功<br>{@code false}: 不存在或创建失败
     */
    public boolean createOrExistsFile(File file) {
        if (file == null) return false;
        //如果存在，是文件则返回true，是目录则返回false
        if (file.exists()) return file.isFile();
        if (!createOrExistsDir(file.getParentFile())) return false;
        try {
            return file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 判断文件是否存在，存在则在创建之前删除
     *
     * @param filePath 文件路径
     * @return {@code true}: 创建成功<br>{@code false}: 创建失败
     */
    public boolean createFileByDeleteOldFile(String filePath) {
        return createFileByDeleteOldFile(getFileByPath(filePath));
    }

    /**
     * 判断文件是否存在，存在则在创建之前删除
     *
     * @param file 文件
     * @return {@code true}: 创建成功<br>{@code false}: 创建失败
     */
    public boolean createFileByDeleteOldFile(File file) {
        if (file == null) return false;
        //文件存在并且删除失败返回false
        if (file.exists() && file.isFile() && !file.delete()) return false;
        //创建目录失败返回false
        if (!createOrExistsDir(file.getParentFile())) return false;
        try {
            return file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 复制或移动目录
     *
     * @param srcDirPath  源目录路径
     * @param destDirPath 目标目录路径
     * @param isMove      是否移动
     * @return {@code true}: 复制或移动成功<br>{@code false}: 复制或移动失败
     */
    private boolean copyOrMoveDir(String srcDirPath, String destDirPath, boolean isMove) {
        return copyOrMoveDir(getFileByPath(srcDirPath), getFileByPath(destDirPath), isMove);
    }

    /**
     * 复制或移动目录
     *
     * @param srcDir  源目录
     * @param destDir 目标目录
     * @param isMove  是否移动
     * @return {@code true}: 复制或移动成功<br>{@code false}: 复制或移动失败
     */
    private boolean copyOrMoveDir(File srcDir, File destDir, boolean isMove) {
        if (srcDir == null || destDir == null) return false;
        //如果目标目录在源目录中则返回false，看不懂的话好好想想递归怎么结束
        //srcPath : F:\\MyGithub\\AndroidUtilCode\\utilcode\\src\\test\\res
        //destPath: F:\\MyGithub\\AndroidUtilCode\\utilcode\\src\\test\\res1
        //为防止以上这种情况出现出现误判，须分别在后面加个路径分隔符
        String srcPath = srcDir.getPath() + File.separator;
        String destPath = destDir.getPath() + File.separator;
        if (destPath.contains(srcPath)) return false;
        //源文件不存在或者不是目录则返回false
        if (!srcDir.exists() || !srcDir.isDirectory()) return false;
        //目标目录不存在返回false
        if (!createOrExistsDir(destDir)) return false;
        File[] files = srcDir.listFiles();
        for (File file : files) {
            File oneDestFile = new File(destPath + file.getName());
            if (file.isFile()) {
                //如果操作失败返回false
                if (!copyOrMoveFile(file, oneDestFile, isMove)) return false;
            } else if (file.isDirectory()) {
                //如果操作失败返回false
                if (!copyOrMoveDir(file, oneDestFile, isMove)) return false;
            }
        }
        return !isMove || deleteDir(srcDir);
    }

    /**
     * 复制或移动文件
     *
     * @param srcFilePath  源文件路径
     * @param destFilePath 目标文件路径
     * @param isMove       是否移动
     * @return {@code true}: 复制或移动成功<br>{@code false}: 复制或移动失败
     */
    private boolean copyOrMoveFile(String srcFilePath, String destFilePath, boolean isMove) {
        return copyOrMoveFile(getFileByPath(srcFilePath), getFileByPath(destFilePath), isMove);
    }

    /**
     * 复制或移动文件
     *
     * @param srcFile  源文件
     * @param destFile 目标文件
     * @param isMove   是否移动
     * @return {@code true}: 复制或移动成功<br>{@code false}: 复制或移动失败
     */
    private boolean copyOrMoveFile(File srcFile, File destFile, boolean isMove) {
        if (srcFile == null || destFile == null) return false;
        //源文件不存在或者不是文件则返回false
        if (!srcFile.exists() || !srcFile.isFile()) return false;
        //目标文件存在且是文件则返回false
        if (destFile.exists() && destFile.isFile()) return false;
        //目标目录不存在返回false
        if (!createOrExistsDir(destFile.getParentFile())) return false;
        try {
            return writeFileFromIS(destFile, new FileInputStream(srcFile), false)
                    && !(isMove && !deleteFile(srcFile));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 复制目录
     *
     * @param srcDirPath  源目录路径
     * @param destDirPath 目标目录路径
     * @return {@code true}: 复制成功<br>{@code false}: 复制失败
     */
    public boolean copyDir(String srcDirPath, String destDirPath) {
        return copyDir(getFileByPath(srcDirPath), getFileByPath(destDirPath));
    }

    /**
     * 复制目录
     *
     * @param srcDir  源目录
     * @param destDir 目标目录
     * @return {@code true}: 复制成功<br>{@code false}: 复制失败
     */
    public boolean copyDir(File srcDir, File destDir) {
        return copyOrMoveDir(srcDir, destDir, false);
    }

    /**
     * 复制文件
     *
     * @param srcFilePath  源文件路径
     * @param destFilePath 目标文件路径
     * @return {@code true}: 复制成功<br>{@code false}: 复制失败
     */
    public boolean copyFile(String srcFilePath, String destFilePath) {
        return copyFile(getFileByPath(srcFilePath), getFileByPath(destFilePath));
    }

    /**
     * 复制文件
     *
     * @param srcFile  源文件
     * @param destFile 目标文件
     * @return {@code true}: 复制成功<br>{@code false}: 复制失败
     */
    public boolean copyFile(File srcFile, File destFile) {
        return copyOrMoveFile(srcFile, destFile, false);
    }

    /**
     * 移动目录
     *
     * @param srcDirPath  源目录路径
     * @param destDirPath 目标目录路径
     * @return {@code true}: 移动成功<br>{@code false}: 移动失败
     */
    public boolean moveDir(String srcDirPath, String destDirPath) {
        return moveDir(getFileByPath(srcDirPath), getFileByPath(destDirPath));
    }

    /**
     * 移动目录
     *
     * @param srcDir  源目录
     * @param destDir 目标目录
     * @return {@code true}: 移动成功<br>{@code false}: 移动失败
     */
    public boolean moveDir(File srcDir, File destDir) {
        return copyOrMoveDir(srcDir, destDir, true);
    }

    /**
     * 移动文件
     *
     * @param srcFilePath  源文件路径
     * @param destFilePath 目标文件路径
     * @return {@code true}: 移动成功<br>{@code false}: 移动失败
     */
    public boolean moveFile(String srcFilePath, String destFilePath) {
        return moveFile(getFileByPath(srcFilePath), getFileByPath(destFilePath));
    }

    /**
     * 移动文件
     *
     * @param srcFile  源文件
     * @param destFile 目标文件
     * @return {@code true}: 移动成功<br>{@code false}: 移动失败
     */
    public boolean moveFile(File srcFile, File destFile) {
        return copyOrMoveFile(srcFile, destFile, true);
    }

    /**
     * 删除目录
     *
     * @param dirPath 目录路径
     * @return {@code true}: 删除成功<br>{@code false}: 删除失败
     */
    public boolean deleteDir(String dirPath) {
        return deleteDir(getFileByPath(dirPath));
    }

    /**
     * 删除目录
     *
     * @param dir 目录
     * @return {@code true}: 删除成功<br>{@code false}: 删除失败
     */
    public boolean deleteDir(File dir) {
        if (dir == null) return false;
        //目录不存在返回true
        if (!dir.exists()) return true;
        //不是目录返回false
        if (!dir.isDirectory()) return false;
        //现在文件存在且是文件夹
        File[] files = dir.listFiles();
        for (File file : files) {
            if (file.isFile()) {
                if (!deleteFile(file)) return false;
            } else if (file.isDirectory()) {
                if (!deleteDir(file)) return false;
            }
        }
        return dir.delete();
    }

    /**
     * 删除文件
     *
     * @param srcFilePath 文件路径
     * @return {@code true}: 删除成功<br>{@code false}: 删除失败
     */
    public boolean deleteFile(String srcFilePath) {
        return deleteFile(getFileByPath(srcFilePath));
    }

    /**
     * 删除文件
     *
     * @param file 文件
     * @return {@code true}: 删除成功<br>{@code false}: 删除失败
     */
    public boolean deleteFile(File file) {
        return file != null && (!file.exists() || file.isFile() && file.delete());
    }

    /**
     * 获取目录下所有文件
     *
     * @param dirPath     目录路径
     * @param isRecursive 是否递归进子目录
     * @return 文件链表
     */
    public List<File> listFilesInDir(String dirPath, boolean isRecursive) {
        return listFilesInDir(getFileByPath(dirPath), isRecursive);
    }

    /**
     * 获取目录下所有文件
     *
     * @param dir         目录
     * @param isRecursive 是否递归进子目录
     * @return 文件链表
     */
    public List<File> listFilesInDir(File dir, boolean isRecursive) {
        if (isRecursive) return listFilesInDir(dir);
        if (dir == null || !isDir(dir)) return null;
        List<File> list = new ArrayList<>();
        File[] files = dir.listFiles();
        Collections.addAll(list, files);
        return list;
    }

    /**
     * 获取目录下所有文件包括子目录
     *
     * @param dir 目录
     * @return 文件链表
     */
    public List<File> listFilesInDir(File dir) {
        if (dir == null || !isDir(dir)) return null;
        List<File> list = new ArrayList<>();
        File[] files = dir.listFiles();
        for (File file : files) {
            list.add(file);
            if (file.isDirectory()) {
                list.addAll(listFilesInDir(file));
            }
        }
        return list;
    }

    /**
     * 获取目录下所有后缀名为suffix的文件
     * <p>大小写忽略</p>
     *
     * @param dirPath     目录路径
     * @param isRecursive 是否递归进子目录
     * @return 文件链表
     */
    public List<File> listFilesInDirWithFilter(String dirPath, String suffix, boolean isRecursive) {
        return listFilesInDirWithFilter(getFileByPath(dirPath), suffix, isRecursive);
    }

    /**
     * 获取目录下所有后缀名为suffix的文件
     * <p>大小写忽略</p>
     *
     * @param dir         目录
     * @param isRecursive 是否递归进子目录
     * @return 文件链表
     */
    public List<File> listFilesInDirWithFilter(File dir, String suffix, boolean isRecursive) {
        if (isRecursive) return listFilesInDirWithFilter(dir, suffix);
        if (dir == null || !isDir(dir)) return null;
        List<File> list = new ArrayList<>();
        File[] files = dir.listFiles();
        for (File file : files) {
            if (file.getName().toUpperCase().endsWith(suffix.toUpperCase())) {
                list.add(file);
            }
        }
        return list;
    }

    /**
     * 获取目录下所有后缀名为suffix的文件包括子目录
     * <p>大小写忽略</p>
     *
     * @param dirPath 目录路径
     * @return 文件链表
     */
    public List<File> listFilesInDirWithFilter(String dirPath, String suffix) {
        return listFilesInDirWithFilter(getFileByPath(dirPath), suffix);
    }

    /**
     * 获取目录下所有后缀名为suffix的文件包括子目录
     * <p>大小写忽略</p>
     *
     * @param dir 目录
     * @return 文件链表
     */
    public List<File> listFilesInDirWithFilter(File dir, String suffix) {
        if (dir == null || !isDir(dir)) return null;
        List<File> list = new ArrayList<>();
        File[] files = dir.listFiles();
        for (File file : files) {
            if (file.getName().toUpperCase().endsWith(suffix.toUpperCase())) {
                list.add(file);
            }
            if (file.isDirectory()) {
                list.addAll(listFilesInDirWithFilter(file, suffix));
            }
        }
        return list;
    }

    /**
     * 获取目录下所有符合filter的文件
     *
     * @param dirPath     目录路径
     * @param isRecursive 是否递归进子目录
     * @return 文件链表
     */
    public List<File> listFilesInDirWithFilter(String dirPath, FilenameFilter filter, boolean isRecursive) {
        return listFilesInDirWithFilter(getFileByPath(dirPath), filter, isRecursive);
    }

    /**
     * 获取目录下所有符合filter的文件
     *
     * @param dir         目录
     * @param isRecursive 是否递归进子目录
     * @return 文件链表
     */
    public List<File> listFilesInDirWithFilter(File dir, FilenameFilter filter, boolean isRecursive) {
        if (isRecursive) return listFilesInDirWithFilter(dir, filter);
        if (dir == null || !isDir(dir)) return null;
        List<File> list = new ArrayList<>();
        File[] files = dir.listFiles();
        for (File file : files) {
            if (filter.accept(file.getParentFile(), file.getName())) {
                list.add(file);
            }
        }
        return list;
    }

    /**
     * 获取目录下所有符合filter的文件包括子目录
     *
     * @param dirPath 目录路径
     * @return 文件链表
     */
    public List<File> listFilesInDirWithFilter(String dirPath, FilenameFilter filter) {
        return listFilesInDirWithFilter(getFileByPath(dirPath), filter);
    }

    /**
     * 获取目录下所有符合filter的文件包括子目录
     *
     * @param dir 目录
     * @return 文件链表
     */
    public List<File> listFilesInDirWithFilter(File dir, FilenameFilter filter) {
        if (dir == null || !isDir(dir)) return null;
        List<File> list = new ArrayList<>();
        File[] files = dir.listFiles();
        for (File file : files) {
            if (filter.accept(file.getParentFile(), file.getName())) {
                list.add(file);
            }
            if (file.isDirectory()) {
                list.addAll(listFilesInDirWithFilter(file, filter));
            }
        }
        return list;
    }

    /**
     * 获取目录下指定文件名的文件包括子目录
     * <p>大小写忽略</p>
     *
     * @param dirPath 目录路径
     * @return 文件链表
     */
    public List<File> searchFileInDir(String dirPath, String fileName) {
        return searchFileInDir(getFileByPath(dirPath), fileName);
    }

    /**
     * 获取目录下指定文件名的文件包括子目录
     * <p>大小写忽略</p>
     *
     * @param dir 目录
     * @return 文件链表
     */
    public List<File> searchFileInDir(File dir, String fileName) {
        if (dir == null || !isDir(dir)) return null;
        List<File> list = new ArrayList<>();
        File[] files = dir.listFiles();
        for (File file : files) {
            if (file.getName().toUpperCase().equals(fileName.toUpperCase())) {
                list.add(file);
            }
            if (file.isDirectory()) {
                list.addAll(listFilesInDirWithFilter(file, fileName));
            }
        }
        return list;
    }

    /**
     * 将输入流写入文件
     *
     * @param filePath 路径
     * @param is       输入流
     * @param append   是否追加在文件末
     * @return {@code true}: 写入成功<br>{@code false}: 写入失败
     */
    public boolean writeFileFromIS(String filePath, InputStream is, boolean append) {
        return writeFileFromIS(getFileByPath(filePath), is, append);
    }

    /**
     * 将输入流写入文件
     *
     * @param file   文件
     * @param is     输入流
     * @param append 是否追加在文件末
     * @return {@code true}: 写入成功<br>{@code false}: 写入失败
     */
    public boolean writeFileFromIS(File file, InputStream is, boolean append) {
        if (file == null || is == null) return false;
        if (!createOrExistsFile(file)) return false;
        OutputStream os = null;
        try {
            os = new BufferedOutputStream(new FileOutputStream(file, append));
            byte data[] = new byte[ConstUT.getInstance().KB];
            int len;
            while ((len = is.read(data)) != -1) {
                os.write(data, 0, len);
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            closeIO(is, os);
        }
    }

    /**
     * 将字符串写入文件
     *
     * @param filePath 文件路径
     * @param content  写入内容
     * @param append   是否追加在文件末
     * @return {@code true}: 写入成功<br>{@code false}: 写入失败
     */
    public boolean writeFileFromString(String filePath, String content, boolean append) {
        return writeFileFromString(getFileByPath(filePath), content, append);
    }

    /**
     * 将字符串写入文件
     *
     * @param file    文件
     * @param content 写入内容
     * @param append  是否追加在文件末
     * @return {@code true}: 写入成功<br>{@code false}: 写入失败
     */
    public boolean writeFileFromString(File file, String content, boolean append) {
        if (file == null || content == null) return false;
        if (!createOrExistsFile(file)) return false;
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(file, append);
            fileWriter.write(content);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            closeIO(fileWriter);
        }
    }

    /**
     * 简单获取文件编码格式
     *
     * @param filePath 文件路径
     * @return 文件编码
     */
    public String getFileCharsetSimple(String filePath) {
        return getFileCharsetSimple(getFileByPath(filePath));
    }

    /**
     * 简单获取文件编码格式
     *
     * @param file 文件
     * @return 文件编码
     */
    public String getFileCharsetSimple(File file) {
        int p = 0;
        InputStream is = null;
        try {
            is = new BufferedInputStream(new FileInputStream(file));
            p = (is.read() << 8) + is.read();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            closeIO(is);
        }
        switch (p) {
            case 0xefbb:
                return "UTF-8";
            case 0xfffe:
                return "Unicode";
            case 0xfeff:
                return "UTF-16BE";
            default:
                return "GBK";
        }
    }

    /**
     * 获取文件行数
     *
     * @param filePath 文件路径
     * @return 文件行数
     */
    public int getFileLines(String filePath) {
        return getFileLines(getFileByPath(filePath));
    }

    /**
     * 获取文件行数
     *
     * @param file 文件
     * @return 文件行数
     */
    public int getFileLines(File file) {
        int count = 1;
        InputStream is = null;
        try {
            is = new BufferedInputStream(new FileInputStream(file));
            byte[] buffer = new byte[ConstUT.getInstance().KB];
            int readChars;
            while ((readChars = is.read(buffer)) != -1) {
                for (int i = 0; i < readChars; ++i) {
                    if (buffer[i] == '\n') ++count;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            closeIO(is);
        }
        return count;
    }

    /**
     * 指定编码按行读取文件到List
     *
     * @param filePath    文件路径
     * @param charsetName 编码格式
     * @return 文件行链表
     */
    public List<String> readFile2List(String filePath, String charsetName) {
        return readFile2List(getFileByPath(filePath), charsetName);
    }

    /**
     * 指定编码按行读取文件到List
     *
     * @param file        文件
     * @param charsetName 编码格式
     * @return 文件行链表
     */
    public List<String> readFile2List(File file, String charsetName) {
        return readFile2List(file, 0, 0x7FFFFFFF, charsetName);
    }

    /**
     * 指定编码按行读取文件到List
     *
     * @param filePath    文件路径
     * @param st          需要读取的开始行数
     * @param end         需要读取的结束行数
     * @param charsetName 编码格式
     * @return 包含制定行的list
     */
    public List<String> readFile2List(String filePath, int st, int end, String
            charsetName) {
        return readFile2List(getFileByPath(filePath), st, end, charsetName);
    }

    /**
     * 指定编码按行读取文件到List
     *
     * @param file        文件
     * @param st          需要读取的开始行数
     * @param end         需要读取的结束行数
     * @param charsetName 编码格式
     * @return 包含从start行到end行的list
     */
    public List<String> readFile2List(File file, int st, int end, String charsetName) {
        if (file == null) return null;
        if (st > end) return null;
        BufferedReader reader = null;
        try {
            String line;
            int curLine = 1;
            List<String> list = new ArrayList<>();
            if (ValidityUT.getInstance().isEmpty(charsetName)) {
                reader = new BufferedReader(new FileReader(file));
            } else {
                reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), charsetName));
            }
            while ((line = reader.readLine()) != null) {
                if (curLine > end) break;
                if (st <= curLine && curLine <= end) list.add(line);
                ++curLine;
            }
            return list;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            closeIO(reader);
        }
    }

    /**
     * 指定编码按行读取文件到字符串中
     *
     * @param filePath    文件路径
     * @param charsetName 编码格式
     * @return 字符串
     */
    public String readFile2String(String filePath, String charsetName) {
        return readFile2String(getFileByPath(filePath), charsetName);
    }

    /**
     * 指定编码按行读取文件到字符串中
     *
     * @param file        文件
     * @param charsetName 编码格式
     * @return 字符串
     */
    public String readFile2String(File file, String charsetName) {
        if (file == null) return null;
        BufferedReader reader = null;
        try {
            StringBuilder sb = new StringBuilder();
            if (ValidityUT.getInstance().isEmpty(charsetName)) {
                reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            } else {
                reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), charsetName));
            }
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\r\n"); //windows系统换行为\r\n，Linux为\n
            }
            //要去除最后的换行符
            return sb.delete(sb.length() - 2, sb.length()).toString();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            closeIO(reader);
        }
    }

    /**
     * 指定编码按行读取文件到字符串中
     *
     * @param filePath 文件路径
     * @return StringBuilder对象
     */
    public byte[] readFile2Bytes(String filePath) {
        return readFile2Bytes(getFileByPath(filePath));
    }

    /**
     * 指定编码按行读取文件到字符串中
     *
     * @param file 文件
     * @return StringBuilder对象
     */
    public byte[] readFile2Bytes(File file) {
        if (file == null) return null;
        try {
            return ConvertUT.getInstance().inputStream2Bytes(new FileInputStream(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 字节数转以unit为单位的size
     *
     * @param byteNum 大小
     * @param unit    <ul>
     *                <li>{@link ConstUT.MemoryUnit#BYTE}: 字节</li>
     *                <li>{@link ConstUT.MemoryUnit#KB}  : 千字节</li>
     *                <li>{@link ConstUT.MemoryUnit#MB}  : 兆</li>
     *                <li>{@link ConstUT.MemoryUnit#GB}  : GB</li>
     *                </ul>
     * @return 以unit为单位的size
     */
    public double byte2Size(long byteNum, ConstUT.MemoryUnit unit) {
        switch (unit) {
            default:
            case BYTE:
                return (double) byteNum / ConstUT.getInstance().BYTE;
            case KB:
                return (double) byteNum / ConstUT.getInstance().KB;
            case MB:
                return (double) byteNum / ConstUT.getInstance().MB;
            case GB:
                return (double) byteNum / ConstUT.getInstance().GB;
        }
    }

    /**
     * 以unit为单位的size转字节数
     *
     * @param size 大小
     * @param unit <ul>
     *             <li>{@link ConstUT.MemoryUnit#BYTE}: 字节</li>
     *             <li>{@link ConstUT.MemoryUnit#KB}  : 千字节</li>
     *             <li>{@link ConstUT.MemoryUnit#MB}  : 兆</li>
     *             <li>{@link ConstUT.MemoryUnit#GB}  : GB</li>
     *             </ul>
     * @return 字节数
     */
    public long size2Byte(long size, ConstUT.MemoryUnit unit) {
        switch (unit) {
            default:
            case BYTE:
                return size * ConstUT.getInstance().BYTE;
            case KB:
                return size * ConstUT.getInstance().KB;
            case MB:
                return size * ConstUT.getInstance().MB;
            case GB:
                return size * ConstUT.getInstance().GB;
        }
    }

    /**
     * 获取文件大小
     * <p>例如：getFileSize(filePath, ConstUT.MB); 返回文件大小单位为MB</p>
     *
     * @param filePath 文件路径
     * @param unit     <ul>
     *                 <li>{@link ConstUT.MemoryUnit#BYTE}: 字节</li>
     *                 <li>{@link ConstUT.MemoryUnit#KB}  : 千字节</li>
     *                 <li>{@link ConstUT.MemoryUnit#MB}  : 兆</li>
     *                 <li>{@link ConstUT.MemoryUnit#GB}  : GB</li>
     *                 </ul>
     * @return 文件大小以unit为单位
     */
    public double getFileSize(String filePath, ConstUT.MemoryUnit unit) {
        return getFileSize(getFileByPath(filePath), unit);
    }

    /**
     * 获取文件大小
     * <p>例如：getFileSize(file, ConstUT.MB); 返回文件大小单位为MB</p>
     *
     * @param file 文件
     * @param unit <ul>
     *             <li>{@link ConstUT.MemoryUnit#BYTE}: 字节</li>
     *             <li>{@link ConstUT.MemoryUnit#KB}  : 千字节</li>
     *             <li>{@link ConstUT.MemoryUnit#MB}  : 兆</li>
     *             <li>{@link ConstUT.MemoryUnit#GB}  : GB</li>
     *             </ul>
     * @return 文件大小以unit为单位
     */
    public double getFileSize(File file, ConstUT.MemoryUnit unit) {
        if (!isFileExists(file)) return -1;
        return byte2Size(file.length(), unit);
    }

    /**
     * 关闭IO
     *
     * @param closeables closeable
     */
    public void closeIO(Closeable... closeables) {
        if (closeables == null) return;
        try {
            for (Closeable closeable : closeables) {
                if (closeable != null) {
                    closeable.close();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取全路径中的最长目录
     *
     * @param file 文件
     * @return filePath最长目录
     */
    public String getDirName(File file) {
        if (file == null) return null;
        return getDirName(file.getPath());
    }

    /**
     * 获取全路径中的最长目录
     *
     * @param filePath 文件路径
     * @return filePath最长目录
     */
    public String getDirName(String filePath) {
        if (ValidityUT.getInstance().isEmpty(filePath)) return filePath;
        int lastSep = filePath.lastIndexOf(File.separator);
        return lastSep == -1 ? "" : filePath.substring(0, lastSep + 1);
    }

    /**
     * 获取全路径中的文件名
     *
     * @param file 文件
     * @return 文件名
     */
    public String getFileName(File file) {
        if (file == null) return null;
        return getFileName(file.getPath());
    }

    /**
     * 获取全路径中的文件名
     *
     * @param filePath 文件路径
     * @return 文件名
     */
    public String getFileName(String filePath) {
        if (ValidityUT.getInstance().isEmpty(filePath)) return filePath;
        int lastSep = filePath.lastIndexOf(File.separator);
        return lastSep == -1 ? filePath : filePath.substring(lastSep + 1);
    }

    /**
     * 获取全路径中的不带拓展名的文件名
     *
     * @param file 文件
     * @return 不带拓展名的文件名
     */
    public String getFileNameNoExtension(File file) {
        if (file == null) return null;
        return getFileNameNoExtension(file.getPath());
    }

    /**
     * 获取全路径中的不带拓展名的文件名
     *
     * @param filePath 文件路径
     * @return 不带拓展名的文件名
     */
    public String getFileNameNoExtension(String filePath) {
        if (ValidityUT.getInstance().isEmpty(filePath)) return filePath;
        int lastPoi = filePath.lastIndexOf('.');
        int lastSep = filePath.lastIndexOf(File.separator);
        if (lastSep == -1) {
            return (lastPoi == -1 ? filePath : filePath.substring(0, lastPoi));
        }
        if (lastPoi == -1 || lastSep > lastPoi) {
            return filePath.substring(lastSep + 1);
        }
        return filePath.substring(lastSep + 1, lastPoi);
    }


    /**
     * 获取全路径中的文件拓展名
     *
     * @param file 文件
     * @return 文件拓展名
     */
    public String getFileExtension(File file) {
        if (file == null) return null;
        return getFileExtension(file.getPath());
    }

    /**
     * 获取全路径中的文件拓展名
     *
     * @param filePath 文件路径
     * @return 文件拓展名
     */
    public String getFileExtension(String filePath) {
        if (ValidityUT.getInstance().isEmpty(filePath)) return filePath;
        int lastPoi = filePath.lastIndexOf('.');
        int lastSep = filePath.lastIndexOf(File.separator);
        if (lastPoi == -1 || lastSep >= lastPoi) return "";
        return filePath.substring(lastPoi);
    }
}
