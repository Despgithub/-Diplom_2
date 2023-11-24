package helpers;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class OrderHelper {

    public static List<String> getOrderList() {
        Random randomGenerator = new Random();
        List<String> bunList = Arrays.asList(
                "61c0c5a71d1f82001bdaaa6d",
                "61c0c5a71d1f82001bdaaa6c");
        int bun = randomGenerator.nextInt(bunList.size());

        List<String> souceList = Arrays.asList(
                "61c0c5a71d1f82001bdaaa72",
                "61c0c5a71d1f82001bdaaa73",
                "61c0c5a71d1f82001bdaaa74",
                "61c0c5a71d1f82001bdaaa75");
        int souce = randomGenerator.nextInt(souceList.size());

        List<String> fillingList = Arrays.asList(
                "61c0c5a71d1f82001bdaaa6f",
                "61c0c5a71d1f82001bdaaa70",
                "61c0c5a71d1f82001bdaaa71",
                "61c0c5a71d1f82001bdaaa6e",
                "61c0c5a71d1f82001bdaaa76",
                "61c0c5a71d1f82001bdaaa77",
                "61c0c5a71d1f82001bdaaa78",
                "61c0c5a71d1f82001bdaaa79",
                "61c0c5a71d1f82001bdaaa7a");
        int filling1 = randomGenerator.nextInt(fillingList.size());
        int filling2 = randomGenerator.nextInt(fillingList.size());

        return Arrays.asList(bunList.get(bun), souceList.get(souce), fillingList.get(filling1), fillingList.get(filling2));
    }

    public static List<String> getErrorOrderList() {
        return Arrays.asList(
                "61c0c5a71d1f73h43bdaaa6f",
                "87c3543a71d1f82001bdaaa7",
                "5435343451d1f82001bd87a7");
    }

}
