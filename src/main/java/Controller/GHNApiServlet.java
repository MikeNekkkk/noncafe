package Controller;

import Service.GHNService;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import org.json.JSONArray;
import org.json.JSONObject;
@WebServlet("/ghn-api")
public class GHNApiServlet extends HttpServlet {

    private GHNService ghn;

    @Override
    public void init() {
        ghn = new GHNService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json; charset=UTF-8");
        resp.setHeader("Cache-Control", "public, max-age=3600"); // cache 1 giờ

        String action = req.getParameter("action");
        JSONObject out = new JSONObject();

        try {
            switch (action == null ? "" : action) {

                case "provinces": {
                    List<Map<String, Object>> list = ghn.getProvinces();
                    JSONArray arr = new JSONArray();
                    for (Map<String, Object> m : list) {
                        JSONObject o = new JSONObject();
                        o.put("id",   m.get("ProvinceID"));
                        o.put("name", m.get("ProvinceName"));
                        arr.put(o);
                    }
                    out.put("success", true);
                    out.put("data", arr);
                    break;
                }

                case "districts": {
                    int provinceId = Integer.parseInt(req.getParameter("provinceId"));
                    List<Map<String, Object>> list = ghn.getDistricts(provinceId);
                    JSONArray arr = new JSONArray();
                    for (Map<String, Object> m : list) {
                        JSONObject o = new JSONObject();
                        o.put("id",   m.get("DistrictID"));
                        o.put("name", m.get("DistrictName"));
                        arr.put(o);
                    }
                    out.put("success", true);
                    out.put("data", arr);
                    break;
                }
			case "wards": {
                    int districtId = Integer.parseInt(req.getParameter("districtId"));
                    List<Map<String, Object>> list = ghn.getWards(districtId);
                    JSONArray arr = new JSONArray();
                    for (Map<String, Object> m : list) {
                        JSONObject o = new JSONObject();
                        o.put("code", m.get("WardCode"));
                        o.put("name", m.get("WardName"));
                        arr.put(o);
                    }
                    
                    out.put("success", true);
                    out.put("data", arr);
                    System.out.println("districtId raw = " + req.getParameter("districtId"));
                    break;
                }

                case "fee": {
                    int    districtId = Integer.parseInt(req.getParameter("districtId"));
                    String wardCode   = req.getParameter("wardCode");
                    int    weight     = req.getParameter("weight") != null
                                        ? Integer.parseInt(req.getParameter("weight")) : 500;
                    BigDecimal fee = ghn.calculateShippingFee(districtId, wardCode, weight);
                    out.put("success", true);
                    out.put("fee", fee.longValue());
                    break;
                }

                default:
                    out.put("success", false);
                    out.put("message", "Unknown action");
                    resp.setStatus(400);
            }
        } catch (Exception e) {
            out.put("success", false);
            out.put("message", e.getMessage());
            resp.setStatus(500);
        }

        resp.getWriter().write(out.toString());
    }
}