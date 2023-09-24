package com.minesweeper;

import java.util.Random;
import java.util.Scanner;

public class Minesweeper {
    public static void main(String[] args) {
        int width = 9;
        int height = 9;
        int mineCount = 10;

        int[][] mineMap = new int[height + 2][width + 2];
        Random randNum = new Random(1);
        int[] nonOverlappingNum = new int[mineCount];
        for (int i = 0; i < mineCount; i++) {
            nonOverlappingNum[i] = randNum.nextInt(width * height);
            for (int j = 0; j < i; j++) {
                if (nonOverlappingNum[i] == nonOverlappingNum[j]) {
                    i--;
                    break;
                }
            }
        }

        for (int i = 0; i < mineCount; i++) {
            int x = nonOverlappingNum[i] / width + 1;
            int y = nonOverlappingNum[i] % width + 1;
            for (int tmpX = x - 1; tmpX <= x + 1; tmpX++) {
                for (int tmpY = y - 1; tmpY <= y + 1; tmpY++) {
                    if (mineMap[tmpX][tmpY] == -1)
                        continue;
                    mineMap[tmpX][tmpY]++;
                }
            }
            mineMap[x][y] = -1;
        }

        char[][] screen = new char[height + 2][width + 2];
        for (int i = 0; i < height + 2; i++) {
            for (int j = 0; j < width + 2; j++) {
                if (i == 0 || j == 0 || i == height + 1 || j == width + 1) {
                    screen[i][j] = '*';
                } else {
                    screen[i][j] = '■';
                }
            }
        }
        printMineMap(height, width, screen);

        boolean[] isFail = {false};
        int[] mineSum = {width * height};
        Scanner in = new Scanner(System.in);

        boolean[][] check = new boolean[height + 2][width + 2];
        while (true) {
            int x = in.nextInt();
            int y = in.nextInt();

            findMine(x, y, width, height, screen, mineMap, isFail, check, mineSum);
            if (isFail[0]) {
                System.out.println("실패!");
                break;
            }
            printMineMap(height, width, screen);
            if (mineSum[0] == mineCount) {
                System.out.println("우승!");
                break;
            }
        }
    }

    public static void printMineMap(int x, int y, char[][] arr) {
        for (int i = 0; i < x + 2; i++) {
            for (int j = 0; j < y + 2; j++) {
                System.out.print(arr[i][j]);
            }
            System.out.println();
        }
    }

    public static void findMine(int x, int y, int height, int width, char[][] arr, int[][] mine, boolean[] isFail, boolean[][] check, int[] mineSum) {
        if (mine[x][y] == -1) {
            isFail[0] = true;
            return;
        }
        if (isFail[0]) {
            for (int i = 1; i <= height; i++) {
                for (int j = 1; j <= width; j++) {
                    arr[i][j] = 'F';
                }
            }
            return;
        }
        if (check[x][y]) {
            return;
        }
        check[x][y] = true;
        arr[x][y] = (char) (mine[x][y] + '0');
        mineSum[0]--;
        for (int tmpX = x - 1; tmpX <= x + 1; tmpX++) {
            for (int tmpY = y - 1; tmpY <= y + 1; tmpY++) {
                if (mine[tmpX][tmpY] == -1)
                    return;
            }
        }
        for (int tmpX = x - 1; tmpX <= x + 1; tmpX++) {
            for (int tmpY = y - 1; tmpY <= y + 1; tmpY++) {
                if ((tmpX == x && tmpY == y) || (arr[tmpX][tmpY] == '*') || (check[tmpX][tmpY]))
                    continue;
                findMine(tmpX, tmpY, width, height, arr, mine, isFail, check, mineSum);
            }
        }
    }
}