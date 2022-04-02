public class CalculateBill {
    long Minutes;
    double bill;
    public CalculateBill(long Minutes){
        this.Minutes = Minutes;
        calculateBill();
    }




    public void calculateBill(){
        if(Minutes <= 20){
            bill = 0.05*Minutes;
        }
        else{
            bill = 1 + (Minutes-20)*0.1;
        }
    }

    public double getBill(){
        return bill;
    }
}
