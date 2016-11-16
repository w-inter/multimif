package main.java.Filter;

import javax.servlet.*;
import javax.servlet.http.*;


// Implements Filter class
public class AuthFilter implements Filter  {

    FilterConfig config;

    public void setFilterConfig(FilterConfig config) {
        this.config = config;
    }

    public FilterConfig getFilterConfig() {
        return config;
    }

    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws java.io.IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) req;
        System.out.println("[API] [FILTER] url: " + request.getRequestURI());



        if(request.getRequestURI().contains("/auth/connect")){
            chain.doFilter(req,res);
        }
        // On vérifie que l'utilisateur est connecté
        if(request.getSession() != null){
            chain.doFilter(req,res);
        }



        /*final String urlWrapped = ((HttpServletResponse) res)
        ServletRequest requestModified =
                new HttpServletRequestWrapper((HttpServletRequest) request) {
                    @Override
                    public String getRequestURI(){
                        return urlWrapped;
                    }
                };
*/

        System.out.println(((HttpServletRequest) req).getRequestURI());
        chain.doFilter(req,res);
    }

    public void init(FilterConfig config) throws ServletException {
        setFilterConfig(config);
    }

    public void destroy() {
    }


}
