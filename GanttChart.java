public class GanttChart {
	// Object to create Gantt Chart
	int OldTime = 0, ProcessNumber = -1;
	String GanttChartOutput = "";
	public GanttChart() {
		// TODO Auto-generated constructor stub
	}
	public void Add(int CurrentTime, int ProcessNum)
	{
		if(ProcessNumber == ProcessNum)
		{
			return;
		}
		else
		{
			setProcessNumber(ProcessNum);
			setOldTime(CurrentTime);
			GanttChartOutput += OldTime + " - P" + ProcessNum + " - ";
		}
	}
	public void Finalize(int CurrentTime)
	{
		GanttChartOutput += CurrentTime;
	}
	public String GanttChartCreate()
	{
		return GanttChartOutput;
	}
	public void setProcessNumber(int p)
	{
		ProcessNumber = p;
	}
	public void setOldTime(int o)
	{
		OldTime = o;
	}
}
