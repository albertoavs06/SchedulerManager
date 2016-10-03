package schedulermanager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import sun.util.calendar.CalendarUtils;

/**
 *
 * @author Nycholas de Sousa
 * @matricula 11228201
 *
 */
public class SJF extends Scheduler {

    private static List<Process> listReady = new ArrayList<Process>();

    private void prepareList(List<Process> process) {
        List<Process> p = new ArrayList<Process>(process);
        int returnSum = 0;
        int min = 0;
        int pivo = 0;

        Collections.sort(p);
        listReady.add(p.remove(0));
        returnSum = listReady.get(0).getDuration();

        while (p.size() > 1) {
            pivo = 0;
            for (int i = 1; i < p.size(); i++) {
                if (p.get(pivo).getDuration() <= p.get(i).getDuration()
                        && p.get(pivo).getArrivalTime() < returnSum) {
                    min = pivo;
                } else if (p.get(i).getArrivalTime() < returnSum) {
                    pivo = i;
                    min = i;
                }
            }

            returnSum += p.get(min).getDuration();
            listReady.add(p.remove(min));
        }

        if (p.size() == 1) {
            listReady.add(p.remove(0));
        }
    }

    public SJF(List<Process> process) {
        int returnTime = 0;
        int responseTime = 0;
        int waitTime = 0;
        int totalProcess = super.getAmountOfProcess(process);
        int returnAux = arrivalMin(process);
        prepareList(process);

        while (!listReady.isEmpty()) {
            Process p = listReady.remove(0);
            returnAux += p.getDuration();
            returnTime += (returnAux - p.getArrivalTime());
            waitTime += (returnAux - p.getArrivalTime() - p.getDuration());
        }

        responseTime = waitTime;

        super.setAvgReturn((double) returnTime / totalProcess);
        super.setAvgResponse((double) responseTime / totalProcess);
        super.setAvgWait((double) waitTime / totalProcess);

    }

    public void print() {
        super.print("SJF");
    }

}
