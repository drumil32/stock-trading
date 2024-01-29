package stock_trading.stock;

import stock_trading.exception.InsufficientStockQuantity;

public class UserStock extends Stock{
    private Integer quantity;
    public UserStock(String stockSymbol,Category category,Integer price,Integer quantity){
        super(stockSymbol,category,price);
        this.quantity = quantity;
    }
    public UserStock(Stock stock,Integer quantity){
        super(stock);
        this.quantity = quantity;
    }
    public Integer getQuantity(){
        return quantity;
    }
    @Override
    public String toString() {
        return String.format("%-10s | %-10s | %-5d | Quantity: %-3d", super.getStockSymbol(), super.getCategory(), super.getPrice(), quantity);
    }

    public void removeQuantity(int quantity) throws InsufficientStockQuantity{
        if( this.quantity<quantity )    throw new InsufficientStockQuantity();
        this.quantity -= quantity;
    }

    public void addQuantity(Integer quantity, Integer price) {
        Integer totalPrice = super.getPrice()*this.quantity + (quantity*price) ;
        Integer totalQuantity = this.quantity+quantity;
        this.quantity = totalQuantity;
        super.setPrice(totalPrice/totalQuantity);
    }
}
