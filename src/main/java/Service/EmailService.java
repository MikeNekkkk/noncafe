package Service;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;

public class EmailService {

	private static final String SENDER_EMAIL = "";
	private static final String SENDER_PASSWORD = "";
	private static final String SENDER_NAME = "";

	private static Session createSession() {
		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.ssl.trust", "smtp.gmail.com"); 

		return Session.getInstance(props, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(SENDER_EMAIL, SENDER_PASSWORD);
			}
		});
	}

	public static boolean sendEmail(String toEmail, String subject, String htmlContent) {
		try {
			Session session = createSession();
			Message message = new MimeMessage(session);

			message.setFrom(new InternetAddress(SENDER_EMAIL, SENDER_NAME, "UTF-8"));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail)); // ngắn chuỗi
			message.setSubject(MimeUtility.encodeText(subject, "UTF-8", "B"));
			message.setContent(htmlContent, "text/html; charset=UTF-8");

			Transport.send(message);
			System.out.println(" Email đã gửi đến: " + toEmail);
			return true;

		} catch (Exception e) {
			System.err.println(" Lỗi gửi email: " + e.getMessage());
			e.printStackTrace();
			return false;
		}
	}

	public static boolean sendResetPasswordEmail(String toEmail, String fullName, String resetLink) {
		String subject = "Đặt lại mật khẩu – NÕN CAFÉ";
		String html = buildResetPasswordHtml(fullName, resetLink);
		return sendEmail(toEmail, subject, html);
	}

	public static boolean sendOrderConfirmationEmail(String toEmail, String fullName, Long orderId,
			String orderDetailsHtml, String string, String paymentMethod, String address) {
		String subject = "Xác nhận đơn hàng #" + orderId + " – NÕN CAFÉ";
		String html = buildOrderConfirmationHtml(fullName, orderId, orderDetailsHtml, string, paymentMethod, address);
		return sendEmail(toEmail, subject, html);
	}

	private static String buildResetPasswordHtml(String fullName, String resetLink) {
		return "<!DOCTYPE html>" + "<html><head><meta charset='UTF-8'>" + "<style>"
				+ "body{font-family:'Segoe UI',Arial,sans-serif;background:#f5f5f0;margin:0;padding:20px}"
				+ ".container{max-width:560px;margin:0 auto;background:#fff;border-radius:12px;"
				+ "overflow:hidden;box-shadow:0 4px 20px rgba(0,0,0,0.1)}"
				+ ".header{background:linear-gradient(135deg,#2c1810,#5c3317);padding:32px;text-align:center}"
				+ ".header h1{color:#f5c842;margin:0;font-size:26px;letter-spacing:2px}"
				+ ".header p{color:#d4a96a;margin:6px 0 0;font-size:13px}" + ".body{padding:32px}"
				+ ".body h2{color:#2c1810;margin-top:0}" + ".body p{color:#555;line-height:1.7;font-size:15px}"
				+ ".btn{display:block;width:fit-content;margin:24px auto;padding:14px 36px;"
				+ "background:linear-gradient(135deg,#f5c842,#e8a020);color:#2c1810;"
				+ "text-decoration:none;border-radius:8px;font-weight:bold;font-size:15px}"
				+ ".warning{background:#fff8e1;border-left:4px solid #f5c842;"
				+ "padding:12px 16px;margin-top:20px;border-radius:4px;font-size:13px;color:#7a6000}"
				+ ".footer{background:#f9f9f7;padding:20px;text-align:center;"
				+ "color:#999;font-size:12px;border-top:1px solid #eee}" + "</style></head><body>"
				+ "<div class='container'>" + "<div class='header'>" + "<h1>☕ NÕN CAFÉ</h1>"
				+ "<p>Mang hương vị đến tận tay bạn</p>" + "</div>" + "<div class='body'>" + "<h2>Xin chào, " + fullName
				+ "!</h2>" + "<p>Chúng tôi nhận được yêu cầu đặt lại mật khẩu cho tài khoản của bạn.</p>"
				+ "<p>Nhấn vào nút bên dưới để tạo mật khẩu mới. "
				+ "Liên kết này sẽ hết hạn sau <strong>30 phút</strong>.</p>" + "<a href='" + resetLink
				+ "' class='btn'>🔑 Đặt lại mật khẩu</a>" + "<div class='warning'>"
				+ "⚠️ Nếu bạn không yêu cầu đặt lại mật khẩu, " + "hãy bỏ qua email này. Tài khoản của bạn vẫn an toàn."
				+ "</div>" + "<p style='margin-top:20px;font-size:13px;color:#999'>"
				+ "Hoặc copy link này vào trình duyệt:<br>" + "<span style='color:#5c3317;word-break:break-all'>"
				+ resetLink + "</span></p>" + "</div>" + "<div class='footer'>"
				+ "© 2025 NÕN CAFÉ | Đà Nẵng, Việt Nam<br>" + "Email này được gửi tự động, vui lòng không trả lời."
				+ "</div></div></body></html>";
	}

	private static String buildOrderConfirmationHtml(String fullName, Long orderId, String orderDetailsHtml,
			String string, String paymentMethod, String address) {
		return "<!DOCTYPE html>" + "<html><head><meta charset='UTF-8'>" + "<style>"
				+ "body{font-family:'Segoe UI',Arial,sans-serif;background:#f5f5f0;margin:0;padding:20px}"
				+ ".container{max-width:600px;margin:0 auto;background:#fff;border-radius:12px;"
				+ "overflow:hidden;box-shadow:0 4px 20px rgba(0,0,0,0.1)}"
				+ ".header{background:linear-gradient(135deg,#2c1810,#5c3317);padding:32px;text-align:center}"
				+ ".header h1{color:#f5c842;margin:0;font-size:26px;letter-spacing:2px}"
				+ ".header p{color:#d4a96a;margin:6px 0 0;font-size:13px}" + ".body{padding:32px}"
				+ ".order-id{background:#fff8e1;border:2px dashed #f5c842;border-radius:8px;"
				+ "padding:16px;text-align:center;margin-bottom:24px}"
				+ ".order-id span{font-size:22px;font-weight:bold;color:#2c1810}"
				+ "table{width:100%;border-collapse:collapse;margin:16px 0}"
				+ "th{background:#2c1810;color:#f5c842;padding:10px 12px;text-align:left;font-size:13px}"
				+ "td{padding:10px 12px;border-bottom:1px solid #f0e8dc;font-size:14px;color:#444}"
				+ "tr:last-child td{border-bottom:none}" + "tr:nth-child(even) td{background:#faf6f0}"
				+ ".total-row td{font-weight:bold;color:#2c1810;font-size:15px;"
				+ "border-top:2px solid #2c1810;background:#fff8e1!important}"
				+ ".info-box{background:#f9f6f2;border-radius:8px;padding:16px;margin-top:16px;"
				+ "font-size:14px;color:#555;line-height:1.8}" + ".info-box strong{color:#2c1810}"
				+ ".footer{background:#f9f9f7;padding:20px;text-align:center;"
				+ "color:#999;font-size:12px;border-top:1px solid #eee}" + "</style></head><body>"
				+ "<div class='container'>" + "<div class='header'>" + "<h1>☕ NÕN CAFÉ</h1>"
				+ "<p>Cảm ơn bạn đã đặt hàng!</p>" + "</div>" + "<div class='body'>"
				+ "<h2 style='color:#2c1810;margin-top:0'>Xin chào, " + fullName + "! 🎉</h2>"
				+ "<p style='color:#555;font-size:15px'>Đơn hàng của bạn đã được xác nhận. "
				+ "Chúng tôi sẽ chuẩn bị và giao đến bạn sớm nhất có thể!</p>" + "<div class='order-id'>"
				+ "Mã đơn hàng: <span>#" + orderId + "</span>" + "</div>" + "<table>"
				+ "<tr><th>Sản phẩm</th><th>SL</th><th>Đơn giá</th><th>Thành tiền</th></tr>" + orderDetailsHtml
				+ "<tr class='total-row'>" + "<td colspan='3'>TỔNG CỘNG</td>" + "<td>" + string + "</td></tr>"
				+ "</table>" + "<div class='info-box'>" + "<strong>📍 Địa chỉ giao hàng:</strong> " + address + "<br>"
				+ "<strong>💳 Phương thức thanh toán:</strong> " + paymentMethod + "<br>"
				+ "<strong>📞 Hỗ trợ:</strong> Liên hệ <a href='mailto:" + SENDER_EMAIL + "' style='color:#5c3317'>"
				+ SENDER_EMAIL + "</a>" + "</div>" + "</div>" + "<div class='footer'>"
				+ "© 2025 NÕN CAFÉ | Đà Nẵng, Việt Nam<br>" + "Email này được gửi tự động, vui lòng không trả lời."
				+ "</div></div></body></html>";
	}
}