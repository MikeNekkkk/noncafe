package Service;

import java.io.*;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.JSONArray;
import org.json.JSONObject;

import Entity.OrderItem;

public class GHNService {

	private static final Logger logger = Logger.getLogger(GHNService.class.getName());

	private static final String TOKEN = "";
	private static final int SHOP_ID_INT = ;
	private static final String ENV = "sandbox";
	private static final int SENDER_DISTRICT_ID = ; // Hải Châu – Đà Nẵng
	private static final String SENDER_WARD_CODE = ""; // Phường Thanh Bình
	private static final String SENDER_PHONE = "";
	private static final String SENDER_ADDRESS = "";
	private static final String SENDER_WARD_NAME = "";
	private static final String SENDER_DISTRICT_NAME = "";
	private static final String SENDER_PROVINCE_NAME = "";
	private static final String SENDER_NAME = "";

	private static final String BASE_URL = "sandbox".equalsIgnoreCase(ENV)
			? "https://dev-online-gateway.ghn.vn/shiip/public-api"
			: "https://online-gateway.ghn.vn/shiip/public-api";

	public List<Map<String, Object>> getProvinces() {
		try {
			JSONObject json = new JSONObject(get("/master-data/province"));
			if (json.getInt("code") != 200)
				return Collections.emptyList();
			JSONArray arr = json.getJSONArray("data");
			List<Map<String, Object>> result = new ArrayList<>();
			for (int i = 0; i < arr.length(); i++) {
				JSONObject o = arr.getJSONObject(i);
				Map<String, Object> m = new LinkedHashMap<>();
				m.put("ProvinceID", o.getInt("ProvinceID"));
				m.put("ProvinceName", o.getString("ProvinceName"));
				result.add(m);
			}
			return result;
		} catch (Exception e) {
			logger.log(Level.SEVERE, "getProvinces error", e);
			return Collections.emptyList();
		}
	}

	public List<Map<String, Object>> getDistricts(int provinceId) {
		try {
			JSONObject json = new JSONObject(post("/master-data/district", "{\"province_id\":" + provinceId + "}"));
			if (json.getInt("code") != 200)
				return Collections.emptyList();
			JSONArray arr = json.getJSONArray("data");
			List<Map<String, Object>> result = new ArrayList<>();
			for (int i = 0; i < arr.length(); i++) {
				JSONObject o = arr.getJSONObject(i);
				Map<String, Object> m = new LinkedHashMap<>();
				m.put("DistrictID", o.getInt("DistrictID"));
				m.put("DistrictName", o.getString("DistrictName"));
				result.add(m);
			}
			return result;
		} catch (Exception e) {
			logger.log(Level.SEVERE, "getDistricts error", e);
			return Collections.emptyList();
		}
	}

	public List<Map<String, Object>> getWards(int districtId) {
		try {
			JSONObject json = new JSONObject(get("/master-data/ward?district_id=" + districtId));
			if (json.getInt("code") != 200)
				return Collections.emptyList();
			JSONArray arr = json.getJSONArray("data");
			List<Map<String, Object>> result = new ArrayList<>();
			for (int i = 0; i < arr.length(); i++) {
				JSONObject o = arr.getJSONObject(i);
				Map<String, Object> m = new LinkedHashMap<>();
				m.put("WardCode", o.getString("WardCode"));
				m.put("WardName", o.getString("WardName"));
				result.add(m);
			}
			return result;
		} catch (Exception e) {
			logger.log(Level.SEVERE, "getWards error", e);
			return Collections.emptyList();
		}
	}

	public int getAvailableServiceId(int fromDistrictId, int toDistrictId) {
		try {
			String body = String.format("{\"shop_id\":%d,\"from_district\":%d,\"to_district\":%d}", SHOP_ID_INT,
					fromDistrictId, toDistrictId);

			String raw = post("/v2/shipping-order/available-services", body);
			logger.info("available-services raw (from=" + fromDistrictId + " to=" + toDistrictId + "): " + raw);

			JSONObject json = new JSONObject(raw);
			if (json.getInt("code") != 200) {
				logger.warning("available-services failed: " + json.optString("message") + " (from=" + fromDistrictId
						+ " to=" + toDistrictId + ")");
				return 0;
			}

			JSONArray services = json.getJSONArray("data");
			if (services.length() == 0) {
				logger.warning("No services from=" + fromDistrictId + " to=" + toDistrictId);
				return 0;
			}

			for (int i = 0; i < services.length(); i++) {
				JSONObject s = services.getJSONObject(i);
				if (s.optInt("service_type_id") == 2) {
					int sid = s.getInt("service_id");
					logger.info("Selected service_id=" + sid + " (type=2)");
					return sid;
				}
			}

			int sid = services.getJSONObject(0).getInt("service_id");
			logger.info("Fallback service_id=" + sid);
			return sid;

		} catch (Exception e) {
			logger.log(Level.SEVERE, "getAvailableServiceId error", e);
			return 0;
		}
	}

	public BigDecimal calculateShippingFee(int toDistrictId, String toWardCode, int weightGram) {
		try {
			int serviceId = getAvailableServiceId(SENDER_DISTRICT_ID, toDistrictId);
			if (serviceId == 0) {
				logger.warning("calculateShippingFee: no service_id → fallback fee");
				return fallbackFee(toDistrictId);
			}

			String body = String.format(
					"{\"service_id\":%d," + "\"insurance_value\":0," + "\"from_district_id\":%d,"
							+ "\"to_district_id\":%d," + "\"to_ward_code\":\"%s\"," + "\"weight\":%d,"
							+ "\"length\":%d,\"width\":%d,\"height\":%d}",
					serviceId, SENDER_DISTRICT_ID, toDistrictId, toWardCode, weightGram, getBoxSize(weightGram)[0],
					getBoxSize(weightGram)[1], getBoxSize(weightGram)[2]);

			JSONObject json = new JSONObject(postWithShopId("/v2/shipping-order/fee", body));
			if (json.getInt("code") != 200) {
				logger.warning("GHN /fee error: " + json.optString("message"));
				return fallbackFee(toDistrictId);
			}

			int fee = json.getJSONObject("data").getInt("total");
			logger.info("GHN fee=" + fee + " VND | district=" + toDistrictId + " ward=" + toWardCode + " weight="
					+ weightGram + "g");
			return BigDecimal.valueOf(fee);

		} catch (Exception e) {
			logger.log(Level.SEVERE, "calculateShippingFee error", e);
			return fallbackFee(toDistrictId);
		}
	}

	private BigDecimal fallbackFee(int toDistrictId) {
		if (toDistrictId >= 1570 && toDistrictId <= 1585)
			return BigDecimal.valueOf(15000);
		return BigDecimal.valueOf(35000);
	}

	static int[] getBoxSize(int weightGram) {
		if (weightGram <= 500) {
			return new int[] { 20, 15, 8 };
		} else if (weightGram <= 1000) {
			return new int[] { 25, 20, 10 };
		} else if (weightGram <= 2000) {
			return new int[] { 30, 20, 15 };
		} else {
			int extraKg = (int) Math.ceil((weightGram - 2000) / 1000.0); // số kg vượt (làm tròn lên)
			int length = Math.min(30 + extraKg * 5, 60);
			int width = Math.min(20 + extraKg * 2, 40);
			int height = Math.min(15 + extraKg * 2, 30);
			return new int[] { length, width, height };
		}
	}

	public String createShipOrder(String receiverName, String receiverPhone, String receiverAddress, int toDistrictId,
			String toWardCode, int weightGram, long internalOrderId, String note, List<OrderItem> orderItems) {
		try {
			int serviceId = getAvailableServiceId(SENDER_DISTRICT_ID, toDistrictId);
			if (serviceId == 0) {
				logger.warning("createShipOrder: no service_id cho orderId=" + internalOrderId);
				return null;
			}

			String itemsJson = buildItemsJson(orderItems, weightGram);

			int[] box = getBoxSize(weightGram);
			int boxLength = box[0], boxWidth = box[1], boxHeight = box[2];
			logger.info("Box size for " + weightGram + "g: " + boxLength + "×" + boxWidth + "×" + boxHeight + " cm");

			String body = String.format("{" + "\"service_id\":%d," + "\"service_type_id\":2," + "\"payment_type_id\":1,"
					+ "\"note\":\"%s\"," + "\"required_note\":\"KHONGCHOXEMHANG\"," + "\"from_name\":\"%s\","
					+ "\"from_phone\":\"%s\"," + "\"from_address\":\"%s\"," + "\"from_ward_name\":\"%s\","
					+ "\"from_district_name\":\"%s\"," + "\"from_province_name\":\"%s\"," + "\"return_phone\":\"%s\","
					+ "\"return_address\":\"%s\"," + "\"return_district_id\":%d," + "\"return_ward_code\":\"%s\","
					+ "\"client_order_code\":\"NON-%d\"," + "\"to_name\":\"%s\"," + "\"to_phone\":\"%s\","
					+ "\"to_address\":\"%s\"," + "\"to_ward_code\":\"%s\"," + "\"to_district_id\":%d,"
					+ "\"weight\":%d," + "\"length\":%d,\"width\":%d,\"height\":%d," // ← động theo trọng lượng
					+ "\"cod_amount\":0," + "\"insurance_value\":0," + "\"items\":%s" + "}", serviceId,
					escapeJson(note != null ? note : ""), escapeJson(SENDER_NAME), escapeJson(SENDER_PHONE),
					escapeJson(SENDER_ADDRESS), escapeJson(SENDER_WARD_NAME), escapeJson(SENDER_DISTRICT_NAME),
					escapeJson(SENDER_PROVINCE_NAME), escapeJson(SENDER_PHONE), escapeJson(SENDER_ADDRESS),
					SENDER_DISTRICT_ID, escapeJson(SENDER_WARD_CODE), internalOrderId, escapeJson(receiverName),
					escapeJson(receiverPhone), escapeJson(receiverAddress), escapeJson(toWardCode), toDistrictId,
					weightGram, boxLength, boxWidth, boxHeight,
					itemsJson);

			logger.info("createShipOrder request body: " + body);
			String raw = postWithShopId("/v2/shipping-order/create", body);
			logger.info("createShipOrder response: " + raw);

			JSONObject json = new JSONObject(raw);
			if (json.getInt("code") != 200) {
				logger.warning("GHN /create error: " + json.optString("message") + " | orderId=" + internalOrderId);
				return null;
			}

			String orderCode = json.getJSONObject("data").getString("order_code");
			logger.info("✅ GHN order_code=" + orderCode + " created for orderId=" + internalOrderId);
			return orderCode;

		} catch (Exception e) {
			logger.log(Level.SEVERE, "createShipOrder error for orderId=" + internalOrderId, e);
			return null;
		}
	}

	private String buildItemsJson(List<OrderItem> orderItems, int totalWeightGram) {
		if (orderItems == null || orderItems.isEmpty()) {
			return "[{\"name\":\"Ca phe Non\",\"code\":\"SP001\"," + "\"quantity\":1,\"weight\":" + totalWeightGram
					+ "," + "\"price\":100000}]";
		}

		int weightPerItem = Math.max(totalWeightGram / orderItems.size(), 100);

		StringBuilder sb = new StringBuilder("[");
		for (int i = 0; i < orderItems.size(); i++) {
			OrderItem item = orderItems.get(i);
			if (i > 0)
				sb.append(",");

			String name = escapeJson(item.getProductName() != null ? item.getProductName() : "San pham");
			String code = "SP" + item.getProductId();
			int itemWeight;
			try {
				int wg = item.getWeightGram(); // weightGram của 1 đơn vị sản phẩm
				itemWeight = (wg > 0) ? wg * item.getQuantity() : weightPerItem;
			} catch (Exception ignored) {
				// Entity chưa có field weightGram → chia đều
				itemWeight = weightPerItem;
			}

			// Giá: GHN nhận số nguyên VND
			long price = (item.getPrice() != null) ? item.getPrice().longValue() : 0L;

			sb.append(String.format("{\"name\":\"%s\",\"code\":\"%s\"," + "\"quantity\":%d,\"weight\":%d,\"price\":%d}",
					name, code, item.getQuantity(), Math.max(itemWeight, 1), price));
		}
		sb.append("]");
		return sb.toString();
	}

	public List<Map<String, Object>> getTrackingLogs(String ghnOrderCode) {
		if (ghnOrderCode == null || ghnOrderCode.isEmpty())
			return Collections.emptyList();
		try {
			String body = "{\"order_code\":\"" + ghnOrderCode + "\"}";
			JSONObject json = new JSONObject(post("/v2/shipping-order/detail", body));
			if (json.getInt("code") != 200)
				return Collections.emptyList();

			JSONObject data = json.getJSONObject("data");
			JSONArray logs = data.optJSONArray("log");
			if (logs == null)
				return Collections.emptyList();

			List<Map<String, Object>> result = new ArrayList<>();
			for (int i = 0; i < logs.length(); i++) {
				JSONObject log = logs.getJSONObject(i);
				Map<String, Object> m = new LinkedHashMap<>();
				m.put("time", log.optString("updated_date", ""));
				m.put("statusCode", log.optString("status", ""));
				m.put("statusName", translateStatus(log.optString("status", "")));
				m.put("location", resolveLocation(log.optString("warehouse_code", ""), log.optString("location", "")));
				result.add(m);
			}
			result.sort((a, b) -> ((String) b.get("time")).compareTo((String) a.get("time")));
			return result;
		} catch (Exception e) {
			logger.log(Level.SEVERE, "getTrackingLogs error for " + ghnOrderCode, e);
			return Collections.emptyList();
		}
	}

	public static String translateStatus(String code) {
		if (code == null)
			return "Không xác định";
		switch (code) {
		case "ready_to_pick":
			return "Chờ lấy hàng";
		case "picking":
			return "Đang lấy hàng";
		case "cancel":
			return "Đã hủy";
		case "money_collect_picking":
			return "Thu tiền khi lấy hàng";
		case "picked":
			return "Đã lấy hàng";
		case "storing":
			return "Đang nhập kho trung chuyển";
		case "transporting":
			return "Đang vận chuyển liên tỉnh";
		case "sorting":
			return "Đang phân loại tại kho";
		case "delivering":
			return "Đang giao hàng";
		case "money_collect_delivering":
			return "Thu tiền khi giao hàng";
		case "delivered":
			return "Giao hàng thành công";
		case "delivery_fail":
			return "Giao hàng thất bại";
		case "waiting_to_return":
			return "Chờ hoàn hàng";
		case "return":
			return "Đang hoàn hàng về shop";
		case "return_transporting":
			return "Vận chuyển hoàn hàng";
		case "return_sorting":
			return "Phân loại hoàn hàng";
		case "returning":
			return "Đang hoàn hàng";
		case "return_fail":
			return "Hoàn hàng thất bại";
		case "returned":
			return "Đã hoàn hàng về shop";
		case "exception":
			return "Ngoại lệ / Sự cố";
		case "damage":
			return "Hàng bị hư hỏng";
		case "lost":
			return "Hàng bị mất";
		case "PENDING":
			return "Đơn hàng mới – chờ xác nhận";
		case "PROCESSING":
			return "Shop đang chuẩn bị hàng";
		case "DELIVERING":
			return "Đã bàn giao cho đơn vị vận chuyển";
		case "COMPLETED":
			return "Giao hàng thành công";
		case "CANCELLED":
			return "Đơn hàng đã hủy";
		default:
			return code;
		}
	}

	private static String resolveLocation(String warehouseCode, String locationRaw) {
		if (locationRaw != null && !locationRaw.isEmpty())
			return locationRaw;
		switch (warehouseCode) {
		case "1570001":
			return "Kho Hải Châu – Đà Nẵng";
		case "1570002":
			return "Kho Liên Chiểu – Đà Nẵng";
		case "1570003":
			return "Kho Sơn Trà – Đà Nẵng";
		case "1570004":
			return "Kho Thanh Khê – Đà Nẵng";
		case "HAN001":
			return "Kho tổng Hà Nội";
		case "SGN001":
			return "Kho tổng TP.HCM";
		default:
			return warehouseCode.isEmpty() ? "Kho Nõn Cà Phê" : warehouseCode;
		}
	}

	private String get(String path) throws IOException {
		HttpURLConnection conn = openConn(BASE_URL + path, "GET");
		return readResponse(conn);
	}

	private String post(String path, String body) throws IOException {
		HttpURLConnection conn = openConn(BASE_URL + path, "POST");
		writeBody(conn, body);
		return readResponse(conn);
	}

	private String postWithShopId(String path, String body) throws IOException {
		HttpURLConnection conn = openConn(BASE_URL + path, "POST");
		conn.setRequestProperty("ShopId", String.valueOf(SHOP_ID_INT));
		writeBody(conn, body);
		return readResponse(conn);
	}

	private HttpURLConnection openConn(String urlStr, String method) throws IOException {
		URL url = new URL(urlStr);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod(method);
		conn.setRequestProperty("Content-Type", "application/json; charset=utf-8");
		conn.setRequestProperty("Token", TOKEN);
		conn.setConnectTimeout(10_000);
		conn.setReadTimeout(15_000);
		return conn;
	}

	private void writeBody(HttpURLConnection conn, String body) throws IOException {
		conn.setDoOutput(true);
		try (OutputStream os = conn.getOutputStream()) {
			os.write(body.getBytes(StandardCharsets.UTF_8));
		}
	}

	private String readResponse(HttpURLConnection conn) throws IOException {
		int httpCode = conn.getResponseCode();
		InputStream is = (httpCode >= 200 && httpCode < 300) ? conn.getInputStream() : conn.getErrorStream();
		if (is == null)
			return "{}";
		try (BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {
			StringBuilder sb = new StringBuilder();
			String line;
			while ((line = br.readLine()) != null)
				sb.append(line);
			return sb.toString();
		}
	}

	private static String escapeJson(String s) {
		if (s == null)
			return "";
		return s.replace("\\", "\\\\").replace("\"", "\\\"").replace("\n", "\\n").replace("\r", "");
	}
}