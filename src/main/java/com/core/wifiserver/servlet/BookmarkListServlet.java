package com.core.wifiserver.servlet;

import com.core.wifiserver.dao.BookmarkListDao;
import com.core.wifiserver.dto.request.BookmarkSaveRequest;
import com.core.wifiserver.dto.request.Request;
import com.core.wifiserver.dto.response.Response;
import com.core.wifiserver.service.BookmarkListService;
import com.google.gson.JsonObject;
import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet(value = "/bookmark-list")
public class BookmarkListServlet extends HttpServlet {

    private BookmarkListService bookmarkListService;

    @Override
    public void init() {
        bookmarkListService = new BookmarkListService(new BookmarkListDao());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        try {
            JsonObject jsonObject = ServletUtils.createJsonObject(req);
            Response<Integer> response = bookmarkListService.save(new Request<>(
                    new BookmarkSaveRequest(jsonObject.get("bookmarkGroupId").getAsInt(),
                            jsonObject.get("wifiName").getAsString())
            ));
            resp.getWriter().print(ServletUtils.entityToJson(response));
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
            resp.getWriter().print(ServletUtils.entityToJson(bookmarkListService.findAll()));
        } catch (Exception e) {
            ServletUtils.createFailResponse(resp, e);
        } finally {
            resp.getWriter().close();
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        try {
            JsonObject jsonObject = ServletUtils.createJsonObject(req);
            Response<Integer> response = bookmarkListService.delete(jsonObject.get("id").getAsInt());
            resp.getWriter().print(ServletUtils.entityToJson(response));
        } catch (Exception e) {
            ServletUtils.createFailResponse(resp, e);
        } finally {
            resp.getWriter().close();
        }
    }
}
