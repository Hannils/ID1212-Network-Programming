import java.net.*;
import java.io.*;

public class HTTPUrlConnection {
	private static final String url = "http://localhost:8090/game/guess";
	
	/**
	 * Main function executes the playGame function 100 times and prints the avg number of guesses until win
	 * @param args Arguments passed to the main function
	 */
	public static void main(String[] args) {
		CookieManager cookieManager = new CookieManager();
		CookieHandler.setDefault(cookieManager);
		int avg = 0;
		for (int x = 0; x < 100; x++) {
			avg += playGame();
		}
		System.out.println("Average is: " + avg/100);
		

	}

	/**
	 * Function which executes the requests until the status "WIN" is acheived
	 * @return The number of guesses it took until the game was won
	 */
	public static int playGame() {
		int guess = 10;
		String status;
		String res;
		do {
			res = POST(guess);
			status = getResponse(res);
			if (status.equals("HIGHER")) {
				guess += 10;
			} else if (status.equals("LOWER")) {
				guess -= 1;
			}
		} while (!status.equals("WIN"));

		return getGuesses(res);
	}

	/**
	 * Function which executes the post request based on the url connection string initialized above.
	 * @param guess The parameter to send along with the POST
	 * @return The entire response from the request
	 */
	public static String POST(int guess) {
		try {
			URL obj = new URL(url);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
			con.setRequestMethod("POST");

			con.setDoOutput(true);
			OutputStream os = con.getOutputStream();
			os.write(("" + guess).getBytes());
			os.flush();
			os.close();
			int responseCode = con.getResponseCode();
			if (responseCode == HttpURLConnection.HTTP_OK) { // success
				BufferedReader in = new BufferedReader(new InputStreamReader(
						con.getInputStream()));
				String inputLine;
				StringBuffer response = new StringBuffer();

				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);
				}
				return response.toString();
			} else {
				System.err.println("POST request not worked");
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Parses the response status key from the JSON response
	 * @param res The entire JSON recieved form the request
	 * @return The response status
	 */
	public static String getResponse(String res) {
		String status = res.split(",")[0].split(": ")[1].replace("\"", "");
		return status;
	}

	/**
	 * Parses the numberOfGuesses key from the JSON response.
	 * @param res The entire JSON recieved from the request
	 * @return The number of guesses
	 */
	public static int getGuesses(String res) {
		return Integer.parseInt(res.split(",")[1].split(": ")[1].replace("\"", "").replace("}", ""));
	}
}