package Service;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import Dao.Dao;
import Entity.Product;
import Entity.ProductAI;

import java.util.*;

public class GeminiService {

	private Dao dao;
	private Gson gson;
	private Map<String, ConversationContext> sessionContexts;

	public GeminiService() {
		this.dao = new Dao();
		this.gson = new Gson();
		this.sessionContexts = new HashMap<>();
	}

	public String getChatResponse(String userMessage, String sessionId) {
		try {
			ConversationContext context = sessionContexts.computeIfAbsent(sessionId, k -> new ConversationContext());

			context.addMessage("USER", userMessage);
			ContextExtractor.extractAndUpdate(userMessage, context);
			IntentType intent = IntentDetector.detect(userMessage);

			String response;
			if (intent == IntentType.PRODUCT_RECOMMEND) {
				response = handleProductConsultation(userMessage, context);
			} else if (intent == IntentType.PRODUCT_DETAIL) {
				response = handleProductDetail(userMessage);
			} else {
				response = handleGeneralQuery(userMessage, context);
			}

			context.addMessage("AI", response);
			return response;

		} catch (Exception e) {
			e.printStackTrace();
			return "Xin lỗi, tôi đang gặp sự cố kỹ thuật. Vui lòng thử lại sau.";
		}
	}

	private String handleProductConsultation(String userMessage, ConversationContext context) throws Exception {

		List<ProductAI> products = dao.getProductsForAI();

		String prompt = ConsultantPromptBuilder.buildConsultantPrompt(products, context) + "\n\nTIN NHẮN KHÁCH HÀNG:\n"
				+ userMessage +  "\n\n⚠️ LỆNH TUYỆT ĐỐI: Bạn phải suy nghĩ 5 BƯỚC một cách NGẦM trong đầu. KHI TRẢ LỜI KHÁCH, CHỈ ĐƯỢC IN RA KẾT QUẢ THEO ĐÚNG 'ĐỊNH DẠNG TRẢ LỜI'. TUYỆT ĐỐI KHÔNG IN RA CÁC TỪ 'BƯỚC 1', 'BƯỚC 2', 'BƯỚC 3', 'BƯỚC 4', 'BƯỚC 5'.";

		return parseGeminiResponse(callGeminiAPI(prompt));
	}

		private String handleProductDetail(String userMessage) throws Exception {
		List<Product> products = dao.getAllProducts();
		StringBuilder ctx = new StringBuilder();
		int count = 0;
		for (Product p : products) {
			if (count++ >= 15)
				break;
			ctx.append("ID: ").append(p.getId()).append(" | ").append(p.getName()).append(" | ")
					.append(String.format("%,.0f", p.getPrice())).append(" VNĐ\n");
		}
		String prompt = "Bạn là trợ lý tư vấn Nõn Café.\nSẢN PHẨM:\n" + ctx + "\nKhách hỏi: " + userMessage
				+ "\nTrả lời ngắn gọn, ghi rõ TÊN + GIÁ + ID.";
		return parseGeminiResponse(callGeminiAPI(prompt));
	}

	private String handleGeneralQuery(String userMessage, ConversationContext context) throws Exception {
		if (context.hasEnoughInfoToConsult()) {
			return handleProductConsultation(userMessage, context);
		}
		String prompt = "Bạn là trợ lý Nõn Café.\n" + (context.isEmpty() ? "" : "Ngữ cảnh: " + context + "\n")
				+ "Khách hỏi: " + userMessage + "\nTrả lời thân thiện, ngắn gọn 2-3 câu.";
		return parseGeminiResponse(callGeminiAPI(prompt));
	}

	private static int currentKeyIndex = 0;
	
	private String callGeminiAPI(String prompt) throws Exception {
		return executeWithRetry(prompt, 0);
	}

	private String executeWithRetry(String prompt, int attemptCount) throws Exception {
		if (attemptCount >= GeminiConfig.API_KEYS.length) {
			return "{\"error\": {\"message\": \"Hệ thống đang quá tải. Vui lòng đợi 1 phút rồi thử lại.\"}}";
		}

		String activeKey = GeminiConfig.API_KEYS[currentKeyIndex];
		String url = "https://generativelanguage.googleapis.com/v1beta/models/"
				+ "gemini-2.5-flash:generateContent?key=" + activeKey.trim();

		JsonObject body = new JsonObject();
		JsonArray contents = new JsonArray();
		JsonObject content = new JsonObject();
		JsonArray parts = new JsonArray();
		JsonObject part = new JsonObject();
		part.addProperty("text", prompt);
		parts.add(part);
		content.add("parts", parts);
		contents.add(content);
		body.add("contents", contents);

		JsonObject genConfig = new JsonObject();
		genConfig.addProperty("temperature", GeminiConfig.TEMPERATURE);
		genConfig.addProperty("maxOutputTokens", GeminiConfig.MAX_OUTPUT_TOKENS);
		genConfig.addProperty("topP", GeminiConfig.TOP_P);
		body.add("generationConfig", genConfig);

		try (CloseableHttpClient client = HttpClients.createDefault()) {
			HttpPost post = new HttpPost(url);
			post.setHeader("Content-Type", "application/json; charset=UTF-8");
			post.setEntity(new StringEntity(body.toString(), "UTF-8"));

			try (CloseableHttpResponse resp = client.execute(post)) {
				int statusCode = resp.getStatusLine().getStatusCode();
				String responseBody = EntityUtils.toString(resp.getEntity(), "UTF-8");

				if (statusCode == 429) {
					System.err.println(" API Key vị trí số [" + currentKeyIndex + "] đã cạn Quota!");

					currentKeyIndex = (currentKeyIndex + 1) % GeminiConfig.API_KEYS.length;

					System.out.println(
							"Đang tự động chuyển sang API Key vị trí số [" + currentKeyIndex + "] và gọi lại...");

					return executeWithRetry(prompt, attemptCount + 1);
				}

				return responseBody;
			}
		}
	}

	private String parseGeminiResponse(String raw) {
		try {
			JsonObject json = gson.fromJson(raw, JsonObject.class);
			if (json.has("error")) {
				String errMsg = json.getAsJsonObject("error").get("message").getAsString();
				System.err.println("[Gemini] Lỗi API: " + errMsg);
				return "Xin lỗi, AI đang bận. Vui lòng thử lại sau.";
			}
			JsonArray candidates = json.getAsJsonArray("candidates");
			if (candidates != null && candidates.size() > 0) {
				JsonObject candidate = candidates.get(0).getAsJsonObject();

				// Kiểm tra finishReason để phát hiện response bị cắt
				if (candidate.has("finishReason")) {
					String reason = candidate.get("finishReason").getAsString();
					if ("MAX_TOKENS".equals(reason)) {
						System.err.println(
								" Response bị cắt do MAX_TOKENS. Tăng GeminiConfig.MAX_OUTPUT_TOKENS.");
					}
				}

				String text = candidate.getAsJsonObject("content").getAsJsonArray("parts").get(0).getAsJsonObject()
						.get("text").getAsString();
				return text.replace("**", "").replace("*", "").trim();
			}
		} catch (Exception e) {
			System.err.println("[Gemini] Lỗi parse response: " + e.getMessage());
			e.printStackTrace();
		}
		return "Xin lỗi, tôi không thể tạo phản hồi lúc này.";
	}

	public void resetContext(String sessionId) {
		sessionContexts.remove(sessionId);
	}
}