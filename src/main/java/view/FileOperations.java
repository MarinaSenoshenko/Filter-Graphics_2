package view;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.util.Objects;

import static java.net.URLDecoder.decode;
import static java.nio.charset.Charset.defaultCharset;
import static javax.swing.JFileChooser.APPROVE_OPTION;

public class FileOperations {
	private static File dataDirectory = null;

	public static File getDataDirectory() {
		if (dataDirectory == null) {
			String path = decode(Frame.class.getProtectionDomain().getCodeSource().getLocation().getFile(), defaultCharset());
			dataDirectory = new File(path).getParentFile();
			if (dataDirectory == null || !dataDirectory.exists()) dataDirectory = new File(".");
			for (File file: Objects.requireNonNull(dataDirectory.listFiles())) {
				if (file.isDirectory() && file.getName().endsWith("_Data")) {
					dataDirectory = file;
					break;
				}
			}
		}
		return dataDirectory;
	}

	public static File getOpenImageFileName(JFrame parent) {
		JFileChooser fileChooser = new JFileChooser();
		FileFilter fileFilter = new FileNameExtensionFilter("PNG, JPEG, BMP, GIF", "png", "jpeg", "bmp", "gif");
		fileChooser.addChoosableFileFilter(fileFilter);
		fileChooser.setCurrentDirectory(getDataDirectory());
		if (fileChooser.showOpenDialog(parent) == APPROVE_OPTION) {
			File file = fileChooser.getSelectedFile();
			if (!file.getName().contains(".")) {
				file = new File(file.getParent(), file.getName() + ".png");
			}
			return file;
		}
		return null;
	}

	public static File getSaveImageFileName(JFrame parent) {
		JFileChooser fileChooser = new JFileChooser();
		FileFilter fileFilter = new FileNameExtensionFilter("png", "png");
		fileChooser.addChoosableFileFilter(fileFilter);
		fileChooser.setAcceptAllFileFilterUsed(false);
		fileChooser.setCurrentDirectory(getDataDirectory());
		if (fileChooser.showSaveDialog(parent) == APPROVE_OPTION) {
			File file = fileChooser.getSelectedFile();
			if (!file.getName().contains(".")) {
				file = new File(file.getParent(), file.getName() + ".png");
			}
			return file;
		}
		return null;
	}
}
