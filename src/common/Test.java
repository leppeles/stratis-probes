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
		System.out.println(File.separator);
		
		String dirSubPath = "////////";
		dirSubPath.replace("/", File.pathSeparator);
		System.out.println(dirSubPath);
	}
}