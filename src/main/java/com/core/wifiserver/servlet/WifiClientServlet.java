package com.core.wifiserver.servlet;


import com.core.wifiserver.client.PublicApiResponseClient;
import com.core.wifiserver.dao.WifiInfoDao;
import com.core.wifiserver.dto.response.Response;
import com.core.wifiserver.service.PublicWifiSearchService;
import com.google.gson.Gson;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "WifiAddServlet", value = "/add-wifi")
public class WifiClientServlet extends HttpServlet {

    private PublicWifiSearchService publicWifiSearchService;

    @Override
    public void init() throws ServletException {
        publicWifiSearchService = new PublicWifiSearchService(new PublicApiResponseClient(), new WifiInfoDao());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("utf-8");
        try {
            Response<Integer> response = publicWifiSearchService.addPublicWifi();
            String json = new Gson().toJson(response);
            resp.getWriter().print(json);
        } catch (Exception e) {
            ServletUtils.createFailResponse(resp, e);
        } finally {
            resp.getWriter().close();
        }
    }

}
