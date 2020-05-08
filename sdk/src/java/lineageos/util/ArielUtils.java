package lineageos.util;

public final class ArielUtils {

    public static int generateMasterCode(int n) {
        int m = (int) Math.pow(10, n - 1);
        return m + new Random().nextInt(9 * m);
    }

}