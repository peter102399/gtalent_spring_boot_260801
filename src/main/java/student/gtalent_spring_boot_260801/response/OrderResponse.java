package student.gtalent_spring_boot_260801.response;

public class OrderResponse {
    private final String message;
    private final String orderNumber;
    private final long totalAmount;
    private final String status;

    public OrderResponse(String orderNumber, long totalAmount, String status) {
        this.message = "訂單已送出";
        this.orderNumber = orderNumber;
        this.totalAmount = totalAmount;
        this.status = status;
    }

    public String getMessage() { return message; }
    public String getOrderNumber() { return orderNumber; }
    public long getTotalAmount() { return totalAmount; }
    public String getStatus() { return status; }
}
