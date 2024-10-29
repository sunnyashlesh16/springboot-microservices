package com.sunny.order_service.domain;

import org.springframework.data.jpa.repository.JpaRepository;

/*
Order Repository is considered as the aggregated root.
As we are not going to set the order Item Entity because if
you need to do changes to the orders we must do it through this.
Because without the order there will not be order items.
*/

interface OrderRepository extends JpaRepository<OrderEntity, Long> {}
