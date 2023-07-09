import java.util.Scanner;
import java.io.*;
import java.util.ArrayList;

class Main {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);
    System.out.println("\t\t\t\t\t\t  CPU SCHEDULING");
    System.out.println("Developer: \n"
                    +  "\tSean Rizarre Reyes \n");

String choice;
      do{
            System.out.println("\nChoose your CPU/Disk Scheduling method: \n"
                            +  "[ A ] Shortest Remaining time First\n"
                            +  "[ B ] Round Robin\n"
                            +  "[ C ] Round Robin with Overhead\n"
                            +  "[ D ] Preemptive Priority\n"
                            +  "[ E ] Multi-level Feedback Queue\n"
                            +  "[ F ] Exit\n"
                            +  "Enter Choice: ");

          choice = sc.nextLine();
          switch(choice){
            //SRTF
            case "A":
            case "a":
            try{ 
              SRTF();
              
            }
            catch(IOException e){
              e.printStackTrace();
            }
            break;

            //Round Robin
            case "B":
            case "b": 
              try{ 
              RR();
            }
            catch(IOException e){
              e.printStackTrace();
            }
            break;

            //RRO
            case "C":
            case "c":
              RRO();
            break;

            //Preemp Priority
            case "D":
            case "d":
              PreemptivePriorityScheduling();
            break;

            //MLFQ
            case "E":
            case "e":
              MQS();
            break;
            
            //Exit
            case "F":
            case "f":
            System.out.println("Input Again? [ Y / N ]");
            choice = sc.nextLine();
          }
    }
    while(!choice.equals("N") & !choice.equals("n"));
    System.out.println("Thank you for using our program! Have a good day!");
  } 


	// Preemptive Priority Scheduling
	static void PreemptivePriorityScheduling() {
		Scanner sc = new Scanner(System.in);
		int totalBurstTime = 0;
		ArrayList<Process> processes = new ArrayList<>();

		System.out.println("Enter the number of processes: ");
		int processCount = sc.nextInt();

		for (int i = 1; i <= processCount; i++) {
			System.out.println("Process Number " + i);
			System.out.println("Enter Arrival Time: ");
			int arrivalTime = sc.nextInt();
			System.out.println("Enter Burst Time: ");
			int burstTime = sc.nextInt();
			System.out.println("Enter Priority: ");
			int priority = sc.nextInt();

			totalBurstTime += burstTime;
			processes.add(new Process(arrivalTime, burstTime, priority));
		}

		System.out.println("Gantt Chart");
		GanttChart ganttChart = new GanttChart();
		int currentTime = 0;
		int completedProcesses = 0;

		while (completedProcesses < processCount) {
			int highestPriority = Integer.MAX_VALUE;
			int highestPriorityProcess = -1;

			for (int i = 0; i < processCount; i++) {
				Process process = processes.get(i);

				if (process.getBurstTime() > 0 && process.getArrivalTime() <= currentTime && process.getPriority() < highestPriority) {
					highestPriority = process.getPriority();
					highestPriorityProcess = i;
				}
			}

			if (highestPriorityProcess == -1) {
				currentTime++;
				continue;
			}

			Process executingProcess = processes.get(highestPriorityProcess);
			executingProcess.setBurstTime(executingProcess.getBurstTime() - 1);
			currentTime++;

			if (executingProcess.getBurstTime() == 0) {
				executingProcess.setTurnAroundTime(currentTime - executingProcess.getArrivalTime());
				executingProcess.setWaitingTime(executingProcess.getTurnAroundTime() - executingProcess.getMaxBurstTime());
				completedProcesses++;
			}

			ganttChart.Add(currentTime, highestPriorityProcess + 1);
		}

		System.out.println(ganttChart.GanttChartCreate());
		System.out.println();

		float averageTurnaroundTime = 0;
		float averageWaitingTime = 0;

		for (Process process : processes) {
			averageTurnaroundTime += process.getTurnAroundTime();
			averageWaitingTime += process.getWaitingTime();
		}

		averageTurnaroundTime /= processCount;
		averageWaitingTime /= processCount;

		System.out.println("Process\tArrival Time\tBurst Time\tPriority");
		for (Process process : processes) {
			System.out.println("Process " + (processes.indexOf(process) + 1) + "\t" +
							process.getArrivalTime() + "\t\t" +
							process.getMaxBurstTime() + "\t\t" +
							process.getPriority());
		}

		System.out.println("Average Turnaround Time: " + averageTurnaroundTime);
		System.out.println("Average Waiting Time: " + averageWaitingTime);
	}


  //Shortest Remaining Time First
  public static void SRTF()throws IOException{
    try{
      BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
      int n;
      System.out.println("Enter the number of Processes: ");
       n = Integer.parseInt(br.readLine());
       int proc[][] = new int[n + 1][4];
       for(int i = 1; i <= n; i++)
       {
      System.out.println("Enter Arrival Time for Process " + i + ": ");
      proc[i][0] = Integer.parseInt(br.readLine());
      System.out.println("Enter Burst Time for Process " + i + ": ");
      proc[i][1] = Integer.parseInt(br.readLine());
     }
       System.out.println();
     
     int total_time = 0;
     for(int i = 1; i <= n; i++)
     {
      total_time += proc[i][1];
     }
     int time_chart[] = new int[total_time];
     
     for(int i = 0; i < total_time; i++)
     {
      int sel_proc = 0;
      int min = 99999;
      for(int j = 1; j <= n; j++)
      {
       if(proc[j][0] <= i)
       {
        if(proc[j][1] < min && proc[j][1] != 0)
        {
         min = proc[j][1];
         sel_proc = j;
        }
       }
      }
      
      time_chart[i] = sel_proc;
      
      proc[sel_proc][1]--;
      
      for(int j = 1; j <= n; j++)
      {
       if(proc[j][0] <= i)
       {
        if(proc[j][1] != 0)
        {
         proc[j][3]++;
            if(j != sel_proc)
             proc[j][2]++;
        }
        else if(j == sel_proc)
         proc[j][3]++;
       }
      }
      
      if(i != 0)
      {
       if(sel_proc != time_chart[i - 1])
       {
        System.out.print("--" + i + "--P" + sel_proc);
       }
      }
      else
       System.out.print(i + "--P" + sel_proc);
      if(i == total_time - 1)
       System.out.print("--" + (i + 1));
      
     }
     System.out.println();
     System.out.println();
     
     System.out.println("P\t WT \t TT ");
     for(int i = 1; i <= n; i++)
     {
      System.out.printf("%d\t%2dms\t%2dms",i,proc[i][2],proc[i][3]);
      System.out.println();
     }
     
     System.out.println();
     
     float WT = 0,TT = 0;
     for(int i = 1; i <= n; i++)
     {
      WT += proc[i][2];
      TT += proc[i][3];
     }
     WT /= n;
     TT /= n;
     System.out.println("Average Waiting Time: " + WT + "ms");
     System.out.println("Average Turnaround Time: " + TT + "ms");
  }
  catch(IOException e){
    e.printStackTrace();
  }
  }


  //Round Robin
  public static void RR()throws IOException{
    try{
    DataInputStream sc = new DataInputStream(System.in);
		int i,j,k,q,sum=0;
		System.out.println("Enter number of processes:");
		int n=Integer.parseInt(sc.readLine());
		int bt[]=new int[n];
		int wt[]=new int[n];
		int tat[]=new int[n];
		int a[]=new int[n];

		for(i=0;i<n;i++){
			System.out.println("Enter burst time " + (i+1) + ": ");
			bt[i]=Integer.parseInt(sc.readLine());
		}
		System.out.println("Enter quantum time: ");
		q=Integer.parseInt(sc.readLine());

		for(i=0;i<n;i++)
			a[i]=bt[i];
		for(i=0;i<n;i++)
			wt[i]=0;
		do{
		for(i=0;i<n;i++){
			if(bt[i]>q){
				bt[i]-=q;
				for(j=0;j<n;j++){
					if((j!=i)&&(bt[j]!=0))
					wt[j]+=q;
				}
			}
			else{
				for(j=0;j<n;j++){
					if((j!=i)&&(bt[j]!=0))
					wt[j]+=bt[i];
				}
				bt[i]=0;
			}
		}
		sum=0;
		for(k=0;k<n;k++)
			sum=sum+bt[k];
		}
		while(sum!=0);
		for(i=0;i<n;i++)
			tat[i]=wt[i]+a[i];
		System.out.println("process\t\tBT\tWT\tTAT");
		for(i=0;i<n;i++){
			System.out.println("process"+(i+1)+"\t"+a[i]+"\t"+wt[i]+"\t"+tat[i]);
		}
		float avg_wt=0;
		float avg_tat=0;
		for(j=0;j<n;j++){
			avg_wt+=wt[j];
		}
		for(j=0;j<n;j++){
			avg_tat+=tat[j];
		}
		System.out.println("Average Waiting Time: " + (avg_wt/n) + 
    "ms\n Average turn around time: " + (avg_tat/n) + "ms");
	}
  catch(IOException e){
    e.printStackTrace();
  }
  }


  //MQS
  public static void MQS()
	{
    Scanner sc = new Scanner(System.in);
		int NumberOfProcess;
		int process[];
		int tq[];
		int qn[];
		int at[];
		int bt[];
		
		int temp;
		int temp2;
		
		System.out.print("\nEnter number of processes: ");
		NumberOfProcess = sc.nextInt();
		
		process = new int[NumberOfProcess];
		tq = new int[NumberOfProcess];
		qn = new int[NumberOfProcess];
		at = new int[NumberOfProcess];
		bt = new int[NumberOfProcess];
		
		for(int i = 0; i < NumberOfProcess; i++)
		{
			System.out.println("\nProcess #" + i + " Queue Number: ");
			qn[i] = sc.nextInt();
			System.out.println("Process #" + i + " Arrival Time: ");
			at[i] = sc.nextInt();
			System.out.println("Process #" + i + " Burst Time: ");
			bt[i] = sc.nextInt();
		}
		
		for (int i = 0; i < NumberOfProcess; i++){
            for (int j = 0; j < NumberOfProcess - i - 1; j++){
                if (at[j] > at[j + 1]){
                    temp = at[j];
                    at[j] = at[j + 1];
                    at[j + 1] = temp;

                    temp = bt[j];
                    bt[j] = bt[j + 1];
                    bt[j + 1] = temp;

                    temp = qn[j];
                    qn[j] = qn[j + 1];
                    qn[j + 1] = temp;

                    temp2 = process[j];
                    process[j] = process[j + 1];
                    process[j + 1] = temp2;
                }
                if (at[j] == at[j + 1]) {
                    if (qn[j] > qn[j + 1]) {
                        temp = at[j];
                        at[j] = at[j + 1];
                        at[j + 1] = temp;

                        temp = bt[j];
                        bt[j] = bt[j + 1];
                        bt[j + 1] = temp;

                        temp = qn[j];
                        qn[j] = qn[j + 1];
                        qn[j + 1] = temp;

                        temp2 = process[j];
                        process[j] = process[j + 1];
                        process[j + 1] = temp2;
		              			  
                }
            }
         }
	}
		
		if(qn[1] == qn[1]) 
	   	{
	   		int CompleteTime[] = new int[NumberOfProcess];
	   		int WaitingTime[] = new int[NumberOfProcess];
	   		int TurnaroundTime[] = new int[NumberOfProcess];
	   		sortbyArrivalTime(at, bt, process, NumberOfProcess);
	   		CompleteTime[0] = at[0] + bt[0];
	   		TurnaroundTime[0] = CompleteTime[0] - at[0];
	   		WaitingTime[0] = TurnaroundTime[0] - bt[0];
	   		for (int i = 1; i < NumberOfProcess; i++)
	   		{
	   			CompleteTime[i] = bt[i] + CompleteTime[i - 1];
	   			TurnaroundTime[i] = CompleteTime[i] - at[i];
	   			WaitingTime[i] = TurnaroundTime[i] - bt[i];
	   		}
	   		float sum = 0;
	   		for (int n : WaitingTime)
	   		{
	   			sum += n;
	   		}
	   		float averageWT = sum / NumberOfProcess;

	   		sum = 0;
	   		for (int n : TurnaroundTime)
	   		{
	   			sum += n;
	   		}
	   		float averageTAT = sum / NumberOfProcess;
	   		for (int i = 0; i < NumberOfProcess; i++)
	   		{
	   			System.out.print("\nPROCESS #: " + i);
	   			System.out.print("\nQueue Number: " + qn[i]);
	   			System.out.print("\nArrival Time: " + at[i]);
	   			System.out.print("\nBurst Time: " + bt[i]);
	   			System.out.print("\nCompletion Time: " + CompleteTime[i]);
	   			System.out.print("\nTurnaround Time: " + TurnaroundTime[i]);
	   			System.out.print("\nWaiting Time: " + WaitingTime[i]);
	   			System.out.println();
	   		}
	   		System.out.print("\n\nAverage Turnaround Time: " + averageTAT);
	   		System.out.print("\nAverage Waiting Time: " + averageWT);
	   	}
		else if(qn[2] == qn[2]) {
			int CompleteTime[] = new int[NumberOfProcess];
	   		int WaitingTime[] = new int[NumberOfProcess];
	   		int TurnaroundTime[] = new int[NumberOfProcess];
	   		sortbyBurstTime(at, bt, process, NumberOfProcess);
	   		sortbyArrivalTime(at, bt, process, NumberOfProcess);
     	        
     	    int value = 0;
     	     
     	    CompleteTime[0] = at[0] + bt[0];
	   		TurnaroundTime[0] = CompleteTime[0] - at[0];
	   		WaitingTime[0] = TurnaroundTime[0] - bt[0];
     	     

     	    for (int i = 1; i < NumberOfProcess; i++){
     	    	 temp = CompleteTime[i-1];
     	    	 int low = bt[i];
     	    	 for (int j = i; j < NumberOfProcess; j++){
     	    		 if (temp >= at[j] && low >= bt[j]) {
     	    			 low = bt[j];
     	    			 value = j;
     	    		 }
     	    	 }
     	    	CompleteTime[value] = temp + bt[value];
     	    	TurnaroundTime[value] = CompleteTime[value] - at[value];
     	    	WaitingTime[value] = TurnaroundTime[value] - bt[value];
     	     }
     	     
     	     
     	    	float sum = 0;
     	        for (int n : WaitingTime){
     	            sum += n;
     	        }
     	        float averageWT = sum / NumberOfProcess;

     	        sum = 0;
     	        for (int n : TurnaroundTime){
     	            sum += n;
     	        }
     	       float averageTAT = sum / NumberOfProcess;
  	   		for (int i = 0; i < NumberOfProcess; i++)
  	   		{
  	   			System.out.print("\nPROCESS #: " + i);
  	   			System.out.print("\nQueue Number: " + qn[i]);
  	   			System.out.print("\nArrival Time: " + at[i]);
  	   			System.out.print("\nBurst Time: " + bt[i]);
  	   			System.out.print("\nCompletion Time: " + CompleteTime[i]);
  	   			System.out.print("\nTurnaround Time: " + TurnaroundTime[i]);
  	   			System.out.print("\nWaiting Time: " + WaitingTime[i]);
  	   			System.out.println();
  	   		}
  	   		System.out.print("\n\nAverage Turnaround Time: " + averageTAT);
  	   		System.out.print("\nAverage Waiting Time: " + averageWT + "\n");
		}
	   		
	}
	
	public static void sortbyBurstTime(int[] at, int[] bt, int[] process, int NumberOfProcess)
	{
		int temp;
		int temp2;
		boolean swapped;
		 for (int i = 0; i < NumberOfProcess; i++){
			 swapped = false;
			 for (int j = 1; j < NumberOfProcess; j++){
				 if (at[i] == at[j]){
					 if (bt[i] > bt[j]){
						 temp = at[i];
						 at[i] = at[j];
						 at[j] = temp;
						 
						 temp = bt[i];
						 bt[i] = bt[j];
						 bt[j] = temp;
						 
						 temp2 = process[i];
						 process[i] = process[j];
						 process[j] = temp2;
						 
						 swapped = true;
						 
					 }
				 }
				 if (swapped = false) {
					 break;
			 }
		 }
	 }
	}
	public static void sortbyArrivalTime(int[] at, int[] bt, int[] process, int NumberOfProcess)
	{
		boolean swapped;
        int temp;
        int temp2;
        for (int i = 0; i < NumberOfProcess; i++){
            swapped = false;
            for (int j = 0; j < NumberOfProcess - i - 1; j++){
                if (at[j] > at[j + 1]){
                    temp = at[j];
                    at[j] = at[j + 1];
                    at[j + 1] = temp;

                    temp = bt[j];
                    bt[j] = bt[j + 1];
                    bt[j + 1] = temp;

                    temp2 = process[j];
                    process[j] = process[j + 1];
                    process[j + 1] = temp2;

                    swapped = true;
                }
            }
            if (swapped == false){
                break;
            }
        }
	}


  //RRO
  public static void RRO()
{
Scanner sc = new Scanner(System.in);
System.out.println("enter no of process: ");
int n = sc.nextInt();
int pid[] = new int[n];  
int ts[] = new int[n];     
int ot[] = new int[n];     
int ct[] = new int[n];     
int ta[] = new int[n];     
int wt[] = new int[n];     
int temp;
float avgwt=0,avgta=0;
 
for(int i = 0; i < n; i++)
{
  System.out.println("Enter process " + (i+1) + " time slice: ");
  ts[i] = sc.nextInt();
  System.out.println("Enter process " + (i+1) + " overhead time: ");
  ot[i] = sc.nextInt();
  pid[i] = i+1;
}
 
for(int i = 0 ; i <n; i++)
  {
    for(int  j=0;  j < n-(i+1) ; j++)
      {
        if( ts[j] > ts[j+1] )
          {
            temp = ts[j];
            ts[j] = ts[j+1];
            ts[j+1] = temp;
             temp = ot[j];
             ot[j] = ot[j+1];
             ot[j+1] = temp;
             temp = pid[j];
             pid[j] = pid[j+1];
             pid[j+1] = temp;
          }
      }
  }
for(int  i = 0 ; i < n; i++)
  {
    if( i == 0)
      {
        ct[i] = ts[i] + ot[i];
      }
    else
      {
        if( ts[i] > ct[i-1])
          {
            ct[i] = ts[i] + ot[i];
          }
        else
          ct[i] = ct[i-1] + ot[i];
      }
      ta[i] = ct[i] - ts[i] ;          
      wt[i] = ta[i] - ot[i] ;         
      avgwt += wt[i] ;               
      avgta += ta[i] ;               
  }

System.out.println("\npid  arrival  burst  complete turn waiting");
for(int  i = 0 ; i< n;  i++)
  {
    System.out.println(pid[i] + "  \t " + ts[i] + "\t" + ot[i] + "\t" + ct[i] + "\t" + ta[i] + "\t"  + wt[i] ) ;
  }
System.out.println("\naverage waiting time: "+ (avgwt/n));     
System.out.println("average turn around time:"+(avgta/n));    
  }
}