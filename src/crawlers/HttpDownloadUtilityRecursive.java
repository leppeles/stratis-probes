package crawlers;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashSet;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class HttpDownloadUtilityRecursive {
	private static final int MAX_DEPTH = 3;
	private static final int PAGE_LIMIT = 1;
	int noOfPage = 0;
	private HashSet<String> links;

	public static void main(String[] args) {

		new HttpDownloadUtilityRecursive().getPageLinks("http://www.mkyong.com/", 1);
	}

	public HttpDownloadUtilityRecursive() {
		links = new HashSet<>();
	}

	public void getPageLinks(String URL, int depth) {
		if ((!links.contains(URL) && (depth < MAX_DEPTH && noOfPage < PAGE_LIMIT))) {
			System.out.println(">> Depth: " + depth + " [" + URL + "]");
			try {
				downloadFile(URL, "C:\\Users\\toszi\\Desktop\\Crawler\\newlocation");
			} catch (IOException e) {
				e.printStackTrace();
			}
			noOfPage++;
			try {
				links.add(URL);

				Document document = Jsoup.connect(URL).get();
				Elements linksOnPage = document.select("a[href]");
				depth++;
				for (Element page : linksOnPage) {
					getPageLinks(page.attr("abs:href"), depth);
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
	 * @param fileURL
	 *            HTTP URL of the file to be downloaded
	 * @param saveParentDir
	 *            path of the directory to save the file
	 * @throws IOException
	 */
	public static void downloadFile(String fileURL, String saveParentDir) throws IOException {
		if(fileURL.endsWith("/")) {
			fileURL = fileURL.substring(0, fileURL.length() - 1);
		}
		URL url = new URL(fileURL);
		HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
		int responseCode = httpConn.getResponseCode();

		// always check HTTP response code first
		if (responseCode == HttpURLConnection.HTTP_OK) {
			String fileName = "";
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
				fileName = (fileURL.substring(fileURL.lastIndexOf("/") + 1, fileURL.length()) + (fileURL.endsWith(".html")?"":".html"));
			}

			System.out.println("Content-Type = " + contentType);
			System.out.println("Content-Disposition = " + disposition);
			System.out.println("Content-Length = " + contentLength);
			System.out.println("fileName = " + fileName);

			// opens input stream from the HTTP connection
			InputStream inputStream = httpConn.getInputStream();
			String saveFilePath = saveParentDir + url.getHost() + File.separator + fileName;

			// opens an output stream to save into file
			FileOutputStream outputStream = new FileOutputStream(saveFilePath);

			int bytesRead = -1;
			byte[] buffer = new byte[BUFFER_SIZE];
			while ((bytesRead = inputStream.read(buffer)) != -1) {
				outputStream.write(buffer, 0, bytesRead);
			}

			outputStream.close();
			inputStream.close();

			System.out.println("File downloaded");
		} else {
			System.out.println("No file to download. Server replied HTTP code: " + responseCode);
		}
		httpConn.disconnect();
	}

}