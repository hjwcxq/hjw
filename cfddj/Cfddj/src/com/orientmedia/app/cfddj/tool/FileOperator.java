package com.orientmedia.app.cfddj.tool;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import com.orientmedia.app.cfddj.task.FileDownTask;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

/**
 * FileOperateHelper<br>
 * 文件操作类.
 */
public class FileOperator {
	
	public static final String ROOT_FOLDER_NAME = "cfddj";
	
	public static final String INDEX_XML_FOLDER_NAME = "index";
	
	public static final String IMAGE_RES_FOLDER_NAME = "image";
	
	public static final String VIDEO_RES_FOLDER_NAME = "video";
	
	public static final String VOICE_RES_FOLDER_NAME = "voice";
	
	public static final String APK_RES_FOLDER_NAME = "apk";
	
	

    /**
     * 创建文件夹<br>
     * 若上级文件夹不存在则一并创建之.<br>
     * 
     * <pre><code>
     * pathName
     * </code>
     * 中涉及的文件路径分割符使用
     * <code>
     * File.separator
     * </code></pre>
     * 
     * @param pathName
     *            指定的文件夹路径.
     */
    public static void newFolder(String pathName) {
        try {
            File fileFolder = new File(pathName);
            if (!fileFolder.exists()) 
                fileFolder.mkdirs();
        } catch (Exception e) {
            System.out.println("*****Problem Creating new Folder*****"
                    + pathName + "*****");
            e.printStackTrace();
        }
    }

    /**
     * 在已经尊在的文件夹中创建指定名称的文件<br>
     * 
     * <pre><code>
     * pathName
     * </code>
     * 中涉及的文件路径分割符使用
     * <code>
     * File.separator
     * </code></pre>
     * 
     * @param pathToName
     *            文件夹路径及名称.
     * @param name
     *            需要被创建的文件名称.
     */
    public static void newFile(String pathToName, String name) {
        try {
            File file = new File(pathToName + File.separator + name);
            if (file.exists()) {
                file.delete();
            } else {
                file.createNewFile();
            }
        } catch (Exception e) {
            System.out.println("*****Problem Creating new File*****"
                    + pathToName + File.separator + name + "*****");
            e.printStackTrace();
        }
    }

    /**
     * 创建指定文件夹及其下指定名称的文件<br>
     * 
     * <pre><code>
     * pathName
     * </code>
     * 中涉及的文件路径分割符使用
     * <code>
     * File.separator
     * </code></pre>
     * 
     * 若上级文件夹不存在则一并创建之.
     * 
     * @param pathToName
     *            需要被创建的文件夹路径及名称.
     * @param name
     *            需要被创建的文件名称.
     */
    public static void newFolderAndFile(String pathToName, String name) {
        try {
            newFolder(pathToName);
            newFile(pathToName, name);
        } catch (Exception e) {
            System.out.println("*****Problem Creating new FolderAndFile*****"
                    + pathToName + File.separator + name + "*****");
            e.printStackTrace();
        }
    }

    /**
     * 将二进制流写入指定目录及名称的文件<br>
     * 调用该方法之前 目标文件必须已经存在(即使没有内容).
     * 
     * <pre><code>
     * pathName
     * </code>
     * 中涉及的文件路径分割符使用
     * <code>
     * File.separator
     * </code></pre>
     * 
     * @param input
     *            输入流.
     * @param filePathAndName
     *            指定的文件路径及名称.
     */
    public static void outBinaryFile(FileInputStream input,
            String filePathAndName) {
        try {
            FileOutputStream outFile = new FileOutputStream(filePathAndName);
            byte[] bytes = new byte[1024];
            int n;
            try {
                while ((n = input.read(bytes, 0, 1024)) != -1) 
                    outFile.write(bytes, 0, n);
                outFile.close();
                outFile = null;
                bytes = null;
            } catch (IOException e) {
                System.out
                        .println("*****Problem BinaryDataOut,Bytes Array*****");
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            System.out.println("*****Problem BinaryDataOut,File "
                    + filePathAndName + "Not Found*****");
            e.printStackTrace();
        }
        try {
            input.close();
        } catch (IOException e) {
            System.out.println("*****Problem Close input*****");
            e.printStackTrace();
        }
    }
    
    public static File getDzlcRootFolder(){
    	String rootPath = Environment.getExternalStorageDirectory()
				+ File.separator + ROOT_FOLDER_NAME;
    	File file = new File(rootPath);
    	if(!file.exists())
    		file.mkdirs();
    	return file;
    }
    
    
    /**
     * 获取XmlFile  的Folder
     */
    public static File getCacheIndexXmlFile(){
    	File file = getDzlcRootFolder();
    	File xmlFile = new File(file,INDEX_XML_FOLDER_NAME);
    	if(!xmlFile.exists())
    		xmlFile.mkdirs();
    	return xmlFile;
    }
    
    /**
     * 获取图片folder
     */
    public static File geCacheBitmapFile(){
    	File file = getDzlcRootFolder();
    	File bitmapFile = new File(file,INDEX_XML_FOLDER_NAME);
    	if(!bitmapFile.exists())
    		bitmapFile.mkdirs();
    	return bitmapFile;
    }
    
    /**
     * 获取视频的 folder
     */
    public static File geCacheVideoFile(){
    	File file = getDzlcRootFolder();
    	File videoFile = new File(file,VIDEO_RES_FOLDER_NAME);
    	if(!videoFile.exists())
    		videoFile.mkdirs();
    	return videoFile;
    }
    
    public static File getCacheVoiceFile(){
    	File file = getDzlcRootFolder();
    	File voiceFile = new File(file,VOICE_RES_FOLDER_NAME);
    	if(!voiceFile.exists())
    		voiceFile.mkdirs();
    	return voiceFile;
    }
    
    public static File getCacheApkFile(){
    	File file = getDzlcRootFolder();
    	File apkFile = new File(file,APK_RES_FOLDER_NAME);
    	if(!apkFile.exists())
    		apkFile.mkdirs();
    	return apkFile;
    }
    
    
    
    public static void deleteAllCache() {
        File newFile = new File(Environment.getExternalStorageDirectory() + ROOT_FOLDER_NAME);
        deleteCache(newFile);
    }
    
    private static void deleteCache(File file) {
        if (file.exists()) {
            if (file.isFile()) {
                file.delete();
            }
            if (file.isDirectory()) {
                final File[] files = file.listFiles();
                for (int i = 0; i < files.length; i++) {
                    deleteCache(files[i]);
                }
            }
        }
    }


    public static boolean saveFileToSdCard(InputStream input, String fileName,String fileType) {
        boolean result = false;
        int BUFFER_SIZE = 1024 * 4;
        FileOutputStream output = null;
        byte[] buffer = new byte[BUFFER_SIZE];
        try {
        	File file = null ;
        	
        	if(INDEX_XML_FOLDER_NAME.equalsIgnoreCase(fileType)){
        		file = getCacheIndexXmlFile() ;
        	}else if(IMAGE_RES_FOLDER_NAME.equalsIgnoreCase(fileName)){
        		file = geCacheBitmapFile() ;
        	}else if(APK_RES_FOLDER_NAME.equalsIgnoreCase(fileType)){
        		file = getCacheApkFile();
        	}
            File newFile = new File(file,fileName);
            
            output = new FileOutputStream(newFile);
            int count = 0;
            while ((count = input.read(buffer, 0, BUFFER_SIZE)) != -1) {
                output.write(buffer, 0, count);
            }
            output.flush();
            result = true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            IOUtilities.closeStream(output);
        }
        return result;
    }

    public static boolean isImageFileCached(String picName) {
        final File file = new File(geCacheBitmapFile(), picName);
        return file.exists();
    }

    public static Bitmap loadImageFromSDCard(String picName) {
        FileInputStream fileInputStream = loadImageStreamFromSDCard(picName);
        Bitmap bitmap = null;
        try {
            bitmap = BitmapFactory.decodeStream(fileInputStream, null, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        IOUtilities.closeStream(fileInputStream);
        return bitmap;
    }
    
    public static FileInputStream loadImageStreamFromSDCard(String picName) {
        File file = new File(geCacheBitmapFile(),picName);
        if (file.exists()) {
            InputStream stream = null;
            try {
                stream = new FileInputStream(file);
                return (FileInputStream) stream;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } 
        }
        return null;
    }
    
    
    /**
     * 根据url获取缓存的文件
     */
    
    public static File getFileByUrl(String url,String fileType){
    	if(url == null || fileType == null)
    		return null;
    	File file = null ;
    	if(FileDownTask.IMAGE_TYPE.equalsIgnoreCase(fileType)){
    		file = geCacheBitmapFile() ;
    	}else if(FileDownTask.VOICE_TYPE.equalsIgnoreCase(fileType)){
    		file = getCacheVoiceFile();
    	}else if(FileDownTask.XML_TYPE.equalsIgnoreCase(fileType)){
    		file = getCacheIndexXmlFile();
    	}
    	String fileName  = "";
    	if(FileDownTask.XML_TYPE.equalsIgnoreCase(fileType)){
    		fileName = "index.xml" + ".tmp" ;
    	}else{
    		fileName = url.substring(url.lastIndexOf("/")+1)  ;
    	}
    	
    	if(file != null)
    		return new File(file, fileName);
    	return null;

    }

}
