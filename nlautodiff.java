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

public enum nlautodiff implements Operation {
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
            return new ObjType[] {ObjType.NUMBER, ObjType.NUMBER, ObjType.NUMBER};
        }

        @Override
        public void execute(Object[] instruction, float[] memory, HashMap<String, WritableFile> writableFiles, HashMap<String, ReadableFile> readableFiles, HashMap<String, Array> arrays, String[] stringTable) throws IOException, InvocationTargetException, NoSuchMethodException, IllegalAccessException, InstantiationException {
            float val = Interpreter.getValue(instruction[1], memory);
            float deriv1X = Interpreter.getValue(instruction[2], memory);
            float deriv1Y = instruction[3] == null ? 0 : Interpreter.getValue(instruction[3], memory);
            float deriv2XX = instruction[4] == null ? 0 : Interpreter.getValue(instruction[4], memory);
            float deriv2XY = instruction[5] == null ? 0 : Interpreter.getValue(instruction[5], memory);
            float deriv2YY = instruction[6] == null ? 0 : Interpreter.getValue(instruction[6], memory);

            memory[(Integer) instruction[8]] = val;
            memory[(Integer) instruction[8] + 1] = deriv1X;
            memory[(Integer) instruction[8] + 2] = deriv1Y;
            memory[(Integer) instruction[8] + 3] = deriv2XX;
            memory[(Integer) instruction[8] + 4] = deriv2XY;
            memory[(Integer) instruction[8] + 5] = deriv2YY;
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
            float derivA1X = memory[(Integer) instruction[1] + 1];
            float derivA1Y = memory[(Integer) instruction[1] + 2];
            float derivA2XX = memory[(Integer) instruction[1] + 3];
            float derivA2XY = memory[(Integer) instruction[1] + 4];
            float derivA2YY = memory[(Integer) instruction[1] + 5];

            float valB = Interpreter.getValue(instruction[2], memory);
            float derivB1X = memory[(Integer) instruction[2] + 1];
            float derivB1Y = memory[(Integer) instruction[2] + 2];
            float derivB2XX = memory[(Integer) instruction[2] + 3];
            float derivB2XY = memory[(Integer) instruction[2] + 4];
            float derivB2YY = memory[(Integer) instruction[2] + 5];

            memory[(Integer) instruction[8]] = valA + valB;
            memory[(Integer) instruction[8] + 1] = derivA1X + derivB1X;
            memory[(Integer) instruction[8] + 2] = derivA1Y + derivB1Y;
            memory[(Integer) instruction[8] + 3] = derivA2XX + derivB2XX;
            memory[(Integer) instruction[8] + 4] = derivA2XY + derivB2XY;
            memory[(Integer) instruction[8] + 5] = derivA2YY + derivB2YY;
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
            float derivA1X = memory[(Integer) instruction[1] + 1];
            float derivA1Y = memory[(Integer) instruction[1] + 2];
            float derivA2XX = memory[(Integer) instruction[1] + 3];
            float derivA2XY = memory[(Integer) instruction[1] + 4];
            float derivA2YY = memory[(Integer) instruction[1] + 5];

            float valB = Interpreter.getValue(instruction[2], memory);
            float derivB1X = memory[(Integer) instruction[2] + 1];
            float derivB1Y = memory[(Integer) instruction[2] + 2];
            float derivB2XX = memory[(Integer) instruction[2] + 3];
            float derivB2XY = memory[(Integer) instruction[2] + 4];
            float derivB2YY = memory[(Integer) instruction[2] + 5];

            memory[(Integer) instruction[8]] = valA - valB;
            memory[(Integer) instruction[8] + 1] = derivA1X - derivB1X;
            memory[(Integer) instruction[8] + 2] = derivA1Y - derivB1Y;
            memory[(Integer) instruction[8] + 3] = derivA2XX - derivB2XX;
            memory[(Integer) instruction[8] + 4] = derivA2XY - derivB2XY;
            memory[(Integer) instruction[8] + 5] = derivA2YY - derivB2YY;
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
            float derivA1X = memory[(Integer) instruction[1] + 1];
            float derivA1Y = memory[(Integer) instruction[1] + 2];
            float derivA2XX = memory[(Integer) instruction[1] + 3];
            float derivA2XY = memory[(Integer) instruction[1] + 4];
            float derivA2YY = memory[(Integer) instruction[1] + 5];

            float valB = Interpreter.getValue(instruction[2], memory);
            float derivB1X = memory[(Integer) instruction[2] + 1];
            float derivB1Y = memory[(Integer) instruction[2] + 2];
            float derivB2XX = memory[(Integer) instruction[2] + 3];
            float derivB2XY = memory[(Integer) instruction[2] + 4];
            float derivB2YY = memory[(Integer) instruction[2] + 5];

            memory[(Integer) instruction[8]] = valA * valB;
            memory[(Integer) instruction[8] + 1] = derivA1X * valB + valA * derivB1X;
            memory[(Integer) instruction[8] + 2] = derivA1Y * valB + valA * derivB1Y;
            memory[(Integer) instruction[8] + 3] = derivA2XX * valB + 2 * derivA1X * derivB1X + valA * derivB2XX;
            memory[(Integer) instruction[8] + 4] = derivA2XY * valB + derivA1X * derivB1Y + derivA1Y * derivB1X + valA * derivB2XY;
            memory[(Integer) instruction[8] + 5] = derivA2YY * valB + 2 * derivA1Y * derivB1Y + valA * derivB2YY;
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
            float derivA1X = memory[(Integer) instruction[1] + 1];
            float derivA1Y = memory[(Integer) instruction[1] + 2];
            float derivA2XX = memory[(Integer) instruction[1] + 3];
            float derivA2XY = memory[(Integer) instruction[1] + 4];
            float derivA2YY = memory[(Integer) instruction[1] + 5];

            float valB = Interpreter.getValue(instruction[2], memory);
            float derivB1X = memory[(Integer) instruction[2] + 1];
            float derivB1Y = memory[(Integer) instruction[2] + 2];
            float derivB2XX = memory[(Integer) instruction[2] + 3];
            float derivB2XY = memory[(Integer) instruction[2] + 4];
            float derivB2YY = memory[(Integer) instruction[2] + 5];

            memory[(Integer) instruction[8]] = valA / valB;
            memory[(Integer) instruction[8] + 1] = (derivA1X * valB - valA * derivB1X) / (valB * valB);
            memory[(Integer) instruction[8] + 2] = (derivA1Y * valB - valA * derivB1Y) / (valB * valB);
            memory[(Integer) instruction[8] + 3] = (derivA2XX * valB - 2 * derivA1X * derivB1X + 2 * valA * derivB2XX) / (valB * valB);
            memory[(Integer) instruction[8] + 4] = (derivA2XY * valB - derivA1X * derivB1Y - derivA1Y * derivB1X + valA * derivB2XY) / (valB * valB);
            memory[(Integer) instruction[8] + 5] = (derivA2YY * valB - 2 * derivA1Y * derivB1Y + 2 * valA * derivB2YY) / (valB * valB);
        }

        @Override
        public String help() {
            return "Divides two pairs (value, derivative)";
        }
    };

    public nlautodiff value(String str) {
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

    nlautodiff() {
    }
}