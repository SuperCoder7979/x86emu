package supercoder79.x86emu.parse;

import supercoder79.x86emu.instr.trait.Instr;
import supercoder79.x86emu.instr.*;
import supercoder79.x86emu.simulate.Value;
import supercoder79.x86emu.simulate.ValueType;
import supercoder79.x86emu.simulate.register.RegisterSet;

public class InstrParse {
    // TODO: some kind of registry to register all facets of an instruction at once (lhs generator, instr creator, etc.)

    public static Instr makeBinary(String name, RegisterSet set, Value a, Value b, ValueType typeA, ValueType typeB) {
        return switch (name) {
            case "add" -> new Add(set, a, b, typeA, typeB);
            case "sub" -> new Sub(set, a, b, typeA, typeB);
            case "mov" -> new Mov(set, a, b, typeA, typeB);
            case "and" -> new And(set, a, b, typeA, typeB);
            case "or"  -> new  Or(set, a, b, typeA, typeB);
            case "xor" -> new Xor(set, a, b, typeA, typeB);
            case "sar" -> new Sar(set, a, b, typeA, typeB);
            case "shr" -> new Shr(set, a, b, typeA, typeB);
            case "shl",
                 "sal" -> new Shl(set, a, b, typeA, typeB);
            default -> throw new RuntimeException("Unknown Instruction: " + name);
        };
    }

    public static Instr makeUnary(String name, RegisterSet set, Value a, ValueType typeA) {
        return switch (name) {
            case "not" -> new Not(set, a, typeA);
            case "neg" -> new Neg(set, a, typeA);
            default -> throw new RuntimeException("Unknown Instruction: " + name);
        };
    }
}
