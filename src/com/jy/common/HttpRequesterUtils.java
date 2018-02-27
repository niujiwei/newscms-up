package com.jy.common;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.jmimemagic.Magic;
import net.sf.jmimemagic.MagicMatch;
import org.apache.log4j.Logger;

public class HttpRequesterUtils {
	private static Logger log = Logger.getLogger(HttpRequesterUtils.class);
	public static String doGet(String urlAddress, String urlParam,
			String charSet) throws Exception {

		String getUrl = urlAddress + "?" + urlParam;
		URL url = new URL(getUrl);
		URLConnection connection = url.openConnection();
		connection.setRequestProperty("Accept-Charset", charSet);
		connection.setRequestProperty("Content-Type",
				"application/x-www-form-urlencoded;charset=" + charSet);
		
		InputStream inputStream = null;
		InputStreamReader inputStreamReader = null;
		BufferedReader bufferedReader = null;
		String readLine = "";
		StringBuffer resultBuffer = new StringBuffer();
		try {
			inputStream = connection.getInputStream();
			inputStreamReader = new InputStreamReader(inputStream,charSet);
			bufferedReader = new BufferedReader(inputStreamReader);
			while ((readLine = bufferedReader.readLine()) != null) {
				resultBuffer.append(readLine);
			}
		} catch (Exception e) {
			log.error("WeChartAction:" + e.getCause().getClass() + ","
					+ e.getCause().getMessage());

		} finally {

			if (bufferedReader != null) {
				bufferedReader.close();
			}

			if (inputStreamReader != null) {
				inputStreamReader.close();
			}

			if (inputStream != null) {
				inputStream.close();
			}

		}

		return resultBuffer.toString();

	}

	public static String doPost(String urlAddress, Map parameterMap,String charSet)
			throws Exception {
		/* Translate parameter map to parameter date string */
		StringBuffer parameterBuffer = new StringBuffer();
		if (parameterMap != null) {
			Iterator iterator = parameterMap.keySet().iterator();
			String key = null;
			String value = null;
			while (iterator.hasNext()) {
				key = (String) iterator.next();
				if (parameterMap.get(key) != null) {
					value = (String) parameterMap.get(key);
				} else {
					value = "";
				}

				parameterBuffer.append(key).append("=").append(value);
				if (iterator.hasNext()) {
					parameterBuffer.append("&");
				}
			}
		}

		log.info("POST parameter : " + parameterBuffer.toString());
		URL localURL = new URL(urlAddress);

		URLConnection connection = localURL.openConnection();
		// HttpURLConnection httpURLConnection = (HttpURLConnection) connection;

		connection.setDoOutput(true);
		// connection.setRequestMethod("POST");
		connection.setRequestProperty("Accept-Charset", charSet);
		connection.setRequestProperty("Content-Type",
				"application/x-www-form-urlencoded;charset=" + charSet);
		connection.setRequestProperty("Content-Length",
				String.valueOf(parameterBuffer.length()));

		OutputStream outputStream = null;
		OutputStreamWriter outputStreamWriter = null;
		InputStream inputStream = null;
		InputStreamReader inputStreamReader = null;
		BufferedReader reader = null;
		StringBuffer resultBuffer = new StringBuffer();
		String tempLine = null;

		try {
			outputStream = connection.getOutputStream();
			outputStreamWriter = new OutputStreamWriter(outputStream,charSet);

			outputStreamWriter.write(parameterBuffer.toString());
			outputStreamWriter.flush();

			inputStream = connection.getInputStream();
			inputStreamReader = new InputStreamReader(inputStream,charSet);
			reader = new BufferedReader(inputStreamReader);

			while ((tempLine = reader.readLine()) != null) {
				resultBuffer.append(tempLine);
			}

		} finally {

			if (outputStreamWriter != null) {
				outputStreamWriter.close();
			}

			if (outputStream != null) {
				outputStream.close();
			}

			if (reader != null) {
				reader.close();
			}

			if (inputStreamReader != null) {
				inputStreamReader.close();
			}

			if (inputStream != null) {
				inputStream.close();
			}

		}
		return resultBuffer.toString();

	}

	public static String doPost(String urlAddress, String json,String charSet)
			throws Exception {
		/* Translate parameter map to parameter date string */

		// log.error("POST parameter : " + parameterBuffer.toString());
		URL localURL = new URL(urlAddress);

		URLConnection connection = localURL.openConnection();
		// HttpURLConnection httpURLConnection = (HttpURLConnection) connection;

		connection.setDoOutput(true);
		// connection.setRequestMethod("POST");
		connection.setRequestProperty("Accept-Charset", charSet);
		connection.setRequestProperty("Content-Type",
				"application/x-www-form-urlencoded;charset=" + charSet);
		connection.setRequestProperty("Content-Length",
				String.valueOf(json.length()));

		OutputStream outputStream = null;
		OutputStreamWriter outputStreamWriter = null;
		InputStream inputStream = null;
		InputStreamReader inputStreamReader = null;
		BufferedReader reader = null;
		StringBuffer resultBuffer = new StringBuffer();
		String tempLine = null;

		try {
			outputStream = connection.getOutputStream();
			outputStreamWriter = new OutputStreamWriter(outputStream);

			outputStreamWriter.write(json);
			outputStreamWriter.flush();

			inputStream = connection.getInputStream();
			inputStreamReader = new InputStreamReader(inputStream,charSet);
			reader = new BufferedReader(inputStreamReader);

			while ((tempLine = reader.readLine()) != null) {
				resultBuffer.append(tempLine);
			}

		} finally {

			if (outputStreamWriter != null) {
				outputStreamWriter.close();
			}

			if (outputStream != null) {
				outputStream.close();
			}

			if (reader != null) {
				reader.close();
			}

			if (inputStreamReader != null) {
				inputStreamReader.close();
			}

			if (inputStream != null) {
				inputStream.close();
			}

		}
		return resultBuffer.toString();
	}

	public static String formUpload(String urlStr, Map<String, String> textMap, Map<String, String> fileMap) {
		String res = "";
		URLConnection conn = null;
		String BOUNDARY = "---------------------------123821742118716"; //boundary就是request头和上传文件内容的分隔符
		try {
			URL url = new URL(urlStr);
			conn =  url.openConnection();
			conn.setConnectTimeout(5000);
			conn.setReadTimeout(30000);
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setUseCaches(false);
			//conn.setRequestMethod("POST");
			conn.setRequestProperty("Connection", "Keep-Alive");
			conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 6.1; zh-CN; rv:1.9.2.6)");
			conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);

			OutputStream out = new DataOutputStream(conn.getOutputStream());
			// text
			if (textMap != null) {
				StringBuffer strBuf = new StringBuffer();
				Iterator<Map.Entry<String, String>> iter = textMap.entrySet().iterator();
				while (iter.hasNext()) {
					Map.Entry<String, String> entry = iter.next();
					String inputName = entry.getKey();
					String inputValue = entry.getValue();
					if (inputValue == null) {
						continue;
					}
					strBuf.append("\r\n").append("--").append(BOUNDARY).append("\r\n");
					strBuf.append("Content-Disposition: form-data; name=\"" + inputName + "\"\r\n\r\n");
					strBuf.append(inputValue);
				}
				out.write(strBuf.toString().getBytes());
			}

			// file
			if (fileMap != null) {
				Iterator<Map.Entry<String, String>> iter = fileMap.entrySet().iterator();
				while (iter.hasNext()) {
					Map.Entry<String, String> entry = iter.next();
					String inputName = entry.getKey();
					String inputValue = entry.getValue();
					if (inputValue == null) {
						continue;
					}

					File file = new File(inputValue);
					String filename = file.getName();
					MagicMatch match = Magic.getMagicMatch(file, false, true);
					String contentType = match.getMimeType();

					StringBuffer strBuf = new StringBuffer();
					strBuf.append("\r\n").append("--").append(BOUNDARY).append("\r\n");
					strBuf.append("Content-Disposition: form-data; name=\"" + "userFile" + "\"; filename=\"" + filename + "\"\r\n");
					strBuf.append("Content-Type:" + contentType + "\r\n\r\n");

					out.write(strBuf.toString().getBytes());

					DataInputStream in = new DataInputStream(new FileInputStream(file));
					int bytes;
					byte[] bufferOut = new byte[1024];
					while ((bytes = in.read(bufferOut)) != -1) {
						out.write(bufferOut, 0, bytes);
					}
					in.close();
				}
			}

			byte[] endData = ("\r\n--" + BOUNDARY + "--\r\n").getBytes();
			out.write(endData);
			out.flush();
			out.close();

			// 读取返回数据
			StringBuffer strBuf = new StringBuffer();
			BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = reader.readLine()) != null) {
				strBuf.append(line);
			}
			res = strBuf.toString();
			reader.close();
		} catch (Exception e) {
			System.out.println("发送POST请求出错。" + urlStr);
			e.printStackTrace();
		} finally {
		/*	if (conn != null) {
				conn.();
				conn = null;
			}*/
		}
		return res;
	}

	public static String formListUpload(String urlStr, Map<String, String> textMap, Map<String, List<String>> fileMap) {
		String res = "";
		URLConnection conn = null;
		String BOUNDARY = "---------------------------123821742118716"; //boundary就是request头和上传文件内容的分隔符
		try {
			URL url = new URL(urlStr);
			conn =  url.openConnection();
			conn.setConnectTimeout(5000);
			conn.setReadTimeout(30000);
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setUseCaches(false);
			//conn.setRequestMethod("POST");
			conn.setRequestProperty("Connection", "Keep-Alive");
			conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 6.1; zh-CN; rv:1.9.2.6)");
			conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);

			OutputStream out = new DataOutputStream(conn.getOutputStream());
			// text
			if (textMap != null) {
				StringBuffer strBuf = new StringBuffer();
				Iterator<Map.Entry<String, String>> iter = textMap.entrySet().iterator();
				while (iter.hasNext()) {
					Map.Entry<String, String> entry = iter.next();
					String inputName = entry.getKey();
					String inputValue = entry.getValue();
					if (inputValue == null) {
						continue;
					}
					strBuf.append("\r\n").append("--").append(BOUNDARY).append("\r\n");
					strBuf.append("Content-Disposition: form-data; name=\"" + inputName + "\"\r\n\r\n");
					strBuf.append(inputValue);
				}
				out.write(strBuf.toString().getBytes());
			}

			// file
			if (fileMap != null) {
				Iterator<Map.Entry<String, List<String>>> iter = fileMap.entrySet().iterator();
				while (iter.hasNext()) {
					Map.Entry<String, List<String>> entry = iter.next();
					String inputName = entry.getKey();
					List<String> list = entry.getValue();
					if (list == null) {
						continue;
					}
					for (String inputValue:list){
						if (inputValue==null||inputValue.equals("")) continue;
						File file = new File(inputValue);
						String filename = file.getName();
						MagicMatch match = Magic.getMagicMatch(file, false, true);
						String contentType = match.getMimeType();

						StringBuffer strBuf = new StringBuffer();
						strBuf.append("\r\n").append("--").append(BOUNDARY).append("\r\n");
						strBuf.append("Content-Disposition: form-data; name=\"" + inputName + "\"; filename=\"" + filename + "\"\r\n");
						strBuf.append("Content-Type:" + contentType + "\r\n\r\n");
						out.write(strBuf.toString().getBytes());
						DataInputStream in = new DataInputStream(new FileInputStream(file));
						int bytes;
						byte[] bufferOut = new byte[1024];
						while ((bytes = in.read(bufferOut)) != -1) {
							out.write(bufferOut, 0, bytes);
						}
						in.close();
					}

				}
			}

			byte[] endData = ("\r\n--" + BOUNDARY + "--\r\n").getBytes();
			out.write(endData);
			out.flush();
			out.close();
			// 读取返回数据
			StringBuffer strBuf = new StringBuffer();
			BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = reader.readLine()) != null) {
				strBuf.append(line);
			}
			res = strBuf.toString();
			reader.close();
		} catch (Exception e) {
			System.out.println("发送POST请求出错。" + urlStr);
			e.printStackTrace();
		} finally {
		/*	if (conn != null) {
				conn.();
				conn = null;
			}*/
		}
		return res;
	}

}
