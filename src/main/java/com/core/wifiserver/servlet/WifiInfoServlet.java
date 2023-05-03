package com.core.wifiserver.servlet;

import com.core.wifiserver.client.SeoulPublicWifiClient;
import com.core.wifiserver.dao.Page;
import com.core.wifiserver.dao.WifiInfoDao;
import com.core.wifiserver.dto.request.Request;
import com.core.wifiserver.dto.request.WifiSearchRequest;
import com.core.wifiserver.service.PublicWifiSearchService;
import com.google.gson.Gson;
import java.io.IOException;
import java.util.Objects;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet(value = {"/find-wifi", "/detail-wifi"})
public class WifiInfoServlet extends HttpServlet {
    private PublicWifiSearchService publicWifiSearchService;

    @Override
    public void init() throws ServletException {
        publicWifiSearchService = new PublicWifiSearchService(new SeoulPublicWifiClient(), new WifiInfoDao());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        try {
            if (Objects.equals("/find-wifi", req.getServletPath())) {
                findWifi(req, resp);
            } else {
                detailWifi(req, resp);
            }
        } catch (Exception e) {
            ServletUtils.createFailResponse(resp, e);
        } finally {
            resp.getWriter()
                    .close();
        }
    }

    private void detailWifi(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.getWriter().print(new Gson().
                toJson(publicWifiSearchService.findOne(req.getParameter("mgrNo"),
                        Double.parseDouble(req.getParameter("latitude")),
                        Double.parseDouble(req.getParameter("longitude")))
                ));
    }

    private void findWifi(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Page page = new Page(Integer.parseInt(req.getParameter("pageNumber")), 20);
        Request<WifiSearchRequest> request = new Request<>(new WifiSearchRequest(page,
                Double.parseDouble(req.getParameter("latitude")),
                Double.parseDouble(req.getParameter("longitude")
                )));
        String s = new Gson()
                .toJson(publicWifiSearchService.findOrderByCoordinateWithPagination(request));
        resp.getWriter().print(s);

    }

}
