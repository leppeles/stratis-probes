package common;

import java.util.List;
import java.util.ListIterator;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.*;

@SuppressWarnings("unused")
public class Test {

	public static void main(String[] args) throws Exception {

		String saveParentDir = "C:\\Users\\toszi\\Desktop\\Crawler";
		String fileURL = "https://docs.oracle.com/javase/7/docs/api/java/nio/file/Files.html";
		URL url = new URL(fileURL);
		String fileSubPath = url.getPath().toString();
		fileSubPath = (fileSubPath.substring(0, fileSubPath.lastIndexOf("/") + 1));
		fileSubPath.replace("/", File.separator);
		System.out.println(fileSubPath);

		File directory = new File(saveParentDir + fileSubPath);
		System.out.println(directory.mkdirs());
	}
}