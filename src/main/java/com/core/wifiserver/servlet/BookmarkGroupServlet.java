package com.core.wifiserver.servlet;

import static com.core.wifiserver.servlet.ServletUtils.createFailResponse;
import static com.core.wifiserver.servlet.ServletUtils.createJsonObject;
import static com.core.wifiserver.servlet.ServletUtils.entityToResponseJson;

import com.core.wifiserver.dao.BookmarkGroupDao;
import com.core.wifiserver.dto.request.BookmarkGroupSaveRequest;
import com.core.wifiserver.dto.request.BookmarkGroupUpdateRequest;
import com.core.wifiserver.service.BookmarkGroupService;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.util.Objects;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "BookmarkGroupServlet", value = {"/api/bookmark-group", "/api/update-bookmark-group"})
public class BookmarkGroupServlet extends HttpServlet {

    private BookmarkGroupService bookmarkGroupService;

    @Override
    public void init() {
        bookmarkGroupService = new BookmarkGroupService(new BookmarkGroupDao());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        try {
            if (Objects.equals(req.getServletPath(), "/bookmark-group")) {
                saveBookmarkGroup(req, resp);
            } else {
                updateBookmarkGroup(req, resp);
            }
            resp.setStatus(200);
        } catch (Exception e) {
            createFailResponse(resp, e);
        } finally {
            resp.getWriter().close();
        }
    }

    private void saveBookmarkGroup(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        JsonObject jsonObject = createJsonObject(req);
        resp.getWriter()
                .print(entityToResponseJson(bookmarkGroupService.save(BookmarkGroupSaveRequest.create(jsonObject))));
    }

    private void updateBookmarkGroup(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        JsonObject jsonObject = createJsonObject(req);
        resp.getWriter().print(entityToResponseJson(
                bookmarkGroupService.update(BookmarkGroupUpdateRequest.create(jsonObject))));

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        try {
            resp.getWriter().print(ServletUtils.entityToResponseJson(bookmarkGroupService.findAll()));
            resp.setStatus(200);
        } catch (Exception e) {
            createFailResponse(resp, e);
        } finally {
            resp.getWriter().close();
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        try {
            resp.getWriter()
                    .print(entityToResponseJson(bookmarkGroupService.delete(createJsonObject(req)
                            .get("id")
                            .getAsInt())));
            resp.setStatus(200);
        } catch (Exception e) {
            createFailResponse(resp, e);
        }
    }
}
