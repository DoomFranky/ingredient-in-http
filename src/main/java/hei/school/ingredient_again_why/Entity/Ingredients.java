package hei.school.ingredient_again_why.Entity;
import java.time.Instant;
import java.util.List;

public class Ingredients {
    private Integer id;
    private String name;
    private Double price;
    private CategoryEnum category;
    private List<StockMouvement> stockMouvementList;
    
    public Ingredients() {
    }
    public Ingredients(Integer id, String name, Double price, CategoryEnum category,
            List<StockMouvement> stockMouvementList) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.category = category;
        this.stockMouvementList = stockMouvementList;
    }
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Double getPrice() {
        return price;
    }
    public void setPrice(Double price) {
        this.price = price;
    }
    public CategoryEnum getCategory() {
        return category;
    }
    public void setCategory(CategoryEnum category) {
        this.category = category;
    }
    public List<StockMouvement> getStockMouvementList() {
        return stockMouvementList;
    }
    public void setStockMouvementList(List<StockMouvement> stockMouvementList) {
        if (stockMouvementList==null) {
            this.stockMouvementList = null;
            return;
        }
        for(StockMouvement stockMouvement: stockMouvementList){
            stockMouvement.setIngredients(this);
        }
        this.stockMouvementList = stockMouvementList;
    }

    public StockValue getStockValueAt(Instant t){
        return new StockValue(
            stockMouvementList.stream().filter(sm->sm.getCreationDateTime().isBefore(t)).mapToDouble(sm->sm.getType()==MovementTypeEnum.OUT ? sm.getValue().getQuantity()*-1 : sm.getValue().getQuantity()).sum(),
            stockMouvementList.stream().map(sm->sm.getValue().getUnit()).findAny().orElse(null)
        );
    }
    @Override
    public String toString() {
        return "Ingredients [id=" + id + ", name=" + name + ", price=" + price + ", category=" + category
                + ", stockMouvementList=" + stockMouvementList + "]";
    }

    

    
}
