import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.stream.IntStream;

public class Main {

    private static final String IP_ADDRESSES_FILE = "ip.txt";

    public static void main(String[] args) throws IOException {
        Main.generateIpAddresses(4, 1);
        System.out.println(Main.calculateCountOfUniqueIpAddresses());
    }

    public static long calculateCountOfUniqueIpAddresses() throws IOException {
        long count = 0;
        byte[][][][] parts = new byte[256][][][];
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(IP_ADDRESSES_FILE)))) {
            String ipAddress;
            while ((ipAddress = reader.readLine()) != null) {
                String[] arr = ipAddress.split("\\.");
                int firstPart = Integer.parseInt(arr[0]);
                int secondPart = Integer.parseInt(arr[1]);
                int thirdPart = Integer.parseInt(arr[2]);
                int fourthPart = Integer.parseInt(arr[3]);
                if (parts[firstPart] == null) {
                    parts[firstPart] = new byte[256][][];
                }
                if (parts[firstPart][secondPart] == null) {
                    parts[firstPart][secondPart] = new byte[256][];
                }
                if (parts[firstPart][secondPart][thirdPart] == null) {
                    parts[firstPart][secondPart][thirdPart] = new byte[256];
                }
                if (parts[firstPart][secondPart][thirdPart][fourthPart] == 0) {
                    parts[firstPart][secondPart][thirdPart][fourthPart] = 1;
                }
            }

            for (byte[][][] firstParts : parts) {
                if (firstParts != null) {
                    for (byte[][] secondParts : firstParts) {
                        if (secondParts != null) {
                            for (byte[] thirdParts : secondParts) {
                                if (thirdParts != null) {
                                    for (byte fourthPartValue : thirdParts) {
                                        if (fourthPartValue == 1) {
                                            count++;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return count;
    }


    public static void generateIpAddresses(int range, int duplicationCount) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("ip.txt"))) {
            IntStream.range(0, duplicationCount)
                    .forEach(c -> IntStream.range(0, range)
                            .mapToObj(String::valueOf)
                            .forEach(i -> IntStream.range(0, range)
                                    .mapToObj(String::valueOf)
                                    .forEach(j -> IntStream.range(0, range)
                                            .mapToObj(String::valueOf)
                                            .forEach(k -> IntStream.range(0, range)
                                                    .mapToObj(String::valueOf)
                                                    .forEach(l -> {
                                                        try {
                                                            writer.write(String.join(".", new String[]{i, j, k, l}));
                                                            writer.newLine();
                                                        } catch (IOException e) {
                                                            e.printStackTrace();
                                                        }
                                                    })))));
        }
    }
}