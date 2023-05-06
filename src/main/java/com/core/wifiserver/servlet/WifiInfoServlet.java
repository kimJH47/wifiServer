package com.core.wifiserver.servlet;

import com.core.wifiserver.client.SeoulPublicWifiClient;
import com.core.wifiserver.dto.Page;
import com.core.wifiserver.dao.WifiInfoDao;
import com.core.wifiserver.dto.request.WifiSearchRequest;
import com.core.wifiserver.service.PublicWifiSearchService;
import java.io.IOException;
import java.util.Objects;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet(value = {"/api/find-wifi", "/api/detail-wifi"})
public class WifiInfoServlet extends HttpServlet {
    private PublicWifiSearchService publicWifiSearchService;

    @Override
    public void init() throws ServletException {
        publicWifiSearchService = new PublicWifiSearchService(new SeoulPublicWifiClient(), new WifiInfoDao());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            if (Objects.equals("/api/find-wifi", req.getServletPath())) {
                findWifi(req, resp);
            } else {
                detailWifi(req, resp);
            }
            resp.setStatus(200);
        } catch (Exception e) {
            ServletUtils.createFailResponse(resp, e);
        } finally {
            resp.getWriter()
                    .close();
        }
    }

    private void detailWifi(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.getWriter()
                .print(ServletUtils.entityToResponseJson(publicWifiSearchService.findOne(req.getParameter("mgrNo"),
                        Double.parseDouble(req.getParameter("latitude")),
                        Double.parseDouble(req.getParameter("longitude")))));
    }

    private void findWifi(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Page page = new Page(Integer.parseInt(req.getParameter("pageNumber")), 20);
        WifiSearchRequest wifiSearchRequest = new WifiSearchRequest(page,
                Double.parseDouble(req.getParameter("latitude")),
                Double.parseDouble(req.getParameter("longitude")));
        resp.getWriter().print(ServletUtils.entityToResponseJson(
                publicWifiSearchService.findOrderByCoordinateWithPagination(wifiSearchRequest)));
    }

}
