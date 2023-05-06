package com.core.wifiserver.servlet;


import com.core.wifiserver.dao.HistoryDao;
import com.core.wifiserver.dto.request.HistorySaveRequest;
import com.core.wifiserver.service.HistoryService;
import com.google.gson.JsonObject;
import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(value = "/api/history")
public class HistoryServlet extends HttpServlet {
    private HistoryService historyService;

    @Override
    public void init() {
        historyService = new HistoryService(new HistoryDao());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        try {
            JsonObject jsonObject = ServletUtils.createJsonObject(req);
            HistorySaveRequest request = new HistorySaveRequest(
                    jsonObject.get("latitude").getAsDouble(),
                    jsonObject.get("longitude").getAsDouble());
            resp.getWriter().print(ServletUtils.entityToResponseJson(historyService.save(request)));
            resp.setStatus(200);
        } catch (Exception e) {
            ServletUtils.createFailResponse(resp, e);
        } finally {
            resp.getWriter().close();
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        try {
            resp.getWriter().print(ServletUtils.entityToResponseJson(historyService.findAll()));
            resp.setStatus(200);
        } catch (Exception e) {
            ServletUtils.createFailResponse(resp, e);
        } finally {
            resp.getWriter().close();
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            resp.getWriter().print(ServletUtils.entityToResponseJson(
                    historyService.delete(ServletUtils.createJsonObject(req).get("id").getAsInt())));
            resp.setStatus(200);
        } catch (Exception e) {
            ServletUtils.createFailResponse(resp, e);
        } finally {
            resp.getWriter().close();
        }
    }
}
