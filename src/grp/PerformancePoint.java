package grp;

import com.zzw.data.Performance;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Calendar;

/**
 * @author GRP
 */
public class PerformancePoint
{
    private int hour_x;
    private int engagement_y;
    private int efficiency_y;

    public PerformancePoint(Performance performance)
    {
        this.engagement_y = performance.getEngagement();
        this.efficiency_y = performance.getEfficiency();

        // 下面需要对hour_x进行初始化
        Date time = new Date();
        time.setTime(performance.getTime());

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(time);

        this.hour_x = calendar.get(Calendar.HOUR_OF_DAY); // 返回的是24小时制
    }

    public int getHour_x() { return hour_x; }

    public int getEngagement_y() { return engagement_y; }

    public int getEfficiency_y() { return efficiency_y; }

    @Override
    public String toString()
    {
        return String.format("[x:%d, eng_y:%d, eff_y:%d]", hour_x, engagement_y, efficiency_y);
    }
}
