package com.jpabook.com.jpashop.controller;

import com.jpabook.com.jpashop.domain.Item.Item;
import com.jpabook.com.jpashop.domain.Member;
import com.jpabook.com.jpashop.domain.Order;
import com.jpabook.com.jpashop.repository.OrderSearch;
import com.jpabook.com.jpashop.service.ItemService;
import com.jpabook.com.jpashop.service.MemberService;
import com.jpabook.com.jpashop.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderServcie;
    private final MemberService memberService;
    private final ItemService itemService;

    @GetMapping("/order")
    public String createForm(Model model) {
        List<Member> members = memberService.findMembers();
        List<Item> items = itemService.findItems();

        model.addAttribute("members", members);
        model.addAttribute("items", items);

        return "order/orderForm";
    }

    @PostMapping("/order")
    public String order(@RequestParam("memberId") Long memberId,
                        @RequestParam("itemId") Long itemId,
                        @RequestParam("count") int count) {
        orderServcie.order(memberId, itemId, count);
        return "redirect:/orders";

    }

    @GetMapping("/orders")
    public String orderList(@ModelAttribute("orderSearch") OrderSearch orderSearch, Model model) {

        List<Order> orders = orderServcie.findOrders(orderSearch);
        model.addAttribute("orders", orders);

        return "order/orderList";
    }

    @PostMapping("/orders/{orderId}/cancel")
    public String cancelOrder(@PathVariable("orderId") Long orderId) {
        orderServcie.cancelOrder(orderId);
        return "redirect:/orders";
    }






}
