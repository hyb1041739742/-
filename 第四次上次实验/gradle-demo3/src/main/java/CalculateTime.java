import java.time.LocalDateTime;

public class CalculateTime {
    LocalDateTime startTime;
    LocalDateTime endTime;
    int inDayLightSavingOfStartTime;
    int inDayLightSavingOfEndTime;
    long Millis;
    long Minutes;
    public CalculateTime(LocalDateTime startTime, int inDayLightSavingOfStartTime, LocalDateTime endTime, int inDayLightSavingOfEndTime){
        this.startTime = startTime;
        this.endTime = endTime;
        this.inDayLightSavingOfEndTime = inDayLightSavingOfEndTime;
        this.inDayLightSavingOfStartTime = inDayLightSavingOfStartTime;
    }


    public void calculateMinutes(){
        if(Millis %(60*1000) == 0){
            this.Minutes = Millis/(60*1000);
        }
        else {
            this.Minutes = Millis /(60*1000)+1;
        }
    }

    long getTime(){
        java.time.Duration duration = java.time.Duration.between(startTime,  endTime );
        if(inDayLightSavingOfStartTime == 0 && inDayLightSavingOfEndTime == 1){
            this.Millis = duration.toMillis()-60*60*1000;
        }
        else if(inDayLightSavingOfStartTime == 1 && inDayLightSavingOfEndTime == 0)
            this.Millis = duration.toMillis()+60*60*1000;
        else
            this.Millis = duration.toMillis();

        calculateMinutes();

        return this.Minutes;
    }

    public long getMillis() {
        return Millis;
    }
}
