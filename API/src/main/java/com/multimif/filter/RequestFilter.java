package com.multimif.filter;

import com.multimif.service.UserService;
import com.multimif.service.UserServiceImpl;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Filtre les requêtes entrantes et vérifie que l'utilisateur est connecté.
 */
public class RequestFilter implements Filter{

    /**
     * Configuration de la servlet
     */
    FilterConfig config;

    /**
     * Le service permettant de vérifier si l'utilisateur est connecté
     */
    UserService userService;

    /**
     * Définit la configuraiton du filter
     * @param config la config de la servlet
     */
    public void setFilterConfig(FilterConfig config) {
        this.config = config;
    }

    /**
     * Renvoi la configuration du filter
     * @return la config de la servlet
     */
    public FilterConfig getFilterConfig() {
        return config;
    }

    /**
     * Execute le filter
     * @param req la requête
     * @param res la réponse
     * @param chain le chaine
     * @throws java.io.IOException si il ne peut pas écrire la réponse
     * @throws ServletException si il n'arrive pas a accéder a la servlet
     */
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
                                    throws java.io.IOException, ServletException {


        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        Cookie[] cookies = request.getCookies();
        System.out.println("[API] [FILTER] url: " + request.getRequestURI());

        if(true || request.getRequestURI().equals("/api/user/login") || request.getRequestURI().equals("/api/user/")){
            chain.doFilter(req,res);
        }else{
            if(cookies == null){
                unauthorized(response,"Vous n'êtes pas connecté.");
            } else{
                String password = null;
                String username = null;

                for (int i = 0; i < cookies.length; i++) {
                    if(cookies[i].getName().equals("password")){
                        password = cookies[i].getValue();
                    }else if(cookies[i].getName().equals("username")){
                        username = cookies[i].getValue();
                    }
                }

                if(password == null || username == null){
                    unauthorized(response,"Vous n'êtes pas connecté.");
                }else{
                    try{

                        System.out.println("[API] [FILTER] username: " + username + " password: " + password);
                        userService.authEntity(username,password,true);
                        chain.doFilter(req,res);
                    }catch (Exception e){
                        unauthorized(response,"Mauvais identifiants.");
                    }
                }
            }
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

        /*System.out.println(((HttpServletRequest) req).getRequestURI());
        chain.doFilter(req,res);*/
    }

    /**
     * Intialise le filter
     * @param config la config de la servlet
     * @throws ServletException erreur dans la servlet
     */
    public void init(FilterConfig config) throws ServletException {
        this.config = config;
        userService = new UserServiceImpl();
    }

    /**
     * Détruit le filter
     */
    public void destroy() {
    }

    /**
     * Renvoi une erreur si l'utilisateur n'est pas connecté
     * @param response la requete réponse
     * @param message le message d'erreur
     * @throws java.io.IOException si il n'arrive pas a ecrire la réponse
     */
    private void unauthorized(HttpServletResponse response, String message) throws java.io.IOException {
        System.out.println("[API] [FILTER] [ERROR] user not connected.");
        response.sendError(401, message);
    }
}
