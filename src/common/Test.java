package common;

import java.util.List;
import java.util.ListIterator;
import java.util.Locale;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import superclass.Subc;
import superclass.SubcImpl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.*;

@SuppressWarnings("unused")
public class Test {

	public static void main(String[] args) throws Exception {
		SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		
		System.out.println(dateformat.format(new Date()) + " " + Test.class);

			StringBuffer logContent = new StringBuffer();
			logContent.append( dateformat.format(new Date()) + " - Run started");
			logContent.append("\nNumber of matches: " + ((int)(Math.random()*90) + 10));
			logContent.append("\n" + dateformat.format(new Date()) + " - Run terminated");
			File logFile = new File("C:\\Users\\toszi\\Desktop\\Crawler\\custome_message_log_filename.log");
			// if file doesnt exists, then create it
			try {
			if (!logFile.exists()) {
				logFile.createNewFile();
			}
			FileWriter fw = new FileWriter(logFile.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(logContent.toString());
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}