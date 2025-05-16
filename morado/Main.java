package com.mantenimiento.morado;

import com.mantenimiento.morado.code.counter.SourceFileAnalyzer;

public class Main {
    public static void main(String[] args) {
        if (args.length == 0 || args[0].isEmpty()) {
            System.out.println("Review the User Manual");
            return;
        }

        SourceFileAnalyzer analyzer = new SourceFileAnalyzer(args[0]);
        analyzer.analyzePath();
    }

}
