package com.core.wifiserver.servlet;


import com.core.wifiserver.client.SeoulPublicWifiClient;
import com.core.wifiserver.dao.WifiInfoDao;
import com.core.wifiserver.service.PublicWifiSearchService;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "WifiAddServlet", value = "/api/add-wifi")
public class WifiClientServlet extends HttpServlet {

    private PublicWifiSearchService publicWifiSearchService;

    @Override
    public void init() throws ServletException {
        publicWifiSearchService = new PublicWifiSearchService(new SeoulPublicWifiClient(), new WifiInfoDao());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("utf-8");
        try {
            int entity = publicWifiSearchService.addPublicWifi();
            resp.getWriter().print(ServletUtils.entityToResponseJson(entity));
            resp.setStatus(200);
        } catch (Exception e) {
            ServletUtils.createFailResponse(resp, e);
        } finally {
            resp.getWriter().close();
        }
    }

}
