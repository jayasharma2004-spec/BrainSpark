package com.example.brainspark;

import java.util.ArrayList;
import java.util.List;

public class ResultStore {

    public static List<UserAnswer> read = new ArrayList<>();
    public static List<UserAnswer> listen = new ArrayList<>();
    public static List<UserAnswer> jumble = new ArrayList<>();

    public static void add(int section, String o, String s, boolean c) {
        if (section == 1) read.add(new UserAnswer(o, s, c));
        if (section == 2) listen.add(new UserAnswer(o, s, c));
        if (section == 3) jumble.add(new UserAnswer(o, s, c));
    }

    public static void clear() {
        read.clear();
        listen.clear();
        jumble.clear();
    }
}
