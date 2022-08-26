package com.rayen.task.manager.Services.formats;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class forChartDashboard {
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd")
    private Date date;
    private int numberOfTasks;
}
