
package Service;

public class GeminiConfig {
	public static final String[] API_KEYS = {
	        "",        // Key 1
	        "",    // Key 2
	        ""
	    };
    
    public static final String MODEL_NAME = "gemini-2.0-flash";

    public static final String GEMINI_API_URL =
        "https://generativelanguage.googleapis.com/v1beta/models/"
        + MODEL_NAME + ":generateContent";
    public static final double TEMPERATURE = 0.7;
    public static final int MAX_OUTPUT_TOKENS = 8192;
    public static final double TOP_P = 0.9;
    public static final int TOP_K = 40;
    
    public static final String BLOCK_THRESHOLD = "BLOCK_MEDIUM_AND_ABOVE";
}