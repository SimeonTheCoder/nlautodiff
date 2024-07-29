package build;

import data.Array;
import data.ObjType;
import data.ReadableFile;
import data.WritableFile;
import operations.Operation;
import parser.Interpreter;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

public enum CustomOperation implements Operation {
    PING {
        @Override
        public ObjType[] getArguments() {
            return new ObjType[]{};
        }

        @Override
        public void execute(Object[] instruction, float[] memory, HashMap<String, WritableFile> writableFiles, HashMap<String, ReadableFile> readableFiles, HashMap<String, Array> arrays, String[] stringTable) throws IOException {
            System.out.println("Pong!");
        }

        @Override
        public String help() {
            return "";
        }
    }, DPR {
        @Override
        public ObjType[] getArguments() {
            return new ObjType[] {ObjType.NUMBER, ObjType.NUMBER};
        }

        @Override
        public void execute(Object[] instruction, float[] memory, HashMap<String, WritableFile> writableFiles, HashMap<String, ReadableFile> readableFiles, HashMap<String, Array> arrays, String[] stringTable) throws IOException, InvocationTargetException, NoSuchMethodException, IllegalAccessException, InstantiationException {
            float val = Interpreter.getValue(instruction[1], memory);
            float derivative = Interpreter.getValue(instruction[2], memory);

            memory[(Integer) instruction[8]] = val;
            memory[(Integer) instruction[8] + 1] = derivative;
        }

        @Override
        public String help() {
            return "Defines a pair value -> derivative";
        }
    }, DAD {
        @Override
        public ObjType[] getArguments() {
            return new ObjType[]{ObjType.NUMBER, ObjType.NUMBER};
        }

        @Override
        public void execute(Object[] instruction, float[] memory, HashMap<String, WritableFile> writableFiles, HashMap<String, ReadableFile> readableFiles, HashMap<String, Array> arrays, String[] stringTable) throws IOException, InvocationTargetException, NoSuchMethodException, IllegalAccessException, InstantiationException {
            float valA = Interpreter.getValue(instruction[1], memory);
            float derivativeA = memory[(Integer) instruction[1] + 1];

            float valB = Interpreter.getValue(instruction[2], memory);
            float derivativeB = memory[(Integer) instruction[2] + 1];

            memory[(Integer) instruction[8]] = valA + valB;
            memory[(Integer) instruction[8] + 1] = derivativeA + derivativeB;
        }

        @Override
        public String help() {
            return "Adds two pairs (value, derivative)";
        }
    }, DSB {
        @Override
        public ObjType[] getArguments() {
            return new ObjType[]{ObjType.NUMBER, ObjType.NUMBER};
        }

        @Override
        public void execute(Object[] instruction, float[] memory, HashMap<String, WritableFile> writableFiles, HashMap<String, ReadableFile> readableFiles, HashMap<String, Array> arrays, String[] stringTable) throws IOException, InvocationTargetException, NoSuchMethodException, IllegalAccessException, InstantiationException {
            float valA = Interpreter.getValue(instruction[1], memory);
            float derivativeA = memory[(Integer) instruction[1] + 1];

            float valB = Interpreter.getValue(instruction[2], memory);
            float derivativeB = memory[(Integer) instruction[2] + 1];

            memory[(Integer) instruction[8]] = valA - valB;
            memory[(Integer) instruction[8] + 1] = derivativeA - derivativeB;
        }

        @Override
        public String help() {
            return "Subtracts two pairs (value, derivative)";
        }
    }, DML {
        @Override
        public ObjType[] getArguments() {
            return new ObjType[]{ObjType.NUMBER, ObjType.NUMBER};
        }

        @Override
        public void execute(Object[] instruction, float[] memory, HashMap<String, WritableFile> writableFiles, HashMap<String, ReadableFile> readableFiles, HashMap<String, Array> arrays, String[] stringTable) throws IOException, InvocationTargetException, NoSuchMethodException, IllegalAccessException, InstantiationException {
            float valA = Interpreter.getValue(instruction[1], memory);
            float derivativeA = memory[(Integer) instruction[1] + 1];

            float valB = Interpreter.getValue(instruction[2], memory);
            float derivativeB = memory[(Integer) instruction[2] + 1];

            memory[(Integer) instruction[8]] = valA * valB;
            memory[(Integer) instruction[8] + 1] = derivativeA * valB + valA * derivativeB;
        }

        @Override
        public String help() {
            return "Multiplies two pairs (value, derivative)";
        }
    }, DDV {
        @Override
        public ObjType[] getArguments() {
            return new ObjType[]{ObjType.NUMBER, ObjType.NUMBER};
        }

        @Override
        public void execute(Object[] instruction, float[] memory, HashMap<String, WritableFile> writableFiles, HashMap<String, ReadableFile> readableFiles, HashMap<String, Array> arrays, String[] stringTable) throws IOException, InvocationTargetException, NoSuchMethodException, IllegalAccessException, InstantiationException {
            float valA = Interpreter.getValue(instruction[1], memory);
            float derivativeA = memory[(Integer) instruction[1] + 1];

            float valB = Interpreter.getValue(instruction[2], memory);
            float derivativeB = memory[(Integer) instruction[2] + 1];

            memory[(Integer) instruction[8]] = valA * valB;
            memory[(Integer) instruction[8] + 1] = (derivativeA * valB - valA * derivativeB) / (valB * valB);
        }

        @Override
        public String help() {
            return "Divides two pairs (value, derivative)";
        }
    };

    public CustomOperation value(String str) {
        return switch (str) {
            case "PING" -> PING;
            case "DPR" -> DPR;
            case "DAD" -> DAD;
            case "DSB" -> DSB;
            case "DML" -> DML;
            case "DDV" -> DDV;
            default -> null;
        };
    }

    CustomOperation() {
    }
}