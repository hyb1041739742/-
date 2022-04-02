import java.time.LocalDateTime;

public class Solve {
    String startTime;
    int inDayLightSavingOfStartTime;
    String endTime;
    int inDayLightSavingOfEndTime;
    LocalDateTime localDateTime1;
    LocalDateTime localDateTime2;

    public Solve(String startTime, int inDayLightSavingOfStartTime, String endTime, int inDayLightSavingOfEndTime) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.inDayLightSavingOfEndTime = inDayLightSavingOfEndTime;
        this.inDayLightSavingOfStartTime = inDayLightSavingOfStartTime;
    }

    public String getResults() {
        if(startTime.compareTo(endTime) > 0){
            return "输入错误";
        }
        else{
            this.localDateTime1 = LocalDateTime.of(
                    Integer.parseInt(startTime.substring(0, 4)), //2022
                    Integer.parseInt(startTime.substring(4, 6)), //04
                    Integer.parseInt(startTime.substring(6, 8)),//02
                    Integer.parseInt(startTime.substring(8, 10)),//15
                    Integer.parseInt(startTime.substring(10, 12)),//57
                    Integer.parseInt(startTime.substring(12, 14))//32
            );
        }
        this.localDateTime2 = LocalDateTime.of(
                Integer.parseInt(endTime.substring(0, 4)), //2022
                Integer.parseInt(endTime.substring(4, 6)), //04
                Integer.parseInt(endTime.substring(6, 8)),//02
                Integer.parseInt(endTime.substring(8, 10)),//15
                Integer.parseInt(endTime.substring(10, 12)),//57
                Integer.parseInt(endTime.substring(12, 14))//32
        );

        CalculateTime calculateTime = new CalculateTime(localDateTime1, inDayLightSavingOfStartTime, localDateTime2, inDayLightSavingOfEndTime);
        if(calculateTime.getTime() > 60*30)
            return "超过30小时";

        CalculateBill calculateBill = new CalculateBill(calculateTime.getTime());
        //return String.valueOf(localDateTime1);
        //return String.valueOf(calculateTime.getTime());
        return String.format("%.2f", calculateBill.getBill());
    }
}
