package uz.pdp.agrarmarket.dashboard;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DashboardResponseDto {
    private long countUsers;
    private long countPost;
    private double userIncreasedPercentageOneMonth;
    private double postIncreasedPercentageOneMonth;
    private int [] yesterdayUserRequestHourByHour;
    private int [] todayUserRequestHourByHour;
}
