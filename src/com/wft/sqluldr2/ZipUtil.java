package com.wft.sqluldr2;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;

import org.apache.commons.compress.archivers.zip.Zip64Mode;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

public class ZipUtil {

	private final static Logger log = Logger.getLogger(ZipUtil.class);

	/**
	 * @param zipFileName
	 * @param files
	 * @param encoding
	 * @param deleteSrcFileFlag
	 */
	public static void zip(String zipFileName, List<File> files,
			String encoding, boolean deleteSrcFileFlag) {
		long startInt = System.currentTimeMillis();
		if (files == null)
			return;

		InputStream inputStream = null;
		ZipArchiveOutputStream zipArchiveOutputStream = null;
		try {
			zipArchiveOutputStream = new ZipArchiveOutputStream(new File(
					zipFileName));
			zipArchiveOutputStream.setUseZip64(Zip64Mode.AsNeeded);
			for (File file : files) {
				log.info("------------------zipArchiveEntry, fileName: 【{}】"
						+ file.getName());
				// 将每个文件用ZipArchiveEntry封装，使用ZipArchiveOutputStream写到压缩文件
				ZipArchiveEntry zipArchiveEntry = new ZipArchiveEntry(file,
						file.getName());
				zipArchiveOutputStream.putArchiveEntry(zipArchiveEntry);
				zipArchiveOutputStream.setEncoding(encoding);

				inputStream = new FileInputStream(file);
				byte[] buffer = new byte[1024 * 5];
				int len = -1;
				while ((len = inputStream.read(buffer)) != -1) {
					// 把缓冲区的字节写入到ZipArchiveEntry
					zipArchiveOutputStream.write(buffer, 0, len);
				}
				// 在此处关闭inputStream流。如果在finally块中关闭流，则只会关闭最后一个流对象
				IOUtils.closeQuietly(inputStream);
			}
			zipArchiveOutputStream.closeArchiveEntry();
			zipArchiveOutputStream.flush();
			zipArchiveOutputStream.finish();
		} catch (Exception e) {
			log.error("", e);
		} finally {
			IOUtils.closeQuietly(inputStream);
			IOUtils.closeQuietly(zipArchiveOutputStream);
			// 如果不删除，直接return
			if (!deleteSrcFileFlag)
				return;
			for (int i = 0; i < files.size(); i++) {
				files.get(i).delete();
			}
		}

		long time = (System.currentTimeMillis() - startInt) / 1000;
		log.info(String.format("文件压缩耗时：{%s}秒", time));

	}
}
