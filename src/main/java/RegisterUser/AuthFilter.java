package RegisterUser;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebFilter(filterName = "AuthFilter", urlPatterns = {"/useraccount.jsp", "/updateuser.jsp", "/deleteuser.jsp", "/uploadvideo.jsp"})
public class AuthFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Initialization code if needed
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        
        String token = null;
        Cookie[] cookies = httpRequest.getCookies();
        
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("authToken".equals(cookie.getName())) {
                    token = cookie.getValue();
                    break;
                }
            }
        }
        
        if (token != null && JWTUtil.validateToken(token)) {
            // Token is valid, proceed with the request
            String username = JWTUtil.getUsernameFromToken(token);
            httpRequest.setAttribute("authenticatedUser", username);
            chain.doFilter(request, response);
        } else {
            // Token is missing or invalid, redirect to login
            httpResponse.sendRedirect("login.jsp?error=unauthorized");
        }
    }

    @Override
    public void destroy() {
        // Cleanup code if needed
    }
}
