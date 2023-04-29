package com.core.wifiserver.servlet;

import static com.core.wifiserver.servlet.ServletUtils.createFailResponse;
import static com.core.wifiserver.servlet.ServletUtils.createJsonObject;
import static com.core.wifiserver.servlet.ServletUtils.entityToJson;

import com.core.wifiserver.dao.BookmarkGroupDao;
import com.core.wifiserver.dto.BookmarkGroupDto;
import com.core.wifiserver.dto.request.Request;
import com.core.wifiserver.dto.response.Response;
import com.core.wifiserver.service.BookmarkGroupService;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.util.Objects;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "BookmarkGroupServlet", value = {"/bookmark-group", "/update-bookmark-group"})
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
        } catch (Exception e) {
            createFailResponse(resp, e);
        } finally {
            resp.getWriter().close();
        }
    }

    private void saveBookmarkGroup(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        JsonObject jsonObject = createJsonObject(req);
        BookmarkGroupDto bookmarkGroupDto = new BookmarkGroupDto(0,
                jsonObject.get("name").getAsString(),
                jsonObject.get("orders").getAsInt(),
                "", "");
        resp.getWriter()
                .print(entityToJson(bookmarkGroupService.save(new Request<>(bookmarkGroupDto))));
    }

    private void updateBookmarkGroup(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        JsonObject jsonObject = createJsonObject(req);
        Response<Integer> response = bookmarkGroupService.update(new Request<>(new BookmarkGroupDto(
                jsonObject.get("id").getAsInt(),
                jsonObject.get("name").getAsString(),
                jsonObject.get("orders").getAsInt(),
                "", "")));
        resp.getWriter().print(entityToJson(response));

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        try {
            resp.getWriter().print(new Gson()
                    .toJson(bookmarkGroupService.findAll()));
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
                    .print(entityToJson(bookmarkGroupService.delete(createJsonObject(req)
                            .get("id")
                            .getAsInt())));

        } catch (Exception e) {
            createFailResponse(resp, e);
        }
    }
}
