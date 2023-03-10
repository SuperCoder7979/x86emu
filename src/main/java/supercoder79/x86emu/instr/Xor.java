package supercoder79.x86emu.instr;

import supercoder79.x86emu.instr.trait.BinaryInstrBase;
import supercoder79.x86emu.instr.trait.Instr;
import supercoder79.x86emu.simulate.Immediate;
import supercoder79.x86emu.simulate.Value;
import supercoder79.x86emu.simulate.ValueType;
import supercoder79.x86emu.simulate.register.RegisterSet;
import supercoder79.x86emu.superopt.ImmCommon;
import supercoder79.x86emu.util.Bits;

import java.util.Random;

import static supercoder79.x86emu.simulate.ValueType.*;

public class Xor extends BinaryInstrBase {
    public Xor(RegisterSet set, Value a, Value b, ValueType typeA, ValueType typeB) {
        super(set, a, b, typeA, typeB);
    }

    @Override
    public void execute() {
        if (typeB == r64) {
            // Sign extension is ok here
            long vb = b.v64();
            long va = switch (typeA) {
                case r64 -> a.v64();
                case r32 -> a.v32();
                case r16 -> a.v16();
                case r8 -> a.v8();
            };

            long result = va ^ vb;

            b.set(result);
            return;
        } else if (typeB == r32) {
            // No sign extension
            int vb = b.v32();
            int va = switch (typeA) {
                case r64 -> (int) a.v64();
                case r32 -> a.v32();
                case r16 -> Bits.u16i(a.v16());
                case r8 -> Bits.u8i(a.v8());
            };

            int result = va ^ vb;

            b.set(result);
            return;
        } else if (typeB == r16) {
            // No sign extension
            short vb = b.v16();
            short va = switch (typeA) {
                case r16 -> a.v16();
                case r8 -> Bits.u8s(a.v8());
                default -> throw new IllegalStateException("Unexpected value: " + typeA);
            };

            short result = (short) (va ^ vb);

            b.set(result);
            return;
        } else if (typeB == r8) {
            // No sign extension
            byte vb = b.v8();
            byte va = switch (typeA) {
                case r8 -> a.v8();
                default -> throw new IllegalStateException("Unexpected value: " + typeA);
            };

            byte result = (byte) (va ^ vb);

            b.set(result);
            return;
        }
    }

    @Override
    public String mnemonic() {
        return "xor";
    }

    public static Instr create(RegisterSet set, Random random) {
        Immediate imm32 = ImmCommon.flagBearingImm(random);
        Value b = set.random(random);
        ValueType typeB = ValueType.randomHigh(random);

        return new Xor(set, imm32, b, r32, typeB);
    }
}
