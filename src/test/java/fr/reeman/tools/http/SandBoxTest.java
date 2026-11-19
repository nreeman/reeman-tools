package fr.reeman.tools.http;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.junit.jupiter.api.Test;

public class SandBoxTest {
	
	Pattern urlPattern = Pattern.compile("https?:\\/\\/(www\\.)?[-a-zA-Z0-9@:%._\\+~#=]{1,256}\\.[a-zA-Z0-9()]{1,6}\\b([-a-zA-Z0-9()@:%_\\+.~#?&//=]*)");

	@Test
	public void test() throws IOException, InterruptedException {
		//get("https://motus.absolu-puzzle.com/", "D:\\Nicolas\\");
		//get("https://mtgtop8.com/compare?l=_838857_838860_", "D:\\\\Nicolas\\\\");
	}
	
	private void get(String url, String targetDir) throws IOException, InterruptedException {
		if (!urlPattern.matcher(url).matches()) {
			throw new RuntimeException("'" + url + "' n'est pas une URL valide");
		}

		String dir = targetDir + (targetDir.endsWith("/") || targetDir.endsWith("\\") ? "" : "/");
		if (!new File(dir).isDirectory()) {
			throw new RuntimeException("'" + targetDir + "' n'est pas un répertoire valide");
		}
		
		HttpClient httpClient = HttpClient.newHttpClient();
		HttpRequest httpRequest = HttpRequest
				.newBuilder()
				.uri(URI.create(url))
				.GET()
				.build();
		
		HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
		write(url, httpResponse, dir + "get_" + new Date().getTime());
		
	}

	private void write(String url, HttpResponse<String> httpResponse, String fullFileName) throws FileNotFoundException, IOException {
		try (PrintStream out = new PrintStream(fullFileName)) {
			out.println(url + " : " + httpResponse.statusCode());
			out.print("---");
			for (Map.Entry<String, List<String>> entry : httpResponse.headers().map().entrySet()) {
                out.println(entry.getKey() + " : " + String.join(", ", entry.getValue()));
            }
			out.print("---");
			out.print(httpResponse.body());
		}
	}
}
