package com.example.w5.controller;

import com.example.w5.dto.MemberDTO;
import com.example.w5.service.MemberService;
import lombok.extern.java.Log;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.UUID;


@WebServlet("/login")
@Log
public class LoginController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("login get........");
        req.getRequestDispatcher("/WEB-INF/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("login post...");

        String mid = req.getParameter("mid");
        String mpw = req.getParameter("mpw");

        //auto 라는 이름으로 체크박스에서 전송되는값이 on인지 확인 (p.205)
        String auto = req.getParameter("auto");

        boolean rememberMe = auto != null && auto.equals("on");

        //

        try {
            MemberDTO memberDTO = MemberService.INSTANCE.login(mid, mpw);

            if (rememberMe) {
                String uuid = UUID.randomUUID().toString();

                MemberService.INSTANCE.updateUuid(mid, uuid);
                memberDTO.setUuid(uuid);

                Cookie rememberCookie =
                        new Cookie("remember-me", uuid);
                rememberCookie.setMaxAge(60*60*24*7);  //유효기간 1주일로 설정
                rememberCookie.setPath("/");

                resp.addCookie(rememberCookie);

            }

            HttpSession session = req.getSession();
            session.setAttribute("loginInfo", memberDTO);
            resp.sendRedirect("/todo/list");
        } catch (Exception e) {
            resp.sendRedirect("/login?result=error");
        }

//        String str = mid+mpw;
//
//        HttpSession session = req.getSession();
//
//        session.setAttribute("loginInfo", str);
//
//        resp.sendRedirect("/todo/list");

    }
}
