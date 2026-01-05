package Pizzeria.config;

import Pizzeria.service.OrderService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component
public class ModelAttributeInterceptor implements HandlerInterceptor {

    private final OrderService orderService;

    //umožňuje zachytiť spracovanie požiadavky pred alebo po dokončení kontroléra
    public ModelAttributeInterceptor(OrderService orderService) {
        this.orderService = orderService;
    }


    //kontrolér už prebehol, model existuje, view sa ešte len bude renderovať
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response,
                          Object handler, ModelAndView modelAndView) {
        
        if (modelAndView == null) {
            return;
        }

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        
        if (auth != null && auth.isAuthenticated() && !auth.getName().equals("anonymousUser")) {
            boolean isCook = auth.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .anyMatch(a -> a.equals("ROLE_KUCHAR"));
            
            if (isCook) {
                long newOrdersCount = orderService.countNewUnassignedOrders();
                modelAndView.addObject("newOrdersCount", newOrdersCount);
            }
        }
    }
}

