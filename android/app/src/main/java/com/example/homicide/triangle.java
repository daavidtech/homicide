package com.example.homicide;

public class triangle {
    static public boolean isTriangle(int n) {
        int sum = 0;
        for (int i = 1; i <= n; i++) {
            sum = sum + i;

            if (sum == n) {
                return true;
            }
        }

        return false;
    }
}
