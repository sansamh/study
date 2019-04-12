package any;

import java.io.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.LongAdder;

public class TransferRegion {

    static AtomicInteger count = new AtomicInteger(-1);

    public static void main(String[] args) throws Exception {
        File inFile = new File("C:\\Users\\LORDSTAR\\Desktop\\code.txt");
        BufferedReader reader = new BufferedReader(new FileReader(inFile));
        String line = null;
        String[] split;
        while ((line = reader.readLine()) != null) {
            line = reader.readLine();
            split = line.split(",");
            dealLine(split);
        }

        System.out.println(count.intValue());
    }

    static String outFilePath = "C:\\Users\\LORDSTAR\\Desktop\\result.txt";
    private static void dealLine(String[] split) throws Exception {
        StringBuilder result = new StringBuilder();
        StringBuilder builder = new StringBuilder();
        if (split.length > 0) {
            for (int i = 0; i < split.length; i++) {
                if (i != 5 && i !=split.length -1) {
                    result.append(split[i] + ",");
                }
                if (i == split.length -1) {
                    result.append(split[i]);
                    String[] split1 = result.toString().split("\\(");
                    split1[2] = count.incrementAndGet() + "," + split1[2];
                    for (int i1 = 0; i1 < split1.length; i1++) {
                        if (i1 != split1.length-1 ) {
                            builder.append(split1[i1] + "(");
                        } else {
                            builder.append(split1[i1]);
                        }
                    }

                }
            }
        }
        BufferedWriter writer = new BufferedWriter(new FileWriter(new File(outFilePath), true));
        writer.append(builder.toString() + "\r\n");
        writer.flush();
        writer.close();
    }
}
