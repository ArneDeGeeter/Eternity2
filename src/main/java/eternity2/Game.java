package eternity2;

import org.deeplearning4j.rl4j.space.Encodable;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

public class Game implements Encodable {
    private Board board;
    private ArrayList<Piece> cornerPieces = new ArrayList<>(Arrays.asList(new Piece("AQXX"), new Piece("AEXX"), new Piece("IQXX"), new Piece("QIXX")));
    private ArrayList<Piece> edgePieces = new ArrayList<>(Arrays.asList(
            new Piece("BAXA"), new Piece("JIXA"), new Piece("FAXA"), new Piece("FMXA"), new Piece("KQXA"), new Piece("GEXA"), new Piece("OIXA"), new Piece("HEXA"),
            new Piece("HMXA"), new Piece("UEXA"), new Piece("JAXI"), new Piece("RQXI"), new Piece("NMXI"), new Piece("SMXI"), new Piece("GIXI"), new Piece("OIXI"),
            new Piece("DEXI"), new Piece("LAXI"), new Piece("LMXI"), new Piece("TAXI"), new Piece("UAXI"), new Piece("BIXQ"), new Piece("BQXQ"), new Piece("JQXQ"),
            new Piece("RQXQ"), new Piece("GMXQ"), new Piece("OIXQ"), new Piece("TQXQ"), new Piece("HIXQ"), new Piece("HEXQ"), new Piece("PMXQ"), new Piece("VEXQ"),
            new Piece("RAXE"), new Piece("CMXE"), new Piece("KMXE"), new Piece("SIXE"), new Piece("SQXE"), new Piece("OAXE"), new Piece("OIXE"), new Piece("OQXE"),
            new Piece("DAXE"), new Piece("TEXE"), new Piece("HEXE"), new Piece("PEXE"), new Piece("BMXM"), new Piece("JAXM"), new Piece("JIXM"), new Piece("FAXM"),
            new Piece("GEXM"), new Piece("DEXM"), new Piece("DMXM"), new Piece("HQXM"), new Piece("PAXM"), new Piece("PMXM"), new Piece("UIXM"), new Piece("VQXM")
    ));
    private ArrayList<Piece> centerPieces = new ArrayList<>(Arrays.asList(
            new Piece("FRBB"), new Piece("NGBB"), new Piece("JCBJ"), new Piece("BHBR"), new Piece("RVBR"), new Piece("NNBR"), new Piece("KJBR"), new Piece("TFBR"),
            new Piece("VHBR"), new Piece("CGBC"), new Piece("GLBC"), new Piece("NRBK"), new Piece("ODBK"), new Piece("TOBK"), new Piece("HCBK"), new Piece("NOBS"),
            new Piece("SOBS"), new Piece("CPBG"), new Piece("TCBG"), new Piece("PUBG"), new Piece("SRBO"), new Piece("RRBD"), new Piece("KDBD"), new Piece("RSBL"),
            new Piece("FNBL"), new Piece("HLBL"), new Piece("PTBL"), new Piece("BUBT"), new Piece("FVBT"), new Piece("DPBT"), new Piece("KLBH"), new Piece("SOBH"),
            new Piece("SDBH"), new Piece("DUBH"), new Piece("LNBH"), new Piece("UCBU"), new Piece("DSBV"), new Piece("THBV"), new Piece("UFBV"), new Piece("VUBV"),
            new Piece("LOJJ"), new Piece("LPJJ"), new Piece("PSJJ"), new Piece("VFJJ"), new Piece("DOJR"), new Piece("CHJF"), new Piece("SHJF"), new Piece("DOJF"),
            new Piece("PKJF"), new Piece("OLJN"), new Piece("LOJN"), new Piece("TSJC"), new Piece("TPJC"), new Piece("NDJK"), new Piece("GLJK"), new Piece("LKJK"),
            new Piece("VPJK"), new Piece("CUJS"), new Piece("PLJG"), new Piece("HVJO"), new Piece("NVJD"), new Piece("FPJT"), new Piece("NSJT"), new Piece("TOJT"),
            new Piece("LVJH"), new Piece("UOJH"), new Piece("NFJP"), new Piece("SUJP"), new Piece("DCJP"), new Piece("THJP"), new Piece("FTJU"), new Piece("LNJU"),
            new Piece("NPJV"), new Piece("KDJV"), new Piece("DCJV"), new Piece("PTJV"), new Piece("TGRR"), new Piece("FCRF"), new Piece("FKRF"), new Piece("FLRF"),
            new Piece("SURF"), new Piece("OFRF"), new Piece("PLRF"), new Piece("UURF"), new Piece("CDRN"), new Piece("RLRC"), new Piece("RVRC"), new Piece("CNRC"),
            new Piece("OLRC"), new Piece("FKRS"), new Piece("DVRS"), new Piece("KKRG"), new Piece("KSRG"), new Piece("VPRG"), new Piece("GGRD"), new Piece("GLRD"),
            new Piece("VGRD"), new Piece("GPRT"), new Piece("HFRT"), new Piece("UURH"), new Piece("FTRP"), new Piece("NTRP"), new Piece("OKRV"), new Piece("DPRV"),
            new Piece("CDFN"), new Piece("DHFN"), new Piece("CCFK"), new Piece("KOFS"), new Piece("SUFS"), new Piece("DHFG"), new Piece("TPFG"), new Piece("UKFG"),
            new Piece("OOFO"), new Piece("LTFO"), new Piece("GUFD"), new Piece("GSFL"), new Piece("NDFT"), new Piece("LPFH"), new Piece("HOFH"), new Piece("GPFP"),
            new Piece("KPFU"), new Piece("GKFU"), new Piece("SHNN"), new Piece("VGNC"), new Piece("SLNK"), new Piece("HHNK"), new Piece("UGNS"), new Piece("NUNG"),
            new Piece("CSNG"), new Piece("PSNG"), new Piece("CCNO"), new Piece("OTNO"), new Piece("KGND"), new Piece("UKNL"), new Piece("UVNL"), new Piece("VONL"),
            new Piece("KVNT"), new Piece("SHNT"), new Piece("TTNT"), new Piece("SCNH"), new Piece("UHNP"), new Piece("VGNP"), new Piece("LSNU"), new Piece("LHNU"),
            new Piece("PCNU"), new Piece("VUNU"), new Piece("VGCC"), new Piece("SVCK"), new Piece("HOCK"), new Piece("KSCG"), new Piece("POCG"), new Piece("CPCO"),
            new Piece("HHCD"), new Piece("CTCL"), new Piece("DVCL"), new Piece("VUCL"), new Piece("SOCT"), new Piece("DLCP"), new Piece("KDCU"), new Piece("KPCV"),
            new Piece("UUCV"), new Piece("UVCV"), new Piece("LVKK"), new Piece("TGKK"), new Piece("POKK"), new Piece("SOKG"), new Piece("LLKG"), new Piece("SHKD"),
            new Piece("GVKT"), new Piece("PHKT"), new Piece("LTKH"), new Piece("LUKH"), new Piece("STSS"), new Piece("PDSG"), new Piece("GDSD"), new Piece("GTSD"),
            new Piece("LOSD"), new Piece("DPSL"), new Piece("OVST"), new Piece("UOST"), new Piece("GUSH"), new Piece("DUSH"), new Piece("OLGO"), new Piece("THGO"),
            new Piece("VTGD"), new Piece("PVGU"), new Piece("UVOO"), new Piece("LDOD"), new Piece("DUOL"), new Piece("PUOT"), new Piece("VHDD"), new Piece("HLDL"),
            new Piece("PTLH"), new Piece("UPTP"), new Piece("PVTV"), new Piece("UVHV")
    ));
    private int orientation; //0 is left, then count clockwise
    private int totalReward = 0;
    private int size = 0;
    private boolean printOutput = false;
    private int cornerPiecesUsed = 0;
    private int edgePiecesUsed = 0;
    private int centerPiecesUsed = 0;
    private ArrayList<Boolean> cornerPieceUsed = new ArrayList<>();
    private ArrayList<Boolean> edgePieceUsed = new ArrayList<>();
    private ArrayList<Boolean> centerPieceUsed = new ArrayList<>();
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd_HH.mm.ss");


    public Game(int size, boolean printOutput) throws WrongLengthException {
        if (size == 4) {
            cornerPieces = new ArrayList<>(Arrays.asList(new Piece("XXAB"), new Piece("AXXB"), new Piece("AAXX"), new Piece("BBXX")));
            edgePieces = new ArrayList<>(Arrays.asList(
                    new Piece("XBDB"), new Piece("XADA"), new Piece("XADB"), new Piece("XBDA"),
                    new Piece("XBCB"), new Piece("XACA"), new Piece("XBCA"), new Piece("XACB")));
            centerPieces = new ArrayList<>(Arrays.asList(new Piece("CCDC"), new Piece("CDDD"), new Piece("CDDC"), new Piece("CDDC")));
        }
        this.size = size;
        this.board = new Board(size);
        this.printOutput = printOutput;
        for (int i = 0; i < cornerPieces.size(); i++) {
            cornerPieceUsed.add(false);
        }
        for (int i = 0; i < edgePieces.size(); i++) {
            edgePieceUsed.add(false);
        }
        for (int i = 0; i < centerPieces.size(); i++) {
            centerPieceUsed.add(false);
        }


    }

    public int assignPiece(int a) throws WrongPieceException {
        // System.out.println("--------------------"+a+"---------------------");
        int reward = 0;
        if (board.getNextTile().isEdge()) {
            if (board.getNextTile().isCorner()) {
                if (!cornerPieceUsed.get(a % 4)) {
                    int result = board.placeNextPiece(cornerPieces.get(a % cornerPieces.size()), orientation);
                    //cornerPieces.remove(a % cornerPieces.size());
                    cornerPiecesUsed++;
                    cornerPieceUsed.remove(a % 4);
                    cornerPieceUsed.add(a % 4, true);
                    reward = result % 10;
                    orientation = (int) (result / 10);
                    totalReward = totalReward + reward;
                }

                return reward;
            }
            if (!edgePieceUsed.get(a % edgePieces.size())) {

                int result = board.placeNextPiece(edgePieces.get(a % edgePieces.size()), orientation);
                //edgePieces.remove(a % edgePieces.size());
                edgePiecesUsed++;
                edgePieceUsed.remove(a % edgePieces.size());
                edgePieceUsed.add(a % edgePieces.size(), true);
                reward = result % 10;
                orientation = (int) (result / 10);
                totalReward = totalReward + reward;
            }
            return reward;
        }
        if (!centerPieceUsed.get(a % (centerPieces.size() * 4) / 4)) {
            int index = (a % (centerPieces.size() * 4)) / 4;
            int rotation = a % 4;
            Piece p = centerPieces.get(index);
            for (int i = 0; i < rotation; i++) {
                p.rotateRight();
            }
            int result = board.placeNextPiece(p, orientation);
            // centerPieces.remove(index);
            centerPiecesUsed++;
            centerPieceUsed.remove(index);
            centerPieceUsed.add(index, true);
            reward = result % 10;
            orientation = (int) (result / 10);
            totalReward = totalReward + reward;
        }
        return reward;
    }

    public void print() {
        this.board.print();
    }

    public boolean isFinished() throws FileNotFoundException, UnsupportedEncodingException {
     /*   System.out.println(cornerPiecesUsed==cornerPieces.size()&&edgePiecesUsed==edgePieces.size()&&centerPiecesUsed==centerPieces.size());
        System.out.println(cornerPiecesUsed);
        System.out.println(edgePiecesUsed);
        System.out.println(centerPiecesUsed);*/
        if (cornerPiecesUsed == cornerPieces.size() && edgePiecesUsed == edgePieces.size() && centerPiecesUsed == centerPieces.size()) {
            Path path = Paths.get("E:\\school\\OS1\\Labos\\Eternity2\\Output");
            /*if (!Files.exists(Paths.get("E:\\school\\OS1\\Labos\\Eternity2\\Output\\" + size + "\\" + totalReward))) {
                File file=new File("E:\\school\\OS1\\Labos\\Eternity2\\Output\\" + size + "\\" + totalReward + "\\" + System.currentTimeMillis() + "_" + totalReward+".txt");
                file.getParentFile().mkdirs();
                PrintWriter writer = new PrintWriter("E:\\school\\OS1\\Labos\\Eternity2\\Output\\" + size + "\\" + totalReward + "\\" + System.currentTimeMillis() + "_" + totalReward+".txt", "UTF-8");
                board.write(writer);
                writer.close();
            }*/

            if (printOutput) {
                if (Toy.points.containsKey(this.totalReward)) {
                    Toy.points.put(this.totalReward, Toy.points.get(this.totalReward) + 1);
                } else {
                    Toy.points.put(this.totalReward, this.totalReward + 1);

                }
                if (System.currentTimeMillis() > Toy.time + 1000 * 60 * 10) {
                    Toy.time = System.currentTimeMillis();
                    Iterator it = Toy.points.entrySet().iterator();
                    Timestamp timestamp = new Timestamp(System.currentTimeMillis());

                    File file = new File("E:\\school\\OS1\\Labos\\Eternity2\\Output\\" + size + "\\Stats\\" + sdf.format(timestamp) + ".txt");
                    file.getParentFile().mkdirs();
                    PrintWriter writer = new PrintWriter(file, "UTF-8");
                    while (it.hasNext()) {
                        Map.Entry pair = (Map.Entry) it.next();
                        writer.println(pair.getKey() + " = " + pair.getValue());
                        it.remove(); // avoids a ConcurrentModificationException
                    }
                    writer.close();
                }
                if (totalReward >= (size * (size - 1)) * 2 * 0.7) {
                    if (totalReward >= (size * (size - 1)) * 2 * 0.9) {
                        printImage();
                    }
                    File file = new File("E:\\school\\OS1\\Labos\\Eternity2\\Output\\" + size + "\\" + "70%AndHigher" + "\\" + totalReward + "_" + System.currentTimeMillis() + ".txt");
                    file.getParentFile().mkdirs();
                    PrintWriter writer = new PrintWriter(file, "UTF-8");
                    board.write(writer);
                    writer.close();
                } /*else {
                    File file = new File("E:\\school\\OS1\\Labos\\Eternity2\\Output\\" + size + "\\" + "sub70" + "\\" + totalReward + "_" + System.currentTimeMillis() + ".txt");
                    file.getParentFile().mkdirs();
                    PrintWriter writer = new PrintWriter(file, "UTF-8");
                    board.write(writer);
                    writer.close();
                }*/
            }
        }
        return cornerPiecesUsed == cornerPieces.size() && edgePiecesUsed == edgePieces.size() && centerPiecesUsed == centerPieces.size();
    }

    public void resetBoard() throws WrongLengthException {
        totalReward = 0;
        if (size == 4) {
            cornerPieces = new ArrayList<>(Arrays.asList(new Piece("XXAB"), new Piece("AXXB"), new Piece("AAXX"), new Piece("BBXX")));
            edgePieces = new ArrayList<>(Arrays.asList(
                    new Piece("XBDB"), new Piece("XADA"), new Piece("XADB"), new Piece("XBDA"),
                    new Piece("XBCB"), new Piece("XACA"), new Piece("XBCA"), new Piece("XACB")));
            centerPieces = new ArrayList<>(Arrays.asList(new Piece("CCDC"), new Piece("CDDD"), new Piece("CDDC"), new Piece("CDDC")));

        } else {
            cornerPieces = new ArrayList<>(Arrays.asList(new Piece("AQXX"), new Piece("AEXX"), new Piece("IQXX"), new Piece("QIXX")));
            edgePieces = new ArrayList<>(Arrays.asList(
                    new Piece("BAXA"), new Piece("JIXA"), new Piece("FAXA"), new Piece("FMXA"), new Piece("KQXA"), new Piece("GEXA"), new Piece("OIXA"), new Piece("HEXA"),
                    new Piece("HMXA"), new Piece("UEXA"), new Piece("JAXI"), new Piece("RQXI"), new Piece("NMXI"), new Piece("SMXI"), new Piece("GIXI"), new Piece("OIXI"),
                    new Piece("DEXI"), new Piece("LAXI"), new Piece("LMXI"), new Piece("TAXI"), new Piece("UAXI"), new Piece("BIXQ"), new Piece("BQXQ"), new Piece("JQXQ"),
                    new Piece("RQXQ"), new Piece("GMXQ"), new Piece("OIXQ"), new Piece("TQXQ"), new Piece("HIXQ"), new Piece("HEXQ"), new Piece("PMXQ"), new Piece("VEXQ"),
                    new Piece("RAXE"), new Piece("CMXE"), new Piece("KMXE"), new Piece("SIXE"), new Piece("SQXE"), new Piece("OAXE"), new Piece("OIXE"), new Piece("OQXE"),
                    new Piece("DAXE"), new Piece("TEXE"), new Piece("HEXE"), new Piece("PEXE"), new Piece("BMXM"), new Piece("JAXM"), new Piece("JIXM"), new Piece("FAXM"),
                    new Piece("GEXM"), new Piece("DEXM"), new Piece("DMXM"), new Piece("HQXM"), new Piece("PAXM"), new Piece("PMXM"), new Piece("UIXM"), new Piece("VQXM")
            ));
            centerPieces = new ArrayList<>(Arrays.asList(
                    new Piece("FRBB"), new Piece("NGBB"), new Piece("JCBJ"), new Piece("BHBR"), new Piece("RVBR"), new Piece("NNBR"), new Piece("KJBR"), new Piece("TFBR"),
                    new Piece("VHBR"), new Piece("CGBC"), new Piece("GLBC"), new Piece("NRBK"), new Piece("ODBK"), new Piece("TOBK"), new Piece("HCBK"), new Piece("NOBS"),
                    new Piece("SOBS"), new Piece("CPBG"), new Piece("TCBG"), new Piece("PUBG"), new Piece("SRBO"), new Piece("RRBD"), new Piece("KDBD"), new Piece("RSBL"),
                    new Piece("FNBL"), new Piece("HLBL"), new Piece("PTBL"), new Piece("BUBT"), new Piece("FVBT"), new Piece("DPBT"), new Piece("KLBH"), new Piece("SOBH"),
                    new Piece("SDBH"), new Piece("DUBH"), new Piece("LNBH"), new Piece("UCBU"), new Piece("DSBV"), new Piece("THBV"), new Piece("UFBV"), new Piece("VUBV"),
                    new Piece("LOJJ"), new Piece("LPJJ"), new Piece("PSJJ"), new Piece("VFJJ"), new Piece("DOJR"), new Piece("CHJF"), new Piece("SHJF"), new Piece("DOJF"),
                    new Piece("PKJF"), new Piece("OLJN"), new Piece("LOJN"), new Piece("TSJC"), new Piece("TPJC"), new Piece("NDJK"), new Piece("GLJK"), new Piece("LKJK"),
                    new Piece("VPJK"), new Piece("CUJS"), new Piece("PLJG"), new Piece("HVJO"), new Piece("NVJD"), new Piece("FPJT"), new Piece("NSJT"), new Piece("TOJT"),
                    new Piece("LVJH"), new Piece("UOJH"), new Piece("NFJP"), new Piece("SUJP"), new Piece("DCJP"), new Piece("THJP"), new Piece("FTJU"), new Piece("LNJU"),
                    new Piece("NPJV"), new Piece("KDJV"), new Piece("DCJV"), new Piece("PTJV"), new Piece("TGRR"), new Piece("FCRF"), new Piece("FKRF"), new Piece("FLRF"),
                    new Piece("SURF"), new Piece("OFRF"), new Piece("PLRF"), new Piece("UURF"), new Piece("CDRN"), new Piece("RLRC"), new Piece("RVRC"), new Piece("CNRC"),
                    new Piece("OLRC"), new Piece("FKRS"), new Piece("DVRS"), new Piece("KKRG"), new Piece("KSRG"), new Piece("VPRG"), new Piece("GGRD"), new Piece("GLRD"),
                    new Piece("VGRD"), new Piece("GPRT"), new Piece("HFRT"), new Piece("UURH"), new Piece("FTRP"), new Piece("NTRP"), new Piece("OKRV"), new Piece("DPRV"),
                    new Piece("CDFN"), new Piece("DHFN"), new Piece("CCFK"), new Piece("KOFS"), new Piece("SUFS"), new Piece("DHFG"), new Piece("TPFG"), new Piece("UKFG"),
                    new Piece("OOFO"), new Piece("LTFO"), new Piece("GUFD"), new Piece("GSFL"), new Piece("NDFT"), new Piece("LPFH"), new Piece("HOFH"), new Piece("GPFP"),
                    new Piece("KPFU"), new Piece("GKFU"), new Piece("SHNN"), new Piece("VGNC"), new Piece("SLNK"), new Piece("HHNK"), new Piece("UGNS"), new Piece("NUNG"),
                    new Piece("CSNG"), new Piece("PSNG"), new Piece("CCNO"), new Piece("OTNO"), new Piece("KGND"), new Piece("UKNL"), new Piece("UVNL"), new Piece("VONL"),
                    new Piece("KVNT"), new Piece("SHNT"), new Piece("TTNT"), new Piece("SCNH"), new Piece("UHNP"), new Piece("VGNP"), new Piece("LSNU"), new Piece("LHNU"),
                    new Piece("PCNU"), new Piece("VUNU"), new Piece("VGCC"), new Piece("SVCK"), new Piece("HOCK"), new Piece("KSCG"), new Piece("POCG"), new Piece("CPCO"),
                    new Piece("HHCD"), new Piece("CTCL"), new Piece("DVCL"), new Piece("VUCL"), new Piece("SOCT"), new Piece("DLCP"), new Piece("KDCU"), new Piece("KPCV"),
                    new Piece("UUCV"), new Piece("UVCV"), new Piece("LVKK"), new Piece("TGKK"), new Piece("POKK"), new Piece("SOKG"), new Piece("LLKG"), new Piece("SHKD"),
                    new Piece("GVKT"), new Piece("PHKT"), new Piece("LTKH"), new Piece("LUKH"), new Piece("STSS"), new Piece("PDSG"), new Piece("GDSD"), new Piece("GTSD"),
                    new Piece("LOSD"), new Piece("DPSL"), new Piece("OVST"), new Piece("UOST"), new Piece("GUSH"), new Piece("DUSH"), new Piece("OLGO"), new Piece("THGO"),
                    new Piece("VTGD"), new Piece("PVGU"), new Piece("UVOO"), new Piece("LDOD"), new Piece("DUOL"), new Piece("PUOT"), new Piece("VHDD"), new Piece("HLDL"),
                    new Piece("PTLH"), new Piece("UPTP"), new Piece("PVTV"), new Piece("UVHV")
            ));
        }
        cornerPieceUsed.clear();
        edgePieceUsed.clear();
        centerPieceUsed.clear();
        cornerPiecesUsed = 0;
        edgePiecesUsed = 0;
        centerPiecesUsed = 0;
        for (int i = 0; i < cornerPieces.size(); i++) {
            cornerPieceUsed.add(false);
        }
        for (int i = 0; i < edgePieces.size(); i++) {
            edgePieceUsed.add(false);
        }
        for (int i = 0; i < centerPieces.size(); i++) {
            centerPieceUsed.add(false);
        }
        board.removePieces();

    }

    public static Game generateFromFile(File file) throws WrongLengthException, FileNotFoundException {
        BufferedReader br = new BufferedReader(new FileReader(file));
        Game g = null;
        try {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();
            if (line != null) {
                g = new Game(line.split(",").length, false);
            }
            while (line != null) {
                sb.append(line + ",");
                line = br.readLine();
            }
            String everything = sb.toString();
            String[] pieces = everything.split(",");
            g.totalReward = g.board.placeAllPieces(pieces);
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return g;
    }

    @Override
    public double[] toArray() {
        return board.toArray();

    }

    public void printImage() {
        if (map == null) {
            map = new HashMap<>();
            for (int i = 0; i < list.size(); i++) {
                map.put(list.get(i), img2matrix(in, i));
            }
        }
        ArrayList<Tile> tiles = board.getLeftTiles();
        int[][] image = new int[x * size + size * 3][x * size + size * 3];
        int horizontal = 0;
        int vertical = 0;
        for (Tile t : tiles) {
            horizontal = 0;
            while (t != null) {
                Piece p = t.getPiece();
                int[][] temp = map.get(p.getLeft());
                for (int i = 0; i < x; i++) {
                    for (int j = 0; j < x; j++) {
                        try {
                            image[horizontal + i][vertical + j] = temp[j][i] + image[horizontal + i][vertical + j];
                        } catch (ArrayIndexOutOfBoundsException e) {
                        }
                    }

                }
                temp = map.get(p.getTop());
                for (int i = 0; i < x; i++) {
                    for (int j = 0; j < x; j++) {
                        try {
                            image[horizontal + i][vertical + j] = temp[i][j] + image[horizontal + i][vertical + j];
                        } catch (ArrayIndexOutOfBoundsException e) {
                        }
                    }

                }
                temp = map.get(p.getBottom());
                for (int i = 0; i < x; i++) {
                    for (int j = 0; j < x; j++) {
                        try {
                            image[horizontal + i][vertical + x - j] = temp[i][j] + image[horizontal + i][vertical + x - j];
                        } catch (ArrayIndexOutOfBoundsException e) {
                        }
                    }

                }
                temp = map.get(p.getRight());
                for (int i = 0; i < x; i++) {
                    for (int j = 0; j < x; j++) {
                        try {
                            image[horizontal - i + x][vertical + j] = temp[j][i] + image[horizontal - i + x][vertical + j];
                        } catch (ArrayIndexOutOfBoundsException e) {
                        }
                    }

                }
                horizontal = horizontal + x + 3;
                t = t.getRightTile();

            }
            vertical = vertical + x + 3;
        }
        makeImage(image);
    }

    private int[][] img2matrix(BufferedImage bi, int number) {
        int[][] c = new int[bi.getHeight() / 3][bi.getWidth() / 4];
        for (int i = x * (number % 4); i < (number % 4 + 1) * x; i++) {
            for (int j = (number / 4) * y; j < (number / 4 + 1) * y; j++) {
                c[i % x][j % y] = (bi.getRGB(i, j));
            }
        }
        return c;
    }

    private void makeImage(int[][] c) {
        BufferedImage image = new BufferedImage(c.length, c[0].length, BufferedImage.TYPE_INT_RGB);
        for (int i = 0; i < c.length; i++) {
            for (int j = 0; j < c[0].length; j++) {
                int a = c[i][j];
                Color newColor = new Color(a);
                image.setRGB(i, j, newColor.getRGB());
            }
        }
        File output = new File("E:\\school\\OS1\\Labos\\Eternity2\\Output\\" + this.size + "\\Images\\" + totalReward + "_" + System.currentTimeMillis() + ".jpg");
        output.mkdirs();
        try {
            ImageIO.write(image, "jpg", output);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static File pieces = new File("pieces.png");
    private static BufferedImage in;

    static {
        try {
            in = ImageIO.read(pieces);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static int y = in.getWidth() / 8;
    private static int x = in.getHeight() / 3;
    private static HashMap<Character, int[][]> map;
    private static ArrayList<Character> list = new ArrayList<>(Arrays.asList('X', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V'));

}
