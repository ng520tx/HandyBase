package com.handy.base.utils;

import android.os.Environment;
import android.os.StatFs;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * SD卡文件管理工具类
 * <p>
 * Created by LiuJie on 2016/1/12.
 */
public class SDCardUT {
    private static SDCardUT sdCardUT = null;
    private String SDCardRoot;
    private String SDStateString;

    private SDCardUT() {
        //得到当前外部存储设备的目录
        SDCardRoot = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator;
        //获取扩展SD卡设备状态
        SDStateString = Environment.getExternalStorageState();
    }

    public synchronized static SDCardUT getInstance() {
        if (sdCardUT == null) {
            sdCardUT = new SDCardUT();
        }
        return sdCardUT;
    }

    /**
     * 判断SD卡是否可用
     *
     * @return true : 可用<br>false : 不可用
     */
    public boolean isSDCardEnable() {
        return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
    }

    /**
     * 获取SD卡路径
     * <p>一般是/storage/emulated/0/</p>
     *
     * @return SD卡路径
     */
    public String getSDCardPath() {
        return Environment.getExternalStorageDirectory().getPath() + File.separator;
    }

    /**
     * 获取SD卡Data路径
     *
     * @return Data路径
     */
    public String getDataPath() {
        return Environment.getDataDirectory().getPath();
    }

    /**
     * 在SD卡上创建文件
     *
     * @param dir      目录路径
     * @param fileName 文件名
     * @return
     * @throws IOException
     */
    public File createFileInSDCard(String dir, String fileName) throws IOException {
        File file = new File(SDCardRoot + dir + File.separator + fileName);
        file.createNewFile();
        return file;
    }

    /**
     * 在SD卡上创建目录
     *
     * @param dir 目录路径
     * @return 目录文件夹
     */
    public File creatSDDir(String dir) {
        File dirFile = new File(SDCardRoot + dir + File.separator);
        if (!dirFile.exists()) {
            dirFile.mkdirs();
        }
        return dirFile;
    }

    /**
     * 判断SD卡上的文件夹是否存在
     *
     * @param dir      目录路径
     * @param fileName 文件名称
     * @return
     */
    public boolean isFileExist(String dir, String fileName) {
        File file = new File(SDCardRoot + dir + File.separator + fileName);
        return file.exists();
    }

    /**
     * 计算SD卡的剩余空间
     *
     * @param unit <ul>
     *             <li>{@link ConstUT.MemoryUnit#BYTE}: 字节</li>
     *             <li>{@link ConstUT.MemoryUnit#KB}  : 千字节</li>
     *             <li>{@link ConstUT.MemoryUnit#MB}  : 兆</li>
     *             <li>{@link ConstUT.MemoryUnit#GB}  : GB</li>
     *             </ul>
     * @return 返回-1，说明SD卡不可用，否则返回SD卡剩余空间
     */
    public double getFreeSpace(ConstUT.MemoryUnit unit) {
        if (isSDCardEnable()) {
            try {
                StatFs stat = new StatFs(getSDCardPath());
                long blockSize, availableBlocks;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR2) {
                    availableBlocks = stat.getAvailableBlocksLong();
                    blockSize = stat.getBlockSizeLong();
                } else {
                    availableBlocks = stat.getAvailableBlocks();
                    blockSize = stat.getBlockSize();
                }
                return FileUT.getInstance().byte2Size(availableBlocks * blockSize, unit);
            } catch (Exception e) {
                e.printStackTrace();
                return -1.0;
            }
        } else {
            return -1.0;
        }
    }

    /**
     * 获取指定路径所在空间的剩余可用容量字节数，单位byte
     *
     * @param filePath
     * @return 容量字节 SDCard可用空间，内部存储可用空间
     */
    public long getFreeBytes(String filePath) {
        //如果是sd卡的下的路径，则获取sd卡可用容量
        if (filePath.startsWith(getSDCardPath())) {
            filePath = getSDCardPath();
        } else { //如果是内部存储的路径，则获取内存存储的可用容量
            filePath = Environment.getDataDirectory().getAbsolutePath();
        }
        StatFs stat = new StatFs(filePath);
        long availableBlocks = (long) stat.getAvailableBlocks() - 4;
        return stat.getBlockSize() * availableBlocks;
    }

    /**
     * 获取系统存储路径
     *
     * @return
     */
    public String getRootDirectoryPath() {
        return Environment.getRootDirectory().getAbsolutePath();
    }

    /**
     * 获取文件的路径
     *
     * @param dir      目录路径
     * @param fileName 文件名称
     * @return 目标文件存储路径
     */
    public String getFilePath(String dir, String fileName) {
        return SDCardRoot + dir + File.separator + fileName;
    }

    /**
     * 获取SD卡的剩余容量,单位是Byte
     *
     * @return
     */
    public long getSDAvailableSize() {
        if (SDStateString.equals(Environment.MEDIA_MOUNTED)) {
            //取得sdcard文件路径
            File pathFile = Environment.getExternalStorageDirectory();
            StatFs statfs = new StatFs(pathFile.getPath());
            //获取SDCard上每个block的SIZE
            long nBlocSize = statfs.getBlockSize();
            //获取可供程序使用的Block的数量
            long nAvailaBlock = statfs.getAvailableBlocks();
            //计算 SDCard 剩余大小Byte
            long nSDFreeSize = nAvailaBlock * nBlocSize;
            return nSDFreeSize;
        }
        return 0;
    }

    /**
     * 将一个字节数组数据写入到SD卡中
     *
     * @param dir      目录路径
     * @param fileName 文件名称
     * @param bytes    字节数组
     * @return
     */
    public boolean writeSD(String dir, String fileName, byte[] bytes) {
        if (bytes == null) {
            return false;
        }
        OutputStream output = null;
        try {
            //拥有可读可写权限，并且有足够的容量
            if (SDStateString.equals(Environment.MEDIA_MOUNTED) && bytes.length < getSDAvailableSize()) {
                File file = null;
                creatSDDir(dir);
                file = createFileInSDCard(dir, fileName);
                output = new BufferedOutputStream(new FileOutputStream(file));
                output.write(bytes);
                output.flush();
                return true;
            }
        } catch (IOException e1) {
            e1.printStackTrace();
        } finally {
            try {
                if (output != null) {
                    output.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * 从sd卡中读取文件，并且以字节流返回
     *
     * @param dir      目录路径
     * @param fileName 文件名称
     * @return
     */
    public byte[] readFromSD(String dir, String fileName) {
        File file = new File(SDCardRoot + dir + File.separator + fileName);
        if (!file.exists()) {
            return null;
        }
        InputStream inputStream = null;
        try {
            inputStream = new BufferedInputStream(new FileInputStream(file));
            byte[] data = new byte[inputStream.available()];
            inputStream.read(data);
            return data;
        } catch (FileNotFoundException e) {
            //TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            //TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                //TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 将一个InputStream里面的数据写入到SD卡中
     *
     * @param dir      目录路径
     * @param fileName 文件名称
     * @param input    输入流
     * @return
     */
    public File writeSDFromInput(String dir, String fileName, InputStream input) {
        File file = null;
        OutputStream output = null;
        try {
            int size = input.available();
            //拥有可读可写权限，并且有足够的容量
            if (SDStateString.equals(Environment.MEDIA_MOUNTED) && size < getSDAvailableSize()) {
                creatSDDir(dir);
                file = createFileInSDCard(dir, fileName);
                output = new BufferedOutputStream(new FileOutputStream(file));
                byte buffer[] = new byte[41024];
                int temp;
                while ((temp = input.read(buffer)) != -1) {
                    output.write(buffer, 0, temp);
                }
                output.flush();
            }
        } catch (IOException e1) {
            e1.printStackTrace();
        } finally {
            try {
                if (output != null) {
                    output.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return file;
    }
}
