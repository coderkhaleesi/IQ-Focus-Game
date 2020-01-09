package comp1110.ass2;

import java.util.HashSet;
import java.util.Set;

public class TestUtility {

    static final String[] BAD_PIECES = {
            "a719",
            "z103",
            "a01a",
            "a911",
            "a660",
            "A700",
            "i3C1",
    };

    static final String[] OFF_BOARD_1 = {
            "c030",
            "a721",
            "b602",
            "d710",
            "e632",
    };

    static final String[] OFF_BOARD_2 = {
            "j600",
            "f642",
            "f643",
            "h020",
            "i031",
    };

    static final String[] OVERLAP = {
            "a023c103d003",
            "a403j502h320",
            "j501a403e423",
            "e523a403j501",
    };

    static final ViablePlacement[] VP_LAST = {
            new ViablePlacement("a000c113d302e323f400g420h522i613j701",
                    "RRRBWBBRB",
                    0,
                    1,
                    "b013"),
            new ViablePlacement("a000c113d302e323f400g420h522i613j701",
                    "RRRBWBBRB",
                    1,
                    3,
                    "b013"),
            new ViablePlacement("a000c113d302e323f400g420h522i613j701",
                    "RRRBWBBRB",
                    1,
                    4,
                    "b013"),
            new ViablePlacement("a000b013d302e323f400g420h522i613j701",
                    "RRRBWBBRB",
                    1,
                    2,
                    "c113"),
            new ViablePlacement("a000b013d302e323f400g420h522i613j701",
                    "RRRBWBBRB",
                    2,
                    2,
                    "c113"),
            new ViablePlacement("a100b130c711d011e212f001h601i523j332",
                    "RRBBBBGGB",
                    4,
                    0,
                    "g400g402"),
            new ViablePlacement("a100b130c711d011e212f001h601i523j332",
                    "RRBBBBGGB",
                    5,
                    1,
                    "g400g402"),
            new ViablePlacement("a100b130c711d011e212g402h601i523j332",
                    "RRBBBBGGB",
                    0,
                    0,
                    "f001f003"),
            new ViablePlacement("a100b130c711d011e212g402h601i523j332",
                    "RRBBBBGGB",
                    0,
                    2,
                    "f001f003"),
    };

    static final ViablePlacement[] VP_CENTRE = {
            new ViablePlacement("d302e132j020",
                    "RRRRRWRWW",
                    4,
                    2,
                    ""),
            new ViablePlacement("d302e132j020",
                    "RRRRRWRWW",
                    4,
                    3,
                    "i432f430f432"),
            new ViablePlacement("d302h322j020",
                    "RRRRRWRWW",
                    4,
                    2,
                    ""),
            new ViablePlacement("d302h322j020",
                    "RRRRRWRWW",
                    3,
                    3,
                    "a130"),
    };

    static final ViablePlacement[] VP_SIDES = {
            new ViablePlacement("j001",
                    "RRRRRWRWW",
                    0,
                    2,
                    "f011f013"),
            new ViablePlacement("j011",
                    "RRRRRWRWW",
                    0,
                    2,
                    ""),
            new ViablePlacement("h620",
                    "RRRRRWRWW",
                    8,
                    3,
                    "i733"),
            new ViablePlacement("j011",
                    "RRRRRWRWW",
                    0,
                    3,
                    ""),
    };

    static final ViablePlacement[] VP_REAL = {
            new ViablePlacement("",
                    "RRRBWBBRB",
                    0,
                    0,
                    "a000a003b001b003c001c002d000d002d003e000e001e003f000f001f002f003g000g002h000h001h003i000i002i003j000j001j003"),
            new ViablePlacement("a000b013c113d302",
                    "RRRBWBBRB",
                    3,
                    2,
                    "e323i323"),
            new ViablePlacement("a000b013c113d302e323",
                    "RRRBWBBRB",
                    4,
                    0,
                    "f400f402h401"),
            new ViablePlacement("",
                    "RRWWRWWRR",
                    0,
                    0,
                    "a000a003b001b003c001c002d000d002d003e000e001e003f000f001f002f003g000g002h000h001h003i000i002i003j000j001j003"),
            new ViablePlacement("a000b013c113e300f321",
                    "RRWWRWWRR",
                    4,
                    2,
                    "j412"),
            new ViablePlacement("a000c232e300f120g030j412",
                    "RRWWRWWRR",
                    4,
                    4,
                    "b432"),
            new ViablePlacement("c001d102",
                    "RRWWRWWRR",
                    1,
                    3,
                    "a121f130f132g121g123g130g132i121i130i132i133j130"),
    };
}

class ViablePlacement {
    String objective;
    String start;
    int xLoc;
    int yLoc;
    String expected;

    ViablePlacement(String iStart,
                    String iObjective,
                    int iX, int iY,
                    String iExpected) {
        objective = iObjective;
        start = iStart;
        xLoc = iX;
        yLoc = iY;
        expected = iExpected;
    }
}