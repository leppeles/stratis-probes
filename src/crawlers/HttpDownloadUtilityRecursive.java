package crawlers;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class HttpDownloadUtilityRecursive {
	private static final int MAX_DEPTH = 3;
	private static final int PAGE_LIMIT = 10;
	private final String saveParentDir = "C:\\Users\\toszi\\Desktop\\Crawler";
	int noOfPage = 0;
	private HashSet<String> links;
	
	/** SLF4J Logger */
	private final static Logger log = LoggerFactory.getLogger(HttpDownloadUtilityRecursive.class);

	public static void main(String[] args) {

		log.info("~~~~~~~~~~~~~~~~~~~~HTTP crawler started~~~~~~~~~~~~~~~~~~~~");
		new HttpDownloadUtilityRecursive().getPageLinks("https://stackoverflow.com/questions/9510932/java-package-vs-folder-structure-what-is-the-difference", 1);
	}

	public HttpDownloadUtilityRecursive() {
		links = new HashSet<>();
	}

	public void getPageLinks(String URL, int depth) {
		if (!links.contains(URL) && depth < MAX_DEPTH && noOfPage < PAGE_LIMIT) {
			log.info(">> Depth: " + depth + " [" + URL + "]");
			try {
				links.add(URL);

				Document document = Jsoup.connect(URL).get();
				Elements linksOnPage = document.select("a[href]");
				depth++;
				for (Element page : linksOnPage) {
					downloadFile(page.attr("abs:href"), saveParentDir);
					noOfPage++;
					if (noOfPage < PAGE_LIMIT) {
						getPageLinks(page.attr("abs:href"), depth);
					}
					else {
						log.info("Page limit reached.");
						log.info("~~~~~~~~~~~~~~~~~~~~HTTP crawler ended~~~~~~~~~~~~~~~~~~~~");
						System.exit(0);
					}
				}
			} catch (IOException e) {
				System.err.println("For '" + URL + "': " + e.getMessage());
			}
		}
	}

	private static final int BUFFER_SIZE = 4096;

	/**
	 * source:
	 * https://stackoverflow.com/questions/26454916/download-the-entire-webpage
	 * Downloads a file from a URL
	 * 
	 * @param stringURL
	 *            HTTP URL of the site to be downloaded
	 * @param saveParentDir
	 *            path of the directory to save the file
	 * @throws IOException
	 */
	public static void downloadFile(String stringURL, String saveParentDir) throws IOException {
		if (stringURL.endsWith("/")) {
			stringURL = stringURL.substring(0, stringURL.length() - 1);
		}
		URL url = new URL(stringURL);
		HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
		int responseCode = httpConn.getResponseCode();

		// always check HTTP response code first
		if (responseCode == HttpURLConnection.HTTP_OK) {
			String fileName = "";
			String dirSubPath = "";
			String disposition = httpConn.getHeaderField("Content-Disposition");
			String contentType = httpConn.getContentType();
			int contentLength = httpConn.getContentLength();

			if (disposition != null) {
				// extracts file name from header field
				int index = disposition.indexOf("filename=");
				if (index > 0) {
					fileName = disposition.substring(index + 10, disposition.length() - 1);
				}
			} else {
				// extracts file name from URL
				fileName = (stringURL.substring(stringURL.lastIndexOf("/") + 1, stringURL.length())
						+ (stringURL.endsWith(".html") ? "" : ".html"));
			}

			log.info("Content-Type = " + contentType);
			log.info("Content-Disposition = " + disposition);
			log.info("Content-Length = " + contentLength);

			dirSubPath = url.getPath().toString();
			dirSubPath = (dirSubPath.substring(0, dirSubPath.lastIndexOf("/") + 1));
			dirSubPath.replace("/", File.separator);

			(new File(saveParentDir + dirSubPath)).mkdirs();

			// opens input stream from the HTTP connection
			InputStream inputStream = httpConn.getInputStream();
			String fileNameWithPath = saveParentDir + dirSubPath + File.separator + fileName;

			// opens an output stream to save into file
			FileOutputStream outputStream = new FileOutputStream(fileNameWithPath);

			int bytesRead = -1;
			byte[] buffer = new byte[BUFFER_SIZE];
			while ((bytesRead = inputStream.read(buffer)) != -1) {
				outputStream.write(buffer, 0, bytesRead);
			}

			outputStream.close();
			inputStream.close();

			log.info("File name = " + fileNameWithPath);
			log.info("File downloaded");
		} else {
			log.error("No file to download. Server replied HTTP code: " + responseCode);
		}
		httpConn.disconnect();
	}

}