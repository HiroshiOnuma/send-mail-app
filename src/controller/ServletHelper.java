package controller;

import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class ServletHelper {

    /**
     * リクエストからエンティティを取得する共通処理
     * 
     * @param <T>
     * @param req
     * @param res
     * @param paramName
     * @param fetcher
     * @param redirectPath
     * @return 見つかったエンティティ、または null
     * @throws IOException
     */
    public static <T> T fetchEntityOrRedirect(HttpServletRequest req, HttpServletResponse res, String paramName,
            EntityFetcher<T> fetcher, String redirectPath) throws IOException {
        String param = req.getParameter(paramName);
        if (param == null || param.isEmpty()) {
            res.sendRedirect(redirectPath);
            return null;
        }

        try {
            int id = Integer.parseInt(param);
            T entity = fetcher.fetchById(id);

            if (entity == null) {
                res.sendRedirect(redirectPath);
                return null;
            }
            return entity;
        } catch (NumberFormatException e) {
            res.sendRedirect(redirectPath);
            return null;
        }
    }

    /**
     * EntityFetcher インターフェース
     */
    public interface EntityFetcher<T> {
        T fetchById(int id);
    }

    /**
     * リクエストからIDを取得して処理を実行し、結果に応じてリダイレクトまたはフォワードする。
     * 
     * @param req
     * @param res
     * @param idParamName
     * @param redirectPath
     * @param operator
     * @param successRedirect
     * @param errorForward
     * @param errorMessage
     * @return 処理が成功したかどうか 成功ならtrue そうでなければfalse。
     * @throws IOException
     * @throws ServletException
     */
    public static boolean processEntityOperation(HttpServletRequest req, HttpServletResponse res,
            String idParamName, String redirectPath, Operator operator, String successRedirect, String errorForward,
            String errorMessage) throws IOException, ServletException {
        String idParam = req.getParameter(idParamName);
        if (idParam == null || idParam.isEmpty()) {
            res.sendRedirect(redirectPath);
            return false;
        }

        try {
            int id = Integer.parseInt(idParam);
            boolean operated = operator.operate(id);

            if (operated) {
                res.sendRedirect(successRedirect);
            } else {
                req.setAttribute("error", errorMessage);
                req.getRequestDispatcher(errorForward).forward(req, res);
            }
            return operated;
        } catch (NumberFormatException e) {
            res.sendRedirect(redirectPath);
            return false;
        }
    }

    /**
     * Operator インターフェース
     */
    public interface Operator {
        boolean operate(int id);
    }

}
