package Service;

public class IntentDetector {

    public static IntentType detect(String message) {
        if (message == null) return IntentType.UNKNOWN;

        String msg = message.toLowerCase();

        if (msg.contains("đậm") || msg.contains("nhẹ") ||
            msg.contains("gợi ý") || msg.contains("phù hợp")) {
            return IntentType.PRODUCT_RECOMMEND;
        }

        if (msg.contains("giá") || msg.contains("bao nhiêu") ||

        	    msg.contains("bao tiền") || msg.contains("mấy tiền") ||  
        	    msg.contains("giá mấy") || msg.contains("giá bao") ||    
        	    msg.contains("dưới") || msg.contains("tầm giá") ||
        	    msg.contains("khoảng") || msg.matches(".*\\d+k.*")) {
        	    return IntentType.PRODUCT_DETAIL;
        	}

        if (msg.contains("lịch sử") || msg.contains("thành lập") ||
            msg.contains("Nõn Café")) {
            return IntentType.COMPANY_HISTORY;
        }

        if (msg.contains("thanh toán") || msg.contains("vận chuyển")) {
            return IntentType.WEBSITE_GUIDE;
        }

        return IntentType.UNKNOWN;
    }
}
