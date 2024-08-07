package com.binaklet.binaklet.controllers;
import com.binaklet.binaklet.DTOs.AdminOrderDto;
import com.binaklet.binaklet.entities.Order;
import dto.requests.Admin.AdminOrderCreateRequest;
import com.binaklet.binaklet.services.admin.AdminOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.binaklet.binaklet.exceptions.ApiRequestException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/orders")
public class AdminOrderController {
    private final AdminOrderService adminOrderService;
//    private final OrderService orderService;


    @PostMapping()
    public ResponseEntity<Order> createOrder(@RequestBody AdminOrderCreateRequest request){
        System.out.println(request.toString());
        if(request.getItemIds().length<1 || request.getSellerId()==null || request.getDeliverAddress()==null || request.getPickUpAddress()==null){
            return ResponseEntity.badRequest().body(null);
        }
        Order createdOrder =adminOrderService.create(request);
        if(createdOrder!=null){
            return ResponseEntity.ok(createdOrder);
        }
        else{
            return  ResponseEntity.badRequest().body(null);
        }

    }

    @GetMapping
    public List<AdminOrderDto> getAllOrders(){
        return adminOrderService.getAll();
    }

    @GetMapping("/{orderId}")
    public void getOrder(@PathVariable String orderId) {
        try {
            Long id = Long.parseLong(orderId);
            adminOrderService.getById(id);
        } catch (NumberFormatException e) {
            throw new ApiRequestException("Lütfen geçerli bir ürün kimliği giriniz");
        } catch (Exception e) {
            throw e;
        }
    }

    @DeleteMapping("/{orderId}/delete")
    public void deleteOrder(@PathVariable String orderId) {
        try {
            Long id = Long.parseLong(orderId);
            adminOrderService.deleteOrderById(id);
        } catch (NumberFormatException e) {
            throw new ApiRequestException("Lütfen geçerli bir ürün kimliği giriniz");
        } catch (Exception e) {
            throw e;
        }
    }

    @GetMapping("/{userId}/orders")
    public List<Order> getUserOrders(@PathVariable String userId) {
        try {
            Long id = Long.parseLong(userId);
            return adminOrderService.getUserOrders(id);
        } catch (NumberFormatException e) {
            throw new ApiRequestException("Lütfen geçerli bir ürün kimliği giriniz");
        } catch (Exception e) {
            throw e;
        }
    }


}
