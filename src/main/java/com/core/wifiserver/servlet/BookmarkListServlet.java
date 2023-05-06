package com.core.wifiserver.servlet;

import com.core.wifiserver.dao.BookmarkListDao;
import com.core.wifiserver.dto.request.BookmarkSaveRequest;
import com.core.wifiserver.service.BookmarkListService;
import com.google.gson.JsonObject;
import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet(value = "/api/bookmark-list")
public class BookmarkListServlet extends HttpServlet {

    private BookmarkListService bookmarkListService;

    @Override
    public void init() {
        bookmarkListService = new BookmarkListService(new BookmarkListDao());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            JsonObject jsonObject = ServletUtils.createJsonObject(req);
            BookmarkSaveRequest request = new BookmarkSaveRequest(
                    jsonObject.get("bookmarkGroupId").getAsInt(),
                    jsonObject.get("wifiName").getAsString());
            resp.getWriter().print(ServletUtils.entityToResponseJson(bookmarkListService.save(request)));
            resp.setStatus(200);
        } catch (Exception e) {
            ServletUtils.createFailResponse(resp, e);
        } finally {
            resp.getWriter().close();
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            resp.getWriter().print(ServletUtils.entityToResponseJson(bookmarkListService.findAll()));
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
            JsonObject jsonObject = ServletUtils.createJsonObject(req);
            resp.getWriter().print(ServletUtils.entityToResponseJson(
                    bookmarkListService.delete(jsonObject.get("id").getAsInt())));
            resp.setStatus(200);
        } catch (Exception e) {
            ServletUtils.createFailResponse(resp, e);
        } finally {
            resp.getWriter().close();
        }
    }
}
